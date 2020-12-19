package game.state;

import game.GameEngine;
import game.SoundEngine;
import game.player.Player;
import game.player.Territory;
import javafx.event.ActionEvent;

import java.util.Arrays;

public class WarState implements GameState{

    private static WarState instance;
    private AttackingState attack;
    private GameEngine engine;
    private int minMovingArmy;
    private int maxMovingArmy;
    private WarState() {
    }

    public static WarState getInstance() {
        if (instance == null) {
            synchronized (WarState.class) {
                if (instance == null) {
                    instance = new WarState();
                }
            }
        }
        return instance;
    }

    // wage war on an enemy territory
    public void war() {
        Territory attackingTerritory = attack.getSource();
        Territory defendingTerritory = attack.getDestination();
        int[] attackingDice = new int[attack.getAttackingArmies()];
        minMovingArmy = attackingDice.length; //only used if the territory is captured
        maxMovingArmy = attackingTerritory.getNumOfArmies() - 1; //only used if the territory is captured
        int[] defendingDice = new int[attack.getDefendingArmies()];
        int attackingLostDice = 0;
        int defendingLostDice = 0;

        SoundEngine.getInstance().playDiceRoll();

        for (int i = 0; i < attackingDice.length; i++) {
            attackingDice[i] = (int) (Math.random() * 6 + 1);
        }
        for (int i = 0; i < defendingDice.length; i++) {
            defendingDice[i] = (int) (Math.random() * 6 + 1);
        }
        boolean[] diceResults;

        if (attackingDice.length > defendingDice.length) {
            diceResults = new boolean[defendingDice.length];
        } else {
            diceResults = new boolean[attackingDice.length];
        }

        Arrays.sort(attackingDice);
        int tmp = attackingDice[0];
        attackingDice[0] = attackingDice[attackingDice.length - 1];
        attackingDice[attackingDice.length - 1] = tmp;
        Arrays.sort(defendingDice);
        tmp = defendingDice[0];
        defendingDice[0] = defendingDice[defendingDice.length - 1];
        defendingDice[defendingDice.length - 1] = tmp;

        for (int i = 0; i < attackingDice.length && i < defendingDice.length; i++) {
            // compare
            if (attackingDice[i] > defendingDice[i]) {
                defendingLostDice++;
                diceResults[i] = true;
            } else {
                attackingLostDice++;
                diceResults[i] = false;
            }
        }

        engine.getController().displayBattleResult(attackingDice, defendingDice, diceResults, attackingTerritory.getRuler(), defendingTerritory.getRuler());

        attackingTerritory.setNumOfArmies(attackingTerritory.getNumOfArmies() - attackingLostDice);
        defendingTerritory.setNumOfArmies(defendingTerritory.getNumOfArmies() - defendingLostDice);

    }

    public void terminating() {
        Territory defendingTerritory = attack.getDestination();
        Territory attackingTerritory = attack.getSource();

        if (defendingTerritory.getNumOfArmies() == 0) {
            Player pastRuler = defendingTerritory.getRuler();
            pastRuler.setNumOfTerritory(pastRuler.getNumOfTerritory() - 1);
            defendingTerritory.setRuler(engine.getCurrentPlayer());
            engine.getCurrentPlayer().setNumOfTerritory(engine.getCurrentPlayer().getNumOfTerritory() + 1);
            //call displayTroopSelector to get the moving armies
            engine.getController().displayTroopSelector(minMovingArmy,maxMovingArmy); // disable back
            engine.getController().disableCancel();
            if (engine.isEliminated(pastRuler)) { // remove player if they have no territories left
                engine.removePlayer(pastRuler);
            }
        } else if (attackingTerritory.getNumOfArmies() == 1) {
            attack.switchState(AttackingPlanningState.getInstance());
        } else {
            attack.switchState(DiceSelectionState.getInstance());
        }
    }

    public void moveArmies(int movingArmies){
        Territory defendingTerritory = attack.getDestination();
        Territory attackingTerritory = attack.getSource();
        attackingTerritory.setNumOfArmies(attackingTerritory.getNumOfArmies() - movingArmies);
        defendingTerritory.setNumOfArmies(movingArmies);
        attack.switchState(AttackingPlanningState.getInstance());
    }

    // Map is not used in this state
    @Override
    public void mapSelect(int territory) {}

    @Override
    public void start() {
        attack = AttackingState.getInstance();
        engine = GameEngine.getInstance();
        war();
    }
}

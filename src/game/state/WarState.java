package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import javafx.event.ActionEvent;

import java.util.Arrays;

public class WarState implements GameState{

    private static WarState instance;
    private AttackingState attack;
    private GameEngine engine;
    private int movingArmies;
    private int minMovingArmy;
    private int maxMovingArmy;

    private WarState() {
        attack = AttackingState.getInstance();
        engine = GameEngine.getInstance();
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

//    public void displayWar(int[] attackingDice, int[] defendingDice, Player attacker, Player defender) {
//        // to do
//    }

    public void armyNumberSelection(int count) { //called by engine
        movingArmies = count;
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

        for (int i = 0; i < attackingDice.length; i++) {
            attackingDice[i] = (int) (Math.random() * 6 + 1);
        }
        for (int i = 0; i < defendingDice.length; i++) {
            defendingDice[i] = (int) (Math.random() * 6 + 1);
        }

        Arrays.sort(attackingDice);
        Arrays.sort(defendingDice);

        engine.getController().displayBattleResult(attackingDice, defendingDice, attackingTerritory.getRuler(), defendingTerritory.getRuler());

        for (int i = 0; i < attackingDice.length && i < defendingDice.length; i++) {
            // compare
            if (attackingDice[i] > defendingDice[i]) {
                defendingLostDice++;
            } else {
                attackingLostDice++;
            }
        }

        attackingTerritory.setNumOfArmies(attackingTerritory.getNumOfArmies() - attackingLostDice);
        defendingTerritory.setNumOfArmies(defendingTerritory.getNumOfArmies() - defendingLostDice);

        terminating();
    }

    private void terminating() {
        Territory defendingTerritory = attack.getDestination();
        Territory attackingTerritory = attack.getSource();

        if (defendingTerritory.getNumOfArmies() == 0) {
            Player pastRuler = defendingTerritory.getRuler();
            defendingTerritory.setRuler(engine.getCurrentPlayer());
            //call displayTroopSelector to get the moving armies
            engine.mapScene.getController().displayTroopSelector(minMovingArmy,maxMovingArmy);
            attackingTerritory.setNumOfArmies(attackingTerritory.getNumOfArmies() - movingArmies);
            defendingTerritory.setNumOfArmies(movingArmies);
            if (engine.isEliminated(pastRuler)) { // remove player if they have no territories left
                engine.removePlayer(pastRuler);
            }
            attack.switchState(AttackingPlanningState.getInstance());
        } else if (attackingTerritory.getNumOfArmies() == 1) {
            attack.switchState(AttackingPlanningState.getInstance());
        } else {
            attack.switchState(DiceSelectionState.getInstance());
        }
    }

    // Map is not used in this state
    @Override
    public void mapSelect(int territory) {}

    @Override
    public void start() {
        war();
    }
}

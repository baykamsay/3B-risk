package game.state;

import game.GameEngine;
import game.SoundEngine;
import game.player.Player;
import game.player.Territory;
import game.player.faculties.Faculty;
import game.player.faculties.Fas;
import game.player.faculties.Ibef;
import game.player.faculties.Mf;
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
        int[] defendingDice;

        // Use IBEF ability
        boolean ibefAbilityUsed = false;
        if ((defendingTerritory.getRuler().getFaculty() instanceof Ibef)
            && attackingTerritory.getNumOfArmies() > defendingTerritory.getNumOfArmies()){
            defendingDice = new int[attack.getDefendingArmies() + 1];
            ibefAbilityUsed = true;
        }
        else {
            defendingDice = new int[attack.getDefendingArmies()];
        }
        int attackingLostDice = 0;
        int defendingLostDice = 0;

        SoundEngine.getInstance().playDiceRoll();

        for (int i = 0; i < attackingDice.length; i++) {
            if (engine.getCurrentPlayer().getFaculty() instanceof Mf){
                attackingDice[i] = (int) (Math.random() * 5 + 2);
            }
            else {
                attackingDice[i] = (int) (Math.random() * 6 + 1);
            }
        }
        for (int i = 0; i < defendingDice.length; i++) {
            if (defendingTerritory.getRuler().getFaculty() instanceof Mf){
                defendingDice[i] = (int) (Math.random() * 5 + 2);
            }
            else {
                defendingDice[i] = (int) (Math.random() * 6 + 1);
            }
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

        //Art Faculty ability used
        if(AttackingState.getInstance().getArtAbilityCanUse()){
            int temp = attackingDice[attackingDice.length - 1]; //min of the attacker
            attackingDice[attackingDice.length - 1] = defendingDice[0]; //put the greatest of the defender to the attacker
            defendingDice[0] = temp;
            AttackingState.getInstance().setArtAbilityCanUse(false);
        }

        //Econ ability used
        if(AttackingState.getInstance().getEconAbilityCanUse() && defendingDice[0] > 1){
            AttackingState.getInstance().setEconAbilityCanUse(false);
            defendingDice[0] -= 1;
        }

        for (int i = 0; i < attackingDice.length && i < defendingDice.length; i++) {
            // compare
            if (attackingDice[i] > defendingDice[i]) {
                defendingLostDice++;
                diceResults[i] = true;
            }
            else if ((engine.getCurrentPlayer().getFaculty() instanceof Fas)
                && attackingTerritory.getArea().getName() == "EASTCAMPUS" && attackingDice[i] == defendingDice[i]) {
                defendingLostDice++;
                diceResults[i] = true;
            }
            else
            {
                attackingLostDice++;
                diceResults[i] = false;
            }
        }

        engine.getController().displayBattleResult(attackingDice, defendingDice, diceResults, attackingTerritory.getRuler(), defendingTerritory.getRuler());

        attackingTerritory.setNumOfArmies(attackingTerritory.getNumOfArmies() - attackingLostDice);

        if(ibefAbilityUsed && defendingLostDice > attack.getDefendingArmies()) {
            defendingLostDice = attack.getDefendingArmies();
        }
        defendingTerritory.setNumOfArmies(defendingTerritory.getNumOfArmies() - defendingLostDice);
        maxMovingArmy = attackingTerritory.getNumOfArmies() - 1; //only used if the territory is captured
    }

    public void terminating() {
        Territory defendingTerritory = attack.getDestination();
        Territory attackingTerritory = attack.getSource();

        if (defendingTerritory.getNumOfArmies() == 0) {
            GameEngine.getInstance().getController().deselect();
            Player pastRuler = defendingTerritory.getRuler();
            pastRuler.setNumOfTerritory(pastRuler.getNumOfTerritory() - 1);
            defendingTerritory.setRuler(engine.getCurrentPlayer());
            engine.getCurrentPlayer().setNumOfTerritory(engine.getCurrentPlayer().getNumOfTerritory() + 1);
            //call displayTroopSelector to get the moving armies
            engine.getController().displayTroopSelector(minMovingArmy,maxMovingArmy);
            // Disable Cancel
            engine.getController().disableCancel();
            // Disable pass
            engine.getController().disablePassButton();
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
        engine.getController().enablePassButton();
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

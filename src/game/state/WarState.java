package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import javafx.event.ActionEvent;

public class WarState implements GameState{

    private static WarState instance;
    private AttackingState attack;
    private GameEngine engine;

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

    public void displayWar(int[] attackingDice, int[] defendingDice) {
        // to do
    }

    public int displayArmyNumberSelection() {
        // to do
        return 1;
    }

    // returns the location of the maximum number in an int array
    private int getMax(int[] arr) {
        int max = -1;
        int maxLocation = -1;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
                maxLocation = i;
            }
        }
        return maxLocation;
    }

    // wage war on an enemy territory
    public void war() {
        int[] attackingDice = {0, 0, 0};
        int[] defendingDice = {0, 0};
        int attackingLostDice = 0;
        int defendingLostDice = 0;

        for (int i = 0; i < attack.getAttackingArmies(); i++) {
            attackingDice[i] = (int) (Math.random() * 6 + 1);
        }
        for (int i = 0; i < attack.getDefendingArmies(); i++) {
            defendingDice[i] = (int) (Math.random() * 6 + 1);
        }

        displayWar(attackingDice, defendingDice);

        // find max rolls
        int maxLocation;
        maxLocation = getMax(attackingDice);
        int maxAttack = attackingDice[maxLocation];
        attackingDice[maxLocation] = 0;
        maxLocation = getMax(defendingDice);
        int maxDefend = defendingDice[maxLocation];
        defendingDice[maxLocation] = 0;

        // compare
        if (maxAttack > maxDefend) {
            defendingLostDice++;
        } else {
            attackingLostDice++;
        }

        maxLocation = getMax(defendingDice);
        maxDefend = defendingDice[maxLocation];

        if (maxDefend > 0) {
            defendingDice[maxLocation] = 0;
            maxLocation = getMax(attackingDice);
            maxAttack = attackingDice[maxLocation];

            if (maxAttack > 0) {
                attackingDice[maxLocation] = 0;

                // compare
                if (maxAttack > maxDefend) {
                    defendingLostDice++;
                } else {
                    attackingLostDice++;
                }
            }
        }

        Territory attackingTerritory = attack.getSource();
        Territory defendingTerritory = attack.getDestination();

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
            int movingArmies = displayArmyNumberSelection();
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
    public void mapSelect(ActionEvent e) {}

    @Override
    public void start() {
        war();
    }
}

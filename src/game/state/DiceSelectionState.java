package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class DiceSelectionState implements GameState {

    private static DiceSelectionState instance;
    private GameEngine engine;
    private AttackingState attack;
    private int attackDiceNo, defendDiceNo,
                aChosenDice, dChosenDice;

    private DiceSelectionState() {
        engine = GameEngine.getInstance();
        attack = AttackingState.getInstance();
        attackDiceNo = 0;
        defendDiceNo = 0;
        aChosenDice = 0;
        dChosenDice = 0;
    }

    public static DiceSelectionState getInstance() {
        if (instance == null) {
            synchronized (DiceSelectionState.class) {
                if (instance == null) {
                    instance = new DiceSelectionState();
                }
            }
        }
        return instance;
    }

    public void displayDiceSelection(ActionEvent e) {
        // to do
    }

    public void mapSelect(ActionEvent e){
        calculateSourceDiceNo();
        calculateDestinationDiceNo();
        displayDiceSelection(e);
        attack.setAttackingArmies(aChosenDice);
        attack.setDefendingArmies(dChosenDice);
        attack.switchState(WarState.getInstance());
    }

    public void calculateSourceDiceNo(){
        attack.getSource().getNumOfArmies();
        if (attack.getSource().getNumOfArmies() > 3){
            attackDiceNo = 3;
        }
        else if (attack.getSource().getNumOfArmies() == 3) {
            attackDiceNo = 2;
        }
        else if (attack.getSource().getNumOfArmies() == 2) {
            attackDiceNo = 1;
        }
        else
        {
            attackDiceNo = 0;
        }
    }

    public void calculateDestinationDiceNo(){
        if (attack.getDestination().getNumOfArmies() >= 2) {
            defendDiceNo = 2;
        }
        else
        {
            defendDiceNo = 1;
        }
    }

    public int getDefendingDiceNo() {
        return defendDiceNo;
    }

    public int getAttackingDiceNo() {
        return attackDiceNo;
    }

    public int getChosenDefendingDice() {
        return dChosenDice;
    }

    public int getChosenAttackingDice() {
        return aChosenDice;
    }

    public void setChosenDefendingDice(int d) {
        dChosenDice = d;
    }

    public void setChosenAttackingDice(int a) {
        aChosenDice = a;
    }
}

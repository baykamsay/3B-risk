package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class DiceSelectionState implements GameState {

    private static DiceSelectionState instance;
    private GameEngine engine;
    private AttackingState attack;
    private int currentPlayer, leastDiceNo;
    private Territory territory;

    private DiceSelectionState() {
        engine = GameEngine.getInstance();
        attack = AttackingState.getInstance();
        territory = null;
        currentPlayer = 0;
        leastDiceNo = 0;
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

    public void mapSelect(ActionEvent e){
        calculateLeastDiceNo();
        //pass leastDiceNo && e to the pop up ui, it will return the selected number of dice
        //engine.switchState(WarState(...));
    }

    public void calculateLeastDiceNo(){
        int troopAmount = territory.getNumOfArmies();
        if (troopAmount > 3){
            leastDiceNo = 3;
        }
        else if (troopAmount == 2) {
            leastDiceNo = 2;
        }
        else
        {
            leastDiceNo = 0;
        }
    }

//    public int getLeastDiceNo(){
//        return leastDiceNo;
//    }
//
//    public int calculateLeastDiceNo(int troopAmount){
//        if (troopAmount > 3){
//            leastDiceNo = 3;
//            return leastDiceNo;
//        }
//        else if (troopAmount == 2) {
//            leastDiceNo = 2;
//            return leastDiceNo;
//        }
//        else
//        {
//            leastDiceNo = 0;
//            return leastDiceNo;
//        }
//    }
//
////    public int getLeastDiceNo(){
////        return leastDiceNo;
////    }
////
////    public int getSelectedDiceNo(){
////        return selectedDiceNo;
////    }
//
//
//
}

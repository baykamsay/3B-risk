package game.state;

import game.GameEngine;
import javafx.event.ActionEvent;

public class FortifyingArmySelectionMenuState implements GameState{
    private static FortifyingArmySelectionMenuState instance;
    private FortifyingState fortify;
    private GameEngine engine;

    private FortifyingArmySelectionMenuState() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
    }

    public static FortifyingArmySelectionMenuState getInstance() {
        if (instance == null) {
            synchronized (FortifyingArmySelectionMenuState.class) {
                if (instance == null) {
                    instance = new FortifyingArmySelectionMenuState();
                }
            }
        }
        return instance;
    }

    public void displayArmyNumberSelection(ActionEvent e) {
        if (fortify.getSource().getNumOfArmies() > 1) {
            //to do
            //setNumOfArmies(...);
            engine.setPlayerTurn(engine.getPlayerTurn() + 1);
            engine.switchState(ArmyPlacementState.getInstance());
        }
    }

    public void setNumOfArmies(int armies){
        fortify.getDestination().setNumOfArmies(armies);
        fortify.getSource().setNumOfArmies(fortify.getSource().getNumOfArmies() - armies);
    }

    public void mapSelect(ActionEvent e) {
        //not implemented
    }
}

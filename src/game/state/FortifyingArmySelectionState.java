package game.state;

import game.GameEngine;
import javafx.event.ActionEvent;

public class FortifyingArmySelectionState implements GameState{
    private static FortifyingArmySelectionState instance;
    private FortifyingState fortify;
    private GameEngine engine;

    private FortifyingArmySelectionState() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
    }

    public static FortifyingArmySelectionState getInstance() {
        if (instance == null) {
            synchronized (FortifyingArmySelectionState.class) {
                if (instance == null) {
                    instance = new FortifyingArmySelectionState();
                }
            }
        }
        return instance;
    }

    public int displayArmyNumberSelection() {
        // to do
        return 1;
    }

    public void setNumOfArmies(int armies){
        fortify.getDestination().setNumOfArmies(fortify.getDestination().getNumOfArmies() + armies);
        fortify.getSource().setNumOfArmies(fortify.getSource().getNumOfArmies() - armies);
    }

    public void mapSelect(ActionEvent e) {
        //not implemented
    }

    @Override
    public void start() {
        int armies = displayArmyNumberSelection();
        setNumOfArmies(armies);
        engine.setPlayerTurn(engine.getPlayerTurn() + 1);
        engine.switchState(ArmyPlacementState.getInstance());
    }
}

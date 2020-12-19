package game.state;

import game.GameEngine;

public class FortifyingArmySelectionState implements GameState{
    private static FortifyingArmySelectionState instance;
    private FortifyingState fortify;
    private GameEngine engine;

    private FortifyingArmySelectionState() {
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

    public void setNumOfArmies(int armies){
        fortify.getDestination().setNumOfArmies(fortify.getDestination().getNumOfArmies() + armies);
        fortify.getSource().setNumOfArmies(fortify.getSource().getNumOfArmies() - armies);
    }

    public void mapSelect(int territory) {
        //not implemented
    }

    @Override
    public void start() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
        //call displayTroop selector, setNumOfArmies will be called
        engine.getController().displayTroopSelector(fortify.getSource().getNumOfArmies() - 1);
        fortify.pass();
    }
}

package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class FortifyingPlanningState implements GameState {

    private static FortifyingPlanningState instance;
    private FortifyingState fortify;
    private GameEngine engine;

    private FortifyingPlanningState() {
    }

    public static FortifyingPlanningState getInstance() {
        if (instance == null) {
            synchronized (FortifyingPlanningState.class) {
                if (instance == null) {
                    instance = new FortifyingPlanningState();
                }
            }
        }
        return instance;
    }

    //Select source and destination territories
    public void mapSelect(int territory) {
        Territory t = engine.getMap().getTerritory(territory);
        if (fortify.getSource() == null) { //make sure that the first selection will be source
            //check the territory name && if player is the ruler && has at least 2 armies
            if ((t.isRuler(engine.getCurrentPlayer())) && t.getNumOfArmies() >= 2) {
                fortify.setSource(t);
            }
        }
        else if (fortify.getSource() == t) {
            fortify.setSource(null);
        }
        else {
            if(fortify.getSource().isAdjacent(t) && t.getRuler() == GameEngine.getInstance().getCurrentPlayer()) { //check if destination is adjacent to the source
                fortify.setDestination(t);
                fortify.switchState(FortifyingArmySelectionState.getInstance());
            }
        }
    }

    @Override
    public void start() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
        engine.mapScene.getController().setState(2);
        fortify.setSource(null);
        fortify.setDestination(null);
    }
}

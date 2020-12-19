package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class FortifyingPlanningState implements GameState {

    private static FortifyingPlanningState instance;
    private FortifyingState fortify;
    private GameEngine engine;
    private Territory destination;
    private Territory source;

    private FortifyingPlanningState() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
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
    if (destination == null && source == null) { //make sure that the first selection will be source
            Territory t = engine.getMap().getTerritory(territory);
                //check the territory name && if player is the ruler
                if ((t.isRuler(engine.getCurrentPlayer()))) {
                    source = t;
                }
        }
        else {
            Territory t = engine.getMap().getTerritory(territory);
                    if(source.isAdjacent(t)) { //check if destination is adjacent to the source
                        destination = t;
                        fortify.switchState(FortifyingArmySelectionState.getInstance());
                    }
        }
    }

    // not used
    @Override
    public void start() {}
}

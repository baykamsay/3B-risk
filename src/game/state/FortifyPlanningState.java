package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class FortifyPlanningState implements GameState {

    private static FortifyPlanningState instance;
    private FortifyingState fortify;
    private GameEngine engine;
    private Territory destination;
    private Territory source;

    private FortifyPlanningState() {
        fortify = FortifyingState.getInstance();
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
    }

    public static FortifyPlanningState getInstance() {
        if (instance == null) {
            synchronized (FortifyPlanningState.class) {
                if (instance == null) {
                    instance = new FortifyPlanningState();
                }
            }
        }
        return instance;
    }

    //Select source and destination territories
    public void mapSelect(ActionEvent e) {
    if (destination == null && source == null) { //make sure that the first selection will be source
            Territory[] territories = engine.getMap().getTerritories();
            for (Territory territory : territories) {
                //check the territory name && if player is the ruler
                if ((territory.getName()).equals(e.getSource().toString())
                        && (engine.getCurrentPlayer() == territory.getRuler())) {
                    source = territory;
                }
            }
        }
        else {
            Territory[] territories = engine.getMap().getTerritories();
            for (Territory territory : territories) {
                if ((territory.getName()).equals(e.getSource().toString())) {
                    if(source.isAdjacent(territory)) { //check if destination is adjacent to the source
                        destination = territory;
                    }
                }
            }
        }
    }
}

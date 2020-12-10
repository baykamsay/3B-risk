package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class FortifyPlanningState extends PlanningState implements GameState {

    public FortifyPlanningState(int width, int height, GameEngine engine) {
        super(width, height, engine);
    }

    //Select source and destination territories
    public void mapSelect(ActionEvent e) {
        if (destination.equals("")) {
            Territory[] territories = engine.getMap().getTerritories();
            for (Territory territory : territories) {
                //check the territory name && if player is the ruler
                if (territory.getName() == e.getSource().toString()
                        && ((engine.getPlayers()).get(engine.getPlayerTurn()) == territory.getRuler())) {
                    source = territory;
                }
            }
        }
        else if(!(source.equals(""))) {
            Territory[] territories = engine.getMap().getTerritories();
            for (Territory territory : territories) {
                if (territory.getName() == e.getSource().toString()) {
                    if(source.isAdjacent(territory)) { //check if destination is adjacent to the source
                        destination = territory;
                    }
                }
            }
        }
    }
}

package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;


public class FortifyPlanningState extends PlanningState implements GameState {

    public FortifyPlanningState(GameEngine engine) {
        super(engine);
    }

    //Select source and destination territories
    public void mapSelect(ActionEvent e) {
    if (destination == null && source == null) { //make sure that the first selection will be source
            Territory[] territories = engine.getMap().getTerritories();
            for (Territory territory : territories) {
                //check the territory name && if player is the ruler
                if ((territory.getName()).equals(e.getSource().toString())
                        && ((engine.getPlayers()).get(engine.getPlayerTurn()) == territory.getRuler())) {
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

package game.state;

import game.GameEngine;
import game.player.Territory;


import javafx.event.ActionEvent;

public class AttackingPlanningState implements GameState {

    private static AttackingPlanningState instance;
    private AttackingState attack;
    private GameEngine engine;
    private Territory destination;
    private Territory source;

    private AttackingPlanningState() {
        attack = AttackingState.getInstance();
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
    }

    public static AttackingPlanningState getInstance() {
        if (instance == null) {
            synchronized (AttackingPlanningState.class) {
                if (instance == null) {
                    instance = new AttackingPlanningState();
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
                    //check the territory name && if player is the ruler && has at least 2 armies
                    if ((territory.getName()).equals(e.getSource().toString())
                            && ((engine.getPlayers()).get(engine.getPlayerTurn()) == territory.getRuler())
                            &&  territory.getNumOfArmies() >= 2) {
                        attack.source = territory;
                    }
                }
            }
            else if( destination == null){
                Territory[] territories = engine.getMap().getTerritories();
                for (Territory territory : territories) {
                    if ((territory.getName()).equals(e.getSource().toString())) {
                        if(source.isAdjacent(territory)) { //check if destination is adjacent to the source
                            attack.destination = territory;
                        }
                    }
                }

            }
            else
                attack.switchState(DiceSelectionState.getInstance());
    }
}

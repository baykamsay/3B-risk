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
    public void mapSelect(int territory) {
        if (destination == null && source == null) { //make sure that the first selection will be source
            Territory t = engine.getMap().getTerritory(territory);
                //check the territory name && if player is the ruler && has at least 2 armies
                if ((t.isRuler(engine.getCurrentPlayer())) && t.getNumOfArmies() >= 2) {
                    attack.setSource(t);
                }
        }
        else if( destination == null){
            Territory t = engine.getMap().getTerritory(territory);
            if(source.isAdjacent(t)) { //check if destination is adjacent to the source
                attack.setDestination(t);
                attack.switchState(DiceSelectionState.getInstance());
            }
        }
    }

    @Override
    public void start() {
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
        attack = AttackingState.getInstance();
        engine.getController().setState(1);
        engine.isGameOver();
    }
}

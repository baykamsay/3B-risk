package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;

public class AttackingPlanningState implements GameState {

    private static AttackingPlanningState instance;
    private AttackingState attack;
    private GameEngine engine;

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
        Territory t = engine.getMap().getTerritory(territory);
        if (t.isRuler(engine.getCurrentPlayer())) { //make sure that the first selection will be source
            //check the territory name && if player is the ruler && has at least 2 armies
                if (t.getNumOfArmies() >= 2) {
                    attack.setSource(t);
                }
        }
        else {
            if(attack.getSource() != null && attack.getSource().isAdjacent(t)) { //check if destination is adjacent to the source
                attack.setDestination(t);
                attack.switchState(DiceSelectionState.getInstance());
            }
        }
    }

    @Override
    public void start() {
        engine = GameEngine.getInstance();
        attack = AttackingState.getInstance();
        attack.setSource(null);
        attack.setDestination(null);
        engine.getController().setState(1);
        engine.isGameOver();
    }
}

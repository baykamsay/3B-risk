package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;

import javafx.event.ActionEvent;


public class PlanningState implements GameState {

    public GameEngine engine;
    public int currentPlayer;
    public Territory destination;
    public Territory source;

    public PlanningState(GameEngine engine) {
        this.engine = engine;
        currentPlayer = 0;
        destination = null;
        source = null;
    }

    public Territory getDestination() {
        return destination;
    }

    public Territory getSource() {
        return source;
    }

    @Override
    public void mapSelect(ActionEvent e) {

    }
}

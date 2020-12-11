package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;

import javafx.event.ActionEvent;


public class PlanningState implements GameState {

    public MapScene mapScene;
    public Scene scene;
    public GameEngine engine;
    public GameMenuManager mgr;
    public int width, height, currentPlayer;
    public Territory destination;
    public Territory source;

    public PlanningState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
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

package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class PlanningState implements MenuState {

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
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        mapScene = new MapScene(width, height, "Attacking");
        scene = mapScene.createScene(mgr, engine, this);
        return scene;
    }
}

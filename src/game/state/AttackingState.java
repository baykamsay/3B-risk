package game.state;

import game.GameEngine;
import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

import java.util.ArrayList;

public class AttackingState implements MenuState {

    public MapScene mapScene;
    public Scene scene;
    public GameEngine engine;
    public GameMenuManager mgr;
    public int width, height, currentPlayer;
    public String destination;
    public String source;
    public int attackingArmies;
    public AttackingArmySelectionMenuState armySelectionState;
    public AttackingPlanningState planningState;

    public AttackingState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.engine = engine;
        currentPlayer = 0;
        destination = "";
        source = "";
        attackingArmies = 0;
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

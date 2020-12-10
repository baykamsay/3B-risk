package game.state;

import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class InitialArmyPlacementState implements MenuState {

    private MapScene mapScene;
    private Scene scene;
    private GameMenuManager mgr;
    private int width, height;

    public InitialArmyPlacementState(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        mapScene = new MapScene(width, height, "Initial Army Placement");
        scene = mapScene.createScene(mgr, this);
        return scene;
    }
}
package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.Event;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class InitialArmyPlacementState implements MenuState {

    private MapScene mapScene;
    private Scene scene;
    private GameEngine engine;
    private GameMenuManager mgr;
    private int width, height, currentPlayer;

    public InitialArmyPlacementState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.engine = engine;
        currentPlayer = 0;
    }

    @Override
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        mapScene = new MapScene(width, height, "Initial Army Placement");
        scene = mapScene.createScene(mgr, engine, this);
        return scene;
    }

    // When player selects a map territory
    public void mapSelect(Event e) {
        Territory[] territories = engine.getMap().getTerritories();
        Player[] players = engine.getPlayers();
        for (Territory territory : territories) {
            // getName() can also be getId() if ids are implemented and the other part requires something to compare
            // when the map is implemented fix this
            // && territory.getRuler() == null this can also be checked but if it is not null disabling the button is
            // better
            if (territory.getName() == e.getSource().toString()) {
                territory.setRuler(players[currentPlayer]);
            }
        }
        checkIfStateOver();
        currentPlayer++;
    }

    public void checkIfStateOver() {
        Territory[] territories = engine.getMap().getTerritories();
        boolean stateOver = true;
        for (Territory territory : territories) {
            if (territory.getRuler() == null) {
                stateOver = false;
            }
        }
        if (stateOver) {
            // fix
            engine.switchState(new ArmyPlacementState(width, height, engine));
        }
    }
}

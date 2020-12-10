package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import java.util.ArrayList;

public class InitialArmyPlacementState implements GameState {

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
    public Scene createScene() {
        mapScene = new MapScene(width, height, "Initial Army Placement");
        return scene;
    }

    // When player selects a map territory
    public void mapSelect(ActionEvent e) {
        Territory[] territories = engine.getMap().getTerritories();
        ArrayList<Player> players = engine.getPlayers();
        for (Territory territory : territories) {
            // getName() can also be getId() if ids are implemented and the other part requires something to compare
            // when the map is implemented fix this
            // && territory.getRuler() == null this can also be checked but if it is not null disabling the button is
            // better
            if (territory.getName() == e.getSource().toString()) {
                territory.setRuler(players.get(currentPlayer));
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

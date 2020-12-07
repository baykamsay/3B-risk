package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.Event;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

import java.util.ArrayList;

public class ArmyPlacementState implements MenuState {
    private MapScene mapScene;
    private Scene scene;
    private GameEngine engine;
    private GameMenuManager mgr;
    private int width, height, currentPlayer, addibleArmies;

    public ArmyPlacementState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.engine = engine;
        currentPlayer = 0;
        addibleArmies = 0;
    }

    @Override
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        mapScene = new MapScene(width, height, "Army Placement");
        scene = mapScene.createScene(mgr, engine, this);
        return scene;
    }

    public void territorySelect(Event e) {
        Territory[] territories = engine.getMap().getTerritories();
        ArrayList<Player> players = engine.getPlayers();
        if (e.getSource().toString() == territory.getName() && players.get(currentPlayer).isRuler(territory) && addibleArmies > 0) {
            //pop up ui will be implemented
            deployArmies(addedArmies, territory) //addedArmies will be returned by the pop ui
            addibleArmies = addibleArmies - addedArmies;
        } else if (e.getSource().toString() == "PASS") { //?????
            engine.switchState(new AttackingState(width, height, engine));
        }
    }

    public void calculateNumberOfArmies(Player p) {
        if (p.territoryList.size() <= 11) { //Arraylists of areas and territories can be implemented in a way where player holds an integer instead of a list
            addibleArmies = 3;
        } else {
            addibleArmies = p.territoryList.size() / 3;
        }
        if (p.areaList.size() > 0) {
            addibleArmies = noOfArmies + p.areaList.size(); //The increase in army number depends on which area is occupied so this is left as a default for now
        }
    }

    public void deployArmies(int addedArmies, Territory t) {
        t.setNumOfArmies(t.getNumOfArmies() + addedArmies);
    }
}

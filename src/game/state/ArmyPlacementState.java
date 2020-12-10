package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

import java.util.ArrayList;

public class ArmyPlacementState implements GameState {
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
    public Scene createScene() {
        mapScene = new MapScene(width, height, "Army Placement");
        return scene;
    }

    @Override
    public void mapSelect(ActionEvent e) {
        Territory[] territories = engine.getMap().getTerritories();
        ArrayList<Player> players = engine.getPlayers();
        if (addibleArmies == 0){
            engine.switchState(AttackingState); //??????
        }
        else {
            for (Territory territory : territories) {
                if ((e.getSource().toString() == territory.getName()) && (players.get(currentPlayer) == territory.getRuler())) {
                    //pop up ui will be implemented
                    deployArmies(addedArmies, territory); //addedArmies will be returned by the pop ui
                    addibleArmies = addibleArmies - addedArmies;
                }
            }
        }
    }

    public void calculateNumberOfArmies(Player p) {
        if (p.getNumOfTerritory() <= 11) {
            addibleArmies = 3;
        } else {
            addibleArmies = p.getNumOfTerritory() / 3;
        }
        if (p.getNumOfArea() > 0) {
            addibleArmies = addibleArmies + p.getNumOfArea(); //This part's calculation will be further discussed
        }
    }

    public void deployArmies(int addedArmies, Territory t) {
        t.setNumOfArmies(t.getNumOfArmies() + addedArmies);
    }

    public int getAddibleArmies(){
        return addibleArmies;
    }
}

package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;


public class DiceSelectionState implements GameState {
    private MapScene mapScene;
    private Scene scene;
    private GameEngine engine;
    private GameMenuManager mgr;
    private int width, height, currentPlayer, leastDiceNo;
    private Territory territory;

    public DiceSelectionState(int width, int height, GameEngine engine){
        this.width = width;
        this.height = height;
        this.engine = engine;
        territory = null;
        currentPlayer = 0;
        leastDiceNo = 0;
    }

    @Override
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene() {
        this.mgr = mgr;
        mapScene = new MapScene(width, height, "Dice Selection");
        scene = mapScene.createScene(mgr, engine, this);
        return scene;
    }

    public void mapSelect(ActionEvent e){
        calculateLeastDiceNo();
        //pass leastDiceNo && e to the pop up ui, it will return the selected number of dice
        //engine.switchState(WarState(...));
    }

    public void calculateLeastDiceNo(){
        int troopAmount = territory.getNumOfArmies();
        if (troopAmount > 3){
            leastDiceNo = 3;
        }
        else if (troopAmount == 2) {
            leastDiceNo = 2;
        }
        else
        {
            leastDiceNo = 0;
        }
    }

//    public int getLeastDiceNo(){
//        return leastDiceNo;
//    }
//
//    public int getSelectedDiceNo(){
//        return selectedDiceNo;
//    }



}

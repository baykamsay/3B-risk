package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.scene.Scene;
import menu.GameMenuManager;


public class DiceSelectionState implements GameState {
    private MapScene mapScene;
    private Scene scene;
    private GameEngine engine;
    private GameMenuManager mgr;
    private int width, height, currentPlayer, leastDiceNo, selectedDiceNo;

    public class DiceSelectionState(int width, int height, GameEngine engine){
        this.width = width;
        this.height = height;
        this.engine = engine;
        currentPlayer = 0;
        leastDiceNo = 0;
        selectedDiceNo = 0;
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

    public void init(Territory t){
        calculateLeastDiceNo(t.getNumOfArmies());
        //pass leastDiceNo to the pop up ui, it will return the selected number of dice
        engine.switchState(WarState);
    }

    public int calculateLeastDiceNo(int troopAmount){
        if (troopAmount > 3){
            leastDiceNo = 3;
            return leastDiceNo;
        }
        else if (troopAmount == 2) {
            leastDiceNo = 2;
            return leastDiceNo;
        }
        else
        {
            leastDiceNo = 0;
            return leastDiceNo;
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

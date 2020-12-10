package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class AttackingState implements GameState {

    public MapScene mapScene;
    public Scene scene;
    public GameEngine engine;
    public GameMenuManager mgr;
    public int width, height;
    public Territory destination;
    public Territory source;
    public int attackingArmies;
    public int defendingArmies;
    public DiceSelectionState diceSelectionState;
    public AttackingPlanningState AttackPlanningState;
    public WarState warState;

    public AttackingState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.engine = engine;
    }

    public void mapSelect(ActionEvent e){
        if(!(e.getSource().toString().equals("PASS"))){
            //plan attacking
            AttackPlanningState = new AttackingPlanningState(width, height, engine);
            destination = AttackPlanningState.getDestination();
            source = AttackPlanningState.getSource();
            //select armies
            diceSelectionState = new DiceSelectionState();

            //war - not complete
            warState = new WarState(width, height, engine); //pass armies as parameters?
        }
    }

    @Override
    public void update() {
        mapScene.update();
    }

    @Override
    public Scene createScene() {
        mapScene = new MapScene(width, height, "Attacking");
        return scene;
    }

}

package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.Event;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class AttackingState implements MenuState {

    public MapScene mapScene;
    public Scene scene;
    public GameEngine engine;
    public GameMenuManager mgr;
    public int width, height;
    public Territory destination;
    public Territory source;
    public int attackingArmies;
    public int defendingArmies;
    public AttackingArmySelectionMenuState armySelectionState;
    public AttackingPlanningState AttackPlanningState;
    public WarState warState;

    public AttackingState(int width, int height, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.engine = engine;
    }

    public void Attack(Event e){
        if(!(e.getSource().toString().equals("PASS"))){
            //plan attacking
            AttackPlanningState = new AttackingPlanningState(width, height, engine);
            destination = AttackPlanningState.getDestination();
            source = AttackPlanningState.getSource();
            //select armies
            armySelectionState = new ArmySelectionState(width, height, engine);
            attackingArmies = armySelectionState.getAttackingArmies();
            defendingArmies = armySelectionState.getDefendingArmies();
            //war - not complete
            warState = new WarState(width, height, engine); //pass armies as parameters?
        }
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

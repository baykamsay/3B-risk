package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class AttackingState implements GameState {

    private static AttackingState instance;
    public GameEngine engine;
    public GameState currentState;
    public Territory destination; //attackPlanning will handle it
    public Territory source; //attackPlanning will handle it

    private AttackingState() {
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
        currentState = AttackingPlanningState.getInstance();
    }

    public static AttackingState getInstance() {
        if (instance == null) {
            synchronized (AttackingState.class) {
                if (instance == null) {
                    instance = new AttackingState();
                }
            }
        }
        return instance;
    }

    public void mapSelect(ActionEvent e){
        if(!(e.getSource().toString().equals("PASS"))){
            currentState.mapSelect(e);
        }
        else
            engine.switchState(FortifyingState.getInstance());
    }

    public void switchState(GameState state){
        currentState = state;
    }

}

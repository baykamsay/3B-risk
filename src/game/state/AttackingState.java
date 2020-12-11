package game.state;

import game.GameEngine;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class AttackingState implements GameState {

    public GameEngine engine;
    public GameState currentState;

    public AttackingState(GameEngine engine) {
        this.engine = engine;
        currentState = new AttackingPlanningState(engine,this);
    }

    public void mapSelect(ActionEvent e){
        if(!(e.getSource().toString().equals("PASS"))){
            currentState.mapSelect(e);
        }
        else
            engine.switchState(new FortifyState(engine));
    }

    public void switchState(GameState state){
        currentState = state;
    }

}

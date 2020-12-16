package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

public class AttackingState implements GameState {

    private static AttackingState instance;
    private GameEngine engine;
    private GameState currentState;
    private Territory destination, source; //attackPlanning will handle it
    private int attackingArmies, defendingArmies; //diceSelection w.h.i

    private AttackingState() {
        engine = GameEngine.getInstance();
        start();
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
        currentState.mapSelect(e);
    }

    @Override
    public void start() {
        destination = null;
        source = null;
        currentState = AttackingPlanningState.getInstance();
        attackingArmies = 0;
        defendingArmies = 0;
    }

    public void pass(ActionEvent e) {
        engine.switchState(FortifyingState.getInstance());
    }

    public void switchState(GameState state){
        currentState = state;
        currentState.start();
    }


    public Territory getDestination() {
        return destination;
    }

    public void setDestination(Territory destination) {
        this.destination = destination;
    }

    public Territory getSource() {
        return source;
    }

    public void setSource(Territory source) {
        this.source = source;
    }

    public int getAttackingArmies() {
        return attackingArmies;
    }

    public void setAttackingArmies(int attackingArmies) {
        this.attackingArmies = attackingArmies;
    }

    public int getDefendingArmies() {
        return defendingArmies;
    }

    public void setDefendingArmies(int defendingArmies) {
        this.defendingArmies = defendingArmies;
    }
}

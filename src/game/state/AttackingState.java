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

    public void mapSelect(int territory){
        currentState.mapSelect(territory);
    }

    @Override
    public void start() {
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
        currentState = AttackingPlanningState.getInstance();
        currentState.start();
        attackingArmies = 0;
        defendingArmies = 0;
        engine.getController().setState(1);
    }

    public void pass() {
        engine.switchState(FortifyingState.getInstance());
    }

    public void switchState(GameState state){
        System.out.println(state);
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

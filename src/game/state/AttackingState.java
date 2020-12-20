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
    private boolean econAbilityCanUse, artAbilityCanUse;

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
        econAbilityCanUse = false;
        artAbilityCanUse = false;
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
        currentState = state;
        currentState.start();
    }

    public void setDiceNo(int diceNo){
        if(currentState instanceof DiceSelectionState){
            ((DiceSelectionState) currentState).setDiceNo(diceNo);
        }else if(currentState instanceof WarState){
            ((WarState) currentState).moveArmies(diceNo);
        }
    }

    public void back(){
        if(currentState instanceof DiceSelectionState) {
            switchState(AttackingPlanningState.getInstance());
        }
    }

    public GameState getCurrentState() { return currentState; }

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

    public void setEconAbilityCanUse(boolean b){ econAbilityCanUse = b; }

    public boolean getEconAbilityCanUse() { return econAbilityCanUse;}

    public void setArtAbilityCanUse(boolean b){ artAbilityCanUse = b; }

    public boolean getArtAbilityCanUse() { return artAbilityCanUse;}
}

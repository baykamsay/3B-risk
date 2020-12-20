package game.state;

import game.GameEngine;
import game.player.Territory;
import game.player.faculties.Mssf;
import javafx.event.ActionEvent;

public class FortifyingState implements GameState {
    private static FortifyingState instance;

    public GameEngine engine;
    public GameState currentState;
    public Territory destination; //fortifyPlanning will handle it
    public Territory source; //fortifyPlanning will handle it
    public int movingArmies; //fortifyingArmySelection will handle it
    public boolean mssfAbilityCanUse;

    private FortifyingState() {
    }

    public static FortifyingState getInstance() {
        if (instance == null) {
            synchronized (FortifyingState.class) {
                if (instance == null) {
                    instance = new FortifyingState();
                }
            }
        }
        return instance;
    }

    public void mapSelect(int territory){
        currentState.mapSelect(territory);
    }

    public void pass() {
        if (mssfAbilityCanUse) {
            engine.switchState(ArmyPlacementState.getInstance());
        }
        else {
            engine.getCurrentPlayer().getFaculty().setCanUse(true);
            engine.nextPlayer();
        }
    }

    @Override
    public void start() {
        mssfAbilityCanUse = false;
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
        currentState = FortifyingPlanningState.getInstance();
        currentState.start();
        movingArmies = 0;
        engine.getController().setState(2);
    }

    public Territory getSource(){
        return source;
    }

    public Territory getDestination(){
        return destination;
    }

    public void switchState(GameState state){
        currentState = state;
        currentState.start();
    }

    public void back(){
        if(currentState instanceof FortifyingArmySelectionState){
            switchState(FortifyingPlanningState.getInstance());
        }
    }

    public void setNumOfArmies(int armies){
        if(currentState instanceof FortifyingArmySelectionState){
            ((FortifyingArmySelectionState) currentState).setNumOfArmies(armies);
        }
    }

    public void setDestination(Territory destination) {
        this.destination = destination;
    }

    public void setSource(Territory source) {
        this.source = source;
    }

    public int getMovingArmies() {
        return movingArmies;
    }

    public void setMovingArmies(int movingArmies) {
        this.movingArmies = movingArmies;
    }

    public void setMssfAbilityCanUseTrue(){
        mssfAbilityCanUse = true;
    }

}

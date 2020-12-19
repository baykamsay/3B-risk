package game.state;

import game.GameEngine;
import game.player.Territory;
import javafx.event.ActionEvent;

public class FortifyingState implements GameState {
    private static FortifyingState instance;

    public GameEngine engine;
    public GameState currentState;
    public Territory destination; //fortifyPlanning will handle it
    public Territory source; //fortifyPlanning will handle it
    public int movingArmies; //fortifyingArmySelection will handle it

    private FortifyingState() {
        start();
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
        engine.nextPlayer();
//        engine.switchState(ArmyPlacementState.getInstance());
    }

    @Override
    public void start() {
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

}

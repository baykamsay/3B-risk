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
        engine = GameEngine.getInstance();
        destination = null;
        source = null;
        currentState = FortifyPlanningState.getInstance();
        movingArmies = 0;
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

    public void mapSelect(ActionEvent e){
        if(!(e.getSource().toString().equals("PASS"))){
            currentState.mapSelect(e);
        }
    }

    public void switchState(GameState state){
        currentState = state;
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

    public int getMovingArmies() {
        return movingArmies;
    }

    public void setMovingArmies(int movingArmies) {
        this.movingArmies = movingArmies;
    }
}

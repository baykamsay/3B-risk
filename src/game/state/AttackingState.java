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
        destination = null;
        source = null;
        currentState = AttackingPlanningState.getInstance();
        attackingArmies = 0;
        defendingArmies = 0;
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
        else if(!engine.isGameOver()) //gameOver check--if it is over, the winner is written into the engine
            engine.switchState(FortifyingState.getInstance());
    }

    public void switchState(GameState state){
        currentState = state;
        //call the war method if current state is war
        if(currentState instanceof WarState){
            ((WarState) currentState).war();
            //elimination check after a war
            for (Player p: engine.getPlayers()) {
                if(engine.isEliminated(p)){
                    engine.removePlayer(p);
                }
            }
        }
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

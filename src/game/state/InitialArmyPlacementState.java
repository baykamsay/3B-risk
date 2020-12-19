package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import javafx.event.ActionEvent;
import java.util.ArrayList;

public class InitialArmyPlacementState implements GameState {

    private static InitialArmyPlacementState instance;
    private GameEngine engine;
    private int currentPlayer;
    private ArrayList<Integer> armyCounts;

    private InitialArmyPlacementState() {
        engine = GameEngine.getInstance();
        armyCounts = new ArrayList<Integer>();
    }

    public static InitialArmyPlacementState getInstance() {
        if (instance == null) {
            synchronized (InitialArmyPlacementState.class) {
                if (instance == null) {
                    instance = new InitialArmyPlacementState();
                }
            }
        }
        return instance;
    }

    // When player selects a map territory
    public void mapSelect(int territory) {
        engine.mapScene.getController().setState(0);
        Territory t = engine.getMap().getTerritory(territory);
        Player p = engine.getPlayers().get(currentPlayer);
        if(!territoryCheck()) {
            //if there are territories left, keep selecting
            if (t.getRuler() == null) {
                t.setRuler(p);
                t.setNumOfArmies(t.getNumOfArmies() + 1);
                armyCounts.set(currentPlayer,armyCounts.get(currentPlayer) - 1); //-1 from total army counts
                currentPlayer = (currentPlayer + 1) % engine.getPlayers().size();
                p.setNumOfTerritory(p.getNumOfTerritory() + 1);
            }
        }
        else {
            if (t.isRuler(p)){
                t.setNumOfArmies(t.getNumOfArmies() + 1);
                armyCounts.set(currentPlayer,armyCounts.get(currentPlayer) - 1); //-1 from total army counts
                currentPlayer = (currentPlayer + 1) % engine.getPlayers().size();
            }
        }
        checkIfStateOver();
        engine.incrementCurrentPlayer();
    }

    //initialize army counts for players
    public void calculateArmyCounts(){
        if(engine.getPlayers().size() == 2){
            armyCounts.add(40);
            armyCounts.add(40);
        }
        else if(engine.getPlayers().size() == 3){
            for(int i = 0; i < 3; i++){
                armyCounts.add(35);
            }
        }
        else if(engine.getPlayers().size() == 4){
            for(int i = 0; i < 4; i++){
                armyCounts.add(30);
            }
        }
        else if(engine.getPlayers().size() == 5){
            for(int i = 0; i < 5; i++){
                armyCounts.add(25);
            }
        }
        else if(engine.getPlayers().size() == 6){
            for(int i = 0; i < 6; i++){
                armyCounts.add(20);
            }
        }
    }

    @Override
    public void start() {
        currentPlayer = 0;
        calculateArmyCounts();
    }

    //check if all of the territories are empty
    public boolean territoryCheck(){
        Territory[] territories = engine.getMap().getTerritories();
        boolean territoryOver = true;
        for (Territory territory : territories) {
            if (territory.getRuler() == null) {
                territoryOver = false;
            }
        }
        return territoryOver;
    }


    // Is the state really over when no territory left empty?
    public void checkIfStateOver() {
        boolean stateOver = true;
        for (int i: armyCounts) {
            if (i != 0) {
                stateOver = false;
            }
        }
        if (stateOver) {
            engine.switchState(ArmyPlacementState.getInstance());
        }
    }
}

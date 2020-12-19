package game.state;

import game.GameEngine;
import game.player.Objective;
import game.player.Player;
import game.player.Territory;
import java.util.ArrayList;

public class InitialArmyPlacementState implements GameState {

    private static InitialArmyPlacementState instance;
    private int currentPlayer;
    private ArrayList<Integer> armyCounts;

    private InitialArmyPlacementState() {
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
        GameEngine.getInstance().getController().setState(0);
        Territory t = GameEngine.getInstance().getMap().getTerritory(territory);
        Player p = GameEngine.getInstance().getCurrentPlayer();
        if(!territoryCheck()) {
            //if there are territories left, keep selecting
            if (t.getRuler() == null) {
                t.setRuler(p);
                t.setNumOfArmies(t.getNumOfArmies() + 1);
                armyCounts.set(currentPlayer,armyCounts.get(currentPlayer) - 1); //-1 from total army counts
                currentPlayer = (currentPlayer + 1) % GameEngine.getInstance().getPlayers().size();
                p.setNumOfTerritory(p.getNumOfTerritory() + 1);
                GameEngine.getInstance().incrementCurrentPlayer();
            }
        }
        else {
            if (t.isRuler(p)){
                t.setNumOfArmies(t.getNumOfArmies() + 1);
                armyCounts.set(currentPlayer,armyCounts.get(currentPlayer) - 1); //-1 from total army counts
                currentPlayer = (currentPlayer + 1) % GameEngine.getInstance().getPlayers().size();
                GameEngine.getInstance().incrementCurrentPlayer();
            }
        }
        checkIfStateOver();
    }

    //initialize army counts for players
    public void calculateArmyCounts(){
        if(GameEngine.getInstance().getPlayers().size() == 2){
            armyCounts.add(25);
            armyCounts.add(25);
        }
        else if(GameEngine.getInstance().getPlayers().size() == 3){
            for(int i = 0; i < 3; i++){
                armyCounts.add(35);
            }
        }
        else if(GameEngine.getInstance().getPlayers().size() == 4){
            for(int i = 0; i < 4; i++){
                armyCounts.add(30);
            }
        }
        else if(GameEngine.getInstance().getPlayers().size() == 5){
            for(int i = 0; i < 5; i++){
                armyCounts.add(25);
            }
        }
        else if(GameEngine.getInstance().getPlayers().size() == 6){
            for(int i = 0; i < 6; i++){
                armyCounts.add(20);
            }
        }
    }

    @Override
    public void start() {
        armyCounts = new ArrayList<Integer>();
        currentPlayer = 0;
        calculateArmyCounts();
    }

    //check if all of the territories are empty
    public boolean territoryCheck(){
        Territory[] territories = GameEngine.getInstance().getMap().getTerritories();
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
            for(Player p : GameEngine.getInstance().getPlayers()) {
                p.setObjective(Objective.generateObjective(p));
            }
            GameEngine.getInstance().switchState(ArmyPlacementState.getInstance());
        }
    }
}

package gameEngine;
import java.io.*;
import java.util.*;

//GameState, GameMap, Objective, Player must be imported

public class GameEngine {
    public GameState currentState;
    public GameMap map;
    public Objective[] objectives;
    public Player[] players;
    public int[] playerSequence;
    public int saveSlot;
    public int turn;

    public GameEngine(int saveSlot){
        this.saveSlot = saveSlot;
    }

    public GameEngine(int saveSlot, Player[] players){
        this.saveSlot = saveSlot;
        this.players = players;
        isOver = false;
        map = new GameMap();
        turn = 0;
        currentState = NULL;
    }

    public void switchState( GameState currentState){
        this.currentState = currentState;
    }

    //map should have a getTerritories method to return the territories array
    public boolean isGameOver(){
        for (int i = 0; i < map.getTerritories(); i++) {
            if( i+1 < (map.getTerritories()).length && (map.getTerritories())[i] != (map.getTerritories())[i+1]){
                //map is occupied by at least 2 players
                return false;
            }
        }
        //map is occupied by 1 player
        return true;
    }

    public void removeObjective(String name){
        int index = 0;
        while(objectives[index].getName() != name){
            index++;
        }

    }
}

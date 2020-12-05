package gameEngine;
import java.io.*;
import java.util.*;

//GameState, GameMap, Objective, Player must be imported

public class GameEngine {
    public GameState currentState;
    public boolean isOver;
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

    public boolean gameOver(){
        return isOver;
    }

    public void removeObjective(String name){
        int index = 0;
        while(objectives[index].getName() != name){
            index++;
        }

    }
}

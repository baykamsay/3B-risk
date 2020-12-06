package game;
import java.io.*;
import java.util.*;

import game.player.Objective;
import game.player.Player;
import game.state.*;
import menu.*;


//GameMap must be imported

public class GameEngine {
    public MenuState currentState;
    public GameMap map;
    public Objective[] objectives;
    public Player[] players;
    public int playerTurn; //keeps the index of the players array to decide who's gonna play
    public int saveSlot;
    public int turn; //total turn count of the game
    public Player winner;
    public int width;
    public int height;

    public GameEngine(int saveSlot,int height, int width){
        //variables will be initialized according to the save file(file parameter?)
        this.saveSlot = saveSlot;
        this.height = height;
        this.width = width;
    }

    public GameEngine(int saveSlot, Player[] players,int height,int width){
        this.saveSlot = saveSlot;
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            //"=" operator for the player class should be overriden
            this.players[i] = players[i];
        }
        map = new GameMap();
        turn = 0;
        currentState = null;
        playerTurn = 0; //first player will go first, which is stored in index 0
        winner = null;
        this.height = height;
        this.width = width;
    }

    public GameMap getMap() {
        return map;
    }

    public Player[] getPlayers(){
        return players;
    }

    public void switchState(MenuState currentState){
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
        winner = (map.getTerritories[0]).getRuler();
        return true;
    }

    public Player getWinner(){
        return winner;
    }

    public void removeObjective(String name){
        int index = 0;
        Objective[] temp = new Objective[objectives.length - 1];
        for (int i = 0; i < objectives.length; i++) {
            if(objectives[i].getName() != name){
                //"=" operator for objective class should be overriden
                temp[index++] = objectives[i];
            }
        }
        objectives = temp;
    }

    public boolean isEliminated(Player p){ //check if a player is eliminated
        for (int i = 0; i < map.getTerritories(); i++) {
            if( (((map.getTerritories())[i]).getRuler()).getName() == p.getName()){
                //player has at least 1 territory
                return false;
            }
        }
        return true; //player has 0 territory
    }

    public void removePlayer(Player p){ //remove an eliminated player from player[]
        int index = 0;
        Player[] temp = new Player[players.length - 1];
        for (int i = 0; i < players.length; i++) {
            if(players[i].getName() != p.getName()){
                //"=" operator for player class should be overriden
                temp[index++] = players[i];
            }
        }
        players = temp;
    }

    public void initGame(){
        if(turn == 0){
            switchState(new InitialArmyPlacementState(width,height,this));
            //call armyPlacement methods
        }
    }
}

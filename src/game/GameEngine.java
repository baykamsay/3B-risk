package game;

import game.player.Objective;
import game.player.Player;
import javafx.scene.Scene;
import menu.*;
import java.util.ArrayList;


//GameMap must be imported

public class GameEngine implements MenuState{
    public MenuState currentState;
    public GameMap map;
    public ArrayList<Objective> objectives;
    public ArrayList<Player> players;
    public int playerTurn; //keeps the index of the players array to decide who's gonna play
    public int saveSlot;
    public int turn; //total turn count of the game
    public Player winner;
    public int width;
    public int height;

    public GameEngine(int saveSlot, int height, int width){
        //variables will be initialized according to the save file(file parameter?)
        this.saveSlot = saveSlot;
        this.height = height;
        this.width = width;
    }

    public GameEngine(int saveSlot, ArrayList<Player> players, int height, int width){
        this.saveSlot = saveSlot;
        this.players = new ArrayList<Player>();
        this.objectives = new ArrayList<Objective>();
        for (int i = 0; i < players.size(); i++) {
            //"=" operator for the player class should be overriden
            (this.players).add(players.get(i));
        }
        map = new GameMap();
        turn = 0;
        currentState = null;
        playerTurn = 0; //first player will go first, which is stored in index 0
        winner = null;
        this.height = height;
        this.width = width;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public GameMap getMap() {
        return map;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void switchState(MenuState currentState){
        this.currentState = currentState;
    }

    //map should have a getTerritories method to return the territories array
    public boolean isGameOver(){
        for (int i = 0; i < map.getTerritories().length; i++) {
            if( i+1 < (map.getTerritories()).length && (map.getTerritories())[i].getRuler() != (map.getTerritories())[i+1].getRuler()){
                //map is occupied by at least 2 players
                return false;
            }
        }
        //map is occupied by 1 player
        winner = (map.getTerritories())[0].getRuler();
        return true;
    }

    public Player getWinner(){
        return winner;
    }

    public void removeObjective(String name){
        int index = 0;
        for (int i = 0; i < objectives.size(); i++) {
            if(((objectives.get(i)).getName()).equals(name)){ //may be description?
                index = i;
                break;
            }
        }
        objectives.remove(index);
    }

    public boolean isEliminated(Player p){ //check if a player is eliminated
        for (int i = 0; i < map.getTerritories().length; i++) {
            if( (((map.getTerritories())[i]).getRuler()).getFaculty() == p.getFaculty()){
                //player has at least 1 territory
                return false;
            }
        }
        return true; //player has 0 territory
    }

    public void removePlayer(Player p){ //remove an eliminated player from player<>
        int index = 0;
        for (int i = 0; i < players.size(); i++) {
            if((players.get(i)).getFaculty() == p.getFaculty()){
                index = i;
                break;
            }
        }
        players.remove(index);
    }

    @Override
    public void update() {
        //fill
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        //fill
        return null;
    }
}

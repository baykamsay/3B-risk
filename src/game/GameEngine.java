package game;

import game.player.Objective;
import game.player.Player;
import game.scene.MapScene;
import game.scene.MapSceneController;
import game.state.GameState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.GameMenuManager;
import menu.Launcher;
import menu.PauseMenu;

import java.util.ArrayList;

public class GameEngine extends Application {

    private static GameEngine instance;
    public GameState currentState;
    public GameMap map;
    public MapScene mapScene;
    private MapSceneController controller;
    private SoundEngine soundEngine;
    public ArrayList<Objective> objectives;
    public ArrayList<Player> players;
    private Launcher launcher;

    public int playerTurn; //keeps the index of the players array to decide who's gonna play
    public int saveSlot;
    public int turn; //total turn count of the game

    public Player winner;
    public int width;
    public int height;

    private Scene gameScene;
    private Stage window;

    private GameEngine(int saveSlot, int width, int height, Launcher launcher){
        super();
        //variables will be initialized according to the save file(file parameter?)
        this.saveSlot = saveSlot;
        this.height = height;
        this.width = width;
        this.soundEngine = SoundEngine.getInstance();
        this.launcher = launcher;
    }

    private GameEngine(int saveSlot,  int width, int height, ArrayList<Player> players, Launcher launcher){
        super();
        this.height = height;
        this.width = width;
        this.saveSlot = saveSlot;
        this.players = new ArrayList<Player>();
        this.objectives = new ArrayList<Objective>();
        this.soundEngine = SoundEngine.getInstance();
        this.launcher = launcher;
        for (int i = 0; i < players.size(); i++) {
            //"=" operator for the player class should be overriden
            (this.players).add(players.get(i));
        }
        turn = 0;
        currentState = null;
        playerTurn = 0; //first player will go first, which is stored in index 0
        winner = null;
    }

    public static GameEngine init(int saveSlot, int width, int height, Launcher launcher) {
        if (instance == null) {
            synchronized (GameEngine.class) {
                if (instance == null) {
                    instance = new GameEngine(saveSlot, width, height, launcher);
                }
            }
        }
        return instance;
    }

    public static GameEngine init(int saveSlot,  int width, int height, ArrayList<Player> players, Launcher launcher) {
        if (instance == null) {
            // yey?
            synchronized (GameEngine.class) {
                if (instance == null) {
                    instance = new GameEngine(saveSlot, width, height, players, launcher);
                }
            }
        }
        return instance;
    }

    // first call init before using getInstance()
    public static GameEngine getInstance() {
        if (instance == null) throw new UnsupportedOperationException(
                    "GameEngine needs to be initialized first"
            );
        return instance;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int turnNo) {
        turn = turnNo;
    }

    public GameMap getMap() {
        return map;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void switchState(GameState currentState){
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
    public void start(Stage stage) throws Exception {
        this.window = stage;
        window.setResizable(false);
        window.setTitle("RISK 101");
        window.getIcons().add(new Image("img\\logo.png"));
        window.initStyle(StageStyle.UNDECORATED);
        window.setMaximized(true);
        this.setupMapScene();
        window.setScene(gameScene);
        window.show();
    }

    public void setupMapScene(){
        map = GameMap.getInstance();
        mapScene = new MapScene(width, height);
        this.gameScene = mapScene.createScene();
        controller = mapScene.getController();
        controller.setPlayers(players);
        this.gameScene.getStylesheets().add("css/menu_stylesheet.css");
        controller.setPlayers(players);
        controller.setMap(map);
        controller.setGameEngine(this);
        controller.addHandlers();
        controller.test();
    }

    public void pause(){
        PauseMenu pause = new PauseMenu(width,height,this);
        Scene pauseScene = pause.createScene();
        pauseScene.getStylesheets().add("css/menu_stylesheet.css");
        window.setScene(pauseScene);
    }

    public void unpause(){
        window.setScene(gameScene);
    }

    public void setScene(Scene scene){
        scene.getStylesheets().add("css/menu_stylesheet.css");
        window.setScene(scene);
    }

    public void backToMainMenu(){
        // Save the file here
        window.close();
        GameMenuManager mgr = new GameMenuManager(launcher);
        mgr.start(window);
    }

    public Player getCurrentPlayer() {
        return players.get(playerTurn);
    }
    
}

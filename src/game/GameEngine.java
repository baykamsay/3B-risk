package game;

import game.player.Objective;
import game.player.Player;
import game.player.faculties.Fen;
import game.scene.GameOverScene;
import game.scene.MapScene;
import game.scene.MapSceneController;
import game.state.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menu.GameMenuManager;
import menu.Launcher;
import menu.PauseMenu;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private int attackerDice;
    private int defenderDice;

    public int playerTurn; //keeps the index of the players array to decide who's gonna play
    public int saveSlot;
    public int turn; //total turn count of the game

    public Player winner;
    public int width;
    public int height;
    private boolean load;

    private Scene gameScene;
    private Stage window;

    private GameEngine(int saveSlot, int width, int height, Launcher launcher){
        super();
        load = true;
        SaveManager.getInstance().loadGame(saveSlot);
        this.saveSlot = saveSlot;
        this.height = height;
        this.width = width;
        this.soundEngine = SoundEngine.getInstance();
        this.launcher = launcher;
    }

    private GameEngine(int saveSlot,  int width, int height, ArrayList<Player> players, Launcher launcher){
        super();
        load = false;
        this.height = height;
        this.width = width;
        this.saveSlot = saveSlot;
        this.players = players;
        this.objectives = new ArrayList<Objective>();
        this.soundEngine = SoundEngine.getInstance();
        this.launcher = launcher;
        turn = 0;
        currentState = null;
        playerTurn = 0; //first player will go first, which is stored in index 0
        winner = null;
        attackerDice = 0;
        defenderDice = 0;
    }

    public void incrementCurrentPlayer(){
         playerTurn = (playerTurn + 1) % players.size();
    }

    public void mapSelect(int territory){
        currentState.mapSelect(territory);
    }

    // Calls set methods for fortifyingArmySelection and DiceSelection
    public void setArmyCount(int count){
        if(currentState instanceof FortifyingState){
            ((FortifyingState) currentState).setNumOfArmies(count);
        }
        else if(currentState instanceof AttackingState){
            ((AttackingState) currentState).setDiceNo(count);
        }
        else if(currentState instanceof ArmyPlacementState){
            ((ArmyPlacementState) currentState).setArmyCount(count);
        }
    }

    public void pass() {
        if (currentState instanceof AttackingState) {
            ((AttackingState) currentState).pass();
        } else if (currentState instanceof FortifyingState) {
            ((FortifyingState) currentState).pass();
        }
    }

    // Load Game init
    public static GameEngine init(int saveSlot, int width, int height, Launcher launcher) {
        synchronized (GameEngine.class) {
            instance = new GameEngine(saveSlot, width, height, launcher);
        }
        return instance;
    }

    // New Game init
    public static GameEngine init(int saveSlot,  int width, int height, ArrayList<Player> players, Launcher launcher) {
        synchronized (GameEngine.class) {
            instance = new GameEngine(saveSlot, width, height, players, launcher);
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

    public int getTurn(){return turn;}

    public void nextPlayer(){
        if(playerTurn == players.size() - 1) { //last player has played
            playerTurn = 0; //reset the player turn
            turn++; //increment the total turn count
        }
        else
            playerTurn++; //increment the current player turn
        instance.switchState(ArmyPlacementState.getInstance());
        controller.setTurn(playerTurn);
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

    public void switchState(GameState currentState){
        this.currentState = currentState;
        this.currentState.start();
    }

    //map should have a getTerritories method to return the territories array
    public void isGameOver(){
        for (int i = 0; i < map.getTerritories().length; i++) {
            if( i+1 < (map.getTerritories()).length && (map.getTerritories())[i].getRuler() != (map.getTerritories())[i+1].getRuler()){
                //map is occupied by at least 2 players
                return;
            }
        }
        //map is occupied by 1 player
        winner = (map.getTerritories())[0].getRuler();
        displayGameOverScreen(winner);
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
        window.getIcons().add(new Image(Launcher.class.getResource("/img/logo.png").toURI().toString()));
        this.setupMapScene();
        if(turn == 0) {
            GameEngine.getInstance().switchState(InitialArmyPlacementState.getInstance());
        }
        window.setScene(gameScene);
        window.show();
    }

    public void setupMapScene(){
        map = GameMap.getInstance();
        map.init();
        mapScene = new MapScene(width, height);
        try {
            mapScene.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameScene = mapScene.createScene();
        controller = mapScene.getController();
        controller.setMap(map);
        controller.setGameEngine(this);
        controller.setPlayers(players);
        try {
            this.gameScene.getStylesheets().add(getClass().getResource("/css/main_stylesheet.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(currentState instanceof InitialArmyPlacementState || currentState instanceof ArmyPlacementState){
            controller.setState(0);
        } else if( currentState instanceof AttackingState){
            controller.setState(1);
        } else {
            controller.setState(2);
        }
        controller.update();
    }

    public void pause(){
        soundEngine.playButtonSound();
        PauseMenu pause = new PauseMenu(width,height,this);
        Scene pauseScene = pause.createScene();
        try {
            pauseScene.getStylesheets().add(Launcher.class.getResource("/css/main_stylesheet.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        window.setScene(pauseScene);
    }

    public void unpause(){
        soundEngine.playButtonSound();
        window.setScene(gameScene);
    }

    public void setScene(Scene scene){
        try {
            scene.getStylesheets().add(getClass().getResource("/css/main_stylesheet.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        window.setScene(scene);
    }

    public void backToMainMenu(){
        soundEngine.playButtonSound();
        // Save the file here
        window.close();
        GameMenuManager mgr = new GameMenuManager(launcher);
        mgr.start(window);
    }

    public void gameEnd(){
        soundEngine.playButtonSound();
        window.close();
        GameMenuManager mgr = new GameMenuManager(launcher);
        mgr.start(window);
    }

    public Player getCurrentPlayer() {
        return players.get(playerTurn);
    }

    public void displayGameOverScreen(Player winner){
        this.winner = winner;
        GameOverScene gameOverScene = new GameOverScene(width,height);
        try {
            gameOverScene.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setScene(gameOverScene.createScene());
        soundEngine.stopMusic();
        soundEngine.playWinSound();
    }

    public void test(){
        displayGameOverScreen(new Player(new Fen()));
    }

    public MapSceneController getController(){
        return controller;
    }

    public void back(){
        if(currentState instanceof AttackingState){
            ((AttackingState) currentState).back();
        }else if(currentState instanceof FortifyingState){
            ((FortifyingState) currentState).back();
        }
    }

    public int getSaveSlot(){ return saveSlot; }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}

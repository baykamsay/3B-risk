package menu;

import game.GameEngine;
import game.SoundEngine;
import game.player.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class GameMenuManager extends Application implements EventHandler<ActionEvent> {

    private Launcher launcher;

    private boolean isMaximized;
    private SoundEngine soundEngine;
    private static final int WINDOWED_WIDTH = 1024;
    private static final int WINDOWED_HEIGHT = 1024;
    private int width, height;
    private Scene scene;
    private MenuState menuState;
    private Stage window;
    boolean displayChanged;

    public GameMenuManager(Launcher launcher){
        this.launcher = launcher;
        this.soundEngine = SoundEngine.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        width = WINDOWED_WIDTH;
        height = WINDOWED_HEIGHT;
        isMaximized = false;
        displayChanged = true;
        soundEngine.changeToMenuMusic();
        try {
            checkForDir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        window = primaryStage;
        window.setResizable(false);
        window.setTitle("RISK 101");
        try {
            window.getIcons().add(new Image(Launcher.class.getResource("/img/logo.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        window.setOnCloseRequest(e -> {
            e.consume();
            exit();
        });

        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        String s = "";
        try{
            s = ((Button) e.getSource()).getText();
            playButtonSound();
        } catch (Exception exception) {
            System.out.println("Not a button.");
        }

        switch (s) {
            case "Help":
                viewHelp();
                break;
            case "Back":
                back();
                break;
            case "Exit":
                exit();
                break;
            case "Credits":
                viewCredits();
                break;
            case "Load Game":
                try {
                    loadGame();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "New Game":
                try {
                    newGame();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "Settings":
                viewSettings();
                break;
            case "Return to Main Menu":
                back();
                break;
        }
    }

    public void changeScene(Scene scene){
        try {
            scene.getStylesheets().add(Launcher.class.getResource("/css/menu_stylesheet.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        window.setScene(scene);
        this.changeMaximized();
        window.show();
    }

    public void loadGame() throws Exception {
        menuState = new LoadGameMenu(width,height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void newGame() throws Exception {
        menuState = new NewGameMenu(width,height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void viewCredits(){
        menuState = new CreditsMenu(width,height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void viewHelp(){
        menuState = new HelpMenu(width,height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void viewSettings(){
        menuState = new SettingsMenu(width,height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void facultySelection(int saveSlot){
        menuState = new FacultySelectionMenu(width, height, saveSlot);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void back(){
        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void startGameEngine(int saveSlot, ArrayList<Player> players) throws Exception {
        this.close();
        soundEngine.changeToGameMusic();
        GameEngine game = GameEngine.init(saveSlot, width,height, players, launcher);
        game.start(window);
    }

    public void exit(){
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Exit RISK101?", ButtonType.YES, ButtonType.NO);
        exit.setTitle("");
        exit.setHeaderText("Exit");
        exit.setContentText("Are you sure you want to exit RISK101?");
        exit.showAndWait();
        if(exit.getResult() == ButtonType.YES){
            window.close();
            Platform.exit();
        }
    }

    public boolean forceMaximized(){
        if(!isMaximized) {
            Alert maximizeAlert = new Alert(Alert.AlertType.CONFIRMATION, "Set Maximized?", ButtonType.YES, ButtonType.NO);
            maximizeAlert.setTitle("");
            maximizeAlert.setHeaderText("Maximize?");
            maximizeAlert.setContentText("RISK101 can only be played in the maximized display mode, set to maximized?");
            maximizeAlert.showAndWait();
            if (maximizeAlert.getResult() == ButtonType.YES) {
                this.setMaximized(true);
                this.changeMaximized();
                return true;
            }
            return false;
        }
        else{
            return true;
        }
    }

    public void checkForDir() throws Exception {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\RISK101");
        if(!file.exists()){
            boolean success = file.mkdir();
            if(!success){
                throw new Exception("Cannot create directory for save files.");
            }
        }
    }

    public void changeMaximized(){
        if (isMaximized) {
            if( displayChanged) {
                window.close();
                window = new Stage();
                window.setResizable(false);
                window.setTitle("RISK 101");
                try {
                    window.getIcons().add(new Image(Launcher.class.getResource("/img/logo.png").toURI().toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                window.initStyle(StageStyle.UNDECORATED);
                window.setScene(this.scene);
            }
        } else {
            if( displayChanged) {
                window.close();
                window = new Stage();
                window.setResizable(false);
                window.setTitle("RISK 101");
                try {
                    window.getIcons().add(new Image(Launcher.class.getResource("/img/logo.png").toURI().toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                window.initStyle(StageStyle.DECORATED);
                window.setScene(this.scene);
            }
        }
        displayChanged = false;
        window.setMinHeight(height);
        window.setMinWidth(width);
        this.window.setMaximized(isMaximized);
    }

    public boolean getMaximized(){
        return isMaximized;
    }

    public boolean getMusicMuted(){
        return soundEngine.isMusicMuted();
    }

    public boolean getSoundFXMuted(){ return soundEngine.isSoundFXMuted(); }

    public double getMusicValue(){
        return soundEngine.getMusicVolume();
    }

    public double getSoundFXValue(){
        return soundEngine.getSoundFXVolume();
    }

    public void setMaximized(boolean maximized){
        this.isMaximized = maximized;
        this.displayChanged = true;
        if(maximized){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        } else {
            width = WINDOWED_WIDTH;
            height = WINDOWED_HEIGHT;
        }
    }

    public void setMusicMuted(boolean mute){
        soundEngine.setMusicMuted(mute);
    }

    public void setSoundFXMuted(boolean mute){
        soundEngine.setSoundFXMuted(mute);
    }

    public void setSoundFXValue(double value){
        soundEngine.setSoundFXVolume(value);
    }

    public void setMusicValue(double value){
        soundEngine.setMusicVolume(value);
    }

    public void playButtonSound(){
        soundEngine.playButtonSound();
    }

    public void close(){
        window.close();
    }
}

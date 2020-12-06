package menu;

import game.state.InitialArmyPlacementState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;


public class GameMenuManager extends Application implements EventHandler<ActionEvent> {

    private boolean isMaximized;
    // private SoundEngine soundEngine;
    private static final int WINDOWED_WIDTH = 1024;
    private static final int WINDOWED_HEIGHT = 1024;
    private int width, height;
    private Scene scene;
    private MenuState menuState;
    private Stage window;
    boolean displayChanged;

    // move these to sound engine when implemented
    private boolean musicMuted, soundFXMuted;
    private double musicValue, soundFXValue;
    private MediaPlayer soundEngine;
    private AudioClip buttonSound;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        width = WINDOWED_WIDTH;
        height = WINDOWED_HEIGHT;
        isMaximized = false;
        musicMuted = false;
        soundFXMuted = false;
        displayChanged = false;
        musicValue = 15.0;
        soundFXValue = 100.0;

        soundEngine = new MediaPlayer(new Media(new File("src\\sound\\menu_music.mp3").toURI().toString()));
        soundEngine.setMute(musicMuted);
        soundEngine.setVolume(musicValue / 100.0);
        soundEngine.setOnEndOfMedia(() -> {
            soundEngine.seek(Duration.ZERO);
            soundEngine.play();
        });
        soundEngine.play();

        buttonSound = new AudioClip(new File("src\\sound\\button_click.wav").toURI().toString());
        buttonSound.setVolume(soundFXValue / 100.0);

        try {
            checkForDir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        window = primaryStage;
        window.setResizable(false);
        window.setTitle("RISK 101");
        window.getIcons().add(new Image("img\\logo.png"));
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
            case "Return to Main Menu":
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
            case "STOP THE BUG":
                menuState.update();
                musicMuted = false;
                soundEngine.setMute(false);
                try {
                    newGame();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    public void changeScene(Scene scene){
        scene.getStylesheets().add("css/menu_stylesheet.css");
        window.setScene(scene);
        this.changeMaximized(isMaximized);
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

    public void back(){
        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
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

    public void checkForDir() throws Exception {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\RISK101");
        if(!file.exists()){
            boolean success = file.mkdir();
            if(!success){
                throw new Exception("Cannot create directory for save files.");
            }
        }
    }

    public void changeMaximized(boolean maximized){
        this.isMaximized = maximized;
        if (maximized) {
            if( displayChanged) {
                window.close();
                window = new Stage();
                window.setResizable(false);
                window.setTitle("RISK 101");
                window.getIcons().add(new Image("img\\logo.png"));
                window.initStyle(StageStyle.UNDECORATED);
                window.setScene(this.scene);
            }
        } else {
            if( displayChanged) {
                window.close();
                window = new Stage();
                window.setResizable(false);
                window.setTitle("RISK 101");
                window.getIcons().add(new Image("img\\logo.png"));
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
        return musicMuted;
    }

    public boolean getSoundFXMuted(){
        return soundFXMuted;
    }

    public double getMusicValue(){
        return musicValue;
    }

    public double getSoundFXValue(){
        return soundFXValue;
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
        this.musicMuted = mute;
        soundEngine.setMute(musicMuted);
    }

    public void setSoundFXMuted(boolean mute){
        this.soundFXMuted = mute;
    }

    public void setSoundFXValue(double value){
        this.soundFXValue = value;
        buttonSound.setVolume( soundFXValue / 100.0);
    }

    public void setMusicValue(double value){
        this.musicValue = value;
        soundEngine.setVolume(musicValue / 100.0);
    }

    public void playButtonSound(){
        if(!soundFXMuted){ buttonSound.play(); }
    }

}

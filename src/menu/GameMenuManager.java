package menu;

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
import javafx.util.Duration;

import java.awt.*;
import java.io.File;


public class GameMenuManager extends Application implements EventHandler<ActionEvent> {

    private boolean isMaximized;
    // private SoundEngine soundEngine;
    private static final int WINDOWED_WIDTH = 600;
    private static final int WINDOWED_HEIGHT = 600;
    private int width, height;
    private Scene scene;
    private MenuState menuState;
    private Stage window;

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
        musicValue = 15.0;
        soundFXValue = 100.0;

        soundEngine = new MediaPlayer(new Media(new File("src\\menu_music.mp3").toURI().toString()));
        soundEngine.setMute(musicMuted);
        soundEngine.setVolume(musicValue / 100.0);
        soundEngine.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                soundEngine.seek(Duration.ZERO);
                soundEngine.play();
            }
        });
        soundEngine.play();

        buttonSound = new AudioClip(new File("src\\button_click.wav").toURI().toString());
        buttonSound.setVolume(soundFXValue / 100.0);

        try {
            checkForDir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        window = primaryStage;
        window.setResizable(false);
        window.setTitle("RISK 101");
        window.getIcons().add(new Image("logo.png"));


        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        String s = "";
        try{
            s = ((Button) e.getSource()).getText();
            buttonSound.play();
        } catch (Exception exception) {
            System.out.println("Not a button.");
        }

        if(s.equals("Help")){
            viewHelp();
        }else if (s.equals("Back")){
            back();
        }else if (s.equals("Exit")){
            exit();
        }else if (s.equals("Credits")){
            viewCredits();
        }else if (s.equals("Load Game")){
            try {
                loadGame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if (s.equals("New Game")){
            try {
                newGame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (s.equals("Settings")){
            viewSettings();
        } else if(s.equals("STOP THE BUG")){
            menuState.update();
            musicMuted = false;
            soundEngine.setMute(false);
            try {
                newGame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void changeScene(Scene scene){
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
        System.out.println("w: " + window.getWidth() + " height: " + window.getHeight());
        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
        this.changeScene(scene);
    }

    public void startGame(){
        menuState = new StickBugGame(width,height);
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
        File file = new File("C:\\Users\\Personal\\AppData\\Local\\RISK101");
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
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        } else {
            width = WINDOWED_WIDTH;
            height = WINDOWED_HEIGHT;
        }
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

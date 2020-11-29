package menu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class GameMenuManager extends Application implements EventHandler<ActionEvent> {

    private boolean isFullscreen;
    // private SoundEngine soundEngine;
    private static final int WINDOWED_WIDTH = 600;
    private static final int WINDOWED_HEIGHT = 600;
    private int width, height;
    private Scene scene;
    private MenuState menuState;
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        width = WINDOWED_WIDTH;
        height = WINDOWED_HEIGHT;
        isFullscreen = false;
        try {
            checkForDir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        window = primaryStage;
        window.setResizable(false);
        window.setTitle("RISK 101");

        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);

        this.changeScene(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        String s = "";
        try{
            s = ((Button) e.getSource()).getText();
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
        }
        this.changeScene(scene);
    }

    public void changeScene(Scene scene){
        window.setScene(scene);
        window.show();
    }

    public void loadGame() throws Exception {
        menuState = new LoadGameMenu(width,height);
        scene = menuState.createScene(this);
    }

    public void newGame() throws Exception {
        menuState = new NewGameMenu(width,height);
        scene = menuState.createScene(this);
    }

    public void viewCredits(){
        menuState = new CreditsMenu(width,height);
        scene = menuState.createScene(this);
    }

    public void viewHelp(){
        menuState = new HelpMenu(width,height);
        scene = menuState.createScene(this);
    }

    public void viewSettings(){

    }

    public void back(){
        menuState = new MainMenu(width, height);
        scene = menuState.createScene(this);
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

    public void setFullscreen(boolean fullscreen){
        if(fullscreen){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        }
        else{
            width = WINDOWED_WIDTH;
            height = WINDOWED_HEIGHT;
        }
    }
}

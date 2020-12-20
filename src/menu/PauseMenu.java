package menu;

import game.GameEngine;
import game.SoundEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class PauseMenu implements EventHandler<ActionEvent>{

    private Scene scene;
    private final int width, height;
    private Label title;
    private Button backButton;
    private Button settingsButton, helpButton, exitButton;
    private VBox root;
    private SoundEngine soundEngine;
    private GameEngine gameEngine;

    public PauseMenu(int width, int height, GameEngine gameEngine){
        this.width = width;
        this.height = height;
        this.soundEngine = SoundEngine.getInstance();
        this.gameEngine = gameEngine;
    }

    @Override
    public void handle(ActionEvent e) {
        if( e.getSource() instanceof Button){
            soundEngine.playButtonSound();
        }

        if( e.getSource() == backButton){
            gameEngine.unpause();
        }
        else if (e.getSource() == settingsButton){
            openSettings();
        }
        else if (e.getSource() == helpButton){
            openHelp();
        }else if (e.getSource() == exitButton){
            gameEngine.backToMainMenu();
        }
    }
    public Scene createScene() {
        init();
        addHandlers();
        return scene;
    }

    public void init(){
        String bigButtonStyle = "menu_button_max" ;
        String titleStyle = "title_max";

        // trivial initialization
        title = new Label("Paused");
        backButton = new Button("Back to Game");
        settingsButton = new Button("Settings");
        helpButton = new Button("Help");
        exitButton = new Button("Exit to Main Menu");
        backButton.getStyleClass().add(bigButtonStyle);
        helpButton.getStyleClass().add(bigButtonStyle);
        exitButton.getStyleClass().add(bigButtonStyle);
        settingsButton.getStyleClass().add(bigButtonStyle);

        // set settings title style
        title.getStyleClass().add(titleStyle);

        // create the layout
        root = new VBox();
        root.setPrefSize(width,height);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(40);
        root.setId("menu_bg");
        root.getChildren().setAll(backButton,settingsButton,helpButton,exitButton);
        scene = new Scene(root,width,height);
    }

    public void addHandlers(){
        backButton.setOnAction(this);
        settingsButton.setOnAction(this);
        helpButton.setOnAction(this);
        exitButton.setOnAction(this);
    }

    public void openSettings(){
        PauseSettingsMenu pauseSettings = new PauseSettingsMenu(width,height,this);
        gameEngine.setScene(pauseSettings.createScene());
    }

    private void openHelp() {
        PauseHelpMenu pauseHelp = new PauseHelpMenu(width,height,this);
        gameEngine.setScene(pauseHelp.createScene());
    }


    public void back(){
        gameEngine.setScene(this.scene);
    }
}

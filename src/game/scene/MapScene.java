package game.scene;

//import game.GameEngine;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import menu.GameMenuManager;
import menu.MenuState;

import java.io.File;

public class MapScene {
    final String style_base = "{" +
            "    -fx-background-color: " +
            "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
            "        linear-gradient(#020b02, #3a3a3a)," +
            "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%)," +
            "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);" +
            "    -fx-background-insets: 0,1,4,5;" +
            "    -fx-background-radius: 9,8,5,4;" +
            "    -fx-padding: 15 30 15 30;" +
            "    -fx-font-family: \"Helvetica\";" +
            "    -fx-font-size: 18px;" +
            "    -fx-font-weight: bold;" +
            "    -fx-text-fill: #404040;" +
            "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);" +
            "    -fx-focus-color: transparent;";

    private int width, height;
    private Scene scene;
    private MenuState state;
    private GameMenuManager mgr;
    private MediaPlayer mp;
    private String stateName;

    public MapScene(int width, int height, String stateName) {
        this.height = height;
        this.width = width;
        this.stateName = stateName;
    }

    public void update() {
        mp.stop();
    }

    public Scene createScene(GameMenuManager mgr, MenuState state) {
        this.state = state;
        this.mgr = mgr;
        init(mgr.getMaximized());
        return scene;
    }

    public void init(boolean maximized) {
        Media video = new Media(new File("src\\video\\stickbug.mp4").toURI().toString());
        mp = new MediaPlayer(video);
        mp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mp.seek(Duration.ZERO);
                mp.play();
            }
        });
        MediaView mv = new MediaView(mp);
        mv.setFitHeight(height);
        mv.setFitWidth(width);
        mp.setAutoPlay(true);
        mgr.setMusicMuted(true);
        Group gp = new Group();
        gp.getChildren().add(mv);

        BorderPane top = new BorderPane();
        Button backButton = new Button("STOP THE BUG");
        backButton.setOnAction(mgr);
        Text[] players = {new Text("MAN"), new Text("ART"), new Text("MF")};
        HBox playerSequence = new HBox();
        playerSequence.getChildren().addAll(players[0], players[1], players[2]);
        playerSequence.setSpacing(5);
        playerSequence.setAlignment(Pos.CENTER);
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> System.out.println("wow nothing happened!"));
        top.setLeft(backButton);
        top.setCenter(playerSequence);
        top.setRight(pauseButton);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(mv);
        scene = new Scene(root, width, height);
    }
}

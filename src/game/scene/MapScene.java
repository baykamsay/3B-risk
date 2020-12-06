package game.scene;

import game.GameEngine;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import menu.GameMenuManager;
import menu.MenuState;

import java.io.File;

public class MapScene {

    private int width, height;
    private Scene scene;
    private MenuState state;
    private GameEngine engine;
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

    public Scene createScene(GameMenuManager mgr, GameEngine engine, MenuState state) {
        this.state = state;
        this.engine = engine;
        this.mgr = mgr;
//        init(mgr.getMaximized());
        init(true);
        return scene;
    }

    public void init(boolean maximized) {
        // Filler for map
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
//        mgr.setMusicMuted(true);
        Group gp = new Group();
        gp.getChildren().add(mv);

        /*
            Map buttons has a listener that calls a function in the state:
            e -> state.mapSelect(e);
         */

        // Overlay top
        BorderPane top = new BorderPane();
        Button backButton = new Button("STOP THE BUG");
//        backButton.setOnAction(mgr);
        Text[] players = {new Text("MAN"), new Text("ART"), new Text("MF")};
        HBox playerSequence = new HBox();
        playerSequence.getChildren().addAll(players[0], players[1], players[2]);
        playerSequence.setSpacing(5);
        playerSequence.setAlignment(Pos.CENTER);
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> System.out.println("wow nothing happened!"));
        top.setLeft(backButton);
        top.setRight(pauseButton);
        StackPane topStack = new StackPane(playerSequence, top);

        // Overlay bottom
        BorderPane bottom = new BorderPane();
        Text currentPlayer = new Text("Faculty of Engineering's Turn");
        bottom.setLeft(currentPlayer);
        Text reinforceStage = new Text("REINFORCE");
        Text attackStage = new Text("ATTACK");
        Text fortifyStage = new Text("FORTIFY");
        HBox stages = new HBox();
        stages.getChildren().addAll(reinforceStage, attackStage, fortifyStage);
        stages.setSpacing(5);
        stages.setAlignment(Pos.CENTER);
        Button pass = new Button("PASS");
        pass.setOnAction(e -> System.out.println("Switching state"));
        bottom.setRight(pass);
        StackPane bottomStack = new StackPane(stages, bottom);

        BorderPane overlay = new BorderPane();
        overlay.setTop(topStack);
        overlay.setBottom(bottomStack);
        StackPane root = new StackPane(mv, overlay);
        scene = new Scene(root, width, height);
    }
}

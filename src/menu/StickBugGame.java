package menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class StickBugGame extends Application implements MenuState{
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
    private GameMenuManager mgr;
    private Scene scene;
    private MediaPlayer mp;

    public StickBugGame(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void update() {
        mp.stop();
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        init(mgr.getMaximized());
        return scene;
    }

    public void init(boolean maximized){
        Media video = new Media(new File("src\\stickbug.mp4").toURI().toString());
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

        VBox root = new VBox();
        Button backButton = new Button("STOP THE BUG");
        backButton.setOnAction(mgr);
        String style_gigantic = style_base + "-fx-pref-width: " + width + "px; -fx-pref-height: 20";
        backButton.setStyle(style_gigantic);
        root.getChildren().addAll(backButton,mv);
        root.setAlignment(Pos.TOP_CENTER);
        scene = new Scene(root, width, height);

    }
}
package game.scene;

//import game.GameEngine;
import javafx.fxml.FXMLLoader;
import game.GameEngine;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import menu.GameMenuManager;
import menu.MenuState;

import java.io.File;
import java.io.IOException;
import java.net.URL;

//implementing menustate to test for now, remove later
public class MapScene implements MenuState{

    private int width, height;
    private Scene scene;
    private MenuState state;
    private GameEngine engine;
    private GameMenuManager mgr;
    private String stateName;

    public MapScene(int width, int height, String stateName) {
        this.height = height;
        this.width = width;
        this.stateName = stateName;
    }

    public void update() {
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        try {
            init(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public Scene createScene(GameMenuManager mgr, GameEngine engine, MenuState state) {
        this.state = state;
        this.engine = engine;
        this.mgr = mgr;
//        init(mgr.getMaximized());
        try {
            init(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public Scene createScene(GameMenuManager mgr, MenuState state) {
        return null;
    }

    public void init(boolean maximized) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlUrl = getClass().getResource("../../fxml/map.fxml");
        loader.setLocation(fxmlUrl);
        VBox root = new VBox();
        root.setId("map_bg");
        Pane map = loader.load();
        root.getChildren().addAll(map);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root,width,height);
    }
}

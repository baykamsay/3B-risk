package game.scene;

<<<<<<< HEAD
//import game.GameEngine;
import javafx.fxml.FXMLLoader;
=======
import game.GameEngine;
>>>>>>> 5f80902a61087ab50eb1be382bff1693812a0fd8
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
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

<<<<<<< HEAD
    @Override
    public Scene createScene(GameMenuManager mgr) {
=======
    public Scene createScene(GameMenuManager mgr, GameEngine engine, MenuState state) {
>>>>>>> 5f80902a61087ab50eb1be382bff1693812a0fd8
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
        Parent root = loader.load();
        scene = new Scene(root);
    }
}

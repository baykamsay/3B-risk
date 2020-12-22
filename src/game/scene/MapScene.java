package game.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import menu.Launcher;

import java.awt.*;
import java.io.IOException;

public class MapScene {

    private int width, height;
    private Scene scene;
    private MapSceneController controller;

    public MapScene(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public Scene createScene() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public void init() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Launcher.class.getResource("/fxml/map.fxml"));
        VBox root = new VBox();
        root.setId("map_bg");
        Pane map = loader.load();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double w =  screenSize.getWidth() / 1920;
        double h =  screenSize.getHeight() / 1080;
        Scale scale = new Scale(w, h, 0, 0);
        this.controller = loader.getController();
        map.getTransforms().add(scale);
        root.getChildren().add(map);
        scene = new Scene(root,width,height);
    }

    public MapSceneController getController(){
        return controller;
    }
}

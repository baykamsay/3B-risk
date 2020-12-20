package game.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import menu.Launcher;

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
        root.getChildren().addAll(map);
        root.setAlignment(Pos.CENTER);
        this.controller = loader.getController();
        scene = new Scene(root,width,height);
    }

    public MapSceneController getController(){
        return controller;
    }
}

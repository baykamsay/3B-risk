package menu;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    private Stage window;

//    public static void main(String[] args){
//        launch(args);
//    }

    @Override
    public void start(Stage stage) throws Exception {
        this.window = stage;
        GameMenuManager mgr = new GameMenuManager(this);
        mgr.start(window);
    }
}

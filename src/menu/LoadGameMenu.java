package menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadGameMenu extends Application implements MenuState{

    private final int NO_OF_SLOTS = 8;
    private final Button[] slots = new Button[NO_OF_SLOTS];
    private final boolean[] occupied = new boolean[NO_OF_SLOTS];
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

    final String style_save_slot =  style_base +
            "    -fx-pref-width: 200px;" +
            "    -fx-pref-height: 25px;"+
            "    -fx-max-height: 25px;"+
            "}";
    final String style_back = " -fx-background-color: " +
            "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
            "        linear-gradient(#020b02, #3a3a3a)," +
            "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%)," +
            "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);" +
            "}";

    private Label title;
    private ScrollPane scrollPane;
    private Button back;
    private int width, height;
    private Scene scene;
    private GameMenuManager mgr;

    //Change this when moving to stylesheets
    private String backgroundPath;

    public LoadGameMenu(int width, int height) throws Exception {
        this.width = width;
        this.height = height;
    }
    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void update() {

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        try {
            checkForSaves();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.mgr = mgr;
        init(mgr.getMaximized());
        return scene;
    }

    public void checkForSaves() throws Exception {
        File[] files = new File(System.getenv("LOCALAPPDATA")+"\\RISK101").listFiles();
        for(File f : files){
            Scanner fScan = null;
            try {
                fScan = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int saveNo = Integer.parseInt(fScan.nextLine());
            if(saveNo > NO_OF_SLOTS){
                throw new Exception("Corrupted/Tampered save file.");
            }
            occupied[saveNo] = true;
            fScan.close();
        }
    }

    public void init(boolean maximized){
        backgroundPath = maximized ? "-fx-background-image: url(\"img/bg_bilkent.png\"); -fx-background-size: cover;" : "-fx-background-image: url(\"img/bg_bilkent.png\");";
        title = new Label("Load Game");
        title.setFont(new Font("Helvetica", 30));
        title.setStyle("-fx-font-weight: bold");
        back = new Button("Back");
        back.setStyle(style_back);

        HBox top = new HBox(back);
        top.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(8);

        VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);
        for(int i = 0; i < NO_OF_SLOTS; i++){
            slots[i] = new Button("Save Slot " + Integer.toString(i + 1));
            if(!occupied[i]){
                slots[i].setDisable(true);
            }
            else{
                //TO-DO: Display saved game info?
            }
            slots[i].setStyle(style_save_slot);
            pane.getChildren().add(slots[i]);
        }

        back.setOnAction(mgr);
        for( Button b : slots){
            b.setOnAction(mgr);
        }

        root.getChildren().addAll(top,title,pane);
        root.setStyle(backgroundPath);
        scene = new Scene(root,width,height);
    }
}

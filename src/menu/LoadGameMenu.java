package menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadGameMenu implements MenuState{

    private final int NO_OF_SLOTS = 8;
    private final Button[] slots = new Button[NO_OF_SLOTS];
    private final boolean[] occupied = new boolean[NO_OF_SLOTS];

    private Label title;
    private Button back;
    private final int width, height;
    private Scene scene;
    private GameMenuManager mgr;


    public LoadGameMenu(int width, int height) {
        this.width = width;
        this.height = height;
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
        String backStyle = mgr.getMaximized() ? "menu_button_back_max" : "menu_button_back_min";
        String emptyStyle = maximized ? "empty_slot_max" : "empty_slot_min";
        String titleStyle = mgr.getMaximized() ? "title_max" : "title_min";
        title = new Label("Load Game");
        title.getStyleClass().add(titleStyle);
        back = new Button("Back");
        back.getStyleClass().add(backStyle);

        HBox top = new HBox(back);
        top.setPadding(new Insets(5));
        top.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(8);

        VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);
        for(int i = 0; i < NO_OF_SLOTS; i++){
            slots[i] = new Button("Save Slot " + (i + 1));
            if(!occupied[i]){
                slots[i].setDisable(true);
            }
            else{
                //TO-DO: Display saved game info?
            }
            slots[i].getStyleClass().add(emptyStyle);
            pane.getChildren().add(slots[i]);
        }

        back.setOnAction(mgr);
        for( Button b : slots){
            b.setOnAction(mgr);
        }

        root.getChildren().addAll(top,title,pane);
        root.setId("menu_bg");
        scene = new Scene(root,width,height);
    }
}
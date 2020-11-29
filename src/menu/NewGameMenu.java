package menu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NewGameMenu extends Application implements MenuState, EventHandler<ActionEvent> {

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

    final String occupied_style =
            "    -fx-background-color: " +
            "        red," +
            "        linear-gradient(rgba(255,0,0,1) 0%, rgba(253,29,29,1) 30%, rgba(252,176,69,1) 100%);" +
            "    -fx-background-insets: 0,1,4,5;" +
            "    -fx-background-radius: 9,8,5,4;" +
            "    -fx-padding: 15 30 15 30;" +
            "    -fx-font-family: \"Helvetica\";" +
            "    -fx-font-size: 18px;" +
            "    -fx-font-weight: bold;" +
            "    -fx-text-fill: #404040;" +
            "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);" +
            "    -fx-pref-width: 200px;" +
            "    -fx-pref-height: 25px;"+
            "    -fx-max-height: 25px;";

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
    int chosenSlot;

    public NewGameMenu(int width, int height) throws Exception {
        this.width = width;
        this.height = height;
        checkForSaves();
        init();
    }
    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void terminating() {

    }

    @Override
    public void update() {

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        back.setOnAction(mgr);
        return scene;
    }

    public void checkForSaves() throws Exception {
        File[] files = new File("C:\\Users\\Personal\\AppData\\Local\\RISK101").listFiles();
        for(File f : files){
            System.out.println(f.getName());
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

    public void init(){
        title = new Label("New Game");
        title.setFont(new Font("Helvetica", 30));
        title.setStyle("-fx-font-weight: bold");
        back = new Button("Back");
        back.setStyle(style_back);

        HBox top = new HBox(back);
        top.setAlignment(Pos.TOP_LEFT);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(8);

        VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);
        for(int i = 0; i < NO_OF_SLOTS; i++){
            slots[i] = new Button("Save Slot " + Integer.toString(i + 1));
            if(occupied[i]){
                slots[i].setStyle(occupied_style);

                System.out.println("hi " + i + " " + slots[i].toString());
            }else{
                //TO-DO: Display saved game info?
                System.out.println("hello " + i + " " + slots[i].toString());
                slots[i].setStyle(style_save_slot);
            }
            slots[i].setOnAction(this);
            pane.getChildren().add(slots[i]);
        }

        root.getChildren().addAll(top,title,pane);
        root.setStyle("-fx-background-image: url(\"bg_bilkent.png\");");
        scene = new Scene(root,width,height);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        String s = b.getText();
        chosenSlot = Integer.parseInt(s.substring(s.length() - 1, s.length())) - 1;
        if(occupied[chosenSlot]){
            overWrite();
        }
        //TO-DO: Call mgr to get to faculty selection screen
    }

    public void overWrite(){
        Alert overWrite = new Alert(Alert.AlertType.WARNING, "Overwrite save file.", ButtonType.YES, ButtonType.NO);
        overWrite.setTitle("Overwrite");
        overWrite.setHeaderText("Overwrite save file?");
        overWrite.setContentText("This will irreversibly destroy the previous save.");
        overWrite.showAndWait();
        if(overWrite.getResult() == ButtonType.YES){
            File file = new File("C:\\Users\\Personal\\AppData\\Local\\Risk101" + "\\save" + chosenSlot + ".txt");
            file.delete();
            //TO-DO: Call mgr to get to faculty selection screen
        }
    }
}

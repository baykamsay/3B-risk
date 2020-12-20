package menu;

import game.SaveManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NewGameMenu implements MenuState, EventHandler<ActionEvent> {

    private final int NO_OF_SLOTS = 8;
    private final Button[] slots = new Button[NO_OF_SLOTS];
    private boolean[] occupied;
    private Label title;
    private Button back;
    private final int width, height;
    private Scene scene;
    int chosenSlot;
    private GameMenuManager mgr;


    public NewGameMenu(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        try {
            occupied = SaveManager.getInstance().checkForSaves(NO_OF_SLOTS);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Save system may be corrupted.");
        }

        this.mgr = mgr;
        init(mgr.getMaximized());
        back.setOnAction(mgr);
        return scene;
    }

    public void init(boolean maximized){
        String backStyle = mgr.getMaximized() ? "menu_button_back_max" : "menu_button_back_min";
        String occupiedStyle = maximized ? "occupied_slot_max" : "occupied_slot_min";
        String emptyStyle = maximized ? "empty_slot_max" : "empty_slot_min";
        String titleStyle = mgr.getMaximized() ? "title_max" : "title_min";
        title = new Label("New Game");
        title.getStyleClass().add(titleStyle);
        back = new Button("Back");
        back.getStyleClass().add(backStyle);
        HBox top = new HBox(back);
        top.setPadding(new Insets(5));
        top.setAlignment(Pos.TOP_LEFT);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPrefSize(width,height);
        root.setSpacing(8);

        VBox pane = new VBox();
        pane.setSpacing(3);
        pane.setAlignment(Pos.TOP_CENTER);
        for(int i = 0; i < NO_OF_SLOTS; i++){
            slots[i] = new Button("Save Slot " + (i + 1));
            if(occupied[i]){
                slots[i].getStyleClass().add(occupiedStyle);
            }else{
                //TO-DO: Display saved game info?
                slots[i].getStyleClass().add(emptyStyle);
            }
            slots[i].setOnAction(this);
            pane.getChildren().add(slots[i]);
        }

        root.getChildren().addAll(top,title,pane);
        root.setId("menu_bg");
        scene = new Scene(root,width,height);
    }

    @Override
    public void handle(ActionEvent e) {
        if(e.getSource() instanceof Button){
            mgr.playButtonSound();
        }
        Button b = (Button) e.getSource();
        String s = b.getText();
        chosenSlot = Integer.parseInt(s.substring(s.length() - 1)) - 1;
        if(occupied[chosenSlot]) {
            overWrite();
        }
        else{
            mgr.facultySelection(chosenSlot);
        }
    }

    public void overWrite(){
        Alert overWrite = new Alert(Alert.AlertType.WARNING, "Overwrite save file.", ButtonType.YES, ButtonType.NO);
        overWrite.setTitle("Overwrite");
        overWrite.setHeaderText("Overwrite save file?");
        overWrite.setContentText("This will irreversibly destroy the previous save.");
        overWrite.showAndWait();
        if(overWrite.getResult() == ButtonType.YES){
            mgr.facultySelection(chosenSlot);
        }
    }
}

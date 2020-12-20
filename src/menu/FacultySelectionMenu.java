package menu;


import game.player.faculties.*;
import game.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class FacultySelectionMenu implements MenuState, EventHandler<ActionEvent> {

    private static final int MAX_SELECTED = 6;
    private static final String[] FACULTY_NAMES = {"Art, Design \nand Architecture","Applied Sciences","Economics, Administration \nand Social Sciences","Education","Science","Humanities and Letters","Law","Business Administration","Engineering","Music \nand Performing Arts"};
    private static final String[] ICON_PATHS = {"art_faculty_icon.png","applied_sciences_faculty_icon.png","econ_faculty_icon.png","education_faculty_icon.png","science_faculty_icon.png","humanities_faculty_icon.png","law_faculty_icon.png","business_faculty_icon.png","engineering_faculty_icon.png","music_finearts_faculty_icon.png"};
    private static final String[] ABILITY_DESCRIPTIONS = {"Everything by Design: Once a turn, you may choose to swap the result of one of your die rolls with the opponent","Eastern Affinity: You win ties when attacking in the East region.", "The Moderate Depression: Once a turn, you may reduce 1 from the dice rolls of the enemy.", "Reeducate: Once a turn you may discard your objective card and draw another one.", "Knowledge Above All: Your rewards from completed objectives are doubled.", "The Pen is Mightier: If the attacker has more armies, gain an extra die.", "Lawyered: Once a turn, you may choose to redo a dice roll.", "Hostile Takeover: Once a game, take over an opponent's territory along with the soldiers.", "Over-Engineered: You can't roll a 1.", "Smoke and Mirrors: Once a game, you may choose to play an extra turn right after your normal one."};
    private int width, height;
    private int selected;
    private boolean[] selectedFaculties;
    private Scene scene;
    private GameMenuManager mgr;
    private Button backButton;
    private Button startButton;
    private ImageView[] icons;
    private Label[] labels;
    private HBox[] elements;
    private int saveSlot;

    public FacultySelectionMenu(int width, int height, int saveSlot) {
        this.saveSlot = saveSlot;
        this.width = width;
        this.height = height;
        selected = 0;
        selectedFaculties = new boolean[]{false,false,false,false,false,false,false,false,false,false};
    }

    @Override
    public void update() {
        if(selected >= 2){
            startButton.setDisable(false);
        }
        else{
            startButton.setDisable(true);
        }
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        init();
        return scene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if( selected >= 2) {
            if (mgr.forceMaximized()) {
                ArrayList<Player> players = new ArrayList<>();
                for(int i = 0; i < 10; i++){
                    if(selectedFaculties[i]){
                        switch(i) {
                            case 0:
                                players.add(new Player(new Art()));
                                break;
                            case 1:
                                players.add(new Player(new Fas()));
                                break;
                            case 2:
                                players.add(new Player(new Feass()));
                                break;
                            case 3:
                                players.add(new Player(new Fedu()));
                                break;
                            case 4:
                                players.add(new Player(new Fen()));
                                break;
                            case 5:
                                players.add(new Player(new Ibef()));
                                break;
                            case 6:
                                players.add(new Player(new Law()));
                                break;
                            case 7:
                                players.add(new Player(new Man()));
                                break;
                            case 8:
                                players.add(new Player(new Mf()));
                                break;
                            case 9:
                                players.add(new Player(new Mssf()));
                                break;
                        }
                    }
                }

                try {
                    mgr.startGameEngine(saveSlot, players);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void init(){
        String backStyle = mgr.getMaximized() ? "menu_button_back_max" : "menu_button_back_min";
        String facultyTitleStyle = mgr.getMaximized() ? "faculty_title_max" : "faculty_title_min";
        String bigButtonStyle = mgr.getMaximized() ? "menu_button_max" : "menu_button_min";

        // Initialize Back button
        HBox backButtonBox = new HBox();
        backButtonBox.setPadding(new Insets(5));
        backButton = new Button("Back");
        backButton.getStyleClass().add(backStyle);
        backButton.setOnAction(mgr);
        backButtonBox.getChildren().add(backButton);

        // Initialize Start button
        HBox startButtonBox = new HBox();
        startButtonBox.setAlignment(Pos.CENTER);
        startButton = new Button("Start Game");
        startButton.getStyleClass().add(bigButtonStyle);
        startButton.setOnAction(this);
        startButton.setDisable(true);
        startButtonBox.getChildren().add(startButton);

        // Initialize root
        VBox root = new VBox();
        root.setSpacing(50);


        // Initialize icons
        initIcons();

        // Initialize labels
        initLabels(facultyTitleStyle);

        // Initialize elements
        initElements();

        // Set the tooltips for ability descriptions.
        setTooltips();

        // Initialize two columns for faculty elements and a HBox to hold them
        HBox cols = new HBox();
        cols.setAlignment(Pos.CENTER);
        cols.setSpacing(20);
        VBox col1 = new VBox();
        col1.setMaxWidth(width / 2);
        col1.setSpacing(10);
        col1.setAlignment(Pos.CENTER_RIGHT);
        VBox col2 = new VBox();
        col2.setMaxWidth(width / 2);
        col2.setSpacing(10);
        col1.setAlignment(Pos.CENTER_LEFT);

        for(int i = 0; i < 10; i++){
            if( i < 5){
                col1.getChildren().add(elements[i]);
            }
            else{
                col2.getChildren().add(elements[i]);
            }
        }

        cols.getChildren().addAll(col1,col2);

        root.getChildren().addAll(backButtonBox,cols, startButtonBox);
        root.setId("menu_bg");
        scene = new Scene(root, width, height);
    }

    public void initElements(){
        elements = new HBox[10];
        for(int i = 0; i < 10; i++){
            if( i < 5) {
                elements[i] = new HBox(labels[i],icons[i]);
                elements[i].setAlignment(Pos.CENTER_RIGHT);
            }
            else{
                elements[i] = new HBox(icons[i],labels[i]);
                elements[i].setAlignment(Pos.CENTER_LEFT);
            }
            elements[i].setSpacing(10);
        }
    }

    public void initLabels(String facultyTitleStyle){
        labels = new Label[10];
        int i = 0;
        for(String s : FACULTY_NAMES){
            labels[i] = new Label(s);
            labels[i].getStyleClass().add(facultyTitleStyle);
            i++;
        }
    }

    public void initIcons(){
        icons = new ImageView[10];
        int i = 0;
        Image tmp;
        ColorAdjust unselected = new ColorAdjust();
        unselected.setSaturation(-0.9);
        for(String s : ICON_PATHS){
            try {
                tmp = new Image(Launcher.class.getResource("/img/icons/" + s).toURI().toString());
                icons[i] = new ImageView();
                icons[i].setImage(tmp);
                icons[i].setEffect(unselected);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            icons[i].setPickOnBounds(false);
            icons[i].setId(Integer.toString(i));
            icons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int id = Integer.parseInt(((ImageView)event.getSource()).getId());
                if( selectedFaculties[id]) {
                    setSelected(id, false);
                }
                else{
                    setSelected(id, true);
                }
            });
            i++;
        }
    }

    public void setSelected(int i, boolean set){
        ColorAdjust unselected = new ColorAdjust();
        unselected.setSaturation(-0.9);
        if(!set){

            icons[i].setEffect(unselected);
            selectedFaculties[i] = false;
            mgr.playButtonSound();
            selected--;
        }else {
            if (selected < MAX_SELECTED) {
                icons[i].setEffect(null);
                selectedFaculties[i] = true;
                mgr.playButtonSound();
                selected++;
            }
        }
        update();
    }

    public void setTooltips(){
        int i = 0;
        for(ImageView iv : icons){
            Tooltip tp = new Tooltip(ABILITY_DESCRIPTIONS[i]);
            tp.setHideDelay(new Duration(0));
            tp.setShowDelay(new Duration(0));
            tp.setId("ability_tooltip");
            tp.setShowDuration(Duration.INDEFINITE);
            Tooltip.install(iv, tp);
            i++;
        }
    }
}

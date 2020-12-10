package menu;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class FacultySelectionMenu implements MenuState, EventHandler<ActionEvent> {

    private static final int MAX_SELECTED = 6;
    private static final String[] FACULTY_NAMES = {"Art, Design \nand Architecture","Applied Sciences","Economics, Administration \nand Social Sciences","Education","Science","Humanities and Letters","Law","Bussiness Administration","Engineering","Music \nand Performing Arts"};
    private static final String[] ICON_PATHS = {"img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png","img\\faculty_icon_placeholder.png"};
    private static final String[] SELECTED_ICON_PATHS = {"img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png","img\\faculty_icon_placeholder_selected.png"};
    private static final String[] ABILITY_DESCRIPTIONS = {"Everything by Design: Once a turn, you may choose to swap the result of one of your die rolls with the opponent","Eastern Affinity: You win ties when attacking in the East region.", "The Moderate Depression: Once a turn, you may reduce 1 from the dice rolls of the enemy.", "Reeducate: When you draw a new Objective card, you may discard it and draw another one.", "Knowledge Above All: Once a game, reveal all other players' Objective cards.", "The Pen is Mightier: If the attacker has more armies, gain an extra die.", "Lawyered: Once a turn, you may choose to redo a dice roll.", "Hostile Takeover: Once a game, take over an opponent's territory along with the soldiers.", "Over-Engineered: You can't roll a 1.", "Smoke and Mirrors: Once a game, you may choose to play an extra turn right after your normal one."};

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

    public FacultySelectionMenu(int width, int height) {
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
        init(mgr.getMaximized());
        return scene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if( selected >= 2) {
            if (mgr.forceMaximized()) {
                mgr.testMap();
            }
        }
    }

    public void init(boolean maximized){
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
        for(String s : ICON_PATHS){
            tmp = new Image(s);
            icons[i] = new ImageView();
            icons[i].setImage(tmp);
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
        if(!set){
            icons[i].setImage(new Image(ICON_PATHS[i]));
            selectedFaculties[i] = false;
            mgr.playButtonSound();
            selected--;
        }else {
            if (selected < MAX_SELECTED) {
                icons[i].setImage(new Image(SELECTED_ICON_PATHS[i]));
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

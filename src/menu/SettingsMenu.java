package menu;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SettingsMenu extends Application implements MenuState, EventHandler<ActionEvent> {

    private Scene scene;
    private final int width, height;
    private GameMenuManager mgr;
    private Button backButton;
    private Label title, musicTitle, soundFXTitle;
    private Slider musicSlider;
    private Slider soundFXSlider;
    private boolean musicMuted, soundFXMuted;
    private double musicValue, soundFXValue;
    private boolean maximized;
    private Button borderedButton, maximizedButton;
    private Button applyButton, discardButton;
    private ImageView displayIcon;
    private ImageView musicIcon, soundFXIcon;
    private VBox root;

    public SettingsMenu(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void handle(ActionEvent e) {
        if( e.getSource() instanceof Button){
            mgr.playButtonSound();
        }

        if (e.getSource() == borderedButton){
            maximized = false;
            borderedButton.setDisable(true);
            maximizedButton.setDisable(false);
            update();
        } else if (e.getSource() == maximizedButton){
            maximized = true;
            borderedButton.setDisable(false);
            maximizedButton.setDisable(true);
            update();
        } else if (e.getSource() == applyButton){
            applyChanges();
        } else if (e.getSource() == discardButton){
            discardChanges();
        }
    }

    @Override
    public void update() {
        String displayImgPath = maximized ? "img\\maximized_icon.png" : "img\\windowed_icon.png";
        Image displayImg = new Image(displayImgPath);
        displayIcon.setImage(displayImg);

        String musicImgPath = musicMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        String soundFXImgPath = soundFXMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        Image musicImg = new Image(musicImgPath);
        Image soundFXImg = new Image(soundFXImgPath);
        musicIcon.setImage(musicImg);
        soundFXIcon.setImage(soundFXImg);
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        init(mgr.getMaximized());
        addHandlers();
        return scene;
    }

    public void init(boolean isMaximized){
        String backStyle = mgr.getMaximized() ? "menu_button_back_max" : "menu_button_back_min";
        String displayStyle = mgr.getMaximized() ? "menu_button_display_max" : "menu_button_display_min";
        String bigButtonStyle = mgr.getMaximized() ? "menu_button_max" : "menu_button_min";
        String labelStyle = mgr.getMaximized() ? "label_max" : "label_min";
        String titleStyle = mgr.getMaximized() ? "title_max" : "title_min";
        int sliderW = mgr.getMaximized() ? 400 : 200;
        int sliderH = mgr.getMaximized() ? 40 : 20;
        int iconSize = mgr.getMaximized() ? 240 : 120;
        this.musicMuted = mgr.getMusicMuted();
        this.soundFXMuted = mgr.getSoundFXMuted();
        this.musicValue = mgr.getMusicValue();
        this.soundFXValue = mgr.getSoundFXValue();
        this.maximized = isMaximized;

        // trivial initialization
        displayIcon = new ImageView();
        title = new Label("Settings");
        musicTitle = new Label("Music");
        soundFXTitle = new Label("Sound Effects");
        backButton = new Button("Back");
        applyButton = new Button("Apply");
        discardButton = new Button("Discard");
        backButton.getStyleClass().add(backStyle);
        applyButton.getStyleClass().add(bigButtonStyle);
        discardButton.getStyleClass().add(bigButtonStyle);

        // set settings title style
        title.getStyleClass().add(titleStyle);

        // set sound setting labels style
        musicTitle.getStyleClass().add(labelStyle);
        soundFXTitle.getStyleClass().add(labelStyle);

        // initialize buttons for changing display
        borderedButton = new Button("Bordered Window");
        maximizedButton = new Button("Maximized");
        borderedButton.getStyleClass().add(displayStyle);
        maximizedButton.getStyleClass().add(displayStyle);
        // set one to disabled
        if (maximized) {
            maximizedButton.setDisable(true);
        } else {
            borderedButton.setDisable(true);
        }

        // initialize music slider
        musicSlider = new Slider(0, 100, musicValue);
        musicSlider.setPrefSize(sliderW, sliderH);
        musicSlider.setShowTickMarks(true);
        musicSlider.setMajorTickUnit(25.0);
        musicSlider.setBlockIncrement(1.0);
        // add change listener
        musicSlider.valueProperty().addListener((observableValue, number, t1) -> musicValue = t1.doubleValue());

        // initialize soundFX slider
        soundFXSlider = new Slider(0, 100, soundFXValue);
        soundFXSlider.setPrefSize(sliderW, sliderH);
        soundFXSlider.setShowTickMarks(true);
        soundFXSlider.setMajorTickUnit(25.0);
        soundFXSlider.setBlockIncrement(1.0);
        // add change listener
        soundFXSlider.valueProperty().addListener((observableValue, number, t1) -> soundFXValue = t1.doubleValue());

        // initialize display icon
        String imgPath = maximized ? "img\\maximized_icon.png" : "img\\windowed_icon.png";
        Image img = new Image(imgPath);
        displayIcon.setImage(img);
        displayIcon.setPreserveRatio(true);
        displayIcon.setFitHeight(iconSize);

        // set graphics for sound mute buttons
        String musicImgPath = musicMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        String soundFXImgPath = soundFXMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        Image musicImg = new Image(musicImgPath);
        Image soundFXImg = new Image(soundFXImgPath);
        soundFXIcon = new ImageView();
        musicIcon = new ImageView();
        soundFXIcon.setPickOnBounds(false);
        musicIcon.setPickOnBounds(false);
        soundFXIcon.setImage(soundFXImg);
        soundFXIcon.setPreserveRatio(true);
        soundFXIcon.setFitHeight(iconSize / 4);
        musicIcon.setImage(musicImg);
        musicIcon.setPreserveRatio(true);
        musicIcon.setFitHeight(iconSize / 4);
        musicIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            musicMuted = !musicMuted;
            mgr.playButtonSound();
            update();
            event.consume();
        });
        soundFXIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            soundFXMuted = !soundFXMuted;
            mgr.playButtonSound();
            update();
            event.consume();
        });
        // create the layout
        root = new VBox();
        root.setPrefSize(width,height);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(40);

        HBox top = new HBox(backButton);
        top.setAlignment(Pos.CENTER_LEFT);

        VBox soundSettings = new VBox();
        soundSettings.setAlignment(Pos.TOP_CENTER);
        soundSettings.setSpacing(10);

        HBox musicSettings = new HBox();
        musicSettings.setAlignment(Pos.TOP_CENTER);
        VBox musicControl = new VBox();
        musicControl.setAlignment(Pos.TOP_CENTER);
        musicControl.getChildren().addAll(musicTitle, musicSlider);
        musicSettings.getChildren().addAll(musicIcon, musicControl);

        HBox soundFXSettings = new HBox();
        soundFXSettings.setAlignment(Pos.TOP_CENTER);
        VBox soundFXControl = new VBox();
        soundFXControl.getChildren().addAll(soundFXTitle, soundFXSlider);
        soundFXControl.setAlignment(Pos.TOP_CENTER);
        soundFXSettings.getChildren().addAll(soundFXIcon, soundFXControl);

        soundSettings.getChildren().addAll(musicSettings, soundFXSettings);

        HBox displaySettings = new HBox();
        displaySettings.setAlignment(Pos.TOP_CENTER);
        displaySettings.setSpacing(20);

        VBox displayControls = new VBox(borderedButton,maximizedButton);
        displayControls.setAlignment(Pos.TOP_CENTER);
        displayControls.setSpacing(5);
        displaySettings.getChildren().addAll(displayIcon, displayControls);

        HBox controlButtons = new HBox(applyButton, discardButton);
        controlButtons.setAlignment(Pos.BOTTOM_CENTER);
        controlButtons.setSpacing(40);

        root.getChildren().addAll(top, title, soundSettings, displaySettings, controlButtons);


        scene = new Scene(root,width,height);
    }

    public void addHandlers(){
        backButton.setOnAction(mgr);
        borderedButton.setOnAction(this);
        maximizedButton.setOnAction(this);
        applyButton.setOnAction(this);
        discardButton.setOnAction(this);
    }

    public void applyChanges(){
        mgr.setMusicMuted(musicMuted);
        mgr.setSoundFXMuted(soundFXMuted);
        mgr.setSoundFXValue(soundFXValue);
        mgr.setMusicValue(musicValue);
        if(mgr.getMaximized() != maximized) mgr.setMaximized(maximized);
        mgr.viewSettings();
    }

    public void discardChanges(){
        mgr.viewSettings();
    }
}

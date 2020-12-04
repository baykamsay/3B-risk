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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SettingsMenu extends Application implements MenuState, EventHandler<ActionEvent> {

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
    final String style_big =  style_base +
            "    -fx-pref-width: 200px;" +
            "    -fx-pref-height: 50px; "+
            "}";
    final String style_display = style_base +
            "    -fx-pref-width: 300px;" +
            "    -fx-pref-height: 30px; "+
            "}";
    final String style_back = " -fx-background-color: " +
            "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
            "        linear-gradient(#020b02, #3a3a3a)," +
            "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%)," +
            "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);" +
            "}";
    final String style_sound = "    -fx-pref-width: 200px;" +
            "    -fx-pref-height: 50px; "+
            "    -fx-background-color: transparent;" +
            "    -fx-border-color: transparent;" +
            "}";
    Scene scene;
    private int width, height;
    private GameMenuManager mgr;
    private Button backButton;
    private Label title, musicTitle, soundFXTitle;
    private Slider musicSlider;
    private Slider soundFXSlider;
    private boolean musicMuted, soundFXMuted;
    private Button muteMusicButton, muteSoundFXButton;
    private double musicValue, soundFXValue;
    private boolean maximized;
    private Button borderedButton, maximizedButton;
    private Button applyButton, discardButton;
    private ImageView displayIcon;
    private ImageView musicIcon, soundFXIcon;
    private VBox root;

    //Change this when moving to stylesheets
    private String backgroundPath;

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

        if( e.getSource() == muteMusicButton){
            musicMuted = !musicMuted;
            update();
        } else if (e.getSource() == muteSoundFXButton){
            soundFXMuted = !soundFXMuted;
            update();
        } else if (e.getSource() == borderedButton){
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
        this.musicMuted = mgr.getMusicMuted();
        this.soundFXMuted = mgr.getSoundFXMuted();
        this.musicValue = mgr.getMusicValue();
        this.soundFXValue = mgr.getSoundFXValue();
        this.maximized = isMaximized;
        backgroundPath = maximized ? "-fx-background-image: url(\"img/bg_bilkent.png\"); -fx-background-size: cover;" : "-fx-background-image: url(\"img/bg_bilkent.png\");";

        // trivial initialization
        displayIcon = new ImageView();
        title = new Label("Settings");
        musicTitle = new Label("Music");
        soundFXTitle = new Label("Sound Effects");
        backButton = new Button("Back");
        applyButton = new Button("Apply");
        discardButton = new Button("Discard");
        muteMusicButton = new Button();
        muteSoundFXButton = new Button();
        backButton.setStyle(style_back);
        applyButton.setStyle(style_big);
        discardButton.setStyle(style_big);
        muteMusicButton.setStyle(style_sound);
        muteSoundFXButton.setStyle(style_sound);

        // set settings title style
        title.setFont(new Font("Helvetica", 30));
        title.setStyle("-fx-font-weight: bold");

        // set sound setting labels style
        musicTitle.setFont(new Font("Helvetica", 15));
        musicTitle.setStyle("-fx-font-weight: bold");
        soundFXTitle.setFont(new Font("Helvetica", 15));
        soundFXTitle.setStyle("-fx-font-weight: bold");

        // initialize buttons for changing display
        borderedButton = new Button("Bordered Window");
        maximizedButton = new Button("Maximized");
        borderedButton.setStyle(style_display);
        maximizedButton.setStyle(style_display);
        // set one to disabled
        if (maximized) {
            maximizedButton.setDisable(true);
        } else {
            borderedButton.setDisable(true);
        }

        // initialize music slider
        musicSlider = new Slider(0, 100, musicValue);
        musicSlider.setPrefSize(200, 20);
        musicSlider.setShowTickMarks(true);
        musicSlider.setMajorTickUnit(25.0);
        musicSlider.setBlockIncrement(1.0);
        //musicSlider.setStyle(".slider .axis .axis-tick-mark {  -fx-fill: null; -fx-stroke: red; }");
        // add change listener
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                musicValue = t1.doubleValue();
            }
        });

        // initialize soundFX slider
        soundFXSlider = new Slider(0, 100, soundFXValue);
        soundFXSlider.setPrefSize(200, 20);
        soundFXSlider.setShowTickMarks(true);
        soundFXSlider.setMajorTickUnit(25.0);
        soundFXSlider.setBlockIncrement(1.0);
        //soundFXSlider.setStyle(".slider .axis .axis-tick-mark {  -fx-fill: null; -fx-stroke: red; }");
        // add change listener
        soundFXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                soundFXValue = t1.doubleValue();
            }
        });

        // initialize display icon
        String imgPath = maximized ? "img\\maximized_icon.png" : "img\\windowed_icon.png";
        Image img = new Image(imgPath);
        displayIcon.setImage(img);
        displayIcon.setPreserveRatio(true);
        displayIcon.setFitHeight(120);

        // set graphics for sound mute buttons
        String musicImgPath = musicMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        String soundFXImgPath = soundFXMuted ? "img\\sound_icon_muted.png" : "img\\sound_icon.png";
        Image musicImg = new Image(musicImgPath);
        Image soundFXImg = new Image(soundFXImgPath);
        soundFXIcon = new ImageView();
        musicIcon = new ImageView();
        soundFXIcon.setImage(soundFXImg);
        soundFXIcon.setPreserveRatio(true);
        soundFXIcon.setFitHeight(50);
        musicIcon.setImage(musicImg);
        musicIcon.setPreserveRatio(true);
        musicIcon.setFitHeight(50);
        muteSoundFXButton.setGraphic(soundFXIcon);
        muteMusicButton.setGraphic(musicIcon);

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
        musicSettings.getChildren().addAll(muteMusicButton, musicControl);

        HBox soundFXSettings = new HBox();
        soundFXSettings.setAlignment(Pos.TOP_CENTER);
        VBox soundFXControl = new VBox();
        soundFXControl.getChildren().addAll(soundFXTitle, soundFXSlider);
        soundFXControl.setAlignment(Pos.TOP_CENTER);
        soundFXSettings.getChildren().addAll(muteSoundFXButton, soundFXControl);

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

        root.setStyle(backgroundPath);

        scene = new Scene(root,width,height);
    }

    public void addHandlers(){
        backButton.setOnAction(mgr);
        muteMusicButton.setOnAction(this);
        muteSoundFXButton.setOnAction(this);
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
        System.out.println("currently " + musicValue);
        mgr.setMaximized(maximized);
        mgr.viewSettings();
    }

    public void discardChanges(){
        mgr.viewSettings();
    }
}

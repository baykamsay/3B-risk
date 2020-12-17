package menu;

import game.SoundEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

import java.net.URISyntaxException;


public class PauseSettingsMenu implements EventHandler<ActionEvent>{

    private Scene scene;
    private final int width, height;
    private Button backButton;
    private Label title, musicTitle, soundFXTitle;
    private Slider musicSlider;
    private Slider soundFXSlider;
    private boolean musicMuted, soundFXMuted;
    private double musicValue, soundFXValue;
    private Button applyButton, discardButton;
    private ImageView musicIcon, soundFXIcon;
    private VBox root;
    private SoundEngine soundEngine;
    private PauseMenu pauseMenu;

    public PauseSettingsMenu(int width, int height, PauseMenu pauseMenu){
        this.width = width;
        this.height = height;
        this.soundEngine = SoundEngine.getInstance();
        this.pauseMenu = pauseMenu;
    }

    @Override
    public void handle(ActionEvent e) {
        if( e.getSource() instanceof Button){
            soundEngine.playButtonSound();
        }

        if(e.getSource() == backButton){
            pauseMenu.back();
        } else if (e.getSource() == applyButton){
            applyChanges();
        } else if (e.getSource() == discardButton){
            discardChanges();
        }
    }

    public void update() {

        String musicImgPath = musicMuted ? "sound_icon_muted.png" : "sound_icon.png";
        String soundFXImgPath = soundFXMuted ? "sound_icon_muted.png" : "sound_icon.png";


        try {
            Image musicImg = new Image(Launcher.class.getResource("/img/" + musicImgPath).toURI().toString());
            Image soundFXImg = new Image(Launcher.class.getResource("/img/" + soundFXImgPath).toURI().toString());
            musicIcon.setImage(musicImg);
            soundFXIcon.setImage(soundFXImg);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Scene createScene() {
        try {
            init();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        addHandlers();
        return scene;
    }

    public void init() throws URISyntaxException {
        String bigButtonStyle = "menu_button_max" ;
        String labelStyle = "label_max";
        String titleStyle = "title_max";
        int sliderW = 400;
        int sliderH = 40;
        int iconSize = 240;
        this.musicMuted = soundEngine.isMusicMuted();
        this.soundFXMuted = soundEngine.isSoundFXMuted();
        this.musicValue = soundEngine.getMusicVolume();
        this.soundFXValue = soundEngine.getSoundFXVolume();

        // trivial initialization
        title = new Label("Settings");
        musicTitle = new Label("Music");
        soundFXTitle = new Label("Sound Effects");
        backButton = new Button("Back");
        applyButton = new Button("Apply Changes");
        discardButton = new Button("Discard Changes");
        backButton.getStyleClass().add(bigButtonStyle);
        applyButton.getStyleClass().add(bigButtonStyle);
        discardButton.getStyleClass().add(bigButtonStyle);

        // set settings title style
        title.getStyleClass().add(titleStyle);

        // set sound setting labels style
        musicTitle.getStyleClass().add(labelStyle);
        soundFXTitle.getStyleClass().add(labelStyle);

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

        // set graphics for sound mute buttons
        String musicImgPath = musicMuted ? "sound_icon_muted.png" : "sound_icon.png";
        String soundFXImgPath = soundFXMuted ? "sound_icon_muted.png" : "sound_icon.png";
        Image musicImg = new Image(Launcher.class.getResource("/img/" + musicImgPath).toURI().toString() );
        Image soundFXImg = new Image(Launcher.class.getResource("/img/" + soundFXImgPath).toURI().toString());
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
            soundEngine.playButtonSound();
            update();
            event.consume();
        });
        soundFXIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            soundFXMuted = !soundFXMuted;
            soundEngine.playButtonSound();
            update();
            event.consume();
        });
        // create the layout
        root = new VBox();
        root.setPrefSize(width,height);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(40);

        HBox bottom = new HBox(backButton);
        bottom.setPadding(new Insets(5));
        bottom.setAlignment(Pos.CENTER);

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

        HBox navigationButtons = new HBox(applyButton, discardButton);
        navigationButtons.setAlignment(Pos.BOTTOM_CENTER);
        navigationButtons.setSpacing(40);

        root.getChildren().addAll( title, soundSettings, navigationButtons, bottom);

        root.setId("menu_bg");
        scene = new Scene(root,width,height);
    }

    public void addHandlers(){
        backButton.setOnAction(this);
        applyButton.setOnAction(this);
        discardButton.setOnAction(this);
    }

    public void applyChanges(){
        soundEngine.setMusicMuted(musicMuted);
        soundEngine.setSoundFXMuted(soundFXMuted);
        soundEngine.setSoundFXVolume(soundFXValue);
        soundEngine.setMusicVolume(musicValue);
    }

    private void discardChanges() {
        pauseMenu.openSettings();
    }

}

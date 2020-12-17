package menu;

import game.SoundEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URISyntaxException;

public class PauseHelpMenu implements EventHandler<ActionEvent> {

    private ImageView helpIcon;
    private final ImageView helpPage;
    private Label title;
    private Button backButton;
    private Button next;
    private Button previous;
    private int pageNo;
    private final int width, height;
    private PauseMenu pauseMenu;
    private SoundEngine soundEngine;
    private Scene scene;

    private final int NO_OF_PAGES = 3;

    public PauseHelpMenu(int width, int height, PauseMenu pauseMenu){
        pageNo = 1;
        this.soundEngine = SoundEngine.getInstance();
        this.pauseMenu = pauseMenu;
        helpPage = new ImageView();
        this.width = width;
        this.height = height;
    }

    public void init() throws URISyntaxException {
        String bigButtonStyle = "menu_button_max";
        String titleStyle = "title_max";
        int imgSize = 600;

        //initialize components
        Image img = new Image(Launcher.class.getResource("/img/help_icon.png").toURI().toString());
        helpIcon = new ImageView(img);
        helpIcon.setPreserveRatio(true);
        helpIcon.setFitWidth(imgSize / 5);

        Image img2 = new Image(Launcher.class.getResource("/img/help1.png").toURI().toString());
        helpPage.setImage(img2);
        helpPage.setPreserveRatio(true);
        helpPage.setFitWidth(imgSize);

        backButton = new Button("Back");
        backButton.getStyleClass().add(bigButtonStyle);

        next = new Button("next");
        previous = new Button("previous");
        next.getStyleClass().add(bigButtonStyle);
        previous.getStyleClass().add(bigButtonStyle);
        initButtons();

        title = new Label("How to Play RISK101", helpIcon);
        title.getStyleClass().add(titleStyle);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        HBox bottom = new HBox(backButton);
        bottom.setPadding(new Insets(5));
        bottom.setAlignment(Pos.CENTER);

        //the main layout
        BorderPane menu = new BorderPane();

        //layout for the navigation bar
        HBox nav = new HBox(previous,next);
        nav.setAlignment(Pos.CENTER);
        nav.setSpacing(20);

        menu.setTop(title);
        menu.setCenter(helpPage);
        menu.setBottom(nav);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(nav, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(10,10,10,10));
        BorderPane.setMargin(nav, new Insets(10,10,10,10));
        root.getChildren().addAll(menu, bottom);
        GridPane.setHalignment(menu,HPos.CENTER);
        root.setId("menu_bg");
        scene = new Scene(root,width,height);
    }

    public void update() {
        Image img2 = null;
        try {
            img2 = new Image(Launcher.class.getResource("/img/help" + pageNo + ".png").toURI().toString() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        helpPage.setImage(img2);
    }

    public Scene createScene() {
        try {
            init();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        initButtons();
        return scene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        soundEngine.playButtonSound();
        if(actionEvent.getSource() == next){
            if(pageNo < NO_OF_PAGES){
                pageNo++;
            }
        }else if (actionEvent.getSource() == previous){
            if(pageNo > 1){
                pageNo--;
            }
        }else if (actionEvent.getSource() == backButton){
            pauseMenu.back();
        }

        this.update();
    }

    public void initButtons(){
        backButton.setOnAction(this);
        previous.setOnAction(this);
        next.setOnAction(this);
    }
}

package menu;

import javafx.application.Application;
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
import javafx.stage.Stage;

public class HelpMenu extends Application implements MenuState, EventHandler<ActionEvent> {

    private ImageView helpIcon;
    private final ImageView helpPage;
    private Label title;
    private Button backButton;
    private Button next;
    private Button previous;
    private int pageNo;
    private final int width, height;
    private GameMenuManager mgr;
    Scene scene;

    private final int NO_OF_PAGES = 3;

    public HelpMenu(int width, int height){
        pageNo = 1;
        mgr = null;
        helpPage = new ImageView();
        this.width = width;
        this.height = height;
    }

    public void init(boolean maximized){
        String backStyle = mgr.getMaximized() ? "menu_button_back_max" : "menu_button_back_min";
        String bigButtonStyle = maximized ? "menu_button_max" : "menu_button_min";
        String titleStyle = mgr.getMaximized() ? "title_max" : "title_min";
        int imgSize = maximized ? 600 : 400;

        //initialize components
        Image img = new Image("img\\help_icon.png");
        helpIcon = new ImageView(img);
        helpIcon.setPreserveRatio(true);
        helpIcon.setFitWidth(imgSize / 5);

        Image img2 = new Image("img\\help1.png");
        helpPage.setImage(img2);
        helpPage.setPreserveRatio(true);
        helpPage.setFitWidth(imgSize);

        backButton = new Button("Back");
        backButton.getStyleClass().add(backStyle);

        next = new Button("next");
        previous = new Button("previous");
        next.getStyleClass().add(bigButtonStyle);
        previous.getStyleClass().add(bigButtonStyle);
        initButtons();

        title = new Label("How to Play RISK101", helpIcon);
        title.getStyleClass().add(titleStyle);
        VBox root = new VBox();

        HBox top = new HBox(backButton);
        top.setPadding(new Insets(5));
        top.setAlignment(Pos.CENTER_LEFT);

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
        root.getChildren().addAll(top,menu);
        GridPane.setHalignment(menu,HPos.CENTER);
        root.setId("menu_bg");
        scene = new Scene(root,width,height);
    }

    @Override
    public void update() {
        Image img2 = new Image("img\\help" + pageNo + ".png");
        helpPage.setImage(img2);
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        this.init(mgr.getMaximized());
        initButtons();
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        mgr.playButtonSound();
        if(actionEvent.getSource() == next){
            if(pageNo < NO_OF_PAGES){
                pageNo++;
            }
        }else if (actionEvent.getSource() == previous){
            if(pageNo > 1){
                pageNo--;
            }
        }

        this.update();
    }

    public void initButtons(){
        backButton.setOnAction(mgr);
        previous.setOnAction(this);
        next.setOnAction(this);
    }
}

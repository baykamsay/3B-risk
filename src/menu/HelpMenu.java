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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpMenu extends Application implements MenuState, EventHandler<ActionEvent> {

    private ImageView helpIcon;
    private ImageView helpPage;
    private Label title;
    private Button backButton;
    private Button next;
    private Button previous;
    private int pageNo;
    private int width;
    private int height;
    private GameMenuManager mgr;
    Scene scene;

    private final int NO_OF_PAGES = 3;
    final String traversalStyle = "";

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

    final String style_back = " -fx-background-color: " +
            "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
            "        linear-gradient(#020b02, #3a3a3a)," +
            "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%)," +
            "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);" +
            "}";

    public HelpMenu(int width, int height){
        pageNo = 1;
        mgr = null;
        helpPage = new ImageView();
        this.width = width;
        this.height = height;
        this.init();
    }

    public void init(){
        //initialize components
        Image img = new Image("help_icon.png");
        helpIcon = new ImageView(img);

        Image img2 = new Image("help1.png");
        helpPage.setImage(img2);

        backButton = new Button("Back");
        backButton.setStyle(style_back);

        next = new Button("next");
        previous = new Button("previous");
        next.setStyle(style_big);
        previous.setStyle(style_big);
        initButtons();

        title = new Label("How to Play RISK101", helpIcon);
        title.setFont(new Font("Helvetica", 35));
        title.setStyle("-fx-font-weight: bold");
        VBox root = new VBox();

        HBox top = new HBox(backButton);
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
        root.setStyle("-fx-background-image: url(\"bg_bilkent.png\");");
        scene = new Scene(root,width,height);
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
        Image img2 = new Image("help" + pageNo + ".png");
        helpPage.setImage(img2);
    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        this.mgr = mgr;
        initButtons();
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("clicked " + actionEvent.getSource());
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
        mgr.changeScene(this.scene);
    }

    public void initButtons(){
        backButton.setOnAction(mgr);
        previous.setOnAction(this);
        next.setOnAction(this);
    }
}

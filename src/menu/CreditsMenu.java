package menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreditsMenu extends Application implements MenuState{

    private Label title;
    private ImageView logo;
    private Label members;
    private Button back;
    private Scene scene;
    private int width, height;
    private GameMenuManager mgr;

    //Change this when moving to stylesheets
    private String backgroundPath;

    final String style_back = " -fx-background-color: " +
            "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
            "        linear-gradient(#020b02, #3a3a3a)," +
            "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%)," +
            "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);" +
            "}";

    public CreditsMenu(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void update() {

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        init(mgr.getMaximized());
        this.mgr = mgr;
        back.setOnAction(mgr);
        return scene;
    }

    public void init(boolean maximized){
        backgroundPath = maximized ? "-fx-background-image: url(\"bg_bilkent.png\"); -fx-background-size: cover;" : "-fx-background-image: url(\"bg_bilkent.png\");";
        title = new Label("Team Members");
        title.setFont(new Font("Helvetica", 40));
        title.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
        members = new Label("Aleyna Sütbaş\n" +
                "Berk Takıt\n" +
                "Ramazan Melih Diksu\n" +
                "Baykam Say\n" +
                "Yiğit Erkal");
        members.setFont(new Font("Helvetica", 28));
        members.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
        Image img = new Image("bilkent_logo.png");
        logo = new ImageView(img);
        back = new Button("Back");
        back.setStyle(style_back);

        HBox top = new HBox(back);
        top.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox();

        VBox bottom = new VBox(title, members, logo);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(20);
        root.getChildren().addAll(top,bottom);
        root.setSpacing(20);
        root.setStyle(backgroundPath);
        scene = new Scene(root,width,height);
    }
}

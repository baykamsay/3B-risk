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
import javafx.stage.Stage;

public class CreditsMenu extends Application implements MenuState{

    private Label title;
    private ImageView logo;
    private Label members;
    private Button back;
    private Scene scene;
    private final int width, height;


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
        back.setOnAction(mgr);
        return scene;
    }

    public void init(boolean maximized){
        String backStyle = maximized ? "menu_button_back_max" : "menu_button_back_min";
        String titleStyle = maximized ? "title_max" : "title_min";
        String labelStyle = maximized ? "label_max" : "label_min";

        title = new Label("Team Members");
        title.getStyleClass().add(titleStyle);
        members = new Label("""
                Aleyna Sütbaş
                Berk Takıt
                Ramazan Melih Diksu
                Baykam Say
                Yiğit Erkal""");
        members.getStyleClass().add(labelStyle);
        Image img = new Image("img\\bilkent_logo.png");
        logo = new ImageView(img);
        back = new Button("Back");
        back.getStyleClass().add(backStyle);

        HBox top = new HBox(back);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setPadding(new Insets(5));

        VBox root = new VBox();
        root.setId("menu_bg");
        VBox bottom = new VBox(title, members, logo);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(20);
        root.getChildren().addAll(top,bottom);
        root.setSpacing(20);

        scene = new Scene(root,width,height);
    }
}

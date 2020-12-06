package menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application implements MenuState {
    private static final int NO_OF_OPTIONS = 6;
    private static final String[] BUTTON_NAMES = {"New Game", "Load Game", "Help", "Settings", "Credits", "Exit"};
    private final Button[] options = new Button[NO_OF_OPTIONS];
    private ImageView logo;
    private Scene scene;
    private final int width, height;

    public MainMenu(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        init(mgr.getMaximized());

        for( Button b : options){
            b.setOnAction(mgr);
        }
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void init(boolean maximized){
        String bigButtonStyle = maximized ? "menu_button_max" : "menu_button_min";
        String longButtonStyle = maximized ? "menu_button_long_max" : "menu_button_long_min";
        //initialize buttons
        String buttonLabel;
        for(int i = 0; i < NO_OF_OPTIONS; i++){
            buttonLabel = i < NO_OF_OPTIONS ? BUTTON_NAMES[i] : "";
            options[i] = new Button(buttonLabel);
            if(i < 2){
                options[i].getStyleClass().add(bigButtonStyle);
            }
            else{
                options[i].getStyleClass().add(longButtonStyle);
            }
        }
        //main menu logo
        try {
            String logoPath = maximized ? "img\\logo_600.png" : "img\\logo_400.png";
            Image img = new Image(logoPath);
            logo = new ImageView(img);
            logo.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //top part of menu
        VBox top = new VBox(options[0],options[1]);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        // bottom part of menu
        GridPane bottom = new GridPane();
        bottom.setVgap(5);
        bottom.setHgap(5);
        bottom.setAlignment(Pos.CENTER);
        bottom.add(options[2], 0, 0);
        bottom.add(options[3], 1, 0);
        bottom.add(options[4], 0, 1);
        bottom.add(options[5], 1, 1);

        //whole of the menu
        VBox menu = new VBox(logo, top, bottom);
        menu.setSpacing(10);
        menu.setPadding(new Insets(10,10,10,10));
        menu.setAlignment(Pos.CENTER);
        menu.setFillWidth(true);

        scene = new Scene(menu,width,height);
    }
}

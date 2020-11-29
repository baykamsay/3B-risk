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
    Scene scene;
    private int width, height;
    //TO-DO: Move the styles to a stylesheet
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

    final String style_small = style_base +
            "    -fx-pref-width: 250px;" +
            "    -fx-pref-height: 30px; "+
            "}";

    public MainMenu(int width, int height){
        this.width = width;
        this.height = height;
        init();
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

    }

    @Override
    public Scene createScene(GameMenuManager mgr) {
        for( Button b : options){
            b.setOnAction(mgr);
        }
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void init(){
        //initialize buttons
        String buttonLabel;
        for(int i = 0; i < NO_OF_OPTIONS; i++){
            buttonLabel = i < NO_OF_OPTIONS ? BUTTON_NAMES[i] : "";
            System.out.println(buttonLabel);
            options[i] = new Button(buttonLabel);
            // TO-DO: make buttons BEAUTIFUL
            if(i < 2){
                options[i].setStyle(style_big);
            }
            else{
                options[i].setStyle(style_small);
            }
        }
        //main menu logo
        try {
            Image img = new Image("logo.png");
            logo = new ImageView(img);
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

        menu.setStyle("-fx-background-image: url(\"bg_bilkent.png\");");
        scene = new Scene(menu,width,height);
    }
}

package game.scene;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import menu.GameMenuManager;
import menu.MenuState;

public class GameOverScene<GameEngine> extends Application implements MenuState, EventHandler<ActionEvent> {
    private Scene scene;
    private Button returnButton;
    private Text gameOver;
    private Text winnerName;
    private Text winAnnouncement;
    private GameMenuManager mgr;
    private GameEngine engine;
    private int width;
    private int height;

    private String backgroundPath;

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

    public GameOverScene(int width, int height){
        engine = NULL;
        mgr = NULL;
        this.width = width;
        this.height = height;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //
    }


    @Override
    public void update() {
        //
    }


    @Override
    public Scene createScene(GameMenuManager mgr, GameEngine engine) {
        this.mgr = mgr;
        this.engine = engine;
        init(engine.isGameOver(), mgr.getMaximized(), engine.getWinner());
        returnButton.setOnAction(mgr);
        return scene;
    }


    public void init(boolean isGameOver, boolean maximized, Player winner){
        if (isGameOver){
            backgroundPath = maximized ? "-fx-background-image: url(\"img/bg_bilkent.png\"); -fx-background-size: cover;" : "-fx-background-image: url(\"img/bg_bilkent.png\");";

            returnButton = new Button("Return to Main Menu");
            returnButton.setStyle(style_big);

            gameOver = new Text("Game Over\n");
            gameOver.setFont(Font.font ("Verdana", 20)); //Temporarily
            gameOver.setFill(Color.BLACK);                 //Temporarily

            winnerName = new Text(winner.name);
            winnerName.setFont(Font.font ("Verdana", 16));  //Temporarily

            if(winner.color == "red" ) {
                winnerName.setFill(Color.RED);
            }
            else if(winner.color == "blue" ) {
                winnerName.setFill(Color.BLUE);
            }
            else if(winner.color == "green" ) {
                winnerName.setFill(Color.GREEN);
            }
            else if(winner.color == "yellow" ) {
                winnerName.setFill(Color.YELLOW);
            }

            winAnnouncement = new Text(" is the winner!\n");
            winAnnouncement.setFont(Font.font ("Verdana", 20)); //Temporarily
            winAnnouncement.setFill(Color.BLACK);                     //Temporarily

            VBox root = new VBox();

            TextFlow txtFlowPane = new TextFlow();
            txtFlowPane.setTextAlignment(TextAlignment.JUSTIFY);
            txtFlowPane.setPrefSize(600, 300);  //Correct numbers will be given after testing
            txtFlowPane.setLineSpacing(5.0);

            txtFlowPane.getChildren().addAll(gameOver, winnerName, winAnnouncement);
            root.getChildren().addAll(txtFlowPane, returnButton);
            root.setSpacing(20);
            root.setStyle(backgroundPath);
            scene = new Scene(root,width,height);
        }
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        //
    }
}


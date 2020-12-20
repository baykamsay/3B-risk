package game.scene;

import game.GameEngine;
import game.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverSceneController implements Initializable {

    @FXML private Label winnerName;
    @FXML private ImageView winnerIcon, trophyL, trophyR;
    @FXML private Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            trophyL.setImage(new Image(getClass().getResource("/img/trophy.png").toURI().toString()));
            trophyR.setImage(new Image(getClass().getResource("/img/trophy.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        returnButton.setOnAction(actionEvent -> GameEngine.getInstance().gameEnd());
        Player winner = GameEngine.getInstance().getWinner();
        String path = winner.getFaculty().getIconName();
        path = path.substring(0, path.length() - 4) + "_500.png";
        winnerName.setText(winner.getFaculty().getName());
        try {
            winnerIcon.setImage(new Image(getClass().getResource(path).toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

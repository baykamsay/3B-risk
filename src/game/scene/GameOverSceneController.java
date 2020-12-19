package game.scene;

import game.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverSceneController implements Initializable {

    @FXML private Label winnerName;
    @FXML private ImageView winnerIcon, trophyL, trophyR;
    @FXML private Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setWinner(Player p) {

    }
}

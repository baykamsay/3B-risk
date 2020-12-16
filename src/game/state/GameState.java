package game.state;

import javafx.event.ActionEvent;

public interface GameState {
    void mapSelect(ActionEvent e);
    void start();
}

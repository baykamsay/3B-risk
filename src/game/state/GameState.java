package game.state;

import javafx.event.ActionEvent;

public interface GameState {
    void mapSelect(int territory);
    void start();
}

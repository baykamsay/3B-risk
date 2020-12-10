package game.state;

import javafx.scene.Scene;

import javafx.event.ActionEvent;

public interface GameState {
    void update();
    Scene createScene();
    void mapSelect(ActionEvent e);
}

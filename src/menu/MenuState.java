package menu;

import javafx.scene.Scene;

public interface MenuState {
    void update();
    Scene createScene(GameMenuManager mgr);
}

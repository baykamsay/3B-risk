package menu;

import javafx.scene.Scene;

public interface MenuState {
    public void update();
    public Scene createScene(GameMenuManager mgr);
}

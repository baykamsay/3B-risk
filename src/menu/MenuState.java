package menu;

import javafx.scene.Scene;

public interface MenuState {
    public void pause();
    public void resume();
    public void terminating();
    public void update();
    public Scene createScene(GameMenuManager mgr);
}

package game.state;

import javafx.event.ActionEvent;

public class FortifyingState implements GameState {
    private static FortifyingState instance;

    private FortifyingState() {}

    public static FortifyingState getInstance() {
        if (instance == null) {
            synchronized (FortifyingState.class) {
                if (instance == null) {
                    instance = new FortifyingState();
                }
            }
        }
        return instance;
    }

    @Override
    public void mapSelect(ActionEvent e) {

    }
}

package game.player;

public class CaptureObjective implements ObjectiveStrategy {
    @Override
    public boolean isDone(Territory[] territories) {
        return false;
    }
}

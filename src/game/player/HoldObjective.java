package game.player;

public class HoldObjective implements ObjectiveStrategy{
    @Override
    public boolean isDone(Territory[] territories) {
        return false;
    }
}

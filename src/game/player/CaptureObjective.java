package game.player;

public class CaptureObjective implements ObjectiveStrategy {

    @Override
    public int isDone(Objective objective) {
        Territory target = (Territory) objective.target;
        if (objective.currentTurn >= objective.turnLimit)
            return -1;
        if (target.isRuler(objective.player))
            return 1;
        return 0;
    }
}

package game.player;

public class CaptureObjective implements ObjectiveStrategy {

    @Override
    public boolean isDone(Objective objective) {
        Territory target = (Territory) objective.target;
//        objective.currentTurn++; // does not work with areas increment elsewhere
        return target.isRuler(objective.player) && objective.currentTurn < objective.turnLimit;
    }
}

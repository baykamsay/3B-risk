package game.player;

public class HoldObjective implements ObjectiveStrategy{

    @Override
    public boolean isDone(Objective objective) {
        Territory target = (Territory) objective.target;

        // does not reset if territory is taken?
        if (!target.isRuler(objective.player)) {
            return false;
        }
//        objective.currentTurn++; // does not work with areas increment elsewhere
        return objective.currentTurn == objective.turnLimit;
    }
}

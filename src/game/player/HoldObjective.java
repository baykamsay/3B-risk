package game.player;

public class HoldObjective implements ObjectiveStrategy{

    @Override
    public int isDone(Objective objective) {
        Territory target = (Territory) objective.target;

        // does not reset if territory is taken?
        if (!target.isRuler(objective.player)) {
            return -1;
        }
        if (objective.currentTurn < objective.turnLimit) {
            return 0;
        }
        return 1;
    }
}

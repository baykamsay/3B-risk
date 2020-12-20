package game.player;

public class AreaDecorator implements ObjectiveStrategy {

    private ObjectiveStrategy strategy;

    public AreaDecorator(ObjectiveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public int isDone(Objective objective) {
        Place originalTarget = objective.target;
        Area target = (Area) objective.target;
        int result = 1;
        for (Territory territory : target.getTerritories()) {
            objective.target = territory;
            int calc = this.strategy.isDone(objective);
            if (calc == -1) {
                return -1;
            } else if (calc == 0) {
                result = 0;
            }
        }
        objective.target = originalTarget;
        return result;
    }
}

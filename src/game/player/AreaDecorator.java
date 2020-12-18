package game.player;

public class AreaDecorator implements ObjectiveStrategy {

    private ObjectiveStrategy strategy;

    public AreaDecorator(ObjectiveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public boolean isDone(Objective objective) {
        Place originalTarget = objective.target;
        Area target = (Area) objective.target;
        boolean result = true;
        for (Territory territory : target.getTerritories()) {
            objective.target = territory;
            if (this.strategy.isDone(objective)) {
                result = false;
            }
        }
        objective.target = originalTarget;
        return result;
    }
}

package game.player;

public class TerritoryDecorator implements ObjectiveStrategy{

    private ObjectiveStrategy strategy;

    public TerritoryDecorator(ObjectiveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public boolean isDone(Objective objective) {
        return this.strategy.isDone(objective);
    }
}

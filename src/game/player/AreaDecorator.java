package game.player;

public class AreaDecorator implements ObjectiveStrategy {

    private ObjectiveStrategy strategy;

    AreaDecorator(ObjectiveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public boolean isDone(Territory[] territories) {
        return this.strategy.isDone(territories);
    }
}

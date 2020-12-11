package game.player;

public class AreaDecorator implements ObjectiveStrategy {

    ObjectiveStrategy strategy;

    AreaDecorator(ObjectiveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public boolean isDone(Territory[] territories) {
        return false;
    }
}

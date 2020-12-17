package game.player;


import game.player.faculties.Faculty;

public class Objective {

    private String name;
    private int turnLimit;
    private Place[] target;
    private ObjectiveStrategy strategy;

    Objective(ObjectiveStrategy strategy, Place[] target){
        this.strategy = strategy;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getTurnLimit(){
        return turnLimit;
    }
    public void setTurnLimit(int turnLimit){
        this.turnLimit = turnLimit;
    }

    public boolean isDone() {
        return strategy.isDone((Territory[])target);
    }
}

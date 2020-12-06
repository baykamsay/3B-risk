package game.player;

enum Faculty{
    ART, FAS, FEASS, FEDU, FEN, IBEF, LAW, MAN, MF, MSSF;
}

public class Player implements Objective {
    private Faculty faculty;
    private Objective currentObjective;
    private Objective[] accomplishedObjectives;

    Player(Faculty faculty){
        this.faculty = faculty;
    }
    private Faculty getFaculty(){
        return faculty;
    }
    private Objective getObjective(){
        return currentObjective;
    }

    //not finished yet.
    @Override
    public boolean isDone() {

        return false;
    }
}
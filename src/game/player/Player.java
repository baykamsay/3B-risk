package game.player;

enum Faculty{
    ART, FAS, FEASS, FEDU, FEN, IBEF, LAW, MAN, MF, MSSF;
}

public class Player implements Objective {
    private Faculty faculty;
    private Objective currentObjective;
    private Objective[] accomplishedObjectives;
    private String name;

    Player(Faculty faculty){
        this.faculty = faculty;
    }
    Player(Faculty faculty, String name){
        this.faculty = faculty;
        this.name = name;
    }
    public Faculty getFaculty(){
        return faculty;
    }
    public Objective getObjective(){
        return currentObjective;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    //not finished yet.
    @Override
    public boolean isDone() {

        return false;
    }
}
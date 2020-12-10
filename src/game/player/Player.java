package game.player;

enum Faculty{
    ART, FAS, FEASS, FEDU, FEN, IBEF, LAW, MAN, MF, MSSF;
}

public class Player {
    private Faculty faculty;
    private Objective currentObjective;
    private Objective[] accomplishedObjectives;
    private String name;
    private int numOfTerritory;
    private int numOfArea;

    Player(Faculty faculty){
        this.faculty = faculty;
        numOfTerritory = 0;
        numOfArea = 0;
    }
    Player(Faculty faculty, String name){
        this.faculty = faculty;
        this.name = name;
        numOfTerritory = 0;
        numOfArea = 0;
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
    public int getNumOfTerritory(){
        return numOfTerritory;
    }
    public void increaseTerritory(){
        numOfTerritory++;
    }
    public void setNumOfTerritory(int numOfTerritory){
        this.numOfTerritory = numOfTerritory;
    }
    public int getNumOfArea(){
        return numOfArea;
    }
    public void setNumOfArea(int numOfArea){
        this.numOfArea = numOfArea;
    }
    public void increaseArea(){
        numOfArea++;
    }


}
package game.player;

import game.player.faculties.Faculty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Player{
    private final Faculty faculty;
    private Objective currentObjective;
    private Objective[] accomplishedObjectives;
    private int numOfTerritory;
    private int numOfArea;

    public Player(Faculty faculty){
        this.faculty = faculty;
        numOfTerritory = 0;
        numOfArea = 0;
    }

    public Faculty getFaculty(){
        return faculty;
    }
    public Objective getObjective(){
        return currentObjective;
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

    public Color getColor(){
        return faculty.getColor();
    }

    public ColorAdjust getCa(){
        return faculty.getCa();
    }
}
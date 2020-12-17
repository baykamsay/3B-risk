package game.player;

import java.util.ArrayList;
/*
public enum  AreaEnum {
    BILKENTISLAND, EASTCAMPUS, LOWERMAINCAMPUS, UPPERMAINCAMPUS;

}*/
public class Area{
    private ArrayList<Territory> territories;
    private String name;

    //This constructor requires to add territories later
    public Area(String name){
        territories = new ArrayList<>();
        this.name = name;
    }

    public Area(String name, ArrayList<Territory> t){
        this.name = name;
        this.territories = t;
    }
    public String getName(){
        return name;
    }
    public void addTerritory(Territory t){
        territories.add(t);
    }
    public void cloneTerritories(ArrayList<Territory> t){
        territories.addAll(t);
    }
}
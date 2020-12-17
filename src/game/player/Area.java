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
    Area(String name){
        territories = new ArrayList<>();
        this.name = name;
    }
    //This constructor initializes the territories
    Area(String name, ArrayList<Territory> t){
        this.name = name;
        this.territories.addAll(t);
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

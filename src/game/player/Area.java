package game.player;

import java.util.ArrayList;

public class Area implements Place{
    private ArrayList<Territory> territories;
    private String name;

    public Area(String name){
        territories = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }
    public void addTerritory(Territory t){
        territories.add(t);
    }
    public ArrayList<Territory> getTerritories() {
        return territories;
    }
    public void cloneTerritories(ArrayList<Territory> t){
        territories.addAll(t);
    }
}
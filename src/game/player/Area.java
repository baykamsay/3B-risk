package game.player;

import game.player.faculties.Faculty;
import menu.FacultySelectionMenu;

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
    public Player getRuler(){
        Player ruler = territories.get(0).getRuler();
        if(ruler == null){
            return null;
        }

        for (Territory t: territories) {
            if (!(t.isRuler(ruler))){
                return null;
            }
        }
        return ruler;
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
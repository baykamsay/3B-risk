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
    public Faculty getRuler(){
        Faculty ruler = territories.get(0).getRuler().getFaculty();
        for (Territory t: territories) {
                if (t.getRuler().getFaculty() != ruler){
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
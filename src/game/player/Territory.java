package game.player;

import javafx.scene.effect.ColorAdjust;

import java.util.ArrayList;

public class Territory implements Place{
    private Area area;
    private String name;
    private int numOfArmies;

    // Used to recognize which ImageView to update
    private int id;
    private Player ruler;
    private ColorAdjust ca;
    private ArrayList<Territory> adjacentTerritories;

    public Territory(Area area, String name, int id){
        this.id = id;
        this.area = area;
        this.name = name;
        this.ruler = null;
        adjacentTerritories = new ArrayList<>();
        this.ca = new ColorAdjust();
    }
    //Add an adjacent territory
    public void addAdjacentTerritory(Territory t){
        adjacentTerritories.add(t);
    }
    //Check if a territory is adjacent
    public boolean isAdjacent(Territory t){
        if(adjacentTerritories.contains(t))
            return true;
        else
            return false;
    }
    public Area getArea(){
        return area;
    }
    @Override
    public String getName(){
        return name;
    }
    public int getNumOfArmies(){
        return numOfArmies;
    }
    public Player getRuler(){
        return ruler;
    }
    public void setNumOfArmies(int numOfArmies){
        this.numOfArmies = numOfArmies;
    }

    public void setRuler(Player ruler){
        this.ruler = ruler;
        this.ca = ruler.getCa();
    }

    //isRuler checks Territory's ruler. Returns true if the given player's faculty matches with the ruler's faculty otherwise returns false.
    public boolean isRuler(Player p){
        if(this.ruler == null){
            return false;
        }
        return ruler.equals(p);
    }

    public ColorAdjust getCa(){
        return ca;
    }
}
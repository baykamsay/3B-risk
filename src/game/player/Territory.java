package game.player;

import java.util.ArrayList;

enum Area{
    BILKENTISLAND,EASTCAMPUS,LOWERMAINCAMPUS,UPPERMAINCAMPUS
}
public class Territory {
    private Area area;
    private String name;
    private int numOfArmies;
    private Player ruler;
    private ArrayList<Territory> adjacentTerritories;

    Territory(Area area, String name){
        this.area = area;
        this.name = name;
        adjacentTerritories = new ArrayList<>();

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
    }

    //isRuler checks Territory's ruler. Returns true if the given player matches with the ruler otherwise returns false.
    public Boolean isRuler(Territory t){
        if (t.getRuler().getName().equals(ruler.getName())){
            return true;
        }
        return false;
    }
}
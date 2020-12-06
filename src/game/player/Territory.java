package game.player;

enum Area{
    BILKENTISLAND,EASTCAMPUS,LOWERMAINCAMPUS,UPPERMAINCAMPUS
}
public class Territory {
    public Area area;
    private String name;
    private int numOfArmies;
    private Player ruler;

    Territory(Area area, String name){
        this.area = area;
        this.name = name;
    }
    private Area getArea(){
        return area;
    }
    private String getName(){
        return name;
    }
    private int getNumOfArmies(){
        return numOfArmies;
    }
    private Player getRuler(){
        return ruler;
    }
    private void setNumOfArmies(int numOfArmies){
        this.numOfArmies = numOfArmies;
    }
    private void setRuler(Player ruler){
        this.ruler = ruler;
    }

}
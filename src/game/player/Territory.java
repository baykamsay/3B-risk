package game.player;

enum Area{
    BILKENTISLAND,EASTCAMPUS,LOWERMAINCAMPUS,UPPERMAINCAMPUS
}
public class Territory {
    private Area area;
    private String name;
    private int numOfArmies;
    private Player ruler;

    Territory(Area area, String name){
        this.area = area;
        this.name = name;
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
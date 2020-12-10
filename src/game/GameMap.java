package game;

import game.player.Area;
import game.player.Territory;

import java.util.ArrayList;

public class GameMap {

    private static final int TOTAL_TERRITORY_COUNT = 49;
//    private static final int EAST_TERRITORY_COUNT = 10;
//    private static final int ISLAND_TERRITORY_COUNT = 5;
//    private static final int UPPER_MAIN_TERRITORY_COUNT = 16;
//    private static final int LOWER_MAIN_TERRITORY_COUNT = 16;
    private static final String[] TERRITORY_NAMES = {"Dorms","Sports Center", "Library", "Prep Buildings", "Health Center", "Cafeteria", "ATM",
            "Coffee Break", "Mozart Cafe", "Entrance", "Bilkent 1 & 2", "Sports International", "Ankuva", "Bilkent Center", "Bilkent Hotel", "MSSF",
            "Concert Hall", "Dorms", "V Building", "F Buildings", "Dorm 76", "Mescit", "Starbucks", "M Building", "Meteksan", "Sports Center", "Nanotam",
            "Mayfest", "A Building", "S Building", "T Building", "G Building", "Coffee Break", "Square", "CafeIn", "Statue", "B Building", "Cyber Park", "ODEON",
            "Library", "Mozart Cafe", "Cafeteria", "EA Building", "Meteksan", "EE Building", "Mithat Coruh", "Entrance"};

    // A list of adjacent territories' ids for each territory
    private static final int[][] ADJACENT_TERRITORIES = {{1,3,4,15},{0,2,3},{1,3,5},{0,1,2,4,5},{0,3,5,6},{2,3,4,6,7,8},{4,5,7},{5,6,8,9},{5,7,9},{7,8,10,12},
            {9,11},{10,12},{9,11,13},{12,14,38,46},{13},{0,16},{15,27},{18,19,20},{17,19,21,22,23},{17,18,20,23,24,25},{17,25,26},{18,22,27},{18,21,23,27},
            {18,19,22,24,27},{19,23,25,28,29},{19,20,24,26,29},{20,25},{16,21,22,23,31},{23,24,29,30},{24,25,28,30},{28,29,32,33},{27,32,34,35},{30,31,33,35},
            {30,35,36},{31,35,38,39},{31,32,33,34,36,39},{33,35,37,39,40},{36},{13,34,41},{34,35,36,40,41,42},{36,39},{38,39,42,44},{39,41,44,45},{44,46},{41,42,43,45,46},{42,44},{43,44}};
    private Territory[] territories;
    private ArrayList<Territory> east;
    private ArrayList<Territory> island;
    private ArrayList<Territory> upperMain;
    private ArrayList<Territory> lowerMain;

    // Initialize new game
    public GameMap(){
        territories = new Territory[TOTAL_TERRITORY_COUNT];
        east = new ArrayList<>();
        island = new ArrayList<>();
        upperMain = new ArrayList<>();
        lowerMain = new ArrayList<>();


    }

    // Initialize saved game
    public GameMap(int saveSlot){

    }

    public void initTerritories(){

        // Initialize territories
        for(int i = 0; i <TOTAL_TERRITORY_COUNT; i++){
            if(i < 10){
                territories[i] = new Territory(Area.EASTCAMPUS, TERRITORY_NAMES[i], i);
                east.add(territories[i]);
            }else if ( i < 15){
                territories[i] = new Territory(Area.BILKENTISLAND, TERRITORY_NAMES[i], i);
                island.add(territories[i]);
            }else if ( i < 31){
                territories[i] = new Territory(Area.UPPERMAINCAMPUS, TERRITORY_NAMES[i], i);
                upperMain.add(territories[i]);
            }else{
                territories[i] = new Territory(Area.LOWERMAINCAMPUS, TERRITORY_NAMES[i], i);
                lowerMain.add(territories[i]);
            }
        }

        // Initialize Adjacent
        for(int i = 0; i <TOTAL_TERRITORY_COUNT; i++) {
            for(int id : ADJACENT_TERRITORIES[i]){
                territories[i].addAdjacentTerritory(territories[id]);
            }
        }
    }

    public Territory[] getTerritories() {
        return territories;
    }
}
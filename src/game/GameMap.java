package game;

import game.player.Territory;
import menu.GameMenuManager;

public class GameMap {

    private Territory[] territories;

    // Initialize new game
    public GameMap(){

    }

    // Initialize saved game
    public GameMap(int saveSlot){

    }

    public Territory[] getTerritories() {
        return territories;
    }
}
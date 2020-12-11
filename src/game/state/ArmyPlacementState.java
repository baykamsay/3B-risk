package game.state;

import game.GameEngine;
import game.player.Player;
import game.player.Territory;
import game.scene.MapScene;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import menu.GameMenuManager;
import menu.MenuState;

import java.util.ArrayList;

public class ArmyPlacementState implements GameState {
    private GameEngine engine;
    private int currentPlayer, addibleArmies;

    public ArmyPlacementState(GameEngine engine) {
        this.engine = engine;
        currentPlayer = 0;
        addibleArmies = 0;
    }

    @Override
    public void mapSelect(ActionEvent e) {
        Territory[] territories = engine.getMap().getTerritories();
        ArrayList<Player> players = engine.getPlayers();

        if (addibleArmies == 0) {
            for (Territory territory : territories) {
                if ((e.getSource().toString() == territory.getName()) && (players.get(currentPlayer) == territory.getRuler())) {
                    //pop up ui will be implemented
                    //deployArmies(addedArmies, territory); *addedArmies will be returned by the pop ui
                    //addibleArmies = addibleArmies - addedArmies;
                }
            }
        }
        else
        {
            //engine.switchState(AttackingState(....)
        }
    }

    public void calculateNumberOfArmies(Player p) {
        if (p.getNumOfTerritory() <= 11) {
            addibleArmies = 3;
        } else {
            addibleArmies = p.getNumOfTerritory() / 3;
        }
        if (p.getNumOfArea() > 0) {
            addibleArmies = addibleArmies + p.getNumOfArea(); //This part's calculation will be further discussed
        }
    }

    public void deployArmies(int addedArmies, Territory t) {
        t.setNumOfArmies(t.getNumOfArmies() + addedArmies);
    }

    public int getAddibleArmies(){
        return addibleArmies;
    }
}

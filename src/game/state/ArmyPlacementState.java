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
    private static ArmyPlacementState instance;
    private GameEngine engine;
    private int currentPlayer, addibleArmyNo, chosenArmyNo;

    private ArmyPlacementState() {
        engine = GameEngine.getInstance();
        currentPlayer = 0;
        addibleArmyNo = 0;
        chosenArmyNo = 0;
    }

    public static ArmyPlacementState getInstance() {
        if (instance == null) {
            synchronized (ArmyPlacementState.class) {
                if (instance == null) {
                    instance = new ArmyPlacementState();
                }
            }
        }
        return instance;
    }

    @Override
    public void mapSelect(ActionEvent e) {
        Territory[] territories = engine.getMap().getTerritories();

        if (addibleArmyNo == 0) {
            for (Territory territory : territories) {
                if ((e.getSource().toString() == territory.getName()) && (engine.getCurrentPlayer().getFaculty() == territory.getRuler().getFaculty())) {
                    displayArmyPlacement(e);
                    deployArmies(chosenArmyNo, territory);
                    addibleArmyNo = addibleArmyNo - chosenArmyNo;
                }
            }
        }
        else
        {
            engine.switchState(AttackingPlanningState.getInstance());
        }
    }

    public void displayArmyPlacement(ActionEvent e) {
        //to do
    }

    public void calculateNumberOfArmies(Player p) {
        if (p.getNumOfTerritory() <= 11) {
            addibleArmyNo = 3;
        } else {
            addibleArmyNo = p.getNumOfTerritory() / 3;
        }
        if (p.getNumOfArea() > 0) {
            addibleArmyNo = addibleArmyNo + p.getNumOfArea(); //This part's calculation will be further discussed
        }
    }

    public void deployArmies(int addedArmies, Territory t) {
        t.setNumOfArmies(t.getNumOfArmies() + addedArmies);
    }

    public int getAddibleArmyNo(){
        return addibleArmyNo;
    }

    public int getChosenArmyNo(){
        return chosenArmyNo;
    }

    public void setChosenArmyNo(int armyNo){
        chosenArmyNo = armyNo;
    }
}

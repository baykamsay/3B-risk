package game.state;

import game.GameEngine;
import game.player.Objective;
import game.player.Player;
import game.player.Territory;
import javafx.event.ActionEvent;

public class ArmyPlacementState implements GameState {
    private static ArmyPlacementState instance;
    private GameEngine engine;
    private int addibleArmyNo;

    private ArmyPlacementState() {
        engine = GameEngine.getInstance();
        calculateNumberOfArmies(engine.getCurrentPlayer());
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

        for (Territory territory : territories) { // disabling the button is a better solution
            if ((e.getSource() == territory) && (engine.getCurrentPlayer() == territory.getRuler())) {
                territory.setNumOfArmies(territory.getNumOfArmies() + 1);
                addibleArmyNo = addibleArmyNo - 1;
            }
        }
        if (addibleArmyNo <= 0) {
            engine.switchState(AttackingPlanningState.getInstance());
        }
    }

    @Override
    public void start() {

    }

    public void calculateNumberOfArmies(Player p) {
        if (p.getNumOfTerritory() <= 9) {
            addibleArmyNo = 3;
        } else {
            addibleArmyNo = p.getNumOfTerritory() / 3;
        }
        if( p.equals(engine.getMap().getAreas()[0].getRuler())){ //east campus area = +3
            addibleArmyNo += 3;
        }
        if( p.equals(engine.getMap().getAreas()[1].getRuler())){ //island area = +2
            addibleArmyNo += 2;
        }
        if( p.equals(engine.getMap().getAreas()[2].getRuler())){ //upper main campus area = +5
            addibleArmyNo += 5;
        }
        if( p.equals(engine.getMap().getAreas()[3].getRuler())){ //lower main campus area = +5
            addibleArmyNo += 5;
        }
        int objectiveResult = p.getObjective().isDone();
        if(objectiveResult == -1){
            p.setObjective(Objective.generateObjective(p));
        } else if (objectiveResult == 1) {
            addibleArmyNo += p.getObjective().getBonus(); //bonus army for a completed objective
            p.setObjective(Objective.generateObjective(p));
        }
    }

    public int getAddibleArmyNo(){
        return addibleArmyNo;
    }
}

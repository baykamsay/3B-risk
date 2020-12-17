package game.state;

import game.GameEngine;
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
        if (p.getNumOfTerritory() <= 11) {
            addibleArmyNo = 3;
        } else {
            addibleArmyNo = p.getNumOfTerritory() / 3;
        }
        if (p.getNumOfArea() > 0) {
            addibleArmyNo = addibleArmyNo + p.getNumOfArea(); //This part's calculation will be further discussed
        }
    }

    public int getAddibleArmyNo(){
        return addibleArmyNo;
    }
}

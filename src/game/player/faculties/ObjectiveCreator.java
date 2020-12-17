package game.player.faculties;

import game.GameEngine;
import game.player.*;

public class ObjectiveCreator {

    private Player p;
    private GameEngine engine;
    final int MAX_HOLD_TURN = 5;
    final int AREA_TURN_LIMIT = 5; //area objectives will be given after this turn
    private Territory[] target;

    public ObjectiveCreator(Player p, GameEngine engine){
        this.p = p;
        this.engine = engine;
        generateObjective();
    }

    public Objective generateObjective(){
        Objective objective;
        ObjectiveStrategy strategy;
        ObjectiveStrategy decorator;
        String objectiveName; //create objective name by adding "capture" or "hold" to the target name

        //choose if the strategy will be capture or hold
        int range = 3;
        int captureOrHold = (int)(Math.random() * range) + 1;
        if(captureOrHold == 1){ //hold objective is frequent than capture
            int limit = (int)(Math.random() * MAX_HOLD_TURN) + 1; //determine the turn for hold objective
            strategy = new HoldObjective(p.getFaculty(), limit);
            objectiveName = "hold";
        }
        else{
            strategy = new CaptureObjective(p.getFaculty());
            objectiveName = "capture";
        }

        //DECORATORS
        //generate territory decorator
        if(engine.getTurn() < AREA_TURN_LIMIT){
            decorator = new TerritoryDecorator(strategy);
            //select the territory to be assigned
            int territoryIndex = (int)(Math.random() * engine.getMap().getTerritories().length);
            target = new Territory[1];
            target[0] = engine.getMap().getTerritories()[territoryIndex];
            objectiveName = objectiveName + target[0].getName();
        }
        else{
            decorator = new AreaDecorator(strategy);
            //select the area to be assigned
            int areaIndex = (int)(Math.random() * 4);
            //target = engine.getMap().getAreas()[areaIndex].getTerritories(); //will be ok after the area adjustments
            //objectiveName = objectiveName + engine.getMap().getAreas()[areaIndex].getName(); //will be ok after area adjustments
        }
        objective = new Objective(decorator,target);
        objective.setName(objectiveName);
        return objective;
    }

}

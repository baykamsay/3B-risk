package game.player;

import game.GameEngine;
import game.GameMap;
import game.player.faculties.Fen;
import java.util.ArrayList;

public class Objective {

    static final int HOLD_LIMIT = 5;
    static final int CAPTURE_LIMIT = 5;
    static final int AREA_TURN_LIMIT = 5; //area objectives will be given after this turn
    static final double CAPTURE_PROB = 0.5;
    static final double TERRITORY_PROB = 0.5;
    static final int CAPTURE_BONUS = 2;
    static final int HOLD_BONUS = 3;

    String name;
    int turnLimit;
    int currentTurn;
    Place target;
    ObjectiveStrategy strategy;
    Player player;
    int bonus;

    public Objective(ObjectiveStrategy strategy, Place target, int turnLimit, String name, Player player,int bonus){
        this.strategy = strategy;
        this.target = target;
        this.turnLimit = turnLimit;
        this.name = name;
        this.player = player;
        this.currentTurn = 0;
        this.bonus = bonus;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getTurnLimit(){
        return turnLimit;
    }
    public void setTurnLimit(int turnLimit){
        this.turnLimit = turnLimit;
    }
    public int getBonus(){return bonus;}
    public int getRemainingTurn(){return this.turnLimit - this.currentTurn;}
    // Returns -1 if the turn limit is passed, 0 if there are still turns but the objective is not accomplished,
    // 1 if objective is accomplished
    public int isDone() {
        currentTurn++;
        return strategy.isDone(this);
    }

    // generates and returns an objective for the given player
    public static Objective generateObjective(Player p){
        GameEngine engine = GameEngine.getInstance();
        Objective objective;
        ObjectiveStrategy strategy;
        ObjectiveStrategy decorator;
        Place place;
        boolean isCapture;
        int bonus = 0;

        String objectiveName; //create objective name by adding "capture" or "hold" to the target name
        int limit;

        // select strategy
        if (Math.random() < CAPTURE_PROB) {
            limit = (int)(Math.random() * HOLD_LIMIT) + 2; //determine the turn for hold objective
            objectiveName = "Capture ";
            strategy = new CaptureObjective();
            bonus += CAPTURE_BONUS;
            isCapture = true;
        } else {
            limit = (int)(Math.random() * CAPTURE_LIMIT) + 2;
            objectiveName = "Hold ";
            strategy = new HoldObjective();
            bonus += HOLD_BONUS;
            isCapture = false;
        }

        // select decorator
        if (Math.random() > TERRITORY_PROB && engine.getTurn() > AREA_TURN_LIMIT) {
            Area[] areas = GameMap.getInstance().getAreas();
            int areaIndex;
            ArrayList<Place> ownedAreas = new ArrayList<>();
            ArrayList<Place> otherAreas = new ArrayList<>();

            for (Area area : areas) {
                if (area.getRuler() == p) {
                    ownedAreas.add(area);
                } else {
                    otherAreas.add(area);
                }
            }

            if (isCapture) {
                areaIndex = (int)(Math.random() * otherAreas.size());
                place = otherAreas.get(areaIndex);
            } else {
                if (ownedAreas.isEmpty()) {
                    return generateObjective(p);
                }
                areaIndex = (int)(Math.random() * ownedAreas.size());
                place = ownedAreas.get(areaIndex);
            }
            decorator = new AreaDecorator(strategy);

            if( place.getName().equals("East Campus")){ //east campus area = +3
                bonus += 5;
            }
            else if( place.getName().equals("Bilkent Island")){ //island area = +2
                bonus += 3;
            }
            else if( place.getName().equals("Upper Main Campus")){ //upper main campus area = +5
                bonus += 7;
            }
            else if( place.getName().equals("Lower Main Campus")){ //lower main campus area = +5
                bonus += 7;
            }
        } else {
            ArrayList<Place> ownedTerritories = new ArrayList<>();
            ArrayList<Place> otherTerritories = new ArrayList<>();

            for (Territory territory : GameMap.getInstance().getTerritories()) {
                if (territory.isRuler(p)) {
                    ownedTerritories.add(territory);
                } else {
                    otherTerritories.add(territory);
                }
            }
            int territoryIndex;

            if (isCapture) {
                territoryIndex = (int)(Math.random() * otherTerritories.size());
                place = otherTerritories.get(territoryIndex);
            } else {
                territoryIndex = (int)(Math.random() * ownedTerritories.size());
                place = ownedTerritories.get(territoryIndex);
            }

            decorator = new TerritoryDecorator(strategy);
        }

        objectiveName += place.getName();
        //Science faculty ability used
        if (p.getFaculty() instanceof Fen){
            objective = new Objective(decorator, place, limit, objectiveName, p, bonus * 2);
        }
        else {
            objective = new Objective(decorator, place, limit, objectiveName, p, bonus);
        }
        return objective;
    }

    public static Objective generateObjective(int captureOrHold, int territoryOrArea, int placeId, int turnLimit, int bonus, Player p) {
        Objective objective;
        ObjectiveStrategy strategy;
        ObjectiveStrategy decorator;
        Place place;
        String objectiveName; //create objective name by adding "capture" or "hold" to the target name

        if (captureOrHold == 0) {
            objectiveName = "Capture ";
            strategy = new CaptureObjective();
        } else {
            objectiveName = "Hold ";
            strategy = new HoldObjective();
        }

        if (territoryOrArea == 0) {
            decorator = new TerritoryDecorator(strategy);
            place = GameMap.getInstance().getTerritories()[placeId];
        } else {
            decorator = new AreaDecorator(strategy);
            place = GameMap.getInstance().getAreas()[placeId];
        }

        objectiveName += place.getName();

        objective = new Objective(decorator, place, turnLimit, objectiveName, p, bonus);

        return objective;
    }
}

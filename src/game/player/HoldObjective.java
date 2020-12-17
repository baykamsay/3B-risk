package game.player;

import game.player.faculties.Faculty;

public class HoldObjective implements ObjectiveStrategy{

    private int currentTourCount;
    private int turnLimit;
    private Faculty faculty;

    public HoldObjective(Faculty faculty, int turnLimit){
            this.faculty = faculty;
            currentTourCount = 0;
            this.turnLimit = turnLimit;
    }

    @Override
    public boolean isDone(Territory[] target) {
        //assuming isDone() method is called every turn for a player
        if(currentTourCount == turnLimit) {
            return true;
        }
        else{
            for (Territory t: target) {
                if(t.getRuler().getFaculty() != faculty){
                    return false;
                }
            }
            currentTourCount++;
            return false;
        }
    }
}

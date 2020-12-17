package game.player;

import game.player.faculties.Faculty;

public class CaptureObjective implements ObjectiveStrategy {

    private Faculty faculty;

    public CaptureObjective(Faculty faculty){
        this.faculty = faculty;
    }

    @Override
    public boolean isDone(Territory[] target) {
        for (Territory t: target) {
            if(t.getRuler().getFaculty() != faculty){
                return false;
            }
        }
        return true;
    }
}

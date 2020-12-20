package game.player.faculties;

import game.GameEngine;
import game.player.Objective;
import game.player.Player;
import game.state.ArmyPlacementState;
import game.state.FortifyingState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Fedu implements Faculty{

    final private Color color = Color.OLIVE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/education_faculty_icon.png";
    final private String name =  "Faculty of Education";
    private boolean canUse;

    public Fedu(){
        ca.setHue(0.638);
        ca.setSaturation(-0.62);
        ca.setBrightness(-0.45);
        canUse = true;
    }

    @Override
    public boolean canUseAbility() {
        if (GameEngine.getInstance().currentState instanceof ArmyPlacementState && canUse) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void useAbility() {
        Player p = GameEngine.getInstance().getCurrentPlayer();
        canUse = false;
        GameEngine.getInstance().getController().setState(0);
        p.setObjective(Objective.generateObjective(p));
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public ColorAdjust getCa() {
        return ca;
    }

    @Override
    public String getIconName(){ return icon;}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSaveId() {
        return 3;
    }

    @Override
    public void setCanUse(boolean b){
        canUse = b;
    }
}

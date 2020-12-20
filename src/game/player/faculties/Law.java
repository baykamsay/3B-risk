package game.player.faculties;

import game.GameEngine;
import game.state.ArmyPlacementState;
import game.state.FortifyingState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Law implements Faculty{

    final private Color color = Color.DARKBLUE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/law_faculty_icon.png";
    final private String name = "Faculty of Law";
    private boolean canUse;

    public Law(){
        ca.setHue(-0.3135);
    }

    @Override
    public boolean canUseAbility() {
        if(GameEngine.getInstance().currentState instanceof ArmyPlacementState && canUse) {
            return true;
        }
        return false;
    }

    @Override
    public void useAbility() {
        canUse = false;
        ArmyPlacementState.getInstance().setLawAbilityCanUseTrue();
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
        return 6;
    }

    @Override
    public void setCanUse(boolean b){
        canUse = b;
    }

    public int abilityUsed(){ return canUse ? 0 : 1;}

    public void loadAbility(boolean canUse) { this.canUse = canUse; }
}

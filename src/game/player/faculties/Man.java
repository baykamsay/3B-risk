package game.player.faculties;

import game.GameEngine;
import game.state.ArmyPlacementState;
import game.state.FortifyingState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Man implements Faculty{

    final private Color color = Color.DARKSLATEGRAY;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/business_faculty_icon.png";
    final private String name = "Faculty of Business Administration";
    private boolean canUse, armyPlaced;

    public Man(){
        canUse = true;
        armyPlaced = false;
        ca.setHue(-0.7095);
        ca.setSaturation(-76);
        ca.setBrightness(-0.63);
    }

    @Override
    public boolean canUseAbility() {
        if(GameEngine.getInstance().currentState instanceof ArmyPlacementState && canUse && !armyPlaced) {
            return true;
        }
        return false;
    }

    @Override
    public void useAbility() {
        canUse = false;
        ArmyPlacementState.getInstance().setManAbilityCanUseTrue();
        GameEngine.getInstance().getController().updateAbilityButton();
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
        return 7;
    }

    public int abilityUsed(){ return canUse ? 0 : 1; }

    @Override
    public void setCanUse(boolean b){}

    public void setArmyPlaced(boolean b){ armyPlaced = b;}

    public void loadAbility(boolean canUse) { this.canUse = canUse; }
}

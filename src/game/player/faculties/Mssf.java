package game.player.faculties;

import game.GameEngine;
import game.state.FortifyingState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Mssf implements Faculty{

    final private Color color = Color.INDIGO;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/music_finearts_faculty_icon.png";
    final private String name = "Faculty of Music and Performing Arts";
    private boolean canUse;

    public Mssf(){
        canUse = true;
        ca.setHue(-0.143);
    }

    @Override
    public boolean canUseAbility() {
        if(GameEngine.getInstance().getCurrentPlayer().getFaculty() instanceof  Mssf && canUse) {
            return true;
        }
        return false;
    }

    @Override
    public void useAbility() {
        canUse = false;
        FortifyingState.getInstance().setMssfAbilityCanUseTrue();
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
        return 9;
    }

    public void setCanUseTrue(){
        canUse = true;
    }

    public int abilityUsed() {return canUse ? 0 : 1;}
}

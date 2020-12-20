package game.player.faculties;

import game.GameEngine;
import game.state.AttackingState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Art implements Faculty{

    final private Color color = Color.DARKORANGE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/art_faculty_icon.png";
    final private String name = "Faculty of Art, Design and Architecture";
    private boolean canUse;

    public Art(){
        canUse = true;
        ca.setHue(0.44);
    }

    @Override
    public boolean canUseAbility() {
        if (GameEngine.getInstance().currentState instanceof AttackingState && canUse) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void useAbility() {
        canUse = false;
        AttackingState.getInstance().setEconAbilityCanUse(true);
        GameEngine.getInstance().getController().setState(1);
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
        return 0;
    }

    @Override
    public void setCanUse(boolean b){
        canUse = b;}
}

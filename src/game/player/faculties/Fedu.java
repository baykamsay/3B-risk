package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Fedu implements Faculty{

    final private Color color = Color.OLIVE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/education_faculty_icon.png";

    public Fedu(){
        ca.setHue(0.638);
        ca.setSaturation(-0.62);
        ca.setBrightness(-0.45);
    }

    @Override
    public boolean canUseAbility() {
        return false;
    }

    @Override
    public void useAbility() {

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
}

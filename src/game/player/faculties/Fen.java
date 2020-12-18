package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Fen implements Faculty{

    final private Color color = Color.LIME;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/science_faculty_icon.png";

    public Fen(){
        ca.setHue(-1.0);
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

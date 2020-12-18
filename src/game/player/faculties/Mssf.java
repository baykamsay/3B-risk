package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Mssf implements Faculty{

    final private Color color = Color.INDIGO;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/music_finearts_faculty_icon.png";

    public Mssf(){
        ca.setHue(-0.143);
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

package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Mf implements Faculty{

    final private Color color = Color.DARKRED;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/engineering_faculty_icon.png";
    final private String name = "Faculty of Engineering";

    public Mf(){
        ca.setHue(0.2805);
    }

    @Override
    public boolean canUseAbility() {
        return false;
    }

    @Override
    public void useAbility() {}

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
}

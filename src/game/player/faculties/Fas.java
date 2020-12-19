package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Fas implements Faculty{

    final private Color color = Color.HOTPINK;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/applied_sciences_faculty_icon.png";
    final private String name = "Faculty of Applied Sciences";

    public Fas(){
        ca.setHue(0.0935);
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

    @Override
    public String getName() {
        return name;
    }
}

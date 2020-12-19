package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Law implements Faculty{

    final private Color color = Color.DARKBLUE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/law_faculty_icon.png";
    final private String name = "Faculty of Law";

    public Law(){
        ca.setHue(-0.3135);
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

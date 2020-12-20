package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Ibef implements Faculty{

    final private Color color = Color.CYAN;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/humanities_faculty_icon.png";
    final private String name = "Faculty of Humanities and Letters";

    public Ibef(){
        ca.setHue(-0.792);
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

    @Override
    public int getSaveId() {
        return 5;
    }
}

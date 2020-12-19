package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Feass implements Faculty{

    final private Color color = Color.YELLOW;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/econ_faculty_icon.png";
    final private String name = "Faculty of Economics, Administration and Social Sciences";

    public Feass(){
        ca.setHue(0.6165);
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
    public String getName(){ return name; }
}

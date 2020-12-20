package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Art implements Faculty{

    final private Color color = Color.DARKORANGE;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/art_faculty_icon.png";
    final private String name = "Faculty of Art, Design and Architecture";

    public Art(){
        ca.setHue(0.44);
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
        return 0;
    }

    @Override
    public void setCanUse(boolean b){}
}

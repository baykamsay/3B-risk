package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Art implements Faculty{

    final public Color color = Color.DARKORANGE;
    final public ColorAdjust ca = new ColorAdjust();

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
}

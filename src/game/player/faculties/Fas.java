package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Fas implements Faculty{

    final public Color color = Color.HOTPINK;
    final public ColorAdjust ca = new ColorAdjust();

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
}

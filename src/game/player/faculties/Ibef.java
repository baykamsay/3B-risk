package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Ibef implements Faculty{

    final public Color color = Color.CYAN;
    final public ColorAdjust ca = new ColorAdjust();

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
}

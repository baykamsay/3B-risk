package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Mf implements Faculty{

    final public Color color = Color.DARKRED;
    final public ColorAdjust ca = new ColorAdjust();

    public Mf(){
        ca.setHue(0.2805);
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

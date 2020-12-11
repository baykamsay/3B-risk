package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Law implements Faculty{

    final public Color color = Color.DARKBLUE;
    final public ColorAdjust ca = new ColorAdjust();

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
}

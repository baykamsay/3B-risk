package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Man implements Faculty{

    final public Color color = Color.DARKSLATEGRAY;
    final public ColorAdjust ca = new ColorAdjust();

    public Man(){
        ca.setHue(-0.7095);
        ca.setSaturation(-76);
        ca.setBrightness(-0.63);
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

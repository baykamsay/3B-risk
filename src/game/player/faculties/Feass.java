package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Feass implements Faculty{

    final public Color color = Color.YELLOW;
    final public ColorAdjust ca = new ColorAdjust();

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
}

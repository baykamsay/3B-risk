package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Mssf implements Faculty{

    final public Color color = Color.INDIGO;
    final public ColorAdjust ca = new ColorAdjust();

    public Mssf(){
        ca.setHue(-0.143);
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

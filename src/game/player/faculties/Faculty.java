package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public interface Faculty {
    public boolean canUseAbility();
    public void useAbility();
    public Color getColor();
    public ColorAdjust getCa();
}

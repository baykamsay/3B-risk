package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public interface Faculty {
    boolean canUseAbility();
    void useAbility();
    Color getColor();
    ColorAdjust getCa();
    String getIconName();
    String getName();
    int getSaveId();
}

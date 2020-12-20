package game.player.faculties;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Man implements Faculty{

    final private Color color = Color.DARKSLATEGRAY;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/business_faculty_icon.png";
    final private String name = "Faculty of Business Administration";
    final private boolean abilityUsed;
    public Man(){
        abilityUsed = false;
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

    @Override
    public String getIconName(){ return icon;}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSaveId() {
        return 7;
    }

    public int abilityUsed(){ return abilityUsed ? 1 : 0; }
}

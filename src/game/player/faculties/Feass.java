package game.player.faculties;

import game.GameEngine;
import game.state.ArmyPlacementState;
import game.state.AttackingState;
import game.state.WarState;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class Feass implements Faculty{

    final private Color color = Color.YELLOW;
    final private ColorAdjust ca = new ColorAdjust();
    final private String icon = "/img/icons/econ_faculty_icon.png";
    final private String name = "Faculty of Economics, Administration and Social Sciences";
    private boolean canUse;

    public Feass(){
        canUse = true;
        ca.setHue(0.6165);
    }

    @Override
    public boolean canUseAbility() {
        if (GameEngine.getInstance().currentState instanceof AttackingState && canUse) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void useAbility() {
        canUse = false;
        AttackingState.getInstance().setEconAbilityCanUse(true);
        GameEngine.getInstance().getController().setState(1);
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
    public String getName(){ return name; }

    @Override
    public int getSaveId() {
        return 2;
    }

    @Override
    public void setCanUse(boolean b){
        canUse = b;
    }
}

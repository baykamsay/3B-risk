package game;

import game.player.Objective;
import game.player.Player;
import game.player.Territory;
import game.player.faculties.Man;
import game.player.faculties.Mssf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveManager {

    private static SaveManager instance;

    public static SaveManager getInstance() {
        if (instance == null) {
            synchronized (SoundEngine.class) {
                if (instance == null) {
                    instance = new SaveManager();
                }
            }
        }
        return instance;
    }

    public void saveGame(int slot) throws Exception {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\RISK101" + "\\save" + slot + ".txt");
        FileWriter fw = new FileWriter(file, false);
        fw.write(Integer.toString(slot));
        fw.write(Integer.toString(GameEngine.getInstance().getTurn()));
        ArrayList<Player> players = GameEngine.getInstance().getPlayers();
        fw.write(Integer.toString(players.size()));
        String playerList = "";
        String abilityUsed = "";
        String objectives = "";

        // Art=0, fas=1, feass=2, fedu=3, fen=4, ibef=5, law=6, man=7, mf=8, mssf=9
        // Only MSSF or MAN matter for ability used (0 for not, 1 for used), all other faculties are -1
        for(Player p: players){
            playerList += p.getFaculty().getSaveId() + " ";
            switch (p.getFaculty().getSaveId()){
                case 7:
                    abilityUsed += ((Man) p.getFaculty()).abilityUsed() + " ";
                    break;
                case 9:
                    abilityUsed += ((Mssf) p.getFaculty()).abilityUsed() + " ";
                    break;
                default:
                    abilityUsed += "-1 ";

            }
        }
        playerList = playerList.strip();
        abilityUsed = abilityUsed.strip();
        fw.write(playerList);
        fw.write(abilityUsed);

        /* Construct save ids for objectives, the structure for them is as follows:
            OBJECTIVE NAME:  CAPTURE/HOLD      TERRITORY_NAME/AREA_NAME          TURN_REMAINING     REWARD
                        ID:   INT  0/1     -   (0-INT [0,46])/(1-INT[0-3])   -         INT       -   INT
            - between every field.
         */
        for(Player p : players){
            String objective = "";
            Objective obj = p.getObjective();
            String[] objString = obj.getName().split("-");

            if(objString[0].equals("Capture")){
                objective += "0-";
            } else if(objString[0].equals("Hold")){
                objective += "1-";
            }

            boolean found = false;

            // Check if place is a territory
            for(int i = 0; i < GameEngine.getInstance().getMap().getTerritories().length; i++){
                if(GameEngine.getInstance().getMap().getTerritory(i).getName().equals(objString[1])){
                    objective +=  "0-" + i + "-";
                    found = true;
                    break;
                }
            }

            if(!found){
                for(int i = 0; i < GameEngine.getInstance().getMap().getAreas().length; i++){
                    if((GameEngine.getInstance().getMap().getAreas())[i].getName().equals(objString[1])){
                        objective +=  "1-" + i + "-";
                        found = true;
                        break;
                    }
                }
            }

            if(!found){
                throw new Exception("Not a valid objective");
            }

            objectives += objective + " ";
        }
        objectives = objectives.strip();
        fw.write(objectives);

        // Get territory troop numbers
        // Get territory rulers
        String troopNums = "";
        String rulers = "";
        for(int i = 0; i < GameEngine.getInstance().getMap().getTerritories().length; i++){
            Territory t = GameEngine.getInstance().getMap().getTerritory(i);
            troopNums += t.getNumOfArmies() + " ";
            Player ruler = t.getRuler();

            for(int j = 0; j < players.size(); i++){
                if(players.get(i).equals(ruler)){
                    rulers += i + " ";
                }
            }
        }

        troopNums = troopNums.strip();
        rulers = rulers.strip();
        fw.write(troopNums);
    }
}

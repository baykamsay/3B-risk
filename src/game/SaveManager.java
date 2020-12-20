package game;

import game.player.Objective;
import game.player.Player;
import game.player.Territory;
import game.player.faculties.Law;
import game.player.faculties.Man;
import game.player.faculties.Mssf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

    public boolean[] checkForSaves(int saveSlots){
        boolean[] results = new boolean[saveSlots];
        File[] files = new File(System.getenv("LOCALAPPDATA")+"\\RISK101").listFiles();

        for(File f : files){
            Scanner fScan = null;
            try {
                fScan = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int saveNo = -1;
            try{
                saveNo = Integer.parseInt(fScan.nextLine());
                results[saveNo] = true;
            }catch(NumberFormatException e){
                System.out.println("Corrupted save file detected, deleting.");
                fScan.close();
                f.delete();
            }
            if(saveNo != -1) {
                if (saveNo > saveSlots) {
                    System.out.println("Invalid save file.");
                }
                fScan.close();
            }
        }

        return results;
    }

    public void saveGame(int slot) throws Exception {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\RISK101" + "\\save" + slot + ".txt");
        FileWriter fw = new FileWriter(file, false);
        fw.write(Integer.toString(slot));
        fw.write("\n" + GameEngine.getInstance().getTurn());
        ArrayList<Player> players = GameEngine.getInstance().getPlayers();
        fw.write("\n" + players.size());
        String playerList = "";
        String abilityUsed = "";
        String objectives = "";

        // Art=0, fas=1, feass=2, fedu=3, fen=4, ibef=5, law=6, man=7, mf=8, mssf=9
        // Only MSSF, MAN and LAW matter for ability used (0 for not, 1 for used), all other faculties are -1
        for(Player p: players){
            playerList += p.getFaculty().getSaveId() + " ";
            switch (p.getFaculty().getSaveId()){
                case 6:
                    abilityUsed += ((Law) p.getFaculty()).abilityUsed() + " ";
                    break;
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
        fw.write("\n" + playerList);
        fw.write("\n" + abilityUsed);

        /* Construct save ids for objectives, the structure for them is as follows:
            OBJECTIVE NAME:  CAPTURE/HOLD      TERRITORY_NAME/AREA_NAME          TURN_REMAINING     REWARD
                        ID:   INT  0/1     -   (0-INT [0,46])/(1-INT[0-3])   -         INT       -   INT
            - between every field.
         */
        for(Player p : players){
            String objective = "";
            Objective obj = p.getObjective();
            String[] objString = obj.getName().split(" ");
            String placeName = "";
            // Kind of a hack but works
            if(objString.length > 2){
                for(int i = 1; i < objString.length; i++){
                    placeName += objString[i] + " ";
                }
            } else {
                placeName = objString[1];
            }
            placeName = placeName.strip();
            if(objString[0].equals("Capture")){
                objective += "0-";
            } else if(objString[0].equals("Hold")){
                objective += "1-";
            }

            boolean found = false;
            System.out.println(objString[0] + "-" + placeName);
            // Check if place is a territory
            for(int i = 0; i < GameEngine.getInstance().getMap().getTerritories().length; i++){
                if(GameEngine.getInstance().getMap().getTerritory(i).getName().equals(placeName)){
                    objective +=  "0-" + i + "-";
                    found = true;
                    break;
                }
            }

            if(!found){
                for(int i = 0; i < GameEngine.getInstance().getMap().getAreas().length; i++){
                    if((GameEngine.getInstance().getMap().getAreas())[i].getName().equals(placeName)){
                        objective +=  "1-" + i + "-";
                        found = true;
                        break;
                    }
                }
            }

            if(!found){
                throw new Exception("Not a valid objective");
            }
            objective += obj.getTurnLimit() + "-" + obj.getBonus();
            objectives += objective + " ";
        }
        objectives = objectives.strip();
        fw.write("\n" + objectives);

        // Get territory troop numbers
        // Get territory rulers
        String troopNums = "";
        String rulers = "";
        for(int i = 0; i < GameEngine.getInstance().getMap().getTerritories().length; i++){
            Territory t = GameEngine.getInstance().getMap().getTerritory(i);
            troopNums += t.getNumOfArmies() + " ";
            Player ruler = t.getRuler();

            for(int j = 0; j < players.size(); j++){
                if(players.get(j).equals(ruler)){
                    rulers += j + " ";
                }
            }
        }

        troopNums = troopNums.strip();
        rulers = rulers.strip();
        fw.write("\n" + troopNums);
        fw.write("\n" + rulers);
        fw.write("\n" + GameEngine.getInstance().getPlayerTurn());
        fw.close();
    }

    public void loadGame(int saveSlot){
        File saveFile = new File(System.getenv("LOCALAPPDATA")+"\\RISK101" + "\\save" + saveSlot + ".txt");
        Scanner save = null;
        try {
            save = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Couldn't open save file");
            return;
        }

    }
}

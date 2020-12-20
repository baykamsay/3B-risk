package game;

import game.player.Objective;
import game.player.Player;
import game.player.Territory;
import game.player.faculties.*;

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

        save.nextLine();
        int turns = Integer.parseInt(save.nextLine());
        int noOfPlayers = Integer.parseInt(save.nextLine());
        int[] playerFacultyIds = new int[noOfPlayers];
        boolean[] playerAbilityUsed = new boolean[noOfPlayers];
        String[] tmp = save.nextLine().split(" ");
        String[] tmp2 = save.nextLine().split(" ");
        for(int i = 0; i < noOfPlayers; i++){
            playerFacultyIds[i] = Integer.parseInt(tmp[i]);
            int abilityUsed = Integer.parseInt(tmp2[i]);
            if(abilityUsed == 1){
                playerAbilityUsed[i] = true;
            }
        }

        // Parse objective
        ArrayList<int[]> objectives = new ArrayList<>();
        tmp = save.nextLine().split(" ");
        for(int i = 0; i < noOfPlayers; i++){
            String[] objDesc = tmp[i].split("-");

            // Parse the full description of the objective
            objectives.add(new int[]{Integer.parseInt(objDesc[0]),Integer.parseInt(objDesc[1]),
                    Integer.parseInt(objDesc[2]),Integer.parseInt(objDesc[3]),Integer.parseInt(objDesc[4])});
        }

        // Parse territory troop count and rulers
        tmp = save.nextLine().split(" ");
        tmp2 = save.nextLine().split(" ");
        int[] territoryNums = new int[tmp.length];
        int[] territoryRulers = new int[tmp.length];
        for(int i = 0; i < tmp.length; i++){
            territoryNums[i] = Integer.parseInt(tmp[i]);
            territoryRulers[i] = Integer.parseInt(tmp2[i]);
        }

        int currentPlayer = Integer.parseInt(save.nextLine());

        GameEngine engine = GameEngine.getInstance();

        // Create players
        ArrayList<Player> players = new ArrayList<Player>();
        for(int i = 0; i < playerFacultyIds.length; i++){
            switch(playerFacultyIds[i]){
                case 0:
                    players.add(new Player(new Art()));
                    break;
                case 1:
                    players.add(new Player(new Fas()));
                    break;
                case 2:
                    players.add(new Player(new Feass()));
                    break;
                case 3:
                    players.add(new Player(new Fedu()));
                    break;
                case 4:
                    players.add(new Player(new Fen()));
                    break;
                case 5:
                    players.add(new Player(new Ibef()));
                    break;
                case 6:
                    players.add(new Player(new Law()));
                    ((Law) players.get(i).getFaculty()).loadAbility(!playerAbilityUsed[i]);
                    break;
                case 7:
                    players.add(new Player(new Man()));
                    ((Man) players.get(i).getFaculty()).loadAbility(!playerAbilityUsed[i]);
                    break;
                case 8:
                    players.add(new Player(new Mf()));
                    break;
                case 9:
                    players.add(new Player(new Mssf()));
                    ((Mssf) players.get(i).getFaculty()).loadAbility(!playerAbilityUsed[i]);
                    break;
            }
        }

        engine.setPlayers(players);
        engine.setupMapScene();

        // Load objectives
        for(int i = 0; i < players.size(); i++){
            int[] obj = objectives.get(i);
            Player p = players.get(i);
            p.setObjective(Objective.generateObjective(obj[0],obj[1],obj[2],obj[3],obj[4],p));
        }

        GameMap.getInstance().init(territoryNums, territoryRulers);
        engine.setCurrentPlayer(currentPlayer);
    }
}

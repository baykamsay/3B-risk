package game.scene;

import game.GameEngine;
import game.GameMap;
import game.player.Player;
import game.player.Territory;
import game.player.faculties.Fas;
import game.player.faculties.Mf;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MapSceneController implements Initializable, EventHandler<ActionEvent> {

    private static final String[] TERRITORY_NAMES = {"Dorms","Sports Center", "Library", "Prep Buildings", "Health Center", "Cafeteria", "ATM",
            "Coffee Break", "Mozart Cafe", "Entrance", "Bilkent 1 & 2", "Sports International", "Ankuva", "Bilkent Center", "Bilkent Hotel", "MSSF",
            "Concert Hall", "Dorms", "V Building", "F Buildings", "Dorm 76", "Mescit", "Starbucks", "M Building", "Meteksan", "Sports Center", "Nanotam",
            "Mayfest", "A Building", "S Building", "T Building", "G Building", "Coffee Break", "Square", "CafeIn", "Statue", "B Building", "Cyber Park", "ODEON",
            "Library", "Mozart Cafe", "Cafeteria", "EA Building", "Meteksan", "EE Building", "Mithat Coruh", "Entrance"};

    // East
    @FXML ImageView dormsEast;
    @FXML ImageView sportsEast;
    @FXML ImageView libraryEast;
    @FXML ImageView prepEast;
    @FXML ImageView healthCenterEast;
    @FXML ImageView cafeteriaEast;
    @FXML ImageView atmEast;
    @FXML ImageView coffeeBreakEast;
    @FXML ImageView mozartEast;
    @FXML ImageView entranceEast;

    // Island
    @FXML ImageView bilkent12Island;
    @FXML ImageView sportsInternationalIsland;
    @FXML ImageView ankuvaIsland;
    @FXML ImageView centerIsland;
    @FXML ImageView hotelIsland;

    // Upper Main
    @FXML ImageView mssfUpperMain;
    @FXML ImageView concertHallUpperMain;
    @FXML ImageView dormsUpperMain;
    @FXML ImageView vBuildingUpperMain;
    @FXML ImageView fBuildingsUpperMain;
    @FXML ImageView dorm76UpperMain;
    @FXML ImageView mescitUpperMain;
    @FXML ImageView starbucksUpperMain;
    @FXML ImageView mBuildingUpperMain;
    @FXML ImageView meteksanUpperMain;
    @FXML ImageView sportsCenterUpperMain;
    @FXML ImageView nanotamUpperMain;
    @FXML ImageView mayfestUpperMain;
    @FXML ImageView aBuildingUpperMain;
    @FXML ImageView sBuildingUpperMain;
    @FXML ImageView tBuildingUpperMain;

    // Lower Main
    @FXML ImageView gBuildingLowerMain;
    @FXML ImageView coffeeBreakLowerMain;
    @FXML ImageView squareLowerMain;
    @FXML ImageView cafeInLowerMain;
    @FXML ImageView statueLowerMain;
    @FXML ImageView bBuildingLowerMain;
    @FXML ImageView cyberParkLowerMain;
    @FXML ImageView odeonLowerMain;
    @FXML ImageView libraryLowerMain;
    @FXML ImageView mozartLowerMain;
    @FXML ImageView cafeteriaLowerMain;
    @FXML ImageView eaBuildingLowerMain;
    @FXML ImageView meteksanLowerMain;
    @FXML ImageView eeBuildingLowerMain;
    @FXML ImageView mithatCoruhLowerMain;
    @FXML ImageView entranceLowerMain;

    // Troop Counters for territories
    @FXML
    Text troops0,troops1,troops2,troops3,troops4,troops5,troops6,troops7,troops8,troops9,troops10,troops11,troops12,
            troops13,troops14,troops15,troops16,troops17,troops18,troops19,troops20,troops21,troops22,troops23,troops24,
            troops25,troops26,troops27,troops28,troops29,troops30,troops31,troops32,troops33,troops34,troops35,troops36,
            troops37,troops38,troops39,troops40,troops41,troops42,troops43,troops44,troops45,troops46;

    // Player Info
    @FXML
    Rectangle p1Bg, p2Bg, p3Bg, p4Bg, p5Bg, p6Bg;
    @FXML
    Circle p1IconBg,p2IconBg,p3IconBg,p4IconBg,p5IconBg,p6IconBg;
    @FXML
    ImageView p1Icon,p2Icon,p3Icon,p4Icon,p5Icon,p6Icon, p1TerritoryIcon, p2TerritoryIcon, p3TerritoryIcon, p4TerritoryIcon, p5TerritoryIcon, p6TerritoryIcon;
    @FXML
    Label p1TerritoryCount,p2TerritoryCount,p3TerritoryCount,p4TerritoryCount,p5TerritoryCount,p6TerritoryCount;

    // Pause button
    @FXML
    Button pauseButton;

    // Used to block interaction with the map.
    @FXML
    Rectangle mapBlocker;

    // Used for displaying battle results.
    @FXML
    Pane battleResultPane;
    @FXML
    Button battleOKButton;
    @FXML
    ImageView attackerIcon, defenderIcon, attackerDie1, attackerDie2, attackerDie3, defenderDie1, defenderDie2;
    @FXML
    Circle attackerCircle, defenderCircle;

    // Used for selecting troop amount.
    @FXML
    Pane selectorPane;
    @FXML
    ImageView selectorCancel, selectorConfirm;
    @FXML
    Label selector0, selector1, selector2, selector3, selector4;

    private ArrayList<Player> players;

    private ImageView[] territories;
    private Text[] troops;

    private ImageView[] pIcons;
    private Shape[] pIconBgs;
    private Shape[] pInfoBgs;
    private Label[] pTerritoryCounts;
    private ImageView[] pTerritoryIcons;

    private GameMap map;
    private GameEngine gameEngine;
    private ImageView[] defenderDiceImages, attackerDiceImages;
    private Label[] selectionLabels;
    private Player curTurn;

    public MapSceneController(){
    }

    public void init(){

        // Arrays for map info
        territories = new ImageView[]{dormsEast, sportsEast, libraryEast, prepEast, healthCenterEast, cafeteriaEast, atmEast,
                coffeeBreakEast, mozartEast, entranceEast, bilkent12Island, sportsInternationalIsland, ankuvaIsland, centerIsland, hotelIsland,
                mssfUpperMain, concertHallUpperMain, dormsUpperMain, vBuildingUpperMain, fBuildingsUpperMain, dorm76UpperMain, mescitUpperMain,
                starbucksUpperMain, mBuildingUpperMain, meteksanUpperMain, sportsCenterUpperMain, nanotamUpperMain, mayfestUpperMain, aBuildingUpperMain,
                sBuildingUpperMain, tBuildingUpperMain, gBuildingLowerMain, coffeeBreakLowerMain, squareLowerMain, cafeInLowerMain, statueLowerMain,
                bBuildingLowerMain, cyberParkLowerMain, odeonLowerMain, libraryLowerMain, mozartLowerMain, cafeteriaLowerMain, eaBuildingLowerMain, meteksanLowerMain,
                eeBuildingLowerMain, mithatCoruhLowerMain, entranceLowerMain};
        troops = new Text[]{troops0,troops1,troops2,troops3,troops4,troops5,troops6,troops7,troops8,troops9,troops10,
                troops11,troops12,troops13,troops14,troops15,troops16,troops17,troops18,troops19,troops20,troops21,
                troops22,troops23,troops24,troops25,troops26,troops27,troops28,troops29,troops30,troops31,troops32,
                troops33,troops34,troops35,troops36,troops37,troops38,troops39,troops40,troops41,troops42,troops43,
                troops44,troops45,troops46};

        // Arrays for player info
        pIcons = new ImageView[]{p1Icon,p2Icon,p3Icon,p4Icon,p5Icon,p6Icon};
        pIconBgs = new Shape[]{p1IconBg,p2IconBg,p3IconBg,p4IconBg,p5IconBg,p6IconBg};
        pInfoBgs = new Shape[]{p1Bg, p2Bg, p3Bg, p4Bg, p5Bg, p6Bg};
        pTerritoryCounts = new Label[]{p1TerritoryCount,p2TerritoryCount,p3TerritoryCount,p4TerritoryCount,p5TerritoryCount,p6TerritoryCount};
        pTerritoryIcons = new ImageView[]{p1TerritoryIcon, p2TerritoryIcon, p3TerritoryIcon, p4TerritoryIcon, p5TerritoryIcon, p6TerritoryIcon};


        int i = 0;
        for(ImageView iv : territories){
            if( iv != null) {
                iv.setPickOnBounds(false);
                iv.setId(Integer.toString(i));

                // TEST CODE
                iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    System.out.println(iv.getId());
                    map.getTerritories()[Integer.parseInt(iv.getId())].getRuler().setNumOfTerritory(map.getTerritories()[Integer.parseInt(iv.getId())].getRuler().getNumOfTerritory() - 1);
                    map.getTerritories()[Integer.parseInt(iv.getId())].setRuler(new Player(new Mf()));
                    nextTurn();
                    update();
                    event.consume();
                });
                //

                iv.setCache(true);
                iv.setCacheHint(CacheHint.SPEED);
                Tooltip tp = new Tooltip(TERRITORY_NAMES[i]);
                tp.setShowDelay(new Duration(0));
                tp.setHideDelay(new Duration(0));
                tp.setShowDuration(Duration.INDEFINITE);
                tp.setId("territory_tooltip");
                Tooltip.install(iv, tp);
            }
            else{
                System.out.println("ImageView is null");
            }
            i++;
        }

        // Make mapBlocker inactive.
        mapBlocker.setVisible(false);
        mapBlocker.setMouseTransparent(true);

        // Initialize battle result components
        battleResultPane.setVisible(false);
        battleResultPane.setMouseTransparent(true);
        battleOKButton.setOnAction(this);
        defenderDiceImages = new ImageView[]{defenderDie1, defenderDie2};
        attackerDiceImages = new ImageView[]{attackerDie1, attackerDie2, attackerDie3};

        // Initialize selector components
        selectorPane.setVisible(false);
        selectorPane.setMouseTransparent(true);
        selectionLabels = new Label[]{selector0,selector1,selector2,selector3,selector4};
        selectorCancel.setPickOnBounds(false);
        ColorAdjust hover = new ColorAdjust();
        hover.setBrightness(0.5);
        // CONTINUE HERE for selection
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
        for(int i = 0; i < players.size(); i++){
            pIconBgs[i].setFill(players.get(i).getColor());
            pInfoBgs[i].setFill(players.get(i).getColor());
            try {
                pIcons[i].setImage(new Image(getClass().getResource(players.get(i).getFaculty().getIconName()).toURI().toString()));
                pTerritoryIcons[i].setImage(new Image(getClass().getResource("/img/territory_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            // Test code
            pIcons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Random rand = new Random();
                displayBattleResult(new int[]{rand.nextInt(6) + 1}, new int[]{rand.nextInt(6) + 1, rand.nextInt(6) + 1}, new Player(new Fas()), new Player(new Fas()));
                event.consume();
            });
            //

        }

        // Set extra player information invisible
        for(int i = players.size(); i < 6; i++){
            pIconBgs[i].setVisible(false);
            pInfoBgs[i].setVisible(false);
            pIcons[i].setVisible(false);
        }
    }

    public void setMap(GameMap map){
        this.map = map;
    }

    public void setGameEngine(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    public void update(){

        // TEST CODE
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getNumOfTerritory() == 0){
                players.remove(i);
            }
        }
        setPlayers(players);
        //

        for(int i = 0; i < territories.length; i++){

            // Update territory colors
            ColorAdjust base = map.getTerritories()[i].getCa();
            ColorAdjust hover = new ColorAdjust(base.getHue(), base.getSaturation(), base.getBrightness() + 0.25, base.getContrast());
            territories[i].effectProperty().bind(
                    Bindings
                            .when(territories[i].hoverProperty())
                            .then((Effect) hover)
                            .otherwise(base)
            );
        }

        // Update player territory counts
        int i;
        for(i = 0; i < players.size(); i++){
            pTerritoryCounts[i].setText(Integer.toString(players.get(i).getNumOfTerritory()));
        }

        // Make eliminated player info invisible
        for(; i < 6; i++){
            pTerritoryCounts[i].setVisible(false);
            pIcons[i].setVisible(false);
            pIconBgs[i].setVisible(false);
            pInfoBgs[i].setVisible(false);
            pTerritoryIcons[i].setVisible(false);
        }
    }

    public void test(){
        Random rand = new Random();
        for(Text t : troops){
            t.setId("troop_counter");
            t.setText(Integer.toString(rand.nextInt(100)));
        }

        for(Territory t : map.getTerritories()){
            Player p = players.get(rand.nextInt(players.size()));
            t.setRuler(p);
            p.setNumOfTerritory(p.getNumOfTerritory() + 1);
        }
        setTurn(players.get(0));
        update();
    }

    public void addHandlers(){
        pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameEngine.pause();
        });
    }

    public void displayBattleResult(int[] attackerDice, int[] defenderDice, Player attacker, Player defender){
        // Set all image views for the dice invisible, set dice effects to null
        for(ImageView iv : attackerDiceImages){
            iv.setVisible(false);
            iv.setEffect(null);
        }
        for(ImageView iv : defenderDiceImages){
            iv.setVisible(false);
            iv.setEffect(null);
        }


        // Set images for available dice and set visible
        for(int i = 0; i < attackerDice.length; i++){
            try {
                attackerDiceImages[i].setImage(new Image(getClass().getResource("/img/die_" + attackerDice[i] + ".png").toURI().toString()));
                attackerDiceImages[i].setVisible(true);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < defenderDice.length; i++){
            try {
                defenderDiceImages[i].setImage(new Image(getClass().getResource("/img/die_" + defenderDice[i] + ".png").toURI().toString()));
                defenderDiceImages[i].setVisible(true);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        // Set images for attacking and defending players
        try {
            attackerIcon.setImage(new Image(getClass().getResource(attacker.getFaculty().getIconName()).toURI().toString()));
            defenderIcon.setImage(new Image(getClass().getResource(defender.getFaculty().getIconName()).toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Gray out losing and unused dice, count attacker wins
        ColorAdjust grayOut = new ColorAdjust();
        grayOut.setSaturation(-1.0);
        int won = 0;
        int numberOfBattles = Math.min(attackerDice.length,defenderDice.length);
        for(int i = 0; i < numberOfBattles; i++){
            if(attackerDice[i] > defenderDice[i]){
                defenderDiceImages[i].setEffect(grayOut);
                won++;
            }
            else{
                attackerDiceImages[i].setEffect(grayOut);
            }
        }

        if(attackerDice.length - defenderDice.length > 0){
            if(defenderDice.length == 1){
                attackerDiceImages[1].setEffect(grayOut);
            }
            attackerDiceImages[2].setEffect(grayOut);
        }
        else{
            defenderDiceImages[1].setEffect(grayOut);
        }

        if(won > numberOfBattles / 2){
            attackerCircle.setStyle("-fx-fill: green; -fx-opacity: 0.5;");
            defenderCircle.setStyle("-fx-opacity: 0;");
        } else if (won == numberOfBattles / 2){
            attackerCircle.setStyle("-fx-fill: yellow; -fx-opacity: 0.5;");
            defenderCircle.setStyle("-fx-fill: yellow; -fx-opacity: 0.5;");
        } else {
            defenderCircle.setStyle("-fx-fill: green; -fx-opacity: 0.5;");
            attackerCircle.setStyle("-fx-opacity: 0;");
        }

        // Set mapBlocker active
        mapBlocker.setVisible(true);
        mapBlocker.setMouseTransparent(false);

        // Set battleResultPane visible
        battleResultPane.setVisible(true);
        battleResultPane.setMouseTransparent(false);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == battleOKButton){
            battleResultPane.setVisible(false);
            battleResultPane.setMouseTransparent(true);
            mapBlocker.setVisible(false);
            mapBlocker.setMouseTransparent(true);
        }
    }

    public void setTurn(Player p){
        curTurn = p;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).equals(p)){
                double x =  pInfoBgs[i].getLayoutX();
                pInfoBgs[i].setLayoutX(x - 50);
                return;
            }
        }
        update();
    }

    public void nextTurn(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).equals(curTurn)){
                double x =  pInfoBgs[i].getLayoutX();
                pInfoBgs[i].setLayoutX(x + 50);
                setTurn(players.get((i + 1) % players.size()));
                return;
            }
        }
    }
}

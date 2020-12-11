package game.scene;

import game.GameEngine;
import game.GameMap;
import game.player.Player;
import game.player.Territory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MapSceneController implements Initializable {

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

    // Troop Counters
    @FXML
    Text troops0,troops1,troops2,troops3,troops4,troops5,troops6,troops7,troops8,troops9,troops10,troops11,troops12,
            troops13,troops14,troops15,troops16,troops17,troops18,troops19,troops20,troops21,troops22,troops23,troops24,
            troops25,troops26,troops27,troops28,troops29,troops30,troops31,troops32,troops33,troops34,troops35,troops36,
            troops37,troops38,troops39,troops40,troops41,troops42,troops43,troops44,troops45,troops46;

    // Player Info
    @FXML
    Shape p1InfoBg,p2InfoBg,p3InfoBg,p4InfoBg,p5InfoBg,p6InfoBg;

    @FXML
    Shape p1IconBg,p2IconBg,p3IconBg,p4IconBg,p5IconBg,p6IconBg;

    @FXML
    ImageView p1Icon,p2Icon,p3Icon,p4Icon,p5Icon,p6Icon;

    @FXML
    Button pauseButton;

    private ArrayList<Player> players;
    private ImageView[] territories;
    private ImageView[] pIcons;
    private Shape[] pIconBgs;
    private Shape[] pInfoBgs;
    private Text[] troops;
    private GameMap map;
    private GameEngine gameEngine;

    public MapSceneController(){
    }

    public void init(){

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
        pIcons = new ImageView[]{p1Icon,p2Icon,p3Icon,p4Icon,p5Icon,p6Icon};
        pIconBgs = new Shape[]{p1IconBg,p2IconBg,p3IconBg,p4IconBg,p5IconBg,p6IconBg};
        pInfoBgs = new Shape[]{p1InfoBg,p2InfoBg,p3InfoBg,p4InfoBg,p5InfoBg,p6InfoBg};

        Random rand = new Random();
        for(Text t : troops){
            t.setId("troop_counter");
            t.setText(Integer.toString(rand.nextInt(100)));
        }

        int i = 0;
        for(ImageView iv : territories){
            if( iv != null) {
                iv.setPickOnBounds(false);
                iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    System.out.println(iv.getId());
                    event.consume();
                });
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
        for(int i = 0; i < players.size(); i++){
            pIconBgs[i].setFill(players.get(i).getColor());
            pInfoBgs[i].setOpacity(0.45);
            pInfoBgs[i].setFill(players.get(i).getColor());
            pIcons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                event.consume();
            });
        }
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
        for(int i = 0; i < territories.length; i++){
            ColorAdjust base = map.getTerritories()[i].getCa();
            ColorAdjust hover = new ColorAdjust(base.getHue(), base.getSaturation(), base.getBrightness() + 0.25, base.getContrast());
            territories[i].effectProperty().bind(
                    Bindings
                            .when(territories[i].hoverProperty())
                            .then((Effect) hover)
                            .otherwise((Effect) base)
            );
        }
    }

    public void test(){
        Random rand = new Random();
        for(Territory t : map.getTerritories()){
            t.setRuler(players.get(rand.nextInt(players.size())));
        }
        update();
    }

    public void addHandlers(){
        pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameEngine.pause();
        });
    }
}

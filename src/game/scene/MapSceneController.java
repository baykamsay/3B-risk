package game.scene;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
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

    private Parent root;
    private ImageView[] imageViews;
    public MapSceneController(){
    }

    public void init(){
        ColorAdjust ca = new ColorAdjust();
        ColorAdjust ca2 = new ColorAdjust();
        ca.setHue(-0.792);
        ca2.setSaturation(-1.0);
        imageViews = new ImageView[]{dormsEast, sportsEast, libraryEast, prepEast, healthCenterEast, cafeteriaEast, atmEast,
                coffeeBreakEast, mozartEast, entranceEast, bilkent12Island, sportsInternationalIsland, ankuvaIsland, centerIsland, hotelIsland,
                mssfUpperMain, concertHallUpperMain, dormsUpperMain, vBuildingUpperMain, fBuildingsUpperMain, dorm76UpperMain, mescitUpperMain,
                starbucksUpperMain, mBuildingUpperMain, meteksanUpperMain, sportsCenterUpperMain, nanotamUpperMain, mayfestUpperMain, aBuildingUpperMain,
                sBuildingUpperMain, tBuildingUpperMain, gBuildingLowerMain, coffeeBreakLowerMain, squareLowerMain, cafeInLowerMain, statueLowerMain,
                bBuildingLowerMain, cyberParkLowerMain, odeonLowerMain, libraryLowerMain, mozartLowerMain, cafeteriaLowerMain, eaBuildingLowerMain, meteksanLowerMain,
                eeBuildingLowerMain, mithatCoruhLowerMain, entranceLowerMain};

        int i = 0;
        for(ImageView iv : imageViews){
            if( iv != null) {
                iv.setPickOnBounds(false);
                iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    System.out.println(iv.getId());
                    event.consume();
                });
                iv.effectProperty().bind(
                        Bindings
                                .when(iv.hoverProperty())
                                .then((Effect) ca)
                                .otherwise((Effect) ca2)
                );
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
}

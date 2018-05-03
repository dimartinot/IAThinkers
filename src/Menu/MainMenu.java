/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This package contains everything needed to display the starting menu
 */
package Menu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * The MainMenu Class defines every useful components of the MainMenu Scene
 * @author IAThinkers
 */
public class MainMenu extends Parent{
    
    /**
     * 
     * @return Locale variable designed for the language
     */
    public static Locale getLanguage() {
        try {
            Locale l;
            FileReader fr = new FileReader("language.txt");
            BufferedReader textReader = new BufferedReader(fr);
            try {
                String line = textReader.readLine();
                if (line.equals("fr")) {
                    l = new Locale("fr","FR");
                } else if (line.equals("en")) {
                    l = new Locale("en","UK");
                } else {
                    l = new Locale("en","UK");
                }
            } catch (IOException ex) {
                l = new Locale("en","UK");
            }
            try {
                fr.close();
            } catch (IOException ex) {
            }
            return l;
        } catch (FileNotFoundException fileNotFoundException) {
        }
        return new Locale("en","UK");
    }
    
    
    /**
     * Constructor of a MainMenu Object
     * @param height Defines the height of the display window
     * @param width Defines the width of the display window
     * @param sceneTab Array of Scenes : initialised in IAThinkers class {@link iathinkers.IAThinkers}, it allows to jump from any scene to any other if needed.
     * @param primaryStage Main Stage variable initialised in IAThikers class {@link iathinkers.IAThinkers}     */
    public MainMenu(int height, int width, Scene[] sceneTab, Stage primaryStage) {
        //Get the languages to set
        Locale l = getLanguage();
        ResourceBundle messages = ResourceBundle.getBundle("Menu/Menu",l);
        /**
         * Defines all needed Button : one for each Scene
        */
        Button buttonPlan = new Button(messages.getString("HOUSE PLAN CREATION"));
        buttonPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[1]);
            }
        });
        Button buttonTrace = new Button(messages.getString("FREEHAND PATH CREATION"));
        Button buttonObjet = new Button(messages.getString("OBJECT CREATION"));
        Button buttonStats = new Button(messages.getString("STATISTICS"));
        Button buttonSql = new Button(messages.getString("SQL SETTINGS"));
        buttonSql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[5]);
            }
        });
        
        //Gives to every button its class in order to adapt its shape and color
        buttonPlan.getStyleClass().add("button-menu");
        buttonTrace.getStyleClass().add("button-menu");
        buttonObjet.getStyleClass().add("button-menu");
        buttonStats.getStyleClass().add("button-menu");
        buttonSql.getStyleClass().add("button-menu");

        buttonPlan.setId("button-first");
        buttonTrace.setId("button-second");
        buttonObjet.setId("button-third");
        buttonStats.setId("button-forth");
        buttonSql.setId("button-fifth");
        
        //Insert all the precedently initialised buttons
        TilePane tile = new TilePane(Orientation.VERTICAL);
        tile.setMaxWidth(Double.MAX_VALUE);
        tile.setMaxHeight(Double.MAX_VALUE);
        tile.getChildren().add(buttonPlan);
        tile.getChildren().add(buttonTrace);
        tile.getChildren().add(buttonObjet);
        tile.getChildren().add(buttonStats);
        tile.getChildren().add(buttonSql);
        tile.setTileAlignment(Pos.TOP_LEFT);
        
        this.getChildren().add(tile);
    }
}

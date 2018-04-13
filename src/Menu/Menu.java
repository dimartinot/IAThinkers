/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

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
 * La classe menu définit les composantes nécessaires à l'affichage du menu en tuiles.
 * Elle possède un constructeur.
 * @author IAThinkers
 */
public class Menu extends Parent{
    
    /**
     * Constructeur de la classe Menu. Définit un objet menu à l'aide des paramètres suivants :
     * @param height Définit la hauteur de la fenêtre d'affichage
     * @param width Définit la largeur de la fenêtre d'affichage
     * @param sceneTab Tableau de scene : initialisé dans la classe IAThinkers {@link iathinkers.IAThinkers}, il permet de passer de n'importe quelle scene à n'importe quelle autre.
     * @param primaryStage Stage principal initialisé dans la classe IAThinkers {@link iathinkers.IAThinkers}, permet l'initialisation des scenes
     */
    public Menu(int height, int width, Scene[] sceneTab, Stage primaryStage) {
        
        Button buttonPlan = new Button("HOUSE PLAN CREATION");
        buttonPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[1]);
            }
        });
        Button buttonTrace = new Button("FREEHAND PATH CREATION");
        Button buttonObjet = new Button("OBJECT CREATION");
        Button buttonStats = new Button("STATISTICS");
        Button buttonSql = new Button("SQL SETTINGS");
        buttonSql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[5]);
            }
        });
        
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
        /*
        buttonPlan.setMaxWidth(Double.MAX_VALUE);
        buttonTrace.setMaxWidth(Double.MAX_VALUE);
        buttonObjet.setMaxWidth(Double.MAX_VALUE);
        buttonStats.setMaxWidth(Double.MAX_VALUE);
        
        VBox vbButtons = new VBox();
        vbButtons.setMaxSize(height, width);
        vbButtons.setSpacing(0);
        //vbButtons.setPadding(new Insets(0, 20, 10, 20)); 
        vbButtons.getChildren().addAll(buttonPlan, buttonTrace, buttonObjet, buttonStats);
        
        
        this.setTranslateX(width/2);
        this.setTranslateY(height/3);
        */
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

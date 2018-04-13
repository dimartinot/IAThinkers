/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Classe de la <i> scène </i> du plan de maison. Elle se compose d'une grille de cellule.
 * @author IAThinkers
 */
public class Plan extends Parent{
    /**
     * Constructeur de la classe Plan. Passe en paramètres une variable Stage et un tableau de Scene[] pour pouvoir retourner au menu principal à partir de cet <i> scène </i>
     * @param primaryStage variable Stage permettant de retourner sur la <i> scène </i> Menu {@link Menu}
     * @param sceneTab tableau de Scene permettant de retourner sur la <i> scène </i> Menu {@link Menu}
     */
    public Plan(Stage primaryStage, Scene[] sceneTab) {
       
        
        this.getStylesheets().add(this.getClass().getResource("plan.css").toExternalForm());
        
        //Horizontale box mère
        HBox hbox = new HBox();
        
        //Menu vertical
        VBox menuVertical = new VBox();
        menuVertical.setPadding(new Insets(0, 0, 0, 5));
        //Grille composante du menu
        GridPane grid = new GridPane();
        //Options de la liste déroulante
        ObservableList<String> options = 
        FXCollections.observableArrayList(
            "Mur",
            "Fenêtre",
            "Autre.."
        );
        
        //Liste déroulante
        final ComboBox choix = new ComboBox(options);
        choix.setId("choixObjet");
        grid.setVgap(10);
        GridPane.setConstraints(choix,2,1);
        
        //Label liste
        Label label = new Label("Choix objet : ");
        GridPane.setConstraints(label, 1, 1); // column=1 row=1
        
        //Info Case
        Text infoCase = new Text(10,50,"<_;_>");
        infoCase.setId("infoCase");
        GridPane.setConstraints(infoCase,1,4);
        
        //Bouton retour
        Button backButton = new Button("Retour");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[0]);
            }
        });
        GridPane.setConstraints(backButton, 1, 5); // column=1 row=5

        
        grid.getChildren().addAll(choix,label,backButton,infoCase);
        menuVertical.getChildren().add(grid);
        
        
        hbox.getChildren().add(new Grille(sceneTab[1]));
        hbox.getChildren().add(menuVertical);
        this.getChildren().add(hbox);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import Menu.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        
        //Liste déroulante
        ComboBox<String> choix = new ComboBox();
        choix.getItems().add("Wall");
        choix.getItems().add("Door");

        //Défini les TextField d'option de chacun des choix de l'utilisateur à la pose d'un Wall
        TextField height = new TextField();
        height.setId("height");
        height.setPrefWidth(30);
        Label heightLbl = new Label("Height");
        // force le textfield a ne contenir que des entrées numériques
        height.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    height.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        TextField width = new TextField();
        width.setId("width");
        width.setPrefWidth(30);
        Label widthLbl = new Label("Width");
        // force le textfield a ne contenir que des entrées numériques
        width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    width.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        //Défini l'orientation choisie à la pose d'une Door : s'ouvre t elle à la verticale ou à l'horizontale (i.e, s'ouvre-t-elle en haut et bas de la de pose 
        //ou à gauche et droite de la case de pose
        ComboBox<String> orientation = new ComboBox();
        orientation.getItems().add("Horizontal");
        orientation.getItems().add("Vertical");
        Label orientationLbl = new Label("Orientation");
        
        
        
        
        //Define the executed action on the choice of an object
        choix.setOnAction((e) -> {
             switch (choix.getSelectionModel().getSelectedItem()) {
                 case "Wall":
                    //Remove les choix de personnalisation d'une Door
                     
                    GridPane.clearConstraints(orientation);
                    GridPane.clearConstraints(orientationLbl);
                    grid.getChildren().remove(orientation);
                    grid.getChildren().remove(orientationLbl);

                    
                    GridPane.setConstraints(height,1,4);
                    GridPane.setConstraints(heightLbl,1,3);
                    GridPane.setConstraints(width,1,6);
                    GridPane.setConstraints(widthLbl,1,5);
                    //Ajoute les choix de personnalisation d'un Wall
                    grid.getChildren().add(height);
                    grid.getChildren().add(width);
                    grid.getChildren().add(heightLbl);
                    grid.getChildren().add(widthLbl);
                    break;
                 case "Door":
                     //Remove les choix de personnalisation d'un Wall
                     
                    GridPane.clearConstraints(height);
                    GridPane.clearConstraints(width);
                    GridPane.clearConstraints(heightLbl);
                    GridPane.clearConstraints(widthLbl);
                    grid.getChildren().remove(height);
                    grid.getChildren().remove(width);
                    grid.getChildren().remove(heightLbl);
                    grid.getChildren().remove(widthLbl);
                    
                    GridPane.setConstraints(orientationLbl,1,3);
                    GridPane.setConstraints(orientation,1,4);
                     //Ajoute les choix de personnalisation d'une Door
                    grid.getChildren().add(orientation);
                    grid.getChildren().add(orientationLbl);
                    break;
                 default:
                    break;
             }
        });
        
        
        
        choix.setId("choixObjet");
        grid.setVgap(10);
        GridPane.setConstraints(choix,1,2);
        
        //Label liste
        Label label = new Label("Object choice");
        GridPane.setConstraints(label, 1, 1); // column=1 row=1
        
        //Info Case
        Text infoCase = new Text(10,50,"<_;_>");
        infoCase.setId("infoCase");
        GridPane.setConstraints(infoCase,1,7);
        
        //Initialisation de l'objet grille
        Grille objetGrid = new Grille(sceneTab[1]);

        Label objectListLbl = new Label("Listing of all created objects");
        GridPane.setConstraints(objectListLbl,1,8);
        
        ComboBox<String> objectList = new ComboBox();
        objectList.setId("objectlist");
        /*for (Object o : objetGrid.getListObjects()) {
            objectList.getItems().add(o.toString());
        }*/
        GridPane.setConstraints(objectList,1,9);
        
        
        //Bouton retour
        Button backButton = new Button("Back..");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[0]);
            }
        });
        GridPane.setConstraints(backButton, 1, 12); // column=1 row=6

        grid.getChildren().addAll(choix,label,backButton,infoCase,objectListLbl,objectList);
        menuVertical.getChildren().add(grid);
        
        hbox.getChildren().add(objetGrid);
        hbox.getChildren().add(menuVertical);
        this.getChildren().add(hbox);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import Menu.Menu;
import java.util.StringTokenizer;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        orientation.setId("orientation");
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
        //Initialisation of the delete Button
        Button deleteButton = new Button("Delete");
            //When we attempt to delete an object, we will have to get the data out of the selected option of the ComboBox
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String selectedObject = objectList.getSelectionModel().getSelectedItem();
                        StringTokenizer splitObject = new StringTokenizer(selectedObject, "(");
                        String type = splitObject.nextElement().toString();
                        String data = splitObject.nextElement().toString();
                        switch (type) {
                            case "Wall":
                                String wallData[] = data.split(", ");
                                try {
                                        int height = Integer.parseInt(wallData[0]);
                                        int width = Integer.parseInt(wallData[1]);
                                        int posX = Integer.parseInt(wallData[2]);
                                        StringTokenizer st = new StringTokenizer(wallData[3], ")");
                                        int posY = Integer.parseInt(st.nextElement().toString());//The last cell may contain a closing parenthesis, so we have to use split one again to make sure it is gone before calling parseInt
                                        for (int i = 0; i < width; i++) {
                                            for (int j = 0; j < height; j++) {
                                                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(posX + i)+"-"+(posY+ j));
                                                if (rectangle.getFill() == Color.THISTLE) {
                                                    rectangle.setFill(Color.ALICEBLUE);
                                                }
                                            }
                                        }
                                        if (objetGrid.deleteWall(height, width, posX, posY)) {
                                            System.out.println("Wall correctly deleted");
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println("Error ! Wall not found");
                                        }
                                    } catch (NumberFormatException e) {

                                    }    
                                break;
                            case "Door":
                                String doorData[] = data.split(", ");
                                try {
                                        int height = Integer.parseInt(doorData[0]);
                                        int width = Integer.parseInt(doorData[1]);
                                        int posX = Integer.parseInt(doorData[2]);
                                        int posY = Integer.parseInt(doorData[3]);
                                        for (int i = 0; i < width; i++) {
                                            for (int j = 0; j < height; j++) {
                                                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(posX + i)+"-"+(posY+ j));
                                                if (rectangle.getFill() == Color.ANTIQUEWHITE) {
                                                    rectangle.setFill(Color.ALICEBLUE);
                                                }
                                            }
                                        }
                                        if (objetGrid.deleteDoor(height, width, posX, posY)) {
                                            System.out.println("Door correctly deleted");
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println("Error ! Door not found");
                                        }
                                    } catch (NumberFormatException e) {

                                    }    
                                break;
                            }
                    } catch (NullPointerException e) {
                
                    }
                }       
            });
        objectList.setOnAction((e) -> {
            grid.getChildren().remove(deleteButton);
            grid.getChildren().add(deleteButton);
        });
        GridPane.setConstraints(deleteButton,1,10);
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

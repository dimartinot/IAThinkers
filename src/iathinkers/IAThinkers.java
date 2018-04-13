/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iathinkers;

//Import les classes du projet
import Menu.Menu;
import Parametres.Parametres;
import Plan.Plan;
//
import java.io.IOException;
//Import les classes JavaFX
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
//Import SQL
import java.sql.Connection;

/**
 * Coeur du projet, la classe IAThinkers regroupe toutes les <i> scènes </i> à afficher et les charge dans un tableau de scène.
 * De plus, elle s'occupe d'afficher la <i> scène </i> du menu principal {@link Menu}.
 * Ainsi, IAThinkers est la classe mère de ce projet, elle ne possède donc qu'un constructeur vide. Ce dernier ne sera jamais appelé.
 * @author IAThinkers
 */
public class IAThinkers extends Application {
    /**
     * Constructeur de classe (vide en l'occurence)
     */
    public IAThinkers() {
        
    } 
    public static Connection connect;
    /**
     * La méthode start est appelée au lancement du programme. Elle redéfinit le start de la classe Application de JavaFX.
     * Le lancement de cette fonction se fait dans la partie main à l'aide de l'appelle de la méthode <i> launch </i> de la classe Application.
     * @param primaryStage variable stage, coeur de JavaFX
     * @throws IOException 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        setConnect(null);
        //tableau de 6 scenes : 1 pour le menu, 1 pour le plan, 1 pour le tracé, 1 pour la création d'objet, 1 pour les stats et 1 pour les paramètres SQL
        Scene[] sceneTab = new Scene[6];
        
        //Scene du menu
        Group root0 = new Group();
        Scene scene0 = new Scene(root0, 800, 600);
        scene0.getStylesheets().add(this.getClass().getResource("menu.css").toExternalForm());
        sceneTab[0] = scene0;

        //Scene du Plan de maison
        Group root1 = new Group();
        Scene scene1 = new Scene(root1,800,600);
        sceneTab[1] = scene1;
        
        //Scene des Parametres
        Group root5 = new Group();
        Scene scene5 = new Scene(root5,800,600);
        sceneTab[5] = scene5;
        
        //Objet menu
        Menu monMenu = new Menu(800,600,sceneTab,primaryStage);
        root0.getChildren().add(monMenu);

        //Objet plan
        Plan monPlan = new Plan(primaryStage, sceneTab);
        root1.getChildren().add(monPlan);
        
        //Objet parametres
        Parametres mesParemetres = new Parametres(primaryStage, sceneTab);
        root5.getChildren().add(mesParemetres);
        
        primaryStage.setScene(scene0);
        primaryStage.setTitle("IA Thinkers - Réinventons le monde !");
        primaryStage.show();
    }

    public static Connection getConnect() {
        return connect;
    }

    public void setConnect(Connection connect) {
        this.connect = connect;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}


/**
 * This package contains just one java file with the main function. This is there that are built all the scenes.
 */
package iathinkers;

//Import the main classes of all the scenes
import Menu.MainMenu;
import Parametres.SQLParameters;
import Plan.Plan;

import java.io.IOException;
//Import useful JavaFX classes
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
//Import SQL
import java.sql.Connection;

/**
 * As explained, this class is the heart of the project. This is where the <i> scenes </i> are initialised.
 * Furthermore, it deals with the displaying of the menu <i> scene </i> {@link Menu.MainMenu}.
 * Then, we can call this class the <i> "mother" </i> of all the others
 * @author IAThinkers team
 */
public class IAThinkers extends Application {
    
    public static Connection connect;
    
    /**
     * Empty class constructor
     */
    public IAThinkers() {
        
    } 
    /**
     * The start method is a mandatory function to define when you deal with JavaFX project.
     * The launch of this method is done using the JavaFX pre-implemented <i> launch </i> method
     */
    @Override
    public void start(Stage primaryStage) {
        setConnect(null);
        //The array of the six main scenes : the first one for the menu, the 2nd for the plan, the 3rd for the freehand path, the 4th for the object creation tool, the 5th for the stats and last, but definitly not the leaset, the 6th for the SQL parameters.
        Scene[] sceneTab = new Scene[6];
        
        //Menu scene
        Group root0 = new Group();
        Scene scene0 = new Scene(root0, 800, 600);
        scene0.getStylesheets().add(this.getClass().getResource("menu.css").toExternalForm());
        sceneTab[0] = scene0;

        //House Plan scene
        Group root1 = new Group();
        Scene scene1 = new Scene(root1,800,600);
        sceneTab[1] = scene1;
        
        //Parameters scene
        Group root5 = new Group();
        Scene scene5 = new Scene(root5,800,600);
        sceneTab[5] = scene5;
        
        //MainMenu Object
        MainMenu monMenu = new MainMenu(800,600,sceneTab,primaryStage);
        root0.getChildren().add(monMenu);

        //Plan Object
        Plan monPlan = new Plan(primaryStage, sceneTab);
        root1.getChildren().add(monPlan);
        
        //SQL SQLParameters Object
        SQLParameters mesParemetres = new SQLParameters(primaryStage, sceneTab);
        root5.getChildren().add(mesParemetres);
        
        primaryStage.setScene(scene0);
        primaryStage.setTitle("IA Thinkers - Rediscover the world !");
        primaryStage.show();
    }

    public static Connection getConnect() {
        return connect;
    }

    public void setConnect(Connection connect) {
        this.connect = connect;
    }

    /**
     * @param args is the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

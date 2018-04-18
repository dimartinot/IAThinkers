/**
 * This package contains everything needed for SQL log-in Scene
 */
package Parametres;

//JavaFX Imports
import iathinkers.IAThinkers;
import Menu.MainMenu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//SQL Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * SQLParameters Class {@link Parametres.SQLParameters} displaying the log-in <i> Scene </I>
 * @author IAThinkers
 */
public class SQLParameters extends Parent{
    /**
     * Connection variables
     */
    private String username;
    private String adresse;
    private String mdp;

    private Statement statement;
    private ResultSet resultSet;
    private Connection cnt;
    /**
     * Constructor of the SQLParameters class {@link Parametres.SQLParameters}
     * @param primaryStage Stage variable used to go back to the MainMenu <i> Scene </i> {@link Menu.MainMenu}
     * @param sceneTab Scene Array used to get the MainMenu <i> Scene </i> {@link Menu.MainMenu}
     */
    
    public SQLParameters(Stage primaryStage, Scene[] sceneTab) {
        cnt = null;
        Scene mainScene = sceneTab[5];
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Welcome !\nPlease enter your MySQL credentials for the good functioning of this app");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label adresseLbl = new Label("MySQL Server Address:");
        grid.add(adresseLbl, 0, 1);
        
        TextField adresseTextField = new TextField();
        adresseTextField.setId("addTxt");
        grid.add(adresseTextField, 1, 1);
        
        Label usernameLbl = new Label("Identifier:");
        grid.add(usernameLbl, 0, 2);

        TextField userTextField = new TextField();
        userTextField.setId("usrTxt");
        grid.add(userTextField, 1, 2);

        Label pwLbl = new Label("Password:");
        grid.add(pwLbl, 0, 3);

        PasswordField pwBox = new PasswordField();
        pwBox.setId("pwTxt");
        grid.add(pwBox, 1, 3);
      
        //Info Case
        Text infoConnexion = new Text("");
        infoConnexion.setId("infoConnexion");
        grid.add(infoConnexion, 0, 6);
        
        Button btn = new Button("Connection");
        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                username = ((TextField) mainScene.lookup("#usrTxt")).getText();
                mdp =  ((PasswordField) mainScene.lookup("#pwTxt")).getText();
                adresse = ((TextField) mainScene.lookup("#addTxt")).getText();
                setUsername(username);
                setMdp(mdp);
                setAdresse(adresse);
                System.out.println(username+" "+mdp+" "+adresse);
                try {
                    cnt = DriverManager.getConnection("jdbc:mysql://"+adresse+"/?"
                        + "user="+username+"&password="+mdp);
                    statement = cnt.createStatement();
                    if (statement.executeUpdate("CREATE DATABASE IF NOT EXISTS iathinkers") != -1) {
                        statement.executeUpdate("USE iathinkers");
                        System.out.println("Database initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`Type` (`idType` INT NOT NULL AUTO_INCREMENT,`typeName` VARCHAR(45) NOT NULL, PRIMARY KEY (`idType`), UNIQUE INDEX `typeName_UNIQUE` (`typeName` ASC)) ENGINE = InnoDB") != -1 ) {
                         ResultSet rs = statement.executeQuery("SELECT * FROM Type");
                         if (!rs.next()) {
                            if (statement.executeUpdate("INSERT INTO Type (idType,typeName) VALUES (1,\'Wall\'), (2,\'Door\'), (3,\'PointA\'), (4,\'PointB\')") != -1) {
                                System.out.println("Type table initialized");
                            }
                         }   
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`Object` (  `idObject` INT NOT NULL AUTO_INCREMENT,`type` INT NOT NULL,`height` INT NULL,`width` INT NULL,`posX` INT NULL,`posY` INT NULL,`isVertical` TINYINT NULL,PRIMARY KEY (`idObject`),CONSTRAINT `type` FOREIGN KEY (`type`) REFERENCES `iathinkers`.`Type` (`idType`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE = InnoDB") != -1) {
                        System.out.println("Object table initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`HousePlan` (\n" +
                                                "  `idHousePlan` INT NOT NULL AUTO_INCREMENT,\n" +
                                                "  `name` VARCHAR(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idHousePlan`),\n" +
                                                "  UNIQUE INDEX `name_UNIQUE` (`name` ASC))\n" +
                                                "ENGINE = InnoDB;") != -1 ) {
                        System.out.println("HousePlan table initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`Composition` (\n" +
                                                "  `idComposition` INT NOT NULL AUTO_INCREMENT,\n" +
                                                "  `object` INT NOT NULL,\n" +
                                                "  `plan` INT NOT NULL,\n" +
                                                "  PRIMARY KEY (`idComposition`),\n" +
                                                "  INDEX `plan_idx` (`plan` ASC),\n" +
                                                "  INDEX `object_idx` (`object` ASC),\n" +
                                                "  CONSTRAINT `object`\n" +
                                                "    FOREIGN KEY (`object`)\n" +
                                                "    REFERENCES `iathinkers`.`Object` (`idObject`)\n" +
                                                "    ON DELETE NO ACTION\n" +
                                                "    ON UPDATE NO ACTION,\n" +
                                                "  CONSTRAINT `plan`\n" +
                                                "    FOREIGN KEY (`plan`)\n" +
                                                "    REFERENCES `iathinkers`.`HousePlan` (`idHousePlan`)\n" +
                                                "    ON DELETE NO ACTION\n" +
                                                "    ON UPDATE NO ACTION)\n" +
                                                "ENGINE = InnoDB;") != -1) {
                        System.out.println("Composition table initialized");
                    }
                    //writeResultSet(resultSet);
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText("Connection achieved !");
                    encrypt();
                    System.out.println("Encryption achieved !");
                } catch (SQLException e) {
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText("Connection failed, please check your credentials");
                    e.printStackTrace();
                } finally {
                    close();
                }
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        
        Button btnRetour = new Button("Back..");
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[0]);
            }
        });
        HBox hbBtnRetour = new HBox(10);
        hbBtnRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnRetour.getChildren().add(btnRetour);
        grid.add(hbBtnRetour, 2, 5);
        
        this.getChildren().add(grid);
    }
    
     /**
      * Method used to close the ResultSet safely
      */
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (cnt != null) {
                cnt.close();
            }
        } catch (Exception e) {

        }
    }
    /**
     * Getter of the username variable
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter username variable
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the adresse variable
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setter of the adresse variable
     * @param adresse 
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter of the mdp (password) variable
     * @return mdp
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Setter of the mdp variable
     * @param mdp 
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    /**
     * Encrypting method using Vigenère algorithm with the username as a key
     */
    public void encrypt() {
        char[] username = getUsername().toCharArray();
        char[] adresse = getAdresse().toCharArray();
        char[] pwd = getMdp().toCharArray();
        
        
        for(int i = 0; i < adresse.length; i++) {
            adresse[i] = (char) ((int) adresse[i] + (int) username[i % username.length]);
        }
        
        for (int i = 0; i < pwd.length; i++) {
            pwd[i] = (char) ((int) pwd[i] + (int) username[i % username.length]);
        }
        
        for(int i = 0; i < username.length; i++) {
            username[i] = (char) ((int)username[i]*2);
        }
        
        
        String usrEncrypted = new String(username);
        String adrEncrypted = new String(adresse);
        String pwdEncrypted = new String(pwd);
        
        BufferedWriter writer = null;
        try {
            File logFile = new File("usrdata.txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(usrEncrypted+System.getProperty("line.separator"));
            writer.write(adrEncrypted+System.getProperty("line.separator"));
            writer.write(pwdEncrypted);
        } catch (IOException iOException) {
                        
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {

            }
        }
        
    }
    
    
}

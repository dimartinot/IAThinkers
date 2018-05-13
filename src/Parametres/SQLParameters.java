/**
 * This package contains everything needed for SQL log-in Scene
 */
package Parametres;

//JavaFX Imports
import static Menu.MainMenu.getLanguage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


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
    
    private ResourceBundle messages;
    /**
     * Constructor of the SQLParameters class {@link Parametres.SQLParameters}
     * @param primaryStage Stage variable used to go back to the MainMenu <i> Scene </i>
     * @param sceneTab Scene Array used to get the MainMenu <i> Scene </i>
     */
    
    public SQLParameters(Stage primaryStage, Scene[] sceneTab) {
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Parametres/Parameters",l);        
        cnt = null;
        Scene mainScene = sceneTab[5];
        BorderPane grid = new BorderPane();
        
        VBox container = new VBox();
        container.setPadding(new Insets(10,10,10,10));
        GridPane entryGrid = new GridPane();
        entryGrid.setPadding(new Insets(50, 50, 50, 50));
        entryGrid.setVgap(20);
        
        Text scenetitle = new Text(messages.getString("WELCOME"));
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        container.getChildren().add(scenetitle);
        
        Label adresseLbl = new Label(messages.getString("MYSQL SERVER ADDRESS:"));
        entryGrid.add(adresseLbl,0,1);
        
        TextField adresseTextField = new TextField();
        adresseTextField.setId("addTxt");
        entryGrid.add(adresseTextField,1,1);
                
        Label usernameLbl = new Label(messages.getString("IDENTIFIER:"));
        entryGrid.add(usernameLbl,0,2);

        TextField userTextField = new TextField();
        userTextField.setId("usrTxt");
        entryGrid.add(userTextField,1,2);

        Label pwLbl = new Label(messages.getString("PASSWORD:"));
        entryGrid.add(pwLbl,0,3);

        
        PasswordField pwBox = new PasswordField();
        pwBox.setId("pwTxt");
        entryGrid.add(pwBox,1,3);
      
        container.getChildren().add(entryGrid);
        
        //Info Case
        Text infoConnexion = new Text("");
        infoConnexion.setId("infoConnexion");
        
        Button btn = new Button(messages.getString("CONNECTION"));
        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                username = ((TextField) mainScene.lookup("#usrTxt")).getText();
                mdp =  ((PasswordField) mainScene.lookup("#pwTxt")).getText();
                adresse = ((TextField) mainScene.lookup("#addTxt")).getText();
                setUsername(username);
                setMdp(mdp);
                setAdresse(adresse);
                try {
                    cnt = DriverManager.getConnection("jdbc:mysql://"+adresse+"/?"
                        + "user="+username+"&password="+mdp);
                    statement = cnt.createStatement();
                    if (statement.executeUpdate("CREATE DATABASE IF NOT EXISTS iathinkers") != -1) {
                        statement.executeUpdate("USE iathinkers");
                        System.out.println("Database initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`Type` (`idType` INT NOT NULL AUTO_INCREMENT,`typeName` VARCHAR(45) NOT NULL, PRIMARY KEY (`idType`), UNIQUE INDEX `typeName_UNIQUE` (`typeName` ASC)) ENGINE = InnoDB") != -1 ) {
                        System.out.println("Type table initialized");
                        ResultSet rs = statement.executeQuery("SELECT * FROM Type");
                        if (!rs.next()) {
                            if (statement.executeUpdate("INSERT INTO Type (idType,typeName) VALUES (1,\'Wall\'), (2,\'Door\'), (3,\'PointA\'), (4,\'PointB\')") != -1) {
                                System.out.println("Type values inserted");
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
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`statistics` (\n" +
                        "  `idstatistics` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `time` INT NOT NULL,\n" +
                        "  `lengthOfPath` INT NOT NULL,\n" +
                        "  `numberOfBlock` INT NOT NULL,\n" +
                        "  `numberOfAvailableCell` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`idstatistics`));") != -1) {
                        System.out.println("Statistics table initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`Shapes` (" +
                            "  `idShapes` INT NOT NULL AUTO_INCREMENT," +
                            "  `shapeName` VARCHAR(45) UNIQUE NOT NULL," +
                            "  PRIMARY KEY (`idShapes`))" +
                            "ENGINE = InnoDB;") != -1) {
                        System.out.println("Shapes table initialized");
                        ResultSet rs = statement.executeQuery("SELECT * FROM Shapes");
                        if (!rs.next()) {
                            if (statement.executeUpdate("INSERT INTO Shapes (shapeName) VALUES (\'Triangle\'), (\'Rectangle\'), (\'Square\'), (\'Pentagon\'), (\'Hexagon\'), (\'Octagon\'), (\'Circle\')") != -1) {
                                System.out.println("Shapes values set");
                            }
                        }                          
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`FreehandObject` (\n" +
                            "  `idObject` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `objetName` VARCHAR(45) NULL,\n" +
                            "  PRIMARY KEY (`idObject`))\n" +
                            "ENGINE = InnoDB;") != -1) {
                        System.out.println("FreehandObject table initialized");
                    }
                    if (statement.executeUpdate("CREATE TABLE IF NOT EXISTS `iathinkers`.`ShapesInstances` (\n" +
                                                "  `idShapesInstances` INT NOT NULL AUTO_INCREMENT,\n" +
                                                "  `shapeType` INT NOT NULL,\n" +
                                                "  `fillColor` VARCHAR(45) NOT NULL,\n" +
                                                "  `borderColor` VARCHAR(45) NULL,\n" +
                                                "  `posX` DOUBLE NOT NULL,\n" +
                                                "  `posY` DOUBLE NOT NULL,\n" +
                                                "  `sizeX` DOUBLE NOT NULL,\n" +
                                                "  `sizeY` DOUBLE NOT NULL,\n" +
                                                "  `object` INT NULL,\n" +
                                                "  PRIMARY KEY (`idShapesInstances`),\n" +
                                                "  INDEX `shapeType_idx` (`shapeType` ASC),\n" +
                                                "  INDEX `object_idx` (`object` ASC),\n" +
                                                "  CONSTRAINT `shapeType`\n" +
                                                "    FOREIGN KEY (`shapeType`)\n" +
                                                "    REFERENCES `iathinkers`.`Shapes` (`idShapes`)\n" +
                                                "    ON DELETE NO ACTION\n" +
                                                "    ON UPDATE NO ACTION,\n" +
                                                "  CONSTRAINT `freehandobject`\n" +
                                                "    FOREIGN KEY (`object`)\n" +
                                                "    REFERENCES `iathinkers`.`FreehandObject` (`idObject`)\n" +
                                                "    ON DELETE NO ACTION\n" +
                                                "    ON UPDATE NO ACTION)\n" +
                                                "ENGINE = InnoDB;") != -1) {
                        System.out.println("ShapesInstances table initialized");
                    }
                    //writeResultSet(resultSet);
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText(messages.getString("CONNECTIONACHIEVED"));
                    encrypt();
                    System.out.println("Encryption achieved !");
                } catch (SQLException e) {
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText(messages.getString("CONNECTIONFAILED"));
                    e.printStackTrace();
                } finally {
                    close();
                }
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        
        Button btnRetour = new Button(messages.getString("BACK"));
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setResizable(false);
                primaryStage.setScene(sceneTab[0]);
            }
        });
        
        hbBtn.getChildren().add(btnRetour);
        container.getChildren().add(hbBtn);
        
        // Menu Bar
        MenuBar menuBar = new MenuBar();
        // Language Menu
        Menu menuLanguage = new Menu(messages.getString("Language"));
        // Menu Items
        RadioMenuItem english = new RadioMenuItem("English");
        RadioMenuItem french = new RadioMenuItem("Français");
        if (l.equals(new Locale("fr","FR"))) {
            french.setSelected(true);
        } else if (l.equals(new Locale("en","UK"))) {
            english.setSelected(true);
        }
        french.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(messages.getString("alertTitle"));
                alert.setHeaderText(messages.getString("alertHeader"));
                alert.setContentText(messages.getString("alertContent"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (english.isSelected() == false) {
                        french.setSelected(true);
                    } else {
                        try {
                            FileWriter f = new FileWriter(new File("language.txt"),false);
                            f.write("fr");
                            f.flush();
                            f.close();
                        } catch (IOException ex) {
                            try {
                                File file = new File("language.txt");
                                file.createNewFile();
                                FileWriter f = new FileWriter(file,false);    
                                f.write("fr");
                                f.flush();
                                f.close();
                            } catch (IOException ex1) {
                            }
                        }
                        english.setSelected(false);

                    }
                    restartApplication();
                } else {
                    if (english.isSelected() == true) {
                        french.setSelected(false);
                    }
                }   
            }
        });
        
        english.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(messages.getString("alertTitle"));
                alert.setHeaderText(messages.getString("alertHeader"));
                alert.setContentText(messages.getString("alertContent"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (french.isSelected() == false) {
                        english.setSelected(true);
                    } else {
                        try {
                            FileWriter f = new FileWriter(new File("language.txt"),false);
                            f.write("en");
                            f.flush();
                            f.close();
                        } catch (IOException ex) {
                            try {
                                File file = new File("language.txt");
                                file.createNewFile();
                                FileWriter f = new FileWriter(file,false);    
                                f.write("en");
                                f.flush();
                                f.close();
                            } catch (IOException ex1) {
                            }
                        }
                        french.setSelected(false);
                    }
                    restartApplication();
                } else {
                    if (french.isSelected() == true) {
                        english.setSelected(false);
                    }
                }   
            }
        });
        menuLanguage.getItems().addAll(english,french);
        menuBar.getMenus().addAll(menuLanguage);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        grid.setBottom(infoConnexion);
        grid.setCenter(container);
        grid.setTop(menuBar);
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
    
    
    /**
     * Method that will restart eh application to apply the new language settings. It finds the executable generated by the compilation of the javacode then executes it before closing itself.
     */
    public void restartApplication()
    {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar;
        try {
            currentJar = new File(iathinkers.IAThinkers.class.getProtectionDomain().getCodeSource().getLocation().toURI());
             /* is it a jar file? */
            if(!currentJar.getName().endsWith(".jar"))
              return;

            /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<String>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            try {
                builder.start();
            } catch (IOException ex) {
                Logger.getLogger(SQLParameters.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        } catch (URISyntaxException ex) {
            Logger.getLogger(SQLParameters.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }
    
    /**
    * Method used to read and decrypt user data saved in the text file 
    */
    public static String[] getSQLInfos() {
        String[] res = new String[3];
        try {
            FileReader fr = new FileReader("usrdata.txt");
            BufferedReader textReader = new BufferedReader(fr);
            String[] textData = new String[3];
            for (int i = 0; i < textData.length; i++ ) {
                try {
                    textData[i] = textReader.readLine();
                } catch (IOException ex) {
                    
                }
            }
            try {
                textReader.close();
            } catch (IOException ex) {
                
            }
            
            char[] usrChar = textData[0].toCharArray();
            char[] adrChar = textData[1].toCharArray();
            char[] pwdChar = textData[2].toCharArray();
            
            for (int i = 0; i < usrChar.length; i++) {
                usrChar[i] = (char)( (int) usrChar[i] / 2);
            }
            
            for (int i = 0; i < adrChar.length; i++) {
                adrChar[i] = (char) ((int)adrChar[i] - (int) usrChar[i % usrChar.length]);
            }
            
            for (int i = 0; i < pwdChar.length; i++) {
                pwdChar[i] = (char) ((int)pwdChar[i] - (int) usrChar[i % usrChar.length]);
            }
            res[0] = String.copyValueOf(usrChar);
            res[1] = String.copyValueOf(pwdChar);
            res[2] = String.copyValueOf(adrChar);
            return res;
                        
        } catch (FileNotFoundException ex) {
            res[0] = "";
            res[1] = "";
            res[2] = "";
            return res;
        }
    }
}
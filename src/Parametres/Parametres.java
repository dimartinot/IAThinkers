/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parametres;

//Import pour JavaFX
import iathinkers.IAThinkers;
import Menu.Menu;
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

//
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe de la <i> scène </i> Parametres {@link Parametres} affichant les <i> TextArea </i> de connexion au serveur MySQL
 * @author Admin
 */
public class Parametres extends Parent{
    /**
     * Variables de connexion au serveur MySQL
     */
    private String username;
    private String adresse;
    private String mdp;

    private Statement statement;
    private ResultSet resultSet;
    private Connection cnt;
    /**
     * Constructeur de la classe Parametres {@link Parametres}
     * @param primaryStage : variable Stage permettant de retourner à la <i> scène </i> Menu {@link Menu}
     * @param sceneTab tableau de scene permettant de retourner à la <i> scène </i> Menu {@link Menu}
     */
    
    public Parametres(Stage primaryStage, Scene[] sceneTab) {
        cnt = IAThinkers.getConnect();
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
                System.out.println(username+" "+mdp+" "+adresse);
                try {
                    cnt = DriverManager.getConnection("jdbc:mysql://"+adresse+"/TEST?"
                        + "user="+username+"&password="+mdp);
                    statement = cnt.createStatement();
                    resultSet = statement.executeQuery("select * from TEST.test");
                    writeResultSet(resultSet);
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText("Connexion établie !");
                } catch (SQLException e) {
                    Text texte = (Text) mainScene.lookup("#infoConnexion");
                    texte.setText("Connexion échouée,\nveuillez vérifier vos identifiants");
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
    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString(2);
            System.out.println("User: " + user);
        }
    }
     // You need to close the resultSet
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
     * Getter de la variable username
     * @return 
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter de la variable username
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter de la variable adresse
     * @return 
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setter de la variable adresse
     * @param adresse 
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter de la variable mdp
     * @return 
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Setter de la variable mdp
     * @param mdp 
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public void saveCredentials() {
        
    }
    
    
}

/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static Menu.MainMenu.getLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class Statistics extends Parent{
    
    /**
    * String variables used as SQL credentials
    */
    private String username;
    private String adresse;
    private String mdp;
    private ResourceBundle messages;
    private ObservableList<Input> data;
    
    public Statistics (Stage primaryStage, Scene[] sceneTab) {
        
        //we setup the datas variable
        ObservableList<Input> data = FXCollections.observableArrayList();        
        //We setup language settings
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Statistics/Statistics",l);
        
        //We setup the main container
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 0, 0, 0));
        
        //We setup the menubar
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        MenuItem homeMenu = new MenuItem(messages.getString("HOME"));
        homeMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                primaryStage.setScene(sceneTab[0]);
            }
        });
        Menu optionMenu = new Menu(messages.getString("OPTION"));
        optionMenu.getItems().add(homeMenu);
        menuBar.getMenus().addAll(optionMenu);
        
        //We setup the table
        TableView<Input> table = new TableView<Input>();
        table.setEditable(false);//The datas shown are uneditable
        table.prefWidthProperty().bind(primaryStage.widthProperty());
        //We, setup all the needed columns
        TableColumn columnID = new TableColumn("idStatistics");
        columnID.setCellValueFactory(
                new PropertyValueFactory<Input,Integer>("id")
        );
        TableColumn columnTime = new TableColumn(messages.getString("TIME"));
        columnTime.setCellValueFactory(
                new PropertyValueFactory<Input,Integer>("time")
        );
        TableColumn columnPath = new TableColumn(messages.getString("PATH"));
        columnPath.setCellValueFactory(
                new PropertyValueFactory<Input,Integer>("lengthOfPath")
        );
        TableColumn columnBlock = new TableColumn(messages.getString("BLOCK"));
        columnBlock.setCellValueFactory(
                new PropertyValueFactory<Input,Integer>("numberOfBlock")
        );
        TableColumn columnCell = new TableColumn(messages.getString("CELL"));
        columnCell.setCellValueFactory(
                new PropertyValueFactory<Input,Integer>("numberOfAvailableCell")
        );
        table.getColumns().addAll(columnID,columnTime,columnPath,columnBlock,columnCell);
        String[] credentials = Parametres.SQLParameters.getSQLInfos();
        this.username = credentials[0];
        this.mdp = credentials[1];
        this.adresse = credentials[2];
        Connection connect;
        try {
            connect = DriverManager.getConnection("jdbc:mysql://"+this.adresse+"/iathinkers?"
                    + "user="+this.username+"&password="+this.mdp);
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM statistics");
            //We, dynamically, get all the data we need for statistics purposes
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getInt(5));
                data.add(new Input(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println("MySQL Error !");
        }
        table.setItems(data);
        //We add the table and menus to our vbox
        vbox.getChildren().addAll(menuBar,table);
        //System.out.println(fields.toString());
        this.getChildren().add(vbox);
    }

    public String getUsername() {
        return username;
    }
    
    public String getAdresse() {
        return adresse;
    }

    public String getMdp() {
        return mdp;
    }

}

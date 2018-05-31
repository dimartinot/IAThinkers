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
import java.util.Locale;
import java.util.ResourceBundle;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

/**
 * The statistic class : it shows the data from the application of the {@link Plan.Algorithm.AStar} algorithm to the {@link Plan.Plan} scene in a JavaFX TableView object.
 * @author IAThinkers
 */
public class Statistics extends Parent{
    
    /**
    * String variables used as SQL credentials
    */
    private String username;
    private String adresse;
    private String mdp;
    /**
     * The ResourceBundle variable, useful to get the translated Strings
     */
    private ResourceBundle messages;
    
    /**
     * The data to be displayed in the TableView using the homemade {@link Input} class
     */
    private ObservableList<Input> data;
    
    /**
     * The Statistics constructor calling, as usual the main stage and the array of all accessible scenes
     * @param primaryStage
     * @param sceneTab 
     */
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
        //We create all menu items
        MenuItem homeMenu = new MenuItem(messages.getString("EXIT"));
        homeMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                primaryStage.setResizable(false);
                primaryStage.setScene(sceneTab[0]);
            }
        });
        Menu graphMenu = new Menu(messages.getString("NEWGRAPH"));
        //We setup the graphmenu items
        MenuItem scatterOption = new MenuItem(messages.getString("SCATTER"));
        scatterOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setScatterOption(primaryStage);
            }
        });
        graphMenu.getItems().addAll(scatterOption);
        Menu fileMenu = new Menu(messages.getString("FILE"));
        
        Menu optionMenu = new Menu(messages.getString("OPTIONS"));
        
        MenuItem refresh = new MenuItem(messages.getString("REFRESH"));
        refresh.setAccelerator(KeyCombination.keyCombination("F5"));
        refresh.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent t) {
               setData(data);
           } 
        });
        MenuItem reset = new MenuItem(messages.getString("RESET"));
        reset.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent t) {
               Alert alert = new Alert(AlertType.CONFIRMATION);
               alert.setTitle(messages.getString("RESETCONFIRMATION"));
               alert.setHeaderText(messages.getString("RESETHEADER"));
               alert.setContentText(messages.getString("RESETCONTENT"));
               
               Optional<ButtonType> result = alert.showAndWait();
               if (result.get() == ButtonType.OK) {
                   resetData(data);
               }
           } 
        });
        optionMenu.getItems().addAll(refresh,reset);
        
        fileMenu.getItems().addAll(graphMenu, new SeparatorMenuItem(),homeMenu);
        menuBar.getMenus().addAll(fileMenu,optionMenu);
        
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
        setData(data);
        table.setItems(data);
        //We add the table and menus to our vbox
        vbox.getChildren().addAll(menuBar,table);
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
    
    /**
     * This method takes a label of a ChoiceBox as an input and return its equivalent in the MySQL database field name. For instance, <i> idStatistics </i> will become <i> id </i>
     * @param a the label taken as an input
     * @return the MySQL field name
     */
    public String fromLabelToSQLFieldName(String a) {
        if (a.equals("idStatistics")) {
            return "idstatistics";
        } else if (a.equals(messages.getString("TIME"))) {
            return "time";
        } else if (a.equals(messages.getString("PATH"))) {
            return "lengthOfPath";
        } else if (a.equals(messages.getString("BLOCK"))) {
            return "numberOfBlock";
        } else if (a.equals(messages.getString("CELL"))) {
            return "numberOfAvailableCell";
        } else {
            return "";
        }
    }
    
    /**
     * This method will create all the needed {@link Input} variables by looking into the database and getting out all the data
     * @param data the data variable to increment of all the {@link Input} variables
     */
    public void setData(ObservableList<Input> data) {
        Connection connect;
        data.clear();
        try {
            connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
                    + "user="+this.getUsername()+"&password="+this.getMdp());
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM statistics");
            //We, dynamically, get all the data we need for statistics purposes
            while (rs.next()) {
                data.add(new Input(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println("MySQL Error !");
        }
    }
    
    /**
     * This will empty the data variable
     * @param data the data ObservableList variable to empty
     */
    public void resetData(ObservableList<Input> data) {
        Connection connect;
        data.clear();
        try {
            connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
                    + "user="+this.getUsername()+"&password="+this.getMdp());
            Statement statement = connect.createStatement();
            if (statement.executeUpdate("TRUNCATE TABLE statistics") == 0) {
                System.out.println("Database emptied !");
            }
            //We, dynamically, get all the data we need for statistics purposes
        } catch (SQLException ex) {
            System.out.println("MySQL Error ! : "+ex.toString());
        }
    }
    
    /**
     * This will achieve the same objective as the setData method but will, instead of creating {@link Input} objets, implement XYChart.Data variables
     * @param x the x field name of the chart, set as a String
     * @param y the y field name of the chart, set as a String
     * @return an ArrayList of XYChart.Data to create an efficient chart
     */
    public ArrayList<XYChart.Data<Integer,Integer>> getDataChart (String x, String y) {
        ArrayList<XYChart.Data<Integer,Integer>> res = new ArrayList<XYChart.Data<Integer,Integer>>();
        if (!x.equals(y)) {
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
                        + "user="+this.getUsername()+"&password="+this.getMdp());
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery("SELECT "+fromLabelToSQLFieldName(x)+", "+fromLabelToSQLFieldName(y)+" FROM statistics");
                //We, dynamically, get all the data we need for statistics purposes
                while (rs.next()) {
                    res.add(new XYChart.Data(rs.getInt(1),rs.getInt(2)));
                }
            } catch (SQLException ex) {
                System.out.println("MySQL Error !");
            }
        } else {
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
                        + "user="+this.getUsername()+"&password="+this.getMdp());
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery("SELECT "+fromLabelToSQLFieldName(x)+" FROM statistics");
                //We, dynamically, get all the data we need for statistics purposes
                while (rs.next()) {
                    res.add(new XYChart.Data(rs.getInt(1),rs.getInt(1)));
                }
            } catch (SQLException ex) {
                System.out.println("MySQL Error !");
            }
        }
        return res;
    }
    
    public void setScatterOption(Stage primaryStage) {
        
        //The main stage of this window
        Stage scatterOptionWindow = new Stage();
        
        String[] options = new String[2];
        BorderPane secondaryLayout = new BorderPane();
        Label instructions = new Label(messages.getString("INSTRUCTIONS"));
        BorderPane.setMargin(instructions, new Insets(0,0,20,0));
        secondaryLayout.setTop(instructions);
        
        HBox choices = new HBox();
        choices.setSpacing(10);
        
        ChoiceBox cbX = new ChoiceBox(FXCollections.observableArrayList(
            "idStatistics", messages.getString("TIME"), messages.getString("PATH"), messages.getString("BLOCK"), messages.getString("CELL"))
        );
        ChoiceBox cbY = new ChoiceBox(FXCollections.observableArrayList(
            "idStatistics", messages.getString("TIME"), messages.getString("PATH"), messages.getString("BLOCK"), messages.getString("CELL"))
        );
        
        choices.getChildren().addAll(cbX,cbY);
        
        HBox buttons = new HBox();
        buttons.setSpacing(30);
        
        BorderPane.setMargin(buttons, new Insets(10,10,10,10));
        
        secondaryLayout.setCenter(choices);
        
        //When this button is clicked, we initializes a new window with only the chart displayed
        Button okButton = new Button(messages.getString("DRAW"));
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                if (cbX.getValue() != null && cbY.getValue() != null) {
                    options[0] = cbX.getValue().toString();
                    options[1] = cbY.getValue().toString();
                    ArrayList<XYChart.Data<Integer,Integer>> dataToDisplay = getDataChart(options[0],options[1]);
                    Stage chartStage = new Stage();
                    chartStage.setTitle(messages.getString("SCATTERSTAGE"));
                    //We sort the array to get the highest x value
                    dataToDisplay.sort(Comparator.comparingDouble(d -> d.getXValue().doubleValue()));
                    NumberAxis xAxis = new NumberAxis(0, dataToDisplay.get(dataToDisplay.size()-1).getXValue()+10, 10);
                    xAxis.setLabel(options[0]);
                    
                    //We sort the array to get the highest y value
                    dataToDisplay.sort(Comparator.comparingDouble(d -> d.getYValue().doubleValue()));
                    NumberAxis yAxis = new NumberAxis(0,dataToDisplay.get(dataToDisplay.size()-1).getYValue()+10,10);
                    yAxis.setLabel(options[1]);
                    //The chart variable
                    ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
                    XYChart.Series series = new XYChart.Series();
                    series.setName("\""+options[1]+"\" "+messages.getString("FUNCTION")+" \""+options[0]);
                    series.getData().addAll(dataToDisplay);
                    sc.getData().add(series);
                    Scene scene = new Scene(sc, 500, 400);
                    chartStage.setScene(scene);
                    chartStage.show();
                }
            }
        });
        
        Button cancelButton = new Button(messages.getString("CANCEL"));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                scatterOptionWindow.close();
            }
        });
        buttons.getChildren().addAll(okButton,cancelButton);
        
        
        secondaryLayout.setBottom(buttons);
        
        Scene secondaryScene = new Scene(secondaryLayout, 350, 200);
        scatterOptionWindow.setScene(secondaryScene);
        scatterOptionWindow.setTitle(messages.getString("SCATTERSETTINGS"));
        //Specifies the modality for the windpw
        scatterOptionWindow.initModality(Modality.WINDOW_MODAL);
        //Specifies the parent of this stage
        scatterOptionWindow.initOwner(primaryStage);
        scatterOptionWindow.show();
    }

}

/**
 * This package concerns every classes used the house plan display
 *
 */
package Plan;

import static Menu.MainMenu.getLanguage;
import Objet.PointType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Plan.Algorithm.*;
import javafx.geometry.Pos;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;

/**
 * Class of the House Plan <i> Scene </i>. It is composed of a grid of multiple {@link Cell} object.
 * @author IAThinkers
 */
public class Plan extends Parent{
    /**
     * Constructor of the Plan class. Initialises every JavaFX components we can see and interact with in the Plan scene
     * @param primaryStage Stage variable used to go back to the MainMenu <i> Scene </i> {@link Menu.MainMenu}
     * @param sceneTab Scene Array used in the {@link Plan.Grid} class and to get the MainMenu <i> Scene </i> {@link Menu.MainMenu}
     */
    
    /**
    * String variables used as SQL credentials
    */
    private String username;
    private String adresse;
    private String mdp;
    /**
     * {@link Plan.Grid} Object
     */
    private Grid objetGrid;
    
    private ResourceBundle messages;
    
    /**
     * {@link Plan.Algorithm.Node} ArrayList used to describe the path between the two points 
     */
    private ArrayList<Node> path;
    
    /**
     * Plan constructor.
     * @param primaryStage
     * @param sceneTab 
     */
    public Plan(Stage primaryStage, Scene[] sceneTab) {
                
        this.path = new ArrayList<Node>();
        
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Plan/Plan",l);
       
        this.getStylesheets().add(this.getClass().getResource("plan.css").toExternalForm());
        
        //Main horizontale box 
        HBox hbox = new HBox();
        
        //Vertical menu
        VBox menuVertical = new VBox();
        menuVertical.setPadding(new Insets(0, 0, 0, 5));
        //Grid composing the menu
        GridPane grid = new GridPane();
        
        //Selecting list
        ComboBox<String> choix = new ComboBox();
        choix.getItems().add(messages.getString("WALL"));
        choix.getItems().add(messages.getString("DOOR"));
        choix.getItems().add(messages.getString("STARTING POINT"));
        choix.getItems().add(messages.getString("ENDING POINT"));

        //Define the textfields for the wall properties
        TextField height = new TextField();
        height.setId("height");
        height.setPrefWidth(30);
        Label heightLbl = new Label(messages.getString("HEIGHT"));
        // force the textfield only to contain numeric characters
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
        Label widthLbl = new Label(messages.getString("WIDTH"));
        // force the textfield only to contain numeric characters
        width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    height.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        //Define the orientation of the door to be put
        ComboBox<String> orientation = new ComboBox();
        orientation.getItems().add(messages.getString("HORIZONTAL"));
        orientation.getItems().add(messages.getString("VERTICAL"));
        orientation.setId("orientation");
        Label orientationLbl = new Label(messages.getString("ORIENTATION"));
        
        
        
        
        //Define the executed action on the choice of an object
        choix.setOnAction((ActionEvent e) -> {
            String choice = choix.getSelectionModel().getSelectedItem();
            String Wall = messages.getString("WALL");
            String Door = messages.getString("DOOR");
            if (choice.equals(Wall)) {
                //Remove personnalisation choices for a door
                     
                    grid.clearConstraints(orientation);
                    grid.clearConstraints(orientationLbl);
                    grid.getChildren().remove(orientation);
                    grid.getChildren().remove(orientationLbl);

                    
                    grid.setConstraints(height,1,4);
                    grid.setConstraints(heightLbl,1,3);
                    grid.setConstraints(width,1,6);
                    grid.setConstraints(widthLbl,1,5);
                    //Add the choices for a wall
                    grid.getChildren().add(height);
                    grid.getChildren().add(width);
                    grid.getChildren().add(heightLbl);
                    grid.getChildren().add(widthLbl);
                    
            } else if (choice.equals(Door)) {
              //Remove the choices for a wall
                     
                    grid.clearConstraints(height);
                    grid.clearConstraints(width);
                    grid.clearConstraints(heightLbl);
                    grid.clearConstraints(widthLbl);
                    grid.getChildren().remove(height);
                    grid.getChildren().remove(width);
                    grid.getChildren().remove(heightLbl);
                    grid.getChildren().remove(widthLbl);
                    
                    grid.setConstraints(orientationLbl,1,3);
                    grid.setConstraints(orientation,1,4);
                     //Add the choices for a door
                    grid.getChildren().add(orientation);
                    grid.getChildren().add(orientationLbl);   
            } else {
                    grid.clearConstraints(height);
                    grid.clearConstraints(width);
                    grid.clearConstraints(heightLbl);
                    grid.clearConstraints(widthLbl);
                    grid.getChildren().remove(height);
                    grid.getChildren().remove(width);
                    grid.getChildren().remove(heightLbl);
                    grid.getChildren().remove(widthLbl);
                    
                    grid.clearConstraints(orientation);
                    grid.clearConstraints(orientationLbl);
                    grid.getChildren().remove(orientation);
                    grid.getChildren().remove(orientationLbl);
            }
        });
        
        
        
        choix.setId("choixobjet");
        grid.setVgap(10);
        grid.setConstraints(choix,1,2);
        
        //Label liste
        Label label = new Label(messages.getString("OBJECT CHOICE"));
        grid.setConstraints(label, 1, 1); // column=1 row=1
        
        //Info Case
        Text infoCase = new Text(10,50,"<_;_>");
        infoCase.setId("infocase");
        grid.setConstraints(infoCase,1,7);
        
        //Grid Object initialisation
        Double gridHeight = (Double) sceneTab[1].getHeight()*70/100;
        Double gridWidth = (Double) sceneTab[1].getHeight()*70/100;
        this.objetGrid = new Grid(sceneTab[1],gridHeight.intValue(),gridWidth.intValue());

        Label objectListLbl = new Label(messages.getString("LISTING OF ALL CREATED OBJECTS"));
        grid.setConstraints(objectListLbl,1,8);
        
        
        
        ComboBox<String> objectList = new ComboBox();
        objectList.setId("objectlist");
        
        HBox boxButtons = new HBox();
        boxButtons.setSpacing(10);
        
        Button closeDoor = new Button(messages.getString("CLOSE"));
        closeDoor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                String selectedObject = objectList.getSelectionModel().getSelectedItem();
                StringTokenizer splitObject = new StringTokenizer(selectedObject, "(");
                String type = splitObject.nextElement().toString();
                String data = splitObject.nextElement().toString();
                String Door = messages.getString("DOOR");
                String doorData[] = data.split(", ");
                try {
                    int height = Integer.parseInt(doorData[0]);
                    int width = Integer.parseInt(doorData[1]);
                    int posX = Integer.parseInt(doorData[2]);
                    int posY = Integer.parseInt(doorData[3]);
                    Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(posX)+"-"+(posY));
                    if (rectangle.getFill() == Color.ANTIQUEWHITE) {
                        rectangle.setFill(Color.RED);
                    } else if (rectangle.getFill() == Color.RED){
                        rectangle.setFill(Color.ANTIQUEWHITE);
                    }
                    objetGrid.changeDoor(height,width,posX,posY);
                } catch (NumberFormatException e) {

                }
            }
        });
        
        //Initialisation of the delete Button
        Button deleteButton = new Button(messages.getString("DELETE"));
            //When we attempt to delete an object, we will have to get the data out of the selected option of the ComboBox
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String selectedObject = objectList.getSelectionModel().getSelectedItem();
                        StringTokenizer splitObject = new StringTokenizer(selectedObject, "(");
                        String type = splitObject.nextElement().toString();
                        String data = splitObject.nextElement().toString();
                        String Wall = messages.getString("WALL");
                        String Door = messages.getString("DOOR");
                        String PointA = messages.getString("POINTA");
                        String PointB = messages.getString("POINTB");
                        if (type.equals(Wall)) {
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
                                            System.out.println(messages.getString("WALL CORRECTLY DELETED"));
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println(messages.getString("ERROR ! WALL NOT FOUND"));
                                        }
                                    } catch (NumberFormatException e) {

                                    }
                        } else if (type.equals(Door)) {
                            String doorData[] = data.split(", ");
                                try {
                                        int height = Integer.parseInt(doorData[0]);
                                        int width = Integer.parseInt(doorData[1]);
                                        int posX = Integer.parseInt(doorData[2]);
                                        int posY = Integer.parseInt(doorData[3]);
                                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(posX)+"-"+(posY));
                                        if (rectangle.getFill() == Color.ANTIQUEWHITE || rectangle.getFill() == Color.RED) {
                                            rectangle.setFill(Color.ALICEBLUE);
                                        }
                                        if (objetGrid.deleteDoor(height, width, posX, posY)) {
                                            System.out.println(messages.getString("DOOR CORRECTLY DELETED"));
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println(messages.getString("ERROR ! DOOR NOT FOUND"));
                                        }
                                    } catch (NumberFormatException e) {

                                    }
                        } else if (type.equals(PointA)) {
                             String pointAData[] = data.split(", ");
                                try {
                                        int posX = Integer.parseInt(pointAData[0]);
                                        StringTokenizer st = new StringTokenizer(pointAData[1], ")");
                                        int posY = Integer.parseInt(st.nextElement().toString());
                                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+posX+"-"+posY);
                                        if (rectangle.getFill() == Color.BROWN) {
                                                rectangle.setFill(Color.ALICEBLUE);
                                        }
                                        if (objetGrid.deletePointA(posX,posY)) {
                                            System.out.println("Starting point correctly deleted");
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println("Error ! Starting Point not found");
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }       
                        } else if (type.equals(PointB)) {
                         String pointBData[] = data.split(", ");
                                try {
                                        int posX = Integer.parseInt(pointBData[0]);
                                        StringTokenizer st = new StringTokenizer(pointBData[1], ")");
                                        int posY = Integer.parseInt(st.nextElement().toString());
                                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+posX+"-"+posY);
                                        if (rectangle.getFill() == Color.TEAL) {
                                                rectangle.setFill(Color.ALICEBLUE);
                                        }
                                        if (objetGrid.deletePointB(posX,posY)) {
                                            System.out.println(messages.getString("ENDING POINT CORRECTLY DELETED"));
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println(messages.getString("ERROR ! ENDING POINT NOT FOUND"));
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }   
                        } else {
                            
                        }
                    } catch (NullPointerException e) {
                
                    }
                }       
            });
        objectList.setOnAction((e) -> {
            boxButtons.getChildren().remove(deleteButton);
            boxButtons.getChildren().add(deleteButton);
            String selectedObject = objectList.getSelectionModel().getSelectedItem();
           if (selectedObject != null) {
                StringTokenizer splitObject = new StringTokenizer(selectedObject, "(");
                String type = splitObject.nextElement().toString();
                if (type.equals(messages.getString("DOOR"))) {
                    boxButtons.getChildren().remove(closeDoor);
                    boxButtons.getChildren().add(closeDoor);
                } else {
                    boxButtons.getChildren().remove(closeDoor);
                }
           }
            
        });
        grid.setConstraints(boxButtons,1,10);
        grid.setConstraints(objectList,1,9);
        
        MenuBar menuBar = new MenuBar();
        
        Menu planMenu = new Menu(messages.getString("PLAN"));
        
        //New button
        MenuItem newButton = new MenuItem(messages.getString("NEW"));
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                emptying(sceneTab);
            }
        });
        
        //Save button
        MenuItem saveButton = new MenuItem(messages.getString("SAVE"));
        saveButton.setMnemonicParsing(true);
        saveButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        TextInputDialog savingPopup = new TextInputDialog("");
        savingPopup.setTitle(messages.getString("HOUSE PLAN SAVING"));
        savingPopup.setHeaderText(messages.getString("IN ORDER TO SAVE YOUR HOUSE PLAN, YOU NEED TO ENTER A NAME FOR YOUR HOUSE PLAN"));
        savingPopup.setContentText(messages.getString("PLEASE ENTER THE SAVING NAME:"));
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
              Optional<String> result = savingPopup.showAndWait();
              if (result.isPresent()) {
                  savingProcess(result.get(),objetGrid.getListObjects(),sceneTab[1]);
              }
           }
        });
        
        //Load button
        MenuItem loadButton = new MenuItem(messages.getString("LOAD"));    
        loadButton.setMnemonicParsing(true);
        loadButton.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
                List<String> housePlans = new ArrayList<>();
                //Set the possible choices
                try {
                    setCredentials();
                    Connection connect = DriverManager.getConnection("jdbc:mysql://" + getAdresse() + "/iathinkers?"
                    + "user="+ getUsername() + "&password=" + getMdp());
                    Statement statement = connect.createStatement();
                    String request = "SELECT name FROM houseplan";                    
                    ResultSet rs = statement.executeQuery(request);
                    while(rs.next()) {
                        housePlans.add(rs.getString("name"));
                    }
                } catch (SQLException e) {

                }
                ChoiceDialog<String> loadingPopup = new ChoiceDialog<>("",housePlans);
                loadingPopup.setTitle(messages.getString("HOUSE PLAN LOADING"));
                loadingPopup.setHeaderText(messages.getString("WARNINGLOADING"));
                loadingPopup.setContentText(messages.getString("PLEASE SELECT THE HOUSE PLAN TO LOAD"));
                Optional<String> result = loadingPopup.showAndWait();
                if (result.isPresent()) {
                    objectList.getItems().clear();
                    loading(sceneTab, result.get());
                }
            }
        });
        
        
        //Back button
        MenuItem backButton = new MenuItem(messages.getString("BACK.."));
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setResizable(false);
                primaryStage.setScene(sceneTab[0]);
            }
        });
        
        planMenu.getItems().addAll(newButton,saveButton,loadButton,new SeparatorMenuItem(), backButton);
                
        Menu optionMenu = new Menu(messages.getString("OPTIONS"));
        Menu sizingMenu = new Menu(messages.getString("SIZING"));/*
        Double min = (Double) sceneTab[1].getHeight()*50/100;
        Double max = (Double) sceneTab[1].getHeight()*80/100;*/
        Slider sizing = new Slider(50,75,1);
        sizing.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    objetGrid.setGridSize(sceneTab[1],new_val.intValue());
            }
        });
        sizing.setShowTickMarks(true);
        sizing.setShowTickLabels(true);
        
        //Whenever the height of the scene is changed, we mke sure that our grid adapt its size to it !
        sceneTab[1].heightProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                Double new_val = new Double(sizing.getValue());
                objetGrid.setGridSize(sceneTab[1],new_val.intValue());
                objetGrid.setVisible(false);
                objetGrid.setVisible(true);
            }
        });
        
        CustomMenuItem gridSize = new CustomMenuItem(sizing); 
        
        sizingMenu.getItems().add(gridSize);
        optionMenu.getItems().add(sizingMenu);
        menuBar.getMenus().addAll(planMenu,optionMenu);
        
        
        //Error text zone
        Text infoSQL = new Text(10,50,"");
        infoSQL.setId("infosql");
        grid.setConstraints(infoSQL,1,11);
        
        
        
        //Launch Button
        Button launchButton = new Button(messages.getString("LAUNCH"));
        launchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (objetGrid.isPointAIsSet() && objetGrid.isPointBIsSet()) {
                    long startTime = System.currentTimeMillis();
                    Graph g = new Graph(objetGrid);
                    //We check if the graph of all the reachable nodes contains the ending point in order to check if a path between the ending and starting point exists
                    if (g.arrayContainsNode(g.getListOfNodes(), new Node(g.getEnd().getPosX(),g.getEnd().getPosY()))) {
                        AStar solution = new AStar(new Node(g.getStart().getPosX(),g.getStart().getPosY()),new Node(g.getEnd().getPosX(),g.getEnd().getPosY()), g);
                        for (Node n : path) {
                            path.add(n);
                            Rectangle r = (Rectangle) sceneTab[1].lookup("#"+(n.getX())+"-"+(n.getY()));
                            r.setFill(Color.BLUE);
                        }
                        path.clear();
                        for (Node n : solution.getSolution()) {
                            path.add(n);
                            Rectangle r = (Rectangle) sceneTab[1].lookup("#"+(n.getX())+"-"+(n.getY()));
                            r.setFill(Color.BLUE);
                        }    
                        long stopTime = System.currentTimeMillis();
                        long elapsedTime = stopTime - startTime;
                        Text infoSQL = (Text) sceneTab[1].lookup("#infosql");
                        infoSQL.setText(messages.getString("CALCULATION")+"= "+elapsedTime+" ms");
                        //After the calculation, we will save path statistics to the database
                        try {
                            setCredentials();
                            Connection connect = DriverManager.getConnection("jdbc:mysql://" + getAdresse() + "/iathinkers?"
                            + "user="+ getUsername() + "&password=" + getMdp());
                            Statement statement = connect.createStatement();
                            String request = "INSERT INTO STATISTICS(TIME,LENGTHOFPATH,NUMBEROFBLOCK,NUMBEROFAVAILABLECELL) VALUES("+elapsedTime+", "+path.size()+", "+objetGrid.numberOfBlocks()+", "+g.getListOfNodes().size()+")";                    
                            if (statement.executeUpdate(request) != -1) {
                                System.out.println("Statistics saved !");
                            }
                             
                        } catch (SQLException e) {
                            infoSQL = (Text) sceneTab[1].lookup("#infosql");
                            infoSQL.setText(infoSQL.getText()+"\n"+messages.getString("MYSQLERROR"));
                        }
                    } else {
                        Text infoSQL = (Text) sceneTab[1].lookup("#infosql");
                        infoSQL.setText(messages.getString("NOPATHERROR"));
                    }     
                } else {
                    Text infoSQL = (Text) sceneTab[1].lookup("#infosql");
                    infoSQL.setText(messages.getString("POINTERROR"));                
                }    
            }
        });
        
        
        //Hbox set to display the launch button
        HBox hbButtons2 = new HBox();
        hbButtons2.setSpacing(10);
        hbButtons2.getChildren().addAll(launchButton);
        grid.setConstraints(hbButtons2,1,14);
        
        //Add all the elements to the grid components
        grid.getChildren().addAll(choix,label,infoCase,objectListLbl,objectList,infoSQL,hbButtons2,boxButtons);
        menuVertical.getChildren().add(grid);
        BorderPane container = new BorderPane();
        container.setTop(menuBar);
        container.setMinSize(BASELINE_OFFSET_SAME_AS_HEIGHT, BASELINE_OFFSET_SAME_AS_HEIGHT);
        container.setCenter(objetGrid);
        menuVertical.setPadding(new Insets(10,10,10,10));
        container.setRight(menuVertical);
        //Add the main hbox to the scene
        sceneTab[1].setRoot(container);
    }

    /**
     * Method used to save an house plan in the MySQL database.
     * @param houseplanName String variable describing the name of 
     * @param listObjects ArrayList of the objects to save
     * @param scene Scene variable describing the Plan scene
     * @return true if correctly saved
     */
    private boolean savingProcess(String houseplanName, ArrayList<Object> listObjects, Scene scene) {
        int idHousePlan = 0;
        int idTypeDoor = 0;
        int idTypePointA = 0;
        int idTypePointB = 0;
        int idTypeWall = 0;
        try {
            setCredentials();
            Connection connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
            + "user="+this.getUsername()+"&password="+this.getMdp());
            //Dealing with house plan insertion
            Statement statement = connect.createStatement();
            String request = "SELECT * FROM HOUSEPLAN WHERE NAME=\'"+houseplanName+"\'";
            ResultSet rs = statement.executeQuery(request);
            if (!rs.next()) {
                if (statement.executeUpdate("INSERT INTO HOUSEPLAN(NAME) VALUES (\'"+houseplanName+"\')") != -1) {
                    System.out.println("HOUSEPLAN VALUE INSERTED");
                }
            } else {
                Text infoSQL = (Text) scene.lookup("#infosql");
                infoSQL.setText(messages.getString("THERE IS AN EXISTING\nPLAN WITH THE NAME \'")+houseplanName+messages.getString("\'. \nPLEASE SELECT ANOTHER NAME. "));
                return false;
            }
            rs = statement.executeQuery("SELECT * FROM HOUSEPLAN WHERE NAME=\'"+houseplanName+"\'");
            
            while (rs.next()) {
              idHousePlan = rs.getInt("idHousePlan");
            }
            //Dealing with type Ids
            rs = statement.executeQuery("SELECT * FROM TYPE");
            while (rs.next()) {
                switch (rs.getString("typeName")) {
                    case "Door":
                        idTypeDoor = rs.getInt("idType");
                        break;
                    case "PointA":
                        idTypePointA = rs.getInt("idType");
                        break;
                    case "PointB":
                        idTypePointB = rs.getInt("idType");
                        break;
                    case "Wall":
                        idTypeWall = rs.getInt("idType");
                        break;
                    default:
                        break;
              }
            }
            for (Object o : listObjects) {
                int objId = 0;
                if (o instanceof Objet.Wall) {
                    Objet.Wall w = (Objet.Wall) o;
                    request = "INSERT INTO Object(type,height,width,posX,posY) VALUES ("+idTypeWall+", "+w.getHeight()+", "+w.getWidth()+", "+w.getPosX()+", "+w.getPosY()+")";
                    if (statement.executeUpdate(request) != -1) {
                        rs = statement.executeQuery("SELECT idObject FROM Object ORDER BY idObject DESC LIMIT 0,1");
                        if (rs.next()) {
                            objId = rs.getInt("idObject");
                        }
                        if (statement.executeUpdate("INSERT INTO Composition(object, plan) VALUES ("+objId+", "+idHousePlan+")") != -1) {
                            System.out.println("OBJECT "+w.toString()+" SAVED");
                        }
                    }
                } else if (o instanceof Objet.Door) {
                    Objet.Door d = (Objet.Door) o;
                    if (d.getIsVertical()) {
                        request = "INSERT INTO Object(type,height,width,posX,posY,isVertical) VALUES ("+idTypeDoor+", "+d.getHeight()+", "+d.getWidth()+", "+d.getPosX()+", "+d.getPosY()+", 1)";
                    } else {
                         request = "INSERT INTO Object(type,height,width,posX,posY,isVertical) VALUES ("+idTypeDoor+", "+d.getHeight()+", "+d.getWidth()+", "+d.getPosX()+", "+d.getPosY()+", 0)";
                    }
                    if (statement.executeUpdate(request) != -1) {
                        rs = statement.executeQuery("SELECT idObject FROM Object ORDER BY idObject DESC LIMIT 0,1");
                        if (rs.next()) {
                            objId = rs.getInt("idObject");
                        }
                        if (statement.executeUpdate("INSERT INTO Composition(object, plan) VALUES ("+objId+", "+idHousePlan+")") != -1) {
                            System.out.println("OBJECT "+d.toString()+" SAVED");
                        }
                    }
                } else if (((Objet.Point) o).getType() == Objet.PointType.POINTA) {
                    Objet.Point p = (Objet.Point) o;
                request = "INSERT INTO Object(type,posX,posY) VALUES ("+idTypePointA+", "+p.getPosX()+", "+p.getPosY()+")";
                if (statement.executeUpdate(request) != -1) {
                        rs = statement.executeQuery("SELECT idObject FROM Object ORDER BY idObject DESC LIMIT 0,1");
                        if (rs.next()) {
                            objId = rs.getInt("idObject");
                        }
                        if (statement.executeUpdate("INSERT INTO Composition(object, plan) VALUES ("+objId+", "+idHousePlan+")") != -1) {
                            System.out.println("OBJECT "+p.toString()+" SAVED");
                        }
                    }                
                } else {
                    Objet.Point p = (Objet.Point) o;
                        request = "INSERT INTO Object(type,posX,posY) VALUES ("+idTypePointB+", "+p.getPosX()+", "+p.getPosY()+")";
                        if (statement.executeUpdate(request) != -1) {
                            rs = statement.executeQuery("SELECT idObject FROM Object ORDER BY idObject DESC LIMIT 0,1");
                        if (rs.next()) {
                            objId = rs.getInt("idObject");
                        }
                        if (statement.executeUpdate("INSERT INTO Composition(object, plan) VALUES ("+objId+", "+idHousePlan+")") != -1) {
                            System.out.println("OBJECT "+p.toString()+" SAVED");
                        }
                    }
                }
            }
            System.out.println("\n Every object saved ! \n");
            Text infoSQL = (Text) scene.lookup("#infosql");
            infoSQL.setText("House Plan correctly\nsaved as "+houseplanName);
            return true;
        } catch (SQLException ex) {
            Text infoSQL = (Text) scene.lookup("#infosql");
            infoSQL.setText("MYSQLERROR");
            return false;
        }
    }
    
    /**
     * Method used to empty the current plan
     * @param sceneTab 
     */
    public void emptying(Scene[] sceneTab) {
        for (Object o : objetGrid.getListObjects()) {
            if (o instanceof Objet.Wall) {
                Objet.Wall w = (Objet.Wall) o;
                for (int i = 0; i < w.getWidth(); i++) {
                    for (int j = 0; j < w.getHeight(); j++) {
                        objetGrid.getListCells().get("#"+(w.getPosX() + i)+"-"+(w.getPosY() + j)).setOccupied(false);
                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(w.getPosX() + i)+"-"+(w.getPosY()+ j));
                        if (rectangle.getFill() == Color.THISTLE) {
                            rectangle.setFill(Color.ALICEBLUE);
                        }
                    }
                }
               // o = null;
            } else if (o instanceof Objet.Door) {
                Objet.Door d = (Objet.Door) o;
                objetGrid.getListCells().get("#"+(d.getPosX())+"-"+(d.getPosY())).setOccupied(false);
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(d.getPosX())+"-"+(d.getPosY()));
                if (rectangle.getFill() == Color.ANTIQUEWHITE) {
                    rectangle.setFill(Color.ALICEBLUE);
                }
                //o = null;
            } else if (((Objet.Point) o).getType() == PointType.POINTA) {
                Objet.Point p = (Objet.Point) o;
                objetGrid.getListCells().get("#"+(p.getPosX())+"-"+(p.getPosY())).setOccupied(false);
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+p.getPosX()+"-"+p.getPosY());
                if (rectangle.getFill() == Color.BROWN) {
                    rectangle.setFill(Color.ALICEBLUE);
                }
                objetGrid.setPointAIsSet(false);
            } else {
                Objet.Point p = (Objet.Point) o;
                objetGrid.getListCells().get("#"+(p.getPosX())+"-"+(p.getPosY())).setOccupied(false);
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+p.getPosX()+"-"+p.getPosY());
                if (rectangle.getFill() == Color.TEAL) {
                    rectangle.setFill(Color.ALICEBLUE);
                }
                objetGrid.setPointBIsSet(false);
            }
        }
        //On vide le chemin affiché
        for (Node n : this.getPath()) {
            Rectangle r = (Rectangle) sceneTab[1].lookup("#"+(n.getX())+"-"+(n.getY()));
            r.setFill(Color.ALICEBLUE);
        }
        objetGrid.getListObjects().clear();
    }
    
    /**
     * Method used to load a saved house plan.
     * @param sceneTab Array of scenes used to get the Plan Scene
     * @param houseplanName name of the plan to load
     * @return true if correctly loaded
     */
    private boolean loading(Scene[] sceneTab, String houseplanName) {
        //objetGrid.printListObjects();
        emptying(sceneTab);
        try {
            setCredentials();
            Connection connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"
                    + "user="+this.getUsername()+"&password="+this.getMdp());
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT idHousePlan FROM houseplan WHERE name=\'"+houseplanName+"\'");
            int idHousePlan = 0;
            if (rs.next()) {
                idHousePlan = rs.getInt("idHousePlan");
            }
            rs = statement.executeQuery("SELECT object FROM composition WHERE plan="+idHousePlan);
            Statement statementBis = connect.createStatement();
            ResultSet rsbis;
            
            
            objetGrid.getListObjects().clear();
            while(rs.next()) {
                rsbis = statementBis.executeQuery("SELECT * FROM object WHERE idObject="+rs.getInt("object"));
                if (rsbis.next()) {
                    switch (rsbis.getInt("type")) {
                        case 1://WALL
                            objetGrid.addWall(rsbis.getInt("height"), rsbis.getInt("width"), rsbis.getInt("posX"), rsbis.getInt("posY"), sceneTab[1]);
                            break;
                        case 2://DOOR
                            if (rsbis.getInt("isVertical") == 1) {
                                objetGrid.addDoor(rsbis.getInt("posX"), rsbis.getInt("posY"), true, sceneTab[1]);
                            } else {
                                objetGrid.addDoor(rsbis.getInt("posX"), rsbis.getInt("posY"), false, sceneTab[1]);
                            }
                            break;
                        case 3://POINTA
                            objetGrid.addPointA(rsbis.getInt("posX"), rsbis.getInt("posY"), sceneTab[1]);
                            break;
                        case 4://POINTB
                            objetGrid.addPointB(rsbis.getInt("posX"), rsbis.getInt("posY"), sceneTab[1]);
                            break;
                        default:
                            break;
                    }
                }    
            }
            Text infoSQL = (Text) sceneTab[1].lookup("#infosql");
            infoSQL.setText(messages.getString("HOUSE PLAN CORRECTLY LOADED !"));
        } catch (SQLException ex) {
            Text infoSQL = (Text) sceneTab[1].lookup("#infosql");
            infoSQL.setText("MYSQLERROR");
            return false;
        }
        return true;
    }
    
    public ArrayList<Node> getPath() {
        return this.path;
    }
    
    /**
     * Method used to set the MySQL credentials
     */
    private void setCredentials() {
        String[] credentials = Parametres.SQLParameters.getSQLInfos();
        this.setUsername(credentials[0]);
        this.setMdp(credentials[1]);
        this.setAdresse(credentials[2]);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

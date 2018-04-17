/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import Menu.MainMenu;
import Objet.PointType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/**
 * Classe de la <i> scène </i> du plan de maison. Elle se compose d'une grille de cellule.
 * @author IAThinkers
 */
public class Plan extends Parent{
    /**
     * Constructeur de la classe Plan. Passe en paramètres une variable Stage et un tableau de Scene[] pour pouvoir retourner au menu principal à partir de cet <i> scène </i>
     * @param primaryStage variable Stage permettant de retourner sur la <i> scène </i> MainMenu {@link MainMenu}
     * @param sceneTab tableau de Scene permettant de retourner sur la <i> scène </i> MainMenu {@link MainMenu}
     * @param connect connection variable linked to the SQL database
     */
    
    private String username;
    private String adresse;
    private String mdp;
    private Grille objetGrid;
    
    public Plan(Stage primaryStage, Scene[] sceneTab) {
       
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
        choix.getItems().add("Wall");
        choix.getItems().add("Door");
        choix.getItems().add("Starting Point");
        choix.getItems().add("Ending Point");

        //Define the textfields for the wall properties
        TextField height = new TextField();
        height.setId("height");
        height.setPrefWidth(30);
        Label heightLbl = new Label("Height");
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
        Label widthLbl = new Label("Width");
        // force the textfield only to contain numeric characters
        width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    width.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        //Define the orientation of the door to be put
        ComboBox<String> orientation = new ComboBox();
        orientation.getItems().add("Horizontal");
        orientation.getItems().add("Vertical");
        orientation.setId("orientation");
        Label orientationLbl = new Label("Orientation");
        
        
        
        
        //Define the executed action on the choice of an object
        choix.setOnAction((e) -> {
             switch (choix.getSelectionModel().getSelectedItem()) {
                 case "Wall":
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
                    break;
                 case "Door":
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
                    break;
                 case "Starting Point":
                 case "Ending Point":
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
                break;    
                 default:
                    break;
             }
        });
        
        
        
        choix.setId("choixObjet");
        grid.setVgap(10);
        grid.setConstraints(choix,1,2);
        
        //Label liste
        Label label = new Label("Object choice");
        grid.setConstraints(label, 1, 1); // column=1 row=1
        
        //Info Case
        Text infoCase = new Text(10,50,"<_;_>");
        infoCase.setId("infoCase");
        grid.setConstraints(infoCase,1,7);
        
        //Grid Object initialisation
        this.objetGrid = new Grille(sceneTab[1]);

        Label objectListLbl = new Label("Listing of all created objects");
        grid.setConstraints(objectListLbl,1,8);
        
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
                                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(posX)+"-"+(posY));
                                        if (rectangle.getFill() == Color.ANTIQUEWHITE) {
                                            rectangle.setFill(Color.ALICEBLUE);
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
                            case "PointA":
                                String pointAData[] = data.split(", ");
                                System.out.println(data);
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
                                break;
                            case "PointB":
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
                                            System.out.println("Ending point correctly deleted");
                                            objectList.getItems().removeAll(selectedObject);
                                        } else {
                                            System.out.println("Error ! Ending Point not found");
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
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
        grid.setConstraints(deleteButton,1,10);
        grid.setConstraints(objectList,1,9);
        
        
        
        //Save button
        Button saveButton = new Button("Save");
        TextInputDialog savingPopup = new TextInputDialog("");
        savingPopup.setTitle("House Plan Saving");
        savingPopup.setHeaderText("In order to save your house plan, you need to enter a name for your House Plan");
        savingPopup.setContentText("Please enter the saving name:");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
              Optional<String> result = savingPopup.showAndWait();
              if (result.isPresent()) {
                  savingProcess(result,objetGrid.getListObjects(),sceneTab[1]);
              }
           }
        });
        
        //Load button
        Button loadButton = new Button("Load");
        
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
                List<String> housePlans = new ArrayList<>();
                //Set the possible choices
                try {
                    setCredentials();
                    Connection connect = DriverManager.getConnection("jdbc:mysql://" + getAdresse() + "/iathinkers?"
                            + "user=" + getUsername() + "&password=" + getMdp());
                    Statement statement = connect.createStatement();
                    String request = "SELECT name FROM houseplan";
                    ResultSet rs = statement.executeQuery(request);
                    while(rs.next()) {
                        housePlans.add(rs.getString("name"));
                    }
                } catch (SQLException e) {

                }
                ChoiceDialog<String> loadingPopup = new ChoiceDialog<>("",housePlans);
                loadingPopup.setTitle("House Plan Loading");
                loadingPopup.setHeaderText("In order to load an House Plan, you need to select one from the list below (/!\\ Warning, your current one will be deleted /!\\) :");
                loadingPopup.setContentText("Please select the House Plan to Load");
                Optional<String> result = loadingPopup.showAndWait();
                if (result.isPresent()) {
                    objectList.getItems().clear();
                    loading(sceneTab, result.get());
                }
            }
        });
        
        //Back button
        Button backButton = new Button("Back..");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneTab[0]);
            }
        });
        
        //Error text zone
        Text infoSQL = new Text(10,50,"");
        infoSQL.setId("infoSQL");
        grid.setConstraints(infoSQL,1,11);
        
        //Hbox set to display the 3 bottom buttons
        
        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10);
        hbButtons.getChildren().addAll(backButton,saveButton,loadButton);
        grid.setConstraints(hbButtons,1,12);
        
        grid.getChildren().addAll(choix,label,infoCase,objectListLbl,objectList,hbButtons,infoSQL);
        menuVertical.getChildren().add(grid);
        
        hbox.getChildren().add(objetGrid);
        hbox.getChildren().add(menuVertical);
        this.getChildren().add(hbox);
    }

    private boolean savingProcess(Optional<String> result, ArrayList<Object> listObjects, Scene scene) {
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
            String request = "SELECT * FROM houseplan WHERE name=\'"+result.get()+"\'";
            System.out.println(request);
            ResultSet rs = statement.executeQuery(request);
            if (!rs.next()) {
                if (statement.executeUpdate("INSERT INTO houseplan(name) VALUES (\'"+result.get()+"\')") != -1) {
                    System.out.println("HousePlan value inserted");
                }
            } else {
                Text infoSQL = (Text) scene.lookup("#infoSQL");
                infoSQL.setText("There is an existing\nplan with the name \'"+result.get()+"\'. \nPlease select another name. ");
                return false;
            }
            rs = statement.executeQuery("SELECT * FROM houseplan WHERE name=\'"+result.get()+"\'");
            
            while (rs.next()) {
              idHousePlan = rs.getInt("idHousePlan");
            }
            //Dealing with type Ids
            rs = statement.executeQuery("SELECT * FROM Type");
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
                    System.out.println(request);
                    if (statement.executeUpdate(request) != -1) {
                        rs = statement.executeQuery("SELECT idObject FROM Object ORDER BY idObject DESC LIMIT 0,1");
                        if (rs.next()) {
                            objId = rs.getInt("idObject");
                        }
                        if (statement.executeUpdate("INSERT INTO Composition(object, plan) VALUES ("+objId+", "+idHousePlan+")") != -1) {
                            System.out.println("Object "+w.toString()+" saved");
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
                            System.out.println("Object "+d.toString()+" saved");
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
                            System.out.println("Object "+p.toString()+" saved");
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
                            System.out.println("Object "+p.toString()+" saved");
                        }
                    }
                }
            }
            System.out.println("\n Every object saved ! \n");
            Text infoSQL = (Text) scene.lookup("#infoSQL");
            infoSQL.setText("House Plan correctly\n saved as "+result.get());
            return true;
        } catch (SQLException ex) {
            Text infoSQL = (Text) scene.lookup("#infoSQL");
            infoSQL.setText("MySQL ERROR : \nPlease make sure\nyou have entered\nyour credentials.");
            return false;
        }
    }
    
    private boolean loading(Scene[] sceneTab, String houseplanName) {
        //objetGrid.printListObjects();
        for (Object o : objetGrid.getListObjects()) {
            if (o instanceof Objet.Wall) {
                Objet.Wall w = (Objet.Wall) o;
                for (int i = 0; i < w.getWidth(); i++) {
                    for (int j = 0; j < w.getHeight(); j++) {
                        Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(w.getPosX() + i)+"-"+(w.getPosY()+ j));
                        if (rectangle.getFill() == Color.THISTLE) {
                            rectangle.setFill(Color.ALICEBLUE);
                        }
                    }
                }
               // o = null;
            } else if (o instanceof Objet.Door) {
                Objet.Door d = (Objet.Door) o;
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+(d.getPosX())+"-"+(d.getPosY()));
                if (rectangle.getFill() == Color.ANTIQUEWHITE) {
                    rectangle.setFill(Color.ALICEBLUE);
                }
                //o = null;
            } else if (((Objet.Point) o).getType() == PointType.POINTA) {
                Objet.Point p = (Objet.Point) o;
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+p.getPosX()+"-"+p.getPosY());
                if (rectangle.getFill() == Color.BROWN) {
                    rectangle.setFill(Color.ALICEBLUE);
                }
            } else {
                Objet.Point p = (Objet.Point) o;
                Rectangle rectangle = (Rectangle) sceneTab[1].lookup("#"+p.getPosX()+"-"+p.getPosY());
                if (rectangle.getFill() == Color.TEAL) {
                    rectangle.setFill(Color.ALICEBLUE);
                }            
            }
        }
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
            System.out.println(objetGrid.getListCells().toString());
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
            Text infoSQL = (Text) sceneTab[1].lookup("#infoSQL");
            infoSQL.setText("House Plan correctly loaded !");
        } catch (SQLException ex) {
            Text infoSQL = (Text) sceneTab[1].lookup("#infoSQL");
            infoSQL.setText("MySQL ERROR : \nPlease make sure\nyou have entered\nyour credentials.");
            return false;
        }
        return true;
    }
    
    private void setCredentials() {
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
            
            this.setUsername(new String(usrChar));
            this.setMdp(new String(pwdChar));
            this.setAdresse(new String(adrChar));
                        
        } catch (FileNotFoundException ex) {
            System.out.println("No database set");
            this.setUsername("");
            this.setMdp("");
            this.setAdresse("");
        }
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

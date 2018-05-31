/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand;

import Freehand.Algorithm.AStarFreehand;
import Freehand.ShapesHandler.MoveObject;
import Freehand.ShapesHandler.ResizeObject;
import static Menu.MainMenu.getLanguage;
import ObjectCreation.Shapes.Hexagon;
import ObjectCreation.Shapes.Octagon;
import ObjectCreation.Shapes.Pentagon;
import ObjectCreation.Shapes.Triangle;
import Plan.Algorithm.Node;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * The Freehand class : it shows the interface used to draw the path in which the {@link AStarFreehand} algorithm is applied.
 * @author IAThinkers
 */
public class Freehand extends Parent {
    /**
     * Connection variables
     */
    private String username;
    private String adresse;
    private String mdp;
    
    
    /**
     * The variable of the first path
     */
    private Path firstPath;
    
    /**
     * The variable of the second path
     */
    private Path secondPath;
    
    /**
     * A boolean indicating if the first path has been drawn
     */
    private Boolean firstIsDone;
    
    /**
     * A boolean indicating if the second path has been drawn
     */
    private Boolean secondIsDone;
    
    /**
     * A boolean indicating if the starting pixel has been set
     */
    private Boolean startingIsDone;
    
    /**
     * A boolean indicating if the ending pixel has been set
     */
    private Boolean endingIsDone;
    
    /**
     * {@link Freehand.StartingPixel} StartingPixel variable
     */
    private StartingPixel start;
    
    /**
     * {@link Freehand.EndingPixel} EndingPixel variable
     */
    private EndingPixel end;
    
    /**
     * The container of the two paths
     */
    private Pane drawingBox;
    
    /**
     * The state of the drawing surface saved in a buffered image
     */
    private WritableImage currentState;
    
    /**
     * Current scene
     */
    private Scene scene;
    
    /**
     * The list of the nodes of the solution
     */
    private ArrayList<Node> solutionPath;
    
    /**
     * The list of Shapes to put 
     */
    private ArrayList<Object> shapesToPut;
    
    /**
     * Map of all the shapes using the object's name as a key
     */
    private HashMap<String,ArrayList<Object>> listOfObjects;
    
    /**
     * ResourceBundle variable set as an instance one to be accessible from every method of this class
     */
    private ResourceBundle messages;
    
    /**
     * The constructor of the Freehand class : it is composed of a drawing surface, a menubar and a vbox with infos. 
     * All of these Nodes are stored in a BorderPane, used as the root of the scene.
     * @param primaryStage
     * @param sceneTab 
     */
    public Freehand(Stage primaryStage, Scene[] sceneTab) {
        firstIsDone = false;
        secondIsDone = false;
        startingIsDone = false;
        endingIsDone = false;
        scene = sceneTab[2];
        solutionPath = new ArrayList<Node>();
        shapesToPut = new ArrayList<Object>();
        listOfObjects = new HashMap<String,ArrayList<Object>>();
        Path drawnPath = new Path();
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Freehand/Freehand",l);
        
        //We initialize the menubar with its item
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menubar");
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        //Firstly, the file menu
        Menu menuFile = new Menu(messages.getString("FILEMENU"));
        MenuItem menuExit = new MenuItem(messages.getString("EXIT"));
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setResizable(false);
                primaryStage.setScene(sceneTab[0]);
            }
        });
        
        menuFile.getItems().addAll(menuExit);
        
        //The object loading menu : put here for proper disabling in others handler
        MenuItem loadObject = new MenuItem(messages.getString("LOADOBJECT"));
        loadObject.setDisable(true);
        loadObject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> objects = new ArrayList<>();
                //Set the possible choices
                try {
                    setCredentials();
                    Connection connect = DriverManager.getConnection("jdbc:mysql://" + getAdresse() + "/iathinkers?"
                    + "user="+ getUsername() + "&password=" + getMdp());
                    Statement statement = connect.createStatement();
                    String request = "SELECT objetName FROM freehandobject";                    
                    ResultSet rs = statement.executeQuery(request);
                    while(rs.next()) {
                        objects.add(rs.getString("objetName"));
                    }
                } catch (SQLException e) {

                }
                ChoiceDialog<String> loadingPopup = new ChoiceDialog<>("",objects);
                loadingPopup.setTitle(messages.getString("OBJECTLOADING"));
                loadingPopup.setHeaderText(messages.getString("SELECTLOADING"));
                Optional<String> result = loadingPopup.showAndWait();
                if (result.isPresent()) {
                    loadingObject(result.get());
                }
            }
        });
        
        
        //The tools menu
        Menu menuTools = new Menu(messages.getString("TOOLS"));
        MenuItem menuEmpty = new MenuItem(messages.getString("EMPTY"));
        menuEmpty.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event) {
               if (secondIsDone == false) {
                firstPath.getElements().clear();
                firstIsDone = false;
               } else {
                    secondPath.getElements().clear();
                    secondIsDone = false;
                    drawingBox.setOnMouseClicked(drawPath);
                    drawingBox.setOnMouseDragged(drawPath);
                    drawingBox.setOnMouseEntered(drawPath);
                    drawingBox.setOnMouseExited(drawPath);
                    drawingBox.setOnMouseMoved(drawPath);
                    drawingBox.setOnMousePressed(drawPath);
                    drawingBox.setOnMouseReleased(drawPath);
               }
           }
        });
        
        //We setup the menuitem for the initialisation of a new path
        MenuItem menuNewPath = new MenuItem(messages.getString("NEWPATH"));
        menuNewPath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                firstPath.getElements().clear();
                secondPath.getElements().clear();
                drawnPath.getElements().clear();
                solutionPath.clear();
                start = null;
                end = null;
                startingIsDone = false;
                endingIsDone = false;
                firstIsDone = false;
                secondIsDone = false;
                loadObject.setDisable(true);
                drawingBox.getChildren().clear();
                drawingBox.getChildren().addAll(firstPath,secondPath);
                drawingBox.setOnMouseClicked(drawPath);
                drawingBox.setOnMouseDragged(drawPath);
                drawingBox.setOnMouseEntered(drawPath);
                drawingBox.setOnMouseExited(drawPath);
                drawingBox.setOnMouseMoved(drawPath);
                drawingBox.setOnMousePressed(drawPath);
                drawingBox.setOnMouseReleased(drawPath);
            }
        });
        
        MenuItem menuPixels = new MenuItem(messages.getString("PIXELS"));
        menuPixels.setId("menupixel");
        menuPixels.setDisable(true);
        menuPixels.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (firstIsDone && secondIsDone) {
                    drawingBox.setOnMouseClicked(setPoints);
                }
            }
        });
        MenuItem menuLaunch = new MenuItem(messages.getString("LAUNCH"));
        Label progressInfo = new Label("Status");
        menuLaunch.setDisable(true);
        menuLaunch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //currentState = new WritableImage((int)drawingBox.getHeight(), (int)drawingBox.getWidth());
                setCurrentState(drawingBox.snapshot(null, getCurrentState()));
                if (firstIsDone && secondIsDone) {
                    menuEmpty.setDisable(true);
                    menuNewPath.setDisable(true);
                    final Node startingNode = new Node(start.getX()/10,start.getY()/10);
                    final Node endingNode = new Node(end.getX()/10,end.getY()/10);
                    final WritableImage currentStateCopy = getCurrentState();
                    final ArrayList<ArrayList<Boolean>> boolArray = fromWritableToArray(currentStateCopy, endingNode);
                    //As the calculus of the path can take some time, we decided to set it in a new threads in order not to freeze the application for that time
                    Task task = new Task<Void>() {
                        @Override 
                        public Void call() {
                            updateMessage(messages.getString("CALCULATION"));
                            AStarFreehand astar = new AStarFreehand(new AStarFreehand(),startingNode, endingNode, boolArray, currentStateCopy);
                            solutionPath.addAll(astar.solved(astar.getEfficientPredecessor(),endingNode,new ArrayList<Node>()));
                            //uncomment this part if you want to see how the A* algorithm has visited the cells of your interface : saved as a png file at the root of the project called "path.png"
                            File outputFile = new File("path.png");
                            BufferedImage bImage = SwingFXUtils.fromFXImage(currentStateCopy, null);
                            try {
                              ImageIO.write(bImage, "png", outputFile);
                            } catch (IOException e) {
                              throw new RuntimeException(e);
                            }
                            menuEmpty.setDisable(false);
                            menuNewPath.setDisable(false);
                            updateMessage(messages.getString("CALCULATIONOVER"));
                            Platform.runLater(new Runnable() {

                            public void run() {
                                drawnPath.getElements().add(new MoveTo(solutionPath.get(0).getX()*10,solutionPath.get(0).getY()*10));
                                int i = 0;
                                //for (int i = 1; i < solutionPath.size()-2; i++) {
                                    while (i < solutionPath.size()) {
                                            //double beforeX = solutionPath.get(i-1).getX()*10;
                                            //double beforeY = solutionPath.get(i-1).getY()*10;
                                            double curveX = solutionPath.get(i).getX()*10;
                                            double curveY = solutionPath.get(i).getY()*10;
                                            //double thenX = solutionPath.get(i+1).getX()*10;
                                            //double thenY = solutionPath.get(i+1).getY()*10;
                                            drawnPath.getElements().add(new LineTo(curveX,curveY));
                                            drawnPath.getElements().add(new MoveTo(curveX,curveY));   
                                            i = i+1;
                                        }
                                Rectangle rectPath = new Rectangle(solutionPath.get(0).getX()*10,solutionPath.get(0).getY()*10,10, 10);
                                rectPath.setArcHeight(5);
                                rectPath.setArcWidth(5);
                                rectPath.setFill(Color.DARKBLUE);
                                PathTransition pathTransition = new PathTransition();
                                pathTransition.setDuration(Duration.millis(4000));
                                pathTransition.setPath(drawnPath);
                                pathTransition.setNode(rectPath);
                                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                                pathTransition.setCycleCount(Timeline.INDEFINITE);
                                pathTransition.play();
                                drawnPath.setStroke(Color.ALICEBLUE);
                                drawingBox.getChildren().addAll(drawnPath,rectPath);
                            }
                        });
                            return null;
                        }
                    };
                    
                            
                    progressInfo.textProperty().bind(task.messageProperty());
                    Thread th = new Thread(task);
                    th.setDaemon(true);
                    th.start();
                }
            }
        });
        menuTools.getItems().addAll(menuEmpty,menuNewPath,new SeparatorMenuItem(), menuPixels,menuLaunch);
        
        Menu menuObject = new Menu(messages.getString("MENUOBJECT"));
        menuObject.getItems().addAll(loadObject);
        
        menuBar.getMenus().addAll(menuFile,menuTools,menuObject);
        
        BorderPane mainContainer = new BorderPane();
        drawingBox = new Pane();
        drawingBox.setPrefSize(500,500);
        drawingBox.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainContainer.setCenter(drawingBox);
        mainContainer.setTop(menuBar);
        
        VBox info = new VBox();
        info.setSpacing(10);
        Text startingInfo = new Text();
        startingInfo.setId("startinginfo");
        startingInfo.setText(messages.getString("STARTINGINFO"));
        Text endingInfo = new Text();
        endingInfo.setId("endinginfo");
        endingInfo.setText(messages.getString("ENDINGINFO"));
        info.getChildren().addAll(startingInfo,endingInfo,progressInfo);
        
        mainContainer.setRight(info);
        
        //We set up the width of the two paths
        firstPath = new Path();
        firstPath.setStrokeWidth(1);
        firstPath.setStroke(Color.BLUE);

        secondPath = new Path();
        secondPath.setStrokeWidth(1);
        secondPath.setStroke(Color.RED);
        
        
        drawingBox.setOnMouseClicked(drawPath);
        drawingBox.setOnMouseDragged(drawPath);
        drawingBox.setOnMouseEntered(drawPath);
        drawingBox.setOnMouseExited(drawPath);
        drawingBox.setOnMouseMoved(drawPath);
        drawingBox.setOnMousePressed(drawPath);
        drawingBox.setOnMouseReleased(drawPath);

        drawingBox.getChildren().add(firstPath);
        drawingBox.getChildren().add(secondPath);
        sceneTab[2].setRoot(mainContainer);

    }
    
    /**
     * This method is used to draw a cross on the Pane, centered at (x,y) coordinates
     * @param drawing
     * @param x
     * @param y 
     */
    public void drawCross(Pane drawing, int x, int y) {
        /*Rectangle rect = new Rectangle(x,y,10,10);
        rect.setStroke(Color.BLACK);*/
        Line line1 = new Line(x-5, y-5, x+5, y+5);
        line1.setFill(Color.BLACK);
        Line line2 = new Line(x-5, y+5, x+5, y-5);
        line2.setFill(Color.BLACK);
        drawing.getChildren().addAll(line1,line2);
    }
    
    /**
     * This handler is responsible for the drawing of the path
     */
    EventHandler<MouseEvent> drawPath = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Path path = new Path();
            if (getFirstIsDone() == false) {
                path = getFirstPath();
            } else if (getSecondIsDone() == false) {
                path = getSecondPath();
                
            } else {
                MenuBar m = (MenuBar) scene.lookup("#menubar");
                //We get the "set launching points" menu and enable it
                MenuItem i = m.getMenus().get(1).getItems().get(3);
                i.setDisable(false);
                //We get the object disposition and enable it
                MenuItem ibis = m.getMenus().get(2).getItems().get(0);
                ibis.setDisable(false);
                drawingBox.setOnMouseClicked(null);
                drawingBox.setOnMouseDragged(null);
                drawingBox.setOnMouseEntered(null);
                drawingBox.setOnMouseExited(null);
                drawingBox.setOnMouseMoved(null);
                drawingBox.setOnMousePressed(null);
                drawingBox.setOnMouseReleased(null);
            }
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                path.getElements()
                .add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && 
                (mouseEvent.getX()<=(drawingBox.getWidth()+drawingBox.getTranslateX()) && mouseEvent.getY()<=(drawingBox.getHeight()+drawingBox.getTranslateY())) && 
                (mouseEvent.getX()>=(drawingBox.getTranslateX()) && mouseEvent.getY()>=(drawingBox.getTranslateY()))) {
                    path.getElements()
                    .add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                if (getFirstIsDone() == false) {
                    setFirstIsDone(true);
                } else if (getSecondIsDone() == false) {
                    setSecondIsDone(true);
                }
            }
        }
    };

    /**
     * The handler responsible for setting both starting and ending points
     */
    EventHandler<MouseEvent> setPoints = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (getStartingIsDone() == false) {
                start = new StartingPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());
                setStartingIsDone(true);
                Text startingInfo = (Text) scene.lookup("#startinginfo");
                startingInfo.setText(messages.getString("STARTINGINFO")+"\n"+start.toString());
                drawCross(drawingBox,(int)mouseEvent.getX(),(int)mouseEvent.getY());
            } else if (getEndingIsDone() == false) {
                end = new EndingPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());
                setEndingIsDone(true);
                Text endingInfo = (Text) scene.lookup("#endinginfo");
                endingInfo.setText(messages.getString("ENDINGINFO")+"\n"+end.toString());
                drawCross(drawingBox,(int)mouseEvent.getX(),(int)mouseEvent.getY());
                
                MenuBar m = (MenuBar) scene.lookup("#menubar");
                //We get the launching menu and enable it
                MenuItem i = m.getMenus().get(1).getItems().get(4);
                i.setDisable(false);
                System.out.println("enabled");
            } else {
                drawingBox.setOnMouseClicked(null);
            }
        }
    };
    
    public Path getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(Path firstPath) {
        this.firstPath = firstPath;
    }

    public Path getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(Path secondPath) {
        this.secondPath = secondPath;
    }

    public Boolean getFirstIsDone() {
        return firstIsDone;
    }

    public void setFirstIsDone(Boolean firstIsDone) {
        this.firstIsDone = firstIsDone;
    }

    public Boolean getSecondIsDone() {
        return secondIsDone;
    }

    public void setSecondIsDone(Boolean secondIsDone) {
        this.secondIsDone = secondIsDone;
    }

    public WritableImage getCurrentState() {
        return currentState;
    }

    public void setCurrentState(WritableImage currentState) {
        this.currentState = currentState;
    }

    public Boolean getStartingIsDone() {
        return startingIsDone;
    }

    public void setStartingIsDone(Boolean startingIsDone) {
        this.startingIsDone = startingIsDone;
    }

    public Boolean getEndingIsDone() {
        return endingIsDone;
    }

    public void setEndingIsDone(Boolean endingIsDone) {
        this.endingIsDone = endingIsDone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
 
    /**
     * Method used to load an object saved in the database. It puts all of them on the drawingBox variable and it initiates the {@link Freehand.ShapesHandler} handlers.
     * @param objectName
     * @return 
     */
    public boolean loadingObject(String objectName) {
        try {
            setCredentials();
            ArrayList<Object> shapes = new ArrayList<Object>();
            //drawingBox.setOnMouseDragged(putObject);
            //drawingBox.setOnMousePressed(putObject);
            //drawingBox.setOnScroll(resizeObject);
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + this.getAdresse() + "/iathinkers?" + "user=" + this.getUsername() + "&password=" + this.getMdp());
            //We load the id of the object
            Statement statement = connect.createStatement();
            String request = "SELECT * FROM FREEHANDOBJECT WHERE OBJETNAME=\'" + objectName + "\'";
            ResultSet rs = statement.executeQuery(request);
            int idObject;
            int idTriangle = 0;
            int idRectangle = 1;
            int idSquare = 2;
            int idPentagon = 3;
            int idHexagon = 4;
            int idOctagon = 5;
            int idCircle = 6;
            if (rs.next()) {
                idObject = rs.getInt("idObject");
                System.out.println("Object selected");
            } else {
                return false;
            }
            //We load the ids of all the shapes to recognize the type of the shape's instances
            rs = statement.executeQuery("SELECT * FROM SHAPES");
            while (rs.next()) {
                switch (rs.getString("shapeName")) {
                    case "Triangle":
                        idTriangle = rs.getInt("idShapes");
                        break;
                    case "Rectangle":
                        idRectangle = rs.getInt("idShapes");
                        break;
                    case "Square":
                        idSquare = rs.getInt("idShapes");
                        break;
                    case "Pentagon":
                        idPentagon = rs.getInt("idShapes");
                        break;
                    case "Hexagon":
                        idHexagon = rs.getInt("idShapes");
                        break;
                    case "Octagon":
                        idOctagon = rs.getInt("idShapes");
                        break;
                    case "Circle":
                        idCircle = rs.getInt("idShapes");
                        break;
                    default:
                        break;
              }
            }
            rs = statement.executeQuery("SELECT * FROM SHAPESINSTANCES WHERE OBJECT = "+idObject);
            while (rs.next()) {
                if (rs.getInt("shapeType") == idTriangle) {
                    Triangle obj = new Triangle(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    System.out.println(rs.getString("fillColor"));
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idRectangle) {
                    Rectangle obj = new Rectangle(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idSquare) {
                    Rectangle obj = new Rectangle(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                     drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idPentagon) {
                    Pentagon obj = new Pentagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idHexagon) {
                    Hexagon obj = new Hexagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idOctagon) {
                    Octagon obj = new Octagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                } else if (rs.getInt("shapeType") == idCircle) {
                    Circle obj = new Circle();
                    obj.setCenterX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setCenterY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setRadius(rs.getDouble("sizeX")*drawingBox.getWidth());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    drawingBox.getChildren().add(obj);
                    shapes.add(obj);
                }
            }
            listOfObjects.put(objectName, shapes);
            MoveObject moveEvent  = new MoveObject(shapes);
            ResizeObject resizeEvent = new ResizeObject(shapes,drawingBox.getWidth(),drawingBox.getHeight());
            for (Object o : shapes) {
                if (o instanceof Rectangle) {
                    ((Rectangle) o).setOnMouseDragged(moveEvent);
                    ((Rectangle) o).setOnScroll(resizeEvent);
                } else if (o instanceof Polygon) {
                    ((Polygon) o).setOnMouseDragged(moveEvent);
                    ((Polygon) o).setOnScroll(resizeEvent);
                } else if (o instanceof Circle) {
                    ((Circle) o).setOnMouseDragged(moveEvent);
                    ((Circle) o).setOnScroll(resizeEvent);
                }
            }
            System.out.println("Every object correctly loaded ! \n");
        } catch (SQLException e) {
            System.err.println(e.toString());
            return false;
        }
        return true;
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
    
    
    private ArrayList<ArrayList<Boolean>> fromWritableToArray (WritableImage g, Node endingNode) {
        ArrayList<ArrayList<Boolean>> res = new ArrayList<ArrayList<Boolean>>();
        PixelReader pr = g.getPixelReader();
        PixelWriter pw = g.getPixelWriter();
        for (int i = 0; i < (int)(g.getHeight()/10); i++) {
            ArrayList<Boolean> line = new ArrayList<Boolean>();
            for (int j = 0; j < (int)(g.getWidth()/10); j++) {
                boolean available = true;
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        //System.out.println((10*i+x) +" "+ (10*j+y));
                        if (pr.getColor(10*j+x, 10*i+y).equals(Color.ALICEBLUE) == false && pr.getColor(10*j+x, 10*i+y).equals(Color.BLACK) == false && (i != endingNode.getY() || j != endingNode.getX())) {
                            available = false;
                                           // System.out.println(i+" "+j);

                        }
                    }
                }
                if (!available) {
                    for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                            pw.setColor(10*j+x, 10*i+y, Color.BLACK);
                    }
                }
                }
                line.add(available);
            }
            res.add(line);
        }
        return res;
    }
        
        
}

/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package ObjectCreation;

import static Menu.MainMenu.getLanguage;
import ObjectCreation.Shapes.Hexagon;
import ObjectCreation.Shapes.Octagon;
import ObjectCreation.Shapes.Pentagon;
import ObjectCreation.Shapes.Triangle;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class of the Object Creation <i> Scene </i>. It is composed of a Pane playing the role of a drawing surface inserted in a BorderPane.
 * @author IAThinkers
 */
public class ObjectCreation extends Parent {
    /**
     * Connection variables
     */
    private String username;
    private String adresse;
    private String mdp;
    
    /**
     * ResourceBundle variable set to access the properties file and get the translated words
     */
    private ResourceBundle messages;
    
    /**
     * Array of boolean saying if a given shape is selected to be drawn. It is ordered this way {0:Triangle, 1:Rectangle, 2:Square, 3:Pentagon, 4:Hexagon, 5:Octagon, 6:Circle}.
     */
    private Boolean[] shapeSelected;
    
    /**
     * list of all disposed shapes
     */
    private ArrayList<Node> listOfShapes;
    
    /**
     * The drawing surface
     */
    private Pane drawingBox; 
    
    /**
     * The current scene
     */
    private Scene currentScene;
    
    /**
     * We put it there to make it accessible to the eventhandler method
     */
    private ContextMenu objectOptions;
    
    public ObjectCreation(Stage primaryStage, Scene[] sceneTab) {
        
        //Firstly, we initialise all the object's variables
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("ObjectCreation/ObjectCreation",l);
        
        shapeSelected = new Boolean[7];
        for (int i = 0; i < shapeSelected.length; i++) {
            shapeSelected[i] = false;
        }
        
        currentScene = sceneTab[3];
        
        listOfShapes = new ArrayList<Node>();
                
        //Then, we setup the main container : a BorderPane
        BorderPane container = new BorderPane();
        VBox leftMenu = new VBox();
        leftMenu.setSpacing(5);
        leftMenu.setPadding(new Insets(5));
        
        Pane shapeMenuContainer = new Pane();
        //We define the gridpane that helps to choose a shape to draw
        GridPane shapeMenu = new GridPane();
        shapeMenu.setPadding(new Insets(5));
        //We will load all the png files for our shapes icons
        ImageView triangle = new ImageView(new Image(getClass().getResourceAsStream("images/triangle.png")));
        HBox triangleBox = new HBox();
        triangleBox.setPadding(new Insets(5));
        triangleBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (triangleBox.getBackground() == null || triangleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    triangleBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (triangleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        triangleBox.setBackground(null);
                    }    
                }                    
            }
        });
        triangleBox.getChildren().add(triangle);
        
        ImageView rectangle = new ImageView(new Image(getClass().getResourceAsStream("images/rectangle.png")));
        HBox rectangleBox = new HBox();
        rectangleBox.getChildren().add(rectangle);
        rectangleBox.setPadding(new Insets(5));
        rectangleBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (rectangleBox.getBackground() == null || rectangleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    rectangleBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (rectangleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        rectangleBox.setBackground(null);
                    }    
                }                    
            }
        });
        
        ImageView square = new ImageView(new Image(getClass().getResourceAsStream("images/square.png")));
        HBox squareBox = new HBox();
        squareBox.getChildren().add(square);
        squareBox.setPadding(new Insets(5));
        squareBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (squareBox.getBackground() == null || squareBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    squareBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (squareBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        squareBox.setBackground(null);
                    }
                }                    
            }
        });
        
        ImageView pentagon = new ImageView(new Image(getClass().getResourceAsStream("images/pentagon.png")));
        HBox pentagonBox = new HBox();
        pentagonBox.getChildren().add(pentagon);
        pentagonBox.setPadding(new Insets(5));
        pentagonBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (pentagonBox.getBackground() == null || pentagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    pentagonBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (pentagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        pentagonBox.setBackground(null);
                    }    
                }                    
            }
        });
        
        ImageView hexagon = new ImageView(new Image(getClass().getResourceAsStream("images/hexagon.png")));
        HBox hexagonBox = new HBox();
        hexagonBox.getChildren().add(hexagon);
        hexagonBox.setPadding(new Insets(5));
        hexagonBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (hexagonBox.getBackground() == null || hexagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    hexagonBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (hexagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        hexagonBox.setBackground(null);
                    }    
                }                    
            }
        });
        
        ImageView octagon = new ImageView(new Image(getClass().getResourceAsStream("images/octagon.png")));
        HBox octagonBox = new HBox();
        octagonBox.getChildren().add(octagon);
        octagonBox.setPadding(new Insets(5));
        octagonBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (octagonBox.getBackground() == null || octagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    octagonBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (octagonBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        octagonBox.setBackground(null);
                    }    
                }                    
            }
        });
        
        ImageView circle = new ImageView(new Image(getClass().getResourceAsStream("images/circle.png")));
        HBox circleBox = new HBox();
        circleBox.getChildren().add(circle);
        circleBox.setPadding(new Insets(5));
        circleBox.hoverProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if( newValue && (circleBox.getBackground() == null || circleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE))) {
                    circleBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (circleBox.getBackground().getFills().get(0).getFill() != (Color.SKYBLUE)) {
                        circleBox.setBackground(null);
                    }    
                }                    
            }
        });
        
        //Then we setup the onmouseclicked events that will, basically, change the background color of the corresponding shape icon and call {@link ObjectCreation.ObjectCreation.setSelected} method.
        triangleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
                circleBox.setBackground(null);
                setSelected(shapeSelected,0);//We set the index 0 at true and the others at false to indicate a triangle is about to be set
            }
        });
        rectangleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
                circleBox.setBackground(null);
                setSelected(shapeSelected,1);//We set the index 0 at true and the others at false to indicate a rectangle is about to be set
            }
        });
        squareBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(null);
                squareBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
                circleBox.setBackground(null);
                setSelected(shapeSelected,2);//We set the index 0 at true and the others at false to indicate a square is about to be set
            }
        });
        pentagonBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
                circleBox.setBackground(null);
                setSelected(shapeSelected,3);//We set the index 0 at true and the others at false to indicate a pentagon is about to be set
            }
        });
        hexagonBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                octagonBox.setBackground(null);
                circleBox.setBackground(null);
                setSelected(shapeSelected,4);//We set the index 0 at true and the others at false to indicate an hexagon is about to be set
            }
        });
        octagonBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                circleBox.setBackground(null);
                setSelected(shapeSelected,5);//We set the index 0 at true and the others at false to indicate an octagon is about to be set
            }
        });
        
        circleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(null);
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
                circleBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                setSelected(shapeSelected,6);//We set the index 0 at true and the others at false to indicate an octagon is about to be set
            }
        });
        
        GridPane.setConstraints(triangleBox, 0, 0);
        GridPane.setConstraints(rectangleBox, 1, 0);//Column = 1, row = 0;
        GridPane.setConstraints(squareBox, 2, 0);
        GridPane.setConstraints(pentagonBox, 0, 1);
        GridPane.setConstraints(hexagonBox, 1, 1);
        GridPane.setConstraints(octagonBox, 2, 1);
        GridPane.setConstraints(circleBox, 0, 2);
        
        shapeMenu.setVgap(10);
        shapeMenu.setHgap(10);
        shapeMenu.setBorder(new Border(new BorderStroke(Color.DARKSLATEBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        shapeMenu.getChildren().addAll(triangleBox,rectangleBox,squareBox,pentagonBox,hexagonBox,octagonBox,circleBox);

        Label shapeLabel = new Label(messages.getString("SHAPESELECTION"));
        shapeLabel.setTextFill(Color.DARKSLATEBLUE);
        
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setId("colorpicker");
        shapeMenuContainer.getChildren().add(shapeMenu);
        
        Text SQLinfo = new Text();
        SQLinfo.setId("sqlinfo");
        
        
        leftMenu.getChildren().addAll(shapeMenuContainer,shapeLabel,colorPicker,SQLinfo);
        leftMenu.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //After the left menu has been created, we set up the drawing surface
        drawingBox = new Pane();
        drawingBox.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        drawingBox.setOnMousePressed(drawFigure);
        drawingBox.setOnMouseDragged(drawFigure);
        drawingBox.setOnMouseReleased(drawFigure);
        //The menubar variable
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        //We setup the file menu
        Menu fileMenu = new Menu(messages.getString("FILEMENU"));
        //We setup its items
        MenuItem newMenu = new MenuItem(messages.getString("NEW"));
        MenuItem loadMenu = new MenuItem(messages.getString("LOAD"));
        loadMenu.setOnAction(new EventHandler<ActionEvent>() {
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
                loadingPopup.setHeaderText(messages.getString("WARNINGLOADING"));
                loadingPopup.setContentText(messages.getString("SELECTLOADING"));
                Optional<String> result = loadingPopup.showAndWait();
                if (result.isPresent()) {
                    loadingObject(result.get());
                }
            }
        });
        MenuItem saveAsMenu = new MenuItem(messages.getString("SAVEAS"));
        
        TextInputDialog savingPopup = new TextInputDialog("");
        savingPopup.setTitle(messages.getString("OBJECTSAVING"));
        savingPopup.setHeaderText(messages.getString("OBJECTSAVINGHEADER"));
        savingPopup.setContentText(messages.getString("OBJECTSAVINGCONTENT"));
        saveAsMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<String> result = savingPopup.showAndWait();
                if (result.isPresent()) {
                    savingObject(result.get());
                }
            }
        });
        MenuItem saveMenu = new MenuItem(messages.getString("SAVE"));
                
        //Exit button
        MenuItem exitButton = new MenuItem(messages.getString("EXIT"));
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setResizable(false);
                primaryStage.setScene(sceneTab[0]);
            }
        });
        
        
        fileMenu.getItems().addAll(newMenu,loadMenu, new SeparatorMenuItem(),saveAsMenu,saveMenu, new SeparatorMenuItem(), exitButton);
        menuBar.getMenus().addAll(fileMenu);
        container.setTop(menuBar);
        container.setLeft(leftMenu);
        container.setCenter(drawingBox);
        container.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setMargin(drawingBox, new Insets(25));
        sceneTab[3].setRoot(container);
    }
    
    /**
     * This method will set the index cell of the shapeSelected array to true and all the others to false;
     * @param shapeSelected
     * @param index 
     */
    private void setSelected(Boolean[] shapeSelected, int index) {
        if (index < shapeSelected.length && index >= 0) {
            for (int i = 0; i < index; i++) {
                shapeSelected[i] = false;
            }
            shapeSelected[index] = true;
            for (int i = index + 1; i < shapeSelected.length; i++) {
                shapeSelected[i] = false;
            }
        }    
    }
    
    /**
     * This method will return the only true boolean of the given boolean array <b> shapeSelected </b>
     * @param shapeSelected
     * @return the index of the "true" cell if found : return the size of the array if not
     */
    private int getSelected(Boolean[] shapeSelected) {
        for (int i = 0; i < shapeSelected.length; i++) {
            if (shapeSelected[i]) {
                return i;
            }
        }
        return shapeSelected.length;
    }

    
    
    /**
     * This is the EventHandler responsible for the onClick procedure of the drawingBox variable.</br>
     * Its main objective is to intuitively allow the user to draw shapes on the scene
     */
    EventHandler<MouseEvent> drawFigure = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getX()<=(drawingBox.getWidth()+drawingBox.getTranslateX()) && mouseEvent.getY()<=(drawingBox.getHeight()+drawingBox.getTranslateY()) 
                && mouseEvent.getX()>=(drawingBox.getTranslateX()) && mouseEvent.getY()>=(drawingBox.getTranslateY())) {
                switch (getSelected(shapeSelected)) {
                    //In case of a triangle
                    case 0:
                        //Firstly, at the click, we create the Triangle object and insert it into the scene
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Triangle triangle = new Triangle(mouseEvent.getX(),mouseEvent.getY());
                            triangle.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(triangle);
                        //Then, once the user has dragged on the scene, we update its sizes    
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Triangle triangle = (Triangle) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (triangle.getPosX()+Math.abs(mouseEvent.getX()-triangle.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && triangle.getPosY()+Math.abs(mouseEvent.getY()-triangle.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                triangle.setSizes(Math.abs(mouseEvent.getX()-triangle.getPosX()),Math.abs(mouseEvent.getY()-triangle.getPosY()));
                            }
                            triangle.setOnMouseClicked(onObjectClicked);
                        //And, when the click is released, we definitly save the shape in the listOfShapes variable.    
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED ) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a rectangle
                    case 1:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Rectangle rectangle = new Rectangle();
                            rectangle.setX(mouseEvent.getX());
                            rectangle.setY(mouseEvent.getY());
                            rectangle.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(rectangle);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Rectangle rectangle = (Rectangle) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (rectangle.getX()+Math.abs(mouseEvent.getX()-rectangle.getX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && rectangle.getY()+Math.abs(mouseEvent.getY()-rectangle.getY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                rectangle.setHeight(Math.abs(rectangle.getY()-mouseEvent.getY()));
                                rectangle.setWidth(Math.abs(rectangle.getX()-mouseEvent.getX()));
                            }
                            rectangle.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a square
                    case 2:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Rectangle rectangle = new Rectangle();
                            rectangle.setX(mouseEvent.getX());
                            rectangle.setY(mouseEvent.getY());
                            rectangle.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(rectangle);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Rectangle rectangle = (Rectangle) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (rectangle.getX()+Math.abs(mouseEvent.getX()-rectangle.getX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && rectangle.getY()+Math.abs(mouseEvent.getY()-rectangle.getY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                rectangle.setHeight((Math.abs(rectangle.getY()-mouseEvent.getY())+Math.abs(rectangle.getX()-mouseEvent.getX()))/2);
                                rectangle.setWidth((Math.abs(rectangle.getY()-mouseEvent.getY())+Math.abs(rectangle.getX()-mouseEvent.getX()))/2);
                            }
                            rectangle.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a pentagon
                    case 3:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Pentagon pentagon = new Pentagon(mouseEvent.getX(),mouseEvent.getY());
                            pentagon.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(pentagon);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Pentagon pentagon = (Pentagon) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (pentagon.getPosX()+Math.abs(mouseEvent.getX()-pentagon.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && pentagon.getPosY()+Math.abs(mouseEvent.getY()-pentagon.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                pentagon.setSizes(Math.abs(mouseEvent.getX()-pentagon.getPosX()),Math.abs(mouseEvent.getY()-pentagon.getPosY()));
                            }
                            pentagon.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a hexagon
                    case 4:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Hexagon hexagon = new Hexagon(mouseEvent.getX(),mouseEvent.getY());
                            hexagon.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(hexagon);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Hexagon hexagon = (Hexagon) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (hexagon.getPosX()+Math.abs(mouseEvent.getX()-hexagon.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && hexagon.getPosY()+Math.abs(mouseEvent.getY()-hexagon.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                hexagon.setSizes(Math.abs(mouseEvent.getX()-hexagon.getPosX()),Math.abs(mouseEvent.getY()-hexagon.getPosY()));
                            }
                            hexagon.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of an octagon
                    case 5:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Octagon octagon = new Octagon(mouseEvent.getX(),mouseEvent.getY());
                            octagon.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(octagon);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Octagon octagon = (Octagon) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (octagon.getPosX()+Math.abs(mouseEvent.getX()-octagon.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && octagon.getPosY()+Math.abs(mouseEvent.getY()-octagon.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                octagon.setSizes(Math.abs(mouseEvent.getX()-octagon.getPosX()),Math.abs(mouseEvent.getY()-octagon.getPosY()));
                            }
                            octagon.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a circle
                    case 6:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Circle circle = new Circle();
                            circle.setCenterX(mouseEvent.getX());
                            circle.setCenterY(mouseEvent.getY());
                            circle.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(circle);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Circle circle = (Circle) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            Double radius = Math.sqrt(Math.pow(mouseEvent.getX()-circle.centerXProperty().doubleValue(),2)+Math.pow(mouseEvent.getY()-circle.centerYProperty().doubleValue(),2));
                            if (circle.centerXProperty().doubleValue()+radius <= drawingBox.getTranslateX()+drawingBox.getWidth() && circle.centerYProperty().doubleValue()+radius <= drawingBox.getTranslateY()+drawingBox.getHeight()
                                && circle.centerXProperty().doubleValue()-radius >= drawingBox.getTranslateX() && circle.centerYProperty().doubleValue()-radius >= drawingBox.getTranslateY()) {
                                circle.setRadius(radius);
                            }
                            circle.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;    
                }
            }    
        }
    };
    
    
    /**
     * This is the EventHandler that is activated when you click on an object such as a Triangle or a Square.</br> 
     * It shows up a ContextMenu (if the click was a left-click), letting you choose between moving or erasing the concerned node
     */
    EventHandler<MouseEvent> onObjectClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                objectOptions = new ContextMenu();
                MenuItem moveItem = new MenuItem(messages.getString("MOVE"));
                moveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        drawingBox.setOnMouseMoved(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                //The moving action won't be the same depending on the selected Node. We, then, have to figure out wich kind of shape it is
                                if (mouseEvent.getSource() instanceof Triangle) {
                                    Triangle obj = (Triangle) mouseEvent.getSource();
                                    obj.move(event.getX(), event.getY(),drawingBox.getTranslateX()+drawingBox.getWidth(),drawingBox.getTranslateY()+drawingBox.getHeight());
                                } else if (mouseEvent.getSource() instanceof Rectangle) {
                                    Rectangle obj = (Rectangle) mouseEvent.getSource();
                                    obj.setX(event.getX());
                                    obj.setY(event.getY());
                                } else if (mouseEvent.getSource() instanceof Pentagon) {
                                    Pentagon obj = (Pentagon) mouseEvent.getSource();
                                    obj.move(event.getX(), event.getY(),drawingBox.getTranslateX()+drawingBox.getWidth(),drawingBox.getTranslateY()+drawingBox.getHeight());
                                } else if (mouseEvent.getSource() instanceof Hexagon) {
                                    Hexagon obj = (Hexagon) mouseEvent.getSource();
                                    obj.move(event.getX(), event.getY(),drawingBox.getTranslateX()+drawingBox.getWidth(),drawingBox.getTranslateY()+drawingBox.getHeight());
                                } else if (mouseEvent.getSource() instanceof Octagon) {
                                    Octagon obj = (Octagon) mouseEvent.getSource();
                                    obj.move(event.getX(), event.getY(),drawingBox.getTranslateX()+drawingBox.getWidth(),drawingBox.getTranslateY()+drawingBox.getHeight());
                                } else if (mouseEvent.getSource() instanceof Circle) {
                                    Circle obj = (Circle) mouseEvent.getSource();
                                    if (event.getX() <= drawingBox.getTranslateX()+drawingBox.getWidth()-obj.getRadius() && event.getY() <= drawingBox.getTranslateY()+drawingBox.getHeight()-obj.getRadius()
                                            && event.getX()-obj.getRadius() >=0 && event.getY()-obj.getRadius() >= 0) {
                                        obj.setCenterX(event.getX());
                                        obj.setCenterY(event.getY());
                                    }
                                }
                            };
                        });
                        drawingBox.setOnMouseClicked((event) -> {
                            drawingBox.setOnMouseMoved(null);
                        });
                    }    
                });
                MenuItem deleteItem = new MenuItem(messages.getString("DELETE"));
                deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        listOfShapes.remove(mouseEvent.getSource());
                        drawingBox.getChildren().remove(mouseEvent.getSource());
                    }
                });
                objectOptions.getItems().addAll(moveItem,deleteItem);
                objectOptions.show((Node) mouseEvent.getSource(),mouseEvent.getScreenX(),mouseEvent.getScreenY());
            }    
        }
    };
        
    public boolean savingObject(String objectName) {
        //We make sure the credentials of the database are set
        try {
            setCredentials();
            Connection connect = DriverManager.getConnection("jdbc:mysql://"+this.getAdresse()+"/iathinkers?"+ "user="+this.getUsername()+"&password="+this.getMdp());
            //We check if there is not an  existing object with this name
            Statement statement = connect.createStatement();
            String request = "SELECT * FROM FREEHANDOBJECT WHERE objetName=\'"+objectName+"\'";
            ResultSet rs = statement.executeQuery(request);
            if (!rs.next()) {
                if (statement.executeUpdate("INSERT INTO FREEHANDOBJECT(OBJETNAME) VALUES (\'"+objectName+"\')") != -1) {
                    System.out.println("FREEHANDOBJECT VALUE INSERTED");
                }
            } else {
                Text SQLinfo = (Text) currentScene.lookup("#sqlinfo");
                SQLinfo.setText(messages.getString("EXISTINGOBJECT"));
                return false;
            }
            int idFreehandObject;
            int idTriangle = 0;
            int idRectangle = 1;
            int idSquare = 2;
            int idPentagon = 3;
            int idHexagon = 4;
            int idOctagon = 5;
            int idCircle = 6;
            
            rs = statement.executeQuery(request);
            if (rs.next()) {
                idFreehandObject = rs.getInt("idObject");
            } else {
                return false;
            }
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
            /* 
             * In our saving process, we won't save the positioning and sizes informations in pixels but in percentage.
             * Indeed, we want our object to be versatile and usable in every situation. So they should not be supporting the constraints of their first container 
             * as their main objective will be to be put in the freehand interface
            */
            for (Object o : this.getListOfShapes()) {
                if (o instanceof Triangle) {
                    Triangle obj = (Triangle) o;
                    double posX = obj.getPosX()/drawingBox.getWidth();
                    double posY = obj.getPosY()/drawingBox.getHeight();
                    double sizeX = obj.getSizeX()/drawingBox.getWidth();
                    double sizeY = obj.getSizeY()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (stroke != null) {
                        request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idTriangle+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                    } else {
                        request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idTriangle+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                    }    
                } else if (o instanceof Rectangle) {
                    Rectangle obj = (Rectangle) o;
                    double posX = obj.getX()/drawingBox.getWidth();
                    double posY = obj.getY()/drawingBox.getHeight();
                    double sizeX = obj.getWidth()/drawingBox.getWidth();
                    double sizeY = obj.getHeight()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (obj.getWidth() == obj.getHeight()) {
                        if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idSquare+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idSquare+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                    } else {
                        if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idRectangle+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idRectangle+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                    }
                } else if (o instanceof Pentagon) {
                    Pentagon obj = (Pentagon) o;
                    double posX = obj.getPosX()/drawingBox.getWidth();
                    double posY = obj.getPosY()/drawingBox.getHeight();
                    double sizeX = obj.getSizeX()/drawingBox.getWidth();
                    double sizeY = obj.getSizeY()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idPentagon+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idPentagon+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                } else if (o instanceof Hexagon) {
                    Hexagon obj = (Hexagon) o;
                    double posX = obj.getPosX()/drawingBox.getWidth();
                    double posY = obj.getPosY()/drawingBox.getHeight();
                    double sizeX = obj.getSizeX()/drawingBox.getWidth();
                    double sizeY = obj.getSizeY()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idHexagon+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idHexagon+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                } else if (o instanceof Octagon) {
                    Octagon obj = (Octagon) o;
                    double posX = obj.getPosX()/drawingBox.getWidth();
                    double posY = obj.getPosY()/drawingBox.getHeight();
                    double sizeX = obj.getSizeX()/drawingBox.getWidth();
                    double sizeY = obj.getSizeY()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idOctagon+", \'"+obj.getFill().toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idOctagon+", \'"+obj.getFill().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                } else if (o instanceof Circle) {
                    Circle obj = (Circle) o;
                    double posX = obj.getCenterX()/drawingBox.getWidth();
                    double posY = obj.getCenterY()/drawingBox.getHeight();
                    double sizeX = obj.getRadius()/drawingBox.getWidth();
                    double sizeY = obj.getRadius()/drawingBox.getHeight();
                    Paint stroke = obj.getStroke();
                    Paint fill = obj.getFill();
                    if (stroke != null) {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,borderColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idCircle+", \'"+fill.toString()+"\', \'"+obj.getStroke().toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        } else {
                            request = "INSERT INTO SHAPESINSTANCES(shapeType,fillColor,posX,posY,sizeX,sizeY,object) VALUES("+
                                idCircle+", \'"+fill.toString()+"\', "+posX+", "+posY+", "+sizeX+", "+sizeY+", "+idFreehandObject+");";
                        }
                }
                try {
                    if (statement.executeUpdate(request) != -1) {
                        if (o instanceof Triangle) {
                            System.out.println(((Triangle)o).toString() +" saved !");
                        } else if (o instanceof Rectangle) {
                            System.out.println(((Rectangle)o).toString() +" saved !");
                        } else if (o instanceof Pentagon) {
                            System.out.println(((Pentagon)o).toString() +" saved !");
                        } else if (o instanceof Hexagon) {
                            System.out.println(((Hexagon)o).toString() +" saved !");
                        } else if (o instanceof Octagon) {
                            System.out.println(((Octagon)o).toString() +" saved !");
                        } else if (o instanceof Circle) {
                            System.out.println(((Circle)o).toString() +" saved !");
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ObjectCreation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("\n Every Shapes saved ! \n");
            Text SQLinfo = (Text) currentScene.lookup("#sqlinfo");
            SQLinfo.setText(messages.getString("SAVINGACHIEVED"));
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectCreation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    } 
    
    public boolean loadingObject(String objectName) {
        try {
            //We empty the drawing surface and the list of shapes
            drawingBox.getChildren().clear();
            this.getListOfShapes().clear();
            setCredentials();
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
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idRectangle) {
                    Rectangle obj = new Rectangle(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idSquare) {
                    Rectangle obj = new Rectangle(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idPentagon) {
                    Pentagon obj = new Pentagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idHexagon) {
                    Hexagon obj = new Hexagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idOctagon) {
                    Octagon obj = new Octagon(rs.getDouble("posX")*drawingBox.getWidth(),rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setSizes(rs.getDouble("sizeX")*drawingBox.getWidth(),rs.getDouble("sizeY")*drawingBox.getHeight());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
                } else if (rs.getInt("shapeType") == idCircle) {
                    Circle obj = new Circle();
                    obj.setCenterX(rs.getDouble("posX")*drawingBox.getWidth());
                    obj.setCenterY(rs.getDouble("posY")*drawingBox.getHeight());
                    obj.setRadius(rs.getDouble("sizeX")*drawingBox.getWidth());
                    obj.setFill(Color.valueOf(rs.getString("fillColor")));
                    if (rs.getString("borderColor") != null) {
                        obj.setStroke(Color.valueOf(rs.getString("borderColor")));
                    }
                    obj.setOnMouseClicked(onObjectClicked);
                    this.getListOfShapes().add(obj);
                    drawingBox.getChildren().add(obj);
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
    
    public Boolean[] getShapeSelected() {
        return shapeSelected;
    }

    public void setShapeSelected(Boolean[] shapeSelected) {
        this.shapeSelected = shapeSelected;
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

    public ArrayList<Node> getListOfShapes() {
        return listOfShapes;
    }

    public void setListOfShapes(ArrayList<Node> listOfShapes) {
        this.listOfShapes = listOfShapes;
    }
    
    
    
}

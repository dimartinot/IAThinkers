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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ObjectCreation extends Parent {
    
    private ResourceBundle messages;
    
    private Boolean[] shapeSelected;
    
    //list of javafx nodes 
    private ArrayList<Node> listOfShapes;
    
    private Pane drawingBox; 
    
    private Scene currentScene;
    
    //We put it there to make it accessible to the eventhandler method
    private ContextMenu objectOptions;
    
    public ObjectCreation(Stage primaryStage, Scene[] sceneTab) {
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("ObjectCreation/ObjectCreation",l);
        
        shapeSelected = new Boolean[6];
        for (int i = 0; i < shapeSelected.length; i++) {
            shapeSelected[i] = false;
        }
        
        currentScene = sceneTab[3];
        
        listOfShapes = new ArrayList<Node>();
                
        BorderPane container = new BorderPane();
        VBox leftMenu = new VBox();
        leftMenu.setSpacing(10);
        leftMenu.setPadding(new Insets(10));
        
        Pane shapeMenuContainer = new Pane();
        //We define the gridpane that helps to choose a shape to draw
        GridPane shapeMenu = new GridPane();
        shapeMenu.setPadding(new Insets(5));
        //We will load all the svg files for our icons
        ImageView triangle = new ImageView(new Image(getClass().getResourceAsStream("images/triangle.png")));
        HBox triangleBox = new HBox();
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
        
        //Then we setup the onmouseclicked events
        triangleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                triangleBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                rectangleBox.setBackground(null);
                squareBox.setBackground(null);
                pentagonBox.setBackground(null);
                hexagonBox.setBackground(null);
                octagonBox.setBackground(null);
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
                setSelected(shapeSelected,5);//We set the index 0 at true and the others at false to indicate an octagon is about to be set
            }
        });
        
        
        GridPane.setConstraints(triangleBox, 0, 0);
        GridPane.setConstraints(rectangleBox, 1, 0);//Column = 1, row = 0;
        GridPane.setConstraints(squareBox, 2, 0);
        GridPane.setConstraints(pentagonBox, 0, 1);
        GridPane.setConstraints(hexagonBox, 1, 1);
        GridPane.setConstraints(octagonBox, 2, 1);
        
        shapeMenu.setVgap(10);
        shapeMenu.setHgap(10);
        shapeMenu.setBorder(new Border(new BorderStroke(Color.DARKSLATEBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        shapeMenu.getChildren().addAll(triangleBox,rectangleBox,squareBox,pentagonBox,hexagonBox,octagonBox);

        Label shapeLabel = new Label(messages.getString("SHAPESELECTION"));
        shapeLabel.setTextFill(Color.DARKSLATEBLUE);
        
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setId("colorpicker");
        shapeMenuContainer.getChildren().add(shapeMenu);
        leftMenu.getChildren().addAll(shapeMenuContainer,shapeLabel,colorPicker);
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
        MenuItem saveAsMenu = new MenuItem(messages.getString("SAVEAS"));
        MenuItem saveMenu = new MenuItem(messages.getString("SAVE"));
                
        fileMenu.getItems().addAll(newMenu,saveAsMenu,saveMenu);
        menuBar.getMenus().addAll(fileMenu);
        container.setTop(menuBar);
        container.setLeft(leftMenu);
        container.setCenter(drawingBox);
        container.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setMargin(drawingBox, new Insets(25));
        sceneTab[3].setRoot(container);
    }
    
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
    
    private int getSelected(Boolean[] shapeSelected) {
        for (int i = 0; i < shapeSelected.length; i++) {
            if (shapeSelected[i]) {
                return i;
            }
        }
        return shapeSelected.length;
    }

    
    
    
    EventHandler<MouseEvent> drawFigure = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getX()<=(drawingBox.getWidth()+drawingBox.getTranslateX()) && mouseEvent.getY()<=(drawingBox.getHeight()+drawingBox.getTranslateY()) 
                && mouseEvent.getX()>=(drawingBox.getTranslateX()) && mouseEvent.getY()>=(drawingBox.getTranslateY())) {
                switch (getSelected(shapeSelected)) {
                    //In case of a triangle
                    case 0:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Triangle triangle = new Triangle();
                            triangle.setPosX(mouseEvent.getX());
                            triangle.setPosY(mouseEvent.getY());
                            triangle.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(triangle);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Triangle triangle = (Triangle) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (triangle.getPosX()+Math.abs(mouseEvent.getX()-triangle.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && triangle.getPosY()+Math.abs(mouseEvent.getY()-triangle.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                triangle.setSizes(Math.abs(mouseEvent.getX()-triangle.getPosX()),Math.abs(mouseEvent.getY()-triangle.getPosY()));
                            }
                            triangle.setOnMouseClicked(onObjectClicked);
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
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.isPrimaryButtonDown()) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a pentagon
                    case 3:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Pentagon pentagon = new Pentagon();
                            pentagon.setPosX(mouseEvent.getX());
                            pentagon.setPosY(mouseEvent.getY());
                            pentagon.setFill(((ColorPicker) currentScene.lookup("#colorpicker")).getValue());
                            drawingBox.getChildren().add(pentagon);
                        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.isPrimaryButtonDown()) {
                            Pentagon pentagon = (Pentagon) drawingBox.getChildren().get(drawingBox.getChildren().size()-1);
                            if (pentagon.getPosX()+Math.abs(mouseEvent.getX()-pentagon.getPosX()) <= drawingBox.getTranslateX()+drawingBox.getWidth() && pentagon.getPosY()+Math.abs(mouseEvent.getY()-pentagon.getPosY()) <= drawingBox.getTranslateY()+drawingBox.getHeight()) {
                                pentagon.setSizes(Math.abs(mouseEvent.getX()-pentagon.getPosX()),Math.abs(mouseEvent.getY()-pentagon.getPosY()));
                            }
                            pentagon.setOnMouseClicked(onObjectClicked);
                        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && mouseEvent.isPrimaryButtonDown()) {
                            if (listOfShapes.contains(drawingBox.getChildren().get(drawingBox.getChildren().size()-1)) == false) {
                                listOfShapes.add(drawingBox.getChildren().get(drawingBox.getChildren().size()-1));
                            }
                        }
                        break;
                    //In case of a hexagon
                    case 4:
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Hexagon hexagon = new Hexagon();
                            hexagon.setPosX(mouseEvent.getX());
                            hexagon.setPosY(mouseEvent.getY());
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
                            Octagon octagon = new Octagon();
                            octagon.setPosX(mouseEvent.getX());
                            octagon.setPosY(mouseEvent.getY());
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
                }
            }    
        }
    };
    
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
        
    public Boolean[] getShapeSelected() {
        return shapeSelected;
    }

    public void setShapeSelected(Boolean[] shapeSelected) {
        this.shapeSelected = shapeSelected;
    }
    
}

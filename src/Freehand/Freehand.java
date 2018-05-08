/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand;

import static Menu.MainMenu.getLanguage;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Freehand extends Parent {
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
     * 
     * @param primaryStage
     * @param sceneTab 
     */
    public Freehand(Stage primaryStage, Scene[] sceneTab) {
        currentState = null;
        firstIsDone = false;
        secondIsDone = false;
        startingIsDone = false;
        endingIsDone = false;
        scene = sceneTab[2];
        
        Locale l = getLanguage();
        ResourceBundle messages = ResourceBundle.getBundle("Freehand/Freehand",l);
        
        //We initialize the menubar with its item
        MenuBar menuBar = new MenuBar();
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
        
        //Then, the tools menu
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
        MenuItem menuPixels = new MenuItem(messages.getString("PIXELS"));
        menuPixels.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (firstIsDone && secondIsDone) {
                    drawingBox.setOnMouseClicked(setPoints);
                }
            }
        });
        menuTools.getItems().addAll(menuEmpty,menuPixels);
        
        
        menuBar.getMenus().addAll(menuFile,menuTools);
        
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
        info.getChildren().addAll(startingInfo,endingInfo);
        
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
    
    public void drawCross(Pane drawing, int x, int y) {
        Line line1 = new Line(x-10, y-10, x+10, y+10);
        Line line2 = new Line(x-10, y+10, x+10, y-10);
        drawing.getChildren().addAll(line1,line2);
    }
    
    EventHandler<MouseEvent> drawPath = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Path path = new Path();
            if (getFirstIsDone() == false) {
                path = getFirstPath();
            } else if (getSecondIsDone() == false) {
                path = getSecondPath();
            } else {
                setCurrentState(drawingBox.snapshot(null, getCurrentState()));
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

    EventHandler<MouseEvent> setPoints = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (getStartingIsDone() == false) {
                start = new StartingPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());
                setStartingIsDone(true);
                System.out.println(start.toString());
                Text startingInfo = (Text) scene.lookup("#startinginfo");
                startingInfo.setText(startingInfo.getText()+start.toString());
                drawCross(drawingBox,(int)mouseEvent.getX(),(int)mouseEvent.getY());
            } else if (getEndingIsDone() == false) {
                end = new EndingPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());
                setEndingIsDone(true);
                System.out.println(end.toString());
                Text endingInfo = (Text) scene.lookup("#endinginfo");
                endingInfo.setText(endingInfo.getText()+end.toString());
                drawCross(drawingBox,(int)mouseEvent.getX(),(int)mouseEvent.getY());
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

    
    
        
        
}

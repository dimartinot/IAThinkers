
package Plan;

import static Menu.MainMenu.getLanguage;
import Objet.Point;
import Objet.PointType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Grid Class. This class defines a grid as an <i> ArrayList </i> of Objects and an <i> HashMap </i> of cells {@link Cell}.
 * @author IAThinkers
 */
public class Grid extends Parent{
    
    /**
     * Integers defining the Scene size in pixels
     */
    private int sceneWidth;
    private int sceneHeight;

    /**
     * Integer defining the Scene size in cells
     */
    private int n = 50;
    private int m = 50;
    
    /**
     * ArrayList variable used to store all the objects (Wall, Door, Points,...) that had been put on the grid
     */
    private ArrayList<Object> listObjects;
    
    private Point start;
    private Point end;
    
    /**
     * HashMap variable storing every cell of the grid using a key following this syntax : '#i-j' where i is the column of the cell and j its row.
     */
    private HashMap<String,Cell> listCells;
    
    //Defines if both the starting and ending point are defined
    private boolean pointAIsSet = false;
    private boolean pointBIsSet = false;
    /**
     * Width and height, in pixel, of a cell of the grid.
     */
    int gridWidth;
    int gridHeight;
    
    private ResourceBundle messages;
    
    /**
     * Constructor of a grid : initialises every cells of the grid, giving it its <i> hover </i> and <i> onClick </i> properties
     * @param scene Scene variable describing the Plan scene.
     */
    public Grid(Scene scene, int height, int width) {
        
        this.sceneHeight = height;
        this.sceneWidth = width;
        this.gridWidth = sceneWidth / n;
        this.gridHeight = sceneHeight / m;
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Plan/Plan",l);
        GridPane grille = new GridPane();
        grille.setHgap(0);
        grille.setVgap(0);
        grille.setPrefSize(sceneWidth, sceneHeight);
        grille.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        this.listObjects = new ArrayList<Object>();
        this.listCells = new HashMap<String,Cell>();
        this.getStylesheets().add(this.getClass().getResource("plan.css").toExternalForm());
        /**
         * Loop initialising every cells and storing them in the HashMap
         */
        for (int i = 0; i < n; i++) {
            /*ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/n);
            grille.getColumnConstraints().add(column);
            
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/m);
            grille.getRowConstraints().add(row);*/
            for (int j = 0; j < m; j++) {
                Cell cellule = new Cell(gridWidth, gridHeight, i * gridWidth, j * gridHeight,i,j,n,m);
                cellule.hoverProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if( newValue) {
                            (cellule).hoverHighlight(scene);
                        } else {
                            (cellule).hoverUnhighlight(scene);
                        }                    
                    }
                });
                cellule.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        ComboBox objectList = (ComboBox) scene.lookup("#objectlist");
                        ComboBox choix = (ComboBox) scene.lookup("#choixobjet");
                        if (choix.getSelectionModel().getSelectedItem()==messages.getString("WALL")) {
                            //We get the values entered for the dimensions of the wall
                            TextField heightTxt = (TextField) scene.lookup("#height");
                            TextField widthTxt = (TextField) scene.lookup("#width");
                            //We, then, convert it to an integer and loop on all chosen concerned rectangles
                            try {
                                boolean fits = true;
                                int height = Integer.parseInt(heightTxt.getText());
                                int width = Integer.parseInt(widthTxt.getText());
                                //Firstly, we search for any potential conflict between existing objects and the ones intended to be put
                                for (int i = 0; i < height; i++) {
                                    for (int j = 0; j < width; j++) {
                                        if ((cellule.getY() + i)< n && (cellule.getX() + j)< m) {
                                            try {
                                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX() + j)+"-"+(cellule.getY() + i));
                                                if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE || rectangle.getFill() == Color.TEAL || rectangle.getFill() == Color.BROWN) {
                                                    fits = false;
                                                }
                                            } catch (NullPointerException e) {

                                            } 
                                        } else {
                                            fits = false;
                                        }
                                    }
                                }
                                if (fits) {
                                    addWall(height,width,cellule.getX(),cellule.getY(),scene);
                                }    
                            } catch (NumberFormatException e) {
                                //e.printStackTrace();
                            }
                        } else if (choix.getSelectionModel().getSelectedItem()==messages.getString("DOOR")) {
                                boolean fits= true;
                                ComboBox orientation = (ComboBox) scene.lookup("#orientation");
                                try {
                                    if (orientation.getSelectionModel().getSelectedItem()==messages.getString("VERTICAL")) {
                                        if ((cellule.getY()-1)>0 && (cellule.getY()+1)<m-1) {
                                            try {
                                                for (int i =0; i< 3; i++) {
                                                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()+i-1));
                                                    if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE || rectangle.getFill() == Color.TEAL || rectangle.getFill() == Color.BROWN) {
                                                        fits = false;
                                                    } 
                                                }

                                            } catch (NullPointerException e) {

                                            }
                                        }
                                        if (fits) {
                                            addDoor(cellule.getX(),cellule.getY(),true,scene);
                                        }
                                    } else if (orientation.getSelectionModel().getSelectedItem()==messages.getString("HORIZONTAL")) {
                                       if ((cellule.getY()-1)>0 && (cellule.getY()+1)<m-1) {
                                            try {
                                                for (int i =0; i< 3; i++) {
                                                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX()+i-1)+"-"+(cellule.getY()));
                                                    if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE || rectangle.getFill() == Color.TEAL || rectangle.getFill() == Color.BROWN) {
                                                        fits = false;
                                                    } 
                                                }

                                            } catch (NullPointerException e) {

                                            }
                                        }
                                        if (fits) {
                                            addDoor(cellule.getX(),cellule.getY(),false,scene);
                                        }
                                    } else {
                                        
                                    }
                            } catch (NumberFormatException e) {
                                //e.printStackTrace();
                            }
                        } else if (choix.getSelectionModel().getSelectedItem()==messages.getString("STARTING POINT")) {
                            if (isPointAIsSet()==false) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL) {
                                    addPointA(cellule.getX(),cellule.getY(),scene);
                                }    
                            }
                        } else if (choix.getSelectionModel().getSelectedItem()==messages.getString("ENDING POINT")) {
                            if (isPointBIsSet()==false) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.BROWN) {
                                    addPointB(cellule.getX(),cellule.getY(),scene);
                                }
                            }
                        } else {
                            
                        }
                    }
                });
                grille.add(cellule, i+1, j+1);
                listCells.put("#"+(cellule.getX())+"-"+(cellule.getY()), cellule);
            }
        }
        this.getChildren().add(grille);
    }
    
    /**
     * Getter of the gridWidth variable
     * @return gridWidth
     */
    public int getGridWidth() {
        return this.gridWidth;
    }
    
    /**
     * Getter of the gridHeight variable
     * @return gridHeight
     */
    public int getGridHeight() {
        return this.gridHeight;
    }
    
    public void setGridWidth(int size) {
        this.gridWidth = size;
    }

    public void setGridHeight(int size) {
        this.gridHeight = size;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
    }
    
    
    
    public void setGridSize(Scene scene, int size) {
        Double convertedSize = new Double(size);
        Double width = scene.getWidth();//We get the height in order to keep squared cells
        Double height = scene.getHeight();
        Double updatedWidth = (width/5+4*height/5)*size/100;
        Double updatedHeight = (width/5+4*height/5)*size/100;
        if (size > 0) {
            this.setSceneHeight(updatedHeight.intValue());
            this.setSceneWidth(updatedWidth.intValue());
            this.setGridWidth(this.getSceneHeight()/this.n);
            this.setGridHeight(this.getSceneWidth()/this.m);
        }
        GridPane gp = (GridPane) this.getChildren().get(0);
        gp.setPrefSize(this.getSceneHeight(), this.getSceneWidth());
        for (Node n : gp.getChildren()) {
            if (n instanceof Cell) {
                for (Node nbis : ((Cell) n).getChildren()) {
                    Rectangle rect = (Rectangle) nbis;
                    rect.setHeight(this.getGridHeight());
                    rect.setWidth(this.getGridWidth());
                }
            }
        }
        /*for (int i = 0; i < n; i++) {
            gp.getColumnConstraints().clear();
            gp.getRowConstraints().clear();
        }
        for (int i = 0; i < n; i++) {
            gp.getColumnConstraints().add(new ColumnConstraints(this.getGridHeight()));
            gp.getRowConstraints().add(new RowConstraints(this.getGridHeight()));
        }*/
    }
    
    /**
     * Getter of the pointAIsSet variable
     * @return pointAIsSet
     */
    public boolean isPointAIsSet() {
        return pointAIsSet;
    }
    
    /**
     * Getter of the pointBIsSet variable
     * @return pointBIsSet
     */
    public boolean isPointBIsSet() {
        return pointBIsSet;
    }
    
    /**
     * Setter of the pointAIsSet variable
     * @param pointAIsSet : boolean to set pointAIsSet variable to
     */
    public void setPointAIsSet(boolean pointAIsSet) {
        this.pointAIsSet = pointAIsSet;
    }

    /**
     * Setter of the pointBIsSet variable
     * @param pointBIsSet : boolean to set pointBIsSet variable to
     */
    public void setPointBIsSet(boolean pointBIsSet) {
        this.pointBIsSet = pointBIsSet;
    }
    
    /**
     * Getter of the ArrayList
     * @return listObjects
     */
    public ArrayList<Object> getListObjects() {
        return listObjects;
    }

    /**
     * Getter of the HashMap
     * @return listCells
     */
    public HashMap<String, Cell> getListCells() {
        return listCells;
    }
    
    /**
     * Method used to delete a wall using its properties 
     * @param height height property of the wall
     * @param width width property of the wall
     * @param posX X position property of the wall
     * @param posY Y position property of the wall
     * @return true if correctly deleted
     */
    public boolean deleteWall(int height, int width, int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Wall) {
                Objet.Wall w = (Objet.Wall) o;
                if (w.getHeight() == height && w.getWidth() == width && w.getPosX() == posX && w.getPosY() == posY ) {
                    for (int i = 0; i < w.getHeight(); i++) {
                        for (int j = 0; j < w.getWidth(); j++) {
                            this.getListCells().get("#"+(w.getPosX()+j)+"-"+(w.getPosY()+i)).setOccupied(false);
                        }
                    }
                    listObjects.remove(listObjects.indexOf(w));
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Method used to delete a door using its properties 
     * @param height height property of the door
     * @param width height property of the door
     * @param posX X position property of the door
     * @param posY Y position property of the door
     * @return true if correctly deleted
     */
    public boolean deleteDoor(int height, int width, int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Door) {
                Objet.Door d = (Objet.Door) o;
                this.getListCells().get("#"+(d.getPosX())+"-"+(d.getPosY())).setOccupied(false);
                if (d.getHeight() == height && d.getWidth() == width && d.getPosX() == posX && d.getPosY() == posY) {
                    listObjects.remove(listObjects.indexOf(d));
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Method used to delete a PointA using its properties 
     * @param posX X position property of the PointA
     * @param posY Y position property of the PointA
     * @return true if correctly deleted
     */
    public boolean deletePointA(int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                this.getListCells().get("#"+(p.getPosX())+"-"+(p.getPosY())).setOccupied(false);
                if (p.getPosX() == posX && p.getPosY() == posY && p.getType() == PointType.POINTA) {
                    listObjects.remove(listObjects.indexOf(p));
                    setPointAIsSet(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Method used to delete a PointB using its properties
     * @param posX X position property of the PointB
     * @param posY Y position property of the PointB
     * @return true if correctly deleted
     */
    public boolean deletePointB(int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                this.getListCells().get("#"+(p.getPosX())+"-"+(p.getPosY())).setOccupied(false);
                if (p.getPosX() == posX && p.getPosY() == posY && p.getType() == PointType.POINTB) {
                    listObjects.remove(listObjects.indexOf(p));
                    setPointBIsSet(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Method used to add a wall to the scene using given properties
     * @param height height property of the wall
     * @param width width property of the wall
     * @param posX X position property of the wall
     * @param posY Y position property of the wall
     * @param scene Scene variable describing the Plan scene
     * @return true if correctly added
     */
    public boolean addWall(int height, int width, int posX, int posY, Scene scene) {
        ComboBox objectList = (ComboBox) scene.lookup("#objectlist");
        Objet.Wall w = new Objet.Wall(height,width,posX,posY);
        objectList.getItems().add(w.toString());
        this.getListObjects().add(w);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cellule = listCells.get("#"+(posX+j)+"-"+(posY+i));
                if ((cellule.getY())<= n && (cellule.getX())<= m) {
                    try {
                        Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                        rectangle.setFill(Color.THISTLE);
                        cellule.setOccupied(true);
                    } catch (NullPointerException e) {
                        return false;
                    }
                }
                listCells.replace("#"+(posX+j)+"-"+(posY+i), cellule);
            }
        }
        return true;
    }

    
    /**
     * Method used to add a door to the scene using given properties 
     * @param posX X position property of the door
     * @param posY Y position property of the door
     * @param isVertical defines the orientation of the door
     * @param scene Scene variable describing the Plan scene
     * @return true if correctly added
     */    
    public boolean addDoor(int posX, int posY, boolean isVertical, Scene scene) {
        ComboBox objectList = (ComboBox) scene.lookup("#objectlist");
        Objet.Door d = new Objet.Door(1,1,posX,posY,isVertical);
        objectList.getItems().add(d.toString());
        this.getListObjects().add(d);
        Cell cellule = listCells.get("#"+(posX)+"-"+(posY));
        if ((cellule.getY())<= n && (cellule.getX())<= m) {
            try {
                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                rectangle.setFill(Color.ANTIQUEWHITE);
                //cellule.setOccupied(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        return true;
}
    
    /**
     * Method used to add a PointA to the scene using given properties 
     * @param posX X position property of the PointA
     * @param posY Y position property of the PointA
     * @param scene Scene variable describing the Plan scene
     * @return true if correctly added
     */
    public boolean addPointA(int posX, int posY, Scene scene) {
        ComboBox objectList = (ComboBox) scene.lookup("#objectlist");
        Objet.Point p = new Objet.Point(PointType.POINTA,posX,posY);
        objectList.getItems().add(p.toString());
        this.getListObjects().add(p);
        Cell cellule = listCells.get("#"+(posX)+"-"+(posY));
        if ((cellule.getY())<= n && (cellule.getX())<= m) {
            try {
                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                rectangle.setFill(Color.BROWN);
                //cellule.setOccupied(true);
                setPointAIsSet(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        this.start = p;
        return true;
}
    
    /**
     * Method used to add a PointB to the scene using given properties 
     * @param posX X position property of the PointB
     * @param posY Y position property of the PointB
     * @param scene Scene variable describing the Plan scene
     * @return true if correctly added
     */
    public boolean addPointB(int posX, int posY, Scene scene) {
        ComboBox objectList = (ComboBox) scene.lookup("#objectlist");
        Objet.Point p = new Objet.Point(PointType.POINTB,posX,posY);
        objectList.getItems().add(p.toString());
        this.getListObjects().add(p);
        Cell cellule = listCells.get("#"+(posX)+"-"+(posY));
        if ((cellule.getY())<= n && (cellule.getX())<= m) {
            try {
                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                rectangle.setFill(Color.TEAL);
                //cellule.setOccupied(true);
                setPointBIsSet(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        this.end = p;
        return true;
}
    
    /**
     * Print the contents of the listObjects variable
     */
    public void printListObjects() {
        for (Object o : listObjects) {
            if (o instanceof Objet.Wall) {
                System.out.println(((Objet.Wall) o).toString());
            } else if (o instanceof Objet.Door) {
                System.out.println(((Objet.Door) o).toString());
            } else {
                System.out.println(((Objet.Point) o).toString());
            }
        }
        System.out.println("------------------");
    }

    /**
     * Search in the list of objects the starting point
     * @return the PointA object
     */
    public Objet.Point getStartingPoint() {
        for (Object o : listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                if (p.getType() == PointType.POINTA) {
                    return p;
                }
            }
        }
        return null;
    }
        
    /**
     * Searches in the list of objects the ending point
     * @return the PointB object
     */
    public Objet.Point getEndingPoint() {
        for (Object o : listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                if (p.getType() == PointType.POINTB) {
                    return p;
                }
            }
        }
        return null;
    }
    
    /**
     * Counts the number of 1 sized blocks there is in all the disposed blocks
     * @return the number of 1 sized blocks as an int
     */
    public int numberOfBlocks() {
        int number = 0;
        for (Object o : listObjects) {
            if (o instanceof Objet.Wall) {
                Objet.Wall p = (Objet.Wall) o;
                number += p.getHeight()*p.getWidth();
            }
        }
        return number;
    }
    
    @Override
    public String toString() {
        return java.text.MessageFormat.format(("GRILLE{" + "SCENEWIDTH={0}, SCENEHEIGHT={1}, LISTOBJECTS={2}, POINTAISSET={3}, POINTBISSET={4}"), new Object[] {sceneWidth, sceneHeight, listObjects, pointAIsSet, pointBIsSet, '}'});
    }

    /**
     * Change the state of a door : pass it from being close to being open and vice versa
     * @param height
     * @param width
     * @param posX
     * @param posY
     * @return 
     */
    public boolean changeDoor(int height, int width, int posX, int posY) {
        for (Object o : listObjects) {
            if (o instanceof Objet.Door){
                Objet.Door d = (Objet.Door) o;
                if (d.equals(new Objet.Door(height,width,posX,posY,d.getIsVertical()))) {
                    if (((Objet.Door) o).isClosed()) {
                        ((Objet.Door) o).open();
                        Cell cellule = listCells.get("#"+(posX)+"-"+(posY));
                        cellule.setOccupied(false);
                        
                        return true;
                    } else {
                        ((Objet.Door) o).close();
                        Cell cellule = listCells.get("#"+(posX)+"-"+(posY));
                        cellule.setOccupied(true);
                        System.out.println(true);
                        return true;
                    }
                }    
            }
        }
        return false;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    
    
    
    
}

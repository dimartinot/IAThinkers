/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import Objet.PointType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Classe de la grille. Cette classe définit une grille comme un <i> Group </i> de cellules {@link Cell}.
 * @author Admin
 */
public class Grille extends Parent{
    
    /**
     * Attributs définissant la taille (en pixels) de la grille
     */
    private final int sceneWidth = 600;
    private final int sceneHeight = 600;

    /**
     * Attributs définissant le nombre de cellules (modifiable dans des versions futures)
     */
    private int n = 50;
    private int m = 50;
    
    /**
     * Variables de boucle passées globales pour pouvoir être utilisées dans le handler des cellules. 
     */
    public int i;
    public int j;
    
    private ArrayList<Object> listObjects;
    private HashMap<String,Cell> listCells;
    
    //Defines if both the starting and ending point are defined
    private boolean pointAIsSet = false;
    private boolean pointBIsSet = false;

    /**
     * Tailles en largeur et longueur de chacunes des cases
     */
    int gridWidth = sceneWidth / n;
    int gridHeight = sceneHeight / m;
    /**
     * Constructeur d'une grille : va initialiser toutes les cellules sous jacentes à cette dernière.
     * @param scene 
     */
    public Grille(Scene scene) {
        Group grille = new Group();
        this.listObjects = new ArrayList<Object>();
        this.listCells = new HashMap<String,Cell>();
        this.getStylesheets().add(this.getClass().getResource("plan.css").toExternalForm());
        /**
         * Boucle initialisant les objets cellules. /!\ Ces derniers ne sont sauvegarder que graphiquement : code à modifier lors de l'implémentation de l'algo
         */
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
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
                        ComboBox choix = (ComboBox) scene.lookup("#choixObjet");
                        if (choix.getSelectionModel().getSelectedItem()=="Wall") {
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
                        } else if (choix.getSelectionModel().getSelectedItem()=="Door") {
                                boolean fits= true;
                                ComboBox orientation = (ComboBox) scene.lookup("#orientation");
                                try {
                                    if (orientation.getSelectionModel().getSelectedItem()=="Vertical") {
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
                                    } else if (orientation.getSelectionModel().getSelectedItem()=="Horizontal") {
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
                        } else if (choix.getSelectionModel().getSelectedItem()=="Starting Point") {
                            if (isPointAIsSet()==false) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL) {
                                    addPointA(cellule.getX(),cellule.getY(),scene);
                                }    
                            }
                        } else if (choix.getSelectionModel().getSelectedItem()=="Ending Point") {
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
                grille.getChildren().add(cellule);
                listCells.put("#"+(cellule.getX())+"-"+(cellule.getY()), cellule);
            }
        }
        this.getChildren().add(grille);
    }
    
    public int getGridWidth() {
        return this.gridWidth;
    }
    
    
    public int getGridHeight() {
        return this.gridHeight;
    }

    public boolean isPointAIsSet() {
        return pointAIsSet;
    }

    public boolean isPointBIsSet() {
        return pointBIsSet;
    }

    public void setPointAIsSet(boolean pointAIsSet) {
        this.pointAIsSet = pointAIsSet;
    }

    public void setPointBIsSet(boolean pointBIsSet) {
        this.pointBIsSet = pointBIsSet;
    }
    
    public ArrayList<Object> getListObjects() {
        return listObjects;
    }

    public HashMap<String, Cell> getListCells() {
        return listCells;
    }
    
    public boolean deleteWall(int height, int width, int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Wall) {
                Objet.Wall w = (Objet.Wall) o;
                if (w.getHeight() == height && w.getWidth() == width && w.getPosX() == posX && w.getPosY() == posY ) {
                    listObjects.remove(listObjects.indexOf(w));
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deleteDoor(int height, int width, int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Door) {
                Objet.Door d = (Objet.Door) o;
                if (d.getHeight() == height && d.getWidth() == width && d.getPosX() == posX && d.getPosY() == posY) {
                    listObjects.remove(listObjects.indexOf(d));
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deletePointA(int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                if (p.getPosX() == posX && p.getPosY() == posY && p.getType() == PointType.POINTA) {
                    listObjects.remove(listObjects.indexOf(p));
                    setPointAIsSet(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deletePointB(int posX, int posY) {
        for (Object o : this.listObjects) {
            if (o instanceof Objet.Point) {
                Objet.Point p = (Objet.Point) o;
                if (p.getPosX() == posX && p.getPosY() == posY && p.getType() == PointType.POINTB) {
                    listObjects.remove(listObjects.indexOf(p));
                    setPointBIsSet(false);
                    return true;
                }
            }
        }
        return false;
    }
    
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
            }
        }
        return true;
    }
    
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
                cellule.setOccupied(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        return true;
    }
    
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
                cellule.setOccupied(true);
                setPointAIsSet(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        return true;
    }
    
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
                cellule.setOccupied(true);
                setPointBIsSet(true);
            } catch (NullPointerException e) {
                return false;
            }
        }
        return true;
    }
    
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

    @Override
    public String toString() {
        return "Grille{" + "sceneWidth=" + sceneWidth + ", sceneHeight=" + sceneHeight + ", listObjects=" + listObjects + ", pointAIsSet=" + pointAIsSet + ", pointBIsSet=" + pointBIsSet + '}';
    }
    
    
}

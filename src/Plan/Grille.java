/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import Objet.PointType;
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
    
    public LinkedList<Object> listObjects;
    
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
        this.listObjects = new LinkedList<Object>();
        
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
                                    for (int i = 0; i < height; i++) {
                                        for (int j = 0; j < width; j++) {
                                            if ((cellule.getY() + i)<= n && (cellule.getX() + j)<= m) {
                                                try {
                                                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX() + j)+"-"+(cellule.getY() + i));
                                                    rectangle.setFill(Color.THISTLE);
                                                    cellule.setOccupied(true);
                                                } catch (NullPointerException e) {

                                                }
                                            }
                                        }
                                    }
                                    Objet.Wall wall = new Objet.Wall(height,width,cellule.getX(),cellule.getY());
                                    listObjects.add(wall);
                                    objectList.getItems().add(wall.toString());
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
                                            Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                            rectangle.setFill(Color.ANTIQUEWHITE);
                                            Objet.Door door = new Objet.Door(1, 1, cellule.getX(), cellule.getY(), true);
                                            listObjects.add(door);
                                            objectList.getItems().add(door.toString());
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
                                            Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                            rectangle.setFill(Color.ANTIQUEWHITE);
                                            Objet.Door door = new Objet.Door(1, 1, cellule.getX(), cellule.getY(), true);
                                            listObjects.add(door);
                                            objectList.getItems().add(door.toString());
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
                                    rectangle.setFill(Color.BROWN);
                                    Objet.Point pointA = new Objet.Point(Objet.PointType.POINTA,cellule.getX(),cellule.getY());
                                    listObjects.add(pointA);
                                    objectList.getItems().add(pointA.toString());
                                    setPointAIsSet(true);
                                }    
                            }
                        } else if (choix.getSelectionModel().getSelectedItem()=="Ending Point") {
                            if (isPointBIsSet()==false) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(cellule.getX())+"-"+(cellule.getY()));
                                if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.BROWN) {
                                    rectangle.setFill(Color.TEAL);
                                    Objet.Point pointB = new Objet.Point(Objet.PointType.POINTB,cellule.getX(),cellule.getY());
                                    listObjects.add(pointB);
                                    objectList.getItems().add(pointB.toString());
                                    setPointBIsSet(true);
                                }
                            }
                        } else {
                            
                        }
                    }
                });
                grille.getChildren().add(cellule);
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
    
    public LinkedList<Object> getListObjects() {
        return listObjects;
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
}

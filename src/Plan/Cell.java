/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Classe définissant les cellules comme des objets Rectangle {@link Rectangle}.
 * @author Admin
 */
public class Cell extends StackPane {
    /**
     * Booléen définissant si une case est occupée ou non : pas utilisé pour le moment
     */
    private boolean occupied;
    
    /**
     * Entier définissant la première coordonnée d'une case
     */
    private int x;
    /**
     * Entier définissant la seconde coordonnée d'une case
     */
    private int y;
    
    /**
     * Integer defining the width of the grid (in cell)
     */
    private final int gridWidth;
    
    /**
     * Integer defining the height of the grid (in cell)
     */
    private final int gridHeight;
    
    /**
     * Constructeur d'une case prenant en paramètre sa taille, sa position dans la scène (en pixel) et sa position dans la grille (en entier).
     * @param width largeur de la cellule (en pixel)
     * @param height hauteur de la cellule (en pixel)
     * @param posX position X de la cellule dans la scène (en pixel)
     * @param posY position Y de la cellule dans la scène (en pixel)
     * @param x position x de la cellule dans la scène (en entier)
     * @param y position y de la cellule dans la scène (en entier)
     */
    public Cell(int width, int height, int posX, int posY, int x, int y, int gridHeight, int gridWidth) {
        this.occupied = false;
        //cree le rectangle de la cellule de taille width-height
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setId(x+"-"+y);
        rectangle.setFill(Color.ALICEBLUE);
        //rectangle.setStroke(Color.BLACK);
        
        //defini sa position avec x et y
        setTranslateX(posX);
        setTranslateY(posY);
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        getChildren().addAll(rectangle);
    }

    /**
     * Getter de la variable occupied
     * @return occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Setter de la variable occupied
     * @param occupied 
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    /**
     * Getter de la variable x
     * @return 
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Getter de la variable y
     * @return 
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter of the width variable
     * @return 
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Getter of the height variable
     * @return 
     */
    public int getGridHeight() {
        return gridHeight;
    }
    
    
    
    /**
     * Fonction <i> hover </i> pour les cases, les colorants lorsque le cruseur passe dessus et remplaçant le contenu d'une zone <i> Text </i> 
     * faisant apparaître les coordonnées de la dite case.
     * @param scene variable scene de la classe Plan {@link Plan}, utilisée pour retrouver la variable <i> Text </i> depuis son id
     */
     public void hoverHighlight(Scene scene) {
            coloring(scene, Color.LIGHTBLUE);
            Text texte = (Text) scene.lookup("#infoCase");
            texte.setText("<"+this.getX()+";"+this.getY()+">");
        }

     /**
      * Fonction désactivant le <i> hover </i> lorsque la souris quitte la case
      */
    public void hoverUnhighlight(Scene scene) {
        coloring(scene, Color.ALICEBLUE);
    }
    
    public void coloring(Scene scene, Color color) {
        /*for (Node node : this.getChildrenUnmodifiable()) {
            Rectangle rectangle = (Rectangle)node;
            rectangle.setFill(color);
        }*/
        ComboBox choix = (ComboBox) scene.lookup("#choixObjet");
        if (choix.getSelectionModel().getSelectedItem()=="Wall") {
            //We get the values entered for the dimensions of the wall
            TextField heightTxt = (TextField) scene.lookup("#height");
            TextField widthTxt = (TextField) scene.lookup("#width");
            //We, then, convert it to an integer and loop on all chosen concerned rectangles
            try {
                int height = Integer.parseInt(heightTxt.getText());
                int width = Integer.parseInt(widthTxt.getText());
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        
                        if ((this.getY() + i)< this.getGridHeight() && (this.getX() + j)< this.getGridWidth()) {
                            try {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX() + j)+"-"+(this.getY() + i));
                                if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE) {
                                  rectangle.setFill(color);
                                }  
                            } catch (NullPointerException e) {
                                
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }    
        } else if (choix.getSelectionModel().getSelectedItem()=="Door") {
            ComboBox orientation = (ComboBox) scene.lookup("#orientation");
            try {
                boolean fits = true;
                if (orientation.getSelectionModel().getSelectedItem()=="Vertical") {
                    if ((this.getY()-1)>0 && (this.getY()+1)<this.getGridHeight()-1) {
                        try {
                            for (int i = 0; i < 3; i++) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()-1+i));
                                if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE) {
                                    fits = false;
                                }  
                            }
                            } catch (NullPointerException e) {
                                
                            }
                    }
                } else {
                   if ((this.getX()-1)>0 && (this.getX()+1)<this.getGridWidth()-1) {
                        try {
                            for (int i = 0; i < 3; i++) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX()-1+i)+"-"+(this.getY()));
                                if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE) {
                                    fits = false;
                                }  
                            }
                        } catch (NullPointerException e) {
                                
                        }
                    } 
                }
                if (fits) {
                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()));
                    rectangle.setFill(color);
                }
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }    
        } else {
            //When no option is selected
        }
    }
}

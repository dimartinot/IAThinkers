/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import javafx.scene.Node;
import javafx.scene.Scene;
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
     * Constructeur d'une case prenant en paramètre sa taille, sa position dans la scène (en pixel) et sa position dans la grille (en entier).
     * @param width largeur de la cellule (en pixel)
     * @param height hauteur de la cellule (en pixel)
     * @param posX position X de la cellule dans la scène (en pixel)
     * @param posY position Y de la cellule dans la scène (en pixel)
     * @param x position x de la cellule dans la scène (en entier)
     * @param y position y de la cellule dans la scène (en entier)
     */
    public Cell(int width, int height, int posX, int posY, int x, int y) {
        occupied = false;
        //cree le rectangle de la cellule de taille width-height
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.ALICEBLUE);
        rectangle.setStroke(Color.BLACK);
        
        //defini sa position avec x et y
        setTranslateX(posX);
        setTranslateY(posY);
        this.x = x;
        this.y = y;
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
     * Fonction <i> hover </i> pour les cases, les colorants lorsque le cruseur passe dessus et remplaçant le contenu d'une zone <i> Text </i> 
     * faisant apparaître les coordonnées de la dite case.
     * @param scene variable scene de la classe Plan {@link Plan}, utilisée pour retrouver la variable <i> Text </i> depuis son id
     */
     public void hoverHighlight(Scene scene) {
            for (Node node : this.getChildrenUnmodifiable()) {
                Rectangle rectangle = (Rectangle)node;
                rectangle.setFill(Color.LIGHTBLUE);
            }
            Text texte = (Text) scene.lookup("#infoCase");
            texte.setText("<"+this.getX()+";"+this.getY()+">");
        }

     /**
      * Fonction désactivant le <i> hover </i> lorsque la souris quitte la case
      */
    public void hoverUnhighlight() {
        for (Node node : this.getChildrenUnmodifiable()) {
            Rectangle rectangle = (Rectangle)node;
            rectangle.setFill(Color.ALICEBLUE);
        }
    }
}

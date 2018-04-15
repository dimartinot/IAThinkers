/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Objet;

/**
 *
 * @author Admin
 */
public class Point {
    
    //There's two types of point : a starting point and an ending point.
    private PointType type;
    
    private int posX;
    private int posY;
    
    public Point(PointType Type, int posX, int posY) {
        this.type = Type;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    public PointType getType() {
        return type;
    }
    
    public String toString() {
        if (this.type == PointType.POINTA) {
            return "PointA("+getPosX()+", "+getPosY()+")";
        } else {
            return "PointB("+getPosX()+", "+getPosY()+")";
        }   
    }
}

/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Objet;

/**
 * Point Class defining every method needed for the representation of a point (starting or ending point) 
 * @author IAThinkers
 */
public class Point {
    
    //There's two types of point : a starting point (PointA) and an ending point (PointB).
    private PointType type;
    
    /**
     * Integers describing the position position
     */
    private int posX;
    private int posY;
    
    /**
     * Constructor of a Point
     * @param Type Enum variable describing the type of the point. All the possibilities are defined here : {@link Objet.PointType}
     * @param posX X position property of the Point
     * @param posY Y position property of the Point
     */
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

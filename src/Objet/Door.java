/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
/**
 * This package contains every object the user is able to put on the plan
 */
package Objet;

/**
 * Door Class defining every method needed for the representation of a door 
 * @author IAThinkers
 */
public class Door {
    
    /**
     * Variable defining door properties such as its size and position
     */
    private int height;
    private int width;
    private int posX;
    private int posY;
    private boolean isVertical;
    /**
     * Constructor of a Door object
     * @param height height property of the door
     * @param width height property of the door
     * @param posX X position property of the door
     * @param posY Y position property of the door
     * @param isVertical Boolean variable describing the orientation of the door
     */
    public Door(int height, int width, int posX, int posY, boolean isVertical) {
        this.height = height;
        this.width = width;
        this.posX = posX;
        this.posY = posY;
        this.isVertical = isVertical;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean getIsVertical() {
        return isVertical;
    }
    
    public String toString() {
        if (this.getIsVertical()) {
            return "Door("+this.getHeight()+", "+this.getWidth()+", "+this.getPosX()+", "+this.getPosY()+", vertical)";
        } else {
            return "Door("+this.getHeight()+", "+this.getWidth()+", "+this.getPosX()+", "+this.getPosY()+", horizontal)";
        }
    }
    
}

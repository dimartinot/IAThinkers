/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
/**
 * This package contains every object the user is able to put on the plan
 */
package Objet;

import static Menu.MainMenu.getLanguage;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private boolean isClosed;
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
        this.isClosed = false;
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
    
    public boolean isClosed() {
        return this.isClosed;
    }
    
    public void close() {
        this.isClosed = true;
    }
    
    public void open() {
        this.isClosed = false;
    }
    
    public String toString() {
        
        Locale l = getLanguage();
        ResourceBundle messages = ResourceBundle.getBundle("Objet/Objet",l);
        if (this.getIsVertical()) {
            return messages.getString("DOOR(")+this.getHeight()+", "+this.getWidth()+", "+this.getPosX()+", "+this.getPosY()+messages.getString(", VERTICAL)");
        } else {
            return messages.getString("DOOR(")+this.getHeight()+", "+this.getWidth()+", "+this.getPosX()+", "+this.getPosY()+messages.getString(", HORIZONTAL)");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Door other = (Door) obj;
        if (this.height != other.height) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.posX != other.posX) {
            return false;
        }
        if (this.posY != other.posY) {
            return false;
        }
        return true;
    }
    
    
    
}

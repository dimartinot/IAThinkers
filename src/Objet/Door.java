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
public class Door {
    
    private int height;
    private int width;
    private int posX;
    private int posY;
    private boolean isVertical;
    /**
     * Constructor of 
     * @param height
     * @param width
     * @param posX
     * @param posY 
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

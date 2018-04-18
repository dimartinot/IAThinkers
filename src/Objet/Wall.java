/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Objet;

/**
 * Wall Class defining every method needed for the representation of a wall 
 * @author IAThinkers
 */
public class Wall {
    
    /**
     * Variable defining wall properties such as its size and position
     */
    private int height;
    private int width;
    private int posX;
    private int posY;
    
    /**
     * Constructor of a Wall object.
     * @param height height property of the wall
     * @param width width property of the wall
     * @param posX X position property of the wall
     * @param posY Y position property of the wall
     */
    public Wall(int height, int width, int posX, int posY) {
        this.height = height;
        this.width = width;
        this.posX = posX;
        this.posY = posY;
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
    
    public String toString() {
        return "Wall("+this.getHeight()+", "+this.getWidth()+", "+this.getPosX()+", "+this.getPosY()+")";
    }

}

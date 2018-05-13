/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand;

/**
 * Defines an ending pixel : the objective searched by the A* algorithm in the {@link Freehand} scene.
 * @author IAThinkers
 */
public class EndingPixel {
     private int x;
    private int y;
    
    public EndingPixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}

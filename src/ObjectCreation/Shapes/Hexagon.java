/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package ObjectCreation.Shapes;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Admin
 */
public class Hexagon extends Polygon {
    
    
    double posX;
    
    double posY;
    
    public Hexagon() {
        super();
    }
    
    public Hexagon(double x, double y) {
        super();
        this.posX = x;
        this.posY = y;
    }
    
    public void setSizes(double x, double y) {
        ObservableList<Double> points = this.getPoints();
        double originX = this.getPosX();
        double originY = this.getPosY();
        points.clear();
        points.addAll(new Double[]{
            //The top left corner of a hexagon
            originX+(x)/(new Double(4)),originY,
            //The left corner of a hexagon
            originX, originY+(y/(new Double(2))),
            //The bottom left corner of a hexagon,
            originX+x/(new Double(4)), originY+y,
            //The bottom right corner of a hexagon
            originX+3*x/(new Double(4)), originY+y,
            //The right corner of a hexagon
            originX+x, originY+(y/(new Double(2))),
            //The top right corner of a hexagon
            originX+(3*x)/(new Double(4)),originY
        }
        );
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    
    
}

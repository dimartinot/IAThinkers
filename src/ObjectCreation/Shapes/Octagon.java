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
public class Octagon extends Polygon {
    
    
    double posX;
    
    double posY;
    
    public Octagon() {
        super();
    }
    
    public Octagon(double x, double y) {
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
            //The top left corner of an octagon
            originX+(x)/(new Double(3)),originY,
            //The first left corner of an octagon
            originX, originY+(y/(new Double(3))),
            //The second left corner of an octagon
            originX, originY+(2*y/(new Double(3))),
            //The bottom left corner of an octagon,
            originX+x/(new Double(3)), originY+y,
            //The bottom right corner of an octagon
            originX+2*x/(new Double(3)), originY+y,
            //The first right corner of an octagon
            originX+x, originY+(2*y/(new Double(3))),
            //The first right corner of an octagon
            originX+x, originY+(y/(new Double(3))),
            //The top right corner of an octagon
            originX+(2*x)/(new Double(3)),originY
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

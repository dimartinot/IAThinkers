/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand.ShapesHandler;

import ObjectCreation.Shapes.Hexagon;
import ObjectCreation.Shapes.Octagon;
import ObjectCreation.Shapes.Pentagon;
import ObjectCreation.Shapes.Triangle;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * This class implements a Scroll in order to detail the process of resizing an object (i.e, resizing all its components, also being called shapes)
 * @author IAThinkers
 */
public class ResizeObject implements EventHandler<ScrollEvent>{
    /**
     * The array of shapes making the object
     */
    ArrayList<Object> shapes;
    /**
     * The width not to exceed
     */
    double maxWidth;
    /**
     * The height not to exceed
     */
    double maxHeight;
    
    /**
     * The constructor of the Handler
     * @param l
     * @param width
     * @param height 
     */
    public ResizeObject(ArrayList<Object> l, double width, double height) {
        this.shapes = l;
        this.maxWidth = width;
        this.maxHeight = height;
    }
    
    /**
     * The handle function. It will use the <i> getDeltaY() </i> method of the event variable and divide it by 1600 to offer a nice balance of resizing while scrolling. 
     * It will, then change the size of all object from 100% to 100% + deltaY/1600.
     * Please note that if deltaY is negative, it will shrink the object. If the deltaY is positive, it will swell the object.
     * @param event 
     */
    @Override
    public void handle(ScrollEvent event) {
            double deltaY = event.getDeltaY()/1600.0;
            for (Object o : this.getShapes()) {
                    if (o instanceof Rectangle) {
                        double newHeight = ((Rectangle) o).getHeight()*(1+deltaY);
                        double newWidth = ((Rectangle) o).getWidth()*(1+deltaY);
                        if (newHeight < this.getMaxHeight() - ((Rectangle) o).getY() && newWidth < this.getMaxWidth() - ((Rectangle) o).getX()) {
                            ((Rectangle) o).setHeight(newHeight);
                            ((Rectangle) o).setWidth(newWidth);
                            ((Rectangle) o).setX(((Rectangle) o).getX()*(1+deltaY));
                            ((Rectangle) o).setY(((Rectangle) o).getY()*(1+deltaY));
                        } else {
                            break;
                        }
                    } else if (o instanceof Triangle) {
                        Triangle obj = ((Triangle) o);
                        obj.setSizes(obj.getSizeX()*(1+deltaY),obj.getSizeY()*(1+deltaY));
                        obj.move(obj.getPosX()*(1+deltaY), obj.getPosY()*(1+deltaY), this.getMaxWidth(), this.getMaxHeight());
                    } else if (o instanceof Pentagon) {
                        Pentagon obj = ((Pentagon) o);
                        obj.setSizes(obj.getSizeX()*(1+deltaY),obj.getSizeY()*(1+deltaY));
                        obj.move(obj.getPosX()*(1+deltaY), obj.getPosY()*(1+deltaY), this.getMaxWidth(), this.getMaxHeight());
                    } else if (o instanceof Hexagon) {
                        Hexagon obj = ((Hexagon) o);
                        obj.setSizes(obj.getSizeX()*(1+deltaY),obj.getSizeY()*(1+deltaY));
                        obj.move(obj.getPosX()*(1+deltaY), obj.getPosY()*(1+deltaY), this.getMaxWidth(), this.getMaxHeight());
                    } else if (o instanceof Octagon) {
                        Octagon obj = ((Octagon) o);
                        obj.setSizes(obj.getSizeX()*(1+deltaY),obj.getSizeY()*(1+deltaY));
                        obj.move(obj.getPosX()*(1+deltaY), obj.getPosY()*(1+deltaY), this.getMaxWidth(), this.getMaxHeight());
                    } else if (o instanceof Circle) {
                        double newRadius = ((Circle) o).getRadius()*(1+deltaY);
                        double newCenterX = ((Circle) o).getCenterX()*(1+deltaY);
                        double newCenterY = ((Circle) o).getCenterY()*(1+deltaY);
                        if (newCenterX+newRadius < this.getMaxHeight() && newCenterY+newRadius < this.getMaxWidth()) {
                            ((Circle) o).setRadius(newRadius);
                            ((Circle) o).setCenterX(((Circle) o).getCenterX()*(1+deltaY));
                            ((Circle) o).setCenterY(((Circle) o).getCenterY()*(1+deltaY));
                        } else {
                            break;
                        }
                    }
                }
        }

    public ArrayList<Object> getShapes() {
        return shapes;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public double getMaxHeight() {
        return maxHeight;
    }
    
    
    
    
}
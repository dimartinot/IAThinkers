/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand.ShapesHandler;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


/**
 * This class implements a MouseEvent in order to detail the process of moving an object (i.e, moving all its components, also being called shapes)
 * @author IAThinkers
 */
public class MoveObject implements EventHandler<MouseEvent>{
    /**
     * ArrayList of object used to store all the shapes of the object to move using this EventHandler
     */
    ArrayList<Object> shapes;
    
    public MoveObject(ArrayList<Object> l) {
        this.shapes = l;
    }
    
    /**
     * The handle function : it will set the translateX and translateY value of all the shapes
     * @param event 
     */
    @Override
    public void handle(MouseEvent event) {
        for (Object o : this.getShapes()) {
            if (o instanceof Rectangle) {
                ((Rectangle) o).setTranslateX(event.getSceneX());
                ((Rectangle) o).setTranslateY(event.getSceneY());
            } else if (o instanceof Polygon) {
                ((Polygon) o).setTranslateX(event.getSceneX());
                ((Polygon) o).setTranslateY(event.getSceneY());
            } else if (o instanceof Circle) {
                ((Circle) o).setTranslateX(event.getSceneX());
               ((Circle) o).setTranslateY(event.getSceneY());
            }
        }
    }

    public ArrayList<Object> getShapes() {
        return shapes;
    }
    
    
    
}


package Plan;

import static Menu.MainMenu.getLanguage;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class defining the cell object. Afterward, they will compose the grid.
 * @author IAThinkers
 */
public class Cell extends StackPane {
    /**
     * Boolean defining if a cell is, indeed, occupied
     */
    private boolean occupied;
    
    /**
     * Integer defining the first coordinate of a cell
     */
    private int x;
    /**
     * Integer defining the second coordinate of a cell
     */
    private int y;
    
    /**
     * Integer defining the width of the grid (in cell)
     */
    private final int gridWidth;
    
    /**
     * Integer defining the height of the grid (in cell)
     */
    private final int gridHeight;
    
    private ResourceBundle messages;
        
    /**
     * Constructor of a cell
     * @param width cell width, in pixel
     * @param height cell height, in pixel
     * @param posX X position of the cell, in pixel
     * @param posY Y position of the cell, in pixel
     * @param x X position of the cell, in integer (i.e. the column in which the cell is put)
     * @param y Y position of the cell, in integer (i.e the row in which the cell is put)
     * @param gridHeight defines the height of the grid (in cell)
     * @param gridWidth defines the width of the grid (in cell)
     */
    public Cell(int width, int height, int posX, int posY, int x, int y, int gridHeight, int gridWidth) {
        
        Locale l = getLanguage();
        messages = ResourceBundle.getBundle("Plan/Plan",l);
        this.occupied = false;
        //Initialises the rectangle variable for the UI perspective
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setId(x+"-"+y);
        rectangle.setFill(Color.ALICEBLUE);
        rectangle.setStroke(Color.LIGHTGRAY);
        
        //Defines its positioning using X and Y coordinates
        setTranslateX(posX);
        setTranslateY(posY);
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        getChildren().addAll(rectangle);
    }

    /**
     * Getter of the occupied variable
     * @return occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Setter of the occupied variable
     * @param occupied 
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    /**
     * Getter of the x variable
     * @return 
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Getter of the y variable
     * @return 
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter of the width variable
     * @return 
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Getter of the height variable
     * @return 
     */
    public int getGridHeight() {
        return gridHeight;
    }
    
    
    
    /**
     * <i> Hover </i> method for the cells. Its main purposes are to change the filling color of the rectangle object of the hovered cell 
     * + it displays its position in a <i>Text</i> object, on the VBbox at the right side.
     * @param scene Scene variable of the {@link Plan} class, used to lookup for the the <i> Text </i> object, using its id.
     */
     public void hoverHighlight(Scene scene) {
            try {
                ComboBox choix = (ComboBox) scene.lookup("#choixobjet");
                coloring(scene, Color.LIGHTBLUE,choix.getSelectionModel().getSelectedItem().toString());
                Text texte = (Text) scene.lookup("#infocase");
                texte.setText("<"+this.getX()+";"+this.getY()+">");
            } catch (NullPointerException e) {
                coloring(scene, Color.LIGHTBLUE,"");
                Text texte = (Text) scene.lookup("#infocase");
                texte.setText("<"+this.getX()+";"+this.getY()+">");            
            }
        }

     /**
      * Method deactivating the <i> hover </i> effect when the mouse leaves the cell.
      * @param scene Scene variable used by the {@link coloring} method.
      */
    public void hoverUnhighlight(Scene scene) {
        try {
            ComboBox choix = (ComboBox) scene.lookup("#choixobjet");
            coloring(scene, Color.ALICEBLUE, choix.getSelectionModel().getSelectedItem().toString());
        } catch (NullPointerException e) {
            coloring(scene, Color.ALICEBLUE, "");
        }
    }
    
    /**
     * Method used to change the color of the hovered cell. It is important to notice that the coloring depends on many things : it will happen only if the object the user 
     * is intending to put down fits. If it does not, then there is no need to change the color the cell. Furthermore, in the case of a wall,
     * the coloration will actually take the shape of the expected wall.
     * @param scene Scene variable useful to check which object is intended to be put
     * @param color Color variable : it represents the new color of the cell.
     */
    public void coloring(Scene scene, Color color, String selected) {
        if (selected==messages.getString("WALL")) {
            //We get the values entered for the dimensions of the wall
            TextField heightTxt = (TextField) scene.lookup("#height");
            TextField widthTxt = (TextField) scene.lookup("#width");
            //We, then, convert it to an integer and loop on all concerned rectangles
            try {
                
                int height = Integer.parseInt(heightTxt.getText());
                int width = Integer.parseInt(widthTxt.getText());
                //We check if the wall will fit entirely inside the drawing surface
                if ((this.getY() + height) < this.getGridHeight() && (this.getX() + width) < this.getGridWidth()) {
                    for (int i = 0; i < height; i++) {
                        Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY() + i));
                        if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                            rectangle.setFill(color);
                        }
                        rectangle = (Rectangle) scene.lookup("#"+(this.getX() + width - 1)+"-"+(this.getY() + i));
                        if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                            rectangle.setFill(color);
                        }                    }
                    for (int j = 0; j < width; j++) {
                            Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX() + j)+"-"+(this.getY()));
                            if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                                rectangle.setFill(color);
                            }
                        rectangle = (Rectangle) scene.lookup("#"+(this.getX() + j)+"-"+(this.getY() + height - 1));
                        if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                            rectangle.setFill(color);
                        }                        
                    }
                }
                /*
                if ((this.getY() + height) < this.getGridHeight() && (this.getX() + width) < this.getGridWidth()) {
                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()));
                    if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                        rectangle.setFill(color);
                    }
                    rectangle = (Rectangle) scene.lookup("#"+(this.getX()+width)+"-"+(this.getY()+height));
                    if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL && rectangle.getFill() != Color.BROWN) {
                        rectangle.setFill(color);
                    }
                } */   
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }    
        } else if (selected==messages.getString("DOOR")) {
            //We get then orientation of the wall
            ComboBox orientation = (ComboBox) scene.lookup("#orientation");
            try {
                boolean fits = true;
                //Based on the orientation, we will seek for any blocking wall that would compromise laying the door
                if (orientation.getSelectionModel().getSelectedItem() == messages.getString("VERTICAL")) {
                    if ((this.getY()-1)>0 && (this.getY()+1)<this.getGridHeight()-1) {
                        try {
                            for (int i = 0; i < 3; i++) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()-1+i));
                                if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                                    fits = false;
                                }  
                            }
                            } catch (NullPointerException e) {
                                
                            }
                    }
                } else if (orientation.getSelectionModel().getSelectedItem() == messages.getString("HORIZONTAL")) {
                   if ((this.getX()-1)>0 && (this.getX()+1)<this.getGridWidth()-1) {
                        try {
                            for (int i = 0; i < 3; i++) {
                                Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()));
                                if (rectangle.getFill() == Color.THISTLE || rectangle.getFill() == Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                                    fits = false;
                                }  
                            }
                        } catch (NullPointerException e) {
                                
                        }
                    } 
                } else {
                    fits = false;
                }
                if (fits) {
                    Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()));
                    rectangle.setFill(color);
                }
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }    
        } else if (selected == messages.getString("STARTING POINT") || selected ==  messages.getString("ENDING POINT")) {
            Rectangle rectangle = (Rectangle) scene.lookup("#"+(this.getX())+"-"+(this.getY()));
            if (rectangle.getFill() != Color.THISTLE && rectangle.getFill() != Color.ANTIQUEWHITE && rectangle.getFill() != Color.TEAL &&rectangle.getFill() != Color.BROWN) {
                rectangle.setFill(color);
            } 
        } else  {
            //When no option is selected
        }
    }
    
    public String toString() {
        return "("+this.getX()+", "+this.getY()+")";
    }
    
}

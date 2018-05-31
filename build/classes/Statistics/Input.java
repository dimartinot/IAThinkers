/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Statistics;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class implements the input of the data array of the {@link Statistics} scene.
 * It is composed of all the rows needed for the <i> TableView </i>, all represented as <i> SimpleIntegerProperty </i>
 * @author IAThinkers
 */
public class Input {
    /**
     * The id of the input, got as saved in the database
     */
    private SimpleIntegerProperty id;
    /**
     * The time it took for the A* algorithm to find the path
     */
    private SimpleIntegerProperty time;
    /**
     * The length of the path
     */
    private SimpleIntegerProperty lengthOfPath;
    /**
     * The number of (1,1) blocks contained in the houseplan
     */
    private SimpleIntegerProperty numberOfBlock;
    /**
     * The number of available cells of the houseplan, starting from the starting {@link Objet.Point} to the ending {@link Objet.Point}
     */
    private SimpleIntegerProperty numberOfAvailableCell;
    
    /**
     * The input constructor, taking all the variables needed as ints and transforming them in <i> SimpleIntegerProperty </i>
     * @param id id variable
     * @param time time variable
     * @param lengthOfPath length of the path variable
     * @param numberOfBlock number of blocks variable
     * @param numberOfAvailableCell number of available cells variables
     */
    public Input(int id, int time, int lengthOfPath, int numberOfBlock, int numberOfAvailableCell) {
        this.id = new SimpleIntegerProperty(id);
        this.time = new SimpleIntegerProperty(time);
        this.lengthOfPath = new SimpleIntegerProperty(lengthOfPath);
        this.numberOfBlock = new SimpleIntegerProperty(numberOfBlock);
        this.numberOfAvailableCell = new SimpleIntegerProperty(numberOfAvailableCell);
    }

    public Integer getId() {
        return new Integer(id.get());
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Integer getTime() {
        return new Integer(time.get());
    }

    public void setTime(Integer time) {
        this.time.set(time);
    }

    public Integer getLengthOfPath() {
        return new Integer(lengthOfPath.get());
    }

    public void setLengthOfPath(Integer lengthOfPath) {
        this.lengthOfPath.set(lengthOfPath);
    }

    public Integer getNumberOfBlock() {
        return new Integer(numberOfBlock.get());
    }

    public void setNumberOfBlock(Integer numberOfBlock) {
        this.numberOfBlock.set(numberOfBlock);
    }

    public Integer getNumberOfAvailableCell() {
        return new Integer(numberOfAvailableCell.get());
    }

    public void setNumberOfAvailableCell(Integer numberOfAvailableCell) {
        this.numberOfAvailableCell.set(numberOfAvailableCell);
    }
    
    
}

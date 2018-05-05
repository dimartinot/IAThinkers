/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Statistics;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Admin
 */
public class Input {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty time;
    private SimpleIntegerProperty lengthOfPath;
    private SimpleIntegerProperty numberOfBlock;
    private SimpleIntegerProperty numberOfAvailableCell;
    
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

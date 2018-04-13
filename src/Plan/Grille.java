/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plan;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
/**
 * Classe de la grille. Cette classe définit une grille comme un <i> Group </i> de cellules {@link Cell}.
 * @author Admin
 */
public class Grille extends Parent{
    
    /**
     * Attributs définissant la taille (en pixels) de la grille
     */
    private int sceneWidth = 600;
    private int sceneHeight = 600;

    /**
     * Attributs définissant le nombre de cellules (modifiable dans des versions futures)
     */
    private int n = 100;
    private int m = 100;
    
    /**
     * Variables de boucle passées globales pour pouvoir être utilisées dans le handler des cellules. 
     */
    public int i;
    public int j;

    /**
     * Tailles en largeur et longueur de chacunes des cases
     */
    int gridWidth = sceneWidth / n;
    int gridHeight = sceneHeight / m;
    /**
     * Constructeur d'une grille : va initialiser toutes les cellules sous jacentes à cette dernière.
     * @param scene 
     */
    public Grille(Scene scene) {
        Group grille = new Group();
        this.getStylesheets().add(this.getClass().getResource("plan.css").toExternalForm());
        /**
         * Boucle initialisant les objets cellules. /!\ Ces derniers ne sont sauvegarder que graphiquement : code à modifier lors de l'implémentation de l'algo
         */
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                Cell cellule = new Cell(gridWidth, gridHeight, i * gridWidth, j * gridHeight,i,j);
                cellule.hoverProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if( newValue) {
                            (cellule).hoverHighlight(scene);
                        } else {
                            (cellule).hoverUnhighlight();
                        }                    
                    }
                });
                grille.getChildren().add(cellule);
            }
        }
        this.getChildren().add(grille);
    }
    
}

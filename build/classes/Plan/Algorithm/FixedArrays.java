/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Plan.Algorithm;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class FixedArrays<X,Y> {
    
    public FixedArrays(){
        
    }
    
    public Y getValue(HashMap<X,Y> g, X current) {
        Set<X> set = g.keySet();
        for (X n : set) {
            if (n.equals(current)) {
                return g.get(n);
            }
        }
        return null;    
    }
    
    public void replaceValue(HashMap<X,Y> g, X current, Y value) {
        Set<X> set = g.keySet();
        for (X n : set) {
            if (n.equals(current)) {
                g.replace(n,value);
            }
        }
    }
    
    
}

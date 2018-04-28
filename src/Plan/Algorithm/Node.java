/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Plan.Algorithm;

/**
 *
 * @author Admin
 */
public class Node implements Comparable {
    private int x;
    private int y;
    private int cost;
    private int heuristique;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.cost = 0;
        this.heuristique = 0;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(int heuristique) {
        this.heuristique = heuristique;
    }
    
    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            Node n = (Node) o;
            if (this.heuristique < n.heuristique) {
                return 1;
            } else if (this.heuristique == n.heuristique) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }    
    }
    
    public String toString() {
        return "("+this.getX()+", "+this.getY()+")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node n = (Node) o;
            if (this.getX() == n.getX() && this.getY() == n.getY()) {
                return true;
            } else return false;
        } else {
            return false;
        }
    }
}

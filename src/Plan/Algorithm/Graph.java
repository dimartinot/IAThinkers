/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Plan.Algorithm;

import Objet.Point;
import Plan.Cell;
import Plan.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class Graph {
    
    private ArrayList<Node> listOfNodes;
    private Objet.Point start;
    private Objet.Point end;
    
    public Graph(Grid g) {
        this.start = g.getStartingPoint();
        this.end = g.getEndingPoint();
        this.listOfNodes = new ArrayList<Node>();
        HashMap<String,Cell> listCells = g.getListCells();
        
        LinkedList<Cell> toVisit = new LinkedList<Cell>();
        if (this.start != null && this.end != null) {
            Cell startingCell = listCells.get("#"+this.start.getPosX()+"-"+this.start.getPosY());
            toVisit.add(startingCell);
            this.listOfNodes = initNodeList(startingCell, listCells, listOfNodes);//init(startingCell, toVisit, this.g, listCells);
        }
    }
    /**
     * This recursive function will search for every accessible cell starting from the PointA object.
     * It will look recursively at the four neighbours of the given current cell and will reach them if they are reachable. Then it starts this process again with its neighbours.
     * @param current Cell variable giving the current cell the algorithm is studying.
     * @param listCells list all existing cells with their properties. The important thing to know is if the cell is occupied or not.
     * @param res the accumulating variable : it consists in an array of nodes.
     * @return 
     */
    public ArrayList<Node> initNodeList(Cell current, HashMap<String,Cell> listCells, ArrayList<Node> res) {
        //ArrayList<Node> res = new ArrayList<Node>();
        if (current == null) {
            //return res;
        } else {
            
            res.add(0,new Node(current.getX(),current.getY()));
            Node top = new Node(current.getX(),current.getY()-1);
            Node bottom = new Node(current.getX(),current.getY()+1);
            Node left = new Node(current.getX()-1,current.getY());
            Node right = new Node(current.getX()+1,current.getY());
            //
            Cell cellule = listCells.get("#"+(top.getX())+"-"+(top.getY()));
            if (cellule != null && !cellule.isOccupied() && !arrayContainsNode(res,top)) {
                initNodeList(cellule,listCells,res);
            }
            cellule = listCells.get("#"+(bottom.getX())+"-"+(bottom.getY()));
            if (cellule != null && !cellule.isOccupied() && !arrayContainsNode(res,bottom)) {
                initNodeList(cellule,listCells,res);
            }
            cellule = listCells.get("#"+(left.getX())+"-"+(left.getY()));
            if (cellule != null && !cellule.isOccupied() && !arrayContainsNode(res,left)) {
                initNodeList(cellule,listCells,res);
            }
            cellule = listCells.get("#"+(right.getX())+"-"+(right.getY()));
            if (cellule != null && !cellule.isOccupied() && !arrayContainsNode(res,right)) {
                initNodeList(cellule,listCells,res);
            }
        }
        return res;
    }
    
    public ArrayList<Node> getListOfNodes() {
        return this.listOfNodes;
    }
    
    public boolean arrayContainsNode(ArrayList<Node> nTab, Node n) {
        for (Node tmp : nTab) {
            if (n.equals(tmp)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This function will list all existing neighbours of a given Node
     * @param l the list of all the Nodes
     * @param current the Node we are searching the neighbours of
     * @return 
     */
    public ArrayList<Node> getNeighbours(ArrayList<Node> l, Node current) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        Node top = new Node(current.getX()-1,current.getY());
        Node bottom = new Node(current.getX()+1,current.getY());
        Node left = new Node(current.getX(),current.getY()-1);
        Node right = new Node(current.getX(),current.getY()+1);
        for (Node n : l) {
            if (n.equals(top) || n.equals(bottom) || n.equals(left) || n.equals(right)) {
                neighbours.add(n);
                if (neighbours.size() == 4) {
                    return neighbours;
                }
            }
        }
        return neighbours;
    }
    
    public String toString() {
        ArrayList<Node> set = this.getListOfNodes();
        String line = "";
        for (Node n : set) {
            line += n.toString()+"\n";
        }
        return line;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }    
}

/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Plan.Algorithm;

import Plan.Cell;
import Plan.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Admin
 */
public class AStar {
    
    private ArrayList<Node> solution;
    
    /**
     * The heuristic estimation method, used a basic R² distance function sqrt((x1-x2)²+(y1-y2)²)
     * @param n the given node to study
     * @param objective the objective node
     * @return the estimation of the distance between those two nodes
     */
    public double heuristicEstimation(Node n, Node objective) {
        return Math.sqrt( Math.pow(n.getX() - objective.getX(), 2) + Math.pow(n.getY() - objective.getY(), 2));
    }
    
    
    /**
     * A* algorithm implementation : I use the {@link Plan.Algorithm.Graph} variable as a data set.
     * @param startingNode The starting node
     * @param endingNode The ending node
     * @param g The graph variable, useful to get the list of all reachable nodes
     */
    public AStar(Node startingNode, Node endingNode, Graph g) {
        //We intiate all the useful set
        
        //It will contain all the Nodes we have already given an evaluation of the cost
        LinkedList<Node> closedSet = new LinkedList<Node>();
        
        //It will contain all know Nodes that are not evaluated yet.
        LinkedList<Node> openSet = new LinkedList<Node>();
        openSet.add(startingNode);
        
        //For a give Key node, it contains its neighbours of the lowest cost
        HashMap<Node,Node> efficientPredecessor = new HashMap<Node,Node>();
        
        //For a give Key node, it contains the score of getting to that Node from the start
        HashMap<Node,Double> nodeScore = new HashMap<Node,Double>();
        
        //These two variables are used to use the generic functions that fixes the bug I encountered dealing with Map using Nodes as Keys (equals method not working??)
        FixedArrays<Node,Node> NodeNode = new FixedArrays<Node,Node>();
        FixedArrays<Node,Double> NodeDouble = new FixedArrays<Node,Double>();
                
        //Set to infty all the scores except for the starting point
        for (Node n : g.getListOfNodes()) {
            nodeScore.put(n, Double.MAX_VALUE);
        }
        
        NodeDouble.replaceValue(nodeScore,startingNode, Double.valueOf(0));
        
        //Set to infty all the scores except for the starting point
        HashMap<Node,Double> nodeTotalScore = new HashMap<Node,Double>();    
        for (Node n : g.getListOfNodes()) {
            nodeTotalScore.put(n, Double.MAX_VALUE);
        }
        NodeDouble.replaceValue(nodeTotalScore,startingNode, heuristicEstimation(startingNode, endingNode));
        
        Double theoricalNodeScore;
        
        //While there is still a Node to evaluate, the while is not over
        while (!openSet.isEmpty()) {
            //We, firstly, will get the unevaluated node with the lowest score inf the nodeTotalScore Map
            Node current = null;
            Double minScore = Double.MAX_VALUE;
            for (Node n : openSet) {
               if (current == null) {
                   current = n;
               } else if (NodeDouble.getValue(nodeTotalScore,n) < minScore) {
                   minScore = NodeDouble.getValue(nodeTotalScore,n);
                   current = n;
               }
           }
           
           if (current.equals(endingNode)) {
               this.solution = solved(efficientPredecessor, endingNode, new ArrayList<Node>());
               break;
           }
           //After that, we pass it into the closed set as it has been treated
           openSet.remove(current);
           closedSet.add(current);
           //Then, we look at all its neighbours to seek for a potential candidate
           for (Node n : g.getNeighbours(g.getListOfNodes(), current)) {
               if (!closedSet.contains(n)) {
                   if (!openSet.contains(n)) {
                       openSet.add(n);
                   }
                   //We calculate the cost of getting to this point from our current position
                   theoricalNodeScore = NodeDouble.getValue(nodeScore, current) + Double.valueOf(1);
                   if (theoricalNodeScore.compareTo( NodeDouble.getValue(nodeScore, n) ) == -1) {
                       //If the value we calculated was lower than the precedent one, we update our data on the n Node
                       if (efficientPredecessor.containsKey(n) == false) {
                           efficientPredecessor.put(n,current);
                       } else {
                           NodeNode.replaceValue(efficientPredecessor,n,current);
                       }
                       NodeDouble.replaceValue(nodeScore, n, theoricalNodeScore);
                       NodeDouble.replaceValue(nodeTotalScore, n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                   }
                   
               }
           }
           
        }
    }
    
    /**
     * This is a second a version of the AStar where there is no list of all the accessible cells. Instead, we check the available neighbours dynamically. 
     * This method has proven itself to be more effective on low-range problem but whenever the number of cells tend to rise, its computation time follows.
     * This is why it is not the Astar used but we still kept it implemented
     * @param startingNode The starting node
     * @param endingNode The ending node
     * @param g The grid object, used to dynamically determine the neighbours of the cells we check
     */
    public AStar(Node startingNode, Node endingNode, Grid g) {
        //We intiate all the useful set
        
        //It will contain all the Nodes we have already given an evaluation of the cost
        LinkedList<Node> closedSet = new LinkedList<Node>();
        
        //It will contain all know Nodes that are not evaluated yet.
        LinkedList<Node> openSet = new LinkedList<Node>();
        openSet.add(startingNode);
        
        //For a give Key node, it contains its neighbours of the lowest cost
        HashMap<Node,Node> efficientPredecessor = new HashMap<Node,Node>();
        
        //For a give Key node, it contains the score of getting to that Node from the start
        HashMap<Node,Double> nodeScore = new HashMap<Node,Double>();
        
        //These two variables are used to use the generic functions that fixes the bug I encountered dealing with Map using Nodes as Keys (equals method not working??)
        FixedArrays<Node,Node> NodeNode = new FixedArrays<Node,Node>();
        FixedArrays<Node,Double> NodeDouble = new FixedArrays<Node,Double>();
         
        
        nodeScore.put(startingNode, Double.valueOf(0));
        
        //Set to infty all the scores except for the starting point
        HashMap<Node,Double> nodeTotalScore = new HashMap<Node,Double>();    
        nodeTotalScore.put(startingNode,heuristicEstimation(startingNode, endingNode));
        
        Double theoricalNodeScore;
        
        //While there is still a Node to evaluate, the while is not over
        while (!openSet.isEmpty()) {
            //We, firstly, will get the unevaluated node with the lowest score inf the nodeTotalScore Map
            Node current = null;
            Double minScore = Double.MAX_VALUE;
            for (Node n : openSet) {
               if (current == null) {
                   current = n;
               } else if (NodeDouble.getValue(nodeTotalScore,n) < minScore) {
                   minScore = NodeDouble.getValue(nodeTotalScore,n);
                   current = n;
               }
           }
           
           if (current.equals(endingNode)) {
               this.solution = solved(efficientPredecessor, endingNode, new ArrayList<Node>());
               break;
           }
           //After that, we pass it into the closed set as it has been treated
           openSet.remove(current);
           closedSet.add(current);
           //Then, we look at all its neighbours to seek for a potential candidate
           for (Node n : getNeighboursFromGrid(g.getListCells(), current)) {
               if (!closedSet.contains(n)) {
                    if (!openSet.contains(n)) {
                        openSet.add(n);
                    }
                    if (nodeScore.containsKey(n)) {
                        //We calculate the cost of getting to this point from our current position
                        theoricalNodeScore = NodeDouble.getValue(nodeScore, current) + Double.valueOf(1);
                        if (theoricalNodeScore.compareTo( NodeDouble.getValue(nodeScore, n) ) == -1) {
                            //If the value we calculated was lower than the precedent one, we update our data on the n Node
                            if (efficientPredecessor.containsKey(n) == false) {
                                efficientPredecessor.put(n,current);
                            } else {
                                NodeNode.replaceValue(efficientPredecessor,n,current);
                            }
                            NodeDouble.replaceValue(nodeScore, n, theoricalNodeScore);
                            NodeDouble.replaceValue(nodeTotalScore, n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                        }
                    } else {
                        if (efficientPredecessor.containsKey(n) == false) {
                                efficientPredecessor.put(n,current);
                            } else {
                                NodeNode.replaceValue(efficientPredecessor,n,current);
                            }
                        theoricalNodeScore = NodeDouble.getValue(nodeScore, current) + Double.valueOf(1);
                        nodeScore.put(n, theoricalNodeScore);
                        nodeTotalScore.put(n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                    }
               }
           }
           
        }
    }
    
    /**
     * This method will reconstruct the path from the start to the arrival with the help of the efficientPredecessor variable. It uses a recursive logic, linking keys to values to keys to values...
     * @param efficientPredecessor The map of Nodes, representing, for the key, its cheapest neighbour as a value
     * @param currentNode The node we are studying 
     * @param path The path we ara reconstructing, passed as a variable
     * @return The path from the start to the ending PointB
     */
    public static ArrayList<Node> solved(HashMap<Node,Node> efficientPredecessor, Node currentNode, ArrayList<Node> path) {
        if (currentNode == null) {
            return path;
        } else {
            path.add(currentNode);
            return solved(efficientPredecessor, new FixedArrays<Node,Node>().getValue(efficientPredecessor,currentNode), path);
        }
    }
    
    public ArrayList<Node> getSolution() {
        return this.solution;
    }
    
    /**
     * This method check if a given cell has neighbours with the help of the HashMap of all cells coming from the {@link Grid} object.
     * @param listCells HashMap of all cells where the key is "#x-y# with x as the {@link Cell} X property and the y the {@link Cell} Y property
     * @param current The current node we are looking the neighbours at
     * @return An ArrayList of all the available neighbours of the given node, everything set as a Node
     */
    public ArrayList<Node> getNeighboursFromGrid(HashMap<String,Cell> listCells, Node current) {
        ArrayList<Node> res = new ArrayList<Node>();
        //top
        Cell cellule = listCells.get("#"+(current.getX()-1)+"-"+(current.getY()));
        if (cellule != null && !cellule.isOccupied()) {
            res.add(new Node(current.getX()-1,current.getY()));
        }
        //bottom
        cellule = listCells.get("#"+(current.getX()+1)+"-"+(current.getY()));
        if (cellule != null && !cellule.isOccupied()) {
            res.add(new Node(current.getX()+1,current.getY()));
        }
        //left
        cellule = listCells.get("#"+(current.getX())+"-"+(current.getY()-1));
        if (cellule != null && !cellule.isOccupied()) {
            res.add(new Node(current.getX(),current.getY()-1));
        }
        //right
        cellule = listCells.get("#"+(current.getX())+"-"+(current.getY()+1));
        if (cellule != null && !cellule.isOccupied()) {
            res.add(new Node(current.getX(),current.getY()+1));
        }
        return res;
    }
}

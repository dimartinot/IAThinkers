/*
 * Groupe IAThinkers - Di Martino Thomas, Lambin J�r�my, Combadi�re Kamyar, Dupuch C�dric
 * Nous sommes 4 �tudiants en 1�re ann�e de cycle ing�nieur sp�cialit� informatique � l'EISTI.
 * Ce projet est r�alis� par nous-m�me de A � Z.
 */
package Freehand.Algorithm;

import Plan.Algorithm.AStar;
import static Plan.Algorithm.AStar.solved;
import Plan.Algorithm.FixedArrays;
import Plan.Algorithm.Node;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Admin
 */
public class AStarFreehand  {
    
    private ArrayList<Node> solution;
    
    private Path drawnSolution;
    
    private LinkedList<Node> closedSet;
    
    private LinkedList<Node> openSet;
    
    private HashMap<Node,Node> efficientPredecessor;
    
    private HashMap<Node,Double> nodeScore;
    
    private HashMap<Node,Double> nodeTotalScore;
    
    private Node current;
    
    public double heuristicEstimation(Node n, Node objective) {
        return Math.abs(n.getX() - objective.getX()) + Math.abs(n.getY() - objective.getY());
    }

    public AStarFreehand() {
        this.solution = new ArrayList<Node>();
        this.drawnSolution = new Path();
        this.closedSet = new LinkedList<Node>();
        this.openSet = new LinkedList<Node>();
        this.efficientPredecessor = new HashMap<Node,Node>();
        this.nodeScore = new HashMap<Node,Double>();
        this.nodeTotalScore = new HashMap<Node,Double>();
    }
    
    /**
     * A* algorithm implementation : I use the {@link Plan.Algorithm.Graph} variable as a data set.
     * @param startingNode The starting node
     * @param endingNode The ending node
     * @param g The WritableImage variable, useful to get the list of the neighbours of a given node
     * @param precedent
     */
    public AStarFreehand(AStarFreehand precedent, Node startingNode, Node endingNode, WritableImage g) {

        //We initiate all the useful sets
        
        //It will contain all the Nodes we have already given an evaluation of the cost
        closedSet = new LinkedList<>();
        //closedSet.addAll(precedent.getClosedSet());
        
        //It will contain all know Nodes that are not evaluated yet.
        openSet = new LinkedList<>();
        //openSet.addAll(precedent.getOpenSet());
        //if (!openSet.contains(startingNode)) {
            openSet.add(startingNode);
        //}
        //For a given Key node, it contains its neighbours of the lowest cost
        efficientPredecessor = new HashMap<>();
        //efficientPredecessor.putAll(precedent.getEfficientPredecessor());
        
        //For a given Key node, it contains the score of getting to that Node from the start
        nodeScore = new HashMap<>();
        //nodeScore.putAll(precedent.getNodeScore());
        //if (!nodeScore.containsKey(startingNode)) {
            nodeScore.put(startingNode,new Double(0));
        //}
        //These two variables are used to use the generic functions that fixes the bug I encountered dealing with Map using Nodes as Keys (equals method not working??)
        FixedArrays<Node,Node> NodeNode = new FixedArrays<>();
        FixedArrays<Node,Double> NodeDouble = new FixedArrays<>();
                                
        nodeTotalScore = new HashMap<>();
        //nodeTotalScore.putAll(precedent.getNodeTotalScore());
        //if (!nodeTotalScore.containsKey(startingNode)) {
            nodeTotalScore.put(startingNode, heuristicEstimation(startingNode, endingNode));
        //}
        Double theoricalNodeScore;
        
        int counter = 0;
        current = startingNode;
        //While there is still a Node to evaluate, the while is not over
        while (!openSet.isEmpty() && current.equals(endingNode) == false && counter < 50) {
            //We, firstly, will get the unevaluated node with the lowest score in the nodeTotalScore Map
            current = null;
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
               this.solution = AStar.solved(efficientPredecessor, endingNode, new ArrayList<>());
               break;
           }
           //After that, we pass it into the closed set as it has been treated
           openSet.remove(current);
           closedSet.add(current);
           counter++;
           //Then, we look at all its neighbours to seek for a potential candidate
           for (Node n : getNeighboursFromImage(g, current)) {
               if (!closedSet.contains(n)) {
                   if (!openSet.contains(n)) {
                       openSet.add(n);
                   }
                   //We calculate the cost of getting to this point from our current position
                   theoricalNodeScore = NodeDouble.getValue(nodeScore, current) + Double.valueOf(1);
                   if (nodeScore.containsKey(n)) {
                        if (theoricalNodeScore.compareTo( NodeDouble.getValue(nodeScore, n) ) == -1) {
                            //If the value we calculated is lower than the precedent one, we update our data on the n Node
                            if (efficientPredecessor.containsKey(n) == false) {
                                efficientPredecessor.put(n,current);
                            } else {
                                NodeNode.replaceValue(efficientPredecessor,n,current);
                            }
                            if (nodeScore.containsKey(n)) {
                                System.out.println(theoricalNodeScore + heuristicEstimation(n,endingNode));
                                NodeDouble.replaceValue(nodeScore, n, theoricalNodeScore);
                                NodeDouble.replaceValue(nodeTotalScore, n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                            } else {
                                nodeScore.put(n,theoricalNodeScore);
                                nodeTotalScore.put(n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                            }
                        }
                    } else {
                        if (efficientPredecessor.containsKey(n) == false) {
                            efficientPredecessor.put(n,current);
                        } else {
                            NodeNode.replaceValue(efficientPredecessor,n,current);
                        }
                        nodeScore.put(n,theoricalNodeScore);
                        nodeTotalScore.put(n, theoricalNodeScore + heuristicEstimation(n,endingNode));
                   }
               }
           }
        }
        this.solution = solved(efficientPredecessor,current,new ArrayList<Node>());
    }
    
    /**
     * This method get from the g image variable all the accessible neighbours of the current node
     * @param g
     * @param current
     * @return 
     */
    public ArrayList<Node> getNeighboursFromImage(WritableImage g, Node current) {
        ArrayList<Node> res = new ArrayList<Node>();
        PixelReader p = g.getPixelReader();
        //left neighbour
        if (p.getColor(current.getX()-1, current.getY()).equals(Color.ALICEBLUE)) {
            res.add(new Node(current.getX()-1, current.getY()));
        }
        //right neighbour
        if (p.getColor(current.getX()+1, current.getY()).equals(Color.ALICEBLUE)) {
            res.add(new Node(current.getX()+1, current.getY()));
        }
        //top neighbour
        if (p.getColor(current.getX(), current.getY()-1).equals(Color.ALICEBLUE)) {
            res.add(new Node(current.getX(), current.getY()-1));
        }
        //bottom neighbour
        if (p.getColor(current.getX(), current.getY()+1).equals(Color.ALICEBLUE)) {
            res.add(new Node(current.getX(), current.getY()+1));
        }
        return res;
    }
    
    
    public ArrayList<Node> getSolution() {
        return this.solution;
    }

    public Path getDrawnSolution() {
        return drawnSolution;
    }

    public LinkedList<Node> getClosedSet() {
        return closedSet;
    }

    public LinkedList<Node> getOpenSet() {
        return openSet;
    }

    public HashMap<Node, Node> getEfficientPredecessor() {
        return efficientPredecessor;
    }

    public HashMap<Node, Double> getNodeScore() {
        return nodeScore;
    }

    public HashMap<Node, Double> getNodeTotalScore() {
        return nodeTotalScore;
    }

    public Node getCurrent() {
        return current;
    }
    
    
    
    public Node getClosest(Node endingNode) {
        Node current = null;
        Double minScore = Double.MAX_VALUE;
        for (Node n : this.getOpenSet()) {
            if (current == null) {
                current = n;
            } else if (heuristicEstimation(n, endingNode) < minScore) {
                minScore = heuristicEstimation(n, endingNode);
                current = n;
            }
        }
        return current;
    }
    
    
}

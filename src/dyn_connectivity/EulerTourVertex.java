package dyn_connectivity;


//vertex represented by an euler tour tree node
public class EulerTourVertex {
    public EulerTourVertex lowerVertex;
    //rep of this edge at the next lower level
    public EulerTourVertex upperVertex;
    //rep of this edge at the next upper level

    public ConnectEdge nonForestListHead;
    //first edge in the linked list of level-i edges adj to vertex in G_i but not in forest
    //not including edges in lower levels
    public ConnectEdge forestListHead;
    //first edge in the linked list of level-i edges adj to vertex in G_i and in forest
    public int level;

    public EulerTourNode ett_node; // the euler tour tree node that represents this vertex
    //ok but which one because there will be like 2-3
}

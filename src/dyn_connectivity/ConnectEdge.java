package dyn_connectivity;


public class ConnectEdge {
    public EulerTourVertex u;
    public EulerTourVertex v;
    public EulerTourEdge e;

    //in liked list of same level edges, 
    //prev edge in ll of same type - forest, non forest
    //for linked list starting with u.forestListHead or u.nonForestListHead

    //different lists for u and v because they both start with thet same edge them 
    //branch off to the subtrees that get to those specific vertices
    public ConnectEdge prev_u;


    //next edge in ll of same type - forest, non forest
    public ConnectEdge next_u;

    // //in linked list of edges at the next level
    public ConnectEdge next_v;
    public ConnectEdge prev_v;

    public ConnectEdge(EulerTourVertex u, EulerTourVertex v) {
        this.u = u;
        this.v = v;
        this.e = null;
        this.prev_u = null; // initially no previous edge in the same level list
        this.next_v = null; // initially no next edge in the same level list
        this.next_v = null; // initially no next edge at the next level
        this.prev_v = null; // initially no previous edge at the next level
    }
}

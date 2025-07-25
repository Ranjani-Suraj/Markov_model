package dyn_connectivity;

//when removing edges, if we do not keep track of which edge we are removing and the end points of it, 
//for the ett it gets confusing and your tour will look wrong
//so we keep track of the edge so we know what the adj vertices are and we can remove them if needed
public class EulerTourEdge {
    public final EulerTourNode u;
    public final EulerTourNode v;
    public EulerTourEdge higherEdge;
    //the edge at the next higher level of the euler tour tree
    //dont need to keep track of the lower edge? I guess? Singly connected ll

    public EulerTourEdge(EulerTourNode u, EulerTourNode v) {
        this.u = u;
        this.v = v;
        this.higherEdge = null; // initially no higher edge
    }
}

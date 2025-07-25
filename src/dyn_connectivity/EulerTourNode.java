package dyn_connectivity;


//nodes of the eulertourtree representation using a redblack bst 
public class EulerTourNode extends RedBlackNode<EulerTourNode> {
    public static final EulerTourNode leaf = new EulerTourNode(null);
    public final EulerTourVertex vertex; // vertex this node visits
    //so there would be. 2-3 of these^
    public int size; //subtree size
    public boolean hasNonTreeAdj;
    public boolean hasForestAdj;

    public EulerTourNode(EulerTourVertex vertex) {
        this.vertex = vertex;
        this.size = 1;
        this.hasNonTreeAdj = false;
        this.hasForestAdj = false;
    }


}
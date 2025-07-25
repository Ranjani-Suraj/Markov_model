package dyn_connectivity;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class VertexInfo {
    public EulerTourVertex vertex;
    //map of each conn vertex adjacent to this vertex, to the edge that connects them
    //connvertex.hashcode is used as key, so the fact that the id is random gives faster search time? i guess? 
    //it just randomizes it which i guess doesnt make a difference on paper but prevents repetitions which is good
    public Map<ConnVertex, ConnectEdge> edges = new HashMap<>();
    
    public VertexInfo(EulerTourVertex vertex) {
        this.vertex = vertex;
    }
}

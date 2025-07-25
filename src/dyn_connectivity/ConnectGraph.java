package dyn_connectivity;


import java.util.HashMap;
import java.util.Map;

public class ConnectGraph {
    //map from each vertex to its information 
    //ett_vertex- adj tree edges, adj nontree edges
    //ett_node - does subtree have tree/nontree adj
    public Map<ConnVertex, VertexInfo> vertexInfo;
    public ConnectGraph(){
        vertexInfo = new HashMap<>();
    }   

    //return a vertex's vertex info, or ccreate a vertex if it does not exist
    VertexInfo getOrCreateVertexInfo(ConnVertex vertex) {
        VertexInfo vi = vertexInfo.get(vertex) ;
        if(vi != null) { //oh so thaaats why were creating hash codes randomly
            return vi; //if it exists, return it
        } 
        EulerTourVertex ettVertex = new EulerTourVertex();
        EulerTourNode ettNode = new EulerTourNode(ettVertex);
        ettVertex.ett_node = ettNode; //set the euler tour node of the vertex
        ettNode.left = EulerTourNode.leaf; //initialize the left and right children of the euler tour node
        ettNode.right = EulerTourNode.leaf; //to the leaf node
        //creating a leaf node to insert into the ett
        vi = new VertexInfo(ettVertex); //create a new vertex info
        vertexInfo.put(vertex, vi); //put it in the map
        return vi;

    }
   
    void addToNonForestLL_u(ConnectEdge e){
        e.prev_v = null;
        e.next_v = e.v.nonForestListHead;
        //add the edge to the non forest list
        //specifically, set it as the new head
        //then, if there IS something in the list, 
        if(e.next_v != null){
            if(e.next_v.u == e.v) // if the next edge's u is u, add it to the next edge's u's set
                e.next_v.prev_u = e; // set the previous edge of the next edge to this edge
            else // if the next edge's v is u
                e.next_u.prev_v = e; // set the previous edge of the next edge to this edge
        }
        e.v.nonForestListHead = e; // set the head of the non forest list to this edge
    }

    void addToNonForestLL_v(ConnectEdge e){
        e.prev_u = null;
        e.next_u = e.u.nonForestListHead;
        //add the edge to the non forest list
        //specifically, set it as the new head
        //then, if there IS something in the list, 
        if(e.next_u != null){
            if(e.next_u.u == e.u) // if the next edge's u is u, add it to the next edge's u's set
                e.next_u.prev_u = e; // set the previous edge of the next edge to this edge
            else // if the next edge's v is u
                e.next_u.prev_v = e; // set the previous edge of the next edge to this edge
        }
        e.u.nonForestListHead = e; // set the head of the non forest list to this edge
    }
    //when adding an edge as a non forest edge to the linked list, 
    //we add it to the head of the non forest list of u, 
    //and then adjust the pointers of the next edge in the list as needed
    
    void addToNonForestLL(ConnectEdge e) {
        addToNonForestLL_u(e); //add to the non forest list of u
        addToNonForestLL_v(e); //add to the non forest list of v
    }


    //when adding a forest edge, add it to the head of the 
    //adjacent vertices u and v's forest linked lists
    //specifically to the head of the linked list
    //and then adjust the pointers accordingly
    void addToForestLL(ConnectEdge e) {
        e.prev_u = null;
        e.next_u = e.u.forestListHead;
        //add the edge to the non forest list
        //specifically, set it as the new head
        //then, if there IS something in the list, 
        if(e.next_u != null){
            if(e.next_u.u == e.u) // if the next edge's u is u, add it to the next edge's u's set
                e.next_u.prev_u = e; // set the previous edge of the next edge to this edge
            else // if the next edge's v is u
                e.next_u.prev_v = e; // set the previous edge of the next edge to this edge
        }
        e.u.forestListHead = e; // set the head of the non forest list to this edge

        e.prev_v = null;
        e.next_v = e.v.forestListHead;
        //add the edge to the non forest list
        //specifically, set it as the new head
        //then, if there IS something in the list, 
        if(e.next_v != null){
            if(e.next_v.u == e.v) // if the next edge's u is u, add it to the next edge's u's set
                e.next_v.prev_u = e; // set the previous edge of the next edge to this edge
            else // if the next edge's v is u
                e.next_u.prev_v = e; // set the previous edge of the next edge to this edge
        }
        e.v.forestListHead = e; // set the head of the non forest list to this edge
    
    }


    void removeFromLL_u(ConnectEdge e){
        if(e.prev_u !=null){ //if e is not the head of the liked list
            if(e.prev_u.u == e.u) // if the previous edge's u is u, remove it from the next edge's u's set
                e.prev_u.next_u = e.next_u; // set the next edge of the previous edge to the next edge
            else // if the previous edge's v is u
                e.prev_u.next_v = e.next_v; // set the next edge of the previous edge to the next edge
        } 
        else if( e == e.u.nonForestListHead)//if it is the head of the non tree list
            e.u.forestListHead = e.next_u; // if there is no previous edge, set the head to the next edge
        else //if it is the head of the tree list
            e.u.forestListHead = e.next_u; // if there is no previous edge, set the head to the next edge
        
        if (e.next_u!=null){ //if it is not the end of the linked list
            if(e.next_u.u == e.u) // if the next edge's u is u, remove it from the next edge's u's set
                e.next_u.prev_u = e.prev_u; // set the previous edge of the next edge to the previous edge
            else // if the next edge's v is u
                e.next_u.prev_v = e.prev_v; // set the previous edge of the next edge to the previous edge
        } 
    }

    void removeFromLL_v(ConnectEdge e){
        if(e.prev_v !=null){ //if e is not the head of the liked list
            if(e.prev_v.u == e.v) // if the previous edge's u is v, remove it from the next edge's u's set
                e.prev_v.next_u = e.next_v; // set the next edge of the previous edge to the next edge
            else // if the previous edge's v is v
                e.prev_v.next_v = e.next_v; // set the next edge of the previous edge to the next edge
        } 
        else if( e == e.v.nonForestListHead)//if it is the head of the liked list
            e.v.nonForestListHead = e.next_v; // if there is no previous edge, set the head to the next edge
        else //if it is the head of the tree list
            e.v.forestListHead = e.next_v; // if there is no previous edge, set the head to the next edge
        
        if (e.next_v!=null){ //if it is not the end of the linked list
            if(e.next_v.u == e.v) // if the next edge's u is v
                e.next_v.prev_u = e.prev_v; // set the previous edge of the next edge to the previous edge
            else // if the next edge's v is v
                e.next_v.prev_v = e.prev_v; // set the previous edge of the next edge to the previous edge
        }
    }


    void removeFromLL(ConnectEdge e){
        removeFromLL_u(e);

        //------------------------------------------

        removeFromLL_v(e);
    }

    EulerTourEdge addForestEdge(EulerTourVertex u, EulerTourVertex v) {
        //ok see this is a problem
        //where we add the forest edge makes a Difference
        //because to add it we need to split one of the trees
        //without messing with the eulerness of the tree
        
        //need to add a func to create the vertex in the graph if it doesnt exist alr
        EulerTourNode root = v.ett_node.root(); //root of v and u's tree will be different since they are not yet attached.
        //we assume that v's is bigger, so we add it there
        EulerTourNode last_in_subtree = root.rightmost(); //get the last node in the subtree of v
        if(last_in_subtree.vertex != v){ //if v is not the leaf
            root = last_in_subtree.remove(); //remove the last node in the subtree of v
            if(last_in_subtree.vertex.ett_node == last_in_subtree) { //woulnt this always be true
                EulerTourNode first_in_subtree = root.leftmost(); //get the first node in the subtree of v
                last_in_subtree.vertex.ett_node = first_in_subtree; //set the euler tour node of v to the first node in the subtree
            }
            EulerTourNode[] splitRoots = root.split(v.ett_node); //split the tree at v's euler tour node
            //first subtree is the one with everything before v, second subtree is v and after
            root = splitRoots[1].concatenate(splitRoots[0]); //add first subtree to end of second subtree
            //this means that the subtree with v in it is first, and everything before v is added to the end of the tree
            EulerTourNode newNode = new EulerTourNode(v); //create a new euler
            newNode.left = EulerTourNode.leaf; //initialize the left and right children of the new euler tour node
            newNode.right = EulerTourNode.leaf; //to the leaf node
            newNode.isRed = true; //set the new euler tour node to be red for insertion
            EulerTourNode parent = root.rightmost(); //get rightmost node in the tree to add the new dummy node to
            parent.right = newNode; //set the right child of the parent to the new euler tour node
            newNode.parent = parent; //set the parent of the new euler tour node to
            root = newNode.fixInsertion();
            last_in_subtree = newNode;
        }

        EulerTourNode[] splitRoots = u.ett_node.root().split(u.ett_node); //split the tree at u's euler tour node
        //so basically youve got a tree of all the et before u, and youve ogt a tree of the et after you. 
        //you have a tree of what youre adding, so you just stuff it in the middle, pivoted at u
        EulerTourNode newNode = new EulerTourNode(u); 
        splitRoots[0].concatenate(root, newNode).concatenate(splitRoots[1]);
        //add the edges before u in u's subtree to the subtree of v, pivoted at u. 
        //then add the tree of u containing u to the subtree, finally combining them
        return new EulerTourEdge(newNode, last_in_subtree); //create a new euler tour edge with the new node and the max node
    }

    public void removeForestEdge(EulerTourEdge e){
        EulerTourNode first = e.u;
        EulerTourNode second = e.v;
        if(e.u.compareTo(e.v) > 0){
            first = e.v; //if u is less than v, set first to v
            second = e.u; //set second to u
        }
        else{
            first = e.u; //if u is greater than v, set first to u
            second = e.v; //set second to v
        }

        EulerTourNode secondSuccessor = second.successor(); //node right after thhis in the et inorder traversal 
        if(first.vertex.ett_node == first) { //if the first node is the euler tour node of the vertex
            EulerTourNode successor = secondSuccessor; 
            first.vertex.ett_node = successor; //set first to the successor of the second node
        }

        EulerTourNode root = first.root();
        EulerTourNode[] firstSplitRoots = root.split(first); //split the tree at the first node
        EulerTourNode before = firstSplitRoots[0]; //get the first subtree
        EulerTourNode[] secondSplitRoots = firstSplitRoots[1].split(second); //split the second subtree at the second node
        before.concatenate(secondSplitRoots[1]);
        first.removeWithoutGettingRoot();
    }

    private void addToEdgeMap(ConnectEdge e, VertexInfo srcInfo, ConnVertex destVertex){
        srcInfo.edges.put(destVertex, e); //add the edge to the source vertex's edge map)
    }

    private ConnectEdge removeFromEdgeMap(VertexInfo srcInfo, ConnVertex destVertex){
        ConnectEdge edge = srcInfo.edges.remove(destVertex); //remove the edge from the source vertex's edge map
        if(edge!=null){
            srcInfo.edges = new HashMap<>();
        }
        return edge;
    }

    public boolean addEdge(ConnVertex u, ConnVertex v) {
        VertexInfo u_info = getOrCreateVertexInfo(u); //get or create the vertex info for u
        VertexInfo v_info = getOrCreateVertexInfo(v); //get or create the vertex info for v
        if(u == v) { //if u and v are the same vertex, return false
            return false; //no self loops allowed
        }
        if(u_info == v_info) { //if u and v are the same vertex in the euler tour tree, return false
            return false; //no self loops allowed
        }
        if(u_info.edges.containsKey(v) || v_info.edges.containsKey(u)) {
            return false; //if the edge already exists, return false
        }
        EulerTourVertex etU = u_info.vertex; //get the euler tour vertex for u
        EulerTourVertex etV = v_info.vertex; //get the euler tour

        ConnectEdge edge = new ConnectEdge(etU, etV); //create a new edge with the two vertices

        boolean isForest = !(etU.ett_node.root() == etV.ett_node.root()); //check if the edge is a forest edge by checking if the roots of the two vertices are the same
        if(isForest) {
            addToForestLL(edge); //if it is a forest edge, add it to the forest linked list
            addToEdgeMap(edge, getOrCreateVertexInfo(u), v); //add the edge to the edge map of u
            addToEdgeMap(edge, getOrCreateVertexInfo(v), u); //add the edge to the edge map of v
        } else {
            addToNonForestLL(edge); //if it is a non forest edge, add it to the non forest linked list
            addToEdgeMap(edge, getOrCreateVertexInfo(u), v); //add the edge to the edge map of u
            addToEdgeMap(edge, getOrCreateVertexInfo(v), u); //add the edge to the edge map of v
        }
        addToEdgeMap(edge, u_info, v); //add the edge to the edge map of u
        addToEdgeMap(edge, v_info, u); //add the edge to the edge
        return true; //return true if the edge was added successfully
    }

    public boolean hasEdge(ConnVertex connVertex1, ConnVertex connVertex2) {
        if (connVertex1 == connVertex2) {
            return false;
        }
        VertexInfo info1 = vertexInfo.get(connVertex1);
        if (info1 == null) {
            return false;
        }
        return info1.edges.containsKey(connVertex2);
    }

    public boolean is_tree_edge(ConnVertex connVertex1, ConnVertex connVertex2) {
        //assertIsAugmented();
        if (connVertex1 == connVertex2) {
            return false;
        }
        VertexInfo info1 = vertexInfo.get(connVertex1);
        if (info1 == null) {
            return false;
        }
        ConnectEdge edge = info1.edges.get(connVertex2);
        if (edge == null) {
            return false;
        }
        return edge.e != null;
    }

    private EulerTourVertex getOrCreateLowerVertex(EulerTourVertex vertex) {
        if (vertex.lowerVertex == null) {
            vertex.lowerVertex = new EulerTourVertex();
            EulerTourNode lowerNode = new EulerTourNode(vertex.lowerVertex);
            EulerTourVertex lowerVertex = vertex.lowerVertex;
            lowerVertex.ett_node = lowerNode; //set the euler tour node of the lower vertex
            vertex.lowerVertex = lowerVertex;
            lowerVertex.upperVertex = vertex; //set the upper vertex of the lower vertex to the current vertex
            lowerNode.left = EulerTourNode.leaf; //initialize the left and right children of
            lowerNode.right = EulerTourNode.leaf; //to the leaf node
        }
        return vertex.lowerVertex;
    }

    public int max_comp_size() {
        //assertIsAugmented();
        if (vertexInfo.isEmpty()) {
            return 0;
        }
        int maxSize = 0;
        for (VertexInfo info : vertexInfo.values()) {
            EulerTourNode root = info.vertex.ett_node.root();
            maxSize = Math.max(maxSize, root.size);
        }
        return maxSize/2 + 1;
    }

    //push all adjacent forest edges down from level i to level i-1
    private void pushForestEdgesDown(EulerTourNode root){
        if(!root.hasForestAdj || root.size == 1) {
            return; //if the root has no forest edge or the size is 1, return
        }
        EulerTourNode node;
        for(node = root; node.left.hasForestAdj; node = node.left){
            EulerTourVertex vertex = node.vertex;
            ConnectEdge edge = vertex.forestListHead; //start of list of forest edges adjacent to the vertex
            if(edge != null) {
                EulerTourVertex lowerVertex = getOrCreateLowerVertex(vertex); //get or create the lower vertex
                ConnectEdge prevEdge = null;
                while(edge!=null){ //for every edge in the forest list
                    if(edge.v == vertex || edge.v == lowerVertex){ //while one end of the edge is either the vertex or the lower representation of the vertex
                        prevEdge = edge; //previous edge becomes the current edge
                        edge = edge.next_v; //edge becomes the forest edge adjacent to it 
                    }
                    else{ //if edge u of the edge is the vertex,
                        edge.u = lowerVertex; //the edge's u becomes the representation of u at the lower level
                        edge.v = getOrCreateLowerVertex(edge.v); //the edge's v becomes the representation of v at the lower level
                        EulerTourEdge lowerEdge = addForestEdge(edge.u, edge.v); //create the edge at a lower level
                        lowerEdge.higherEdge = edge.e; //represetnation of the edge at a higher level becomes the current edge, meaning that the edge is being pushed down into a smaller forest
                        edge.e = lowerEdge;
                        prevEdge = edge;
                        edge = edge.next_u;
                    }
                }
                //add vertex.forestListHead to beginning of the lower vertex's forest list
                //basically, everything in the forest list of the vertex is now in the lower vertex's forest list as well
                if(prevEdge.u == lowerVertex)
                    prevEdge.next_u = lowerVertex.forestListHead;
                else
                    prevEdge.next_v = lowerVertex.forestListHead;
                //adding the prev pointers for the douby linked list
                //we added the edge's forest list to the front of the lower vertex's forest list
                if(lowerVertex.forestListHead != null) {
                    if(lowerVertex.forestListHead.u == lowerVertex) {
                        lowerVertex.forestListHead.prev_u = prevEdge;
                    } else {
                        lowerVertex.forestListHead.prev_v = prevEdge;
                    }
                }
                //reset lowerVertex's forest list head to the existing forest list head since we have added the edges to the front
                lowerVertex.forestListHead = vertex.forestListHead;
                vertex.forestListHead = null; //clear the forest list of the vertex
            }

        }
    }

    private ConnectEdge findReplacementEdge(EulerTourNode root){
        if(!root.hasNonTreeAdj) {
            return null; //if the root has no non-tree adjacent edges, return null
        }
        EulerTourNode node;
        //find the leftmost node in the ett with a non-tree adjacent edge
        for(node = root; node.left.hasNonTreeAdj; node = node.left);


        while(node!=null){  //going through every node with adjacent non-tree nodes, from left to right
            EulerTourVertex vertex = node.vertex; //get the vertex of the node
            ConnectEdge edge = vertex.nonForestListHead; //get the first edge in the non-forest list
            //if there are adj non tree edges
            if(edge!=null){

                ConnectEdge replacement = null;
                ConnectEdge prevEdge = null;

                while(edge!=null){ //go through every edge in non forest linked list of vertex
                    EulerTourVertex adjVertex;
                    ConnectEdge nextEdge;
                    if(edge.u == vertex) { //if the edge's u is the vertex
                        adjVertex = edge.v; //the adjacent vertex is the edge's v
                        nextEdge = edge.next_u; //the next edge is the edge's next_v
                    } 
                    else { //if the edge's v is the vertex
                        adjVertex = edge.u; //the adjacent vertex is the edge's u
                        nextEdge = edge.next_v; //the next edge is the edge's next_u
                    }

                    if(adjVertex.ett_node.root() != root){ //if it connects to a different connected component
                        //then it can replace the existing edge since whatever component it connects to
                        //needs to connect to the component that just got disconnected because if it was any other comp, then they would alr be added as a tree edge
                        replacement = edge; //if the adjacent vertex's euler tour node's root is not the root, 
                        break; //break the loop
                    }

                    //if its not a replacement edge, we lower its level. TO start, we remove it from level i
                    if(edge.u == adjVertex)
                        removeFromLL_u(edge);
                    else
                        removeFromLL_v(edge);
                        
                    edge.u = getOrCreateLowerVertex(edge.u); //set the edge's u to the lower vertex
                    edge.v = getOrCreateLowerVertex(edge.v); //set the edge's v to

                    //add edge to lower level
                    if(edge.u != vertex.lowerVertex) //vertex = node.vertex, so we would add edge to the set of adjacent edges of vertex
                        addToNonForestLL_u(edge);
                    else //then edge.v is the lower version of vertex, vertex being the vertex represented by node
                        addToNonForestLL_v(edge);

                    prevEdge = edge; //set the previous edge to the current edge
                    edge = nextEdge; //move to the next edge in the non-forest list
                } 
                //prevedge will be the end of the linked list of non tree edges of vertex
                //if we att prevedge to the start of the lower representtion of vertex, then all the edges that we considered will be added to the lower level
                if(prevEdge != null){  
                    EulerTourVertex lowerVertex = vertex.lowerVertex;
                    //add prevEdge to lowerVertex nonForest list
                    if(prevEdge.u == lowerVertex)
                        prevEdge.next_u = lowerVertex.nonForestListHead;
                    else
                        prevEdge.next_v = lowerVertex.nonForestListHead;
                    //other direction
                    if(lowerVertex.nonForestListHead != null) { //there are adj graph nodes
                        if(lowerVertex.nonForestListHead.u == lowerVertex)   
                            lowerVertex.nonForestListHead.prev_u = prevEdge;
                        else
                            lowerVertex.nonForestListHead.prev_v = prevEdge;
                    }
                    lowerVertex.nonForestListHead = vertex.nonForestListHead;
                }
                //how... is every node in this being affected by only the dead changing. We arent changing the head everywhere is the thing
                vertex.nonForestListHead = edge;
                if(edge.u == vertex){
                    edge.prev_u = null;
                }
                else{
                    edge.prev_v = null;
                }
                if(replacement != null)
                    return replacement;

            }
            //if replacement not found
            //go to the next node with adj non tree nodes to try in next iteration
            if(node.right.hasNonTreeAdj){
                for(node = node.right; node.left.hasNonTreeAdj; node = node.left);
            }
            else{
                while(node.parent != null && (node.parent.right == node || !node.parent.hasNonTreeAdj)){
                    node = node.parent;
                }
                node = node.parent;
            }
        }
        return null; //if no replacements are found
    }
    
    public boolean removeEdge(ConnVertex v_u, ConnVertex v_v){
        if(v_u == v_v)
            return true;
        VertexInfo u_info = vertexInfo.get(v_u);
        if(u_info == null)  
            return false;
        ConnectEdge edge = removeFromEdgeMap(u_info, v_v);
        if(edge == null)
            return false; 
        VertexInfo v_info = vertexInfo.get(v_v);
        removeFromEdgeMap(v_info, v_u);

        removeFromLL(edge);

        if(edge.e != null){
            for(EulerTourEdge levelEdge = edge.e; levelEdge!=null; levelEdge = levelEdge.higherEdge)
                removeForestEdge(levelEdge);
            edge.e = null;
            //search for replacement
            ConnectEdge replacement = null;
            EulerTourVertex levelVertex_u = edge.u;
            EulerTourVertex levelVertex_v = edge.v;
            while(levelVertex_u!=null){
                EulerTourNode root_u = levelVertex_u.ett_node.root();
                EulerTourNode root_v = levelVertex_v.ett_node.root();
                //if one of the graphs does not have an adjacent non-tree edge, then this is deg not true
                if(root_u.hasNonTreeAdj && root_v.hasNonTreeAdj){
                    EulerTourNode root;
                    if(root_u.size < root_v.size){
                        root = root_u;
                    }
                    else    
                        root = root_v;
                    pushForestEdgesDown(root); //push down all adjacent forest edges
                    replacement = findReplacementEdge(root);
                    if(replacement != null)
                        break;
                } 
                if(root_u.size == 1 && levelVertex_u.upperVertex != null)
                    levelVertex_u.upperVertex.lowerVertex = null;
                if(root_v.size == 1 && levelVertex_v.upperVertex != null)
                    levelVertex_v.upperVertex.lowerVertex = null;
                
                //if we dont find an edge at this level, check the next higher level
                levelVertex_u = levelVertex_u.upperVertex;
                levelVertex_v = levelVertex_v.upperVertex;
            }

            if(replacement != null){
                removeFromLL(replacement); //remove it from non forest lsit
                addToForestLL(replacement); //add it to the forest list
                EulerTourVertex replacement_u = replacement.u;
                EulerTourVertex replacement_v = replacement.v;
                EulerTourEdge lowerEdge = null;
                //need to update all higher levels that this edge is now a forest edge
                while(replacement_u!=null){
                    EulerTourEdge levelEdge = addForestEdge(replacement_u, replacement_v);
                    if(lowerEdge == null){//if it is not able to add a forest edge
                        //this shouldnt even happen theoretically
                        replacement.e = levelEdge;
                    }
                    else{
                        lowerEdge.higherEdge = levelEdge;
                    }
                    lowerEdge = levelEdge;
                    replacement_u = replacement_u.upperVertex;
                    replacement_v = replacement_v.upperVertex;
                }
            }
        }
        return true;
    }

    public boolean connected(ConnVertex u_v, ConnVertex v_v){
        if(u_v == v_v)  
            return true;
        VertexInfo u_info = vertexInfo.get(u_v);
        if(u_info == null)
            return false;
        VertexInfo v_info = vertexInfo.get(v_v);
        if(v_info == null)
            return false;
        return u_info.vertex.ett_node.root() == v_info.vertex.ett_node.root();
    }


}

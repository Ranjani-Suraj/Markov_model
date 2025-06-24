package Dynamic_Graph;

//import java.lang.reflect.Array;
import java.util.*;



//i really need to fix this cuz idk if the dictionary stuff is gonna hold up
public class ET_tour{
    //need a map for IDtoNode, NodeSet,
    //edgemap, and adj_map
    Map<Integer, Node> IDtoNode;
    Map<Integer, Set<Node>> NodeSet;
    Map<Integer, Map<Integer, Node>> edgemap;//im dumb this is j tha adjacence list
    //it->second maps (u, v) it to node u
    Map<Boolean, Map<Integer, Integer>> adj_map;

    //adjacency list?

    Bst btree; //wait but we could have multiple trees because theyre not necessarily connected
    //ig that doesnt matter? idk. wait no it definitely does matter wtf is happening


    public ET_tour(){
        NodeSet = new HashMap<>();
        IDtoNode = new HashMap<>();
        edgemap = new Hashtable<>();
        btree = new Bst();
        adj_map = new HashMap<>();
        adj_map.put(true, new HashMap<>()); //tree edges
        adj_map.put(false, new HashMap<>()); //non tree edges
        
    }
    public Node get_node(int u){ //returns IDtoNode[u] whatever that means
        if(IDtoNode.isEmpty()){
            return null;
        }
        return IDtoNode.get(u);
    }
    //returns 
    public Node get_edge(int u, int v){
        //ArrayList<Node> neighbours = NodeSet.get(u);
        //int[] edge = {u, v}; returns u
        if (edgemap.get(u)!=null && edgemap.get(u).get(v) != null){
            return edgemap.get(u).get(v);
        }
        return null;
    }

    public void add_node(int u, Node nu){
        System.out.println("Adding node " + u);

        Bst.change_root(nu);
        
        if(!IDtoNode.containsKey(u)){ //it is not already in the tree
            IDtoNode.put(u, nu);
            //nu.createIfAbsent();
            // adj_map.get(false).computeIfAbsent(u, k -> new HashMap<Integer, Integer>());
            // adj_map.get(true).computeIfAbsent(u, k -> new HashMap<Integer, Integer>());
            nu.adjacent_nodes[0] = adj_map.get(false).getOrDefault(u, 0); //tree edges
            //so that is. no. adhacent nodes of u which we are..not in the tree
            nu.adjacent_nodes[1] = adj_map.get(true).getOrDefault(u, 0); //non tree edges
            NodeSet.computeIfAbsent(u, k -> new HashSet<>());
            System.out.println("adjacent nodes: " + nu.adjacent_nodes[0] + " " + nu.adjacent_nodes[1]);
        }
        nu.update();

    }

    public void add_edge(int u, int v, Node n){
        edgemap.computeIfAbsent(u, k -> new HashMap<>());
        edgemap.computeIfAbsent(v, k -> new HashMap<>());
        edgemap.get(u).put(v, n);
        //edgemap.get(v).put(u, n);
        //^ is supposed to store node v not u but ok?

        //since its pointers this should be okay? i think?
        //how does this initialize tho like when i initiali
    }

    void remove_node(int u, Node n){
        int ntree = n.adjacent_nodes[0], nntree = n.adjacent_nodes[1];
        if(!IDtoNode.containsKey(u)){
            return; //node does not exist
        }
        if(!NodeSet.get(u).remove(n)){
            return;
        }

        if(NodeSet.get(u).isEmpty()){
            IDtoNode.remove(u);
        }
        else{
            Node next = NodeSet.get(u).iterator().next(); //get the next node in the set
            IDtoNode.put(u, next);
            Bst.change_root(next);
            next.adjacent_nodes[0] = ntree;
            next.adjacent_nodes[1] = nntree;
            next.update();
        }
    }

    void remove_edge(int u, int v){
        edgemap.get(u).remove(v);
    }

    void reroot(Node u){
        Bst.change_root(u);
        if(u.left == null){
            return;
        }
        Node lchild = u.left;
        //remove lchild, so we remove the entire left side of the tree
        Bst.remove_child_node(lchild); //removes this nodes connection to its parent, but keeps its suBst
        Node front = Bst.leftmost(lchild); //make the leftmost of the above suBst the root
        front.left = u; //connects the leftmost of the subtree to u
        //why... are we doing this again
        u.parent = front;
        front.update();
        //at this point the root is the previous leftmost node, and u is the left child
        Bst.change_root(u);
        //this changes the root to u, so it only has a right subtree rooted at the previous leftmost
    }

    public boolean connected(int u, int v){
        if(u == v){
            return true;
        }
        Node x = get_node(u);
        Node y = get_node(v);
        if(x == null || y == null){
            return false;
        }
        Bst.change_root(x);
        Bst.change_root(y);
        //keep rotating until y becomes x's parent, or x becomes the root
        while(x.parent != null && x.parent !=y){
            Bst.rotate(x);
        }
        return x.parent == y;
    }


    public int size(int u){
        Node x = get_node(u);
        if(x == null){
            return 1;
        }
        Bst.change_root(x);
        return x.size_subtree/2 + 1;
    }

    //return number of neighbours
    public int get_adjacent(int u, boolean is_treeedge){
        Node x = get_node(u);
        if(x == null){ //the node is not used yet
            return adj_map.get(is_treeedge).getOrDefault(u, 0)>0? u: -1;
        }
        Bst.change_root(x);
        if(x.sum_adjacent_nodes[(is_treeedge)? 1:0] <= 0){
            return -1;
        }
        //is this ... right?
        while(adj_map.get(is_treeedge).getOrDefault(x.name, -1) == 0){
            Node lchild = x.left;
            Node rchild = x.right;
            if(lchild != null && lchild.sum_adjacent_nodes[is_treeedge? 1:0] >0){
                x = lchild;
            }
            else if(rchild != null && rchild.sum_adjacent_nodes[is_treeedge? 1:0] > 0){
                x = rchild;
            }
        }

        Bst.change_root(x);
        return x.name;
    }

    public void update_adjacent(int u, int add_adj, boolean is_treeedge){
        adj_map.get(is_treeedge).merge(u, add_adj, Integer::sum);

        Node x = get_node(u);
        if(x == null){
            return;
        }
        Bst.change_root(x);
        x.adjacent_nodes[is_treeedge? 1:0] += add_adj;
        x.update();
    }

    //now to do cut and link

    public boolean cut(int u, int v){
        System.out.println("Cutting " + u + " and " + v);
        if(!connected(u, v)){
            System.out.println("Nodes " + u + " and " + v + " are not connected.");
            return false; //they are not connected so this actually makes no sense
        }
        Node x = get_edge(u, v);
        if(x == null){
            return false; //edge does not exist
        }
        Node y = get_edge(v, u);
        reroot(x);
        Bst.change_root(y);
        while(x.parent != y){
            Bst.rotate(x);
        }
        Bst.remove_child_node(x); //removes the edge from the tree
        Node next = Bst.next(y);
        if(next != null){
            int temp = next.name;
            Node t = Bst.rightmost(next);
            remove_edge(v, temp);
            add_edge(v, temp, t);
        }
        remove_node(u, x);
        remove_node(v, y);
        remove_edge(u, v);
        remove_edge(v, u);
        Bst.delete_node(x);
        Bst.delete_node(y);
        return true;

    }

    public boolean link(int u, int v){
        System.out.println("Linking " + u + " and " + v);
        print_tour(u);
        print_tour(v);
        if(connected(u, v)){
            System.out.println("Nodes " + u + " and " + v + " are already connected.");
            return false; //they are already connected so no need to link
        }
        Node x = get_node(u);
        Node y = get_node(v);
        if(x != null)
            reroot(x);
        if(y != null)
            reroot(y);
        //make x and y the roots of their respective trees
        Node utemp = Bst.insert_node(x);
        Node vtemp = Bst.insert_node(y);
        //add a new child to x and y and name it utemp and vtemp
        utemp.name = u;
        vtemp.name = v;
        print_tour(u);
        print_tour(v); //this is j ->u u
        //inserts a node t the end of u's tree and v's tree and names it u and v
        add_node(u, utemp);
        add_node(v, vtemp);
        //add it to teh ett
        if(y == null){//if there is no node v, then set it to vtemp, which is the new node iserted into y's tree
            y = vtemp;
        }
        Bst.change_root(y);

        utemp.right = y;
        y.parent = utemp;
        utemp.update();
        
        add_edge(u, v, utemp);
        add_edge(v, u, vtemp);
        print_tour(u);
        return true;
    }

    void inorder(Node u){
        if (u == null){
            return;
        }
        inorder(u.left);
        System.out.print(u.name + " ");

        inorder(u.right);
    }

    void preorder(Node u){
        if (u == null){
            return;
        }
        System.out.print(u.name + " ");
        preorder(u.left);
        preorder(u.right);
    }

    public void print_tour(int u){
        Node root = get_node(u);
        if(root == null){
            System.out.println("Node " + u + " does not exist 290.");
            return;
        }
        Bst.change_root(root);
        while(root.parent!=null){
            root = root.parent; //go to the big time root of the tree
        }
        System.out.println("Tour for node " + u + ":");
        preorder(root);
        System.out.println();
    }

    //now the actual ET Tree functions -> link, split, is connected, size, get adjacent, update adjacent
}
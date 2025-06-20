package Dynamic_Graph;

//import java.lang.reflect.Array;
import java.util.*;



//i really need to fix this cuz idk if the dictionary stuff is gonna hold up
public class ET_tour{
    //need a map for IDtoNode, NodeSet,
    //edgemap, and adj_map
    Map<Character, Node> IDtoNode;
    Map<Character, ArrayList<Node>> NodeSet;
    Map<Character, Map<Character, Node>> edgemap;//im dumb this is j tha adjacence list
    //it->second maps (u, v) it to node u
    Map<Character, Integer> adj_map_tree;
    Map<Character, Integer> adj_map_non_tree;

    //adjacency list?

    Bst btree;

    public ET_tour(){
        IDtoNode = new Hashtable<>();
        edgemap = new Hashtable<>();
        btree = new Bst();
        adj_map_non_tree = new HashMap<>();
        adj_map_tree = new HashMap<>();
    }
    public Node get_node(char u){ //returns IDtoNode[u] whatever that means
        if(IDtoNode.isEmpty()){
            return null;
        }
        return IDtoNode.get(u);
    }
    //returns 
    public Node get_edge(char u, char v){
        //ArrayList<Node> neighbours = NodeSet.get(u);
        //char[] edge = {u, v}; returns u
        if (edgemap.get(u)!=null && edgemap.get(u).get(v) != null){
            return edgemap.get(u).get(v);
        }
        return null;
    }

    public void add_node(char u, Node nu){
        System.out.println("Adding node " + u);
        btree.change_root(nu);
        if(IDtoNode.get(u) == null){ //it is not already in the tree
            IDtoNode.put(u, nu);

            nu.adjacent_nodes[0] = (adj_map_tree.get(u) == null)? 0: adj_map_tree.get(u);
            //so that is. no. adhacent nodes of u which we are..not in the tree
            nu.adjacent_nodes[1] = (adj_map_non_tree.get(u) == null)? 0: adj_map_non_tree.get(u);
            System.out.println("adjacent nodes: " + nu.adjacent_nodes[0] + " " + nu.adjacent_nodes[1]);
        }
        nu.update();

    }

    public void add_edge(char u, char v, Node n){
        edgemap.computeIfAbsent(u, k -> new HashMap<>());
        edgemap.computeIfAbsent(v, k -> new HashMap<>());
        edgemap.get(u).put(v, n);
        //edgemap.get(v).put(u, n);
        //^ is supposed to store node v not u but ok?

        //since its pointers this should be okay? i think?
        //how does this initialize tho like when i initiali
    }

    void remove_node(char u, Node n){
        int ntree = n.adjacent_nodes[0], nntree = n.adjacent_nodes[1];
        NodeSet.get(u).remove(n);
        if(NodeSet.isEmpty()){
            IDtoNode.remove(u);
        }
        else{
            Node next = NodeSet.get(u).get(0);
            IDtoNode.put(u, next);
            btree.change_root(next);
            next.adjacent_nodes[0] = ntree;
            next.adjacent_nodes[1] = nntree;
            next.update();
        }
    }

    void remove_edge(char u, char v){
        edgemap.get(u).remove(v);
    }

    void reroot(Node u){
        btree.change_root(u);
        if(u.left == null){
            return;
        }
        Node lchild = u.left;
        //remove lchild, so we remove the entire left side of the tree
        btree.remove_child_node(lchild); //removes this nodes connection to its parent, but keeps its subtree
        Node front = btree.leftmost(lchild); //make the leftmost of the above subtree the root
        front.left = u; //connects the leftmost of the subtree to u
        //why... are we doing this again
        u.parent = front;
        front.update();
        //at this point the root is the previous leftmost node, and u is the left child
        btree.change_root(u);
        //this changes the root to u, so it only has a right subtree rooted at the previous leftmost
    }

    boolean connected(char u, char v){
        if(u == v){
            return true;
        }
        Node x = get_node(u);
        Node y = get_node(v);
        if(x == null || y == null){
            return false;
        }
        btree.change_root(x);
        btree.change_root(y);
        //keep rotating until y becomes x's parent, or x becomes the root
        while(x.parent != null && x.parent !=y){
            btree.rotate(x);
        }
        return x.parent == y;
    }

    int size(char u){
        Node x = get_node(u);
        if(x == null){
            return 1;
        }
        btree.change_root(x);
        return x.size_subtree/2 + 1;
    }
    //return number of neighbours
    int get_adjacent(char u, boolean is_treeedge){
        Node x = get_node(u);
        if(x == null){ //the node is not used yet
            if(!is_treeedge){//??? in ref code it does [is_treeedge] which is 0 if false, but adj[0] is tree edges?
                return (adj_map_tree.get(u) > 0)? u: -1;
            }
            else{
                return(adj_map_non_tree.get(u) > 0)? u: -1;
            }
        }
        btree.change_root(x);
        if(x.sum_adjacent_nodes[(is_treeedge)? 1:0] <= 0){
            return -1;
        }
        Map<Character, Integer> adj = is_treeedge? adj_map_non_tree: adj_map_tree;
        while(adj.get(x.name) == 0){
            Node lchild = x.left;
            Node rchild = x.right;
            if(lchild != null && lchild.sum_adjacent_nodes[is_treeedge? 1:0] >0){
                x = lchild;
            }
            else if(rchild != null && rchild.sum_adjacent_nodes[is_treeedge? 1:0] > 0){
                x = rchild;
            }
        }

        btree.change_root(x);
        return x.name;
    }

    void update_adjacent(char u, int add_adj, boolean is_treeedge){
        if(is_treeedge) {
            adj_map_non_tree.merge(u, add_adj, Integer::sum);
        }
        else
            adj_map_tree.merge(u, add_adj, Integer::sum);
        Node x = get_node(u);
        if(x == null){
            return;
        }
        btree.change_root(x);
        x.adjacent_nodes[is_treeedge? 1:0] += add_adj;
        x.update();
    }

    //now to do cut and link

    public boolean cut(char u, char v){
        Node x = get_edge(u, v);
        if(x == null){
            return false; //edge does not exist
        }
        Node y = get_edge(v, u);
        reroot(x);
        btree.change_root(y);
        while(y.parent != y){
            btree.rotate(x);
        }
        btree.remove_child_node(x); //removes the edge from the tree
        Node next = btree.next(y);
        if(next != null){
            char temp = next.name;
            Node t = btree.rightmost(next);
            remove_edge(v, temp);
            add_edge(v, temp, t);
        }
        remove_node(u, x);
        remove_node(v, y);
        remove_edge(u, v);
        remove_edge(v, u);
        btree.delete_node(x);
        btree.delete_node(y);
        return true;

    }

    public boolean link(char u, char v){
        System.out.println("Linking " + u + " and " + v);
        if(connected(u, v)){
            return false; //they are already connected so no need to link
        }
        Node x = get_node(u);
        Node y = get_node(v);
        if(x != null)
            reroot(x);
        if(y != null)
            reroot(y);
        
        Node utemp = btree.insert_node(x);
        Node vtemp = btree.insert_node(y);
        utemp.name = u;
        vtemp.name = v;
        add_node(u, utemp);
        add_node(v, vtemp);
        if(y == null){
            y = vtemp;
        }
        btree.change_root(y);
        utemp.right = y;
        y.parent = utemp;
        utemp.update();
        add_edge(u, v, utemp);
        add_edge(v, u, vtemp);
        return true;
    }




    //now the actual ET Tree functions -> link, split, is connected, size, get adjacent, update adjacent
}
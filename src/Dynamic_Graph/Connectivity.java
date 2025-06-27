package Dynamic_Graph;
//import java.lang.reflect.Array;
import java.util.*;

public class Connectivity {
    ArrayList<Integer> vertices;
    ArrayList<ET_tour> spf;
    ArrayList<Map<Integer, Set<Integer>>> adj;
    ArrayList<Map<Integer, Set<Integer>>> tree_adj;
    Map<Integer, Map<Integer, Set<Integer>>> edge_level; //for node u node v, we store level of edge??? what 
    Map<Integer, Set<Integer>> edges;
    //this is supposed to be unordered_map<pair<int, int>, multiset<int>, hash_pair> edge_level;
    //who knows if this is gonna work wtf is a hash pair even
    // struct hash_pair {
    // 	template <class T1,class T2> 
    //     size_t operator()(const pair<T1,T2> &p) const {
    //         auto h1 = hash<T1>{}(p.first);
    //         auto h2 = hash<T2>{}(p.second);
    //         return int(((long long)(1000000007)) * h1 + h2);
    // }
    // };
    //what does this DO^^

    public Connectivity(){
        vertices = new ArrayList<>();
        spf = new ArrayList<>(); //et tours for eahc level
        adj = new ArrayList<>();
        tree_adj = new ArrayList<>();
        edge_level = new HashMap<>();
        //initialize the first level
        spf.add(new ET_tour());
        adj.add(new HashMap<>());
        tree_adj.add(new HashMap<>());
        edges = new HashMap<>();
        //there are logn levels, so we can just. initialize all of them i guess?
        //int num_levels = (int)(Math.floor(Math.log(5)/Math.log(2)));
        // for(int i = 0; i < n; i++){
        //     tree_adj.add(new HashMap<>());
        // }
    }

    public  void add_vertex(int u){
        vertices.add(u);
        spf.add(spf.size()-1, new ET_tour());
        adj.add(adj.size()-1, new HashMap<Integer, Set<Integer>>());
        tree_adj.add(tree_adj.size()-1, new HashMap<Integer, Set<Integer>>());
        System.out.println("Added vertex " + u);
    }

    void add_edge_level(int u, int v, int level, boolean is_tree_edge){
        if(u > v){
            int temp = u;
            u = v;
            v = temp;
        }
        System.out.println("Adding edge " + u + "-" + v + " at level " + level);
        edge_level.putIfAbsent(u, new HashMap<>());
        edge_level.get(u).putIfAbsent(v, new HashSet<>());
        edge_level.get(u).get(v).add(level);
        edge_level.putIfAbsent(v, new HashMap<>());
        edge_level.get(v).putIfAbsent(u, new HashSet<>());
        edge_level.get(v).get(u).add(level);

        if(is_tree_edge){
            tree_adj.get(level).putIfAbsent(u, new HashSet<>());
            tree_adj.get(level).putIfAbsent(v, new HashSet<>());

            tree_adj.get(level).get(u).add(v);
            tree_adj.get(level).get(v).add(u);
        }
        else{
            adj.get(level).putIfAbsent(u, new HashSet<>());
            adj.get(level).putIfAbsent(v, new HashSet<>());

            adj.get(level).get(u).add(v);
            adj.get(level).get(v).add(u);
        }
        spf.get(level).update_adjacent(u, 1, is_tree_edge);
        spf.get(level).update_adjacent(v, 1, is_tree_edge);
    }

    void remove_edge_level(int u, int v, int level, boolean is_tree_edge){
        if(u > v){
            int temp = u;
            u = v;
            v = temp;
        }
        // edge_level.putIfAbsent(u, new HashMap<>());
        // edge_level.get(u).putIfAbsent(v, new ArrayList<>());

        edge_level.get(u).get(v).remove(level); //remove the edge from the set of edges of x level
        edge_level.get(v).get(u).remove(level); //remove the edge from the set of edges of y level
                //ok see problem is this is a problem because level is index but im deleting entry chee
        
        if(is_tree_edge){
            
            tree_adj.get(level).get(u).remove(v); //if itis a tree edge then we remove it from its level
            tree_adj.get(level).get(v).remove(u);
        }
        else{
            System.out.println("Removing edge lelvel " + u + "-" + v + " at level " + level + " "+adj.get(level).get(u));
            adj.get(level).get(u).remove(v);
            adj.get(level).get(v).remove(u);
        }
        spf.get(level).update_adjacent(u, -1, is_tree_edge); //reduce level of edge by 1
        spf.get(level).update_adjacent(v, -1, is_tree_edge);
    }

    public int level(int u, int v){
        if(u > v){
            int temp = u;
            u = v;
            v = temp;
        }
        if(!edge_level.containsKey(u) || !edge_level.get(u).containsKey(v)){
            return -1; //edge does not exist
        }
        int lev = -1;
        if(edge_level.get(u).get(v).iterator().hasNext()){
            lev = edge_level.get(u).get(v).iterator().next();
            System.out.println("found level = "+lev);
        }

        return lev;//return the first level of the edge
    }

    public  boolean add_edge(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        if(v>u){
            int t = u;
            u = v; v = t;
        }
        if(has_edge(u, v) || has_edge(v, u)){
            System.out.println("Edge " + u + "," + v + " already exists");
            return true; //edge already exists
        }
        //if they are connected at the lowest level then they are connected at all levels
        if(!spf.get(0).connected(u, v)){
           //they are not already connected, so we add the edge
           System.out.println("Adding edge " + u + "-" + v);
           spf.get(0).link(u, v);
           add_edge_level(u, v, 0, true);
           
        }
        else{
            System.out.println("alr connected so j adding tree");
            add_edge_level(u, v, 0, false);
        }
        edges.putIfAbsent(u, new HashSet<>());
        edges.get(u).add(v);
        edges.putIfAbsent(v, new HashSet<>());
        edges.get(v).add(u);
        return true;
    }

    public boolean has_edge(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        //return adj.contains(u) && adj.get(u).(v);

        return edges.get(u) != null && edges.get(u).contains(v);
    }

    public boolean delete_edge(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        int level = level(u, v);
        if(level == -1){
            return false; //edge does not exist
        }
        //need to cut for all levels?
        if(!spf.get(0).cut(u, v)){ //if cutting the edge results in false, then it failed to cut. we need to cut it at every level right
            System.out.println("non tree edge  " + u + "-" + v );
            //it is not a tree edge, so we j remove it from adj 
            edges.get(u).remove(v);
            edges.get(v).remove(u);
            remove_edge_level(u, v, level, false);
            return true;
        }
        edges.get(u).remove(v);
        edges.get(v).remove(u);
        remove_edge_level(u, v, level, true);
        System.out.println("Cut succeeded now we search for a replacement");
        
        //for every level, 
        for(int i = level; i>=0; i--){
            System.out.println("checking level "+ i);
            if(spf.get(i).size(u) > spf.get(i).size(v)){
                //make u the smaller subset
                int temp = u;
                u = v;
                v = temp;
            }
            //this isnt working 
            //it is not able to find replacements that are not directly connected to u.
            while(true){

                int x = spf.get(i).get_adjacent(u, true);
                System.out.println("checking adj tree nodes of u: "+u+" x: "+x);
                if(x == -1){
                    break; //no more adjacent nodes
                }
                // if(tree_adj.get(i).get(x).isEmpty()){
                //     System.out.println("no adjacent nodes at level "+i+" so we break");
                //     // remove_edge_level(x, u, i, false);
                //     // add_edge_level(x, u, i+1, false);
                //     continue; //no more adjacent nodes
                // }
                //increase the level of every tree
                while(!tree_adj.get(i).get(x).isEmpty()){ //adjacency list of tree nodes, so adjacent tree nodes to x at level i are increased level

                    int y = tree_adj.get(i).get(x).iterator().next();
                    System.out.println("checked adj edge edges? y = "+y);
                    remove_edge_level(x, y, i, true);
                    add_edge_level(x, y, i+1, true);
                    spf.get(i+1).link(x, y); //link x and y at a higher level???? now that the edges exist just on a higher level??? on the ET tree???
                }
            
            }

            boolean flag = false;
            while(!flag){
                
                int x = spf.get(i).get_adjacent(u, false);
                System.out.println("adjacent nodes at level "+i+" non tree: "+x);
                if(x == -1){
                    break;
                }
                // if(!adj.get(i).containsKey(x)){
                //     System.out.println("no adjacent nodes at level "+i+" so we break");
                //     break; //no more adjacent nodes
                // }
                if(adj.get(i).get(x).isEmpty()){
                    System.out.println("no adjacent nodes at level "+i+" so we break");
                    // remove_edge_level(x, u, i, false);
                    // add_edge_level(x, u, i+1, false);
                    break; //no more adjacent nodes
                }
                while(!adj.get(i).get(x).isEmpty()){
                    System.out.println("if there is a non tree edge at level "+i+" that is adjacent to u, ");
                    int y = adj.get(i).get(x).iterator().next();
                    System.out.println("we check if the adjacent incident node y also connects to v: we check if x: "+x+
                        "which is adjacent to a node in the cc of :"+u+" is also adjacent to a node in the cc of :"+v);
                    if(spf.get(i).connected(y, v)){
                        
                        for(int j = 0; j<=i; j++){
                            spf.get(j).link(x, y);
                        }
                        flag = true;
                        remove_edge_level(x, y, i, false);
                        add_edge_level(x, y, i, true);
                        break;
                    }
                    else{
                        remove_edge_level(x, y, i, false);
                        add_edge_level(x, y, i+1, false);
                    }
                    
                }
            }
            if(flag){
                //we found a replacement
                break;
            }
        }
        //}
        return true;
    }


    public  boolean connected(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        if(u == v){
            return true; //they are the same vertex
        }
        return spf.get(0).connected(u, v);
    }
}

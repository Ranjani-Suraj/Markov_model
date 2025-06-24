import Dynamic_Graph.*;

//import java.lang.reflect.Array;
import java.util.*;

public class Connectivity {
     ArrayList<Integer> vertices;
     ArrayList<ET_tour> spf;
     ArrayList<Map<Integer, ArrayList<Integer>>> adj;
     ArrayList<Map<Integer, ArrayList<Integer>>> tree_adj;
     Map<Integer, Map<Integer, ArrayList<Integer>>> edge_level;
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
        spf = new ArrayList<>();
        adj = new ArrayList<>();
        tree_adj = new ArrayList<>();
        edge_level = new HashMap<>();
        //initialize the first level
        spf.add(new ET_tour());
        adj.add(new HashMap<>());
        tree_adj.add(new HashMap<>());
        //there are logn levels, so we can just. initialize all of them i guess?
        int num_levels = (int)(Math.floor(Math.log(5)/Math.log(2)));
        for(int i = 0; i <= num_levels; i++){
            tree_adj.put(i, new ArrayList<>());
        }
    }

    public  void add_vertex(int u){
        vertices.add(u);
        spf.add(spf.size()-1, new ET_tour());
        adj.add(adj.size()-1, new HashMap<Integer, ArrayList<Integer>>());
        tree_adj.add(tree_adj.size()-1, new HashMap<Integer, ArrayList<Integer>>());
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
        edge_level.get(u).putIfAbsent(v, new ArrayList<>());
        edge_level.get(u).get(v).add(level);
        if(is_tree_edge){
            tree_adj.get(level).get(u).add(v);
            tree_adj.get(level).get(v).add(u);
        }
        else{
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
        edge_level.putIfAbsent(u, new HashMap<>());
        edge_level.get(u).putIfAbsent(v, new ArrayList<>());
        edge_level.get(u).get(v).remove(level);
        //ok see problem is this is a problem because level is index but im deleting entry chee
        
        if(is_tree_edge){
            tree_adj.get(level).get(u).remove(v);
            tree_adj.get(level).get(v).remove(u);
        }
        else{
            adj.get(level).get(u).remove(v);
            adj.get(level).get(v).remove(u);
        }
        spf.get(level).update_adjacent(u, -1, is_tree_edge);
        spf.get(level).update_adjacent(v, -1, is_tree_edge);
    }

    int level(int u, int v){
        if(u > v){
            int temp = u;
            u = v;
            v = temp;
        }
        if(!edge_level.containsKey(u) || !edge_level.get(u).containsKey(v)){
            return -1; //edge does not exist
        }

        return edge_level.get(u).get(v).get(0); //return the first level of the edge
    }

    public  boolean add_edge(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        if(!spf.get(0).connected(u, v)){
           //they are not already connected, so we add the edge
           spf.get(0).link(u, v);
           add_edge_level(u, v, 0, true);
        }
        else{
            add_edge_level(u, v, 0, false);
        }
        return true;
    }



    public boolean delete_edge(int u, int v){
        if(!vertices.contains(u) || !vertices.contains(v)){
            return false; //one of the vertices does not exist
        }
        int level = level(u, v);
        if(level == -1){
            return false; //edge does not exist
        }
        if(!spf.get(0).cut(u, v)){
            //it is not a tree edge, so we j remove it from adj 
            remove_edge_level(u, v, level, false);
            return true;
        }
        remove_edge_level(u, v, level, true);
        for(int i = level; i>=0; i--){
            if(spf.get(i).size(u) > spf.get(i).size(v)){
                //u is the root of the subtree, so we can just remove it
                int temp = u;
                u = v;
                v = temp;
            }
            while(true){
                int x = spf.get(i).get_adjacent(u, true);
                if(x == -1){
                    break; //no more adjacent nodes
                }
                while(!tree_adj.get(i).get(x).isEmpty()){
                    int y = tree_adj.get(i).get(x).get(0);
                    remove_edge_level(x, y, i, true);
                    add_edge_level(x, y, i+1, true);
                    spf.get(i+1).link(x, y);
                }
            }
            boolean flag = false;
            while(!flag){
                int x = spf.get(i).get_adjacent(u, false);
                if(x == -1){
                    break;
                }
                while(!adj.get(i).get(x).isEmpty()){
                    int y = adj.get(i).get(x).get(0);
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

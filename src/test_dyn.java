import java.util.*;
import Dynamic_Graph.*;
// import random;

public class test_dyn {

    ConnGraph g1 = new ConnGraph();
    Map<Integer, Set<Integer>> graph = new HashMap<>();
    int n;
    int cycles;
    
    ArrayList<ConnVertex> dyn_vertices;
    public test_dyn(int size, int runs){
        n = size; cycles = runs;
        dyn_vertices = new ArrayList<>(n);
    }


    Set<Integer> explore(int start){
        Set<Integer> visited = new HashSet<>();
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(start);
        int v;
        while(stack.isEmpty() == false){
            v = stack.remove(stack.size()-1);
            for(int u : graph.get(v)){
                if(!visited.contains(u)){
                    visited.add(u);
                    stack.add(u);
                }
                if(visited.size() == n){
                    stack.clear();
                    break;
                }
            }
        }

        return visited;
    }

    public void dfs(int start){
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(start);
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        ArrayList<Integer> remaining_unvisited = new ArrayList<>();
        for(int i = 0; i < n; i++){
            if (i!=start && !graph.get(start).contains(i)){
                remaining_unvisited.add(i);
            }
            else {
                visited.add(i);
            }
        }
        while(remaining_unvisited.isEmpty() == false){
            visited.addAll(explore(remaining_unvisited.get(remaining_unvisited.size())));
            remaining_unvisited.clear();
            for(int i = 0; i < n; i++){
                if (!visited.contains(i)){
                    remaining_unvisited.add(i);
                }
            }
        }
    }

    void generate_dyn_graph(){
        for(int i = 0; i<n; i++){
            dyn_vertices.add(new ConnVertex());
        }
    }
    void generate_reg_graph(){
        for(int i = 0; i<n; i++){
            graph.put(i, new HashSet<>());
        }
        // for(int i = 0; i<n; i++){
        //     for(int j = 0; j<n; j++){
        //         if(i!=j){
        //             graph.get(i).add(j);
        //             graph.get(j).add(i);
        //         }
        //     }
        // }
    }

    boolean connected(int u, int v){
        Set<Integer> cc_u = explore(u);
        return cc_u.contains(v);
    }

    public boolean test(){
        Random random = new Random();
        generate_dyn_graph();
        generate_reg_graph();
        double add_time = 0.0, del_time = 0.0, conn_time = 0.0;
        double avg_add = 0.0, avg_del = 0.0, avg_conn = 0.0;
        int num_add = 0, num_del = 0, num_conn = 0;
        double start, end;
        for(int i = 0; i<cycles; i++){
            add_time = 0.0; del_time = 0.0; conn_time = 0.0;
            int[] edge = {random.nextInt(n-1), random.nextInt(n-1)};
            if(edge[0] == edge[1]){
                //i-=1;
                continue;
            }
            boolean add = random.nextBoolean();
            if(add == true){
                if(!graph.get(edge[0]).contains(edge[1])){
                    graph.get(edge[0]).add(edge[1]);
                    graph.get(edge[1]).add(edge[0]);
                }
                start = System.nanoTime();
                g1.addEdge(dyn_vertices.get(edge[0]), dyn_vertices.get(edge[1]));
                add_time = (System.nanoTime() - start);
                num_add += 1;
            }
            else{
                if(graph.get(edge[0]).contains(edge[1])){
                    graph.get(edge[0]).remove(edge[1]);
                    graph.get(edge[1]).remove(edge[0]);
                }
                start = System.nanoTime();
                g1.removeEdge(dyn_vertices.get(edge[0]), dyn_vertices.get(edge[1]));
                del_time = (System.nanoTime() - start);
                num_del += 1;
            }
            int[] edge2 = {random.nextInt(n-1), random.nextInt(n-1)};
            if(edge2[0] == edge2[1]){
                //i-=1;
                continue;
            }
            boolean vanilla_conn = connected(edge2[0], edge2[1]);
            start = System.nanoTime();
            boolean dyn_conn =  g1.connected(dyn_vertices.get(edge2[0]), dyn_vertices.get(edge2[1]));
            conn_time = (System.nanoTime() - start);
            num_conn += 1;
            boolean res = (vanilla_conn && dyn_conn) || !(vanilla_conn || dyn_conn);
            // System.out.println("huh"+res);
            if(res != true){
                System.out.println(i);
                System.out.println(vanilla_conn+" "+ dyn_conn);
                return false;
            }

            int[] edge3 = {random.nextInt(n-1), random.nextInt(n-1)};
            if(edge3[0] == edge3[1]){
                //i-=1;
                continue;
            }
            boolean vanilla_has = graph.get(edge3[0]).contains(edge3[1]), dyn_has =  g1.hasEdge(dyn_vertices.get(edge3[0]), dyn_vertices.get(edge3[1]));
           
            boolean res2 = (vanilla_has && dyn_has) || !(vanilla_has || dyn_has);
            // System.out.println("huh"+res);
            if(res2 != true){
                System.out.println(i);
                System.out.println("has "+vanilla_has+" "+ dyn_has);
                return false;
            }
            //System.out.println("add time: "+add_time+" del time: "+del_time+" conn time: "+conn_time);
            avg_add = (add_time !=0)? avg_add + add_time : avg_add; 
            avg_del = (del_time !=0)? avg_del + del_time : avg_del;
            avg_conn = (conn_time !=0)? avg_conn + conn_time : avg_conn;
            //averages=> add time: 4881.438093314482 del time: 1128.5742652899125 conn time: 1461.3527054108217 ???
        }
        System.out.println("add time: "+avg_add/num_add+" del time: "+avg_del/num_del+" conn time: "+avg_conn/num_conn);

        return true;
    }
}

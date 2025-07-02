package markov_funcs;
//import java.util.ArrayList;

import Dynamic_Graph.*;


public class CouplingPast {
    //need to implement coupling form the past
    //this takes a graph and basically, for some number of epochs, will randomly generate a bunch of numbers and edges and have you try coupling using them
    //if you succeed, great. If not, you do another x epochs and again and again until you succeed
    //so for this, lets just accept the number of epochs, 2 graphs, and try coupling them
    Connectivity g1, g2;
    int n;
    int epochs;
    double p, q, pi;
    int edges1, edges2; //number of edges in g1 and g2 respectively
    int iterations;
    public CouplingPast(int epochs, int n, double p, double q) {
        this.epochs = epochs;
        this.n = n;
        this.g1 = new Connectivity();
        this.g2 = new Connectivity();
        this.p = p;
        this.q = q;
        this.pi = p/(p+q*(1-p));
        //make g1 a complete graph
        //make g2 an empty graph
        for (int i = 1; i<=n; i++){
            g1.add_vertex(i);
            g2.add_vertex(i);
            
        }
        //System.out.println("NODES ADDED");
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n; j++){
                //System.out.println("ADD EDGE: "+j);
                if(i!=j)
                    g1.add_edge(i, j);

            }
        }
        edges1 = n * (n - 1) / 2; //complete graph has n(n-1)/2 edges
        edges2 = 0; //empty graph has 0 edges
        iterations = 0;
    }


    // void add_remove(boolean cut_edge, int[] edge, double r, Connectivity g){
    //     if(cut_edge){
    //         if(r <= pi){ //we add it if it is a cut edge w probability pi
    //             if(g.has_edge(edge[0], edge[1])){
    //                 edges1++;
    //                 g.add_edge(edge[0], edge[1]); 

    //                 System.out.println("added edge to  g1 "+edge[0]  + " " + edge[1]);
    //             }
    //             //dont need to mess with teh cc stuff since thats all handled
    //         }
    //         else{
    //             //remove it 
    //             if(g.has_edge(edge[0], edge[1])){
    //                 g.delete_edge(edge[0], edge[1]);
    //                 edges1--;
    //             }
    //         }
    //     }
    //     else if(r <= p){ //we still add it if its not a cut edge w prob p
    //         if(g.has_edge(edge[0], edge[1])){
    //             edges1++;
    //             g.add_edge(edge[0], edge[1]);
    //             System.out.println("added edge to g1 "+edge[0]  + " " + edge[1]);
    //         }
    //     }
    //     else{ //we remove it
    //         if(g.has_edge(edge[0], edge[1])){
    //             g.delete_edge(edge[0], edge[1]);
    //             edges1--;
    //         }
    //     }
    // }

    public boolean run_epochs(){
        //System.out.println("running epochs tada---------------------------------------------");
        int t = 0, i=0;
        for (i = 0; i<epochs; i++){
            double r = Math.random();
            if(edges1 == edges2){
                //coupling over
                //System.out.println("Coupling successful after " + t + " more epochs.");
                this.iterations += t;
                return true;
            }
            
            int[] edge = {(int)(Math.random() * n) + 1, (int)(Math.random() * n) + 1};
            if(edge[0] > edge[1]){
                int tm = edge[0]; edge[0] = edge[1]; edge[1] = tm;
            }
            //System.out.println("NEW EDGE TIME "+ edge[0] + " " + edge[1]);
            //System.out.println("Current edges: g1: "+edges1+" g2: "+edges2);
            //System.out.println("p: "+p+" q: "+q+" r: "+r+" pi: "+pi);
            if(edge[0] == edge[1]){
                i--;
                continue; 
            }
            t++;
           
            //need to check if edge is a cut edge. This means we remove edge, if it is still connected, then it is not.
            boolean replace1 = g1.has_edge(edge[0], edge[1]);
            //if it is not a tree edge and is in the graph, then it is def not a cut edge
            
            //do i need to actually delete and replace it. Maybe i can just see if i find a replacement?
            //code to find a replacement exists, maybe I dublicate it to see if it returns soemthing?
            boolean cut_edge1 = false, cut_edge2 = false;
            if(g1.is_tree_edge(edge[0], edge[1])){
                //System.out.println("edge is a tree edge");
                if(replace1){
                    //System.out.println("edge is in g1");
                    g1.delete_edge(edge[0], edge[1]);
                
                }
                
                
                //if they are not connected, then removing the edge
                //splits the cc so it IS a cut edge
                if(!g1.connected(edge[0], edge[1])){
                    //edge is not a cut edge, so we can add it to g2
                    //System.out.println("edge is a cut edge for g1");
                    cut_edge1 = true;
                }
                else{
                    //System.out.println("edge is not a cut edge for g1");
                    cut_edge1 = false;
                }
                if(replace1){
                    //System.out.println("adding edge back to g1 "+edge[0]  + " " + edge[1]);
                    g1.add_edge(edge[0], edge[1]); //add it back
                }
            }
            else if (replace1){
                cut_edge1 = false;
                //System.out.println("edge is not a tree edge in g1 but it is IN g1 so it cannot be a cut edge");
            }
            else{
                //it is not in the graph, but we need to check if they are alr connected
                boolean connected = g1.connected(edge[0], edge[1]);
                //System.out.println("nodes u: "+edge[0]+" and v: "+edge[1]+" are "+connected);
                cut_edge1 = !connected;
            }
            
            

            //System.out.println("starting g2 now");

            boolean replace2 = g2.has_edge(edge[0], edge[1]);
            //it says the edge does not exist which is so confusing
            if(replace2)                {
                g2.delete_edge(edge[0], edge[1]);
            }
            if(!g2.connected(edge[0], edge[1])){
                //edge is not a cut edge, so we can add it to g2
                //System.out.println("edge is a cut edge for g2");
                cut_edge2 = true;
            }
            if(replace2)
                g2.add_edge(edge[1], edge[0]); //add it back

            //System.out.println("starting to add/delete!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
            //need to do the markov stuff lol i forgor
            //need to keep track of number of edges whoops
            if(cut_edge1){
                //System.out.println("cut edge for g1 "+edge[0]  + " " + edge[1]+ ", r = " + r + " pi = " + pi);
                if(r <= pi){ //we add it if it is a cut edge w probability pi
                    if(!g1.has_edge(edge[0], edge[1])){
                        edges1++;
                        g1.add_edge(edge[0], edge[1]); 
                        //System.out.println("added edge to g1 "+edge[0]  + " " + edge[1]+ "------------------------152");
                    }
                    //dont need to mess with teh cc stuff since thats all handled
                }
                else{
                    //remove it 
                    if(g1.has_edge(edge[0], edge[1])){
                        g1.delete_edge(edge[0], edge[1]);
                        edges1--;
                        //System.out.println("deleted edge to g1 "+edge[0]  + " " + edge[1]+ "------------------------161");
                    }
                }
            }
            else if(r <= p){ //we still add it if its not a cut edge w prob p
                //System.out.println("not cut edge for g1 "+edge[0]  + " " + edge[1]+ ", r = " + r + " p = " + p);
                if(!g1.has_edge(edge[0], edge[1])){
                    edges1++;
                    g1.add_edge(edge[0], edge[1]);
                    //System.out.println("added edge to g1 "+edge[0]  + " " + edge[1]+ "------------------------169");
                }
            }
            else{ //we remove it
                //System.out.println("not cut edge for g1 "+edge[0]  + " " + edge[1]+ ", r = " + r + " p = " + p);
                if(g1.has_edge(edge[0], edge[1])){
                    g1.delete_edge(edge[0], edge[1]);
                    edges1--;
                    //System.out.println("deleted edge to g1 "+edge[0]  + " " + edge[1]+ "------------------------176");
                }
            }

            if(cut_edge2){
                //System.out.println("cut edge for g2 "+edge[0]  + " " + edge[1]+ ", r = " + r + " pi = " + pi);

                if(r <= pi){ //we add it if it is a cut edge w probability pi
                    if(!g2.has_edge(edge[0], edge[1])){
                        edges2++;
                        g2.add_edge(edge[0], edge[1]); 
                        //System.out.println("added edge to g2 "+edge[0]  + " " + edge[1]+ "------------------------185");
                    }
                    //else System.out.println("already in g2 "+edge[0]  + " " + edge[1]+ "------------------------187");
                    //dont need to mess with teh cc stuff since thats all handled
                }
                else{
                    //remove it 
                    if(g2.has_edge(edge[0], edge[1])){
                        g2.delete_edge(edge[0], edge[1]);
                        edges2--;
                        //System.out.println("deleted edge to g2 "+edge[0]  + " " + edge[1]+ "------------------------194");

                    }
                }
            }
            else if(r <= p){ //we still add it if its not a cut edge w prob p
                if(!g2.has_edge(edge[0], edge[1])){
                    edges2++;
                    g2.add_edge(edge[0], edge[1]);
                    //System.out.println("added edge to g2 "+edge[0]  + " " + edge[1] + "------------------------203");

                }
            }
            else{ //we remove it
                if(g2.has_edge(edge[0], edge[1])){
                    g2.delete_edge(edge[0], edge[1]);
                    edges2--;
                    //System.out.println("deleted edge to g2 "+edge[0]  + " " + edge[1]+ "------------------------211");

                }
            }
            //System.out.println("additions over");
        }
        this.iterations+=t;
        return false;
    }

    public double[] couple(){
        int num = 1;
        while(true){
        //for (int m = 0; m<num; m++){
            //just keep running until its true? i dont even need to generate anything it 
            //just generates right
            boolean done = run_epochs();
            if(done)
                break;
            //need to find a way to print the graph so far
            // for(int i = 1; i<=n; i++){
            //     for(int j = i; j<=n; j++){
            //         if(g1.has_edge(i, j)){
            //             System.out.println("G1 :"+ i+" " + j + " lvl: "+g1.level(i, j));
            //         }
            //         if(g2.has_edge(i, j)){
            //             System.out.println("G2 :" +i+" " + j + " lvl: "+g2.level(i, j));
            //         }
                    

            //     }
            // }
            // System.out.println("so far: "+iterations+"------------------------------");

        }
        for(int i = 1; i<=n; i++){
            for(int j = i; j<=n; j++){
                if(g1.has_edge(i, j)){
                    //System.out.println("G1 :"+ i+" " + j + " lvl: "+g1.level(i, j));
                }
                if(g2.has_edge(i, j)){
                    //System.out.println("G2 :" +i+" " + j + " lvl: "+g2.level(i, j));
                }
                

            }
        }
        System.out.println("edges: "+edges1+" "+edges2+" iterations: "+iterations);
        int largest_component = g1.dfs(1);
        double[] result = {largest_component, iterations};
        return result;
    }

}

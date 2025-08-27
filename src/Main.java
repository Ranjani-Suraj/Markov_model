// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
//import Dynamic_Graph.*;
import markov_funcs.GlauberCoupling;
import markov_funcs.CouplingPast;
import java.util.Random;
//import com.dyn_connect;
//import java.sql.Time;
import java.util.*;


// import Dynamic_Graph.Bst;
// import Dynamic_Graph.ConnGraph;
// import Dynamic_Graph.ConnVertex;
// import Dynamic_Graph.Connectivity;
// import Dynamic_Graph.ET_tour;
// import Dynamic_Graph.Augmentation;
// import Dynamic_Graph.Node;


// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.chart.BarChart;
// import javafx.scene.chart.CategoryAxis;
// import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.XYChart;
// import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        //Node u = new Node(1);
        //Bst btree = new Bst();
        
        // ET_tour et = new ET_tour();
        // Connectivity con = new Connectivity();
        // con.add_vertex(1);
        // con.add_vertex(2);
        // con.add_vertex(3);
        // con.add_vertex(4);
        // con.add_vertex(5);
        // System.out.println("Added nodes 1 to 5");
        // con.add_edge(1, 2);
        // System.out.println("Added edge 1-2");
        // con.add_edge(2, 3);
        // con.add_edge(5, 4);
        // con.add_edge(4, 5);
        // System.out.println("1 and 4 connected? " + con.connected(1, 4));
        // System.out.println("1 and 5 connected? " + con.connected(1, 5));
        // System.out.println("1 and 3 connected? " + con.connected(1, 3));
        // con.add_edge(3, 4);
        // con.add_edge(1, 3);
        // con.add_edge(2, 4);
        // con.delete_edge(1, 3);
        // con.delete_edge(3, 4);
        // con.delete_edge(2, 4);
        // System.out.println("1 and 3 connected? " + con.connected(1, 3));
        // System.out.println("1 and 5 connected? " + con.connected(1, 5));

       
        //x/5 = 0.05
        //so x = 0.25
        //double p = 5.0/(double);

        //CouplingPast cp = new CouplingPast(100, n, 0.02, 2.0);
        // cp.couple();
        
        //....it seems to work?
        // et.cut(4, 1);
        // et.print_tour(1);

        // et.cut(3, 2);
        // et.print_tour(3);
        // et.print_tour(2);

        //it...seems to work????
        //now lets test connectivity i guess?

        System.out.println("Let us begin (battle music)");

        // Connectivity con = new Connectivity();
        // for(int i = 0; i<5; i++){
        //     con.add_vertex(i);
        //     for(int j = 0; j<5; j++){
        //         if(i!=j)
        //             con.add_edge(i, j);
        //         System.out.println("i ="+i+" j="+j);
        //         //con.add_edge(j, i);
        //         con.max_cc();
        //     }
        // }

        // ConnGraph graph = new ConnGraph();
        // ConnVertex vertex1 = new ConnVertex();
        // ConnVertex vertex2 = new ConnVertex();
        // ConnVertex vertex3 = new ConnVertex();
        // ConnVertex vertex4 = new ConnVertex();
        // ConnVertex vertex5 = new ConnVertex();
        // ConnVertex vertex6 = new ConnVertex();
        // graph.addEdge(vertex1, vertex2);
        // graph.addEdge(vertex2, vertex3);
        // graph.addEdge(vertex4, vertex5);
        // graph.addEdge(vertex2, vertex6);
        // graph.addEdge(vertex1, vertex4);
        // graph.addEdge(vertex1, vertex6);
        // System.out.println(graph.connected(vertex1, vertex3));  // Returns true
        // graph.removeEdge(vertex1, vertex2);
        // System.out.println(graph.connected(vertex1, vertex6));  // Returns true
        // graph.removeEdge(vertex2, vertex6);
        // System.out.println(graph.connected(vertex1, vertex6));  // Returns true
        // graph.removeEdge(vertex1, vertex6);
        // System.out.println(graph.connected(vertex1, vertex6));  // Returns false


        //Main.tests(); p = 4/10 so it should be. big? so thats. for n = 10 is 10*9/5 = 45 no it should be way bigger 
        //



        // Random rand = new Random(42);
        // System.out.println("Randomint "+rand.nextInt() + " ");
        // System.out.println("Randomint "+rand.nextInt() + " ");
        // Random rand2 = new Random(42);

        // System.out.println("Randomint "+rand.nextInt() + " "+rand2.nextInt());
        // System.out.println("Randomint "+rand.nextInt() + " "+rand2.nextInt());

        long start = System.nanoTime();
        CouplingPast cp1 = new CouplingPast(1000*1000, 1000 , 0.0015, 2);
        cp1.couple();
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) + " ns");
        System.out.println();

        // start = System.currentTimeMillis();
        // GlauberCoupling cp2 = new GlauberCoupling(1, 1000 , 1.5/1000, 2);
        
        // cp2.couple();
        // end = System.currentTimeMillis();
        // System.out.println("Time taken: " + (end - start) + " ms");
        // System.out.println();
         


        // test_dyn test = new test_dyn(1000, 10000);
        // System.out.println(test.test());






        //sum adjacent is sometimes negative which shouldtn be possible so thats a problem
        //so its failing because im updating twice since its in add_edge_level
        //obv size isnt working which is a thing in and of itself  


        // int iters = 1000;
        // Map<Double, ArrayList<double[]>> results = new HashMap<>();
        // long[] times = new long[iters];
        // double[] p_choices = new double[25];//{0.0015, 0.0016, 0.0017, 0.0018, 0.0019, 0.002, 0.0021, 0.0022, 0.0023, 0.0024, 0.0025};
        // int n = 100;
        // p_choices[0] = 0.5/n; //0.5, 0.6, 0.7, 0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 
        // for(int i = 1; i<=24; i+=1){
        //     p_choices[i] = p_choices[i-1] + 0.1/n;

        // }
        
        // for(int i = 0; i < iters; i++){
        //     int ch = Math.abs((int)(Math.random()*p_choices.length));
        //     System.out.println("n = "+n+", epochs = "+n*n+", p = "+p_choices[ch] + " q = 2");
        //     CouplingPast cp = new CouplingPast(n*n, n, p_choices[ch], 2);
        //     long start1 = System.nanoTime();
        //     double[] output = cp.couple(); //largest component, iterations
        //     long end1 = System.nanoTime();
        //     times[i] = end1-start1;
        //     results.putIfAbsent(p_choices[ch], new ArrayList<>());
        //     results.get(p_choices[ch]).add(output);
        //     System.out.println("Run "+i+"took "+times[i]+" nanoseconds, "+output[1]+" iterations, and gave largest cc "+output[0]+" for p = "+p_choices[ch]);
        // }
        // //results: {p, {largest comp, iterations}}
        // double avg_time = 0.0, avg_size = 0.0, avg_overall_time = 0.0;
        // for(int i = 0; i<p_choices.length; i++){
        //     double t_c[] = new double[2] ;
        //     int size = results.get(p_choices[i]).size();
        //     for(int j = 0; j< size; j++){
        //         t_c = results.get(p_choices[i]).get(j);
        //         //System.out.println(""+p_choices[i]+" "+t_c[0]+ " ");
        //         avg_size+=t_c[0];
        //         avg_time += t_c[1];
        //         avg_overall_time += t_c[1];

        //     }
        //     avg_size/=size; avg_time/=size;
        //     System.out.println("for p = "+p_choices[i]+" , avg time "+avg_time+" avg size "+avg_size+" \\");
        //     avg_size = 0.0; avg_time = 0.0;
        // }
        // System.out.println("Average overall time: "+avg_overall_time/iters);
    }
}
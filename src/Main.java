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
        

        System.out.println("Let us begin (battle music)");

        // long start = System.nanoTime();
        // int x = 1000;
        // CouplingPast cp1 = new CouplingPast(x*x, x , 0.0015, 2);
        // cp1.couple();
        // long end = System.nanoTime();
        // System.out.println("Time taken: " + (end - start) + " ns");
        // System.out.println();



        //sum adjacent is sometimes negative which shouldtn be possible so thats a problem
        //so its failing because im updating twice since its in add_edge_level
        //obv size isnt working which is a thing in and of itself  


        int iters = 100;
        Map<Double, ArrayList<double[]>> results = new HashMap<>();
        long[] times = new long[iters];
        Random random = new Random();
        double[] p_choices = new double[25];//{0.0015, 0.0016, 0.0017, 0.0018, 0.0019, 0.002, 0.0021, 0.0022, 0.0023, 0.0024, 0.0025};
        int n = 1000;
        p_choices[0] = 0.5/n; //0.5, 0.6, 0.7, 0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 
        for(int i = 1; i<=24; i+=1){
            p_choices[i] = p_choices[i-1] + 0.1/n;

        }
        int[] graph_sizes = {1000, 10000, 5000, 7500, 2500, 500, 750};
        int[] q_options = {1, 2};
        for(int i = 0; i < iters; i++){
            int ch = Math.abs((int)(Math.random()*p_choices.length));
            int n_index = random.nextInt(7);
            //n = graph_sizes[n_index];
            // int q_index = (int)(Math.round(random.nextDouble()));
            // int q = q_options[q_index];
            int q = 2;
            System.out.println("n = "+n+", epochs = "+n*n+", p = "+p_choices[ch] + " q = "+q);
            long start1 = System.nanoTime();
            CouplingPast cp = new CouplingPast(n*n, n, p_choices[ch], q);
            double[] output = cp.couple(); //largest component, iterations
            
            long end1 = System.nanoTime();
            times[i] = end1-start1;
            output[1] = times[i];
            results.putIfAbsent(p_choices[ch], new ArrayList<>());
            results.get(p_choices[ch]).add(output);
            System.out.println("Run "+i+"took "+times[i]+" nanoseconds, "+output[1]+" iterations, and gave largest cc "+output[0]+" for p = "+p_choices[ch]);
        }
        //results: {p, {largest comp, iterations}}
        double avg_time = 0.0, avg_size = 0.0, avg_overall_time = 0.0;
        int index = 0;
        for(int i = 0; i<p_choices.length; i++){
            
            double t_c[] = new double[2] ;
            int size = results.get(p_choices[i]).size();
            for(int j = 0; j< size; j++){
                t_c = results.get(p_choices[i]).get(j);
                //System.out.println(""+p_choices[i]+" "+t_c[0]+ " ");
                avg_size+=t_c[0];
                avg_time += t_c[1];
                avg_overall_time += t_c[1];
            }
            avg_size/=size; avg_time/=size;
            System.out.println("for p = "+p_choices[i]+" , avg time "+avg_time+" avg size "+avg_size+" \\");
            avg_size = 0.0; avg_time = 0.0;
        }
        System.out.println("Average overall time: "+avg_overall_time/iters);
    }
}

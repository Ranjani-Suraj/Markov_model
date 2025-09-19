// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
//import Dynamic_Graph.*;
import markov_funcs.GlauberCoupling;
import markov_funcs.CouplingPast;

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


        int iters = 1000;
        Map<Double, ArrayList<double[]>> results = new HashMap<>();
        long[] times = new long[iters];
        Random random = new Random();
        double[] p_choices = new double[25];//{0.0015, 0.0016, 0.0017, 0.0018, 0.0019, 0.002, 0.0021, 0.0022, 0.0023, 0.0024, 0.0025};
        int n = 1000;
        p_choices[0] = 0.5/n; //0.5, 0.6, 0.7, 0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 
        for(int i = 1; i<=24; i+=1){
            p_choices[i] = p_choices[i-1] + 0.1/n;

        }
        int[] graph_sizes = {1000, 10000, 5000, 7500, 2500, 500, 100000};
        int[] q_options = {1, 2};
        Map<Integer, Map<Integer, Map<Double, ArrayList<double[]>>>> final_results = new HashMap<>();
        // {n, {q, {p, {largest comp, time}}}}
        for(int i = 0; i < iters; i++){
            int ch = Math.abs((int)(Math.random()*p_choices.length));
            int n_index = random.nextInt(7);
            n = graph_sizes[n_index];
            int q_index = (int)(Math.round(random.nextDouble()));
            int q = q_options[q_index];
            //int q = 2;
            if (final_results.containsKey(n)){ //if n has not been hit yet
                if (final_results.get(n).containsKey(q)){ //if this q fro this n has not been hit yet
                    if (!final_results.get(n).get(q).containsKey(p_choices[ch])){ //if this p for this q for this n has not been hit yet
                        final_results.get(n).get(q).put(p_choices[ch], new ArrayList<>());
                    }
                    //if this p exists, then we do nothing
                    
                } 
                else {
                    final_results.get(n).put(q, new HashMap<>());
                    final_results.get(n).get(q).put(p_choices[ch], new ArrayList<>());
                }
            } else {
                final_results.put(n, new HashMap<>());
                final_results.get(n).put(q, new HashMap<>());
                final_results.get(n).get(q).put(p_choices[ch], new ArrayList<>());
            }
            System.out.println("n = "+n+", epochs = "+n*n+", p = "+p_choices[ch] + " q = "+q);
            long start1 = System.nanoTime();
            CouplingPast cp = new CouplingPast(n*n, n, p_choices[ch], q);
            double[] output = cp.couple(); //largest component, iterations
            
            long end1 = System.nanoTime();
            times[i] = end1-start1;
            output[1] = times[i];
            results.putIfAbsent(p_choices[ch], new ArrayList<>());
            results.get(p_choices[ch]).add(output);
            final_results.get(n).get(q).get(p_choices[ch]).add(output);
            System.out.println("Run "+i+"took "+times[i]+" nanoseconds, "+output[1]+" iterations, and gave largest cc "+output[0]+" for p = "+p_choices[ch]);
        }
        //results: {p, {largest comp, iterations}}
        double avg_time = 0.0, avg_size = 0.0, avg_overall_time = 0.0;
        int index = 0;
        
        for(int _n = 0; _n < graph_sizes.length; _n++){
            n = graph_sizes[_n];
            if (final_results.containsKey(n)){
                System.out.print("For n = "+n+"-----------------------\n");
                for(int _q = 0; _q < q_options.length; _q++){
                    int q = q_options[_q];
                    if (final_results.get(n).containsKey(q)){
                        System.out.println(" For q = "+q+"------------------------\n");
                        for(int i = 0; i<p_choices.length; i++){
                            //double avg_time = final_results.get(n).get(q).get(p_choices[i])
                            int size = 0;
                            avg_size = 0.0; avg_time = 0.0;
                            int index2 = 0;
                            if (final_results.get(n).get(q).containsKey(p_choices[i])){
                                for(index2 = 0; index2 < final_results.get(n).get(q).get(p_choices[i]).size(); index2++){
                                    double t_c[] = new double[2] ;
                                    size = final_results.get(n).get(q).get(p_choices[i]).size();
                                    t_c = final_results.get(n).get(q).get(p_choices[i]).get(index2);
                                    //index2 ++;
                                    //System.out.println(""+p_choices[i]+" "+t_c[0]+ " ");
                                    avg_size += t_c[0];
                                    avg_time += t_c[1];
                                    avg_overall_time += t_c[1];
                                }
                            }
                            
                            System.out.println("  for p = "+p_choices[i]+" , time "+avg_time/size+" avg size "+avg_size/size+" \\");
                        }
                    }
                }
            }
        }
        // for(int i = 0; i<p_choices.length; i++){
            
        //     double t_c[] = new double[2] ;
        //     int size = results.get(p_choices[i]).size();
        //     for(int j = 0; j< size; j++){
        //         ArrayList<double[]> t = results.get(p_choices[i]);
        //         t_c = t.get(j);
        //         //System.out.println(""+p_choices[i]+" "+t_c[0]+ " ");
        //         avg_size+=t_c[0];
        //         avg_time += t_c[1];
        //         avg_overall_time += t_c[1];
        //     }
        //     avg_size/=size; avg_time/=size;
        //     System.out.println("for p = "+p_choices[i]+" , avg time "+avg_time+" avg size "+avg_size+" \\");
        //     avg_size = 0.0; avg_time = 0.0;
        // }
        System.out.println("Average overall time: "+avg_overall_time/iters);
    }
}

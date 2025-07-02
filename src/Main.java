// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
//import Dynamic_Graph.*;
import markov_funcs.CouplingPast;

//import java.sql.Time;
import java.util.*;



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
        int iters = 1000;
        Map<Double, ArrayList<double[]>> results = new HashMap<>();
        long[] times = new long[iters];
        double[] p_choices = new double[25];//{0.0015, 0.0016, 0.0017, 0.0018, 0.0019, 0.002, 0.0021, 0.0022, 0.0023, 0.0024, 0.0025};
        int n = 100;
        p_choices[0] = 0.5/n; //0.5, 0.6, 0.7, 0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 
        for(int i = 1; i<=24; i+=1){
            p_choices[i] = p_choices[i-1] + 0.1/n;

        }
        
        for(int i = 0; i < iters; i++){
            int ch = Math.abs((int)(Math.random()*p_choices.length));
            System.out.println("n = "+n+", epochs = 100, p = "+p_choices[ch] + " q = 2");
            CouplingPast cp = new CouplingPast(100, n, p_choices[ch], 2);
            long start = System.nanoTime();
            double[] output = cp.couple(); //largest component, iterations
            long end = System.nanoTime();
            times[i] = end-start;
            results.putIfAbsent(p_choices[ch], new ArrayList<>());
            results.get(p_choices[ch]).add(output);
            System.out.println("Run "+i+"took "+times[i]+" nanoseconds, "+output[1]+" iterations, and gave largest cc "+output[0]+" for p = "+p_choices[ch]);
        }
        double avg_time = 0.0, avg_size = 0.0;
        for(int i = 0; i<p_choices.length; i++){
            double t_c[] = new double[2] ;
            int size = results.get(p_choices[i]).size();
            for(int j = 0; j< size; j++){
                t_c = results.get(p_choices[i]).get(j);
                //System.out.println("for p = "+p_choices[i]+" results: "+t_c[0]+ " "+t_c[1]);
                avg_size+=t_c[0];
                avg_time += t_c[1];

            }
            avg_size/=size; avg_time/=size;
            System.out.println("for p = "+p_choices[i]+", avg time "+avg_time+" avg size "+avg_size);
            avg_size = 0.0; avg_time = 0.0;
        }


        System.out.printf("\nHello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import Dynamic_Graph.*;
import markov_funcs.CouplingPast;

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

        int n = 5;
        CouplingPast cp = new CouplingPast(100, n, 3/n, 1);
        cp.couple();
        
        //....it seems to work?
        // et.cut(4, 1);
        // et.print_tour(1);

        // et.cut(3, 2);
        // et.print_tour(3);
        // et.print_tour(2);

        //it...seems to work????
        //now lets test connectivity i guess?


        System.out.printf("\nHello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}
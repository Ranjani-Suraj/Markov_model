package Dynamic_Graph;

public class Node{
    public int name;
    public Node left, right, parent;
    public int size_subtree;
    public int[] adjacent_nodes; //number of tree nodes in the tree[1] and non tree
    public int[] sum_adjacent_nodes; //sum of adjacent nodes in subtree
    
    public Node(int name){
        this.name = name;
        left = right = parent = null;
        adjacent_nodes = new int[2];
        sum_adjacent_nodes = new int[2];
        adjacent_nodes[0] = adjacent_nodes[1] = sum_adjacent_nodes[0] =
                sum_adjacent_nodes[1] = 0;
        size_subtree = 1;

    }

    // public Node getParent() {
    //     return parent;
    // }

    public void update(){
        size_subtree = 1;
        sum_adjacent_nodes[0] = adjacent_nodes[0];
        sum_adjacent_nodes[1] = adjacent_nodes[1];
        if(left != null){
            size_subtree += left.size_subtree;
            sum_adjacent_nodes[0] += left.sum_adjacent_nodes[0];
            sum_adjacent_nodes[1] += left.sum_adjacent_nodes[1];
        }
        if(right != null){
            size_subtree += right.size_subtree;
            sum_adjacent_nodes[0] += right.sum_adjacent_nodes[0];
            sum_adjacent_nodes[1] += right.sum_adjacent_nodes[1];
        }
    }


}

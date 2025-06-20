package Dynamic_Graph;
// import java.util.ArrayList;
// import java.util.*;
public class Bst {
    //ArrayList<Node> current_tour;

    void rotate_right(Node u){
        Node par = u.parent;
        Node gpar = par.parent;
        Node rchild = u.right;
        u.parent = gpar;
        if(gpar != null){
            if(par == gpar.left)
                gpar.left = u;
            else
                gpar.right = u;
        }
        u.right = par;
        par.parent = u;
        par.left = rchild;
        if(rchild != null){
            rchild.parent = par;
        }
        par.update();
        u.update();
    }
    void rotate_left(Node u){
        Node par = u.parent;
        Node gpar = par.parent;
        Node lchild = u.left;
        u.parent = gpar;
        if(gpar != null){
            if(par == gpar.right)
                gpar.right = u;
            else
                gpar.left = u;
        }
        u.left = par;
        par.parent = u;
        par.right = lchild;
        if(lchild != null){
            lchild.parent = par;
        }
        par.update();
        u.update();
    }
    void rotate(Node u){
        Node par = u.parent;
        if(par == null){
            return;
        }
        if(u == par.left){
            rotate_right(u);
        }
        else
            rotate_left(u);
    }
    public void change_root(Node u){     //making u the root
        Node par = u.parent;
        if(par == null){
            return; //u is already the root
        }
        
        Node gpar = par.parent;
        while(u.parent != null){
            par = u.parent;
            gpar = par.parent;
            if(gpar == null){
                rotate(u);
                break;
            }
            if((u == par.left) == (par == gpar.left)){ //both are left or both are right
                rotate(par);              //go up 2 levels
                rotate(u);
            }
            else{                  //one left one right
                rotate(u);
            }   rotate(u);   
        }
    }


    Node insert_node(Node u){       //inserting a new node
        if(u == null){
            return null;
        }
        change_root(u);   //keeps it balanced
        while(u.right!=null)
            u = u.right; //keep going down until you become the rightmost node
        Node newnode = new Node('0');
        newnode.parent = u;
        u.right = newnode;    //put the newnode as the right child of the rightmost node
        u.update();
        change_root(newnode);       //make the newnode the root, so every time we insert, it becomes the root
        return newnode;
    }

    void delete_node(Node u){
        change_root(u);
        Node lchild = u.left;
        Node rchild = u.right;
        if(lchild == null && rchild == null){     // if there is only 1 node and we delete it
             return;
        }
        if(lchild == null){
            //only a right child exists
            u.right.parent = null;
            u.right = null;
            u.update();
        }
        else if(rchild == null){
            //only left child
            u.left.parent = null;
            u.left = null;
            u.update();
        }
        else{

        }

    }

    void remove_child_node(Node u){
        Node par = u.parent;
        if(par == null){
            return;
        }
        if(u == par.left){
            par.left = null;
        }
        else{
            par.right = null;
        }
        u.parent = null;
        par.update();
    }

    Node leftmost(Node u){
        change_root(u);
        while(u.left != null){
            u = u.left;
        }
        change_root(u);
        return u;
    }

    Node rightmost(Node u){
        change_root(u);
        while(u.right != null){
            u = u.right;
        }
        change_root(u);
        return u;
    }

    Node next(Node u){
        change_root(u);
        u = u.right;
        if(u == null)
            return null;
        while(u.left != null){
            u = u.left;
        }
        change_root(u);
        return u;
    }


}

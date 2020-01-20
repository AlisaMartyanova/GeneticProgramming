import java.util.ArrayList;
import java.util.List;


public class Individual {

    Node root;
    List<Node> genes = new ArrayList<>();
    int maxHeight = 6;
    int geneLength(){
        return genes.size();
    }
    int fitness = 0;
    int solution;



    public Individual(){
        makeInd();
    }


    private void makeInd(){
        while (geneLength()<2){     //to have height more than 1
            if (genes.size()>0)
                genes.clear();
            root = generate(1, root);   //generate random tree
            makeList(root);
            geneLength();
        }
    }

    private void regenerate(){
        genes.clear();
        makeInd();
    }


    //generate random tree with height from 2 to 6
    public Node generate(int h, Node node){

        if(h >= maxHeight)
            return null;

        if (h<maxHeight-1) {
            node = new Node(16);
            node.setLevel(h);
        }
        else {
            node = new Node(10);
            node.setLevel(h);
            node.left=node.right=null;
            return node;
        }


        //if a node contains the sign it can have children, node with factorial can have only left child
        if (node.value>10){
            node.left = generate(h+1, node.left);
            node.left.parent = node;
            if(node.value<16){
                node.right = generate(h+1, node.right);
                node.right.parent = node;
            }

        }

        return node;
    }

    public void makeList(Node node){
        if (node == null)
            return;
        makeList(node.left);
        genes.add(node);
        makeList(node.right);
    }


    /*
    * solution of the whole tree(equation) lies in the root
    * if it meets division by zero or stack overflow, the tree regenerates
    */
    public void computeSolution(){
        boolean inv = false;
        while(!inv){
            try {
                solution = root.computeValue();
                inv = true;
            } catch (ArithmeticException | StackOverflowError ex) {
                regenerate();
            }
        }


    }

    public void computeFitness(int target){
        fitness = Math.abs((solution - target));
    }

    public void print(Node node){
        if (node == null)
            return;

        System.out.print("(");;
        print(node.left);

        if(node.value<11)
            System.out.print(node.value);
        else{
            switch (node.value){
                case 11: System.out.print("+"); break;
                case 12: System.out.print("-"); break;
                case 13: System.out.print("*"); break;
                case 14: System.out.print("/"); break;
                case 15: System.out.print("^"); break;
                case 16: System.out.print("!"); break;
            }
        }
        if(node.value!=16)
            print(node.right);

        System.out.print(")");

    }

}

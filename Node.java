import java.math.BigInteger;
import java.util.Random;

public class Node {

    /*
    * 1-10 - numbers
    * 11 +
    * 12 -
    * 13 *
    * 14 /
    * 15 ^
    * 16 !
    */
    int value, level = 0;
    Node left = null, right = null, parent = null;
    int computedValue = 0;
    Random rn = new Random();

    public Node(int bound){

        value = rn.nextInt(bound)+1;
    }

    public void setLevel(int val){
        level = val;
    }

    public int computeValue(){
        if(value<11)
            computedValue = value;
        else {
                switch (value){
                    case 11: computedValue = left.computeValue() + right.computeValue(); break;
                    case 12: computedValue = left.computeValue() - right.computeValue(); break;
                    case 13: computedValue = left.computeValue() * right.computeValue(); break;
                    case 14: computedValue = left.computeValue() / right.computeValue(); break;
                    case 15: computedValue = (int)Math.pow(left.computeValue(), right.computeValue()); break;
                    case 16: computedValue = factorial(left.computeValue()); break;
                }
        }

        return computedValue;

    }

    private int factorial(int n){
        if (n <= 1)
            return 1;
        else
            return n * factorial(n - 1);
    }
}

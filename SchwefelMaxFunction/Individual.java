import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual {

    int fitness = 0;
    List<Integer> genes = new ArrayList<>();    // 0 and 8 for signs of two numbers, 1-7, 9-15 for binary numbers
    int geneLength = 16;

    // generate individual with random genes
    public Individual(){
        Random rn = new Random();

        for (int i = 0; i<geneLength; i++){
            genes.add(rn.nextInt(2));
        }
    }

    //get abs decimal value of x0 and x1 out of gene
    //d1 and d2 are for defining is it x0 or x1
    protected int getXi(int d1, int d2){
        int ans = 0;
        int tempInd = 6;
        int temp[] = new int[7];

        for (int i = d1; i<d2; i++){    //make from binary to decimal
            temp[tempInd] = genes.get(i);
            tempInd--;
        }
        for (int i = 0; i<temp.length; i++){
            ans+=temp[i]*Math.pow(2, i);
        }
        if(genes.get(d1-1) == 1)
            ans*=-1;
        return ans;
    }

    // fitnes equals to the absolute greatest value between two numbers in gene
    public void computeFitness(){
        fitness = 0;
        int x0 = Math.abs(getXi(1, 8));
        int x1 = Math.abs(getXi(9, 16));
        if (x0>=x1)
            fitness = x0;
        else
            fitness = x1;
    }
}

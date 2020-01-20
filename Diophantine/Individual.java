import java.util.Random;

public class Individual {

    Random rn = new Random();
    int alleles[] = new int[4];
    int fitness = 0;
    int geneLength = 4;
    int result = 0;

    public Individual(int target){
        for(int i = 0; i<geneLength; i++){
            alleles[i] = rn.nextInt(target)+1;
        }
    }

    public void computeFitness(int aCoef, int bCoef, int cCoef, int dCoef, int target){
        fitness = Math.abs(target-(aCoef*alleles[0]+bCoef*alleles[1]+cCoef*alleles[2]+dCoef*alleles[3]));
    }

    public void computeResult (int aCoef, int bCoef, int cCoef, int dCoef, int target){
        result = aCoef*alleles[0]+bCoef*alleles[1]+cCoef*alleles[2]+dCoef*alleles[3];
    }
}

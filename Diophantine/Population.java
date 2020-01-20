import java.util.ArrayList;
import java.util.List;

public class Population {

    int popSize;
    List<Individual> individuals = new ArrayList<>();
    int fitness = 0;

    public void initPop(int size, int target){
        popSize = size;
        for(int i = 0; i<popSize; i++){
            individuals.add(new Individual(target));
        }
    }

    public void computeFitness(int aCoef, int bCoef, int cCoef, int dCoef, int target){
        int min = Integer.MAX_VALUE;
        for(int i = 0; i< individuals.size(); i++){
            individuals.get(i).computeFitness(aCoef, bCoef, cCoef, dCoef, target);
            if (individuals.get(i).fitness<min)
                min = individuals.get(i).fitness;
        }

        fitness = min;
    }

    public Individual getFittest(){
        int min = Integer.MAX_VALUE;
        Individual temp = null;

        for(int i = 0; i< individuals.size(); i++){
            if (individuals.get(i).fitness<min){
                min = individuals.get(i).fitness;
                temp = individuals.get(i);
            }
        }

        return temp;
    }

    public Individual getSecondFittest(){
        int min = Integer.MAX_VALUE;
        Individual temp = null;

        for(int i = 0; i< individuals.size(); i++){
            if (individuals.get(i).fitness<min && !individuals.get(i).equals(getFittest())){
                min = individuals.get(i).fitness;
                temp = individuals.get(i);
            }
        }

        return temp;
    }
}

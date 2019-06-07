import java.util.ArrayList;
import java.util.List;

public class Population {


    int popSize;
    List<Individual> individuals = new ArrayList<>();
    int fitness;

    public void initPopulation(int popSize){
        this.popSize = popSize;
        for(int i = 0; i<popSize; i++)
            individuals.add(new Individual());
    }


    public Individual getFittest(){
        int min = Integer.MAX_VALUE;
        int minInd = individuals.size();

        for (int i = 0; i<individuals.size(); i++){
            if(min >= individuals.get(i).fitness){
                min = individuals.get(i).fitness;
                minInd = i;
            }
        }
        fitness = individuals.get(minInd).fitness;
        return individuals.get(minInd);
    }


    public void calculateFitness(int target){
        for (int i = 0; i<individuals.size(); i++){
            individuals.get(i).computeSolution();
            individuals.get(i).computeFitness(target);
        }

        getFittest();
    }

}

import java.util.ArrayList;
import java.util.List;

public class Population {
    int popSize;
    List<Individual> inds = new ArrayList<>();
    int fittest = 0;

    //initial random population
    public void initPopulation(int size){
        popSize = size;
        for (int i = 0; i<popSize; i++){
            inds.add(new Individual());
        }

    }

    //the fittest individual is with the least fitness
    public Individual getFittest(){
        int min = Integer.MAX_VALUE;
        int minInd = inds.size();

        for (int i = 0; i<inds.size(); i++){
            if(min >= inds.get(i).fitness){
                min = inds.get(i).fitness;
                minInd = i;
            }
        }
        fittest = inds.get(minInd).fitness;
        return inds.get(minInd);
    }

    public Individual getSecondFittest(){
        int min = Integer.MAX_VALUE;
        int minInd = inds.size();

        for (int i = 0; i<inds.size(); i++){
            if(min >= inds.get(i).fitness && !inds.get(i).equals(getFittest())){
                min = inds.get(i).fitness;
                minInd = i;
            }
        }
        //fittest = inds.get(minInd).fitness;
        return inds.get(minInd);
    }

    //calculate fitness of each individual and then fitness of population
    public void calculateFitness(){
        for (int i = 0; i < inds.size(); i++){
            inds.get(i).computeFitness();
        }
        getFittest();
    }

}

import java.util.Random;


/*
* This program implements Schwefel Max Function.
* The purpose is to find such x0 and x1 that the value of function y(x0, x1) = max(x0, x1) will be minimal.
*/


public class GeneticAlgorithm {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;

    public static void main(String args[]){

        GeneticAlgorithm alg = new GeneticAlgorithm();
        alg.population.initPopulation(10);
        alg.population.calculateFitness();

        System.out.println("Generation: " + alg.generationCount + " fittest: " +
                            alg.population.getFittest().fitness + " second fittest: " + alg.population.getSecondFittest().fitness);

        for (int i = 0; i<alg.population.popSize; i++){
            System.out.print("individual " + i + " ");
            for(int j = 0; j<alg.population.inds.get(0).geneLength; j++){
                System.out.print(alg.population.inds.get(i).genes.get(j));
            }
            System.out.println(" fittnes: " + alg.population.inds.get(i).fitness);
        }


        /*for (int i = 0; i<alg.population.popSize; i++)
            System.out.print(alg.population.inds[i].fitness + " ");*/

        System.out.println();
        System.out.println();

        while (alg.generationCount<50){
            alg.generationCount++;

            alg.selection();
            alg.crossover();

            System.out.println("Generation: " + alg.generationCount + " fittest: " +
                               alg.population.getFittest().fitness + " second fittest: " + alg.population.getSecondFittest().fitness);
            for (int i = 0; i<alg.population.popSize; i++){
                System.out.print("individual " + i + " ");
                for(int j = 0; j<alg.population.inds.get(0).geneLength; j++){
                    System.out.print(alg.population.inds.get(i).genes.get(j));
                }
                System.out.println(" fittnes: " + alg.population.inds.get(i).fitness);
            }
            System.out.println();
            System.out.println();

        }

        System.out.println("Solution: abs x0 = " + alg.population.getFittest().getXi(1, 8)
                            + ", abs x1 = " + alg.population.getFittest().getXi(9, 16));

    }

    // find two fittest individuals
    void selection(){
        fittest = population.getFittest();
        secondFittest = population.getSecondFittest();
    }

    /*
    * randomly choose point for crossover
    * produce two children out of two parents
    * apply mutation and calculate new fitness of children
    * add children to new population
    */
    public void newChildren(Individual par1, Individual par2, Population newPop){
        Random rn = new Random();
        int crossPoint = rn.nextInt(population.inds.get(0).geneLength);
        Individual ch1 = new Individual();
        Individual ch2 = new Individual();
        for (int i = 0; i < crossPoint; i++) {
            ch1.genes.add(i, par1.genes.get(i));
            ch2.genes.add(i, par2.genes.get(i));
        }
        for(int i = crossPoint; i<fittest.geneLength; i++){
            ch1.genes.add(i, par2.genes.get(i));
            ch2.genes.add(i, par1.genes.get(i));
        }
        mutation(ch1);
        mutation(ch2);
        ch1.computeFitness();
        ch2.computeFitness();
        newPop.inds.add(ch1);
        newPop.inds.add(ch2);

    }

    //randomly choose cell in gene and change it
    void mutation(Individual in){
        Random rn = new Random();
        int ind = rn.nextInt(in.geneLength);
        if (in.genes.get(ind) == 0)
            in.genes.set(ind, 1);
        else
            in.genes.set(ind, 0);

    }


    void copy(Population pop1, Population pop2){
        for(int i = 0; i<pop1.popSize; i++){
            for (int j = 0; j<pop1.inds.get(0).geneLength; j++){
                pop1.inds.get(i).genes.set(j, pop2.inds.get(i).genes.get(j));
            }
        }

    }


    /*
    * produce new population, substitute the old one with the new one,
    * keeping the fittest individual for next generation (elitism)
    * randomly make mutation
    * compute new fitness
    */
    void crossover(){
        Individual parents[] = new Individual[2];
        Population temp = new Population();
        temp.popSize = 4;
        Random rn = new Random();
        Population newPop = new Population();
        newPop.popSize = 10;

        for(int k = 0; k<population.popSize-2; k++){
            for (int j = 0; j<2; j++){
                for(int i = 0; i < 4; i++){
                    temp.inds.add(population.inds.get(rn.nextInt(population.inds.size())));
                }
                parents[j] = temp.getFittest();
            }
            newChildren(parents[0], parents[1], newPop);
        }
        newPop.inds.add(population.getFittest());
        newPop.inds.add(population.getSecondFittest());
        copy(population, newPop);
        population.calculateFitness();

    }
}


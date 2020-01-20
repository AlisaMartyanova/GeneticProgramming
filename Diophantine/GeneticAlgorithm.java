import java.util.Random;


public class GeneticAlgorithm {
    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    int popSize = 10;
    int aCoef = 1, bCoef = 2, cCoef = 3, dCoef = 4, target = 40;
    Random rn = new Random();

    public static void main(String args[]){
        GeneticAlgorithm alg = new GeneticAlgorithm();
        alg.population.initPop(alg.popSize, alg.target);
        alg.population.computeFitness(alg.aCoef, alg.bCoef, alg.cCoef, alg.dCoef, alg.target);
        alg.population.getFittest().computeResult(alg.aCoef, alg.bCoef, alg.cCoef, alg.dCoef, alg.target);

        System.out.println("Generation: " + alg.generationCount);
        System.out.println(alg.aCoef + "*" + alg.population.getFittest().alleles[0] + " + " +
                alg.bCoef + "*" + alg.population.getFittest().alleles[1] + " + " +
                alg.cCoef + "*" + alg.population.getFittest().alleles[2] + " + " +
                alg.dCoef + "*" + alg.population.getFittest().alleles[3] + " = " +
                alg.population.getFittest().result);

        while (alg.population.fitness != 0){
            alg.generationCount ++;
            alg.selection();
            alg.population.getFittest().computeResult(alg.aCoef, alg.bCoef, alg.cCoef, alg.dCoef, alg.target);
            System.out.println("Generation: " + alg.generationCount);
            System.out.println("Best individual: " + alg.aCoef + "*" + alg.population.getFittest().alleles[0] + " + " +
                    alg.bCoef + "*" + alg.population.getFittest().alleles[1] + " + " +
                    alg.cCoef + "*" + alg.population.getFittest().alleles[2] + " + " +
                    alg.dCoef + "*" + alg.population.getFittest().alleles[3] + " = " +
                    alg.population.getFittest().result);
        }

        alg.population.getFittest().computeResult(alg.aCoef, alg.bCoef, alg.cCoef, alg.dCoef, alg.target);
        System.out.println("Result: a = " + alg.population.getFittest().alleles[0] +
                            ", b = " + alg.population.getFittest().alleles[1] +
                            ", c = " + alg.population.getFittest().alleles[2] +
                            ", d = " + alg.population.getFittest().alleles[3]);
        System.out.println(alg.aCoef + "*" + alg.population.getFittest().alleles[0] + " + " +
                           alg.bCoef + "*" + alg.population.getFittest().alleles[1] + " + " +
                           alg.cCoef + "*" + alg.population.getFittest().alleles[2] + " + " +
                           alg.dCoef + "*" + alg.population.getFittest().alleles[3] + " = " +
                           alg.population.getFittest().result);

    }

    public void selection(){
        fittest = population.getFittest();
        secondFittest = population.getSecondFittest();

        Individual parents[] = new Individual[2];
        Population temp = new Population();
        temp.popSize = 5;
        Population newPop = new Population();
        newPop.popSize = popSize;
        Individual bestTemp = new Individual(target);

        for(int k = 0; k<population.popSize-2; k++){
            for (int j = 0; j<2; j++){
                for(int i = 0; i < temp.popSize; i++){
                    temp.individuals.add(population.individuals.get(rn.nextInt(population.individuals.size())));
                }
                parents[j] = new Individual(target);
                copy(parents[j], temp.getFittest());
                if(j==0) {
                    bestTemp = temp.getFittest();
                    population.individuals.remove(bestTemp);    //not to choose the same individual twice
                }
            }
            crossover(parents[0], parents[1], newPop);
            population.individuals.add(bestTemp);
        }

        population = newPop;
        population.computeFitness(aCoef, bCoef, cCoef, dCoef, target);
    }

    private void copy(Individual par1, Individual par2){
        for(int i = 0; i<par1.geneLength; i++){
            par1.alleles[i] = par2.alleles[i];
        }
        par1.computeFitness(aCoef, bCoef, cCoef, dCoef, target);
    }

    public void crossover(Individual par1, Individual par2, Population newPop){
        int crossPoint = rn.nextInt(population.individuals.get(0).geneLength);
        Individual ch1 = new Individual(target);
        Individual ch2 = new Individual(target);
        for (int i = 0; i < crossPoint; i++) {
            ch1.alleles[i] = par1.alleles[i];
            ch2.alleles[i] = par2.alleles[i];
        }
        for(int i = crossPoint; i<fittest.geneLength; i++){
            ch1.alleles[i] = par2.alleles[i];
            ch2.alleles[i] = par1.alleles[i];
        }
        if(rn.nextInt(100)<40)
            mutation(ch1);
        if (rn.nextInt(100)<40)
            mutation(ch2);
        ch1.computeFitness(aCoef, bCoef, cCoef, dCoef, target);
        ch2.computeFitness(aCoef, bCoef, cCoef, dCoef, target);
        newPop.individuals.add(ch1);
        newPop.individuals.add(ch2);
    }

    public void mutation(Individual ind){
        int indx = rn.nextInt(ind.geneLength);
        ind.alleles[indx] = rn.nextInt(target)+1;
    }
}











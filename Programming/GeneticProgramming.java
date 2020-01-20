import java.util.Random;

public class GeneticProgramming {

    Population population = new Population();

    int target = 30;
    int genCount = 0;
    Random rn = new Random();
    int popSize = 50;

    public static void main(String[] args) {

        GeneticProgramming alg = new GeneticProgramming();
        alg.population.initPopulation(alg.popSize);
        alg.population.calculateFitness(alg.target);

        while (alg.population.fitness != 0 && alg.genCount<5000) {
            System.out.println("Generation: " + alg.genCount);
            alg.genCount++;

            alg.selection();

            alg.population.calculateFitness(alg.target);

            System.out.print("best individual: ");
            alg.population.getFittest().print(alg.population.getFittest().root);

            System.out.println(" = " + alg.population.getFittest().solution);
            System.out.println();
        }


        System.out.println();
        System.out.println("Target value: " + alg.target);
        System.out.print("The best found solution: ");
        alg.population.getFittest().print(alg.population.getFittest().root);
        System.out.println(" = " + alg.population.getFittest().root.computedValue);

        if(alg.population.getFittest().root.computedValue == alg.target)
            System.out.println("Successfully! :)");
        else
            System.out.println("Unsuccessfully :(");

        /*for(int j = 0; j<alg.population.popSize; j++) {
            Individual temp = alg.population.individuals.get(j);
            System.out.println(temp.geneLength());

            for (int i = 0; i < temp.geneLength(); i++) {
                System.out.println(i + ": " + temp.genes.get(i).value);
            }

            temp.print(temp.root);
            System.out.println(" root: " + temp.root.value);
            alg.mutation(temp);
            temp.print(temp.root);
            System.out.println(" root: " + temp.root.value);
        }*/

    }


    //choose random node for mutation and regenerate subtree from that node
    void mutation(Individual ind) {
        int indx = rn.nextInt(ind.genes.size());
        while (ind.genes.get(indx) == ind.root)
            indx = rn.nextInt(ind.genes.size());

        /*System.out.println("GeneSize: " + ind.genes.size());
        System.out.println("Index: " + indx);
        System.out.println("Value: " + ind.genes.get(indx).value);*/

        Node temp = ind.genes.get(indx);

        if (ind.genes.get(indx).parent.left == ind.genes.get(indx)) {
            ind.genes.get(indx).parent.left = ind.generate(ind.genes.get(indx).level, ind.genes.get(indx));
            ind.genes.get(indx).parent.left.parent = temp.parent;
        } else {
            ind.genes.get(indx).parent.right = ind.generate(ind.genes.get(indx).level, ind.genes.get(indx));
            ind.genes.get(indx).parent.right.parent = temp.parent;
        }

        ind.genes.clear();
        ind.makeList(ind.root);
        ind.geneLength();
        ind.computeFitness(target);
    }

    /*void mutation(Individual ind){
        int indx = rn.nextInt(ind.geneLength());
        if (ind.genes.get(indx).value<11)
            ind.genes.get(indx).value = rn.nextInt(10)+1;
        else if(ind.genes.get(indx).value<16)
            ind.genes.get(indx).value = rn.nextInt(5)+11;
    }*/



    /*
    * choose two parents, produce two children (crossover), add them into the new population
    * keep two best individuals for the next generation
    */
    void selection(){
        Individual parents[] = new Individual[2];
        Population temp = new Population();
        temp.popSize = 10;
        Population newPop = new Population();
        newPop.popSize = popSize;
        Individual bestTemp = new Individual();

        for(int k = 0; k<population.popSize-2; k++){
            for (int j = 0; j<2; j++){
                for(int i = 0; i < temp.popSize; i++){
                    temp.individuals.add(population.individuals.get(rn.nextInt(population.individuals.size())));
                }
                parents[j] = new Individual();
                copy(parents[j], temp.getFittest());
                if(j==0) {
                    bestTemp = temp.getFittest();
                    population.individuals.remove(bestTemp);    //not to choose the same individual twice
                }
            }
            crossover(parents[0], parents[1], newPop);
            population.individuals.add(bestTemp);
        }

        newPop.individuals.add(population.getFittest());
        newPop.individuals.add(population.getSecondFittest());
        newPop.calculateFitness(target);
        population = newPop;

    }

    /*
    * randomly choose point for crossover
    * produce two children out of two parents
    * apply mutation and calculate new fitness of children
    * add children to new population
    */
    void crossover (Individual par1, Individual par2, Population newPop){

        int crossPoint1 = rn.nextInt(par1.geneLength());
        while (par1.genes.get(crossPoint1).equals(par1.root))
            crossPoint1 = rn.nextInt(par1.geneLength());

        int crossPoint2 = rn.nextInt(par2.geneLength());
        while (par2.genes.get(crossPoint2).equals(par2.root))
            crossPoint2 = rn.nextInt(par2.geneLength());

        Individual ch1 = new Individual();
        Individual ch2 = new Individual();
        copy(ch1, par1);
        copy(ch2, par2);

        Node temp1 = ch1.genes.get(crossPoint1);
        Node temp2 = ch2.genes.get(crossPoint2);

        if(temp1.parent.left.equals(temp1))
            temp1.parent.left = temp2;
        else
            temp1.parent.right = temp2;

        if(temp2.parent.left.equals(temp2))
            temp2.parent.left = temp1;
        else
            temp2.parent.right = temp1;

        Node tmp = temp1.parent;
        temp1.parent = temp2.parent;
        temp2.parent = tmp;


        ch1.makeList(ch1.root);

        ch2.makeList(ch2.root);

        int mut = rn.nextInt(100);
        if (mut<40)
            mutation(ch1);
        mut = rn.nextInt(100);
        if (mut<40)
            mutation(ch2);

        ch1.computeFitness(target);
        ch2.computeFitness(target);
        newPop.individuals.add(ch1);
        newPop.individuals.add(ch2);
    }

    void copy(Individual ind1, Individual ind2){
        ind1.genes.clear();
        ind1.root = ind2.root;
        for (int i = 0; i<ind2.genes.size(); i++){
            ind1.genes.add(ind2.genes.get(i));
            ind1.genes.get(i).right = ind2.genes.get(i).right;
            ind1.genes.get(i).left = ind2.genes.get(i).left;
            ind1.genes.get(i).parent = ind2.genes.get(i).parent;
            ind1.genes.get(i).value = ind2.genes.get(i).value;
            ind1.genes.get(i).computedValue = ind2.genes.get(i).computedValue;
        }
        ind1.computeSolution();
        ind1.computeFitness(target);
    }

}

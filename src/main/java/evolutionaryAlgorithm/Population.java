package evolutionaryAlgorithm;

import java.util.*;

public class Population {
    private List<Individual> individuals;
    private static Random random = new Random();
    private Individual fittest;

    public Population(List<Individual> individuals) {
        this.individuals = individuals;
        Collections.sort(individuals);
    }

    public Population() {
        this.individuals= new ArrayList<>();
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }


    public Individual getFittest() {
        return individuals.get(0);
    }

    public int size() {
        return individuals.size();
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
        Collections.sort(individuals);
    }

    public Individual popFittest() {
        Individual individual = new Individual(individuals.get(0));
        individuals.remove(0);
        return  individual;
    }

    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
    }

    public Individual getRandomIndividual() {
        int index = random.nextInt();
        return getIndividual(index);
    }

}

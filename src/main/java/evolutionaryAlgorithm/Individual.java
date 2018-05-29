package evolutionaryAlgorithm;

import model.FetalState;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Individual implements Comparable<Individual> {
    private List<Double> genotype;
    private Double fitness = 0.0;
    private Sigmoid sigmoid = new Sigmoid();



    public Individual(List<Double> genotype) {
        this.genotype = genotype;
    }

    public Individual(Individual other) {
        this.genotype = other.getGenotype();
        this.fitness = other.getFitness();
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void setGenotype(List<Double> genotype) {
        this.genotype = genotype;
    }

    public Individual() {
        this.genotype = new ArrayList<>();
    }

    public List<Double> getGenotype() {
        return genotype;
    }

    public void evaluate(double trainInData[][], FetalState trainOutData[]) {
        int size = trainInData.length;
        for (int i=0;i<size;i++) {
            Double computedOutput = 0.0;
            int length = genotype.size();
            for (int j=0;j<length-1;j++) {
                Double value = genotype.get(j);
                computedOutput += value*trainInData[i][j];
            }
            if (genotype.size()>0)
                computedOutput += genotype.get(genotype.size()-1);
            Double difference = trainOutData[i].Value - sigmoid.value(computedOutput);
            int diff = difference.intValue();
            this.fitness += diff^2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        if (genotype != null ? !genotype.equals(that.genotype) : that.genotype != null) return false;
        return fitness != null ? fitness.equals(that.fitness) : that.fitness == null;
    }

    @Override
    public int hashCode() {
        int result = genotype != null ? genotype.hashCode() : 0;
        result = 31 * result + (fitness != null ? fitness.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "genotype=" + genotype +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.fitness,o.getFitness());
    }
}

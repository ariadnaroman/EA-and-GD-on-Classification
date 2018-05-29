package UI;

import evolutionaryAlgorithm.EvolutionaryAlgorithm;
import gradientDescent.GradientDescent;
import model.FetalState;
import repository.FileRepository;

import java.util.Scanner;

public class UI {
    private FileRepository fileRepository;
    private double[][] trainInData;
    private FetalState[] trainOutData;
    private int n;
    private int m;
    private double accuracy;

    public UI() {
        this.fileRepository = new FileRepository("data.csv");
        this.trainInData = fileRepository.getTrainInData();
        this.trainOutData = fileRepository.getTrainOutData();
        this.n = fileRepository.getN();
        this.m = fileRepository.getM()-4;
    }

    public void show() {
        while (true){
            System.out.println("Choose one of the methods: EA/GD");
            Scanner scanner = new Scanner(System.in);
            String method = scanner.next();
            switch (method) {
                case "EA":
                    EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(trainInData,trainOutData,n,m);
                    System.out.println("Insert the number of generations: ");
                    int generationsNo = scanner.nextInt();
                    evolutionaryAlgorithm.setNrGenerations(generationsNo);
                    System.out.println("Insert the size of the population: ");
                    int popSize = scanner.nextInt();
                    evolutionaryAlgorithm.setPopSize(popSize);
                    accuracy = evolutionaryAlgorithm.accuracy();
                    System.out.println("The accuracy for this method is: " + accuracy);
                    break;
                case "GD":
                    GradientDescent gradientDescent = new GradientDescent(trainInData,trainOutData,n,m);
                    System.out.println("Insert the number of iterations: ");
                    int iterNo = scanner.nextInt();
                    gradientDescent.setNoIter(iterNo);
                    System.out.println("Insert the learning rate: ");
                    Double learningRate = scanner.nextDouble();
                    gradientDescent.setLearningRate(learningRate);
                    accuracy = gradientDescent.myLogisticRegression();
                    System.out.println("The accuracy for this method is: " + accuracy);
                    break;
            }
        }
    }
}

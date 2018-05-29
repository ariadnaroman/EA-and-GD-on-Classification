package gradientDescent;

import model.FetalState;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.Random;

public class GradientDescent {
    double[] coefficients;
    int noData;
    int noAttributes;
    private double[][] trainInData;
    private double[][] testInData;
    private FetalState[] trainOutData;
    private FetalState[] testOutData;
    private int indexTestStart;
    private double learningRate = 0.6;
    private int noIter = 1000;
    private Random random;
    Sigmoid sigmoid = new Sigmoid();

    public GradientDescent(double[][] trainInData, FetalState[] trainOutData, int n, int m) {
        this.indexTestStart = 80 * n / 100;
        this.noData = n;
        this.noAttributes = m;
        this.trainInData = new double[indexTestStart][m];
        this.trainOutData = new FetalState[indexTestStart];
        this.testInData = new double[n - indexTestStart][m];
        this.testOutData = new FetalState[n - indexTestStart];
        for (int i = 0; i < indexTestStart; i++) {
            for (int j = 0; j < m; j++) {
                this.trainInData[i][j] = trainInData[i][j];
            }
            this.trainOutData[i] = trainOutData[i];
        }
        for (int i = indexTestStart; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.testInData[i - indexTestStart][j] = trainInData[i][j];
            }
            this.testOutData[i - indexTestStart] = trainOutData[i];
        }
        this.random = new Random();
        this.coefficients = new double[m];
    }

    public GradientDescent() {
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public int getNoData() {
        return noData;
    }

    public void setNoData(int noData) {
        this.noData = noData;
    }

    public int getNoAttributes() {
        return noAttributes;
    }

    public void setNoAttributes(int noAttributes) {
        this.noAttributes = noAttributes;
    }

    public double[][] getTrainInData() {
        return trainInData;
    }

    public void setTrainInData(double[][] trainInData) {
        this.trainInData = trainInData;
    }

    public double[][] getTestInData() {
        return testInData;
    }

    public void setTestInData(double[][] testInData) {
        this.testInData = testInData;
    }

    public FetalState[] getTrainOutData() {
        return trainOutData;
    }

    public void setTrainOutData(FetalState[] trainOutData) {
        this.trainOutData = trainOutData;
    }

    public FetalState[] getTestOutData() {
        return testOutData;
    }

    public void setTestOutData(FetalState[] testOutData) {
        this.testOutData = testOutData;
    }

    public int getIndexTestStart() {
        return indexTestStart;
    }

    public void setIndexTestStart(int indexTestStart) {
        this.indexTestStart = indexTestStart;
    }

    public Double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    private double prediction(double[] example) {
        double s = 0.0;
        for (int i = 0; i < example.length; i++) {
            s += coefficients[i] * example[i];
        }
        return s;
    }

    private double sigmoidFunction(double z) {
        return sigmoid.value(z);
    }

    private double costFunction() {
        double totalCost = 0.0;
        for (int i = 0; i < indexTestStart; i++) {
            double[] example = trainInData[i];
            double prediction = prediction(example);
            double predictedValue = sigmoidFunction(prediction);
            FetalState realLabel = trainOutData[i];
            double class1_cost = realLabel.Value * Math.log(predictedValue);
            double class2_cost = (1 - realLabel.Value) * Math.log(1 - predictedValue);
            double crtCost = class1_cost - class2_cost;
            totalCost += crtCost;
        }
        System.out.println(totalCost);
        System.out.println(totalCost/indexTestStart);
        return totalCost / indexTestStart;
    }

    private void updateCoefs() {
        double[] predictedValues = new double[indexTestStart];
        FetalState[] realLabels = new FetalState[indexTestStart];
        for (int i = 0; i < indexTestStart; i++) {
            double[] crtExample = trainInData[i];
            predictedValues[i] = sigmoidFunction(prediction(crtExample));
            realLabels[i] = trainOutData[i];
        }
        for (int j = 0; j < noAttributes; j++) {
            double gradient = 0.0;
            for (int i = 0; i < indexTestStart; i++) {
                double[] crtExample = trainInData[i];
                gradient += crtExample[j] * (predictedValues[i] - realLabels[i].Value);
            }
            coefficients[j] = coefficients[j] - gradient * learningRate;
        }
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getNoIter() {
        return noIter;
    }

    public void setNoIter(int noIter) {
        this.noIter = noIter;
    }

    private void train() {
        for (int i = 0; i < noAttributes; i++) {
            coefficients[i] = random.nextDouble();
        }
        for (int it = 0; it < noIter; it++) {
            updateCoefs();
        }
    }

    private FetalState[] test() {
        FetalState[] predictedLabels = new FetalState[testInData.length];
        for (int i=0;i<noData-indexTestStart;i++) {
            double predictedValue = sigmoidFunction(prediction(testInData[i]));
            if (predictedValue < 0.33) predictedLabels[i] = FetalState.Normal;
            else if (predictedValue < 0.66) predictedLabels[i] = FetalState.Suspect;
            else predictedLabels[i] = FetalState.Patologic;
        }
        return predictedLabels;
    }

    public double accuracy(FetalState[] computedLabels) {
        double noMatches = 0.0;
        for (int i=0;i<noData-indexTestStart;i++) {
            if (computedLabels[i].equals(testOutData[i])) {
                noMatches += 1;
            }
        }
        double nData = noData;
        return  noMatches/(nData-indexTestStart);
    }

    public double myLogisticRegression() {
        train();
        FetalState[] computedLabels = test();
        return accuracy(computedLabels);
    }


}

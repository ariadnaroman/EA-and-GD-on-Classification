package repository;

import model.FetalState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileRepository {
    private double[][] trainInData;
    private FetalState[] trainOutData;
    private int n;
    private int m;
    private String filename;

    public FileRepository(String filename) {
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile() {
        String line;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            if (line != null) {
                n = Integer.parseInt(line.trim());
            }
            line = bufferedReader.readLine();
            if (line != null) {
                m = Integer.parseInt(line.trim());
            }

            trainInData = new double[n][m];
            trainOutData = new FetalState[n];

            for (int i=0;i<n;i++) {
                line = bufferedReader.readLine();
                String elems[] = line.split(",");
                for (int j=3;j<m;j++) {
                    if (j != m - 1) {
                        double value = Double.parseDouble(elems[j]);
                        trainInData[i][j-3] = value;
                    } else {
                        double d = (Integer.parseInt(elems[j])-1)/2.0;
                        FetalState fetalState = FetalState.valueOf(d);
                        trainOutData[i] = fetalState;
                    }
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }
        double medie = 0.0;
        double stddev = 0.0;
        double tmp=0.0;
        for (int i=0;i<m;i++){
            medie = 0.0;

            for(int j=0;j<n;j++){
                medie += trainInData[j][i];
            }
            medie /=n;
            stddev=0.0;
            for(int j=0;j<n;j++){
                tmp = (trainInData[j][i]-medie);

                stddev += tmp*tmp  ;
            }


            stddev = stddev/(n-1);
            stddev = Math.sqrt(stddev);
            if (stddev>0){
                for(int j=0;j<n;j++) {
                    trainInData[j][i] = (trainInData[j][i] - medie)/stddev;
                }
            }
        }

    }

    public double[][] getTrainInData() {
        return trainInData;
    }

    public void setTrainInData(double[][] trainInData) {
        this.trainInData = trainInData;
    }

    public FetalState[] getTrainOutData() {
        return trainOutData;
    }

    public void setTrainOutData(FetalState[] trainOutData) {
        this.trainOutData = trainOutData;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}

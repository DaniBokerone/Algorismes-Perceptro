package com.project;

import java.util.ArrayList;  
import java.util.List;

public class Main {
    // Provar per diferents EPOCHS: 1, 2, ... 10
    private static final int EPOCHS = 20;

    public static void main(String[] args) {

        // Crea una instància d'un perceptró
        // inputSize = 9 que son els bits 0/1 de l'entrada
        // activationType = "relu" que és fer una suma simple de cada entrada pel seu pes
        // learningRate = 0.1 que defineix el canvi dels pesos durant l'entrenament, en cas d'error/encert
        Perceptron perceptroDiagonal = new Perceptron(9, "relu", 0.1);
        Perceptron perceptroVertical = new Perceptron(9, "relu", 0.1);
        Perceptron perceptroHoritzontal = new Perceptron(9, "relu", 0.1);

        //Generar els labels que considerarem correctes
        List<int[]> allMatrices = generateAllMatrices();

        int[] labelsDiagonal = labelDiagonal(allMatrices);
        int[] labelsVertical = labelVertical(allMatrices);
        int[] labelsHoritzontal = labelHoritzontal(allMatrices);

        // Fes l'entrenament del perceptró
        System.out.println("Resultats d'entrenament de perceptrons: ");

        int[][] inputData = allMatrices.toArray(new int[0][0]);

        //Entrenaments y percentatged d'acert
        perceptroDiagonal.train(inputData, labelsDiagonal, EPOCHS);
        double accuracyDiagonal = perceptroDiagonal.testAccuracy(inputData, labelsDiagonal);

        perceptroVertical.train(inputData, labelsVertical, EPOCHS);
        double accuracyVertical = perceptroVertical.testAccuracy(inputData, labelsVertical);

        perceptroHoritzontal.train(inputData, labelsHoritzontal, EPOCHS);
        double accuracyHorizontal = perceptroHoritzontal.testAccuracy(inputData, labelsHoritzontal);


        //Resposta entrenament:
        System.out.println();
        System.out.println("-".repeat(70));
        System.out.println("Percentatge d'encert del Perceptró Diagonal amb " + EPOCHS + " EPOCHS: " + accuracyDiagonal + "%\n");
        //EPOCHS minims per al 50% de fiabilitat -> 1
        //EPOCHS minims per al 80% de fiabilitat -> 2

        System.out.println("Percentatge d'encert del Perceptró Vertical amb " + EPOCHS + " EPOCHS: " + accuracyVertical + "%\n");
        //EPOCHS minims per al 50% de fiabilitat -> 1
        //EPOCHS minims per al 80% de fiabilitat -> ?(Per molts intents que posi no millora)
        
        System.out.println("Percentatge d'encert del Perceptró Horitzontal amb " + EPOCHS + " EPOCHS: " + accuracyHorizontal + "%\n");
        //EPOCHS minims per al 50% de fiabilitat -> 1
        //EPOCHS minims per al 80% de fiabilitat -> ?(Per molts intents que posi no millora)

        System.out.println("-".repeat(70));

    }

    public static List<int[]> generateAllMatrices() {
        List<int[]> matrices = new ArrayList<>();
        
        // Hi ha 2^9 combinacions possibles
        for (int i = 0; i < 512; i++) {
            int[] matrix = new int[9];
            
            // Convertir l'índex `i` a una combinació binària de 9 bits
            String binary = String.format("%9s", Integer.toBinaryString(i)).replace(' ', '0');
            
            // Omplir l'array amb els bits corresponents
            for (int j = 0; j < 9; j++) {
                matrix[j] = binary.charAt(j) - '0';
            }
            
            matrices.add(matrix);
        }
        
        return matrices;
    }


    /** Generar */
    public static int[] labelDiagonal(List<int[]> matrices) {
        int[] labels = new int[matrices.size()];
        for (int i = 0; i < matrices.size(); i++) {
            int[] matrix = matrices.get(i);
            labels[i] = (matrix[0] == 1 && matrix[4] == 1 && matrix[8] == 1) || 
                        (matrix[2] == 1 && matrix[4] == 1 && matrix[6] == 1) ? 1 : 0;
        }
        return labels;
    }

    public static int[] labelVertical(List<int[]> matrices) {
        int[] labels = new int[matrices.size()];
        for (int i = 0; i < matrices.size(); i++) {
            int[] matrix = matrices.get(i);
            labels[i] = (matrix[0] == 1 && matrix[3] == 1 && matrix[6] == 1) || 
                        (matrix[1] == 1 && matrix[4] == 1 && matrix[7] == 1) || 
                        (matrix[2] == 1 && matrix[5] == 1 && matrix[8] == 1) ? 1 : 0;
        }
        return labels;
    }

    public static int[] labelHoritzontal(List<int[]> matrices) {
        int[] labels = new int[matrices.size()];
        for (int i = 0; i < matrices.size(); i++) {
            int[] matrix = matrices.get(i);
            labels[i] = (matrix[0] == 1 && matrix[1] == 1 && matrix[2] == 1) || 
                        (matrix[3] == 1 && matrix[4] == 1 && matrix[5] == 1) || 
                        (matrix[6] == 1 && matrix[7] == 1 && matrix[8] == 1) ? 1 : 0;
        }
        return labels;
    }
}
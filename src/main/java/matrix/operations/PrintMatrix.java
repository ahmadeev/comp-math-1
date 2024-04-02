package matrix.operations;

import utils.Result;

import static java.util.Objects.isNull;
import static matrix.operations.MatrixUtility.getResultFromExtendedMatrix;

public class PrintMatrix {
    public static void printMatrix(String msg, double[][] matrixA, double[] matrixB) {

        if (isNull(matrixA) && isNull(matrixB)) {
            System.out.println("Матрица и матричное дополнение не были заданы!");
            System.exit(1);
        } else if (isNull(matrixA)) {
            System.out.println("Матрица не была задана!");
            System.exit(1);
        } else if (isNull(matrixB)) {
            System.out.println("Матричное дополнение не было задано!");
            System.exit(1);
        }

        System.out.println(msg);
        for (int i = 0; i < matrixA.length; i++) {
            for (double j : matrixA[i]){
                System.out.printf("%8.2f", j);
            }
            System.out.println(" | " + String.format("%8.2f", matrixB[i]));
        }
        System.out.println();

    }

    public static void printMatrix(String msg, Result result) {
        double[][] matrixA = result.matrix();
        double[] matrixB = result.matrixExtension();
        printMatrix(msg, matrixA, matrixB);
    }

    public static void printMatrix(String msg, double[][] extendedMatrix) {
        Result result = getResultFromExtendedMatrix(extendedMatrix);
        printMatrix(msg, result);
    }
}

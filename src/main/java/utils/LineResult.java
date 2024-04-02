package utils;

import java.util.Arrays;

public class LineResult {
/*
    private double[][] extendedMatrix;
    private int size;

    public LineResult(double[][] matrix, double[] matrixExtension) {

        this.size = matrixExtension.length;
        this.extendedMatrix = new double[size][size + 1];

        for (int i = 0; i < size; i++) {
            extendedMatrix[i] = Arrays.copyOf(matrix[i], size + 1);
            extendedMatrix[i][size] = matrixExtension[i];
        }
    }

    public Result getResult(double[][] extendedMatrix) {
        double[][] matrix = new double[size][size];
        double[] matrixExtension = new double[size];
        for (int i = 0; i < size; i++) {
            matrix[i] = Arrays.copyOf(extendedMatrix[i], size);
            matrixExtension[i] = extendedMatrix[i][size];
        }
        return new Result(matrix, matrixExtension);
    }

    public double[][] getMatrix() {
        return getResult(extendedMatrix).getMatrix();
    }

    public double[] getMatrixExtension() {
        return getResult(extendedMatrix).getMatrixExtension();
    }

    public double[][] getExtendedMatrix() {
        return extendedMatrix;
    }*/
}

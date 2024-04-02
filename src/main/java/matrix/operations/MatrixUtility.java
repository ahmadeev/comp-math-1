package matrix.operations;

import utils.Result;

import java.util.Arrays;

public class MatrixUtility {
    public static double[][] matrixToTriangle(double[][] matrix, double[] matrixExtension) {
        int size = matrixExtension.length;
        double[][] extendedMatrix = getExtendedMatrix(matrix, matrixExtension);

        for (int i = 0; i < size; i++) {
            if (extendedMatrix[i][i] != 0 && extendedMatrix[i][i] != 1) {
                extendedMatrix[i] = mul(extendedMatrix, 1 / extendedMatrix[i][i], i);
            } else if (extendedMatrix[i][i] == 0) {
                extendedMatrix = sortArray(extendedMatrix);
            }
            for (int k = i + 1; k < size; k++) {
                double[] multipliedLine = mul(
                        extendedMatrix,
                        findCoefficient(extendedMatrix[i][i], extendedMatrix[k][i]),
                        i);
                extendedMatrix[k] = sum(extendedMatrix, multipliedLine, k);
                switch (checkIfSolutionExists(extendedMatrix, k)) {
                    case (-1): {
                        System.out.println("Система не имеет решений!");
                        System.exit(0);
                        break;
                    }
                    case 1: {
                        System.out.println("Система имеет бесконечно много решений!");
                        System.exit(0);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
        return extendedMatrix;
    }

    public static boolean isNoZeroColumns(double[][] matrix) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            boolean flag = false;
            for (double[] row : matrix) {
                if (row[i] != 0) {flag = true; break;}
            }
            if (flag) {continue;}
            return false;
        }
        return true;
    }

    public static boolean isNoZeroRows(double[][] matrix) {
        int size = matrix.length;
        for (double[] row : matrix) {
            boolean flag = false;
            for (int i = 0; i < size; i++) {
                if (row[i] != 0) {flag = true; break;}
            }
            if (flag) {continue;}
            return false;
        }
        return true;
    }

    public static double findDeterminant(double[][] matrix, double[] matrixExtension) {
        if (!isNoZeroColumns(matrix) || !isNoZeroRows(matrix)) {
            return 0;
        } else {
            double[][] extendedMatrix = matrixToTriangle(matrix, matrixExtension);
            int size = extendedMatrix.length;
            double determinant = extendedMatrix[0][0];
            for (int i = 1; i < size; i++) {
                determinant *= extendedMatrix[i][i];
            }
            return determinant;
        }
    }

    public static double[] mul(double[][] extendedMatrix, double coefficient, int linePosition) {
        int subsize = extendedMatrix[0].length;
        double[] result = new double[subsize];

        for (int i = 0; i < subsize; i++) {
            result[i] = extendedMatrix[linePosition][i] * coefficient;
            if (result[i] == -0.0) result[i] = 0;
        }
        return result;
    }

    public static double[] sum(double[][] extendedMatrix, int firstLinePosition, int secondLinePosition) {
        int subsize = extendedMatrix[0].length;
        double[] result = new double[subsize];

        for (int i = 0; i < subsize; i++) {
            result[i] = extendedMatrix[firstLinePosition][i] + extendedMatrix[secondLinePosition][i];
            if (result[i] == -0.0) result[i] = 0;
        }

        return result;
    }

    public static double[] sum(double[][] extendedMatrix, double[] line, int secondLinePosition) {
        int subsize = extendedMatrix[0].length;
        double[] result = new double[subsize];

        for (int i = 0; i < subsize; i++) {
            result[i] = line[i] + extendedMatrix[secondLinePosition][i];
            if (result[i] == -0.0) result[i] = 0;
        }

        return result;
    }

    public static double findCoefficient(double a, double b) {
        if (a == 0.0d) return 1.0d;
        return -b/a;
    }

    public static double[][] getAnswer(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                double[] multipliedLine = mul(
                        extendedMatrix,
                        findCoefficient(extendedMatrix[i][i], extendedMatrix[j][i]),
                        i);
                extendedMatrix[j] = sum(extendedMatrix, multipliedLine, j);

            }
        }
        return extendedMatrix;
    }

    public static int checkIfSolutionExists(double[][] extendedMatrix, int linePosition) {
        int size = extendedMatrix.length;
        for (int i = 0; i < size; i++) {
            if (extendedMatrix[linePosition][i] != 0) {
                return 0;
            }
        }
        if (extendedMatrix[linePosition][size] == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static double[][] getExtendedMatrix(double[][] matrix, double[] matrixExtension) {
        int size = matrixExtension.length;
        double[][] extendedMatrix = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            extendedMatrix[i] = Arrays.copyOf(matrix[i], size + 1);
            extendedMatrix[i][size] = matrixExtension[i];
        }
        return extendedMatrix;
    }

    public static Result getResultFromExtendedMatrix(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        double[][] matrix = new double[size][size];
        double[] matrixExtension = new double[size];
        for (int i = 0; i < size; i++) {
            matrix[i] = Arrays.copyOf(extendedMatrix[i], size);
            matrixExtension[i] = extendedMatrix[i][size];
        }
        return new Result(matrix, matrixExtension);
    }

    public static double[][] sortArray(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        int[] counters = new int[size];

        for (int i = 0; i < size; i++) {
            int zeroCounter = 0;
            for (int j = 0; j < size && extendedMatrix[i][j] == 0; j++) {
                zeroCounter++;
            }
            counters[i] = zeroCounter;
        }

        for (int i = 1; i < size; i++) {
            if (counters[i-1] > counters[i]) {
                int tempInt = counters[i-1];
                counters[i-1] = counters[i];
                counters[i] = tempInt;

                double[] tempArrDbl = extendedMatrix[i-1];
                extendedMatrix[i-1] = extendedMatrix[i];
                extendedMatrix[i] = tempArrDbl;
            }
        }

        for (int i = size - 1; i > 0; i--) {
            if (counters[i-1] > counters[i]) {
                int tempInt = counters[i-1];
                counters[i-1] = counters[i];
                counters[i] = tempInt;

                double[] tempArrDbl = extendedMatrix[i-1];
                extendedMatrix[i-1] = extendedMatrix[i];
                extendedMatrix[i] = tempArrDbl;
            }
        }
        return extendedMatrix;
    }
}

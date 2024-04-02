package utils;

import Exceptions.InvalidArraySizeException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.IOUtils;

import static java.util.Objects.isNull;

public class Matrix {
    public static double[][] setMatrixManually() {
        Scanner input = new Scanner(System.in);

        int n;
        double[][] matrix;

        System.out.println("Введите размер матрицы: ");
        try {
            n = input.nextInt();
            if (n <= 0) throw new InvalidArraySizeException("Введенная строка не может быть размером матрицы! Требуется целое положительное число.");
        } catch (InputMismatchException e) {
            System.out.println("Введенная строка не может быть размером матрицы! Требуется число.");
            input.close();
            return null;
        } catch (NoSuchElementException | IllegalStateException | InvalidArraySizeException e) {
            System.out.println(e.getMessage());
            input.close();
            return null;
        }

        try {
            matrix = new double[n][n];
        } catch (NegativeArraySizeException e) {
            System.out.println(e.getMessage());
            input.close();
            return null;
        }

        System.out.println("Введите исходную матрицу: ");
        for (int i = 0; i < n; i++)
        {
            System.out.println("Строка "+ (i + 1) +" (" + n + " чисел)");
            for (int j = 0; j < n; j++) {
                try {
                    matrix[i][j] = input.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Введенная строка не является числом!");
                    input.close();
                    return null;
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.println(e.getMessage());
                    input.close();
                    return null;
                }
            }
        }

        System.out.println("Конец ввода матрицы.\n");
        return matrix;
    }

    public static double[] setMatrixExtensionManually(double[][] matrix) {
        if (isNull(matrix)) {
            System.out.println("Матрица не была задана!");
            System.exit(1);
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Введите дополнение: ");
        double[] matrixExtension = new double[matrix.length];
        for (int i = 0; i < matrixExtension.length; i++) {
            try {
                double n = input.nextDouble();
                matrixExtension[i] = n;
            } catch (InputMismatchException e) {
                System.out.println("Введенная строка не является числом!");
                input.close();
                return null;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println(e.getMessage());
                input.close();
                return null;
            }
        }
        input.close();
        System.out.println("Конец ввода матричного дополнения.\n");
        return matrixExtension;
    }

    public static Result setViaFile() {
        Scanner input = new Scanner(System.in);
        String pathExample = "C:\\Users\\danis\\OneDrive\\Рабочий стол\\untitled\\src\\main\\resources\\file.txt";
        System.out.println("Введите путь к файлу в формате\n" + pathExample);
        String path = input.nextLine();
        try(FileInputStream reader = new FileInputStream(path)) {

            String everything = IOUtils.toString(reader);
            String[] modded = everything.split("\n");
            int size = modded.length;
            String[][] modded2 = new String[size][0];

            double[][] matrix = new double[size][size];
            double[] matrixExtension = new double[size];

            for(int i = 0; i < size; i++) {
                modded2[i] = modded[i].trim().split(" ");
            }
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    matrix[i][j] = Double.parseDouble(modded2[i][j]);
                }
                matrixExtension[i] = Double.parseDouble(modded2[i][size]);
            }
            reader.close();
            return new Result(matrix, matrixExtension);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new Result(null, null);
        }
    }

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
        double[][] matrixA = result.getMatrix();
        double[] matrixB = result.getMatrixExtension();
        printMatrix(msg, matrixA, matrixB);
    }

    public static void printMatrix(String msg, double[][] extendedMatrix) {
        Result result = getResultFromExtendedMatrix(extendedMatrix);
        printMatrix(msg, result);
    }

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

        //printMatrix(getResultFromExtendedMatrix(extendedMatrix).getMatrix(), getResultFromExtendedMatrix(extendedMatrix).getMatrixExtension());

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

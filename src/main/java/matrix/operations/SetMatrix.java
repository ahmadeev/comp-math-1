package matrix.operations;

import exceptions.InvalidArraySizeException;
import org.apache.commons.io.IOUtils;
import utils.Result;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class SetMatrix {
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
}

package utils;

import Exceptions.InvalidArraySizeException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class RandomNumberGenerator {

    public static class RandomNumber {
        private boolean sign;
        private int intUnit;
        private double floatUnit;
        private double number;

        Random rng = new Random();
        public RandomNumber() {
            this.sign = rng.nextBoolean();
            this.intUnit = rng.nextInt(999);
            this.floatUnit = rng.nextDouble();

            this.number = intUnit + floatUnit;
            number = (sign ? number : -number);
        }

        public double getNumber() {
            return number;
        }
    }

    public static Result setRandomMatrix() {
        Scanner input = new Scanner(System.in);

        int n;
        double[][] matrix;
        double[] matrixExtension;

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
            matrixExtension = new double[n];
        } catch (NegativeArraySizeException e) {
            System.out.println(e.getMessage());
            input.close();
            return null;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (new RandomNumber()).getNumber();
            }
        }

        for (int i = 0; i < n; i++) {
            matrixExtension[i] = (new RandomNumber()).getNumber();
        }

        return new Result(matrix, matrixExtension);
    }

}

package org.example;

import java.util.Scanner;

import utils.Matrix;

import static java.util.Objects.isNull;
import static utils.Matrix.*;

public class Main {
    public static void main(String[] args) {
        double[][] matrixA;
        double[] matrixB;

        matrixA = setMatrix();
        matrixB = setMatrixExtension(matrixA);

/*        double[][] matrixA = {{1, 2}, {3, 4}};
        double[] matrixB = {5, 6};*/

/*        double[][] matrixA = {{2, 5}, {6, 7}};
        double[] matrixB = {5, 6};*/

/*        double[][] matrixA = {{1, 5}, {0, 0}};
        double[] matrixB = {5, 6};*/

        printMatrix(matrixA, matrixB);


        //System.out.println(isNoZeroColumns(matrixA));
        //System.out.println(isNoZeroRows(matrixA));

        //matrixToTriangle(matrixA, matrixB);
        System.exit(0);
    }
}
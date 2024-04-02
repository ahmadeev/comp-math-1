import utils.Messages;
import utils.Result;

import static matrix.operations.SetMatrix.*;
import static matrix.operations.PrintMatrix.*;
import static matrix.operations.MatrixUtility.*;
import static utils.InputModeSwitch.*;
import static utils.RandomNumberGenerator.*;

public class Main {
    public static void main(String[] args) {

        int inputMode = getInputMode();

        double[][] matrixA = null;
        double[] matrixB = null;
        double[][] extendedMatrix;
        double determinant;

        if (inputMode == 1) {
            matrixA = setMatrixManually();
            matrixB = setMatrixExtensionManually(matrixA);
        } else if (inputMode == 2) {
            Result result = setViaFile();
            matrixA = result.matrix();
            matrixB = result.matrixExtension();
        } else if (inputMode == 3) {
            Result result = setRandomMatrix();
            matrixA = result.matrix();
            matrixB = result.matrixExtension();
        } else {
            System.out.println("Режим ввода не был выбран! (Ошибка при вводе или такого режима не существует).");
            System.exit(1);
        }

        printMatrix(Messages.MATRIX_OUTPUT_INFO_1.getMsg(), matrixA, matrixB);

        determinant = findDeterminant(matrixA);
        System.out.printf("Определитель матрицы: %.2f\n", determinant);

        if (determinant == 0) {
            System.out.println("Определитель равен нулю! Система несовместна.");
            System.exit(1);
        }

        extendedMatrix = matrixToTriangle(matrixA, matrixB);
        Result packed = getResultFromExtendedMatrix(extendedMatrix);

        printMatrix(Messages.MATRIX_OUTPUT_INFO_2.getMsg(), packed.matrix(), packed.matrixExtension());

        //  не пишем extendedMatrix = ..., потому что метод изменяет переданный объект
        getAnswer(extendedMatrix);
        packed = getResultFromExtendedMatrix(extendedMatrix);
        printMatrix(Messages.MATRIX_OUTPUT_INFO_3.getMsg(), packed.matrix(), packed.matrixExtension());

/*        System.out.println(extendedMatrix[4][4]);
        System.out.printf("%7.15f", extendedMatrix[4][4]);*/

        System.exit(0);
    }
}
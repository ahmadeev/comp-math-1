import utils.LineResult;
import utils.Messages;
import utils.Result;

import static utils.Matrix.*;
import static utils.InputModeSwitch.*;
import static utils.Messages.MATRIX_OUTPUT_INFO_3;

public class Main {
    public static void main(String[] args) {

        int inputMode = getInputMode();

        double[][] matrixA = null;
        double[] matrixB = null;
        double[][] extendedMatrix;

        if (inputMode == 1) {
            matrixA = setMatrixManually();
            matrixB = setMatrixExtensionManually(matrixA);
        } else if (inputMode == 2) {
            Result result = setViaFile();
            matrixA = result.getMatrix();
            matrixB = result.getMatrixExtension();
        } else {
            System.out.println("Режим ввода не был выбран! (Ошибка при вводе или такого режима не существует).");
            System.exit(1);
        }

        printMatrix(Messages.MATRIX_OUTPUT_INFO_1.getMsg(), matrixA, matrixB);

        extendedMatrix = matrixToTriangle(matrixA, matrixB);
        Result packed = getResultFromExtendedMatrix(extendedMatrix);

        printMatrix(Messages.MATRIX_OUTPUT_INFO_2.getMsg(), packed.getMatrix(), packed.getMatrixExtension());

        extendedMatrix = getAnswer(extendedMatrix);
        packed = getResultFromExtendedMatrix(extendedMatrix);
        printMatrix(Messages.MATRIX_OUTPUT_INFO_3.getMsg(), packed.getMatrix(), packed.getMatrixExtension());


/*        LineResult result = new LineResult(matrixA, matrixB);
        matrixA = result.getMatrix();
        matrixB = result.getMatrixExtension();
        printMatrix(matrixA, matrixB);*/

        System.exit(0);
    }
}
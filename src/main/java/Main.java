import utils.Result;

import static utils.Matrix.*;
import static utils.InputModeSwitch.*;

public class Main {
    public static void main(String[] args) {
        int inputMode = getInputMode();

        double[][] matrixA = null;
        double[] matrixB = null;

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
package utils;

import exceptions.InvalidInputModeException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputModeSwitch {
    public static int getInputMode() {
        Scanner input = new Scanner(System.in);
        System.out.println("""
                Выберите источник ввода:
                1 -- клавиатура,
                2 -- файл,
                3 -- случайная генерация
                """);

        int inputMode;
        try {
            inputMode = input.nextInt();
            if (!(inputMode == 1 | inputMode == 2 | inputMode == 3)) {
                throw new InvalidInputModeException("Такого режима ввода не существует!");
            }
            return inputMode;
        } catch (InputMismatchException e) {
            System.out.println("Введенная строка не может быть режимом ввода!");
            input.close();
            return -1;
        } catch (NoSuchElementException | IllegalStateException | InvalidInputModeException e) {
            System.out.println(e.getMessage());
            input.close();
            return -1;
        }
    }
}

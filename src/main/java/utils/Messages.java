package utils;

public enum Messages {
    MATRIX_OUTPUT_INFO_1 ("Расширенная матрица: "),
    MATRIX_OUTPUT_INFO_2 ("Расширенная матрица, приведенная к треугольному виду: "),
    MATRIX_OUTPUT_INFO_3 ("Матрица со значениями 'x': ");

    private String msg;

    Messages(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}

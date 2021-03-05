import java.util.ArrayList;

public class SimpleIteration {
    private double[] previousVariableValues;
    private int iterationCounter = 0;
    private ArrayList<Double> errorList = new ArrayList<Double>();
    public SimpleIteration (double eps, double[][] matrix, int size) {
        // Введем вектор значений неизвестных на предыдущей итерации,
        // размер которого равен числу строк в матрице, т.е. size,
        // причем согласно методу изначально заполняем его нулями
        previousVariableValues = new double[size];
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = matrix[i][size];
        }
        // Будем выполнять итерационный процесс до тех пор,
        // пока не будет достигнута необходимая точность
        while (true) {
            // Введем вектор значений неизвестных на текущем шаге
            double[] currentVariableValues = new double[size];
            // Посчитаем значения неизвестных на текущей итерации
            // в соответствии с теоретическими формулами
            for (int i = 0; i < size; i++) {
                // Инициализируем i-ую неизвестную значением
                // свободного члена i-ой строки матрицы
                currentVariableValues[i] = matrix[i][size] / matrix[i][i];
                // Вычитаем сумму по всем отличным от i-ой неизвестным
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j] / matrix[i][i];
                    }
                }
            }
            // Посчитаем текущую погрешность относительно предыдущей итерации
            double error = 0.0;
            //Критерий по абсолютным отклонениям
            error = Math.abs(massMax(currentVariableValues) - massMax(previousVariableValues));
            errorList.add(error);
            // Если необходимая точность достигнута, то завершаем процесс
            iterationCounter++;
            if (error < eps) {
                break;
            }
            // Переходим к следующей итерации, так
            // что текущие значения неизвестных
            // становятся значениями на предыдущей итерации
            previousVariableValues = currentVariableValues;
        }
    }

    private static double massMax (double[] mass){
        double max = mass[0];
        for (int count = 1; count < mass.length-1; count++)
            if (max < mass[count]) {
                max = mass[count];
            }
        return max;
    }

    public double[] getPreviousVariableValues (){
        return previousVariableValues;
    }

    public int getIterationCounter (){
        return  iterationCounter;
    }

    public ArrayList<Double> getErrorList (){
        return errorList;
    }
}

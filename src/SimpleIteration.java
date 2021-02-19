import java.util.ArrayList;
import java.util.List;

public class SimpleIteration {
    private double[] previousVariableValues;
    private int iterationCounter = 0;
    private ArrayList<double[]> errorList = new ArrayList<double[]>();
    public SimpleIteration (double eps, double[][] matrix, int size) {
        // Введем вектор значений неизвестных на предыдущей итерации,
        // размер которого равен числу строк в матрице, т.е. size,
        // причем согласно методу изначально заполняем его нулями
        previousVariableValues = new double[size];
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = 0.0;
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
                currentVariableValues[i] = matrix[i][size];
                // Вычитаем сумму по всем отличным от i-ой неизвестным
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j];
                    }
                }
                // Делим на коэффициент при i-ой неизвестной
                currentVariableValues[i] /= matrix[i][i];
            }
            // Посчитаем текущую погрешность относительно предыдущей итерации
            double error = 0.0;
            double[] a = new double[size];
            for (int i = 0; i < size; i++) {
                error += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                a[i] = error;
            }
            errorList.add(a);
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

    public double[] getPreviousVariableValues (){
        return previousVariableValues;
    }

    public int getIterationCounter (){
        return  iterationCounter;
    }

    public ArrayList<double[]> getErrorList (){
        return errorList;
    }
}

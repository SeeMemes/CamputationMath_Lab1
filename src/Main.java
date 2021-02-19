import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static double[][] matrix;

    public static void main (String [] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(new Locale("Russian"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        boolean answerGiven = false;
        boolean scannerLine = true;
        while (!answerGiven) {
            System.out.print("Ввод из файла/из строки (true/false): ");
            try {
                boolean method = Boolean.parseBoolean(scanner.nextLine());
                if (method){
                    System.out.print("Введите название файла: ");
                    String path = scanner.nextLine();
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
                    scannerLine = false;
                }
                answerGiven = true;
            } catch (Exception e) {}
        }

        double eps;
        if (scannerLine) System.out.print("Погрешность: ");
        eps = Double.parseDouble(bufferedReader.readLine());
        int size;
        if (scannerLine) System.out.print("Размер: ");
        size = Integer.parseInt(bufferedReader.readLine());
        matrix = new double[size][size + 1];
        if (scannerLine) System.out.println("Вводите матрицу: ");
        for (int i = 0; i < size; i++) {
            //for (int j = 0; j < size + 1; j++) {
                //matrix[i][j] = scanner.nextDouble();
                StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                int count = 0;
                while (stringTokenizer.hasMoreTokens()){
                    matrix[i][count] = Double.parseDouble(stringTokenizer.nextToken());
                    count++;
                }
            //}
        }

        try {
            fix(size);

            SimpleIteration result = new SimpleIteration(eps, matrix, size);
            double[] previousVariableValues = result.getPreviousVariableValues();

            System.out.print("Вектор неизвестных: " + "\n");
            for (int i = 0; i < size; i++) {
                System.out.print(previousVariableValues[i] + " ");
            }
            System.out.print("\n" + "Количество итераций: " + result.getIterationCounter());
            System.out.print("\n" + "Вектор погрешностей: " + "\n");
            ArrayList<double[]> errorList = result.getErrorList();
            for (int i = 0; i < errorList.size(); i++) {
                for (int j = 0; j < errorList.get(i).length; j++){
                    System.out.print(errorList.get(i)[j] + " ");
                }
                System.out.print("\n");
            }
        } catch (DiagonalDominatingException e) {
            e.printStackTrace();
        }


        scanner.close();
        bufferedReader.close();
    }




    private static void fix (int size) throws DiagonalDominatingException {
        for (int i = 0; i < size; i++){
            double a[] = matrix[i];

            double sum = 0;
            for (int j = 0; j < size; j++){
                if (j != i) sum += Math.abs(a[j]);
            }
            if (!(sum <= a[i]) && i != size-1) {
                double[] massMax = massMax(a);
                int table = (int) Math.round(massMax[1]);
                if (table >= i)
                    for (int j = 0; j < size; j++) {
                        double buff;
                        buff = matrix[j][i];
                        matrix[j][i] = matrix[j][table];
                        matrix[j][table] = buff;
                    }
                else {
                    throw new DiagonalDominatingException("Отсутствие диагонального преобладания в строке: " + i+1);
                }
            }
        }
    }

    private static double[] massMax (double[] mass){
        double[] max = new double[2];
        max[0] = mass[0];
        max[1] = 0;
        for (int count = 1; count < mass.length-1; count++)
            if (max[0] < mass[count]) {
                max[0] = mass[count];
                max[1] = count;
            }
        return max;
    }
}

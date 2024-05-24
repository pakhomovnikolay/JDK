import Task_3.ArrayComparator;
import Task_3.Calculator;
import Task_3.Pair;

public class Lesson_3 {

    public static void main(String[] args) {

        // Примеры использования калькулятора
        System.out.println("Сумма: " + Calculator.sum(10, 25));
        System.out.println("Результат деления: " + Calculator.divide(12.0, 2.5));
        System.out.println("Разность: " + Calculator.subtract(125, 200));
        System.out.println("Произведение: " + Calculator.multiply(3.5, 2.0));

        // Примеры использования compareArrays()
        Integer[] intArray1 = {1, 2, 3, 4, 5};
        Integer[] intArray2 = {1, 2, 3, 4, 5};
        Integer[] intArray3 = {1, 2, 3, 4, 6};
        System.out.println(ArrayComparator.compareArrays(intArray1, intArray2));
        System.out.println(ArrayComparator.compareArrays(intArray2, intArray3));
        System.out.println(ArrayComparator.compareArrays(intArray3, intArray1));
        
        // Примеры использования обобщенного класса Pair
        Pair<Integer, String> pair1 = new Pair<>(42, "Hello");
        Pair<Double, Boolean> pair2 = new Pair<>(3.14, true);
        System.out.println("Pair 1: " + pair1.toString());
        System.out.println("First value of Pair 2: " + pair2.getFirst());
        System.out.println("Second value of Pair 2: " + pair2.getSecond());
    }
}
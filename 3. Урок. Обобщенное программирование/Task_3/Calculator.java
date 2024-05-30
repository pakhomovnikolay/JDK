package Task_3;

// 1. Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы: sum(), multiply(), divide(), subtract().
    // Параметры этих методов – два числа разного типа, над которыми должна быть произведена операция.
    // Методы должны возвращать результат своей работы.

public class Calculator {

  // Обобщенный метод для сложения
  public static <T extends Number> double sum(T num1, T num2) {
    return (num1.intValue() + num2.intValue());
  }

  // Обобщенный метод для умножения
  public static <T extends Number> double multiply(T num1, T num2) {
    return (num1.intValue() * num2.intValue());
  }

  // Обобщенный метод для деления
  public static <T extends Number> double divide(T num1, T num2) {
    return (num1.intValue() / num2.intValue());
  }

  // Обобщенный метод для вычитания
  public static <T extends Number> double subtract(T num1, T num2) {
    return (num1.intValue() - num2.intValue());
  }
}
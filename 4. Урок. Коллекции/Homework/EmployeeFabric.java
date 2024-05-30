package Homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeFabric {
    private static Random random = new Random();

    private static Employee generateEmployee(){
        String[] names = new String[] { "Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий", "Рубен", "Герман" };
        String[] phones = new String[] { "81231112233", "81232223344", "81233334455", "81234445566", "81235556677", "81236667788", "81237778899", "81238889900", "81239990011", "81230001122" };
        int id = random.nextInt(1, 10000);
        int experience = random.nextInt(1, 30);
        Employee employee = new Employee(id, phones[random.nextInt(phones.length)], names[random.nextInt(names.length)], experience);
        return employee;
    }

    public static List<Employee> generateEmployees(int count) {
        List<Employee> employee = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            employee.add(generateEmployee());
        }
        return employee;
    }
}

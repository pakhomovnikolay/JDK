import java.util.List;

import Homework.Employee;
import Homework.EmployeeDirectory;
import Homework.EmployeeFabric;

public class Lesson_4 {

    public static void main(String[] args) {

        EmployeeDirectory directory = new EmployeeDirectory();
        List<Employee> employees = EmployeeFabric.generateEmployees(10);

        int experience = 0;
        String name = "";
        int Id = 0;
        boolean flTmp = false;
        for (Employee employee : employees) {
            directory.addEmployee(employee);

            /** Заглушка, чтобы заполнить поля для поиска */
            if (!flTmp) {
                experience = employee.getExperience();
                name = employee.getName();
                Id = employee.getId();
                flTmp = true;
            }
        }


        System.out.println("Список сотрудников отфильтрованных по стажу: " + experience + "\n" + directory.findEmployeeByExperience(experience));
        System.out.println();
        System.out.println("Список номеров телефонов по имени: " + name + "\n" + directory.findPhoneByName(name));
        System.out.println();
        System.out.println("Сотрудник с табельным номером - " + Id + "\n" + directory.findEmployeeById(Id));

        
    }
}

// Создать справочник сотрудников
//  Необходимо:
//      Создать класс справочник сотрудников, который содержит внутри
//      коллекцию сотрудников - каждый сотрудник должен иметь следующие атрибуты:
//          Табельный номер
//          Номер телефона
//          Имя
//          Стаж
//      Добавить метод, который ищет сотрудника по стажу (может быть список)
//      Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
//      Добавить метод, который ищет сотрудника по табельному номеру
//      Добавить метод добавления нового сотрудника в справочник
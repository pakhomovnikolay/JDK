package Homework;

import java.util.ArrayList;
import java.util.List;
import Homework.Interfaces.IEmployee;
import Homework.Interfaces.IEmployeeDirectory;


/**
 * Класс добавляет сотрудников в справочную книгу
 * Имеет методы:
 *  - Поиска сторудников по стажу
 *  - Поиска номера телефона по имени сотрудника.
 *  - Поиска сторудников по табельному номеру
 *  - Добавления сотрудника с справочник
 *  - Удаления сотрудника из справочника
 */
public class EmployeeDirectory implements IEmployeeDirectory {

    /**
     * Поля класса
     * employee - список сотрудников
     */
    private List<IEmployee> employees;

    /**
     * Констурктор класса
     * Инициализирует справочник для создания сотрудников
     */
    public EmployeeDirectory() {
        employees = new ArrayList<IEmployee>();
    }

    @Override
    public List<IEmployee> findEmployeeByExperience(int experience) {
        return employees.stream().filter(e -> e.getExperience() == experience).toList();
    }

    @Override
    public List<String> findPhoneByName(String name) {
        List<IEmployee> Filtered = employees.stream().filter(e -> e.getName() == name).toList();
        List<String> phoneList = new ArrayList<>();

        for (IEmployee iEmployee : Filtered) {
            phoneList.add(iEmployee.getPhone());
        }
        return phoneList;
    }

    @Override
    public IEmployee findEmployeeById(int id) {
        for (IEmployee iEmployee : employees) {
            if (iEmployee.getId() == id) {
                return iEmployee;
            }
        }
        throw new RuntimeException("Сотрудник с табельным номером - " + id + " не найден.");
    }

    @Override
    public void addEmployee(IEmployee employee) {
        employees.add(employee);
    }

    @Override
    public int removeEmployee(IEmployee employee) {
        for (IEmployee iEmployee : employees) {
            if (iEmployee.equals(employee)) {
                employees.remove(employee);
                return 0;
            }
        }
        return 1;
    }
}

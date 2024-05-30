package Homework.Interfaces;

import java.util.List;

public interface IEmployeeDirectory {

    /**
     * Метод поиска сотрудника по стажу
     * @param experience стаж
     * @return
     */
    List<IEmployee> findEmployeeByExperience(int experience);
    
    /**
     * Метод поиска номера телефона сотрудника по имени
     * @param name имя
     * @return
     */
    List<String> findPhoneByName(String name);
    
    /**
     * Метод поиска сотрудника по табельному номеру
     * @param id табельный номер
     * @return
     */
    IEmployee findEmployeeById(int id);

    /**
     * Метод добавляет сотрудника в справочник
     * @param employee сотрудник
     */
    void addEmployee(IEmployee employee);
    
    /**
     * Метод удаляет сотрудника из справочника
     * @param employee сотрудник
     * @return 0 - при успешном удалении, 1 - если указанный сотрудник не найден.
     */
    int removeEmployee(IEmployee employee);
}

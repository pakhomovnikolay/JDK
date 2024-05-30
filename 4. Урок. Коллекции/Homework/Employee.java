package Homework;

import Homework.Interfaces.IEmployee;


/**
 * Класс создает сотрудника с указанными поля.
 * Имеет возвожности получения и изменения значений полей
 * Перегружен метод сравнения сотрудников (табельный номер, стаж, имя)
 */
public class Employee implements IEmployee {

    /**
     * Поля класса
     * id - табельный номер
     * phone - номер телефона
     * name - имя
     * experience - стаж
     */
    private int id;
    private String phone;
    private String name;
    private int experience;

    /**
     * Констурктор класса
     * @param id - табельный номер
     * @param phone - номер телефона
     * @param name - имя
     * @param experience - стаж
     */
    Employee(int id, String phone, String name, int experience) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.experience = experience;
    }
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setId(String phone) {
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IEmployee employee) {
            return id == employee.getId() &&
                    experience == employee.getExperience() &&
                    name == employee.getName();
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "{ Табельный номер - " + id + "\n" +
                        "Номер телефона - " + phone + "\n" +
                        "Имя - " + name + "\n" +
                        "Стаж - " + experience + "\n" +
                        "}";
        return result;
    }
}

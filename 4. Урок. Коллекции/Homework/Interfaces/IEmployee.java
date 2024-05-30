package Homework.Interfaces;

public interface IEmployee {

    /**
     * Метод возвращает табельный номер
     * @return
     */
    int getId();

    /**
     * Метод изменения табельного номера
     * @param id табельный номер
     */
    void setId(int id);

    /**
     * Метод возвращает номер телефона
     * @return
     */
    String getPhone();

    /**
     * Метод изменения номера телефона
     * @param phone номер телефона
     */
    void setId(String phone);

    /**
     * Метод возвращает имя
     * @return
     */
    String getName();

    /**
     * Метод изменения имени
     * @param name имя
     */
    void setName(String name);

    /**
     * Метод возвращает стаж
     * @return
     */
    int getExperience();

    /**
     * Метод изменения стажа
     * @param experience стаж
     */
    void setExperience(int experience);
}

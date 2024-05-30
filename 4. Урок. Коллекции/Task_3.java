import java.util.Map;
import java.util.HashMap;

public class Task_3 {

    public static Map<String, String> getPhoneBook() {
        Map<String, String> phoneBook = new HashMap<>();
        phoneBook.put("123", "Константин");
        phoneBook.put("123123", "Мария");
        phoneBook.put("12311", "Вячеслав");
        phoneBook.put("12", "Кирилл");
        phoneBook.put("911", "Юлия");

        System.out.println(phoneBook.entrySet()
                .stream().min((e1, e2) -> Integer.parseInt(e1.getKey()) - Integer.parseInt(e2.getKey()))
                .get()
                .getValue());

        System.out.println(phoneBook.entrySet()
                .stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .get()
                .getKey());

        return phoneBook;
    }
}

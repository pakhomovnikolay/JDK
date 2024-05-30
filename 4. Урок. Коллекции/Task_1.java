// В рамках выполнения задачи необходимо:
//  ● Создайте коллекцию мужских и женских имен с помощью интерфейса List
//  ● Отсортируйте коллекцию в алфавитном порядке
//  ● Отсортируйте коллекцию по количеству букв в слове
//  ● Разверните коллекцию

import java.util.ArrayList;
import java.util.List;

public class Task_1 {

    public static List<String> sortByAlphabet(List<String> list) {
        // List<String> result = new ArrayList<>(list);
        // result.sort(null);
        // return result;

        return list.stream().sorted().toList();
    }

    public static List<String> sortByLengthLine(List<String> list) {
        //List<String> result = new ArrayList<>(list);
        //  result.sort(new Comparator<String>() {
        //     @Override
        //     public int compare(String o1, String o2) {
        //         // return Integer.compare(o1.length(), o2.length());
        //         // return o1.length() -o2.length();
        //     }
        //  });

        // result.sort((o1, o2) -> o1.length() -o2.length());

        // result.sort(Comparator.comparingInt(String::length));
        // return result;

        return list.stream().sorted((o1, o2) -> o1.length() - o2.length()).toList();
    }

    public static List<String> reversList(List<String> list) {
        return list.reversed();
        
        // List<String> result = new ArrayList<>(list);
        // Collections.reverse(result);
        // return result;
        
        // return list.stream().sorted((o1, o2) -> o1.length() - o2.length());
    }

    public static List<String> createListName() {
        List<String> result = new ArrayList<>();
        result.add("Артём");
        result.add("Василий");
        result.add("Игорь");
        result.add("Елена");
        result.add("Василиса");
        result.add("Егор");
        return result;

        // List<String> result = new ArrayList<>(list);
        // result.sort(null);
        // return result;

        // return list.stream().sorted().toList();
    }
}

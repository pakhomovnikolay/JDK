// Задание 2
// В рамках выполнения задачи необходимо:
//  ● Создайте коллекцию мужских и женских имен с помощью интерфейса List - добавьте повторяющиеся значения
//  ● Получите уникальный список Set на основании List
//  ● Определите наименьший элемент (алфавитный порядок)
//  ● Определите наибольший элемент (по количеству букв в слове но в обратном порядке)
//  ● Удалите все элементы содержащие букву ‘A’

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task_2 {
    
    public static Set<String> createUniqueList(List<String> list) {

        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(list);
        return hashSet;
    }
    
    public static List<String> createListName() {
        List<String> result = new ArrayList<>();
        result.add("Артём");
        result.add("Василий");
        result.add("Игорь");
        result.add("Елена");
        result.add("Василиса");
        result.add("Егор");
        result.add("Ева");
        return result;
    }

    public static String sortByAlphabet(Set<String> hashSet) {
        return hashSet.stream().min(Comparator.naturalOrder()).orElse("");
    }

    public static String sortByLengthLine(Set<String> hashSet) {
        // return hashSet.stream().max((o1, o2) -> o1.length() - o2.length()).orElse("");
        String result = "";
        int maxLength = 0;
        for (String item : hashSet) {
            if (maxLength < item.length()) {
                maxLength = item.length();
                result = item;
            }
        }
        return result;
    }

    public static Set<String> removeElemestWithoutSimbolA(Set<String> hashSet) {
        return hashSet.stream().filter(e -> !e.toLowerCase().contains("а")).collect(Collectors.toSet());
    }
}

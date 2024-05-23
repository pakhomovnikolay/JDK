import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserDialogService {

    /**
     * Метод сохранения данных в файл
     * 
     * @param content
     * @param path
     * @param append
     * @return
     */
    public Boolean saveFile(String content, String path, boolean append) {

        try (FileWriter fileWriter = new FileWriter(path, append)) {
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Не удалось записать данные в файл - " + path + ". Описание ошибки: " + e.getMessage());
        }
    }

    /**
     * Метод получения данных из файла
     * 
     * @param path
     * @return
     */
    public String readFile(String path) {
        try {
            String resultData = "";
            File file = new File(path);
            if (!file.isFile())
                return resultData;

            FileReader fr = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line = reader.readLine();
                while (line != null) {
                    resultData += line + "\n";
                    line = reader.readLine();
                }
                return resultData;
            } catch (IOException e) {
                throw new RuntimeException(
                        "Не удалось получить данные из файла - " + path + ". Описание ошибки: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Не удалось получить данные из файла - " + path + ". Описание ошибки: " + e.getMessage());
        }
    }
}


public class Lesson_5 {

    public static synchronized void main(String[] args) {

        Table table = new Table(5, 5000, 3);
        table.start();
    }
}
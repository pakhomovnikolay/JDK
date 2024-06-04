import java.util.ArrayList;
import java.util.List;

public class Table extends Thread {
    
    /** Коллекция философов */
    private List<Philosopher> philosophers;

    /**
     * Конструктор класса
     * @param philosopherMax - Максимальное количество возможных философов
     * @param eatingTime - Время примема пищи
     * @param eatingMax - Максимальное количество приемов пищи
     */
    Table(int philosopherMax, int eatingTime, int eatingMax) {
        philosophers = new ArrayList<>(eatingMax);

        for (int i = 0; i < philosopherMax; i++) {
            String name = PhilosophersFabrik.getName(i);
            philosophers.add(new Philosopher(name, i, philosopherMax, eatingTime, eatingMax));
        }
    }

    @Override
    public void run() {
        try {
            inviteAllPhilosopher();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void inviteAllPhilosopher() throws InterruptedException {
        System.out.println("============================================================================");
        for (Philosopher philosopher : philosophers) {
            philosopher.invite();
        }
    }
}

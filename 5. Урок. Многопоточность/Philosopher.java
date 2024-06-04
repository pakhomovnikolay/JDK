import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Philosopher extends Thread {

    /** Количество принимающих пищу философов */
    private static volatile int countPhilosopherEating;

    /** Количество философов готовых философствовать */
    private static volatile int countPhilosopherEatingFinished;

    /** Максимально возможное количество философов принимающих пищу */
    private final int COUNT_PHILOSOPHER_EATING = 2;

    /** Имя философа */
    private volatile String philosopherName;

    /** Индекс философа */
    private volatile int philosopherIndex;

    /** Максимальное количество философов */
    private volatile int maxPhilosopher;

    /** Время приема пищи */
    private volatile int eatingTime;

    /** Максимальное количество приемов пищи */
    private volatile int maxEating;

    /** Текущее количество приемов пищи */
    private volatile int countEating;

    /** Состояние приема пищи */
    private volatile boolean eating;

    /** Состояние приема пищи */
    private volatile boolean thought;

    /** Максимальное количество приемов пищи */
    private volatile int leftPhilosopher;

    /** Максимальное количество приемов пищи */
    private volatile int rightPhilosopher;
    
    /** Формат даты и времени */
    private final DateTimeFormatter formater = DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss SSS");

    Philosopher(String philosopherName, int philosopherIndex, int maxPhilosopher, int eatingTime, int maxEating) {
        this.philosopherName = philosopherName;
        this.philosopherIndex = philosopherIndex;
        this.maxPhilosopher = maxPhilosopher;
        this.eatingTime = eatingTime;
        this.maxEating = maxEating;
        this.countEating = 0;

        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] Создан филосов - " + this.philosopherName);
    }

    /**
     * Получить текущее количество приемов пищи
     * @return
     */
    public int getCountEating() {
        return countEating;
    }

    @Override
    public void run() {
        while (countEating < maxEating) {
            try {
                leftPhilosopher = countPhilosopherEating;
                rightPhilosopher = countPhilosopherEating + COUNT_PHILOSOPHER_EATING;
                
                if (rightPhilosopher >= maxPhilosopher) {
                    rightPhilosopher -= maxPhilosopher;
                }

                if (leftPhilosopher == philosopherIndex || rightPhilosopher == philosopherIndex) {
                    if (eating) {
                        eatingFinished();
                    } else if (thought) {
                        eating();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] Уважаемый " + philosopherName + ", наша корчма закрывается. Приходите завтра. ");
    }

    public void invite() {
        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] За стол сел - " + this.philosopherName);
        System.out.println("[ " + timeStamp + " ] Философ " + philosopherName + ". Размышляет... ~~~ " + PhilosophersFabrik.getThought() + " ~~~");
        thought = true;
        start();
    }

    /** Метод приема пищи */
    private void eating() throws InterruptedException {
        
        eating = true;
        thought = false;
        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] Философ " + this.philosopherName + " поел " + (countEating + 1) + " раз(а)");
        if (eatingTime > 0) {
            sleep(eatingTime);
        }
    }

    /** Метод завершения приема пищи */
    private void eatingFinished() {
        eating = false;
        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] Философ " + this.philosopherName + " положил вилку на стол");
        thought();
        countEating++;
    }

    /** Метод размышления о... */
    private synchronized void thought() {
        try {
            thought = true;
            String timeStamp = LocalDateTime.now().format(formater);
            System.out.println("[ " + timeStamp + " ] Философ " + philosopherName + ". Размышляет... ~~~ " + PhilosophersFabrik.getThought() + " ~~~");
            
            countPhilosopherEatingFinished++;
            if (countPhilosopherEatingFinished >= COUNT_PHILOSOPHER_EATING) {
                countPhilosopherEatingFinished = 0;
                countPhilosopherEating++;
                if (countPhilosopherEating >= maxPhilosopher) {
                    countPhilosopherEating = 0;
                }
            }
            next();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /** Метод проверки возможности начала приема пищи слудющему фолософу */
    public synchronized void next() throws InterruptedException {
        String timeStamp = LocalDateTime.now().format(formater);
        System.out.println("[ " + timeStamp + " ] Философ " + this.philosopherName + " ожидает вилку");
    }
}

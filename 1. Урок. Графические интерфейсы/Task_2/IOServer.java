public interface IOServer<S> {

    /** Состояние клиента - Подключен */
    public static final int CLIENT_CONNECTED = 1;

    /** Состояние клиента - Отключен */
    public static final int CLIENT_DISCONNECTED = 0;

    /** Состояние сервера - Запущен */
    public static final int SERVER_START = 1;

    /** Состояние сервера - Остановлен */
    public static final int SERVER_STOP = 0;

    /**
     * Возвращает текущее состояние сервера
     * 
     * @return
     */
    int getServerStatus();

    /**
     * Задает состояние сервера
     * 
     * @param serverStatus
     */
    void setServerStatus(int serverStatus);

    /**
     * Возвращает статус работы сервера
     * 
     * @return
     */
    Boolean isRunning();

    /**
     * Метод записи данных в лог файл
     * 
     * @param message
     */
    void writeLog(String message);

    /**
     * Возвращает данные лог файла
     * 
     * @return
     */
    void readLog();

    /**
     * Метод отправки сообщений
     * 
     * @param message
     */
    void sendMessage(String message, S e);

    /**
     * Метод инициализации элементов управления
     */
    void initializeControlElement();

    /**
     * Метод создания стартового окна
     */
    void createStartWindow();

    /**
     * Метод создания наблюдатетей
     */
    void createActionListener();

    /**
     * Метод создания нового клиента
     */
    void createClient(ClientService client);

    /**
     * Метод очистки окна сообщений
     */
    void clearLogViewer();
}

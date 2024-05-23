public interface IServerWindow {

    /** Высота окна */
    final int WINDOW_HEIGHT = 600;

    /** Ширина окна */
    final int WINDOW_WIDHT = 500;

    /** Наименование окна */
    final String TITLE_DEFAULT = "Чат (сервер)";

    /** Метод запуска сервера */
    void startServer();

    /** Метод завершения работы сервера */
    void stopServer();

    /* Метод добавления записи в журнал сообщений */
    void appenedMessage(String message);

    /** Метод обновления состояния клиента */
    void updateServerStatus();
}

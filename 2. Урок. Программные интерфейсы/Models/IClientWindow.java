public interface IClientWindow {

    /** Высота окна */
    final int WINDOW_HEIGHT = 600;

    /** Ширина окна */
    final int WINDOW_WIDHT = 500;

    /** Наименование окна */
    final String TITLE_DEFAULT = "Чат (клиент)";

    /** Метод запроса подключения к серверу */
    void requestConnect();

    /** Метод отправки сообщения серверу */
    void sendMessage();

    /** Метод запроса на отключение от сервера */
    void requestDisconnect();

    /* Метод добавления записи в журнал сообщений */
    void appenedMessage(String message);

    /** Метод обновления состояния клиента */
    void updateClientStatus();

}

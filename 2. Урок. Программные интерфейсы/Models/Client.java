import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {

    /** Запуск клиента */
    final int CLIENT_START = 1;

    /** Останов клиента */
    final int CLIENT_STOP = 0;

    /** Путь сохранения логов */
    final String SAVE_LOG_PATH = "Log.txt";

    /** Сервис взаимодействия */
    private UserDialogService userDialog;

    /** Экземпляр окна */
    private ClientWindow window;

    /** Экземпляр контроллера */
    ControllerService controller;

    /** Состояние клиента */
    private int clientStatus;

    /** Поле для хранения сетевого адреса */
    private String address;

    /** Поле для хранения сетевого порта */
    private String port;

    /** Поле для хранения логина */
    private String login;

    /** Поле для хранения пароля */
    private String password;

    /**
     * Метод получения сетевого адреса
     * 
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Метод изменения сетевого адреса
     * 
     * @return
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Метод получения сетевого порта
     * 
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * Метод изменения сетевого порта
     * 
     * @return
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Метод получения логина клиента
     * 
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     * Метод изменения логина клиента
     * 
     * @return
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Метод получения пароля клиента
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Метод изменения пароля клиента
     * 
     * @return
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Конструктор класса */
    Client(ControllerService controller) {
        this.controller = controller;
        userDialog = new UserDialogService();
        window = new ClientWindow(this);
    }

    /**
     * Метод возвращает текущее состояние клиента
     * 
     * @return
     */
    public int getClientState() {
        return clientStatus;
    }

    /**
     * Метод устанавливает состояние клиента
     * 
     * @param status
     */
    public void setClentState(int clientStatus) {
        this.clientStatus = clientStatus;
        window.updateClientStatus();
        if (clientStatus == CLIENT_START) {
            readLog();
            writeLog("Подключение с сервером установлено");

            controller.requestMessage(this);
        } else {
            writeLog("Произведено отключение от сервера");
        }
    }

    /**
     * Метод возвращает статус клиента - Подключен
     * 
     * @return
     */
    public Boolean getStatusConnected() {
        return clientStatus == CLIENT_START;
    }

    /**
     * Метод запроса на подключение
     * 
     * @return
     */
    public void requestConnect() {
        if (controller.requestConnect(this)) {
            setClentState(CLIENT_START);
        } else {
            writeLog("Не удалось подключиться к серверу. Повторите попытку позже");
        }
    }

    /**
     * Метод отключения от сервера
     */
    public void requestDisconnect() {
        if (!controller.requestDisconnect(this)) {
            writeLog("Не удалось отключиться от сервер. Повторите попытку позже");
        }
    }

    /**
     * Метод отключения от сервера, по иницитиве сервера
     */
    public void disconnect() {
        setClentState(CLIENT_STOP);
    }

    /**
     * Метод записи данных в лог
     * 
     * @param content
     */
    public void writeLog(String content) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
        String message = "[ " + timeStamp + " ] " + content + "\n";
        window.appenedMessage(message);
        userDialog.saveFile(message, window.getName() + "_" + SAVE_LOG_PATH, true);
    }

    /*
     * Метод чтения данных из лога
     */
    public void readLog() {
        if (userDialog == null)
            userDialog = new UserDialogService();
        window.appenedMessage(userDialog.readFile(window.getName() + "_" + SAVE_LOG_PATH));
    }

    /**
     * Метод отправки сообщения
     * 
     * @param content
     */
    public void sendMessage(String content) {
        if (content.isEmpty()) {
            return;
        }
        controller.sendMessage(content, this);
    }

    /**
     * Метод получнеия сообщения от сервера
     * 
     * @param content
     */
    public void receivingMessage(String content) {
        if (content.isEmpty()) {
            return;
        }
        window.appenedMessage(content);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Client client) {
            return client.getAddress() == this.getAddress() &&
                    client.getPort() == this.getPort() &&
                    client.getLogin() == this.getLogin();
        }
        return false;
    }
}

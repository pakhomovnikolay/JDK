import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class Server {

    /** Запуск сервера */
    final int SERVER_START = 1;

    /** Останов сервера */
    final int SERVER_STOP = 0;

    /** Путь сохранения логов */
    final String SAVE_LOG_PATH = "Log.txt";

    /** Путь сохранения сообщений */
    final String SAVE_MSG_PATH = "Message.txt";

    /** Сервис взаимодействия */
    private UserDialogService userDialog;

    /** Экземпляр окна */
    private ServerWindow window;

    /** Состояние сервера */
    private int serverStatus;

    private Collection<Client> clients;

    /** Экземпляр класса */
    Server() {
        userDialog = new UserDialogService();
        window = new ServerWindow(this);
        clients = new ArrayList<>();
    }

    /**
     * Метод возвращает текущее состояние сервера
     * 
     * @return
     */
    public int getServerState() {
        return serverStatus;
    }

    /**
     * Метод устанавливает состояние сервера
     * 
     * @param status
     */
    public void setServerState(int status) {
        serverStatus = status;
        window.updateServerStatus();
        if (status == SERVER_START) {
            readLog();
            writeLog("Сервер запущен.");
        } else {
            writeLog("Сервер остановлен.");
            disconnectAll();
        }
    }

    /**
     * Метод возвращает статус сервера - Запущен
     * 
     * @return
     */
    public Boolean getStatusRunning() {
        return serverStatus == SERVER_START;
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
        userDialog.saveFile(message, SAVE_LOG_PATH, true);
    }

    /*
     * Метод чтения данных из лога
     */
    public void readLog() {
        if (userDialog == null)
            userDialog = new UserDialogService();
        window.appenedMessage(userDialog.readFile(SAVE_LOG_PATH));
        window.appenedMessage(userDialog.readFile(SAVE_MSG_PATH));
    }

    /**
     * Метод запроса на подключение клиента
     * 
     * @return
     */
    public Boolean requestConnect(Client client) {
        if (getStatusRunning()) {
            for (Client currClient : clients) {
                if (currClient.equals(client)) {
                    writeLog("Пользователь " + client.getLogin() + ": присоеденился к чату");
                    return true;
                }
            }
            clients.add(client);
            writeLog("Пользователь " + client.getLogin() + ": присоеденился к чату");
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Метод отключения клиента
     * 
     * @return
     */
    public Boolean disconnect(Client client) {
        for (Client currClient : clients) {
            if (currClient.equals(client)) {
                client.disconnect();
                clients.remove(client);
                return true;
            }
        }
        return false;
    }

    /**
     * Метод отключения всех клиентов
     * 
     * @return
     */
    public void disconnectAll() {
        for (Client currClient : clients) {
            currClient.disconnect();
        }
    }

    /**
     * Метод остановки сервера
     * 
     * @return
     */
    public void stopServer() {
        setServerState(SERVER_STOP);
    }

    /**
     * Метод запуска сервера
     * 
     * @return
     */
    public void startServer() {
        setServerState(SERVER_START);
    }

    /**
     * Метод записи сообщений
     * 
     * @param content
     */
    public void writeMessage(String content, Client client) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
        String message = "[ " + timeStamp + " ] " + content + "\n";
        window.appenedMessage(message);
        for (Client currClient : clients) {
            if (currClient.getStatusConnected()) {
                currClient.receivingMessage(message);
            }
        }
        userDialog.saveFile(message, SAVE_MSG_PATH, true);
    }

    /**
     * Метод записи сообщений
     * 
     * @param content
     */
    public void sendMessage(Client currClient) {
        currClient.receivingMessage(userDialog.readFile(SAVE_MSG_PATH));
    }

    /**
     * Метод возвращает кол-во клиентов
     * @return
     */
    public int getCountClient() {
        return clients.size();
    }
}
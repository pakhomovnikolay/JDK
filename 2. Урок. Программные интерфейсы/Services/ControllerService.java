public class ControllerService {

    /** Экземпляр сервера */
    Server server;

    /**
     * Конструктор класса
     * 
     * @param server
     */
    ControllerService(Server server) {
        this.server = server;
    }

    /**
     * Метод запроса на подключение
     * 
     * @return
     */
    public Boolean requestConnect(Client client) {
        return server.requestConnect(client);
    }

    /**
     * Метод запроса на отключение
     * 
     * @return
     */
    public Boolean requestDisconnect(Client client) {
        return server.disconnect(client);
    }

    /**
     * Метод отключения клиента
     * 
     * @return
     */
    public void disconnect(Client client) {
        client.disconnect();
    }

    /**
     * Метод отправки сообщения серверу
     * 
     * @return
     */
    public void sendMessage(String message, Client client) {
        server.writeMessage(client.getLogin() + ": " + message, client);
    }

    /**
     * Метод запроса сообщений от сервера
     * 
     * @return
     */
    public void requestMessage(Client client) {
        server.sendMessage(client);
    }
}

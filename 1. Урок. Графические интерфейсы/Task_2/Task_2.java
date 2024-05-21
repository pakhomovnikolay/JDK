public class Task_2 {

    public static void main(String[] args) {
        
        ServerService server = new ServerService();
        new ClientService(server);
        new ClientService(server);
    }
}

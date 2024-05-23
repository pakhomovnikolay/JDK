public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        ControllerService controller = new ControllerService(server);

        
        

        new Client(controller);
        new Client(controller);
    }
}

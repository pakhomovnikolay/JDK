import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class ServerService extends JFrame implements IOServer<ClientService> {

    /** Высота окна */
    private static final int WINDOW_HEIGHT = 555;

    /** Ширина окна */
    private static final int WINDOW_WIDHT = 504;

    /** Позиция по координате X */
    private static final int WINDOW_POS_X = 800;

    /** Позиция по координате Y */
    private static final int WINDOW_POS_Y = 300;

    /** Наименование окна */
    private static final String TITLE = "Чат (сервер)";

    /** Путь для сохранения логов сервера */
    private static final String FILE_PATH = "ServerLog.txt";

    /** Путь для сохранения сообщений пользователей */
    private static final String FILE_PATH_MESSAGE = "MessagesLog.txt";

    /** Состояние сервера */
    private int serverStatus;

    /** список полученных сообщений */
    private String messages = "";

    /** Коллекция созданных клиентов */
    private HashSet<ClientService> clients;

    /** Элемента управления */
    private JButton btnStart, btnStop;
    private JTextArea logViewer;
    private JPanel bottomPanel, centerPanel;



    /**
     * Конструктор класса
     */
    ServerService() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POS_X, WINDOW_POS_Y);
        setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
        setTitle(TITLE);

        initializeControlElement();
        createStartWindow();
        createActionListener();
        readLog();
        setVisible(true);
    }

    @Override
    public int getServerStatus() {
        return serverStatus;
    }

    @Override
    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
        if (isRunning()) {
            writeLog("Сервер запущен");
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        } else {
            writeLog("Сервер остановлен");
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);

            for (ClientService clientService : clients) {
                if (clientService.isRunning()) {
                    clientService.setServerStatus(CLIENT_DISCONNECTED);
                }
            }
        }
    }

    @Override
    public Boolean isRunning() {
        return serverStatus == SERVER_START;
    }

    @Override
    public void writeLog(String message) {

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
        String msg = logViewer.getText() + "[ " + timeStamp + " ] " + message + "\n";

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false)) {
            fileWriter.write(msg);
            logViewer.setText(msg);
        } catch (Exception e) {
            System.out.println("Не удалось записать данные в файл - " + FILE_PATH);
        }
    }

    @Override
    public void readLog() {
        try {
            clearLogViewer();
            File file = new File(FILE_PATH);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                String msg = logViewer.getText() + line + "\n";
                logViewer.setText(msg);
                line = reader.readLine();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println(
                    "Не удалось записать данные в файл - " + FILE_PATH + ". Описание ошибки: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(
                    "Не удалось записать данные в файл - " + FILE_PATH + ". Описание ошибки: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(String message, ClientService e) {
        writeMesssage(message, e);
    }

    @Override
    public void initializeControlElement() {
        btnStart = new JButton("Запустить сервер");
        btnStop = new JButton("Остановить сервер");
        btnStop.setEnabled(false);

        logViewer = new JTextArea();
        clients = new HashSet<>();
    }

    @Override
    public void createStartWindow() {
        add(createButtonPanel(), BorderLayout.SOUTH);
        add(createLogPanel());
    }

    @Override
    public void createActionListener() {

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getServerStatus() == SERVER_START) {
                    writeLog("Сервер запущен. Действие не требуется.");
                } else {
                    setServerStatus(SERVER_START);
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getServerStatus() == SERVER_STOP) {
                    writeLog("Сервер остановлен. Действие не требуется.");
                } else {
                    setServerStatus(SERVER_STOP);
                }
            }
        });

        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isRunning()) {
                    setServerStatus(SERVER_STOP);
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    @Override
    public void createClient(ClientService client) {
        clients.add(client);
    }

    @Override
    public void clearLogViewer() {
        logViewer.setText("");
    }

    /**
     * Метод создания элементов управления
     * 
     * @return
     */
    private JPanel createButtonPanel() {

        bottomPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);

        return bottomPanel;
    }

    /**
     * Метод создания панели сообщений
     * 
     * @return
     */
    private JScrollPane createLogPanel() {

        centerPanel = new JPanel(new GridLayout(1, 0));

        logViewer.setEditable(false);
        logViewer.setMargin(new Insets(5, 5, 0, 5));

        centerPanel.add(logViewer);
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        return scrollPane;
    }


    /**
     * Метод подключения пользователя к серверу
     * @param name
     * @return
     */
    public boolean connectionServer(String name) {
        boolean connected = isRunning();
        if (connected) {
            writeLog("Пользователь \"" + name + "\" успешно подключен с серверу");
        }
        return connected;
    }
    

    /**
     * Метод сохранения сообщения в лог
     * @param message
     * @param e
     */
    public void writeMesssage(String message, ClientService e) {

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
        String msg = messages +  "[ " + timeStamp + " ] " + "Пользователь \" " + e.getNameClient() + "\": " + message + "\n";
        writeLog("Пользователь \" " + e.getNameClient() + "\": " + message);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH_MESSAGE, false)) {
            fileWriter.write(msg);
            messages = msg;
        } catch (Exception ext) {
            System.out.println("Не удалось записать данные в файл - " + FILE_PATH_MESSAGE);
        }

        for (ClientService clientService : clients) {
            if (clientService.isRunning()) {
                clientService.writeMesssage("Пользователь \" " + e.getNameClient() + "\": " + message);
            }
        }
    }

    /**
     * Метод чтения сообщений из лога
     */
    public String readMesssages(ClientService e) {
        String savedMessages = "";
        try {
            File file = new File(FILE_PATH_MESSAGE);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                // if (savedMessages.contains(line)) {
                //     savedMessages += line + "\n";
                // }
                savedMessages += line + "\n";
                line = reader.readLine();
            }
            reader.close();
            return savedMessages;

        } catch (FileNotFoundException ext) {
            System.out.println( "Не удалось записать данные в файл - " + FILE_PATH_MESSAGE + ". Описание ошибки: " + ext.getMessage());
            return savedMessages;
        } catch (IOException ext) {
            System.out.println( "Не удалось записать данные в файл - " + FILE_PATH_MESSAGE + ". Описание ошибки: " + ext.getMessage());
            return savedMessages;
        }
    }
}

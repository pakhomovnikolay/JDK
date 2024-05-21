import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class ClientService extends JFrame implements IOServer<ServerService> {

    /** Высота окна */
    private static final int WINDOW_HEIGHT = 555;

    /** Ширина окна */
    private static final int WINDOW_WIDHT = 504;

    /** Позиция по координате X */
    private static final int WINDOW_POS_X = 800;

    /** Позиция по координате Y */
    private static final int WINDOW_POS_Y = 300;
    
    /** Наименование окна */
    private static final String TITLE = "Чат (клиент)";

    /** Состояние клиента */
    private int clientStatus;

    private ServerService server;

    /** Элемента управления */
    private JButton btnConnect, btnSendMessage;
    private JTextArea logViewer;
    private JTextField address, port, name, messages;
    private JPasswordField password;
    private Timer timerControlConnect;
    private JPanel topPanel, bottomPanel, centerPanel;
    
    /**
     * Конструктор класса
     * @param server
     */
    ClientService(ServerService server) {
        this.server = server;
        setLocationRelativeTo(server);
        setLocation(WINDOW_POS_X + server.getWidth(), WINDOW_POS_Y);
        setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
        setTitle(TITLE);

        initializeControlElement();
        createStartWindow();
        createActionListener();
        setVisible(true);
        timerRun();

        createClient(this);
        setVisible(true);
    }

    @Override
    public int getServerStatus() {
        return clientStatus;
    }

    @Override
    public void setServerStatus(int clientStatus) {
        this.clientStatus = clientStatus;
        if (isRunning()) {
            readLog();
            writeLog("Подключение к серверу успешно установлено");
            logViewer.setText(logViewer.getText() + server.readMesssages(this));
            btnSendMessage.setEnabled(true);
            messages.setEditable(true);
            topPanel.setVisible(false);
            setTitle(TITLE + ": " + name.getText());

        } else {
            writeLog("Соединение потеряно");
            btnSendMessage.setEnabled(false);
            messages.setEditable(false);
            topPanel.setVisible(true);
            setTitle(TITLE);

        }
    }

    @Override
    public Boolean isRunning() {
        return clientStatus == CLIENT_CONNECTED;
    }

    @Override
    public void writeLog(String message) {
        String FILE_PATH = "ClientLog_" + name.getText()  +".txt";
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
        String FILE_PATH = "ClientLog_" + name.getText()  +".txt";
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
            System.out.println("Не удалось записать данные в файл - " + FILE_PATH + ". Описание ошибки: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Не удалось записать данные в файл - " + FILE_PATH + ". Описание ошибки: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(String message, ServerService e) {
        server.sendMessage(message, this);
    }

    @Override
    public void initializeControlElement() {
        address = new JTextField("127.0.0.1");
        port = new JTextField("1101");
        name = new JTextField("Иван");
        password = new JPasswordField("121321321321");
        messages = new JTextField();
        messages.setEditable(false);

        btnConnect = new JButton("Подключиться");
        btnSendMessage = new JButton("Отправить");
        btnSendMessage.setEnabled(false);
    }

    @Override
    public void createStartWindow() {
        add(createConnectPanel(), BorderLayout.NORTH);
        add(createMessagePanel(), BorderLayout.SOUTH);
        add(createLogPanel(), BorderLayout.CENTER);
    }
    
    @Override
    public void createActionListener() {
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText() == null || name.getText() == "") {
                    writeLog("Невозможно подключиться к серверу. Не указано имя пользователя");
                } else if (server.connectionServer(name.getText())) {
                    setServerStatus(CLIENT_CONNECTED);
                } else {
                    writeLog("Не удалось подключиться к серверу. Повторите попытку позже");
                }
            }
        });
        
        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    writeLog("Невозможно отправить сообщение. Отсутствует соединение с сервером");
                } else {
                    sendMessage(messages.getText(), server);
                }
            }
        });
        
        messages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning()) {
                    writeLog("Невозможно отправить сообщение. Отсутствует соединение с сервером");
                } else {
                    sendMessage(messages.getText(), server);
                }
            }
        });
    }

    @Override
    public void createClient(ClientService client) {
        server.createClient(client);
    }

    @Override
    public void clearLogViewer() {
        logViewer.setText("");
    }

    /**
     * Метод создания верхней панели
     * @return
     */
    private JPanel createConnectPanel() {
        topPanel = new JPanel(new GridLayout(2, 0));

        JPanel addressPanel = new JPanel(new GridLayout(2, 3));
        addressPanel.add(new JLabel(" IP адрес:"));
        addressPanel.add(new JLabel(" Порт:"));
        addressPanel.add(new JLabel());
        addressPanel.add(address);
        addressPanel.add(port);

        JPanel dataPanel = new JPanel(new GridLayout(2, 3));
        dataPanel.add(new JLabel(" Имя пользователя:"));
        dataPanel.add(new JLabel(" Пароль:"));
        dataPanel.add(new JLabel());
        dataPanel.add(name);
        dataPanel.add(password);
        dataPanel.add(btnConnect);
        
        topPanel.add(addressPanel);
        topPanel.add(dataPanel);

        return topPanel;
    }

    /**
     * Метод создания нижней панели
     * @return
     */
    private JPanel createMessagePanel() {
        bottomPanel = new JPanel(new GridLayout(2, 0));

        bottomPanel.add(messages);
        bottomPanel.add(btnSendMessage);

        return bottomPanel;
    }

    /**
     * Метод создания панели сообщений
     * @return
     */
    private JScrollPane createLogPanel() {
        centerPanel = new JPanel(new GridLayout(1, 1));

        logViewer = new JTextArea();
        logViewer.setEditable(false);
        logViewer.setMargin(new Insets(5, 5, 0, 5));

        centerPanel.add(logViewer);

        JScrollPane scrollPane = new JScrollPane(centerPanel); 
        
        return scrollPane;
    }


    /**
     * Метод создания таймера
     */
    private void timerRun() {
        timerControlConnect = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientStatus == CLIENT_CONNECTED && !server.isRunning()) {
                    setServerStatus(CLIENT_DISCONNECTED);
                }
            }
        });

        timerControlConnect.start();
    }

    /**
     * Метод записи сообщения
     * @param message
     */
    public void writeMesssage(String message) {

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
        String msg = logViewer.getText() + "[ " + timeStamp + " ] " + message + "\n";
        logViewer.setText(msg);
        messages.setText("");
    }


    /**
     * Метод получения имени пользователя
     * @return
     */
    public String getNameClient() {
        return name.getText();
    }
    


    // /** Состояние клиента */
    // private int clientStatus;

    // /**
    //  * Метод возвращает состояние клиента
    //  * @return
    //  */
    // public int getClientStatus() {
    //     return clientStatus;
    // }

    // /**
    //  * Метод изменения состояния клиента
    //  * 
    //  * @param clientStatus
    //  */
    // private void setClientStatus(int clientStatus) {
    //     this.clientStatus = clientStatus;
    //     if (clientStatus == CLIENT_CONNECTED) {
            
    //         getLog();
    //         addLog("Подключение к серверу успешно установлено");
    //         btnSendMessage.setEnabled(true);
    //         messages.setEditable(true);
    //         topPanel.setVisible(false);
    //         setTitle(TITLE + ": " + name.getText());
    //     } else if (clientStatus == CLIENT_DISCONNECTED) {

    //         addLog("Соединение потеряно");
    //         setTitle(TITLE);
    //         btnSendMessage.setEnabled(false);
    //         messages.setEditable(false);
    //         topPanel.setVisible(true);
    //     }
    // }

    // private void timerRun() {
    //     timerControlConnect = new Timer(1000, new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             if (clientStatus == CLIENT_CONNECTED && !server.isRunning()) {
    //                 setClientStatus(CLIENT_DISCONNECTED);
    //             }
    //         }
    //     });

    //     timerControlConnect.start();
    // }

    // /**
    //  * Конструктор класса
    //  * @param server
    //  */
    // ClientService(ServerService server) {
    //     this.server = server;
    //     setLocationRelativeTo(server);
    //     setLocation(WINDOW_POS_X + server.getWidth(), WINDOW_POS_Y);
    //     setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
    //     setTitle(TITLE);
        
    //     initializeControlElement();
    //     createStartWindow();
    //     createActionListener();
    //     setVisible(true);
    //     timerRun();
    // }

    // /**
    //  * Метод инициализации элементов упраления
    //  */
    // private void initializeControlElement() {
    //     address = new JTextField("127.0.0.1");
    //     port = new JTextField("1101");
    //     name = new JTextField("Иван");
    //     password = new JPasswordField("121321321321");
    //     messages = new JTextField();
    //     messages.setEditable(false);

    //     btnConnect = new JButton("Подключиться");
    //     btnSendMessage = new JButton("Отправить");
    //     btnSendMessage.setEnabled(false);
    // }

    // /**
    //  * Метод создания верхней панели
    //  * @return
    //  */
    // private JPanel createConnectPanel() {
    //     topPanel = new JPanel(new GridLayout(2, 0));

    //     JPanel addressPanel = new JPanel(new GridLayout(2, 3));
    //     addressPanel.add(new JLabel(" IP адрес:"));
    //     addressPanel.add(new JLabel(" Порт:"));
    //     addressPanel.add(new JLabel());
    //     addressPanel.add(address);
    //     addressPanel.add(port);

    //     JPanel dataPanel = new JPanel(new GridLayout(2, 3));
    //     dataPanel.add(new JLabel(" Имя пользователя:"));
    //     dataPanel.add(new JLabel(" Пароль:"));
    //     dataPanel.add(new JLabel());
    //     dataPanel.add(name);
    //     dataPanel.add(password);
    //     dataPanel.add(btnConnect);
        
    //     topPanel.add(addressPanel);
    //     topPanel.add(dataPanel);

    //     return topPanel;
    // }

    // /**
    //  * Метод создания нижней панели
    //  * @return
    //  */
    // private JPanel createMessagePanel() {
    //     bottomPanel = new JPanel(new GridLayout(2, 0));

    //     bottomPanel.add(messages);
    //     bottomPanel.add(btnSendMessage);

    //     return bottomPanel;
    // }

    // /**
    //  * Метод создания панели сообщений
    //  * @return
    //  */
    // private JScrollPane createLogPanel() {
    //     centerPanel = new JPanel(new GridLayout(1, 1));
        

    //     log = new JTextArea();
    //     log.setEditable(false);
    //     log.setMargin(new Insets(5, 5, 0, 5));

    //     centerPanel.add(log);

    //     JScrollPane scrollPane = new JScrollPane(centerPanel); 
        
    //     return scrollPane;
    // }

    // /**
    //  * Метод создания панелей при старте приложения
    //  */
    // private void createStartWindow() {
    //     add(createConnectPanel(), BorderLayout.NORTH);
    //     add(createMessagePanel(), BorderLayout.SOUTH);
    //     add(createLogPanel(), BorderLayout.CENTER);
    // }

    // /**
    //  * Метод создания слушателей
    //  */
    // private void createActionListener() {
    //     btnConnect.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             // if (server.connected(address.getText(), port.getText(), name.getText(), password.getSelectedText())) {
    //             //     setClientStatus(CLIENT_CONNECTED);
    //             // } else {
    //             //     addLog("Не удалось подключиться к серверу");
    //             // }
    //         }
    //     });

    //     btnSendMessage.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             if (getClientStatus() == CLIENT_DISCONNECTED) {
    //                 addLog("Невозможно отправить сообщение. Отсутствует соединение с сервером");
    //             } else {
    //                 addLog(messages.getText());
    //                 messages.setText("");
    //             }
    //         }
    //     });

    //     messages.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             if (getClientStatus() == CLIENT_DISCONNECTED) {
    //                 addLog("Невозможно отправить сообщение. Отсутствует соединение с сервером");
    //             } else {
    //                 addLog(messages.getText());
    //                 messages.setText("");
    //             }
    //         }
    //     });
    // }


    // /**
    //  * Метод логирования событий
    //  * @param message
    //  */
    // public void addLog(String message) {
    //     // String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
    //     // String msg = log.getText() + "[ " + timeStamp + " ] " + message + "\n";
    //     // log.setText(msg);
    //     // String login = name.getText();

    //     // if (getClientStatus() == CLIENT_CONNECTED && login != "") {
    //     //     String filePath = "log_" + login + ".txt";
    //     //     try (FileWriter fileWriter = new FileWriter(filePath, false)) {
    //     //         fileWriter.write(msg);
    //     //         if (!topPanel.isVisible()) {
    //     //             server.addLog(login + ": " + message);
    //     //         }
    //     //     } catch (Exception e) {
    //     //         System.out.println("Не удалось записать данные в файл - " + filePath);
    //     //     }
    //     // }

    //     // if (getClientStatus() == CLIENT_DISCONNECTED) {
    //     //     log.setText("[ " + timeStamp + " ] " + message);
    //     // }
    // }

    // /**
    //  * Метод получения событий
    //  */
    // private void getLog() {
    //     log.setText("");
    //     String login = name.getText();
    //     String filePath = "log_" + login + ".txt";
    //     try {
    //         File file = new File(filePath);
    //         FileReader fr = new FileReader(file);
    //         BufferedReader reader = new BufferedReader(fr);

    //         String line = reader.readLine();
    //         while (line != null) {
    //             log.setText(log.getText() + line + "\n");
    //             line = reader.readLine();
    //         }
    //         reader.close();

    //     } catch (FileNotFoundException e) {
    //         System.out.println("Не удалось записать данные в файл - " + filePath + ". Описание ошибки: " + e.getMessage());
    //     } catch (IOException e) {
    //         System.out.println("Не удалось записать данные в файл - " + filePath + ". Описание ошибки: " + e.getMessage());
    //     }
    // }
}

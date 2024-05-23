import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientWindow extends JFrame {

    /** Высота окна */
    private final int WINDOW_HEIGHT = 600;

    /** Ширина окна */
    private final int WINDOW_WIDHT = 500;

    /** Наименование окна */
    private final String TITLE_DEFAULT = "Чат (клиент)";

    /** Экземпляр клиента */
    private Client client;

    /** Элемента управления */
    private JTextField address, port, name, messages;
    private JPasswordField password;
    private JButton btnConnect, btnSendMessage, btnClearLog;
    private JTextArea logViewer;
    private JPanel topPanel, bottomPanel, centerPanel;
    private JScrollPane scrolPanel;

    /** Конструктор класса */
    ClientWindow(Client client) {
        this.client = client;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int WindowX = dim.width / 2 - WINDOW_WIDHT / 2;
        int WindowY = dim.height / 2 - WINDOW_HEIGHT / 2;
        setLocation(WindowX, WindowY);
        setSize(WINDOW_WIDHT, WINDOW_HEIGHT);

        initializeControlElement();
        updateStateControlElement();
        createActionListener();
        createStartWindow();
        setVisible(true);
    }

    /** Метод инициализации компонентов */
    private void initializeControlElement() {
        address = new JTextField("127.0.0.1");
        port = new JTextField("1010");
        name = new JTextField("Иван");
        password = new JPasswordField("*****************");
        messages = new JTextField();
        btnConnect = new JButton("Подключиться");
        btnSendMessage = new JButton("Отправить");
        btnClearLog = new JButton("Очистить журнал");
        logViewer = new JTextArea();
        logViewer.setMargin(new Insets(5, 5, 0, 5));
        topPanel = new JPanel(new GridLayout(2, 0));
        bottomPanel = new JPanel(new GridLayout(2, 0));
        centerPanel = new JPanel(new GridLayout(1, 0));
        scrolPanel = new JScrollPane();
    }

    /** Метод обновления состояние элементов управления */
    private void updateStateControlElement() {
        boolean isConnect = client.getStatusConnected();
        topPanel.setVisible(!isConnect);
        btnConnect.setEnabled(!isConnect);
        logViewer.setEditable(false);
        btnSendMessage.setEnabled(isConnect);
        messages.setEditable(isConnect);
        btnClearLog.setEnabled(true);

        String title = isConnect ? TITLE_DEFAULT + " " + name.getText() + ": Подключен"
                : TITLE_DEFAULT + " " + name.getText() + ": Отключен";
        setTitle(title);
    }

    /** Метод создания слушателей */
    private void createActionListener() {

        /** Слушатель события нажатия кнопки подключиться */
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsClient();
                client.requestConnect();
            }
        });

        /** Слушатель события нажатия кнопки отправить сообщение */
        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(messages.getText());
                messages.setText("");
            }
        });

        /** Слушатель события нажатия кнопки очистить журнал */
        btnClearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLogViewer();
            }
        });

        /** Слушатель события завершения ввода сообщения */
        messages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(messages.getText());
                messages.setText("");
            };
        });

        /** Слушатель события закрытия окна */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.requestDisconnect();
            }
        });
    }

    /** Метод создает элементы окна при старте */
    private void createStartWindow() {
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createCenterPanel());
    }

    /**
     * Метод создания верхней панели
     * 
     * @return
     */
    private JPanel createTopPanel() {

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
     * 
     * @return
     */
    private JPanel createBottomPanel() {
        JPanel messagePanel = new JPanel(new GridLayout(1, 0));
        messagePanel.add(messages);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(btnSendMessage);

        buttonPanel.add(btnClearLog, BorderLayout.EAST);
        bottomPanel.add(messagePanel);
        bottomPanel.add(buttonPanel);
        return bottomPanel;
    }

    /**
     * Метод создания панели сообщений
     * 
     * @return
     */
    private JScrollPane createCenterPanel() {
        centerPanel.add(logViewer);
        scrolPanel = new JScrollPane(centerPanel);
        return scrolPanel;
    }

    /** Метод установки данных полей пользователя */
    private void setFieldsClient() {
        client.setAddress(address.getText());
        client.setPort(port.getText());
        client.setLogin(name.getText());

        char[] value = password.getPassword();
        String setPassword = "";
        for (char c : value) {
            setPassword += c;
        }
        client.setPassword(setPassword);
    }

    /**
     * Метод очищает журнал сообщений
     */
    private void clearLogViewer() {
        logViewer.setText("");
        client.writeLog("Произведена очистка журнала");
    }

    /**
     * Метод добавления сообщения в журнал
     * 
     * @param message
     */
    public void appenedMessage(String message) {
        logViewer.append(message);
    }

    /**
     * Метод обновления состояния клиента
     * 
     * @param message
     */
    public void updateClientStatus() {
        updateStateControlElement();
    }
}

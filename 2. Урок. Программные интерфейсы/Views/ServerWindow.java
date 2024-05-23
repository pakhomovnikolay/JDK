import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ServerWindow extends JFrame {

    /** Высота окна */
    private final int WINDOW_HEIGHT = 600;

    /** Ширина окна */
    private final int WINDOW_WIDHT = 500;

    /** Наименование окна */
    private final String TITLE_DEFAULT = "Чат (сервер)";

    /** Экземпляр сервера */
    private Server server;

    /** Элемента управления */
    private JButton btnStart, btnStop, btnClearLog;
    private JTextArea logViewer;
    private JPanel bottomPanel, centerPanel;
    private JScrollPane scrolPanel;

    /** Конструктор класса */
    ServerWindow(Server server) {
        this.server = server;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int WindowX = dim.width / 2 - WINDOW_WIDHT / 2;
        int WindowY = dim.height / 2 - WINDOW_HEIGHT / 2;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        btnStart = new JButton("Запустить сервер");
        btnStop = new JButton("Остановить сервер");
        btnClearLog = new JButton("Очитстить журнал");
        logViewer = new JTextArea();
        bottomPanel = new JPanel();
        centerPanel = new JPanel();
        scrolPanel = new JScrollPane();
    }

    /** Метод обновления состояние элементов управления */
    private void updateStateControlElement() {
        boolean isRun = server.getStatusRunning();
        btnStart.setEnabled(!isRun);
        btnStop.setEnabled(isRun);
        logViewer.setEditable(false);

        String title = isRun ? TITLE_DEFAULT + ": В работе" : TITLE_DEFAULT + ": Остановлен";
        setTitle(title);
    }

    /** Метод создает элементы окна при старте */
    private void createStartWindow() {
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createLogPanel());
    }

    /**
     * Метод создания элементов управления
     * 
     * @return
     */
    private JPanel createBottomPanel() {
        bottomPanel = new JPanel(new GridLayout(0, 3));
        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);
        bottomPanel.add(btnClearLog);
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
        scrolPanel = new JScrollPane(centerPanel);
        return scrolPanel;
    }

    /** Метод создания слушателей */
    private void createActionListener() {

        /** Слушатель событие нажатия кнопки старт */
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startServer();
            }
        });

        /** Слушатель событие нажатия кнопки стоп */
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
            }
        });

        /** Слушатель событие нажатия кнопки очистить журнал */
        btnClearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLogViewer();
            }
        });

        /** Слушатель событие закрытия окна */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
            }
        });
    }

    /**
     * Метод очищает журнал сообщений
     */
    private void clearLogViewer() {
        logViewer.setText("");
        server.writeLog("Произведена очистка журнала");
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
     * Метод обновления состояния сервера
     * 
     * @param message
     */
    public void updateServerStatus() {
        updateStateControlElement();
    }
}

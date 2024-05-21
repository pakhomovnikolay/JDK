import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    /** Высота окна */
    private static final int WINDOW_HEIGHT = 555;

    /** Ширина окна */
    private static final int WINDOW_WIDHT = 504;

    /** Позиция по координате X */
    private static final int WINDOW_POS_X = 800;

    /** Позиция по координате Y */
    private static final int WINDOW_POS_Y = 300;

    /** Отображать окно настроек при старте */
    private static final boolean VIEW_WINDOW_ON_SATRT = true;

    /** Разрешение изменения резмеров окна */
    private static final boolean ACCESS_RESIZE = false;

    /** Наименование окна */
    private static final String TITLE = "Крестики-нолики";

    /** Добавляем элементы управления */
    /** Кнопки */
    JButton btnStart, btnExit, btnSettings;

    /** Игровое поле */
    Map map_game;

    /** Окно настроек */
    SettingsWindow settings_window;

    /** Конструктор класса */
    GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POS_X, WINDOW_POS_Y);
        setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
        setResizable(ACCESS_RESIZE);
        setTitle(TITLE);

        /** Добавляем панель */
        map_game = new Map();
        add(map_game);

        /**
         * Расположение кнопок запуска новой игры, завершения игры и открытия окна
         * настроек
         */
        JButton btnStart = new JButton("Новая игра");
        JButton btnExit = new JButton("Завершить");
        JButton btnSettings = new JButton("Настройки");

        /** Инициализируем панель элементов управления */
        JPanel ButtonPanel = new JPanel(new GridLayout(1, 3));
        ButtonPanel.add(btnStart);
        ButtonPanel.add(btnExit);
        ButtonPanel.add(btnSettings);
        add(ButtonPanel, BorderLayout.SOUTH);

        /** Иницализируем окно настроек */
        settings_window = new SettingsWindow(this);

        /** Добавляем слушателя событий для кнопки выхода из игры */
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(ABORT);
            }
        });

        /** Добавляем слушателя событий для кнопки запуска игры */
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_game.startNewGame(settings_window.getMode(), settings_window.getfSzX(), settings_window.getfSzY(),
                        settings_window.getvLen());
            }
        });

        /** Добавляем слушателя событий для кнопки открытия настроек */
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings_window.setVisible(true);
            }
        });

        /** Показываем форму */
        setVisible(true);

        /** Показываем форму настроект */
        settings_window.setVisible(VIEW_WINDOW_ON_SATRT);
    }
}

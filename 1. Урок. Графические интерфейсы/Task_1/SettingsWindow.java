import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JDialog {

    private static final int WINDOW_HEIGHT = 150;
    private static final int WINDOW_WIDHT = 350;
    private static final boolean ACCESS_RESIZE = false;
    private static final String TITLE = "Настройки игры";
    private final int MODE_COMPUTER = 0;
    private final int MODE_HUMAN = 1;

    private static int mode = 0;

    public int getMode() {
        return mode;
    }

    private static int fSzX = 3;

    public int getfSzX() {
        return fSzX;
    }

    private static int fSzY = 3;

    public int getfSzY() {
        return fSzY;
    }

    private static int vLen = 3;

    public int getvLen() {
        return vLen;
    }

    JButton btnSave = new JButton("Сохранить");
    JLabel modeCaption = new JLabel("Выберите режим игры");
    JRadioButton aiMode = new JRadioButton("Человек против компьютера");
    JRadioButton humanMode = new JRadioButton("Человек против человека");
    JTextField sizeGrid = new JTextField(getvLen());

    SettingsWindow(GameWindow gameWindow) {
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDHT, WINDOW_HEIGHT);
        setLocation(gameWindow.getX(), gameWindow.getY() / 2 + WINDOW_HEIGHT);
        setResizable(ACCESS_RESIZE);
        setTitle(TITLE);
        setModal(true);

        /** Расположение кнопок запуска новой игры и завершения */
        JPanel ButtonPanel = new JPanel(new GridLayout(5, 1));

        ButtonPanel.add(modeCaption);
        ButtonPanel.add(aiMode);
        ButtonPanel.add(humanMode);
        ButtonPanel.add(sizeGrid);
        ButtonPanel.add(btnSave);
        add(ButtonPanel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.map_game.startNewGame(mode, fSzX, fSzY, vLen);
                gameWindow.map_game.setBackground(Color.WHITE);
                setVisible(false);
            }
        });

        aiMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanMode.setSelected(false);
                aiMode.setSelected(true);
                mode = MODE_COMPUTER;
            }
        });

        humanMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aiMode.setSelected(false);
                humanMode.setSelected(true);
                mode = MODE_HUMAN;
            }
        });

        sizeGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int size = Integer.parseInt(sizeGrid.getText());
                    fSzX = size;
                    fSzY = size;
                    vLen = size;
                } catch (RuntimeException exp) {
                    throw new RuntimeException(exp.getMessage());
                }
            }
        });
    }
}

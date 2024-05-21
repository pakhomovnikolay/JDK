import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private final int DOT_PADDING = 5;
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private final int MODE_COMPUTER = 0;
    private final int MODE_HUMAN = 1;
    private final int Gamer_1 = 0;
    private final int Gamer_2 = 1;

    private boolean isGameOver;
    private boolean isInitialized = false;
    private int fieldSizeX;
    private int fieldSizeY;
    private char[][] fields;
    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;
    private int mode;
    private int fSzX;
    private int fSzY;
    private int vLen;
    private int lastCellX;
    private int lastCellY;
    private int currGamer;

    public boolean getisGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
        if (isGameOver) {
            isInitialized = false;
        }
    }

    Map() {
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    void startNewGame(int mode, int fSzX, int fSzY, int vLen) {
        this.mode = mode;
        this.fSzX = fSzX;
        this.fSzY = fSzY;
        this.vLen = vLen;

        initMap();
        repaint();
    }

    private void initMap() {
        fieldSizeX = fSzX;
        fieldSizeY = fSzY;
        fields = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                fields[i][j] = EMPTY_DOT;
            }
        }
        isInitialized = true;
        isGameOver = false;
        currGamer = Gamer_1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialized)
            return;

        panelWidth = getWidth();
        panelHeight = getHeight();
        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;

        g.setColor(Color.black);
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fields[i][j] == EMPTY_DOT)
                    continue;

                if (fields[i][j] == HUMAN_DOT) {
                    g.setColor(Color.GREEN);
                    g.fillOval(j * cellWidth + DOT_PADDING, i * cellHeight + DOT_PADDING, cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (fields[i][j] == AI_DOT) {
                    g.setColor(Color.BLUE);
                    g.fillOval(j * cellWidth + DOT_PADDING, i * cellHeight + DOT_PADDING, cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Unexeption value" + fields[i][j] + "in cell: x = " + j + "y = " + i);
                }
            }
        }

        if (isGameOver) {
            showMessagegameOver(g, lastCellX, lastCellY);
            return;
        }
    }

    private void update(MouseEvent e) {
        if (!isInitialized || isGameOver)
            return;

        lastCellX = e.getX() / cellWidth;
        lastCellY = e.getY() / cellHeight;
        if (!IsValidCell(lastCellX, lastCellY) || !isEmptyCell(lastCellX, lastCellY)) {
            return;
        }
        fields[lastCellY][lastCellX] = HUMAN_DOT;
        checkGameOver(lastCellX, lastCellY);
        repaint();
        if (isGameOver || mode == MODE_HUMAN) {
            nextGamer();
            return;
        }

        aiTurn();
        if (isGameOver)
            return;
    }

    private void nextGamer() {
        if (currGamer == Gamer_1) {
            currGamer = Gamer_2;
        } else {
            currGamer = Gamer_1;
        }
    }

    private boolean IsValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return fields[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {
        if (mode != MODE_COMPUTER) {
            return;
        }

        int MaxCount = 0;
        int lastX = 0;
        int lastY = 0;
        boolean finded = false;

        for (int y = 0; y < fieldSizeY; y++) {
            int count = 0;
            for (int x = 0; x < fieldSizeX; x++) {
                if (fields[y][x] == HUMAN_DOT) {
                    count++;
                    if (count > MaxCount) {
                        MaxCount = count;
                    }
                } else if (MaxCount == count && MaxCount > 1 && isEmptyCell(x, y)) {
                    lastX = x;
                    lastY = y;
                    finded = true;
                }
            }
        }

        if (!finded) {
            for (int x = 0; x < fieldSizeX; x++) {
                int count = 0;
                for (int y = 0; y < fieldSizeY; y++) {
                    if (fields[y][x] == HUMAN_DOT) {
                        count++;
                        if (count > MaxCount) {
                            MaxCount = count;
                        }
                    } else if (MaxCount == count && MaxCount > 1 && isEmptyCell(x, y)) {
                        lastX = x;
                        lastY = y;
                        finded = true;
                    }
                }
            }
        }

        if (!finded) {
            int count = 0;
            for (int i = 0; i < fieldSizeX; i++) {
                if (fields[i][i] == HUMAN_DOT) {
                    count++;
                    if (count > MaxCount) {
                        MaxCount = count;
                    }
                } else if (MaxCount == count && MaxCount > 1 && fields[i][i] == EMPTY_DOT) {
                    lastX = i;
                    lastY = i;
                    finded = true;
                }
            }
            if (!finded) {
                count = 0;
                for (int i = 0; i < fieldSizeX; i++) {
                    if (fields[fieldSizeX - 1 - i][fieldSizeX - 1 - i] == HUMAN_DOT) {
                        count++;
                        if (count > MaxCount) {
                            MaxCount = count;
                        }
                    } else if (MaxCount == count && MaxCount > 1
                            && fields[fieldSizeX - 1 - i][fieldSizeX - 1 - i] == EMPTY_DOT) {
                        lastX = fieldSizeX - 1 - i;
                        lastY = fieldSizeX - 1 - i;
                        finded = true;
                    }
                }
            }
        }

        if (!finded) {
            int count = 0;
            for (int i = 0; i < fieldSizeX; i++) {
                if (fields[fieldSizeY - 1 - i][i] == HUMAN_DOT) {
                    count++;
                    if (count > MaxCount) {
                        MaxCount = count;
                    }
                } else if (MaxCount == count && MaxCount > 1 && fields[fieldSizeY - 1 - i][i] == EMPTY_DOT) {
                    lastX = i;
                    lastY = fieldSizeY - 1 - i;
                    finded = true;
                }
            }
            if (!finded) {
                count = 0;
                for (int i = 0; i < fieldSizeX; i++) {
                    if (fields[i][fieldSizeY - 1 - i] == HUMAN_DOT) {
                        count++;
                        if (count > MaxCount) {
                            MaxCount = count;
                        }
                    } else if (MaxCount == count && MaxCount > 1 && fields[i][fieldSizeY - 1 - i] == EMPTY_DOT) {
                        lastX = fieldSizeY - 1 - i;
                        lastY = i;
                        finded = true;
                    }
                }
            }
        }

        if (finded) {
            lastCellX = lastX;
            lastCellY = lastY;
        } else {
            do {
                lastCellX = RANDOM.nextInt(fieldSizeX);
                lastCellY = RANDOM.nextInt(fieldSizeY);
            } while (!isEmptyCell(lastCellX, lastCellY));
        }

        fields[lastCellY][lastCellX] = AI_DOT;
        checkGameOver(lastCellX, lastCellY);
        repaint();
    }

    private boolean checkWin(char c) {

        boolean isWin = false;

        for (int i = 0; i < fieldSizeY; i++) {
            int LenY = 0;
            for (int j = 0; j < fieldSizeX; j++) {
                if (fields[i][j] == c) {
                    LenY++;
                }
            }
            isWin = isWin || LenY >= vLen;
        }

        for (int i = 0; i < fieldSizeX; i++) {
            int LenX = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (fields[j][i] == c) {
                    LenX++;
                }
            }
            isWin = isWin || LenX >= vLen;
        }

        int BottomToUp = 0;
        for (int i = 0; i < fieldSizeY; i++) {
            if (fields[i][i] == c) {
                BottomToUp++;
            }
        }

        int UpToottom = 0;
        for (int i = 0; i < fieldSizeY; i++) {
            if (fields[fieldSizeY - 1 - i][i] == c) {
                UpToottom++;
            }
        }
        return isWin || BottomToUp >= vLen || UpToottom >= vLen;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fields[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkGameOver(int x, int y) {
        isGameOver = checkWin(fields[y][x]) || isMapFull();
    }

    private void showMessagegameOver(Graphics g, int x, int y) {

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Verdana", Font.BOLD, 25));

        if (isGameOver) {
            if (checkWin(fields[y][x])) {
                if (fields[y][x] == HUMAN_DOT) {
                    g.drawString("Победил игрок", 135, getHeight() / 2);
                } else if (fields[y][x] == AI_DOT) {
                    g.drawString("Победил компьютер", 100, getHeight() / 2);
                }
            } else if (isMapFull()) {
                g.drawString("Ничья", 195, getHeight() / 2);
            }
        }
    }
}

package oop.h4t.tetris;// JTetris.java

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.Toolkit;
import java.io.FileInputStream;

/**
 * Group 15: H4T
 * On 30/11/15.
 */

/**
 * Chuong trinh choi Tetris don gian.
 * <p>
 * Luat choi:
 * <p>
 * Su dung phim j-k-l de di chuyen, n de cho roi (hoac phim 4-5-6 0)
 * Trong khi di chuyen, cac dong da duoc lap day se chuyen mau xanh.
 * Xoa tu 1 - 4 dong se lan luot duoc diem la 5, 10, 20, 40.
 */

/*
 Chu y:
 -"currentPiece" chi vao hinh hien tai dang roi, hoac la null khi khong co hinh nao.
 -tick() chuyen hinh hien tai
 -Mot doi tuong thoi gian khi hinh roi
 -Board.undo() duoc su dung de chuyen hinh nhu vi tri cu va Board.place()duoc su dung de chuyen hinh trong vi tri moi.
*/

public class JTetris extends JComponent {

    // size of the board in blocks
    //kích thước board
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;

    // Khỏng trống tối thiểu phía trên. Kết thúc trò chơi nếu khoảng trống < TOPSPACE
    public static final int TOP_SPACE = 4;

    // Khi tesMode = true, chỉ có 100 pieces
    protected boolean testMode = false;
    public final int TEST_LIMIT = 100;

    // Phương pháp tối ưu
    protected boolean DRAW_OPTIMIZE = false;

    // Cấu trúc Board
    protected Board board;
    protected Piece[] pieces;


    // Piece hiện tại
    protected Piece currentPiece;
    protected int currentX;
    protected int currentY;
    protected boolean moved;    // Kiểm tra pieces đã di chuyển hay chưa

    //nextPiece.

    protected Piece nextPiece;
    // Tạo piece tại tọa độ x,y
    protected Piece newPiece;
    protected int newX;
    protected int newY;

    // Trạng thái trò chơi
    protected boolean gameOn;    // true nếu đang chơi
    protected int count;         // số lượng piece đã chơi
    protected long startTime;    // Thời gian chơi
    protected Random random;     // Random pieces

    // Controls
    protected JLabel countLabel;
    protected JLabel scoreLabel;
    protected int score;
    private int high_score;
    protected JLabel timeLabel;
    protected JButton startButton;
    protected JButton stopButton;
    protected javax.swing.Timer timer;
    protected JSlider speed;
    protected JCheckBox testButton;
    protected JPanel nextPiecePanel;
    public final int DELAY = 400;    // milliseconds mỗi tick

    public Piece getNextPiece() {
        return nextPiece;
    }

    public int getHigh_score() {
        return high_score;
    }


    public int getCount() {
        return count;
    }

    public int getScore() {
        return score;
    }


    JTetris(int pixels) {
        super();
        setPreferredSize(new Dimension((WIDTH * pixels) + 2,
                (HEIGHT + TOP_SPACE) * pixels + 2));
        gameOn = false;

        pieces = Piece.getPieces();
        board = new Board(WIDTH, HEIGHT + TOP_SPACE);

        // LEFT
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(LEFT);
                    }
                }, "left", KeyStroke.getKeyStroke('4'), WHEN_IN_FOCUSED_WINDOW
        );
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(LEFT);
                    }
                }, "left", KeyStroke.getKeyStroke('j'), WHEN_IN_FOCUSED_WINDOW
        );


        // RIGHT
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(RIGHT);
                    }
                }, "right", KeyStroke.getKeyStroke('6'), WHEN_IN_FOCUSED_WINDOW
        );
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(RIGHT);
                    }
                }, "right", KeyStroke.getKeyStroke('l'), WHEN_IN_FOCUSED_WINDOW
        );


        // ROTATE
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(ROTATE);
                    }
                }, "rotate", KeyStroke.getKeyStroke('5'), WHEN_IN_FOCUSED_WINDOW
        );
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(ROTATE);
                    }
                }, "rotate", KeyStroke.getKeyStroke('k'), WHEN_IN_FOCUSED_WINDOW
        );


        // DROP
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(DROP);
                    }
                }, "drop", KeyStroke.getKeyStroke('0'), WHEN_IN_FOCUSED_WINDOW
        );
        registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tick(DROP);
                    }
                }, "drop", KeyStroke.getKeyStroke('n'), WHEN_IN_FOCUSED_WINDOW
        );

        timer = new javax.swing.Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick(DOWN);
            }
        });

        requestFocusInWindow();
    }


    /**
     * Bấm giờ và start
     */
    public void startGame() {
        try {
            FileInputStream fi = new FileInputStream("high_score.txt"); // Duyệt điểm cao nhất từ file
            Scanner sc = new Scanner(fi);
            if (sc.hasNextInt()) {
                high_score = sc.nextInt();
            } else high_score = 0;
        } catch (Exception e) {
            high_score = 0;
            e.printStackTrace();
        } finally {
            board = new Board(WIDTH, HEIGHT + TOP_SPACE);

            repaint();
            count = 0;
            score = 0;
            updateCounters();
            gameOn = true;

            // Set mode based on checkbox at start of game
            testMode = testButton.isSelected();

            if (testMode) random = new Random(0);    // same seq every time
            else random = new Random(); // diff seq each game
            nextPiece = pieces[(int) (pieces.length * random.nextDouble())];
            enableButtons();
            timeLabel.setText(" ");
            addNewPiece();
            timer.start();
            startTime = System.currentTimeMillis();

        }
    }

    /**
     * Cai dat che do kich hoat cua nut strat/stop
     */
    private void enableButtons() {
        startButton.setEnabled(!gameOn);
        stopButton.setEnabled(gameOn);
    }

    /**
     * Dung tro choi
     */
    public void stopGame() {
        //PrintWriter pw=test PrintWriter(HIGH_SCORE);
        gameOn = false;
        enableButtons();
        timer.stop();

        long delta = (System.currentTimeMillis() - startTime) / 10;
        timeLabel.setText(Double.toString(delta / 100.0) + " seconds");

    }


    /**
     * Cho hinh, can cai dat hinh vao trong tro choi va cho no la hinh hien tai.
     * Can ve lai hinh,
     * Tro choi phai goi commint() (trai thai luu va khoi phuc cac can choi)
     */
    public int setCurrent(Piece piece, int x, int y) {
        int result = board.place(piece, x, y);

        if (result <= Board.PLACE_ROW_FILLED) { // SUCESS
            // repaint the rect where it used to be
            if (currentPiece != null) repaintPiece(currentPiece, currentX, currentY);
            currentPiece = piece;
            currentX = x;
            currentY = y;
            // repaint the rect where it is now
            repaintPiece(currentPiece, currentX, currentY);

        } else {
            board.undo();
        }

        return (result);
    }


    /**
     * Chon hinh tiep theo ngau nhien va cai dat trong startGame().
     */
    public Piece pickNextPiece() {
        int pieceNum;

        pieceNum = (int) (pieces.length * random.nextDouble());

        Piece piece = pieces[pieceNum];

        return (piece);
    }


    /**
     * Them mot hinh bat ky vao dau cua ban choi.
     */
    public void addNewPiece() {
        count++;
        score++;

        if (testMode && count == TEST_LIMIT + 1) {
            stopGame();
            return;
        }

        // commit things the way they are
        board.commit();
        currentPiece = nextPiece;

        nextPiece = pickNextPiece();

        // Center it up at the top
        int px = (board.getWidth() - currentPiece.getWidth()) / 2;
        int py = board.getHeight() - currentPiece.getHeight();

        // add the test piece to be in play
        int result = setCurrent(currentPiece, px, py);

        // This probably never happens, since
        // the blocks at the top allow space
        // for test pieces to at least be added.
        if (result > Board.PLACE_ROW_FILLED) {
            stopGame();
        }

        updateCounters();
    }

    /**
     * Nang cap biet dem cho cac gia tri tiep theo.
     */
    private void updateCounters() {
        countLabel.setText("Pieces " + count);
        scoreLabel.setText("Score " + score);
        nextPiecePanel.repaint();

    }


    /**
     * Tao vi tri moi cua hinh.)
     */
    public void computeNewPosition(int verb) {
        newPiece = currentPiece;
        newX = currentX;
        newY = currentY;

        switch (verb) {
            case LEFT:
                newX--;
                break;

            case RIGHT:
                newX++;
                break;

            case ROTATE:
                newPiece = newPiece.fastRotation();

                newX = newX + (currentPiece.getWidth() - newPiece.getWidth()) / 2;
                newY = newY + (currentPiece.getHeight() - newPiece.getHeight()) / 2;
                break;

            case DOWN:
                newY--;
                break;

            case DROP:
                newY = board.dropHeight(newPiece, newX);

                if (newY > currentY) {
                    newY = currentY;
                }
                break;

            default:
                throw new RuntimeException("Bad verb");
        }

    }

    //Cac phim dieu khien
    public static final int ROTATE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DROP = 3;
    public static final int DOWN = 4;

    /**
     * Coi toi su thay doi vua hinh hien tai.
     */
    public void tick(int verb) {
        if (!gameOn) return;

        if (currentPiece != null) {
            board.undo();    // xóa hình tại vị trí cũ
        }

        computeNewPosition(verb);

        int result = setCurrent(newPiece, newX, newY);

        // neu xoa hang co van de, ve lai toan bo ban choi
        if (result == Board.PLACE_ROW_FILLED) {
            repaint();

        }


        boolean failed = (result >= Board.PLACE_OUT_BOUNDS);

        // neu khong lam viec, quay lai trang thai truoc do
        if (failed) {
            if (currentPiece != null) board.place(currentPiece, currentX, currentY);
            repaintPiece(currentPiece, currentX, currentY);
        }

		/*
        Hinh tiep xu voi hang duoi cung the nao
		*/
        if (failed && verb == DOWN && !moved) {

            int cleared = board.clearRows();
            if (cleared > 0) {
                // tinh diem 5, 10, 20, 40 khi co hang bi xoa. Tao tieng beep khi xoa 4 hang
                switch (cleared) {
                    case 1:
                        score += 5;
                        break;
                    case 2:
                        score += 10;
                        break;
                    case 3:
                        score += 20;
                        break;
                    case 4:
                        score += 40;
                        Toolkit.getDefaultToolkit().beep();
                        break;
                    default:
                        score += 50;
                }
                updateCounters();
                repaint();

            }

            if (board.getMaxHeight() > board.getHeight() - TOP_SPACE) {
                stopGame();
            } else {
                addNewPiece();

            }
        }

        moved = (!failed && verb != DOWN);
    }

    public void repaintPiece(Piece piece, int x, int y) {
        if (DRAW_OPTIMIZE) {
            int px = xPixel(x);
            int py = yPixel(y + piece.getHeight() - 1);
            int pwidth = xPixel(x + piece.getWidth()) - px;
            int pheight = yPixel(y - 1) - py;

            repaint(px, py, pwidth, pheight);
        } else {
            repaint();
        }
    }

    private final float dX() {
        return (((float) (getWidth() - 2)) / board.getWidth());
    }

    private final float dY() {
        return (((float) (getHeight() - 2)) / board.getHeight());
    }

    private final int xPixel(int x) {
        return (Math.round(1 + (x * dX())));
    }

    private final int yPixel(int y) {
        return (Math.round(getHeight() - 1 - (y + 1) * dY()));
    }

    public void paintComponent(Graphics g) {
//        g.setColor(Piece.change(currentPiece.getColor()));

        g.setColor(Color.black);
        // Ve hinh vuong
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Ve duong ngan voi tren
        int spacerY = yPixel(board.getHeight() - TOP_SPACE - 1);
        g.drawLine(0, spacerY, getWidth() - 1, spacerY);


        Rectangle clip = null;
        if (DRAW_OPTIMIZE) {
            clip = g.getClipBounds();
        }


        final int dx = Math.round(dX() - 2);
        final int dy = Math.round(dY() - 2);
        final int bWidth = board.getWidth();

        int x, y;
        for (x = 0; x < bWidth; x++) {
            int left = xPixel(x);

            int right = xPixel(x + 1) - 1;

            if (DRAW_OPTIMIZE && clip != null) {
                if ((right < clip.x) || (left >= (clip.x + clip.width))) continue;
            }
            final int yHeight = board.getColumnHeight(x);
            for (y = 0; y < yHeight; y++) {
                if (board.getGrid(x, y)) {
                    boolean filled = (board.getRowWidth(y) == bWidth);
                    if (filled) g.setColor(Color.green);

                    g.fillRect(left + 1, yPixel(y) + 1, dx, dy);    // +1 khi ra ngoai duong bao

                    if (filled) g.setColor(Color.black);
                }
            }
        }
    }


    /**
     * Cap nhat thoi gian hien tai.
     */
    public void updateTimer() {
        double value = ((double) speed.getValue()) / speed.getMaximum();
        timer.setDelay((int) (DELAY - value * DELAY));
    }


    public JComponent createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Preview nextPiece;
        nextPiecePanel = new nextPiece(this);
        panel.add(nextPiecePanel);
        // COUNT

        countLabel = new JLabel("0");
        panel.add(countLabel);

        // SCORE
        scoreLabel = new JLabel("0");
        panel.add(scoreLabel);

        // TIME
        timeLabel = new JLabel(" ");
        panel.add(timeLabel);

        panel.add(Box.createVerticalStrut(12));

        // START button
        startButton = new JButton("Start");
        panel.add(startButton);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // STOP button
        stopButton = new JButton("Stop");
        panel.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });

        enableButtons();

        JPanel row = new JPanel();

        // SPEED slider
        panel.add(Box.createVerticalStrut(12));
        row.add(new JLabel("Speed:"));
        speed = new JSlider(0, 200, 75);    // min, max, current
        speed.setPreferredSize(new Dimension(100, 15));

        updateTimer();
        row.add(speed);

        panel.add(row);
        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateTimer();
            }
        });

        testButton = new JCheckBox("Test sequence");
        panel.add(testButton);


        return panel;
    }


    public static JFrame createFrame(JTetris tetris) {
        JFrame frame = new JFrame("Tetris Game H4T");

        frame.setResizable(false);
        JComponent container = (JComponent) frame.getContentPane();
        container.setLayout(new BorderLayout(5, 5));
        container.add(tetris, BorderLayout.CENTER);

        JComponent controls = tetris.createControlPanel();
        container.add(controls, BorderLayout.EAST);

        controls.add(Box.createVerticalStrut(2));
        JButton quit = new JButton("Quit");
        controls.add(quit);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        return frame;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JTetris tetris = new JTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

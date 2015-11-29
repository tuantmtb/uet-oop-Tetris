package oop.h4t.tetris;// JTetris.java

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.Toolkit;


/**
 Chuong trinh choi Tetris don gian.

 Luat choi:

 Su dung phim j-k-l de di chuyen, n de cho roi (hoac phim 4-5-6 0)
 Trong khi di chuyen, cac dong da duoc lap day se chuyen mau xanh.
 Xoa tu 1 - 4 dong se lan luot duoc diem la 5, 10, 20, 40.
*/

/*
 Chu y:
 -"currentPiece" chi vao hinh hien tai dang roi, hoac la null khi khong co hinh nao.
 -tick() chuyen hinh hien tai
 -Mot doi tuong thoi gian khi hinh roi
 -Board.undo() duoc su dung de chuyen hinh nhu vi tri cu va Board.place()duoc su dung de chuyen hinh trong vi tri moi.
*/

public class JTetris extends JComponent {
//	// Kich co ban choi
//	public static final int WIDTH = 10;
//	public static final int HEIGHT = 20;
//
//	// Khoang trong toi thieu phia tren cho phep hien hinh moi. Tro choi ket thuc khi khoang trong <4.
//	public static final int TOP_SPACE = 4;
//
//	// Khi nhan gia tri tru, choi voi so hinh fix la 100
//	protected boolean testMode = false;
//	public final int TEST_LIMIT = 100;
//
//	// Phuong phap toi uu
//	protected boolean DRAW_OPTIMIZE = false;
//
//	// Cau truc du lieu ban choi
//	protected Board board;
//	protected Piece[] pieces;
//
//
//	// Hinh hien tai
//	protected Piece currentPiece;
//	protected int currentX;
//	protected int currentY;
//	protected boolean moved;	// kiem tra da di chuyen hinh hay chua
//
//
//	// Tao hinh tai toa do x, y
//	protected Piece newPiece;
//	protected int newX;
//	protected int newY;
//
//	// Trang thai tro choi
//	protected boolean gameOn;	// true neu dang choi
//	protected int count;		 // bao nhieu hinh da choi
//	protected long startTime;	// su dung de do thoi gian da choi
//	protected Random random;	 // tao ngau nhien hinh moi
//
//
//	// Dieu khien
//	protected JLabel countLabel;
//	protected JLabel scoreLabel;
//	protected int score;
//	protected JLabel timeLabel;
//	protected JButton startButton;
//	protected JButton stopButton;
//	protected javax.swing.Timer timer;
//	protected JSlider speed;
//	protected JCheckBox testButton;
//
//	public final int DELAY = 400;	// milliseconds
//
//
//	JTetris(int width, int height) {
//		super();
//
//		setPreferredSize(new Dimension(width, height));
//		gameOn = false;
//
//		pieces = Piece.getPieces();
//		board = new Board(WIDTH, HEIGHT + TOP_SPACE);
//
//
//		/*
//		 Su dung ban phim de dieu khien
//		*/
//
//		// Trai
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(LEFT);
//				}
//			}, "left", KeyStroke.getKeyStroke('5'), WHEN_IN_FOCUSED_WINDOW
//		);
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(LEFT);
//				}
//			}, "left", KeyStroke.getKeyStroke('j'), WHEN_IN_FOCUSED_WINDOW
//		);
//
//
//		// Phai
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(RIGHT);
//				}
//			}, "right", KeyStroke.getKeyStroke('6'), WHEN_IN_FOCUSED_WINDOW
//		);
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(RIGHT);
//				}
//			}, "right", KeyStroke.getKeyStroke('l'), WHEN_IN_FOCUSED_WINDOW
//		);
//
//
//		// Quay
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(ROTATE);
//				}
//			}, "rotate", KeyStroke.getKeyStroke('5'), WHEN_IN_FOCUSED_WINDOW
//		);
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(ROTATE);
//				}
//			}, "rotate", KeyStroke.getKeyStroke('k'), WHEN_IN_FOCUSED_WINDOW
//		);
//
//
//		// Tha
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(DROP);
//				}
//			}, "drop", KeyStroke.getKeyStroke('0'), WHEN_IN_FOCUSED_WINDOW
//		);
//		registerKeyboardAction(
//			new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					tick(DROP);
//				}
//			}, "drop", KeyStroke.getKeyStroke('n'), WHEN_IN_FOCUSED_WINDOW
//		);
//
//
//		// Tao doi tuong Timer de tinh thoi gian chuyen dong xuong cua mot hinh
//		timer = new javax.swing.Timer(DELAY, new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				tick(DOWN);
//			}
//		});
//
//		requestFocusInWindow();
//	}
//
//
//
//	/**
//	 Trai thai hien tai va thoi gian choi
//	*/
//	public void startGame() {
//		// khoi phuc lai trang thai choi
//		board = new Board(WIDTH, HEIGHT + TOP_SPACE);
//
//		// Ve van co moi
//		repaint();
//
//		count = 0;
//		score = 0;
//		updateCounters();
//		gameOn = true;
//
//		// Dat che do checkbox tai luc khoi dong tro choi.
//
//		if (testMode) random = new Random(0);
//		else random = new Random();
//
//		enableButtons();
//		timeLabel.setText(" ");
//		addNewPiece();
//		timer.start();
//		startTime = System.currentTimeMillis();
//	}
//
//
//	/**
//	 Cai dat che do kich hoat cua nut strat/stop.
//	*/
//	private void enableButtons() {
//		startButton.setEnabled(!gameOn);
//		stopButton.setEnabled(gameOn);
//	}
//
//	/**
//	 Dung tro choi
//	*/
//	public void stopGame() {
//		gameOn = false;
//		enableButtons();
//		timer.stop();
//
//		long delta = (System.currentTimeMillis() - startTime)/10;
//		timeLabel.setText(Double.toString(delta/100.0) + " seconds");
//
//	}
//
//
//	/**
//	 Cho hinh, can cai dat hinh vao trong tro choi va cho no la hinh hien tai.
//	 Can ve lai hinh,
//	 Tro choi phai goi commint() (trai thai luu va khoi phuc cac can choi)
//
//	*/
//	public int setCurrent(Piece piece, int x, int y) {
//		int result = board.place(piece, x, y);
//
//		if (result <= Board.PLACE_ROW_FILLED) { // SUCESS
//
//			if (currentPiece != null) repaintPiece(currentPiece, currentX, currentY);
//			currentPiece = piece;
//			currentX = x;
//			currentY = y;
//
//			repaintPiece(currentPiece, currentX, currentY);
//		}
//		else {
//			board.undo();
//		}
//
//		return(result);
//	}
//
//
//	/**
//	 Chon hinh tiep theo ngau nhien va cai dat trong startGame().
//	*/
//	public Piece pickNextPiece() {
//		int pieceNum;
//
//		pieceNum = (int) (pieces.length * random.nextDouble());
//
//		Piece piece	 = pieces[pieceNum];
//
//		return(piece);
//	}
//
//
//	/**
//	 Them mot hinh bat ky vao dau cua ban choi.
//	*/
//	public void addNewPiece() {
//		count++;
//		score++;
//
//		if (testMode && count == TEST_LIMIT+1) {
//			 stopGame();
//			 return;
//		}
//
//		// Luu tam thoi trang thai hien tai
//		board.commit();
//		currentPiece = null;
//
//		Piece piece = pickNextPiece();
//
//		int px = (board.getWidth() - piece.getWidth())/2;
//		int py = board.getHeight() - piece.getHeight();
//
//		int result = setCurrent(piece, px, py);
//
//		if (result>Board.PLACE_ROW_FILLED) {
//			stopGame();
//		}
//
//		updateCounters();
//	}
//
//	/**
//	 Nang cap biet dem cho cac gia tri tiep theo.
//	 */
//	private void updateCounters() {
//		countLabel.setText("Pieces " + count);
//		scoreLabel.setText("Score " + score);
//	}
//
//
//	/**
//	 Tao vi tri moi cua hinh.)
//	*/
//	public void computeNewPosition(int verb) {
//
//		newPiece = currentPiece;
//		newX = currentX;
//		newY = currentY;
//
//		switch (verb) {
//			case LEFT: newX--; break;
//
//			case RIGHT: newX++; break;
//
//			case ROTATE:
//				newPiece = newPiece.fastRotation();
//
//				newX = newX + (currentPiece.getWidth() - newPiece.getWidth())/2;
//				newY = newY + (currentPiece.getHeight() - newPiece.getHeight())/2;
//				break;
//
//			case DOWN: newY--; break;
//
//			case DROP:
//			 newY = board.dropHeight(newPiece, newX);
//
//			 if (newY > currentY) {
//				 newY = currentY;
//			 }
//			 break;
//
//			default:
//				 throw new RuntimeException("Bad verb");
//		}
//
//	}
//
//	//Cac phim dieu khien
//	public static final int ROTATE = 0;
//	public static final int LEFT = 1;
//	public static final int RIGHT = 2;
//	public static final int DROP = 3;
//	public static final int DOWN = 4;
//	/**
//	 Coi toi su thay doi vua hinh hien tai.
//	*/
//	public void tick(int verb) {
//		if (!gameOn) return;
//
//		if (currentPiece != null) {
//			board.undo();	// xoa hinh tai vi tri cu
//		}
//
//		computeNewPosition(verb);
//
//		int result = setCurrent(newPiece, newX, newY);
//
//		// neu xoa hang co van de, ve lai toan bo ban choi
//		if (result ==  Board.PLACE_ROW_FILLED) {
//			repaint();
//		}
//
//
//		boolean failed = (result >= Board.PLACE_OUT_BOUNDS);
//
//		// neu khong lam viec, quay lai trang thai truoc do
//		if (failed) {
//			if (currentPiece != null) board.place(currentPiece, currentX, currentY);
//			repaintPiece(currentPiece, currentX, currentY);
//		}
//
//		/*
//		 Hinh tiep xu vois hang duoi cung the nao?
//		*/
//		if (failed && verb==DOWN && !moved) {
//
//			int cleared = board.clearRows();
//			if (cleared > 0) {
//				// tinh diem 5, 10, 20, 40 khi co hang bi xoa. Tao tieng beep khi xoa 4 hang
//				switch (cleared) {
//					case 1: score += 5;	 break;
//					case 2: score += 10;  break;
//					case 3: score += 20;  break;
//					case 4: score += 40; Toolkit.getDefaultToolkit().beep(); break;
//					default: score += 50;
//				}
//				updateCounters();
//				repaint();
//			}
//
//
//			if (board.getMaxHeight() > board.getHeight() - TOP_SPACE) {
//				stopGame();
//			}
//
//			else {
//				addNewPiece();
//			}
//		}
//
//		moved = (!failed && verb!=DOWN);
//	}
//
//
//	public void repaintPiece(Piece piece, int x, int y) {
//		if (DRAW_OPTIMIZE) {
//			int px = xPixel(x);
//			int py = yPixel(y + piece.getHeight() - 1);
//			int pwidth = xPixel(x+piece.getWidth()) - px;
//			int pheight = yPixel(y-1) - py;
//
//			repaint(px, py, pwidth, pheight);
//		}
//		else {
//			repaint();
//		}
//	}
//
//
//	private final float dX() {
//		return( ((float)(getWidth()-2)) / board.getWidth() );
//	}
//
//	private final float dY() {
//		return( ((float)(getHeight()-2)) / board.getHeight() );
//	}
//
//	private final int xPixel(int x) {
//		return(Math.round(1 + (x * dX())));
//	}
//
//	private final int yPixel(int y) {
//		return(Math.round(getHeight() -1 - (y+1)*dY()));
//	}
//
//
//	public void paintComponent(Graphics g) {
//
//		// Ve hinh vuong
//		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
//
//
//		// Ve duong ngan voi tren
//		int spacerY = yPixel(board.getHeight() - TOP_SPACE - 1);
//		g.drawLine(0, spacerY, getWidth()-1, spacerY);
//
//
//		Rectangle clip = null;
//		if (DRAW_OPTIMIZE) {
//			clip = g.getClipBounds();
//		}
//
//		final int dx = Math.round(dX()-2);
//		final int dy = Math.round(dY()-2);
//		final int bWidth = board.getWidth();
//		//final int bHeight = board.getHeight();
//
//		int x, y;
//		for (x=0; x<bWidth; x++) {
//			int left = xPixel(x);
//
//			int right = xPixel(x+1) -1;
//
//			if (DRAW_OPTIMIZE && clip!=null) {
//				if ((right<clip.x) || (left>=(clip.x+clip.width))) continue;
//			}
//
//			final int yHeight = board.getColumnHeight(x);
//			for (y=0; y<yHeight; y++) {
//				if (board.getGrid(x, y)) {
//					final boolean filled = (board.getRowWidth(y)==bWidth);
//					if (filled) g.setColor(Color.green);
//
//					g.fillRect(left+1, yPixel(y)+1, dx, dy);	// +1 khi ra ngoai duong bao
//
//					if (filled) g.setColor(Color.black);
//				}
//			}
//		}
//	}
//
//
//	/**
//	 Cap nhat thoi gian hien tai.
//	*/
//	public void updateTimer() {
//		double value = ((double)speed.getValue())/speed.getMaximum();
//		timer.setDelay((int)(DELAY - value*DELAY));
//	}
//
//
//	/**
//
//	*/
//	public Container createControlPanel() {
//		Container panel = Box.createVerticalBox();
//
//		// Dem
//		countLabel = new JLabel("0");
//		panel.add(countLabel);
//
//		// Diem
//		scoreLabel = new JLabel("0");
//		panel.add(scoreLabel);
//
//		// Thoi gian
//		timeLabel = new JLabel(" ");
//		panel.add(timeLabel);
//
//		panel.add(Box.createVerticalStrut(12));
//
//		// nut START
//		startButton = new JButton("Start");
//		panel.add(startButton);
//		startButton.addActionListener( new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				startGame();
//			}
//		});
//
//		// Nut dung
//		stopButton = new JButton("Stop");
//		panel.add(stopButton);
//		stopButton.addActionListener( new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				stopGame();
//			}
//		});
//
//		enableButtons();
//
//		JPanel row = new JPanel();
//
//		// Tang toc slider
//		panel.add(Box.createVerticalStrut(12));
//		row.add(new JLabel("Speed:"));
//		speed = new JSlider(0, 200, 75);	// min, max, current
//		speed.setPreferredSize(new Dimension(100,15));
//
//		updateTimer();
//		row.add(speed);
//
//		panel.add(row);
//		speed.addChangeListener( new ChangeListener() {
//			// khi slide thay doi, dong bo thoi gian voi gia tri cua no
//			public void stateChanged(ChangeEvent e) {
//				updateTimer();
//			}
//		});
//
//		testButton = new JCheckBox("Test sequence");
//		panel.add(testButton);
//
//
//		return(panel);
//	}
////
////	/*
////	Ham khoi tao chinh
////	*/
////	public static void main(String[] args)
////
////	{
////		JFrame frame = new JFrame("Stanford Tetris");
////		JComponent container = (JComponent)frame.getContentPane();
////		container.setLayout(new BorderLayout());
////
////
////		final int pixels = 16;
////		JTetris tetris;
////		//Tao JTetris, JBrainTetris tai day
////		if (false) {
////			tetris = new JTetris(WIDTH*pixels+2, (HEIGHT+TOP_SPACE)*pixels+2);
////		} else {
////			tetris = new JBrainTetris(WIDTH*pixels+2, (HEIGHT+TOP_SPACE)*pixels+2);
////		}
////
////
////		container.add(tetris, BorderLayout.CENTER);
////
////		Container panel = tetris.createControlPanel();
////
////		// Them nut Quit duoi man hinh
////		panel.add(Box.createVerticalStrut(12));
////		JButton quit = new JButton("Quit");
////		panel.add(quit);
////		quit.addActionListener( new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				System.exit(0);
////			}
////		});
////
////
////		container.add(panel, BorderLayout.EAST);
////		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		frame.pack();
////		frame.setVisible(true);
////
////	}
//	/**
//	 * Creates and returns a frame around the given JTetris.
//	 * The new frame is not visible.
//	 */
//	public static JFrame createFrame(JTetris tetris) {
//		JFrame frame = new JFrame("Stanford Tetris");
//		JComponent container = (JComponent)frame.getContentPane();
//		container.setLayout(new BorderLayout());
//
//		// Install the passed in JTetris in the center.
//		container.add(tetris, BorderLayout.CENTER);
//
//		// Create and install the panel of controls.
//		JComponent controls = tetris.createControlPanel();
//		container.add(controls, BorderLayout.EAST);
//
//		// Add the quit button last so it's at the bottom
//		controls.add(Box.createVerticalStrut(12));
//		JButton quit = new JButton("Quit");
//		controls.add(quit);
//		quit.addActionListener( new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
//
//		return frame;
//	}
//
//	/**
//	 Creates a frame with a JTetris.
//	 */
//	public static void main(String[] args) {
//		// Set GUI Look And Feel Boilerplate.
//		// Do this incantation at the start of main() to tell Swing
//		// to use the GUI LookAndFeel of the native platform. It's ok
//		// to ignore the exception.
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception ignored) { }
//
//		JTetris tetris = new JTetris(16);
//		JFrame frame = JTetris.createFrame(tetris);
//		frame.setVisible(true);
//	}
// size of the board in blocks
public static final int WIDTH = 10;
	public static final int HEIGHT = 20;

	// Extra blocks at the top for pieces to start.
	// If a piece is sticking up into this area
	// when it has landed -- game over!
	public static final int TOP_SPACE = 4;

	// When this is true, plays a fixed sequence of 100 pieces
	protected boolean testMode = false;
	public final int TEST_LIMIT = 100;

	// Is drawing optimized
	// (default false, so debugging is easier)
	protected boolean DRAW_OPTIMIZE = false;

	// Board data structures
	protected Board board;
	protected Piece[] pieces;


	// The current piece in play or null
	protected Piece currentPiece;
	protected int currentX;
	protected int currentY;
	protected boolean moved;	// did the player move the piece


	// The piece we're thinking about playing
	// -- set by computeNewPosition
	// (storing this in ivars is slightly questionable style)
	protected Piece newPiece;
	protected int newX;
	protected int newY;

	// State of the game
	protected boolean gameOn;	// true if we are playing
	protected int count;		 // how many pieces played so far
	protected long startTime;	// used to measure elapsed time
	protected Random random;	 // the random generator for new pieces


	// Controls
	protected JLabel countLabel;
	protected JLabel scoreLabel;
	protected int score;
	protected JLabel timeLabel;
	protected JButton startButton;
	protected JButton stopButton;
	protected javax.swing.Timer timer;
	protected JSlider speed;
	protected JCheckBox testButton;

	public final int DELAY = 400;	// milliseconds per tick

	/**
	 * Creates a new JTetris where each tetris square
	 * is drawn with the given number of pixels.
	 */
	JTetris(int pixels) {
		super();

		// Set component size to allow given pixels for each block plus
		// a 1 pixel border around the whole thing.
		setPreferredSize(new Dimension((WIDTH * pixels)+2,
				(HEIGHT+TOP_SPACE)*pixels+2));
		gameOn = false;

		pieces = Piece.getPieces();
		board = new Board(WIDTH, HEIGHT + TOP_SPACE);


		/*
		 Register key handlers that call
		 tick with the appropriate constant.
		 e.g. 'j' and '4'  call tick(LEFT)

		 I tried doing the arrow keys, but the JSliders
		 try to use those too, causing problems.
		*/

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


		// Create the Timer object and have it send
		// tick(DOWN) periodically
		timer = new javax.swing.Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick(DOWN);
			}
		});

		requestFocusInWindow();
	}



	/**
	 Sets the internal state and starts the timer
	 so the game is happening.
	 */
	public void startGame() {
		// cheap way to reset the board state
		board = new Board(WIDTH, HEIGHT + TOP_SPACE);

		// draw the new board state once
		repaint();

		count = 0;
		score = 0;
		updateCounters();
		gameOn = true;

		// Set mode based on checkbox at start of game
		testMode = testButton.isSelected();

		if (testMode) random = new Random(0);	// same seq every time
		else random = new Random(); // diff seq each game

		enableButtons();
		timeLabel.setText(" ");
		addNewPiece();
		timer.start();
		startTime = System.currentTimeMillis();
	}


	/**
	 Sets the enabling of the start/stop buttons
	 based on the gameOn state.
	 */
	private void enableButtons() {
		startButton.setEnabled(!gameOn);
		stopButton.setEnabled(gameOn);
	}

	/**
	 Stops the game.
	 */
	public void stopGame() {
		gameOn = false;
		enableButtons();
		timer.stop();

		long delta = (System.currentTimeMillis() - startTime)/10;
		timeLabel.setText(Double.toString(delta/100.0) + " seconds");

	}


	/**
	 Given a piece, tries to install that piece
	 into the board and set it to be the current piece.
	 Does the necessary repaints.
	 If the placement is not possible, then the placement
	 is undone, and the board is not changed. The board
	 should be in the committed state when this is called.
	 Returns the same error code as Board.place().
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
		}
		else {
			board.undo();
		}

		return(result);
	}


	/**
	 Selects the next piece to use using the random generator
	 set in startGame().
	 */
	public Piece pickNextPiece() {
		int pieceNum;

		pieceNum = (int) (pieces.length * random.nextDouble());

		Piece piece	 = pieces[pieceNum];

		return(piece);
	}


	/**
	 Tries to add a new random piece at the top of the board.
	 Ends the game if it's not possible.
	 */
	public void addNewPiece() {
		count++;
		score++;

		if (testMode && count == TEST_LIMIT+1) {
			stopGame();
			return;
		}

		// commit things the way they are
		board.commit();
		currentPiece = null;

		Piece piece = pickNextPiece();

		// Center it up at the top
		int px = (board.getWidth() - piece.getWidth())/2;
		int py = board.getHeight() - piece.getHeight();

		// add the new piece to be in play
		int result = setCurrent(piece, px, py);

		// This probably never happens, since
		// the blocks at the top allow space
		// for new pieces to at least be added.
		if (result>Board.PLACE_ROW_FILLED) {
			stopGame();
		}

		updateCounters();
	}

	/**
	 Updates the count/score labels with the latest values.
	 */
	private void updateCounters() {
		countLabel.setText("Pieces " + count);
		scoreLabel.setText("Score " + score);
	}


	/**
	 Figures a new position for the current piece
	 based on the given verb (LEFT, RIGHT, ...).
	 The board should be in the committed state --
	 i.e. the piece should not be in the board at the moment.
	 This is necessary so dropHeight() may be called without
	 the piece "hitting itself" on the way down.

	 Sets the ivars newX, newY, and newPiece to hold
	 what it thinks the new piece position should be.
	 (Storing an intermediate result like that in
	 ivars is a little tacky.)
	 */
	public void computeNewPosition(int verb) {
		// As a starting point, the new position is the same as the old
		newPiece = currentPiece;
		newX = currentX;
		newY = currentY;

		// Make changes based on the verb
		switch (verb) {
			case LEFT: newX--; break;

			case RIGHT: newX++; break;

			case ROTATE:
				newPiece = newPiece.fastRotation();

				// tricky: make the piece appear to rotate about its center
				// can't just leave it at the same lower-left origin as the
				// previous piece.
				newX = newX + (currentPiece.getWidth() - newPiece.getWidth())/2;
				newY = newY + (currentPiece.getHeight() - newPiece.getHeight())/2;
				break;

			case DOWN: newY--; break;

			case DROP:
				newY = board.dropHeight(newPiece, newX);

				// trick: avoid the case where the drop would cause
				// the piece to appear to move up
				if (newY > currentY) {
					newY = currentY;
				}
				break;

			default:
				throw new RuntimeException("Bad verb");
		}

	}




	public static final int ROTATE = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DROP = 3;
	public static final int DOWN = 4;
	/**
	 Called to change the position of the current piece.
	 Each key press calls this once with the verbs
	 LEFT RIGHT ROTATE DROP for the user moves,
	 and the timer calls it with the verb DOWN to move
	 the piece down one square.

	 Before this is called, the piece is at some location in the board.
	 This advances the piece to be at its next location.

	 Overriden by the brain when it plays.
	 */
	public void tick(int verb) {
		if (!gameOn) return;

		if (currentPiece != null) {
			board.undo();	// remove the piece from its old position
		}

		// Sets the newXXX ivars
		computeNewPosition(verb);

		// try out the new position (rolls back if it doesn't work)
		int result = setCurrent(newPiece, newX, newY);

		// if row clearing is going to happen, draw the
		// whole board so the green row shows up
		if (result ==  Board.PLACE_ROW_FILLED) {
			repaint();
		}


		boolean failed = (result >= Board.PLACE_OUT_BOUNDS);

		// if it didn't work, put it back the way it was
		if (failed) {
			if (currentPiece != null) board.place(currentPiece, currentX, currentY);
			repaintPiece(currentPiece, currentX, currentY);
		}

		/*
		 How to detect when a piece has landed:
		 if this move hits something on its DOWN verb,
		 and the previous verb was also DOWN (i.e. the player was not
		 still moving it),	then the previous position must be the correct
		 "landed" position, so we're done with the falling of this piece.
		*/
		if (failed && verb==DOWN && !moved) {	// it's landed

			int cleared = board.clearRows();
			if (cleared > 0) {
				// score goes up by 5, 10, 20, 40 for row clearing
				// clearing 4 gets you a beep!
				switch (cleared) {
					case 1: score += 5;	 break;
					case 2: score += 10;  break;
					case 3: score += 20;  break;
					case 4: score += 40; Toolkit.getDefaultToolkit().beep(); break;
					default: score += 50;  // could happen with non-standard pieces
				}
				updateCounters();
				repaint();	// repaint to show the result of the row clearing
			}


			// if the board is too tall, we've lost
			if (board.getMaxHeight() > board.getHeight() - TOP_SPACE) {
				stopGame();
			}
			// Otherwise add a new piece and keep playing
			else {
				addNewPiece();
			}
		}

		// Note if the player made a successful non-DOWN move --
		// used to detect if the piece has landed on the next tick()
		moved = (!failed && verb!=DOWN);
	}



	/**
	 Given a piece and a position for the piece, generates
	 a repaint for the rectangle that just encloses the piece.
	 */
	public void repaintPiece(Piece piece, int x, int y) {
		if (DRAW_OPTIMIZE) {
			int px = xPixel(x);
			int py = yPixel(y + piece.getHeight() - 1);
			int pwidth = xPixel(x+piece.getWidth()) - px;
			int pheight = yPixel(y-1) - py;

			repaint(px, py, pwidth, pheight);
		}
		else {
			// Not-optimized -- rather than repaint
			// just the piece rect, repaint the whole board.
			repaint();
		}
	}


	/*
	 Pixel helpers.
	 These centralize the translation of (x,y) coords
	 that refer to blocks in the board to (x,y) coords that
	 count pixels. Centralizing these computations here
	 is the only prayer that repaintPiece() and paintComponent()
	 will be consistent.

	 The +1's and -2's are to account for the 1 pixel
	 rect around the perimeter.
	*/


	// width in pixels of a block
	// this is a const pixel set by the constructor, 16 here.
	private final float dX() {
		return( ((float)(getWidth()-2)) / board.getWidth() );
	}

	// height in pixels of a block
	// this is a const pixel set by the constructor, 16 here.
	private final float dY() {
		return( ((float)(getHeight()-2)) / board.getHeight() );
	}

	// the x pixel coord of the left side of a block
	private final int xPixel(int x) {
		return(Math.round(1 + (x * dX())));
	}

	// the y pixel coord of the top of a block
	private final int yPixel(int y) {
		return(Math.round(getHeight() -1 - (y+1)*dY()));
	}


	/**
	 Draws the current board with a 1 pixel border
	 around the whole thing. Uses the pixel helpers
	 above to map board coords to pixel coords.
	 Draws rows that are filled all the way across in green.
	 */
	public void paintComponent(Graphics g) {

		// Draw a rect around the whole thing
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);

		// Draw the line separating the top
		int spacerY = yPixel(board.getHeight() - TOP_SPACE - 1);
		g.drawLine(0, spacerY, getWidth()-1, spacerY);


		// check if we are drawing with clipping
		//Shape shape = g.getClip();
		Rectangle clip = null;
		if (DRAW_OPTIMIZE) {
			clip = g.getClipBounds();
		}


		// Factor a few things out to help the optimizer
		final int dx = Math.round(dX()-2);
		final int dy = Math.round(dY()-2);
		final int bWidth = board.getWidth();

		int x, y;
		// Loop through and draw all the blocks
		// left-right, bottom-top
		for (x=0; x<bWidth; x++) {
			int left = xPixel(x);	// the left pixel

			// right pixel (useful for clip optimization)
			int right = xPixel(x+1) -1;

			// skip this x if it is outside the clip rect
			if (DRAW_OPTIMIZE && clip!=null) {
				if ((right<clip.x) || (left>=(clip.x+clip.width))) continue;
			}

			// draw from 0 up to the col height
			final int yHeight = board.getColumnHeight(x);
			for (y=0; y<yHeight; y++) {
				if (board.getGrid(x, y)) {
					boolean filled = (board.getRowWidth(y)==bWidth);
					if (filled) g.setColor(Color.green);

					g.fillRect(left+1, yPixel(y)+1, dx, dy);	// +1 to leave a white border

					if (filled) g.setColor(Color.black);
				}
			}
		}
	}


	/**
	 Updates the timer to reflect the current setting of the
	 speed slider.
	 */
	public void updateTimer() {
		double value = ((double)speed.getValue())/speed.getMaximum();
		timer.setDelay((int)(DELAY - value*DELAY));
	}


	/**
	 Creates the panel of UI controls -- controls wired
	 up to call methods on the JTetris. This code is very repetitive.
	 */
	public JComponent createControlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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
		startButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});

		// STOP button
		stopButton = new JButton("Stop");
		panel.add(stopButton);
		stopButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopGame();
			}
		});

		enableButtons();

		JPanel row = new JPanel();

		// SPEED slider
		panel.add(Box.createVerticalStrut(12));
		row.add(new JLabel("Speed:"));
		speed = new JSlider(0, 200, 75);	// min, max, current
		speed.setPreferredSize(new Dimension(100, 15));

		updateTimer();
		row.add(speed);

		panel.add(row);
		speed.addChangeListener( new ChangeListener() {
			// when the slider changes, sync the timer to its value
			public void stateChanged(ChangeEvent e) {
				updateTimer();
			}
		});

		testButton = new JCheckBox("Test sequence");
		panel.add(testButton);


		return panel;
	}

	/**
	 * Creates and returns a frame around the given JTetris.
	 * The new frame is not visible.
	 */
	public static JFrame createFrame(JTetris tetris) {
		JFrame frame = new JFrame("Stanford Tetris");
		JComponent container = (JComponent)frame.getContentPane();
		container.setLayout(new BorderLayout());

		// Install the passed in JTetris in the center.
		container.add(tetris, BorderLayout.CENTER);

		// Create and install the panel of controls.
		JComponent controls = tetris.createControlPanel();
		container.add(controls, BorderLayout.EAST);

		// Add the quit button last so it's at the bottom
		controls.add(Box.createVerticalStrut(12));
		JButton quit = new JButton("Quit");
		controls.add(quit);
		quit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		return frame;
	}

	/**
	 Creates a frame with a JTetris.
	 */
	public static void main(String[] args) {
		// Set GUI Look And Feel Boilerplate.
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }

		JTetris tetris = new JTetris(16);
		JFrame frame = JTetris.createFrame(tetris);
		frame.setVisible(true);
	}

}

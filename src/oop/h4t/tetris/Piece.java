package oop.h4t.tetris;// Piece.java

import java.awt.Color;
import java.util.*;
/**
 * Group 15: H4T
 * On 30/11/15.
 */

/**
 * Tao hinh
 * <p>
 * Piece pyra = test Piece(PYRAMID_STR);		// Tao hinh moi
 * int width = pyra.getWidth();			// Lay do rong
 * Piece pyra2 = pyramid.computeNextRotation(); // Quay
 * <p>
 * Piece[] pieces = Piece.getPieces();	// Chuoi hinh goc
 * Piece stick = pieces[STICK];
 * int width = stick.getWidth();		// Tao do rong
 * Piece stick2 = stick.fastRotation();
 */
public class Piece {
    private TPoint[] body;
    private int[] skirt;
    private int width;
    private int height;
    private int color;
    private Piece next;

    static private Piece[] pieces;

    /**
     * Xac dinh hinh moi cho boi TPoint[]
     */
    public Piece(TPoint[] points) {
        // Viet code tai day
        body = Arrays.copyOf(points, points.length);
        int[] temp = new int[100];
        Arrays.fill(temp, Integer.MAX_VALUE);
        for (TPoint p : body) {
            if (p.x > width) {
                width = p.x;
            }
            if (p.y > height)
                height = p.y;
            if (p.y < temp[p.x])
                temp[p.x] = p.y;
        }
        width += 1;
        height += 1;
        skirt = Arrays.copyOfRange(temp, 0, width);
    }

    public Piece(String points) {
        this(parsePoints(points));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TPoint[] getBody() {
        return body;
    }

    /**
     * Tra con tro ve quanh cac hinh.
     */
    public int[] getSkirt() {
        return skirt;
    }

    /**
     * Tra ve hinh moi duoc quay 90 do so voi chieu dong ho
     */
    public Piece computeNextRotation() {
        List<TPoint> points = new ArrayList<>();
        int minX = Integer.MAX_VALUE;
        for (TPoint point : body) {
            points.add(new TPoint(-point.y, point.x));
            if (minX > -point.y) {
                minX = -point.y;
            }
        }
        for (TPoint p : points) {
            p.x -= minX;
        }
        TPoint[] array = points.toArray(new TPoint[0]);

        return new Piece(array);
    }

    public Piece fastRotation() {
        return next;
    }

    /**
     * Tra ve 2 hinh co cung diem.
     */
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof Piece)) return false;
        Piece other = (Piece) obj;

        // Viet code tai day
        TPoint[] otherBody = other.getBody();
        TPoint[] otherBodyCopy = Arrays.copyOf(otherBody, otherBody.length);
        Arrays.sort(otherBodyCopy);
        TPoint[] thisBodyCopy = Arrays.copyOf(body, body.length);
        Arrays.sort(thisBodyCopy);
        if (Arrays.deepEquals(thisBodyCopy, otherBodyCopy)) {
            return true;
        }
        return false;
    }

    // Tao 7 hinh trong cho truoc
    public static final String STICK_STR = "0 0	0 1	 0 2  0 3";
    public static final String L1_STR = "0 0	0 1	 0 2  1 0";
    public static final String L2_STR = "0 0	1 0 1 1	 1 2";
    public static final String S1_STR = "0 0	1 0	 1 1  2 1";
    public static final String S2_STR = "0 1	1 1	 1 0  2 0";
    public static final String SQUARE_STR = "0 0	0 1	 1 0  1 1";
    public static final String PYRAMID_STR = "0 0  1 0  1 1	2 0";

    // Gan cac gia tri
    public static final int STICK = 0;
    public static final int L1 = 1;
    public static final int L2 = 2;
    public static final int S1 = 3;
    public static final int S2 = 4;
    public static final int SQUARE = 5;
    public static final int PYRAMID = 6;


    public static Piece[] getPieces() {
        // lazy evaluation -- create static array if needed
        if (Piece.pieces == null) {

            Piece.pieces = new Piece[]{
                    makeFastRotations(new Piece(STICK_STR)),
                    makeFastRotations(new Piece(L1_STR)),
                    makeFastRotations(new Piece(L2_STR)),
                    makeFastRotations(new Piece(S1_STR)),
                    makeFastRotations(new Piece(S2_STR)),
                    makeFastRotations(new Piece(SQUARE_STR)),
                    makeFastRotations(new Piece(PYRAMID_STR)),
            };
        }


        return Piece.pieces;
    }

    public int getColor() {
        if (this.equals(new Piece(STICK_STR))) {
            return 0;
        }
        if (this.equals(new Piece(L1_STR))) {
            return 1;
        }
        if (this.equals(new Piece(L2_STR))) {
            return 2;
        }
        if (this.equals(new Piece(S1_STR))) {
            return 3;
        }
        if (this.equals(new Piece(S2_STR))) {
            return 4;
        }
        if (this.equals(new Piece(SQUARE_STR))) {
            return 5;
        }
        if (this.equals(new Piece(PYRAMID_STR))) {
            return 6;
        }
        return 0;
    }

    public static Color change(int a) {
        switch (a) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.MAGENTA;
        }
        return Color.BLACK;
    }

    //Quay hinh
    private static Piece makeFastRotations(Piece root) {
        // Viet code tai day
        root.initRotationSequence(root);
        return root;
    }

    private void initRotationSequence(Piece head) {
        Piece currentPiece = this;
        Piece nextPiece;
        while (true) {
            nextPiece = currentPiece.computeNextRotation();
            if (nextPiece.equals(head)) {
                currentPiece.next = head;
                break;
            } else {
                currentPiece.next = nextPiece;
            }
            currentPiece = nextPiece;
        }
    }

    private static TPoint[] parsePoints(String string) {
        List points = new ArrayList();
        StringTokenizer tok = new StringTokenizer(string);
        try {
            while (tok.hasMoreTokens()) {
                int x = Integer.parseInt(tok.nextToken());
                int y = Integer.parseInt(tok.nextToken());
                points.add(new TPoint(x, y));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Could not parse x,y string:" + string);
        }

        // Tao mang lua chon
        TPoint[] array = (TPoint[]) points.toArray(new TPoint[0]);
        return array;
    }
}

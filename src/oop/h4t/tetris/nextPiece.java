
package oop.h4t.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Group 15: H4T
 * On 30/11/15.
 */

public class NextPiece extends JPanel {
    int high_score;
    private Piece next;
    private JTetris tetris;
    private static final int tile_size = 20;
    private static final int sizePiece = 80;
    private static final int x = 40;
    private static final int y = 20;
    private static final int center_x = x + (sizePiece) / 2;
    private static final int center_y = y + (sizePiece) / 2;
    private static final int hgap = 2;
    private static final int vgap = 2;


    public NextPiece(JTetris tetris) {
        this.high_score = tetris.getHigh_score();
        next = tetris.getNextPiece();
        this.tetris = tetris;
        this.setPreferredSize(new Dimension(50, 150));

    }

    public void drawTile(Color c, int x, int y, int size, Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, size, size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(x, y, sizePiece, sizePiece);
        g.drawString("High Score: " + tetris.getHigh_score(), 50, 160);

        next = tetris.getNextPiece();
        if (next != null) {
            int length = next.getBody().length;
            TPoint[] x = next.getBody();
            int xPiece = (next.getWidth() * tile_size + hgap) / 2;
            int yPiece = (next.getHeight() * tile_size + hgap) / 2;
            for (int i = 0; i < length; i++) {
                drawTile(Piece.change(next.getColor()), center_x - xPiece + x[i].x * tile_size, center_y - yPiece + x[i].y * tile_size, tile_size - 2, g);
            }
        }
    }

}

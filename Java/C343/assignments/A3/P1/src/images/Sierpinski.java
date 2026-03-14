package images;

import java.awt.*;

public class Sierpinski extends AbstractFractal {
    public void draw(Graphics g) {
        drawSierpinski(g, 5, 100, 100, WIDTH / 2);
    }

    public void drawSierpinski(Graphics g, int count, int x, int y, int side) {
        if (count == 0) return;

        int step = side / 3;
        int[] xCoords = { x, x + step, x + 2 * step } ;
        int[] yCoords = { y, y + step, y + 2 * step } ;

        g.fillRect(xCoords[1], yCoords[1], step, step);
        for (int i = 0; i < xCoords.length; i++) {
            for (int j = 0; j < yCoords.length; j++) {
                if (i == 1 && j == 1) continue;
                drawSierpinski(g, count - 1, xCoords[i], yCoords[j], step);
            }
        }

    }

    public static void main(String[] args) {
        new Sierpinski();
    }
}

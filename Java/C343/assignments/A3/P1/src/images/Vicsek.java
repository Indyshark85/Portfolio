package images;

import java.awt.*;

public class Vicsek extends AbstractFractal {
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 729, 729);
        drawVicsek(g, 5, 0, 0, 729);
    }

    public void drawVicsek(Graphics g, int count, int x, int y, int side) {
        if (count == 0) return;

        int step = side / 3;
        int[] xCoords = { x, x + step, x + 2 * step } ;
        int[] yCoords = { y, y + step, y + 2 * step } ;

        g.setColor(Color.WHITE);

        for (int i = 0; i < xCoords.length; i++) {
            for (int j = 0; j < yCoords.length; j++) {
                if (i != 1 && j != 1) g.fillRect(xCoords[i], yCoords[j], step, step);
                else drawVicsek(g, count - 1, xCoords[i], yCoords[j], step);
            }
        }
    }

    public static void main(String[] args) {
        new Vicsek();
    }
}

package images;

import java.awt.*;

public class Squares extends AbstractFractal {
    public void draw(Graphics g) {
        drawSquares(g, 10, 100, 100, WIDTH / 2);
    }

    public void drawSquares(Graphics g, int count, int x, int y, int size) {
        if (count == 0) return;

        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        g.setColor(new Color(red, green, blue));

        g.fillRect(x, y, size, size);

        int nextX = x + size / 8;
        int nextY = y + size / 8;
        int nextSize = 3 * size / 4;
        drawSquares(g, count - 1, nextX, nextY, nextSize);
    }

    public static void main(String[] args) {
        new Squares();
    }

}

package images;

import java.awt.*;

public class Mondrian extends AbstractFractal {
    final double FRAC_COLORED = 2.0 / 3;

    public void draw(Graphics g) {
        drawMondrian(g, 3, 100, 100, 500, 500);
    }

    public void drawMondrian(Graphics g, int count, int x, int y, int width, int height) {
        if (count == 0) return;

        if (Math.random() < FRAC_COLORED) {
            float red = (float) Math.random();
            float green = (float) Math.random();
            float blue = (float) Math.random();
            g.setColor(new Color(red, green, blue));
        } else g.setColor(Color.WHITE);

        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        int xSplit = (int) (Math.random() * width);
        int ySplit = (int) (Math.random() * height);

        drawMondrian(g, count - 1, x, y, xSplit , ySplit );
        drawMondrian(g, count - 1, x + xSplit, y, width - xSplit, ySplit);
        drawMondrian(g, count - 1, x, y + ySplit, xSplit, height - ySplit);
        drawMondrian(g, count - 1, x + xSplit, y + ySplit, width - xSplit, height - ySplit);
    }

    public static void main(String[] args) {
        new Mondrian();
    }
}

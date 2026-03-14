package images;

import javax.swing.*;

public class TurtleLoops {
    private final Turtle t;

    public TurtleLoops(Turtle t) {
        this.t = t;
        JFrame frame = new JFrame("Iterative Shapes");
        frame.add(t);
        frame.setSize(t.WIDTH+50, t.HEIGHT+50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void drawHouse (double len, double wid) {
        t.move(len);
        t.turnRight(150); t.move(2 * wid);
        t.turnLeft(150); t.move(len);
        t.turnLeft(90); t.move(wid);
        t.turnRight(120); t.move(wid);
        t.turnRight(120); t.move(wid);
        t.turnRight(60); t.move(2 * wid);
        t.turnLeft(120); t.move(wid);
    }

    public  void drawSquare (double len) {
        for (int i = 0; i < 4; i++) {
            t.move(len);
            t.turnRight(90);
        }
    }

    public void drawRosette (int n, double len) {
        for (int i = 0; i < n; i++) {
            drawSquare(len);
            t.turnRight(360.0 / n);
        }
    }

    public void drawSpiral (int n, double len, double inc, double angle) {
        double currentLen = len;
        for (int i = 0; i < n; i++) {
            t.move(currentLen);
            t.turnLeft(angle);
            currentLen += inc;
        }
    }

    public void drawQuarterCircle (double len) {
        for (int i = 0; i < 90; i++) {
            t.move(len);
            t.turnLeft(1);
        }
    }

    public void drawPetal (double len) {
        drawQuarterCircle(len);
        t.turnLeft(90);
        drawQuarterCircle(len);
        t.turnLeft(90);
    }

    public void drawFlower (int n, double len) {
        for (int i = 0; i < n; i++) {
            drawPetal(len);
            t.turnLeft(360.0 / n);
        }
    }

    public static void main (String[] args) {
        Turtle t = new Turtle();
        TurtleLoops is = new TurtleLoops(t);

        t.setPos(50.0, t.HEIGHT-200.0);
        is.drawHouse(100.0, 100.0 / Math.sqrt(3));

        t.setPos(200.0, t.HEIGHT-200.0);
        is.drawSquare(100.0);

        t.setPos(600.0, t.HEIGHT-200.0);
        is.drawRosette(10, 100.0);

        t.setPos(1000.0, t.HEIGHT-200.0);
        is.drawRosette(30, 100.0);

        t.setPos(200.0, 250.0);
        is.drawSpiral(50, 5.0, 5.0, 90.0);

        t.setPos(500.0, 250.0);
        is.drawSpiral(50, 9.0, 4.0, 125.0);

        t.setPos(800.0, 250.0);
        is.drawFlower(10, 1.0);

        t.setPos(1000.0, 250.0);
        is.drawFlower(50, 1.0);
    }

}

package images;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Turtle extends Canvas {
    final int WIDTH = 1200;
    final int HEIGHT = 800;

    private double x, y, angle;
    private final ArrayList<Line2D> lines;

    // (x=0,y=0) is the top-left corner of the canvas
    // x increases to the right, y increases downwards
    // Angles: 0 degrees is east, 90 degrees is north, 180 degrees is west, 270 degrees is south
    // Start position bottom left corner facing east
    public Turtle() {
        setSize(WIDTH, HEIGHT);
        this.x = 0.0;
        this.y = HEIGHT;
        this.angle = 0.0;
        this.lines = new ArrayList<>();
    }

    public void setPos (double x, double y) {
        this.x = x;
        this.y = y;
        faceNorth();
    }

    public void faceNorth () { this.angle = 90.0; }

    public void turnLeft (double degrees) {
        this.angle += degrees;
    }

    public void turnRight (double degrees) {
        this.angle -= degrees;
    }

    public void move (double dist) {
        double newX = this.x + dist * Math.cos(Math.toRadians(this.angle));
        double newY = this.y - dist * Math.sin(Math.toRadians(this.angle));
        Line2D line = new Line2D.Double(this.x, this.y, newX, newY);
        this.x = newX;
        this.y = newY;
        this.lines.add(line);
    }

    public void paint (Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        for (Line2D line : this.lines) {
            int startX = (int) line.getX1();
            int startY = (int) line.getY1();
            int endX = (int) line.getX2();
            int endY = (int) line.getY2();
            double length = line.getP1().distance(line.getP2());
            ((Graphics2D)g).setStroke(new BasicStroke((float) length / 75));
            g.drawLine(startX, startY, endX, endY);
        }
    }

    public String toString() {
        return "Turtle at (" + this.x + ", " + this.y + ") facing " + this.angle + " degrees";
    }

}

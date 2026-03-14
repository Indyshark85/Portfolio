package images;

import javax.swing.*;

public class TurtleTrees {
    private final Turtle t;

    public TurtleTrees(Turtle t) {
        this.t = t;
        JFrame frame = new JFrame("Recursive Shapes");
        frame.add(t);
        frame.setSize(t.WIDTH + 50, t.HEIGHT + 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void drawTree(int count, double len, double leftAngle, double leftScale,
                         double rightAngle, double rightScale, double angleWidth, double scaleWidth) {
        if (count == 0) return;

        double randLeftAngle = leftAngle + (Math.random() - 0.5) * angleWidth;
        double randRightAngle = rightAngle + (Math.random() - 0.5) * angleWidth;
        double randLeftScale = leftScale + (Math.random() - 0.5) * scaleWidth;
        double randRightScale = rightScale + (Math.random() - 0.5) * scaleWidth;

        t.move(len);
          t.turnLeft(randLeftAngle);
            drawTree(count-1, len * randLeftScale, leftAngle, leftScale,
                    rightAngle, rightScale, angleWidth, scaleWidth);
          t.turnRight(randLeftAngle);
          t.turnRight(randRightAngle);
            drawTree(count-1, len * randRightScale, leftAngle, leftScale,
                    rightAngle, rightScale, angleWidth, scaleWidth);
          t.turnLeft(randRightAngle);
        t.move(-len);
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        TurtleTrees rs = new TurtleTrees(t);

        t.setPos(250.0, t.HEIGHT * 2.0 / 3.0);
        rs.drawTree(15, 70.0, 20.0, 0.8,
                20.0, 0.8, 0.0, 0.0);

        t.setPos(700.0, t.HEIGHT * 2.0 / 3.0);
        rs.drawTree(13, 100.0, 30.0, 0.7,
                15.0, 0.7, 10.0, 0.1);

        t.setPos(1000.0, t.HEIGHT * 2.0 / 3.0);
        rs.drawTree(13, 60.0, 33.0, 0.77,
                33.0, 0.77, 66.0, 0.05);


    }
}

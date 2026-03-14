package images;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractFractal extends Canvas {
    final int WIDTH = 800;
    final int HEIGHT = 800;

    public AbstractFractal() {
        setSize(WIDTH, HEIGHT);
        JFrame frame = new JFrame("Fractals");
        frame.setSize(WIDTH , HEIGHT);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) { draw(g); }

    public abstract void draw(Graphics g);
}

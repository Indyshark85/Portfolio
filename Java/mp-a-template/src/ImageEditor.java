import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

class ImageEditor extends JPanel {

    private final Stack<BufferedImage> UNDO_STACK;
    private final Stack<BufferedImage> REDO_STACK;
    private final JPanel IMAGE_PANEL;
    private final JMenuBar MENU_BAR;
    private final JScrollPane SCROLL_PANE;
    private final ShortcutKeyMap SHORTCUT_KEY_MAP;
    private final ZoomMouseEventListener ZOOM_LISTENER;
    private int zoomImageIndex;

    ImageEditor() {
        this.UNDO_STACK = new Stack<>();
        this.REDO_STACK = new Stack<>();
        this.SHORTCUT_KEY_MAP = new ShortcutKeyMap(this);
        this.IMAGE_PANEL = new ImagePanel(this);
        this.SCROLL_PANE = new JScrollPane(this.IMAGE_PANEL, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.MENU_BAR = new MenuBar(this);
        this.ZOOM_LISTENER = new ZoomMouseEventListener(this, this.IMAGE_PANEL);
        this.zoomImageIndex = 0;
        this.setLayout(new BorderLayout());
        this.add(this.MENU_BAR, BorderLayout.NORTH);
        this.add(this.SCROLL_PANE, BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.IMAGE_PANEL.repaint();
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.IMAGE_PANEL != null) {
            this.IMAGE_PANEL.revalidate();
        }
    }

    /**
     * reads the file data into a new BufferedImage object instance, and returns that image.
     *
     * @param in is the file name in ppm format to be read
     */
    void readPpmImage(String in) {
        try {

            Scanner scanner = new Scanner(new File(in));

            while (scanner.hasNext("#")) {
                scanner.nextLine();
            }
            // Read the width, height into the "width" and "height" variables;
            String format = scanner.next();
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int MaxValue = scanner.nextInt();

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y=0; y<height; y++){
                for (int x=0; x<width; x++){
                    int r = scanner.nextInt();
                    int g = scanner.nextInt();
                    int b = scanner.nextInt();
                    Color currentColor = new Color(r,g,b);
                    img.setRGB(x,y,currentColor.getRGB());
                }
            }


            // Do not modify the lines below.
            this.UNDO_STACK.clear();
            this.REDO_STACK.clear();
            this.zoomImageIndex = 0;
            this.addImage(img);
            scanner.close();
        } catch (RuntimeException | FileNotFoundException e) { // <- this will need to be a different exception!
            throw new RuntimeException(e);
        }
    }

    /**
     * write a file using the input "out".
     *
     * @param out is the file path/name.
     */
    void writePpmImage(String out) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(out))) {
            BufferedImage img = this.getImage();

            int height = img.getHeight();
            int width = img.getWidth();

            writer.println("P3");
            writer.println(width + " " + height);
            writer.println("255");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color rgb = new Color(img.getRGB(x,y));

                    int r = rgb.getRed();
                    int g = rgb.getGreen();
                    int b = rgb.getBlue();

                    writer.println(r + " " + g + " " + b);
                }
            }

            writer.close();

        } catch (RuntimeException | IOException e) { // <- this will need to be a different exception!
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new image to the editor and the undo stack. It is assumed that the image
     * being passed is not zoomed. If so, use the other addImage method.
     *
     * @param img image to add.
     */
    void addImage(BufferedImage img) {
        this.UNDO_STACK.push(img);
        this.REDO_STACK.clear();
        this.revalidate();
        this.repaint();
        this.zoomImageIndex++;
    }

    /**
     * Adds a new zoomed image to the editor. Because we only want to apply transformations
     * to non-zoomed images, we need to keep track of where the last non-zoomed image is in
     * the undo stack.
     *
     * @param img    image to add.
     * @param zoomed flag indicating whether the image is zoomed. This is always true.
     */
    void addImage(BufferedImage img, boolean zoomed) {
        this.UNDO_STACK.push(img);
        this.REDO_STACK.clear();
        this.revalidate();
        this.repaint();
        if (!zoomed) {
            this.zoomImageIndex++;
        }
    }

    /**
     * Removes the current image from the editor and the undo stack.
     * The undone image is pushed to the redo stack. If there are no images
     * to undo, this method does nothing.
     */
    void undoImage() {
        if (!this.UNDO_STACK.isEmpty()) {
            this.REDO_STACK.push(this.UNDO_STACK.pop());
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Redoes the last undone image. The redone image is pushed to the undo stack.
     * If there are no images to redo, this method does nothing.
     */
    void redoImage() {
        if (!this.REDO_STACK.isEmpty()) {
            this.UNDO_STACK.push(this.REDO_STACK.pop());
            this.revalidate();
            this.repaint();
        }
    }

    Stack<BufferedImage> getUndoStack() {
        return this.UNDO_STACK;
    }

    Stack<BufferedImage> getRedoStack() {
        return this.REDO_STACK;
    }

    BufferedImage getImage() {
        return this.UNDO_STACK.isEmpty() ? null : this.UNDO_STACK.peek();
    }

    BufferedImage getOriginalImage() {
        if (this.zoomImageIndex < 1 || this.zoomImageIndex >= this.UNDO_STACK.size()) {
            return null;
        } else {
            return this.UNDO_STACK.elementAt(this.zoomImageIndex - 1);
        }
    }

    MenuBar getMenuBar() {
        return (MenuBar) MENU_BAR;
    }

    JScrollPane getScrollPane() {
        return this.SCROLL_PANE;
    }

    ZoomMouseEventListener getZoomListener() {
        return this.ZOOM_LISTENER;
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;

class ImageOperations {

    /**
     * returns the image with the red channel pixels removed.
     *
     * @param img is the image to remove the red pixels from.
     * @return a new image.
     */
    static BufferedImage zeroRed(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage newImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color rgb = new Color(img.getRGB(x,y));

                int r = 0;
                int g = rgb.getGreen();
                int b = rgb.getBlue();

                Color newRGB = new Color(r,g,b);

                newImg.setRGB(x, y, newRGB.getRGB());

            }
        }

        return newImg;
    }

    /**
     * turns the image to gray by using a formula for what each pixel is supposed to look like.
     *
     * @param img the image to be edited.
     * @return a gray scale version the image provided.
     */
    static BufferedImage grayscale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage newImg = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color rgb = new Color(img.getRGB(x,y));

                int r = rgb.getRed();
                int g = rgb.getGreen();
                int b = rgb.getBlue();

                int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);

                Color grayColor = new Color(gray, gray, gray);

                newImg.setRGB(x,y, grayColor.getRGB());
            }
        }




        //BufferedImage newImg = null;
        return newImg;
    }

    /**
     * Invert the pixels of an image, by subtracting the pixel values by the max amount, IE. 255 255 255 inverse = 0 0 0.
     *
     * @param img Is the image to invert.
     * @return a new image.
     */
    static BufferedImage invert(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage newImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color rgb = new Color(img.getRGB(x, y));

                int r = 255 - rgb.getRed();
                int g = 255 - rgb.getGreen();
                int b = 255 - rgb.getBlue();

                Color newRGB = new Color(r,g,b);

                newImg.setRGB(x, y, newRGB.getRGB());
            }
        }

        return newImg;
    }

    /**
     * mirrors the image, IE pixels on right side go to left side, and pixels on left side go to right side.
     *
     * @param img image to be mirrored.
     * @param dir the direction in which to mirror.
     * @return the mirrored image.
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage newImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        if (dir == MirrorMenuItem.MirrorDirection.VERTICAL) {
            for (int y = 0; y < height; y++ ) {
                int x = 0;
                while (x < width / 2) {
                    Color rgb = new Color(img.getRGB(x, y));
                    newImg.setRGB(x,y,rgb.getRGB());
                    x++;
                }
                int x2 = width / 2;
                x = width / 2;
                while (x < width) {
                    Color rgb = new Color(img.getRGB(x2, y));
                    newImg.setRGB(x,y,rgb.getRGB());
                    x++;
                    x2--;
                }
            }
        } else {
            for (int x = 0; x < height; x++ ) {
                int y = 0;
                while (y < width / 2) {
                    Color rgb = new Color(img.getRGB(x, y));
                    newImg.setRGB(x,y,rgb.getRGB());
                    y++;
                }
                int y2 = width / 2;
                y = width / 2;
                while (y < width) {
                    Color rgb = new Color(img.getRGB(x, y2));
                    newImg.setRGB(x,y,rgb.getRGB());
                    y++;
                    y2--;
                }
            }
        }
        return newImg;
    }

    /**
     * rotates the image based on the direction specified in the dir variable..
     *
     * @param img the image to be rotated.
     * @param dir the direction to be turned.
     * @return a rotated image.
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage newImg = new BufferedImage(height, width, img.getType());

        if (dir == RotateMenuItem.RotateDirection.CLOCKWISE) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x,y);
                    newImg.setRGB(height - 1 - y, x, rgb);
                }
            }
        } else {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x, y);
                    newImg.setRGB(y, width - 1 - x, rgb);
                }
            }
        }
        return newImg;
    }

    /**
     * Repeats the image in either direction that is declared n times.
     *
     * @param img the image you gave to us like this is obvious user.
     * @param n   n times (like new york times).
     * @param dir direction that it repeats.
     * @return the image .
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        BufferedImage newImg = null;
        int width =img.getWidth();
        int height =img.getHeight();
        // newImg must be instantiated in both branches with the correct dimensions.
        if (dir == RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(width*n,height,BufferedImage.TYPE_INT_RGB);
            Graphics g = newImg.getGraphics();
            for(int i=0; i<n; i++){
                g.drawImage(img,i*width,0,null);
            }
            g.dispose();
        } else {
            newImg = new BufferedImage(width,height*n,BufferedImage.TYPE_INT_RGB);
            Graphics g = newImg.getGraphics();
            for(int i=0; i<n; i++){
                g.drawImage(img,i*height,0,null);
            }
            g.dispose();
        }
        return newImg;
    }

    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}

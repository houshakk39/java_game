import java.awt.*;

public class Sprite {
    protected double x, y;
    protected Image image;
    protected double tileWidth, tileHeight;

    // Constructor for Sprite
    public Sprite(double x, double y, Image image, double tileWidth, double tileHeight) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) tileWidth, (int) tileHeight, null);    // Default drawing behavior (to be overridden)
    }

    // Getter and Setter methods
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Add the getBounds method
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) tileWidth, (int) tileHeight);
    }
}
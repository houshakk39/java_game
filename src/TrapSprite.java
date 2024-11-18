import java.awt.*;
import java.awt.image.BufferedImage;

public class TrapSprite extends Sprite {
    public TrapSprite(double x, double y, BufferedImage image) {
        // Appeler le constructeur de la superclasse Sprite avec les bons paramètres
        super(x, y, image, image.getWidth(), image.getHeight());
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, null);
    }
}
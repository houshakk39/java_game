import java.awt.*;

public class SolidSprite extends Sprite {

    public SolidSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height); // Appel du constructeur de la classe parent (Sprite)
    }

    // Méthode pour dessiner un élément solide
    @Override
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, (int) tileWidth, (int) tileHeight, null);
        }
    }

}
import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelSprite extends Sprite {
    private String nextLevelPath; // Chemin du fichier du niveau suivant

    public LevelSprite(double x, double y, BufferedImage image, String nextLevelPath) {
        super(x, y, image, image.getWidth(), image.getHeight());
        this.nextLevelPath = nextLevelPath;
    }

    public String getNextLevelPath() {
        return nextLevelPath;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}

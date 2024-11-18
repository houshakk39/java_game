import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment;

    public Playground(String pathName) {
        environment = new ArrayList<>();
        try {
            // Charger les images pour les différents types de sprites
            Image imageTree = ImageIO.read(new File("img/tree.png"));
            Image imageRock = ImageIO.read(new File("img/rock.png"));
            Image imageGrass = ImageIO.read(new File("img/grass.png"));
            Image imageTrap = ImageIO.read(new File("img/trap.png"));
            Image imageTileSetTest = ImageIO.read(new File("img/tileSetTest.png")); // image du test à changer après

            // Lire le fichier de niveau ligne par ligne
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line;
            int lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null) {
                int columnNumber = 0;
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    char c = (char) element;
                    switch (c) {
                        case 'T':
                            // Arbre
                            environment.add(new SolidSprite(columnNumber * imageTree.getWidth(null),
                                    lineNumber * imageTree.getHeight(null),
                                    imageTree, imageTree.getWidth(null), imageTree.getHeight(null)));
                            break;
                        case 'R':
                            // Rocher
                            environment.add(new SolidSprite(columnNumber * imageRock.getWidth(null),
                                    lineNumber * imageRock.getHeight(null),
                                    imageRock, imageRock.getWidth(null), imageRock.getHeight(null)));
                            break;
                        case ' ':
                            // Espace -> Herbe
                            environment.add(new Sprite(columnNumber * imageGrass.getWidth(null),
                                    lineNumber * imageGrass.getHeight(null),
                                    imageGrass, imageGrass.getWidth(null), imageGrass.getHeight(null)));
                            break;
                        case 'L':
                            // 'L' -> level tile
                            environment.add(new Sprite(columnNumber * imageTileSetTest.getWidth(null),
                                    lineNumber * imageTileSetTest.getHeight(null),
                                    imageTileSetTest, imageTileSetTest.getWidth(null), imageTileSetTest.getHeight(null)));
                            break;

                        case 'K':
                            // Piège
                            environment.add(new TrapSprite(columnNumber * imageTrap.getWidth(null),
                                    lineNumber * imageTrap.getHeight(null),
                                    (BufferedImage) imageTrap));
                            break;
                        case '.':
                            // Caractère vide (pas de sprite à ajouter)
                            break;
                    }
                    columnNumber++;
                }
                lineNumber++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retourne l'environnement contenant tous les sprites
    public ArrayList<Sprite> getEnvironment() {
        return environment;
    }
}
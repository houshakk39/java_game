import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main extends JPanel {
    private DynamicSprite hero;
    private GameEngine gameEngine;
    private Playground playground;
    private ArrayList<Sprite> environment;
    private ArrayList<Enemy> enemies;
    private int currentLevel = 1; // Variable pour suivre le niveau actuel
    private String levelPath = "data/level1.txt"; // Chemin initial du niveau

    // Variables pour calculer les FPS
    private long lastTime = System.currentTimeMillis();  // Pour mesurer le temps
    private int frames = 0;                              // Nombre de frames rendues
    private int fps = 0;                                 // Framerate calculé

    public Main() {
        try {
            // Initialize Playground and load environment
            playground = new Playground("data/level1.txt");
            environment = playground.getEnvironment();

            // Initialize GameEngine with hero and environment
            hero = new DynamicSprite(200, 300, ImageIO.read(new File("img/heroTileSheetLowRes.png")), 48, 50);
            gameEngine = new GameEngine(hero, environment);

            // Initialize enemies
            enemies = new ArrayList<>();
            Image enemyImage = ImageIO.read(new File("img/enemy.png"));
            enemies.add(new Enemy(400, 100, enemyImage, 48, 50)); // enemy1 and its position
            enemies.add(new Enemy(800, 500, enemyImage, 48, 50)); // enemy2 and its position

            // Add KeyListener to capture keyboard events
            this.addKeyListener(gameEngine);
            this.setFocusable(true); // Allow panel to capture key events
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadNextLevel() {
        // Déterminer le niveau suivant et charger le bon fichier
        switch (currentLevel) {
            case 1:
                levelPath = "data/level2.txt";
                break;
            case 2:
                levelPath = "data/level3.txt";
                break;
            default:
                return; // Pas de niveau suivant
        }

        currentLevel++; // Incrémenter le niveau actuel
        try {
            playground = new Playground(levelPath);
            environment = playground.getEnvironment();
            hero.setX(200); // Réinitialiser la position du héros
            hero.setY(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all sprites in the environment
        for (Sprite sprite : environment) {
            sprite.draw(g);
        }

        // Draw the hero
        if (hero != null) {
            hero.draw(g);

            // Draw the health bar above the hero
            int barWidth = 100;
            int healthWidth = (int) (barWidth * ((double) hero.getCurrentHealth() / hero.getMaxHealth()));
            g.setColor(Color.RED);
            g.fillRect((int) hero.getX(), (int) hero.getY() - 10, healthWidth, 5);  // Red health bar
            g.setColor(Color.BLACK);
            g.drawRect((int) hero.getX(), (int) hero.getY() - 10, barWidth, 5);      // Outline of health bar
        }

        // Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        displayFPS(g);
    }


    private void displayFPS(Graphics g) {
        frames++;
        long currentTime = System.currentTimeMillis();

        // Mettre à jour les FPS chaque seconde
        if (currentTime - lastTime >= 1000) {
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }

        // Afficher les FPS en haut à gauche
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("FPS: " + fps, 10, 20);
    }

    public static void main(String[] args) {
        // Set up JFrame
        JFrame frame = new JFrame("Java Game Test");
        Main panel = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.add(panel);
        frame.setVisible(true);

        // Timer for redrawing and updating the game
        Timer timer = new Timer(16, e -> {
            panel.repaint();       // Redraw the panel
            panel.gameEngine.update();  // Update game logic

            for (Enemy enemy : panel.enemies) {
                enemy.followHero(panel.hero, panel.environment);
            }
        });
        timer.start();
    }
}
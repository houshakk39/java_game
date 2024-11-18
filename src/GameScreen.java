import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private DynamicSprite hero;
    private GameEngine gameEngine;
    private Playground playground;
    private ArrayList<Sprite> environment;

    public GameScreen() {
        try {
            // Initialiser Playground et charger l'environnement
            playground = new Playground("data/level1.txt");
            environment = playground.getEnvironment(); // Récupérer l'environnement du Playground

            // Initialiser GameEngine avec le héros et l'environnement
            hero = new DynamicSprite(200, 300, new ImageIcon("img/heroTileSheetLowRes.png").getImage(), 48, 50);
            gameEngine = new GameEngine(hero, environment);

            // Ajouter KeyListener pour capturer les événements clavier
            this.addKeyListener(gameEngine);
            this.setFocusable(true); // Permet au panneau de capturer les événements clavier
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner tous les sprites dans l'environnement
        for (Sprite sprite : environment) {
            sprite.draw(g);  // Dessiner chaque sprite
        }

        // Dessiner le héros
        if (hero != null) {
            hero.draw(g);
        }
    }

    // Méthode pour mettre à jour l'état du jeu
    public void update() {
        // Ajouter la logique de mise à jour du jeu ici
        // Par exemple : hero.updatePosition();
    }
}
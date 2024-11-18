import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine implements KeyListener {
    private DynamicSprite hero;
    private ArrayList<Sprite> environment;
    private boolean isGameOver = false;  // Variable pour vérifier si le jeu est terminé

    public GameEngine(DynamicSprite hero, ArrayList<Sprite> environment) {
        this.hero = hero;
        this.environment = environment;
    }

    // Cette méthode s'occupe de gérer la mort du héros et affiche "Game Over"
    public void update() {
        if (!isGameOver) {
            hero.moveIfPossible(environment);

            // Vérifiez la collision avec les pièges
            for (Sprite sprite : environment) {
                if (sprite instanceof TrapSprite && hero.getBounds().intersects(sprite.getBounds())) {
                    hero.takeDamage(1);  // Réduire la santé de 1 lorsqu'il entre en collision avec un piège
                    if (!hero.isAlive()) {
                        isGameOver = true;  // Le jeu est terminé si le héros meurt
                        // Forcer un redessin du jeu pour afficher "Game Over"
                        repaint();
                    }
                }
            }
        }
    }

    // Affichez "Game Over" et le bouton Retry si le jeu est terminé
    public void draw(Graphics g) {
        if (isGameOver) {
            // Afficher "GAME OVER"
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 200, 200);  // Positionner "Game Over" au centre de l'écran

            // Afficher le bouton Retry
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Press Enter to Retry", 250, 300);  // Positionner "Press Enter to Retry"
        } else {
            // Dessiner les objets du jeu si le jeu est en cours
            hero.draw(g);
            for (Sprite sprite : environment) {
                sprite.draw(g);
            }
        }
    }

    // Lorsque vous cliquez sur le bouton "Retry", réinitialisez le jeu
    public void retryGame() {
        // Réinitialisez les paramètres du jeu
        isGameOver = false;

        // Assurez-vous que la santé est réinitialisée à la valeur maximale
        hero.setHealth(hero.getMaxHealth());

        // Réinitialisez la position du héros (ajustez selon votre logique)
        hero.setX(200);
        hero.setY(300);

        // Vérifiez si la santé est réinitialisée correctement
        System.out.println("Health reset to: " + hero.getCurrentHealth());  // Debug

        // Réinitialisez les pièges et autres objets si nécessaire
        // Exemple : réinitialiser la position ou l'état des pièges
        for (Sprite sprite : environment) {
            if (sprite instanceof TrapSprite) {
                // Réinitialiser l'état du piège (si applicable)
                // sprite.reset();  // Décommentez et ajustez selon votre logique
            }
        }

        // Redessiner la fenêtre après réinitialisation
        repaint();
    }

    // Méthodes pour KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) {
            // Si "Game Over", vérifiez si le joueur clique sur "Retry"
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                retryGame();  // Relance le jeu si le joueur appuie sur Enter
            }
        } else {
            // Logique de déplacement et autres actions
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    hero.setDirection(DynamicSprite.Direction.NORTH);
                    hero.setSpeed(5.0); // Définit une vitesse de déplacement
                    break;
                case KeyEvent.VK_DOWN:
                    hero.setDirection(DynamicSprite.Direction.SOUTH);
                    hero.setSpeed(5.0);
                    break;
                case KeyEvent.VK_LEFT:
                    hero.setDirection(DynamicSprite.Direction.WEST);
                    hero.setSpeed(5.0);
                    break;
                case KeyEvent.VK_RIGHT:
                    hero.setDirection(DynamicSprite.Direction.EAST);
                    hero.setSpeed(5.0);
                    break;
                case KeyEvent.VK_F:
                    hero.setSpeed(10.0); // Double la vitesse si "F" est maintenu
                    break;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                hero.stop(); // Arrête le mouvement lorsque la touche est relâchée
                break;
            case KeyEvent.VK_F:
                hero.setSpeed(0.0); // Réinitialise la vitesse normale si "F" est relâché
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Optionnel
    }

    // Méthode pour forcer un redessin de l'écran (à appeler dans un environnement graphique)
    public void repaint() {
        // Cette méthode doit être appelée pour redessiner la fenêtre
        // Dans une fenêtre de jeu, il faut s'assurer que cette méthode soit appelée via la boucle de jeu ou un autre mécanisme
    }

    // Méthode appelée dans la boucle principale de votre jeu (assurez-vous qu'elle est bien dans une boucle)
    public void gameLoop() {
        // Mise à jour de l'état du jeu
        update();

        // Redessiner les éléments graphiques
        // L'appel à repaint() doit redessiner l'écran
    }
}
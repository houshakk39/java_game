import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel {
    private JButton playButton;

    public TitleScreen() {
        // Créer le bouton "Jouer"
        playButton = new JButton("Jouer");
        playButton.setFont(new Font("Arial", Font.PLAIN, 24));
        playButton.setPreferredSize(new Dimension(200, 60));
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);

        // Ajouter un ActionListener au bouton
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lorsque l'on clique sur "Jouer", on change l'écran
                GameScreen gameScreen = new GameScreen(); // Créer un nouvel écran de jeu
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(TitleScreen.this);
                topFrame.setContentPane(gameScreen); // Remplacer le contenu de la fenêtre
                topFrame.revalidate();  // Revalider le contenu de la fenêtre
                topFrame.repaint();     // Repeindre la fenêtre
            }
        });

        // Organiser le bouton dans le panneau
        this.setLayout(new BorderLayout());
        this.add(playButton, BorderLayout.CENTER);
        this.setBackground(Color.BLACK);  // Fond noir pour l'écran titre
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Bienvenue dans le jeu!", 150, 100);  // Affiche un message
    }
}
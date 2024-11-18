import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JPanel implements Engine {
    private List<Displayable> renderList;

    // Constructeur par défaut qui initialise la liste renderList
    public RenderEngine() {
        renderList = new ArrayList<>();
    }

    // Setter pour renderList
    public void setRenderList(List<Displayable> renderList) {
        this.renderList = renderList;
    }

    // Méthode pour ajouter un élément à la renderList
    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    @Override
    public void update() {
        repaint(); // Redessine le panneau régulièrement
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Appelle la méthode paint de JPanel

        // Dessine chaque élément de renderList en appelant leur méthode draw
        for (Displayable displayable : renderList) {
            displayable.draw(g);
        }
    }
}
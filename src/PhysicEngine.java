import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private ArrayList<Sprite> movingSpriteList;  // List of moving sprites (DynamicSprites)
    private ArrayList<Sprite> environment;       // List of solid sprites (SolidSprites)

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
    }

    // Method to add an element to the movingSpriteList
    public void addMovingSprite(Sprite sprite) {
        movingSpriteList.add(sprite);
    }

    // Setter for the environment (list of solid sprites)
    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    // Overridden update method to check if movement is possible for each moving sprite
    @Override
    public void update() {
        // Loop through each sprite in the movingSpriteList
        for (Sprite sprite : movingSpriteList) {
            if (sprite instanceof DynamicSprite) {
                // If the sprite is a DynamicSprite, check if movement is possible
                DynamicSprite dynamicSprite = (DynamicSprite) sprite;
                dynamicSprite.moveIfPossible(environment); // Move if no collision
            }
        }
    }
}
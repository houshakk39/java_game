import java.awt.*;
import java.util.ArrayList;

public class Enemy extends DynamicSprite {
    private int attackDamage = 5;
    private int attackRange = 30; // Range within which the enemy can attack
    private int attackCooldown = 1000; // Cooldown in milliseconds
    private long lastAttackTime = 0;

    // Additional variables for animation
    private int animationFrame = 0;
    private int maxAnimationFrames = 1; // Assuming 4 frames per direction for animation

    public Enemy(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void followHero(DynamicSprite hero, ArrayList<Sprite> environment) {
        // Calculate direction to the hero
        double dx = hero.getX() - this.getX();
        double dy = hero.getY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > attackRange) {
            // Normalize direction and move towards the hero
            double normalizedX = dx / distance;
            double normalizedY = dy / distance;

            setSpeed(2.0); // Adjust speed as needed
            this.setX(this.getX() + normalizedX * getSpeed());
            this.setY(this.getY() + normalizedY * getSpeed());

            // Update the animation based on movement direction
            if (dx > 0) {
                setDirection(Direction.EAST); // Facing right
            } else if (dx < 0) {
                setDirection(Direction.WEST); // Facing left
            } else if (dy > 0) {
                setDirection(Direction.SOUTH); // Facing down
            } else if (dy < 0) {
                setDirection(Direction.NORTH); // Facing up
            }

            // Update animation frame
            animationFrame = (animationFrame + 1) % maxAnimationFrames;
        } else {
            // Stop moving and attack if in range
            this.stop();
            attackHero(hero);
        }
    }

    protected double getSpeed() {
        return 2.0; // Set a non-zero speed (adjust as needed)
    }

    private void attackHero(DynamicSprite hero) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= attackCooldown) {
            hero.takeDamage(attackDamage); // Reduce the hero's health
            lastAttackTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics g) {
        // Get the correct sprite frame based on the current direction and animation frame
        int tileX = currentTileX * tileWidth;
        int tileY = currentTileY * tileHeight + (animationFrame * tileHeight); // Adjust for animation frames

        // Draw sprite based on direction and animation
        if (direction == Direction.EAST || direction == Direction.WEST) {
            g.drawImage(image, (int) getX() + tileWidth, (int) getY(), (int) getX(), (int) getY() + tileHeight,
                    tileX, tileY, tileX + tileWidth, tileY + tileHeight, null);
        } else {
            g.drawImage(image, (int) getX(), (int) getY(), (int) getX() + tileWidth, (int) getY() + tileHeight,
                    tileX, tileY, tileX + tileWidth, tileY + tileHeight, null);
        }

        // Draw the health bar for the enemy
        int barWidth = 100;
        int healthWidth = (int) (barWidth * ((double) getCurrentHealth() / getMaxHealth()));
        g.setColor(new Color(128, 0, 128)); // Purple health bar for the enemy
        g.fillRect((int) getX(), (int) getY() - 10, healthWidth, 5); // Fill purple health bar
        g.setColor(Color.BLACK); // Outline
        g.drawRect((int) getX(), (int) getY() - 10, barWidth, 5);
    }
}

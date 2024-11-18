import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends Sprite {
    private Direction direction;
    private double speed;
    private int maxHealth = 100;
    private int currentHealth = 100;
    private int tileWidth = 48;
    private int tileHeight = 50;
    private int currentTileX = 0;
    private int currentTileY = 0;
    private int health;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
        this.speed = 5.0;
        this.direction = Direction.SOUTH;
    }

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void stop() {
        this.speed = 0;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case NORTH -> currentTileY = 2;
            case EAST -> currentTileY = 1;
            case SOUTH -> currentTileY = 0;
            case WEST -> currentTileY = 3;
        }
    }

    // Optional: Override getBounds() in DynamicSprite if you need direction-specific behavior
    @Override
    public Rectangle getBounds() {
        // Optionally, add logic based on direction if needed, or use the default from Sprite
        return new Rectangle((int) x, (int) y, (int) tileWidth, (int) tileHeight);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle hitBox;
        switch (direction) {
            case NORTH:
                hitBox = new Rectangle((int) x, (int) y - (int) speed, (int) tileWidth, (int) tileHeight);
                break;
            case EAST:
                hitBox = new Rectangle((int) (x + speed), (int) y, (int) tileWidth, (int) tileHeight);
                break;
            case SOUTH:
                hitBox = new Rectangle((int) x, (int) (y + speed), (int) tileWidth, (int) tileHeight);
                break;
            case WEST:
                hitBox = new Rectangle((int) (x - speed), (int) y, (int) tileWidth, (int) tileHeight);
                break;
            default:
                hitBox = new Rectangle((int) x, (int) y, (int) tileWidth, (int) tileHeight);
                break;
        }

        for (Sprite s : environment) {
            if (s instanceof SolidSprite) {
                Rectangle solidBox = new Rectangle((int) s.getX(), (int) s.getY(), (int) s.tileWidth, (int) s.tileHeight);
                if (hitBox.intersects(solidBox) && s != this) {
                    return false; // Collision detected, movement not possible
                }
            }
        }
        return true; // No collision detected, movement possible
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }
    public void update(ArrayList<Sprite> environment, Runnable loadNextLevel) {
        for (Sprite sprite : environment) {
            if (sprite instanceof LevelSprite levelSprite) {
                // Vérifier si le héros entre en collision avec le LevelSprite
                if (this.getBounds().intersects(levelSprite.getBounds())) {
                    // Charger le niveau suivant
                    loadNextLevel.run();
                    return;
                }
            }
        }

        // Effectuer le mouvement du héros si aucune transition de niveau
        this.moveIfPossible(environment);
    }

    protected void move() {
        switch (direction) {
            case NORTH -> y -= speed;
            case EAST -> x += speed;
            case SOUTH -> y += speed;
            case WEST -> x -= speed;
        }
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);  // Reduce health but prevent negative values
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public void setHealth(int health) {
        if (health > maxHealth) {
            this.currentHealth = maxHealth;
        } else if (health < 0) {
            this.currentHealth = 0;
        } else {
            this.currentHealth = health;
        }
    }


    public int getHealth() {
        return this.health;
    }
    @Override
    public void draw(Graphics g) {
        int tileX = currentTileX * tileWidth;
        int tileY = currentTileY * tileHeight;

        // Draw sprite based on direction
        if (direction == Direction.EAST || direction == Direction.WEST) {
            g.drawImage(image, (int) x + tileWidth, (int) y, (int) x, (int) y + tileHeight,
                    tileX, tileY, tileX + tileWidth, tileY + tileHeight, null);
        } else {
            g.drawImage(image, (int) x, (int) y, (int) x + tileWidth, (int) y + tileHeight,
                    tileX, tileY, tileX + tileWidth, tileY + tileHeight, null);
        }

        // Draw health bar above the sprite
        g.setColor(Color.RED);
        int healthBarWidth = (int) ((currentHealth / (double) maxHealth) * tileWidth);
        g.fillRect((int) x, (int) y - 10, healthBarWidth, 5);
    }
}
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
    private int x, y;
    private int speed;
    private Rectangle bounds;
    private double angle;

    public Bullet(int startX, int startY, double angle) {
        this.x = startX;
        this.y = startY;
        this.angle = angle;
        this.speed = 10;
        this.bounds = new Rectangle(x, y, 5, 5);
    }

    public void update() {
        x += (int) (Math.cos(Math.toRadians(angle)) * speed);
        y -= (int) (Math.sin(Math.toRadians(angle)) * speed);
        bounds.setLocation(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 5);
    }

    public boolean isOutOfScreen() {
        return x < 0 || x > GamePanel.WIDTH || y < 0 || y > GamePanel.HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

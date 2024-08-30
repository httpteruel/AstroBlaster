import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Ship {
    private int x, y;
    private int angle;
    private static final int SIZE = 15;
    private Polygon shape;
    private boolean up, left, right;

    public Ship(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.angle = 0;
        this.shape = new Polygon(
            new int[]{0, -SIZE, SIZE},
            new int[]{-SIZE, SIZE, SIZE},
            3
        );
        updateShape();
    }

    public void update() {
        if (up) {
            double rad = Math.toRadians(angle);
            x += (int) (Math.cos(rad) * 7);
            y -= (int) (Math.sin(rad) * 7);
        }
        if (left) {
            angle -= 7;
        }
        if (right) {
            angle += 7;
        }

        if (x < 0) x = GamePanel.WIDTH;
        if (x > GamePanel.WIDTH) x = 0;
        if (y < 0) y = GamePanel.HEIGHT;
        if (y > GamePanel.HEIGHT) y = 0;

        updateShape();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawPolygon(shape);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAngle() {
        return angle;
    }

    public Rectangle getBounds() {
        return shape.getBounds();
    }

    private void updateShape() {
        int[] xPoints = {0, -SIZE, SIZE};
        int[] yPoints = {-SIZE, SIZE, SIZE};

        double rad = Math.toRadians(angle);
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);

        for (int i = 0; i < xPoints.length; i++) {
            int xOld = xPoints[i];
            int yOld = yPoints[i];
            xPoints[i] = (int) (cos * xOld - sin * yOld + x);
            yPoints[i] = (int) (sin * xOld + cos * yOld + y);
        }

        shape = new Polygon(xPoints, yPoints, xPoints.length);
    }
}

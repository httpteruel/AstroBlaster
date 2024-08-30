import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

public class Asteroid {
    private int x, y;
    private int size;
    private Polygon shape;
    private double velocityX, velocityY;

    public Asteroid(int startX, int startY, int size) {
        this.x = startX;
        this.y = startY;
        this.size = size;
        this.velocityX = (Math.random() - 0.5) * 4;
        this.velocityY = (Math.random() - 0.5) * 4;
        this.shape = createShape();
    }

    private Polygon createShape() {
        Random random = new Random();
        int numPoints = 12;
        int[] xPoints = new int[numPoints];
        int[] yPoints = new int[numPoints];

        for (int i = 0; i < numPoints; i++) {
            double angle = Math.toRadians(i * (360.0 / numPoints));
            double radius = size * (0.7 + random.nextDouble() * 0.6);
            xPoints[i] = (int) (Math.cos(angle) * radius);
            yPoints[i] = (int) (Math.sin(angle) * radius);
        }

        return new Polygon(xPoints, yPoints, numPoints);
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    
        // Ricochetear
        if (x < 0) {
            x = 0;
            velocityX = -velocityX; // Inverte a direção horizontal
        }
        if (x > GamePanel.WIDTH) {
            x = GamePanel.WIDTH;
            velocityX = -velocityX;
        }
        if (y < 0) {
            y = 0;
            velocityY = -velocityY; // Inverte a direção vertical
        }
        if (y > GamePanel.HEIGHT) {
            y = GamePanel.HEIGHT;
            velocityY = -velocityY;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        Polygon drawnShape = new Polygon();
        for (int i = 0; i < shape.npoints; i++) {
            int xPoint = shape.xpoints[i] + x;
            int yPoint = shape.ypoints[i] + y;
            drawnShape.addPoint(xPoint, yPoint);
        }
        g.drawPolygon(drawnShape);
    }
    
    public Rectangle getBounds() {
        // Retorna os limites do polígono deslocados para a posição atual
        return new Rectangle(x, y, shape.getBounds().width, shape.getBounds().height);
    }
}

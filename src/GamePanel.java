import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Ship ship;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Bullet> bullets;
    private Timer timer;
    private int asteroidCount;
    private int maxAsteroids;
    private double increaseFactor;
    private int score;
    private int lives; // Adiciona a variável de vidas

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        ship = new Ship(WIDTH / 2, HEIGHT / 2);
        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        timer = new Timer(16, this); // 60 FPS
        setBackground(Color.BLACK);

        // Inicializa as variáveis
        maxAsteroids = 10;
        increaseFactor = 0.025; // 2.5% aumento por onda
        asteroidCount = maxAsteroids;
        score = 0;
        lives = 3; // Define 3 vidas
        spawnAsteroids();
    }

    private void spawnAsteroids() {
        asteroids.clear();
        for (int i = 0; i < asteroidCount; i++) {
            asteroids.add(new Asteroid((int)(Math.random() * WIDTH), (int)(Math.random() * HEIGHT), 30));
        }
    }

    public void startGame() {
        spawnAsteroids();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ship.draw(g);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        // Desenha o score
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(10f)); // Define o tamanho da fonte
        g.drawString("Score: " + score, 10, 20); // Desenha o score no canto superior esquerdo

        // Desenha as vidas
        g.setColor(Color.RED);
        int lifeIconSize = 10;
        for (int i = 0; i < lives; i++) {
            g.fillOval(10 + i * (lifeIconSize + 5), 30, lifeIconSize, lifeIconSize); // Desenha os ícones de vida
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ship.update();
        for (Asteroid asteroid : asteroids) {
            asteroid.update();
        }
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.isOutOfScreen()) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        boolean shipCollided = false;

        // Verifica colisões entre balas e asteroides
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getBounds().intersects(asteroid.getBounds())) {
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    score += 10; // Adiciona 10 pontos por asteroide destruído
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        asteroids.removeAll(asteroidsToRemove);

        // Verifica colisões entre a nave e os asteroides
        for (Asteroid asteroid : asteroids) {
            if (ship.getBounds().intersects(asteroid.getBounds())) {
                shipCollided = true;
                break; // Se a nave colidiu com um asteroide, não precisa verificar mais
            }
        }

        if (shipCollided) {
            lives--; // Perde uma vida ao colidir com um asteroide
            if (lives == 0) {
                // Lógica para fim de jogo
                timer.stop();
                System.out.println("Game Over! Final Score: " + score);
                // Adicione lógica para reiniciar o jogo ou sair
            } else {
                // Se o jogador ainda tem vidas, reinicie a posição da nave e asteroides
                ship = new Ship(WIDTH / 2, HEIGHT / 2);
                spawnAsteroids();
            }
        }

        if (asteroids.isEmpty() && lives > 0) {
            maxAsteroids += (int)(maxAsteroids * increaseFactor); // Aumenta o número de asteroides
            asteroidCount = maxAsteroids;
            spawnAsteroids();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        ship.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(ship.getX(), ship.getY(), ship.getAngle()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ship.keyReleased(e);
    }
}

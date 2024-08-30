import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("AstroBlaster");
        GamePanel gamePanel = new GamePanel();

        frame.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.setVisible(true);

        gamePanel.startGame();
    }
}

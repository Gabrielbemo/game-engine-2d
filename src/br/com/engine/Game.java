package br.com.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    private final int WIDTH = 240;
    private final int HEIGHT = 160;
    private final int SCALE = 3;
    private int x;

    private BufferedImage image;

    private Spritesheet sheet;
    private BufferedImage player;

    public Game() {
        sheet = new Spritesheet("/spritesheet.png");
        player = sheet.getSprite(0, 0, 16, 16);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    }

    public void initFrame() {
        frame = new JFrame("Main screen");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void tick() {
        x++;
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(91, 37, 88));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Ola mundo", 20, 20);

        g.setColor(Color.GREEN);
        g.fillRect(20, 20, 20, 20);
        g.fillRect(10, 10, 20, 20);

        /*renderizando o game*/
        g.drawImage(player, x, 40, null);
        /***/
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("fps: " + frames);
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
        stop();
    }
}

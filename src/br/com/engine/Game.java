package br.com.engine;

import javax.swing.*;
import java.awt.*;

public class Game extends Canvas implements Runnable {

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    private final int WIDTH = 160;
    private final int HEIGHT = 120;
    private final int SCALE = 4;

    public Game() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
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

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void run() {
        while (isRunning) {
            System.out.println("Game rodando");
        }
    }
}

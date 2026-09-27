package controller;

import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller2D {
    private final Panel panel;
    // snake
    private int snakeX;
    private int snakeY;

    public Controller2D(Panel panel) {
        this.panel = panel;

        snakeX = panel.getWidth() / 2;
        snakeY = panel.getHeight() / 2;
        panel.getRaster().setRGB(snakeX, snakeY, 0xffff00);

        initListeners();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.getRaster().setRGB(e.getX(), e.getY(), 0xffff00);
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Úsečka horizontální
                if(e.getKeyCode() == KeyEvent.VK_L){
                    for(int x = 300; x <= 400; x++) {
                        panel.getRaster().setRGB(x, 300, 0xffff00);
                    }
                    panel.repaint();
                }
                // Úsečku ze středu obrazovky po pravý okraj
                if(e.getKeyCode() == KeyEvent.VK_X){
                    // 1. spočítat střed
                    int centerX = panel.getWidth() / 2;
                    int centerY = panel.getHeight() / 2;

                    // 2. spočítat pravý okraj
                    int rightBorder = panel.getWidth();

                    // 3. cyklus, který obarví pixely
                    for (int x = centerX; x < rightBorder; x++) {
                        panel.getRaster().setRGB(x, centerY, 0xffff00);
                    }

                    panel.repaint();
                }

                // Snake
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    snakeY--;

                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    snakeY++;

                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    snakeX++;

                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    snakeX--;

                if(0 <= snakeX && snakeX < panel.getWidth() && 0 <= snakeY && snakeY < panel.getHeight()) {
                    panel.getRaster().setRGB(snakeX, snakeY, 0xffff00);
                    panel.repaint();
                }
            }
        });
    }
}

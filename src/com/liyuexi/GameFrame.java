package com.liyuexi;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_L;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    public GameFrame(){
        gamePanel = new GamePanel();

        setContentPane(gamePanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }



}

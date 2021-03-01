//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.io.*;

public class TetrisWindow extends JFrame {
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_width = 900;
    private int win_height= 750;


    public TetrisWindow(){
        this.setTitle("The Tetris Game");
        this.setSize(win_width,win_height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new TetrisGame(26, 16);
        display = new TetrisDisplay(game);

        this.add(display);
        initMenu();
        this.setVisible(true);
    }

    public void initMenu()
    {
        JMenuBar menubar =  new JMenuBar();
        this.setJMenuBar(menubar);

        JMenu men1 = new JMenu("LeaderBoard");
        JMenu men2 = new JMenu("NewGame");

        menubar.add(men1);
        menubar.add(men2);

        JMenuItem displayHighScore = new JMenuItem("Display HighScore");
        JMenuItem saveHighScore = new JMenuItem("Save HighScore");
        JMenuItem deleteHighScore = new JMenuItem("Delete HighScore");

        men1.add(displayHighScore);
        men1.addSeparator();
        men1.add(saveHighScore);
        men1.addSeparator();
        men1.add(deleteHighScore);

        displayHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.displayHighScore();
            }
        });

        saveHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.saveHighScores();
            }
        });

        deleteHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.DeleteHighScores();

            }
        });

        JMenuItem startNewGame = new JMenuItem("Start New Game?");
        men2.add(startNewGame);

        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startNewGame();

            }
        });
    }


    public static void main(String[] args)
    {
        new TetrisWindow();
    }
}
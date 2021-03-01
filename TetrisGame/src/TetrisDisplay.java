//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class TetrisDisplay extends JPanel {
    private TetrisGame game;
    private  int start_x = 0;
    private int start_y= 0;
    private int cellSize= 25;
    Timer timer;
    private Color[] brickColor ={Color.black, Color.orange,Color.GRAY, Color.pink, Color.RED,Color.blue,
                                    Color.magenta, Color.PINK};
    private int delay;

    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        delay = 200;


        this.setBackground(Color.black);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                responseToKey(e);
            }
        });

        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.isValidVerticleMove()){

                    game.makemove();
                    repaint();
                }
                else
                {

                    game.transerColor();

                    game.newpiece();
                    repaint();

                    if(game.IsGameOver())
                    {
                        timer.stop();
                    };
                }
            }
        });
        timer.start();


        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void responseToKey(KeyEvent e){

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT && game.isValidRightMove()) {
            game.getCurrentpiece().moveRight();
        }
        else if (keyCode == KeyEvent.VK_LEFT && game.isValidLeftMove()) {
            game.getCurrentpiece().moveLeft();
        }
        else if (keyCode == KeyEvent.VK_UP && game.isValidLeftRotation() && game.isValidRightrotation() && game.IsValidRotationAtBase()) {

            game.getCurrentpiece().rotate();
        }
        else if(keyCode == KeyEvent.VK_DOWN)
        {
            while(game.isValidVerticleMove()) {

                game.makemove();
            }
        }
        char readkeychar = e.getKeyChar();
        if( readkeychar == 'n')
        {
            game.startNewGame();
        }
        else if (readkeychar == 'p')
        {
            game.togglePause();
            if(!(game.GamePaused()))
            {
                timer.start();
            }
        }
    }




    //working on paint component to make the graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int StrokeWidth = 5;
       drawgrid(g);
       drawshape(g);
       drawInstruction(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke( new BasicStroke(StrokeWidth));
       g.setColor(Color.RED);
       g.drawRect(0,0,game.getNumberCols()*cellSize,game.getNumberRows()*cellSize);

        if(game.GamePaused())
        {
            drawpause(g);
            timer.stop();
        }

       if(game.IsGameOver())
       {
           gameover(g,StrokeWidth);
       }

    }
    public void gameover(Graphics g, int strokeWidth){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke( new BasicStroke(strokeWidth));
        g.setColor(Color.BLACK);
        g.drawRect(200,150, 520,120);
        g.setColor(Color.WHITE);
        g.fillRect(200,150, 520,120);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Serif", Font.BOLD, 100));
        g.drawString("Game Over!",200,250);

    }
    public void drawpause(Graphics g){

        int StrokeWidth = 10;

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke( new BasicStroke(StrokeWidth));
        g.setColor(Color.BLACK);
        g.drawRect(200,150, 420,120);
        g.setColor(Color.WHITE);
        g.fillRect(200,150, 420,120);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Serif", Font.BOLD, 100));
        g.drawString("Paused!",240,250);
    }

    public void drawgrid(Graphics g)
    {
        int x;
        int y=start_x;
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        for(int row = 0; row < this.game.getNumberRows(); row++)
        {

            x = start_x;

            for(int col = 0; col < this.game.getNumberCols(); col++) {

                x += cellSize;

                if(game.getBoardPositionValue(row,col) > 0)
                {
                    g.setColor( brickColor[game.getBoardPositionValue(row,col)]);
                    g.fillRect(col * cellSize, row * cellSize + start_y, cellSize, cellSize);

                }

            }
            y += cellSize;
        }
    }

    public void drawshape(Graphics g)
    {
        Color color = game.getCurrentpiece().getColor();
        g.setColor(color);

        int x1 = this.game.getCurrentpiece().getPosition()[0][0];
        int x2 = this.game.getCurrentpiece().getPosition()[1][0];
        int x3 = this.game.getCurrentpiece().getPosition()[2][0];
        int x4 = this.game.getCurrentpiece().getPosition()[3][0];

        int y1 = this.game.getCurrentpiece().getPosition()[0][1];
        int y2 = this.game.getCurrentpiece().getPosition()[1][1];
        int y3 = this.game.getCurrentpiece().getPosition()[2][1];
        int y4 = this.game.getCurrentpiece().getPosition()[3][1];

        g.fillRect(x1*cellSize+start_x,y1*cellSize+start_y,cellSize,cellSize);
        g.fillRect(x2*cellSize+start_x,y2*cellSize+start_y,cellSize,cellSize);
        g.fillRect(x3*cellSize+start_x,y3*cellSize+start_y,cellSize,cellSize);
        g.fillRect(x4*cellSize+start_x,y4*cellSize+start_y,cellSize,cellSize);

        g.setColor(Color.WHITE);
        g.drawRect(x1*cellSize+start_x,y1*cellSize+start_y,cellSize,cellSize);
        g.drawRect(x2*cellSize+start_x,y2*cellSize+start_y,cellSize,cellSize);
        g.drawRect(x3*cellSize+start_x,y3*cellSize+start_y,cellSize,cellSize);
        g.drawRect(x4*cellSize+start_x,y4*cellSize+start_y,cellSize,cellSize);
    }

    public void drawInstruction(Graphics g)
    {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        String scorestr = "Score:"+ game.getScore();

        g.drawString(scorestr, 500, 100 );

        String instruction = "Instruction:";
        String instructionUp = "'up; arrow to rotate";
        String instructionLeft = "'left' arrow to move left";
        String instructionRight = "'right' to move right ";
        String instructionDown = "'down' to drop the block";
        String instructionNG =  "n to start new game";

        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.setColor(Color.white);

        g.drawString(instruction, 500,170);
        g.drawString(instructionUp, 500,200);
        g.drawString(instructionLeft, 500,230);
        g.drawString(instructionRight, 500,260);
        g.drawString(instructionDown, 500,290);
        g.drawString(instructionNG, 500,310);
    }


    //getters
    public int getCellSize() {
        return cellSize;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public void setBrickColor(Color[] brickColor) {
        this.brickColor = brickColor;
    }
    public void setGame(TetrisGame game) {
        this.game = game;
    }

}

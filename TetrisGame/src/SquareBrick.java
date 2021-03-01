//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020

import javax.swing.*;
import java.awt.*;

public class SquareBrick extends TetrisBrick{
    public SquareBrick(Color colr) {

        super(colr);
        numCOlor = 5;
        initPosition();
    }

    @Override
    public void initPosition() {

        int x1 = 7;
        int y1 = 0;

        int x2 = 8;
        int y2 = 0;

        int x3 = 7;
        int y3 = 1;

        int x4 = 8;
        int y4 = 1;


        int[][] arr = {{x1,y1},
                {x2,y2},
                {x3,y3},
                {x4,y4}};


        super.setPosition(arr);
    }

    @Override
    public void rotate() {


    }
}

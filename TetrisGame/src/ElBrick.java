//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020


import java.awt.*;

public class ElBrick extends  TetrisBrick{


    public ElBrick(Color colr) {
        super(colr);
        numCOlor = 1;
        initPosition();
    }


    @Override
    public void initPosition() {
        int x1 = 8;
        int x2 = 6;
        int x3 = 7;
        int x4 = 8;
        int y1 = 0;
        int y2 = 1;
        int y3 = 1;
        int y4 = 1;
        int[][] arr = {{x1, y1},
                {x2, y2},
                {x3, y3},
                {x4, y4}};


        super.setPosition(arr);
    }

    @Override
    public void rotate() {
        if (super.getOrientation() == 0)
        {
          //  super.getPosition()[0][0] += 1;//1
            super.getPosition()[1][0] += 1;//2
       // super.getPosition()[2][0] += 1;//3
           super.getPosition()[3][0] -= 1; //4

            super.getPosition()[0][1] += 2; //1
            super.getPosition()[1][1] -= 1; //2
           // super.getPosition()[2][1] -= 1; //3
            super.getPosition()[3][1] += 1;  //4

            super.IncreaseOrientation();
        }
        else if(super.getOrientation() == 1)
        {
            super.getPosition()[0][0] -= 2; //1
            super.getPosition()[1][0] += 1; //2
            super.getPosition()[3][0] -= 1; //4

            //super.getPosition()[0][1] += 1;  11
            super.getPosition()[1][1] += 1; //2
            super.getPosition()[3][1] -= 1; //3
            super.IncreaseOrientation();  //4
        }
        else if(super.getOrientation() == 2) {
           // super.getPosition()[0][0] -= 1;//1
            super.getPosition()[1][0] -= 1;//2
            super.getPosition()[3][0] += 1;//4

            super.getPosition()[0][1] -= 2;//1
            super.getPosition()[1][1] += 1;//2
            super.getPosition()[3][1] -= 1;//4

            super.IncreaseOrientation();

        }
        else if (super.getOrientation() == 3)
        {
            super.getPosition()[0][0] += 2;//1
            super.getPosition()[1][0] -= 1;//2
            super.getPosition()[3][0] += 1;//4

         //   super.getPosition()[0][1] -= 1;//1
            super.getPosition()[1][1] -= 1;//2
            super.getPosition()[3][1] += 1;//4

            super.IncreaseOrientation();

        }

    }
}

//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020


import java.awt.*;
public abstract class TetrisBrick {
      //attributes instantiation

    private int[][] position;
    private Color color;
    private int orientation;
    private int numSegments;
    int  numCOlor;


     public TetrisBrick(Color colr){
         color = colr;
         position= new int[4][2];
    }
    //getters


    public int getNumCOlor() {
        return numCOlor;
    }

    public int[][] getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public int getOrientation() {
        return (orientation%4) ;
    }

    public int getNumSegments() {
        return numSegments;
    }


    //setters

    public void setPosition(int[][] pos) {
        for(int i =0 ; i < 4; i++)
        {
            for(int j = 0; j < 2; j++){
                position[i][j] = pos[i][j];
            }
        }
    }
    public void IncreaseOrientation()
    {
        orientation +=1;
    }

    public void setColor(Color col)
    {
        color = col;
    }

    public void setNumSegments(int numSeg) {
        numSegments = numSeg;
    }
    public void setOrientation(int orien)
    {
        orientation = orien;
    }

    //abstract methods
    public abstract void initPosition();

     public abstract void rotate();

     public void moveLeft(){
         this.position[0][0]-=1;
         position[1][0] -= 1;
         position[2][0]  -= 1;
         position[3][0] -= 1;
     }





     public void moveRight(){
         position[0][0]+=1;
         position[1][0] += 1;
         position[2][0]  += 1;
         position[3][0] += 1;

     }
     public void moveDown(){

     }

}


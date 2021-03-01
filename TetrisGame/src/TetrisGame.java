//To run tetris window and its game component
//Author: Aaditya Ranjan Mahato
//11/16/2020


import javax.sound.midi.Soundbank;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class TetrisGame {
    private  TetrisBrick currentpiece;
    private int[][] board;
    private int numberRows;
    private int numberCols;
    private int score ;
    private int state;
    String fileName = "HighScores.txt";

    public TetrisGame(int rows, int cols)
    {
        currentpiece = new SquareBrick(Color.blue);
        numberRows = rows;
        numberCols = cols;
        board = new int[numberRows][numberCols];
        this.score = 0;
        state = 0;
        PlayMusicAfterScore("Beethoven.wav");
    }


    public void clearBoard(){
        for(int i = 0; i < numberRows;i++)
        {
            for(int j = 0; j < numberCols;j++)
            {
                board[i][j] = 0;
            }
        }
    }
    public void PlayMusicAfterScore(String filepath)
    {
        try{
            File theMusicFile = new File(filepath);
            if(theMusicFile.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(theMusicFile);
                Clip theaudioclip = AudioSystem.getClip();
                theaudioclip.open(audioInput);
                theaudioclip.start();

                if(filepath == "Beethoven.wav" && !(GamePaused()))
                {
                    FloatControl gainControl = (FloatControl) theaudioclip.getControl(FloatControl.Type.MASTER_GAIN);
                    float reducVolume = -20.0f;
                    gainControl.setValue(reducVolume);
                    theaudioclip.loop(Clip.LOOP_CONTINUOUSLY);
                }

            }
            else
            {
                System.out.println("The audio file cannot be found");
            }

        }
        catch (Exception e)
        {
            System.out.println("Error in loading the file \n");
            e.printStackTrace();
        }
    }

    public boolean IsRowColored(int row, int col)
    {
        if ( board[row][col] > 0)
        {
            return true;
        }
        else {

            return false;
        }
    }


    public void RemoveRow(int row){
        for (int col = 0; col <numberCols; col++) {
            board[row][col] = 0;
        }

        for(int r = row-1; r >=0; r-- )
        {
            for(int c = 0; c < numberCols; c++ )
            {

                if(IsRowColored(r,c)) {
                    int aboverowvalue = board[r][c];
                    board[r][c] = 0;
                    board[r+1][c] = aboverowvalue;
                }

            }
        }
    }

    public void CheckForRowColored()
    {
        int row, col;
        for( row = 0; row < numberRows; row++)
        {
            for( col = 0; col < numberCols; col++) {
                if (!IsRowColored(row, col)) {
                    break;
                }

            }
                if(col == numberCols)
                {
                    this.score += 100;
                    RemoveRow(row);
                    PlayMusicAfterScore("Coin Sound.wav");
                }
        }
    }


    public boolean IsGameOver()
    {

        if(doesCollideWithOtherPieces() && IsPieceAtTopBase())
        {
            return true;
        }
        return false;
    }

    public void makemove() {

        CheckForRowColored();
         this.currentpiece.getPosition()[0][1] += 1;
        this.currentpiece.getPosition()[1][1] += 1;
        this.currentpiece.getPosition()[2][1] += 1;
        this.currentpiece.getPosition()[3][1] += 1;
        }


    public void newpiece(){
        currentpiece.getPosition()[0][1] = 0;
        currentpiece.getPosition()[1][1] = 0;
        currentpiece.getPosition()[2][1] = 1;
        currentpiece.getPosition()[3][1] = 1;

        //using random generator to make random entry of blocks
        Random rand = new Random();
        int anotherpiece = rand.nextInt(6);
        if (anotherpiece == 0) {
            currentpiece = new ZeeBrick(Color.pink);
        } else if (anotherpiece == 1) {
            currentpiece = new SquareBrick(Color.blue);
        } else if (anotherpiece == 2) {
            currentpiece = new LongBrick(Color.RED);
        } else if (anotherpiece == 3) {
            currentpiece = new StackBrick(Color.magenta);
        } else if (anotherpiece == 4) {
            currentpiece = new ElBrick(Color.ORANGE);
        } else if (anotherpiece == 5) {
            currentpiece = new EssBrick(Color.gray);
        } else if (anotherpiece == 6) {
            currentpiece = new JayBrick(Color.PINK);
        }
    }
    public void startNewGame(){

        clearBoard();
        newpiece();
        score = 0;
        state = 0;

    }

    public void transerColor(){
        int[][] pos = this.currentpiece.getPosition();

        for(int i = 0; i < 4; i++)
        {
            int x = pos[i][0];
            int y = pos[i][1];
            board[y][x] = currentpiece.getNumCOlor();
        }
    }


    boolean IsvalidtransferColor(int row, int col)
    {
        if (!(board[row][col] == 0)) {
            return true;
        }
        return false;
    }

    public void displayHighScore(){
        File highscorefile = new File(fileName);
        String accumulator = "";
        if(!highscorefile.exists())
        {
            System.out.println("The file does not exist");
        }
        try {
            Scanner inScanner = new Scanner(highscorefile);
            while(inScanner.hasNext()){
                String name = inScanner.next();
                int highscore = inScanner.nextInt();
                accumulator += name + "      " + highscore + "\n";
            }
            inScanner.close();
        }
        catch (IOException ioe){
            System.out.println("Error in reading the file \n");
            ioe.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, accumulator, "High Scores", 1);
    }

    public void DeleteHighScores(){
        try{
            FileWriter saveScore = new FileWriter(fileName);

            saveScore.write("");
            saveScore.close();
        }
        catch(IOException ioe)
        {
            System.out.println("the score could not be saved");
        }

    }

    public void saveHighScores(){
        String PlayerName = JOptionPane.showInputDialog("Please Enter your Name");
        File highscorefile = new File(fileName);
        int tempScore = 0;
        String scoreRecord = "";
        String accumulator = "";
        ArrayList<PlayerRecord> ArPlayerRecord = new ArrayList<>();

         try{

            Scanner inScanner = new Scanner(highscorefile);

            while(inScanner.hasNext()) {
                String name = inScanner.next();
                int highscore = inScanner.nextInt();

                ArPlayerRecord.add(new PlayerRecord(name, highscore));

            }

            inScanner.close();
            ArPlayerRecord.add(new PlayerRecord(PlayerName, this.score));
            Collections.sort(ArPlayerRecord,Collections.reverseOrder());

             for(int i = 0;i < ArPlayerRecord.size(); i++)
             {
                 scoreRecord += ArPlayerRecord.get(i).toString() + "\n";
             }

            FileWriter saveScore = new FileWriter(fileName);

            saveScore.write(scoreRecord);
            saveScore.close();

        }
        catch(IOException ioe)
        {
            System.out.println("the score could not be saved");
        }

    }

    public int getBoardPositionValue(int row, int col)
    {
        return board[row][col];
    }

    public boolean isValidRightMove(){
        int leftXUp = currentpiece.getPosition()[0][0];
        int rightXUp = currentpiece.getPosition()[1][0];
        int leftXDown = currentpiece.getPosition()[2][0];
        int rightXDown = currentpiece.getPosition()[3][0];
        int rightBorder = numberCols;

        if(rightXUp >= rightBorder-1 || rightXDown >= rightBorder-1 || leftXUp >= rightBorder-1 || leftXDown >= rightBorder-1 ){
            return false;
        }
        return true;
    }

    public boolean isValidLeftMove(){
        int leftXUp = currentpiece.getPosition()[0][0];
        int rightXUp = currentpiece.getPosition()[1][0];
        int leftXDown = currentpiece.getPosition()[2][0];
        int rightXDown = currentpiece.getPosition()[3][0];
        int leftBorder = 0;

        if(leftXUp <= leftBorder || leftXDown <= leftBorder || rightXDown <= leftBorder|| rightXUp <= leftBorder){
            return false;
        }
        return true;
    }

    public boolean isValidLeftRotation(){
        int leftXUp = currentpiece.getPosition()[0][0];
        int rightXUp = currentpiece.getPosition()[1][0];
        int leftXDown = currentpiece.getPosition()[2][0];
        int rightXDown = currentpiece.getPosition()[3][0];
        int leftBorder = 0;

        if(leftXUp <= leftBorder || leftXDown <= leftBorder || rightXDown <= leftBorder|| rightXUp <= leftBorder){
            return false;
        }
        return true;
    }
    public boolean isValidRightrotation(){
        int leftXUp = currentpiece.getPosition()[0][0];
        int rightXUp = currentpiece.getPosition()[1][0];
        int leftXDown = currentpiece.getPosition()[2][0];
        int rightXDown = currentpiece.getPosition()[3][0];
        int rightBorder = numberCols-1;

        if(rightXUp >= rightBorder  || rightXDown >= rightBorder || leftXUp >= rightBorder || leftXDown >= rightBorder){
            return false;
        }
        return true;
    }
    public boolean IsValidRotationAtBase(){
        int buttomLeftY = currentpiece.getPosition()[2][1];
        int buttomRightY = currentpiece.getPosition()[3][1];
        int toprightY = currentpiece.getPosition()[0][1];
        int topleftY = currentpiece.getPosition()[1][1];

        int buttomBorder = numberRows;

        if(buttomLeftY >= buttomBorder-1 || buttomRightY >= buttomBorder-1 || topleftY >= buttomBorder-1 || toprightY >= buttomBorder-1 ){
            return false;
        }
        return true;
    }

    public boolean IsPieceAtTopBase()
    {
        int buttomLeftY = currentpiece.getPosition()[2][1];
        int buttomRightY = currentpiece.getPosition()[3][1];
        int toprightY = currentpiece.getPosition()[0][1];
        int topleftY = currentpiece.getPosition()[1][1];

        if(buttomLeftY == 1 || buttomRightY == 1 || topleftY == 0 || toprightY ==0)
        {
            return  true;
        }
        return false;
    }

    public boolean doesCollideWithBase(){
        int buttomLeftY = currentpiece.getPosition()[2][1];
        int buttomRightY = currentpiece.getPosition()[3][1];
        int toprightY = currentpiece.getPosition()[0][1];
        int topleftY = currentpiece.getPosition()[1][1];

        int buttomBorder = numberRows;

        if(buttomLeftY >= buttomBorder-1 || buttomRightY >= buttomBorder-1 || topleftY >= buttomBorder-1 || toprightY >= buttomBorder-1 ){
            return true;
        }
        return false;
    }

    public boolean doesCollideWithOtherPieces(){

        int[][] pos = this.currentpiece.getPosition();
        for(int i = 0; i < currentpiece.getPosition().length; i++)
        {
            int x = pos[i][0];
            int y = pos[i][1];
            if((y+1) == board.length){
                break;
            }
            if(board[y+1][x] != 0)
            {
                return  true;
            }
        }

        return  false;}

    public boolean isValidVerticleMove(){

        if(doesCollideWithBase() || doesCollideWithOtherPieces()){
            return false;
        }
        return true;
    }


    public String toString()
    {
        return currentpiece + "," +"Grid DImension: "+ numberCols+","+ numberCols+ "Score= "+score;
    }

    public int getNumberRows() {
        return numberRows;
    }

    public void setCurrentpiece(TetrisBrick _currentpiece) {
        this.currentpiece = _currentpiece;
    }

    public int getNumberCols() {
        return numberCols;
    }

    //getters and setter
    public TetrisBrick getCurrentpiece() {
        return this.currentpiece;
    }

    public int[][] getBoard() {
        int [][] newBoard = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }


    //to return the score of the game
    public int getScore() {
        return score;
    }

    //to check if game is paused or not
    public int getState() {
        return state;
    }

    public boolean GamePaused(){
        if(state == 1)
        {
            return true;
        }
        return false;
    }



    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setNumberRows(int numberRows) {
        this.numberRows = numberRows;
    }

    public void setNumberCols(int numberCols) {
        this.numberCols = numberCols;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setState(int state) {
        this.state = state;
    }
    public void togglePause(){
        state = (state +1)%2;

    }

    public int getlengthofboard(){
        return board.length;
    }

    public int getwidthofboard(){
        return board[0].length;
    }
}


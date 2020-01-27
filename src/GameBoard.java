import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {

    private boolean[][] board;
    private int SIZE_OF_BOARD = 4;
    private int MAX_NUMBER_OF_QUEENS = 4;
    private int numberOfQueens = 0;
    
    public GameBoard(){
        board = new boolean[4][4];
        resetBoardGame();
    }
    
    public void resetBoardGame(){
        for(boolean[] row : board){
            for(boolean cell : row){
                cell = false;
            }
        }
    }
    
    private boolean isOccupied(int row, int column){
        return board[row][column];
    }
    
    public boolean putQueenOnGame(int row, int column) throws Exception{
        board[row][column] = true;
        return true;     
    }    
    
    public boolean isOutsideBoard(int row, int column){
        if(row < 0){
            return false;
        }
        if (row >= SIZE_OF_BOARD){
            return false;
        }
        if (column < 0 ){
            return false;
        }
        if( column >= SIZE_OF_BOARD){
            return false;
        }
        return true;
    }
    
    public void printGame(){
        for(boolean[] row : board){
            for(boolean cell : row){
                if(cell == false){
                    System.out.print(" O ");
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println(" ");
        }
    }
    
    public int[] askUserInput() throws NumberFormatException, IOException{
        System.out.println("Where would you like to place your queen?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a row number:");
        int row = Integer.parseInt(reader.readLine());
        
        System.out.println("Enter a column number:");
        int column = Integer.parseInt(reader.readLine());
            
        return new int[] {row - 1, column - 1};
    }
    
    public boolean hasQeenOnRow(int row){
        for(int column = 0; column < board.length; column++){
            if(board[row][column] == true){
                return true;
            }
        }
        return false;
    }
    
    public boolean hasQeenOnColumn(int column){
        for(int row = 0; row < board.length; row++){
            if(board[row][column] == true){
                return true;
            }
        }
        return false;
    }
    
    public boolean hasQueenOnDiagonal(int row, int column){
        // left up diagonal
        for(int i = row, j = column; i >= 0 && j >= 0; i--, j-- ){
            if(board[i][j]){
                return true;
            }
        }
        
        //right down diagonal
        for(int i = row, j = column; i <  SIZE_OF_BOARD && j < SIZE_OF_BOARD; i++, j++ ){
            if(board[i][j]){
                return true;
            }
        }
        
        //right up diagonal
        for(int i = row, j = column; i >= 0 && j < SIZE_OF_BOARD; i--, j++ ){
            if(board[i][j]){
                return true;
            }
        }
        
        //left down diagonal
        for(int i = row, j = column; i < SIZE_OF_BOARD && j >= 0; i++, j-- ){
            if(board[i][j]){
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isValidMove(int row, int column){
        if(!isOutsideBoard(row, column)){
            System.out.println("You try to put a queen outside the game. Please select position between 0 and " + SIZE_OF_BOARD);
            return false;
        }
        
        if(isOccupied(row, column)){
            System.out.println("This square has already a queen on it");
            return false;
        }
        
        if(hasQeenOnRow(row)){
            System.out.println("There is a queen on the row...");
            return false;
        }
        
        if(hasQeenOnColumn(column)){
            System.out.println("There is a queen on the column...");
            return false;
        }
        
        if(hasQueenOnDiagonal(row, column)){
            System.out.println("There is a queen on the diagonal...");
            return false;
        }
        
        return true;
        
    }
    
    public void putFirstQueen() throws Exception{
        int randomRow = ThreadLocalRandom.current().nextInt(0, SIZE_OF_BOARD + 1);
        int randomColumn = ThreadLocalRandom.current().nextInt(0, SIZE_OF_BOARD + 1);
        putQueenOnGame(randomRow, randomColumn);
        numberOfQueens++;
    }
    
    public void searchValidMove(){
        try{
            putFirstQueen();
        } catch (Exception e){
            e.printStackTrace(); 
        }
    }
    
    public void play(){
        while(numberOfQueens < MAX_NUMBER_OF_QUEENS){
            printGame();
        
            try{
                int[] newCoordinate = askUserInput();
                int row = newCoordinate[0];
                int column = newCoordinate[1];
                
                if(!isValidMove(row, column)){
                    System.out.println("-----Try Again------");
                }else {
                    putQueenOnGame(row, column);
                    numberOfQueens++;
                }
            }catch(Exception exception ){
                exception.printStackTrace();
            }
        }
        printGame();
        System.out.println("You won!");
    }
}

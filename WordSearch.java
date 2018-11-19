import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class WordSearch{
  private char[][]data;
  private int seed;
  private Random randgen;
  private ArrayList<String>wordsToAdd;
  private ArrayList<String>wordsAdded;
  //private int row;
  //private int col;

  /**Initialize the grid to the size specified
   *and fill all of the positions with '_'
   *@param row is the starting height of the WordSearch
   *@param col is the starting width of the WordSearch
   */

   /*
   *public WordSearch(int rows,int cols){
    *if (rows > 0 && cols > 0){
      *data = new char[rows][cols];
      *row = rows;
      *col = cols;
      *this.clear();
    *}
    *else{
      *throw new IllegalArgumentException("Number of rows or columns must be greater than 0.");
    *}
  *}
    */

  // Choose a randSeed using the clock random
  public WordSearch(int rows,int cols, String FN){
    try {
      data = new char[rows][cols];
      randgen = new Random(seed);
      wordsToAdd = new ArrayList();
      wordsAdded = new ArrayList();
      Scanner in = new Scanner(new File(FN));
      // Adds all words from file to the arraylist of words to be added
      while (in.hasNext()){
        wordsToAdd.add(in.nextLine());
      }
      this.clear();
      this.addAllWords();
      in.close();
    }
    catch(FileNotFoundException e){
      System.out.println("File not found: " + FN);
      System.exit(1);
    }
   }
  // Use the random seed specified.
  public WordSearch(int rows, int cols, String FN, int givenSeed){
   try{
     data = new char[rows][cols];
     randgen = new Random(givenSeed);
     wordsToAdd = new ArrayList();
     wordsAdded = new ArrayList();
     Scanner in = new Scanner(new File(FN));
     // Adds all words from file to the arraylist of words to be added
     while (in.hasNext()){
       wordsToAdd.add(in.nextLine());
     }
     this.clear();
     this.addAllWords();
     in.close();
   }
    catch(FileNotFoundException e){
      System.out.println("File not found: " + FN);
      System.exit(1);
    }
  }

  /**Set all values in the WordSearch to underscores'_'*/
  private void clear(){
    for (int r = 0; r < row; r++){
      for (int c = 0; c < col; c++){
        data[r][c] = '_';
      }
    }
  }

  /**Each row is a new line, there is a space between each letter
   *@return a String with each character separated by spaces, and rows separated by newlines.
   */
  public String toString(){
    String grid = new String("");
    for (int r = 0; r < data.length; r++){
      grid += "|";
      for (int c = 0; c < data[r].length; c++){
        grid += data[r][c];
        if (!(r == data[r].length - 1)){
          grid += ",";
        }
      }
      grid += "|\n";
    }

    grid += "Words: ";
    for (int i = 0; i < wordsAdded.size(); i++){
      grid += wordsAdded.get(i);
      if (!(i == data[i].length - 1)){
        grid += " ";
      }
    }
    grid += "\n";

    grid += "Seed: " + seed;
    return grid;
  }

  /**Attempts to add a given word to the specified position of the WordGrid.
   *The word is added in the direction rowIncrement,colIncrement
   *Words must have a corresponding letter to match any letters that it overlaps.
   *
   *@param word is any text to be added to the word grid.
   *@param row is the vertical locaiton of where you want the word to start.
   *@param col is the horizontal location of where you want the word to start.
   *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
   *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
   *@return true when: the word is added successfully.
   *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
   *        OR there are overlapping letters that do not match
   */
   private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
     try{
       if (row < 0 || col < 0 || col > data[row].length || row > data.length) {
         return false;
       }
       if (rowIncrement == 0 && colIncrement == 0){
         return false;
       }
       if (rowIncrement != -1 && rowIncrement != 0 && rowIncrement != 1)) {
         return false;
       }
       if (colIncrement != -1 && colIncrement != 0 && colIncrement != 1)) {
         return false;
       }
       for (int i = 0; i < word.length(); i++){
           if ((data[row + i * rowIncrement][col + i * colIncrement] != '_') && (data[row + i * rowIncrement][col + i * colIncrement != word.charAt(i))){
             return false;
           }
       }
       // to avoid adding failed words
       for (int i = 0; i < word.length(); i++){
           data[row + i * rowIncrement][col + i * colIncrement
       }
       return true;
     }catch(ArrayIndexOutOfBoundsException e){
       return false;
     }
   }
    /*[rowIncrement,colIncrement] examples:
     *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
     *[ 1,0] would add downwards because (row+1), with no col change
     *[ 0,-1] would add towards the left because (col - 1), with no row change
     */


     private void addAllWords() {
       int row;
       int col;
       int rowIncrement;
       int colIncrement;
       int attempts = 0;
       int max = 150;
       for (int i = 0; i < wordsToAdd.size() - 1; i++){
         while (attempts < max){
           row = Math.abs(randgen.nextInt(seed) % data.length - 1);
           col = Math.abs(randgen.nextInt(seed) % data[0].length - 1);
           rowIncrement = Math.abs(randgen.nextInt(2));
           colIncrement = Math.abs(randgen.nextInt(2));
           if (AddWord(wordsToAdd.get(i), row, col, rowIncrement, colIncrement)){
             attempts = max;
             String s = wordsToAdd.get(i);
             wordsToAdd.remove(i);
             wordsAdded.add(s);
           } else {
             attempts++;
           }
         }
       }
     }
  /**Attempts to add a given word to the specified position of the WordGrid.
   *The word is added from left to right, must fit on the WordGrid, and must
   *have a corresponding letter to match any letters that it overlaps.
   *
   *@param word is any text to be added to the word grid.
   *@param row is the vertical locaiton of where you want the word to start.
   *@param col is the horizontal location of where you want the word to start.
   *@return true when the word is added successfully. When the word doesn't fit,
   * or there are overlapping letters that do not match, then false is returned
   * and the board is NOT modified.
   */
  public boolean addWordHorizontal(String word, int row, int col){
    //row or col are either too small or too large
    if ((row < 0 || col < 0) || (col >= data[row].length || row >= data.length)){
      return false;
    }
    //word extends outside of grid horizontally
    if (col + word.length() - 1 > data[row].length){
      return false;
    }
    for (int i = 0; i < word.length(); i++){
        if ((data[row][i + col] != '_') && (data[row][i + col] != word.charAt(i))){
          return false;
        }
    }
    // to avoid adding failed words
    for (int i = 0; i < word.length(); i++){
        data[row][col + i] = word.charAt(i);
    }
    return true;
  }
  /**Attempts to add a given word to the specified position of the WordGrid.
    *The word is added from top to bottom, must fit on the WordGrid, and must
    *have a corresponding letter to match any letters that it overlaps.
    *
    *@param word is any text to be added to the word grid.
    *@param row is the vertical locaiton of where you want the word to start.
    *@param col is the horizontal location of where you want the word to start.
    *@return true when the word is added successfully. When the word doesn't fit,
    *or there are overlapping letters that do not match, then false is returned.
    *and the board is NOT modified.
  */
  public boolean addWordVertical(String word,int row, int col){
    //row or col are either too small or too large
    if ((row < 0 || col < 0) || (col >= data[row].length || row >= data.length)){
      return false;
    }
    //word extends outside of grid horizontally
    if (row + word.length() - 1 > data.length){
      return false;
    }
    for (int i = 0; i < word.length(); i++){
        if ((data[row + i][col] != '_') && (data[row + i][col] != word.charAt(i))){
          return false;
        }
    }
    // to avoid adding failed words
    for (int i = 0; i < word.length(); i++){
        data[row + i][col] = word.charAt(i);
    }
    return true;
  }
  /**Attempts to add a given word to the specified position of the WordGrid.
   *The word is added from top left to bottom right, must fit on the WordGrid,
   *and must have a corresponding letter to match any letters that it overlaps.
   *
   *@param word is any text to be added to the word grid.
   *@param row is the vertical locaiton of where you want the word to start.
   *@param col is the horizontal location of where you want the word to start.
   *@return true when the word is added successfully. When the word doesn't fit,
   *or there are overlapping letters that do not match, then false is returned.
   */
  public boolean addWordDiagonal(String word,int row, int col){
    //row or col are either too small or too large
    if ((row < 0 || col < 0) || (col > data[row].length || row > data.length)){
      return false;
    }
    //word extends outside of grid horizontally or vertically
    if (col + word.length() > data[row].length||row + word.length() > data.length){
      return false;
    }
    for (int i = 0; i < word.length(); i++){
        if ((data[row + i][col + i] != '_') && (data[row + i][col + i] != word.charAt(i))){
          return false;
        }
    }
    // to avoid adding failed words
    for (int i = 0; i < word.length(); i++){
        data[row + i][col + i] = word.charAt(i);
    }
    return true;
  }
}

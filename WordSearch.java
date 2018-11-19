import java.util.Arrays;
public class WordSearch{
    private char[][]data;
    private int row;
    private int col;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
      if (rows > 0 && cols > 0){
        data = new char[rows][cols];
        row = rows;
        col = cols;
        this.clear();
      }
      else{
        throw new IllegalArgumentException("Number of rows or columns must be greater than 0.");
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
      for (int r = 0; r < row; r++){
        for (int c = 0; c < col; c++){
          grid += data[r][c] + " ";
        }
        grid += "\n";
      }
      return grid;
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
    //public boolean addWordDiagonal(String word,int row, int col){
    //}
  }

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class WordSearch{
  private char[][]data;
  private int seed;
  private Random randgen;
  private ArrayList<String> wordsToAdd;
  private ArrayList<String> wordsAdded;

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
  public WordSearch(int rows, int cols, String FN, int givenSeed, boolean answer){
   try{
     data = new char[rows][cols];
     seed = givenSeed;
     randgen = new Random(seed);
     wordsToAdd = new ArrayList<String>();
     wordsAdded = new ArrayList<String>();
     Scanner in = new Scanner(new File(FN));
     // Adds all words from file to the arraylist of words to be added
     while (in.hasNextLine()){
       wordsToAdd.add(in.nextLine());
     }
     this.clear();
     this.addAllWords();
     if (!(answer)){
       this.addRandom();
     }
     in.close();
   }
    catch(FileNotFoundException e){
      System.out.println("File not found: " + FN);
      System.exit(1);
    }
  }

  /**Set all values in the WordSearch to underscores'_'*/
  private void clear(){
    for (int r = 0; r < data.length; r++){
      for (int c = 0; c < data[r].length; c++){
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
        if (!(c == data.length - 1)){
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
     if ((row < 0 || col < 0 || col > data[row].length || row > data.length)
     || (rowIncrement == 0 && colIncrement == 0)
     || (rowIncrement != -1 && rowIncrement != 0 && rowIncrement != 1)
     || (colIncrement != -1 && colIncrement != 0 && colIncrement != 1)) {
       return false;
     }
     for (int i = 0; i < word.length()-1; i++){
         if ((data[row + (i * rowIncrement)][col + (i * colIncrement)] != '_') && (data[row + (i * rowIncrement)][col + (i * colIncrement)] != word.charAt(i))){
           return false;
         }
     }
     // to avoid adding failed words
     for (int i = 0; i < word.length(); i++){
       data[row + (i * rowIncrement)][col + (i * colIncrement)] = word.charAt(i);
     }
     return true;
   }
    /*[rowIncrement,colIncrement] examples:
     *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
     *[ 1,0] would add downwards because (row+1), with no col change
     *[ 0,-1] would add towards the left because (col - 1), with no row change
     */

   private void addAllWords() {
     int row = 0;
     int col = 0;
     int rowIncrement = 0;
     int colIncrement = 0;
     int attempts = 0;
     int max = 150;
     //Random rng = new Random();
     for (int i = 0; i < wordsToAdd.size(); i++){
       while (attempts < max){
         row = (randgen.nextInt() % (data.length - 1));
         col = (randgen.nextInt() % (data[i].length - 1));
         rowIncrement = randgen.nextInt(3) - 1;
         colIncrement = randgen.nextInt(3) - 1;
         if (addWord(wordsToAdd.get(i), row, col, rowIncrement, colIncrement)){
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

   private void addRandom() {
     for (int r = 0; r < data.length; r++){
       for (int c = 0; c < data[r].length; c++){
         if (data[r][c] == '_'){
           data[r][c] = (char)(Math.abs(randgen.nextInt() % 26) + 65);
         }
       }
     }
   }

  public static void main(String[] args) {
    String output = "Input Java WordSearch #rows #columns FileName /n or /n Java WordSearch #rows #columns FileName seed /n or /n Java WordSearch #rows #columns FileName seed answers /n The boolean answers will determine if the WordSearch will include random letters or not.";

    if (args.length == 3){
      if (args[0].equals("0") || args[1].equals("0")){
        System.out.println("Row and or column must be larger than 0");
      }else {
        WordSearch ws = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], (int)System.currentTimeMillis(), false);
        System.out.println(ws);
      }
    }
    if (args.length == 4){
      if (args[0].equals("0") || args[1].equals("0")){
        System.out.println("Row and or column must be larger than 0");
      }else {
        WordSearch ws = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), false);
        System.out.println(ws);
      }
    }
    if (args.length == 5){
      if (args[0].equals("0") || args[1].equals("0")){
        System.out.println("Row and or column must be larger than 0");
      }else {
        Boolean ans = false;
        if (args[4].equals("key")){
          ans = true;
        }else{
          System.out.println("Please specify if you want to return answers by entering \'key\' as either true or false\n");
        }
        WordSearch ws = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), ans);
        System.out.println(ws);
      }
    }

    if (args.length < 3 || args.length > 5) {
      System.out.println(output);
    }
  }
}

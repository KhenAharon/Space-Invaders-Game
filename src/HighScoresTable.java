import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * HighScoresTable - table meant to keep scores.
 */
class HighScoresTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private int size;
    private List<ScoreInfo> scoreInfList;
    static final int TABLE_SIZE = 10;
    /**
     * Create an empty high-scores table with the specified size.
     * @param size of the table
     */
   public HighScoresTable(int size) {
       this.size = size;
       this.scoreInfList = new LinkedList<ScoreInfo>();
   }
   /**
    * func to check whether a new score fits in.
    * @param score to check
    * @return true if it fits in
    */
   public boolean check(int score) {
       int rank = this.getRank(score);
       if (rank < this.size) {
           return true;
       }
       return false;
   }
   /**
    * func to add new records and push out old ones.
    * @param score to be added.
    */
   public void add(ScoreInfo score) {
       int rank = this.getRank(score.getScore());
       if (rank < this.size) {
           this.scoreInfList.add(rank, score);
       }
       if (this.scoreInfList.size() > this.getSize()) {
           this.scoreInfList.remove(this.scoreInfList.size() - 1);
       }
   }

   /**
    * the function returns a table size.
    * @return size a table size.
    */
   public int getSize() {
       return this.size;
   }

   /**
    * func to return a list of current high scores.
    * @return the current high scores.
    */
   public List<ScoreInfo> getHighScores() {
       return this.scoreInfList;
   }

   /**
    * func meant to evaluate if a new value can enter
    * the list and tell us in which rank.
    * @param score to check
    * @return i the rank
    */
   public int getRank(int score) {
       int i = 0;
       for (ScoreInfo entity: scoreInfList) {
           if (score > entity.getScore()) {
               return i;
           }
           i++;
       }
       return i;
   }

   /**
    * Clears the table.
    */
   public void clear() {
       this.scoreInfList.clear();
   }

   /**
    * this sweet little func will try to load an existing
    * file of scores or create a new one if needed.
    * @param filename is the file to read from
    * @throws IOException if something goes wrong while reading.
    */
   public void load(File filename) throws IOException {
       ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
       try {
           Object obj = input.readObject();
           this.clear();
           if (obj instanceof HighScoresTable) {
               this.scoreInfList.addAll(((HighScoresTable) obj).getHighScores());
               this.size = ((HighScoresTable) obj).getSize();
           }
       } catch (ClassNotFoundException e) {
           input.close();
           throw new IOException();
       }
       input.close();
   }

   /**
    * Save table data to a specified file.
    * @param filename file to read from
    * @throws IOException if something goes wrong while reading
    */
   public void save(File filename) throws IOException {
       try {
           ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
           output.writeObject(this);
           output.close();
       } catch (FileNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
   }

   /**
    * read and create table out of given file - if
    * does not exist - an empty one returned.
    * @param filename is the file to read from
    * @return a table
    */
   public static HighScoresTable loadFromFile(File filename) {
       HighScoresTable table = new HighScoresTable(TABLE_SIZE);
       try {
           table.load(filename);
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }
       return table;
   }
}

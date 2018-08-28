import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * class LevelSets - Class meant to keep info about the level sets.
 */
public class LevelSets {
    private String keys;
    private String messages;
    private String path;
    /**
     * ctor.
     * @param k is the given key
     * @param m is the given message
     * @param p is the given path
     */
    public LevelSets(String k, String m, String p) {
        this.keys = k;
        this.messages = m;
        this.path = p;
    }
    /**
     * initialise.
     */
    public LevelSets() {
        this.keys = null;
        this.messages = null;
        this.path = null;
    }
    /**
     * read file and return a list of levels.
     * @param reader a reader
     * @return  listOfLevelSets of LevelSets.
     */
    public List<LevelSets> fromReader(java.io.Reader reader) {
        List<LevelSets> levelist = new ArrayList<LevelSets>();
        LineNumberReader is = null;
        try {
            is = new LineNumberReader(reader);
            String pass;
            while ((pass = is.readLine()) != null) {
                pass = pass.trim();
                if (is.getLineNumber() % 2 == 0) {
                    this.path = pass;
                    levelist.add(new LevelSets(this.keys, this.messages,
                            this.path));
                } else {
                    String[] split = pass.split(":");
                    this.keys = split[0];
                    this.messages = split[1];
                }
            }
        } catch (IOException e) {
            System.out.println(" Something went wrong while reading !");
            return null;
        }
        return levelist;
    }
    /**
     * @return  path.
     */
    public String getPath() {
        return this.path;
    }
    /**
     * @param myPath path.
     */
    public void setPath(String myPath) {
        this.path = myPath;
    }
    /**
     * @return  keys.
     */
    public String getKeys() {
        return this.keys;
    }
    /**
     * @param theKeys list of keys
     */
    public void setKeys(String theKeys) {
        this.keys = theKeys;
    }
    /**
     * @return  keys.
     */
    public String getMessages() {
        return this.messages;
    }
    /**
     * @param theMessages list of messages
     */
    public void setMessages(String theMessages) {
        this.messages = theMessages;
    }
}

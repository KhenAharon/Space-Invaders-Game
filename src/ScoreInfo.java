import java.io.Serializable;
/**
 * class ScoreInfo - responsible of skeeping scores with names.
 *
 */
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;
    /**
     * construct ScoreInfo according to given parameters.
     * @param name is the name
     * @param score is the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * the function returns a name.
     * @return a name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * the function returns a score.
     * @return a score.
     */
    public int getScore() {
        return this.score;
    }
}

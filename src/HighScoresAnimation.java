import java.awt.Color;
import biuoop.DrawSurface;
/**
 * HighScoresAnimation - responsible of the animation of the highscores screen.
 *
 */
public class HighScoresAnimation implements Animation {
    private static final int WINDOW_LENGHT = 600, WINDOW_WIDE = 800;
    private HighScoresTable scores;
    /**
     * construct a HighScoresAnimation according to a given parameter.
     * @param scores is the parameter to initialize.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }
    /**
     * func to create one frame.
     * @param d surface
     * @param dt speed indicator
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
       this.drawOn(d);
    }
    /**
     * the function draw a screen.
     * @param d is the surface
     */
    public void drawOn(DrawSurface d) {
            Block background = new Block(new Point(0, 0), WINDOW_WIDE,
                    WINDOW_LENGHT, new Color(102, 205, 170), -1);
            background.drawOn(d);
            d.drawText(150, 100, "Score Table", 40);
            d.setColor(Color.BLUE);
            d.drawText(150, 150, "name", 40);
            d.drawText(350, 150, "score", 40);
            int i = 150;
            for (ScoreInfo entity: this.scores.getHighScores()) {
               i += 30;
               d.drawText(150, i, entity.getName(), 20);
               d.drawText(350, i, entity.getScore() + "", 20);
           }
    }

    /**
     * the function returns true if it should stop.
     * @return the function returns
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
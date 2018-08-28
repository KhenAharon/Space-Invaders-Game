import biuoop.DrawSurface;
/**
 * interface LevelInformation - is an interface that specifies
 * the information required to fully describe a level.
 *
 */
public interface LevelInformation {
    /**
     * number of left balls.
     * @return the number of balls that was left.
     */
    int numberOfBalls();
    /**
     * Initial velocity of each ball.
     * @return a velocity list of the balls.
     */
    java.util.List<Velocity> initialBallVelocities();
    /**
     * @return the paddle's speed.
     */
    int paddleSpeed();
    /**
     * @return the paddle's width.
     */
    int paddleWidth();
    /**
     * @return level name.
     */
    String levelName();
    /**
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();
    /**
     * Draws a background on the surface.
     * @param d is the surface to be draw on.
     */
    void drawBackground(DrawSurface d);
    /**
     * @return a list of blocks that make up this level.
     */
    java.util.List<Block> blocks();
    /**
     * @return number of blocks that should be removed.
     */
    int numberOfBlocksToRemove();
}
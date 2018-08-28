/**
 * Removing balls from the game.
 */
public class BallRemover implements HitListener {
    private Counter remBalls;
    private GameLevel gl;
    /**
     * @param beingHit block being hit.
     * @param hitter ball hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gl);
        this.remBalls.decrease(1);
    }
    /**
     * remove an unnecessary ball.
     * @param gl our game level.
     * @param remBalls counter.
     */
    public BallRemover(GameLevel gl, Counter remBalls) {
        this.gl = gl;
        this.remBalls = remBalls;
    }
}
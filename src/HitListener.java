/**
 * Listeners interface.
 */
public interface HitListener {
    /**
     * @param beingHit block being hit.
     * @param hitter the hitting ball.
     */
   void hitEvent(Block beingHit, Ball hitter);
}
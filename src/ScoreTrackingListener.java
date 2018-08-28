/**
 * A listener of the game score.
 */
public class ScoreTrackingListener implements HitListener {
   private Counter currentScore;
   /**
    * @param scoreCounter current score counter.
    */
   public ScoreTrackingListener(Counter scoreCounter) {
      this.currentScore = scoreCounter;
   }
   /**
    *  Update current score according to the hit occurrences.
    *  @param beingHit block that is being hit
    *  @param hitter ball that hit the block
    */
   public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getHitPoint() >= 1) {
           this.currentScore.increase(5);
       } else {
           this.currentScore.increase(100);
       }
   }
}
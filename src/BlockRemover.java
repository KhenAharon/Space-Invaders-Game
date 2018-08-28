/**
 * BlockRemover class is in charge of removing blocks
 * and update the counter.
 */
public class BlockRemover implements HitListener {
    private GameLevel g;
    private Counter removedBlocks;
    /**
     * Ctor.
     * @param gameLevel the current game.
     * @param removedBlocks counter.
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.g = gameLevel;
        this.removedBlocks = removedBlocks;
    }
    /**
     * Update hit points and remove a block when hits counter is 0.
     * @param beingHit being hit block.
     * @param hitter hitting ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoint() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.g);
            if (beingHit.getCollidableSymbol().equals("e")) {
                this.removedBlocks.decrease(1);
            }
        }
    }
}
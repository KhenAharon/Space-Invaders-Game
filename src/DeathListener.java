/**
 * @author khen
 */
public class DeathListener {

    private boolean death;
    private final int deathR;

    /**
     * ctor.
     */
    public DeathListener() {
        this.death = false;
        this.deathR = 470;
    }
    /**
     * @param beingHit block being hit.
     */
    public void moveEvent(Block beingHit) {
        if (beingHit.getCollisionRectangle().getUpperLeft().getY() > deathR && beingHit.isAlien()) {
            this.death = true;
        }
    }
    /**
     * @return if we have reached death zone
     */
    public boolean death() {
        return this.death;
    }
    /**
     * reset death.
     */
    public void resetDeath() {
        this.death = false;
    }
}

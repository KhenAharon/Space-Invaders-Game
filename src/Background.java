import biuoop.DrawSurface;
/**
 * @author khen
 * creating a background for a level.
 */
public class Background implements Sprite {
    private LevelInformation levelInfo;
    /**
     * @param ds to draw on the surface.
     */
    public void drawOn(DrawSurface ds) {
        this.levelInfo.drawBackground(ds);
    }
    /**
     * @param levelInfo our level.
     */
    public Background(LevelInformation levelInfo) {
        this.levelInfo = levelInfo;
    }
    /**
     * @param l level to add.
     */
    public void addToGame(GameLevel l) {
        l.addSprite(this);
    }
    /**
     * nothing meanwhile.
     * @param dt frames change.
     */
    public void timePassed(double dt) {
    }
}

import biuoop.DrawSurface;
/**
 * tracking after lives number.
 */
public class LevelNameIndicator implements Sprite {
    private String s;
    private Rectangle rectangle;
    /**
     * @param rect of idicator
     * @param name of indicator
     */
    public LevelNameIndicator(Rectangle rect, String name) {
        this.s = name;
        this.rectangle = rect;
    }
    /**
     * Draws LivesIndicator block on surface.
     * @param d
     *            surface to draw.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.gray);
        d.drawText((int) this.rectangle.getUpperLeft().getX() + (int) this.rectangle.getWidth() * 3 / 4,
                (int) this.rectangle.getUpperLeft().getY() + 15, s, 15);
    }
    /**
     * @param game our game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
    /**
     * Every level we need to print only in the beggining (once).
     * So no need timePassed for this object.
     * @param dt frames change.
     */
    @Override
    public void timePassed(double dt) {
    }
}

import biuoop.DrawSurface;

/**
 * Class LivesIndicator extends Indicator implements Sprite. LivesIndicator
 * track after the number of the lives.
 */
public class LivesIndicator implements Sprite {
    private Counter count;
    private Rectangle rectangle;
    /**
     * Ctor.
     * @param rect of indicator.
     * @param score of indicator.
     */
    public LivesIndicator(Rectangle rect, Counter score) {
        this.count = score;
        this.rectangle = rect;
    }
    /**
     * @param d
     *            surface to draw.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.gray);
        String show = String.valueOf(this.count.getValue());
        d.drawText((int) this.rectangle.getUpperLeft().getX() + (int) this.rectangle.getWidth() / 7,
                (int) this.rectangle.getUpperLeft().getY() + 15, "Lives: " + show, 15);
    }
    /**
     * adding to game this object.
     * @param game our game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
    @Override
    public void timePassed(double dt) {
    }
}

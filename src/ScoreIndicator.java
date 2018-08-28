import biuoop.DrawSurface;

/**
 * Displaying the current score. Holds a reference to the score counter,
 * and will be added to the game as a sprite positioned at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter count;
    private Rectangle rectangle;
    /**
     * Ctor.
     * @param rect rectangle of score.
     * @param score the score.
     */
    public ScoreIndicator(Rectangle rect, Counter score) {

        this.count = score;
        this.rectangle = rect;
    }
    /**
     * The function draws the score on the surface.
     * @param d the surface to draw on it.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.lightGray);
        d.fillRectangle(0, 0, 800, 25);
        d.setColor(java.awt.Color.gray);
        String livesDisplay = String.valueOf(this.count.getValue());
        d.drawText((int) this.rectangle.getUpperLeft().getX() + (int) this.rectangle.getWidth() / 2 - 25,
                (int) this.rectangle.getUpperLeft().getY() + 15, "Score: " + livesDisplay, 15);
    }
    /**
     * Add this indicator to our game.
     * @param game our game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub

    }
}

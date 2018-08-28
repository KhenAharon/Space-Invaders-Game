import biuoop.DrawSurface;

/**
 * Sprite interface will define all moveable-drawable objects
 * in the game.
 */
public interface Sprite {
 /**
 * draw the sprite to the screen.
 * @param d where we draw
 */
void drawOn(DrawSurface d);
 /**
 * notify the sprite that time has passed.
 * @param dt frames change.
 */
void timePassed(double dt);
}
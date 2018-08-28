import biuoop.DrawSurface;
/**
 * @author khen
 * animation interface.
 */
public interface Animation {
    /**
     * @param d drawing surface to draw the animation.
     * @param dt frames changer
     */
    void doOneFrame(DrawSurface d, double dt);
    /**
     * @return boolean if we should stop the animation.
     */
    boolean shouldStop();
}

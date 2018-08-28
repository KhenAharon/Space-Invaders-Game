/**
 * @author Khen Aharon
 */
public interface Collidable {
    /**
     * @return collision rectangle
     */
    Rectangle getCollisionRectangle();
    /**
     * @param collisionPoint where object was hit.
     * @param currentVelocity velocity of the ball when hitting.
     * @param hitter the hitting ball.
     * @return velocity after hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
    /**
     * @return collidable symbol.
     */
    String getCollidableSymbol();
    /**
     * @return a serial of collidable.
     */
    int getSerial();
}

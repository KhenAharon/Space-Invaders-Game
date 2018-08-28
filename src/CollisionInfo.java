/**
 * @author Khen Aharon
 */
public class CollisionInfo {
    private Collidable collisionObject;
    private Point collisionPoint;

    /**
     * @param collisionObject the object that has been collided.
     * @param collisionPoint point of collision.
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;
    }
    /**
     * @return the point at which the collision occurs.
     */
    public Point getCollisionPoint() {
        return collisionPoint;
    }
    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable getCollisionObject() {
        return collisionObject;
    }
}

import java.util.ArrayList;
import java.util.Random;
/**
 * @author Khen Aharon
 */
public class GameEnvironment {
    private ArrayList<Collidable> collidables;
    /**
     * array for collids.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**
     * @param c add the collidable to the environment arr.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * @param c remove collide from environment
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
    /**
     * Method will bring data regarding collision if any took place.
     * @param traj - line which indicates the ball movement.
     * @return CollisionInfo collision information - point and object.
     */
    public CollisionInfo getClosestCollision(Line traj) {
        int indexOfCollidable = -1;
        Point closestP = null;
        Point collisionP;
        Rectangle currentC;
        for (int i = 0; i < collidables.size(); i++) {
            currentC = collidables.get(i).getCollisionRectangle();
            collisionP = traj.closestIntersectionToStartOfLine(currentC);
            if (collisionP != null) {
                //if first point
                if (closestP == null) {
                    indexOfCollidable = i;
                    closestP = collisionP;
                //otherwise
                } else {
                    if (traj.start().distance(collisionP) < traj.start().distance(closestP)) {
                        indexOfCollidable = i;
                        closestP = collisionP;
                    }
                }
            }
        }
        if (closestP != null) {
            return new CollisionInfo(collidables.get(indexOfCollidable), closestP);
        }
        return null;
    }
    /**
     * remove all collidables from environment.
     */
    public void deplete() {
        while (!collidables.isEmpty()) {
            collidables.remove(0);
        }
    }
    /**
     * @return a lowest alien.
     */
    public Collidable getAnyLowestAlien() {
        double minY = 0;
        Collidable cFinal = null;
        Random rand = new Random();
        int random = rand.nextInt(9);

        for (Collidable c : collidables) {
            if (c != null && c.getCollidableSymbol().equals("e")
                && c.getSerial() % 10 == random
                && c.getCollisionRectangle().getUpperLeft().getY() > minY) {
                cFinal = c;
                minY = cFinal.getCollisionRectangle().getUpperLeft().getY();
            }
        }
        return cFinal;
    }
}

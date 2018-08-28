import java.util.ArrayList;
import biuoop.DrawSurface;

/**
 * This class will hold all the sprite objects of the game and
 * perform some tasks on them.
 */
public class SpriteCollection {

    private ArrayList<Sprite> sprites;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

 /**
 * Method to add sprites to collection.
 * @param s a sprite to add.
 */
public void addSprite(Sprite s) {
       this.sprites.add(s);
   }

/**
 * Method to remove sprites to collection.
 * @param s a sprite to remove.
 */
   public void removeSprite(Sprite s) {
       this.sprites.remove(s);
   }

 /**
 * Call timePassed() on all sprites.
 * @param dt frames change.
 */
public void notifyAllTimePassed(double dt) {
       for (int i = 0; i < sprites.size(); i++) {
           this.sprites.get(i).timePassed(dt);
       }
   }

 /**
 * Call drawOn(d) on all sprites.
 * @param d where we draw.
 */
public void drawAllOn(DrawSurface d) {
       for (int i = 0; i < sprites.size(); i++) {
           this.sprites.get(i).drawOn(d);
       }
}
/**
 * Remove all elements (sprites) from collection.
 */
public void deplete() {
    while (!sprites.isEmpty()) {
        sprites.remove(0);
    }
}
}
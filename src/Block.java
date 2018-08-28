import java.awt.Color;
import java.util.ArrayList;
import biuoop.DrawSurface;
import java.awt.Image;
import java.util.Map;
/**
 * @author Khen Aharon
 */
public class Block implements Collidable, HitNotifier, Sprite {
    private Rectangle rect;
    private Rectangle defRect;
    private int hits;
    private ArrayList<HitListener> hitListeners;
    private ArrayList<DeathListener> deathListeners;
    private Color color;
    private Image img;
    private java.awt.Color stroke;
    private Map<Integer, Image> imageWhenHit;
    private Map<Integer, Color> colorWhenHit;
    private String symbol;
    private int dx;
    private static boolean right;
    private static final int RIGHT_CHANGE_DIR = 732;
    private static final int LEFT_CHANGE_DIR = 25;
    private boolean lastTimeRight;
    private static final int DOWN_HEIGHT = 30;
    private int serial;
    /**
     * reset to default settings.
     */
    public void resetRect() {
        this.rect = new Rectangle(defRect.getUpperLeft(), defRect.getWidth(), defRect.getHeight());
        this.dx = 1;
    }
    @Override
    public String getCollidableSymbol() {
        return this.symbol;
    }
    /**
     * @param r rectangle.
     * @param defRect rectangle.
     * @param symbol its symbol.
     * @param img its image.
     * @param hits its hits number.
     * @param serial serial
     */
    public Block(Rectangle r, Rectangle defRect, Image img, int hits, String symbol, int serial) {
        this.rect = r;
        this.img = img;
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.deathListeners = new ArrayList<DeathListener>();
        this.symbol = symbol;
        dx = 1;
        right = true;
        lastTimeRight = true;
        this.defRect = defRect;
        this.serial = serial;
    }
    /**
     * @param r rectangle.
     * @param color its color.
     * @param img its image.
     * @param hits its hits number.
     * @param stroke its stroke.
     * @param colorWhenHit its color when hit.
     * @param imageWhenHit its image when hit.
     */
    public Block(Rectangle r, Color color, Image img, int hits,
                 Color stroke, Map<Integer, Color> colorWhenHit,
                 Map<Integer, Image> imageWhenHit) {
        this.rect = r;
        this.color = color;
        this.img = img;
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.deathListeners = new ArrayList<DeathListener>();
        this.stroke = stroke;
        this.imageWhenHit = imageWhenHit;
        this.colorWhenHit = colorWhenHit;
        symbol = "";
        this.serial = 0;
    }
    /**
     * @param rect
     *            rectangle
     * @param hits
     *            no times of being hit
     */
    public Block(Rectangle rect, int hits) {
        this.rect = rect;
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.deathListeners = new ArrayList<DeathListener>();
        symbol = "";
        this.serial = 0;
    }

    /**
     * @param point
     *            location
     * @param width
     *            of block
     * @param height
     *            of block
     * @param color
     *            of block
     * @param hits
     *            how much times remaining
     */
    public Block(Point point, int width, int height, Color color, int hits) {
        rect = new Rectangle(point, width, height, color);
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.deathListeners = new ArrayList<DeathListener>();
        symbol = "";
        this.serial = 0;
    }

    @Override
    /**
     * @return this rect
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Tells collidable object whether it's been hit and return new velocity
     * accordingly.
     * @param collisionPoint
     *            collision point where there was a hit of block or paddle or
     *            wall (block too).
     * @param currentVelocity
     *            velocity before the collision.
     * @param hitter the hitting ball.
     * @return new velocity after hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle.LinePosition side = this.rect.getIntersectionLinePoisition(collisionPoint);
        if (side == Rectangle.LinePosition.Down || side == Rectangle.LinePosition.Up) {
            currentVelocity.changeDyDirection();
        }
        if (side == Rectangle.LinePosition.Left || side == Rectangle.LinePosition.Right) {
            currentVelocity.changeDxDirection();
        }
        if (hits > 0) {
            this.hits--;
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     * Draws the block on the surface.
     * @param d
     *            surface on which to draw
     */
    public void drawOn(DrawSurface d) {
        int x = (int) rect.getUpperLeft().getX();
        int y = (int) rect.getUpperLeft().getY();
        // width
        int w = (int) rect.getWidth();
        // height
        int h = (int) rect.getHeight();
        drawMe(d, x, y, w, h);
    }
    /**
     * Draws the rectangle.
     * @param d surface to draw.
     * @param x cordinate.
     * @param y cordinate.
     * @param w width.
     * @param h height.
     */
    private void drawMe(DrawSurface d, int x, int y, int w, int h) {
        if (this.stroke != null) {
            d.setColor(this.stroke);
            d.drawRectangle(x, y, w, h);
        }
        if (this.colorWhenHit != null && this.colorWhenHit.containsKey(this.hits)) {
            d.setColor(this.colorWhenHit.get(this.hits));
            d.fillRectangle(x + 1, y + 1, w, h);
        } else if (this.imageWhenHit != null && this.imageWhenHit.containsKey(this.hits)) {
            d.drawImage(x + 1, y + 1, this.imageWhenHit.get(this.hits));
        } else if (this.color != null) {
            d.setColor(this.color);
            d.fillRectangle(x + 1, y + 1, w, h);
        } else if (this.img != null) {
            d.drawImage(x + 1, y + 1, this.img);
        } else {
            d.setColor(java.awt.Color.black);
            d.drawRectangle(x, y, w, h);
            d.setColor(rect.getColor());
            d.fillRectangle(x + 1, y + 1, w - 1, h - 1);
        }
    }
    @Override
    public void timePassed(double dt) {
        if (symbol.equals("e")) {
            double newY;
            if (right) {
                if (!lastTimeRight) {
                    newY = this.rect.getUpperLeft().getY() + DOWN_HEIGHT;
                    lastTimeRight = true;
                    dx++;
                } else {
                    newY = this.rect.getUpperLeft().getY();
                }
                double newX = this.rect.getUpperLeft().getX() + dx;
                Point p = new Point(newX, newY);
                this.rect = new Rectangle(p, this.rect.getWidth(), this.rect.getHeight());
                if (newX > RIGHT_CHANGE_DIR) {
                    right = false;
                }
            } else {
                if (lastTimeRight) {
                    lastTimeRight = false;
                    newY = this.rect.getUpperLeft().getY() + DOWN_HEIGHT;
                } else {
                    newY = this.rect.getUpperLeft().getY();
                }
                double newX = this.rect.getUpperLeft().getX() - dx;
                Point p = new Point(newX, newY);
                this.rect = new Rectangle(p, this.rect.getWidth(), this.rect.getHeight());
                if (newX < LEFT_CHANGE_DIR) {
                    right = true;
                }
            }
        }
        this.notifyMove();
    }

    /**
     * the function add a ball to the game.
     * @param game
     *            is the object that the ball add to it
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * the function will remove a block.
     * @param game to remove from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    /**
     * @param hitter hitting ball.
     */
    private void notifyHit(Ball hitter) {
              // Make a copy of the hitListeners before iterating over them.
              ArrayList<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
              // Notify all listeners about a hit event:
              for (HitListener hl : listeners) {
                 hl.hitEvent(this, hitter);
              }
           }
    /**
     * let know move.
     */
    private void notifyMove() {
              // Make a copy of the hitListeners before iterating over them.
              ArrayList<DeathListener> listeners = new ArrayList<DeathListener>(this.deathListeners);
              // Notify all listeners about a hit event:
              for (DeathListener hl : listeners) {
                 hl.moveEvent(this);
              }
           }
    /**
     * @return number of hits that's taken place
     */
    public int getHitPoint() {
        return this.hits;
    }
    /**
     * the function add hl as a listener to hit events.
     * @param hl is notify listener.
     */
    public void addDeathListener(DeathListener hl) {
        this.deathListeners.add(hl);
    }
    /**
     * the function add hl as a listener to hit events.
     * @param hl is notify listener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * the function remove hl from the list of listeners to hit events.
     * @param hl is notify listener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * @return is block is alien block
     */
    public boolean isAlien() {
        if (this.symbol.equals("e")) {
            return true;
        }
        return false;
    }
    /**
     * @return serial of block.
     */
    public int getSerial() {
        return this.serial;
    }
}

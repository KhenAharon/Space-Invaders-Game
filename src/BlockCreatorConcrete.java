import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
/**
 * The maker- makes the blocks.
 */
public class BlockCreatorConcrete implements BlockCreator {
    private String symbol;
    private Integer hits;
    private Integer width;
    private Integer height;
    private Color stroke;
    private Color color;
    private Image img;
    private Map<Integer, Image> imageWhenHit;
    private Map<Integer, Color> colorWhenHit;
    private Map<Integer, String> fillK;
    private static int serializer = 0;

     /**
     * default ctor.
     */
    public BlockCreatorConcrete() {
        this.hits = 0;
        this.width = 0;
        this.height = 0;
        this.color = null;
        this.stroke = null;
        this.img = null;
        this.symbol = null;
        this.fillK = new HashMap<Integer, String>();
        this.imageWhenHit = new HashMap<Integer, Image>();
        this.colorWhenHit = new HashMap<Integer, Color>();
    }
    /**
     * @param b get parameteres from another block maker.
     */
    public BlockCreatorConcrete(BlockCreatorConcrete b) {
        this.hits = b.getHits();
        this.width = b.getWidth();
        this.height = b.getHeight();
        this.color = b.getColor();
        this.stroke = b.getStroke();
        this.img = b.getImage();
        this.symbol = b.getSymbol();
        this.fillK = b.getMap();
        this.imageWhenHit = b.getImageMap();
        this.colorWhenHit = b.getMappedColors();
    }
    /**
     * Make a block.
     * @param x x block coordinate.
     * @param y y block coordinate.
     * @return a desired block
     */
    public Block create(int x, int y) {
        fillMap();
        if (!initialize()) {
            return null;
        }
        if (this.img != null || this.color != null
            || this.imageWhenHit.size() > 1 || this.colorWhenHit.size() > 1) {
            Rectangle rect = new Rectangle(new Point((double) x, (double) y),
                                           this.width, this.height);
            Rectangle defRect = new Rectangle(new Point((double) x, (double) y),
                                             this.width, this.height);
            return new Block(rect, defRect, this.img, this.hits, this.symbol, ++serializer);
        }
        return null;
    }
    /**
     * Setting an image and a color.
     */
    private void fillMap() {
        for (int i = 0; i <= this.hits; i++) {
            if (getMap().containsKey(i)) {
                ColorsParser parser = new ColorsParser();
                String[] type = this.fillK.get(i).split("\\(");
                if (type[0].equals("color")) {
                    Color c = null;
                    c = parser.colorFromString(type);
                    setHitColor(i, c);
                } else if (type[0].equals("image")) {
                    Image image = parser.imageFromString(type);
                    setHitImg(i, image);
                }
            }
        }
    }
    /**
     * @param hit hits number.
     * @param c the set color.
     */
    private void setHitColor(int hit, Color c) {
        this.colorWhenHit.put(hit, c);
    }
    /**
     * @param hit hit no.
     * @param fill filling image.
     */
    private void setHitImg(int hit, Image fill) {
        this.imageWhenHit.put(hit, fill);
    }
    /**
     * @return map of images.
     */
    private Map<Integer, Image> getImageMap() {
        Map<Integer, Image> m = new HashMap<Integer, Image>();
        m.putAll(this.imageWhenHit);
        return m;
    }
    /**
     * @return fill list map.
     */
    private Map<Integer, String> getMap() {
        Map<Integer, String> m = new HashMap<Integer, String>();
        m.putAll(this.fillK);
        return m;
    }
    /**
     * @return whether block has symbol, hits, width and height
     */
    private boolean initialize() {
        if (this.symbol == null) {
            return false;
        }
        if (this.hits == null) {
            return false;
        }
        if (this.width == null) {
            return false;
        }
        if (this.height == null) {
            return false;
        }
        return true;
    }
    /**
     * @param hitsNo hits numbers
     */
    public void setHits(int hitsNo) {
        this.hits = hitsNo;
    }
    /**
     * @param c color to set
     */
    public void setColor(Color c) {
        this.color = c;
    }
    /**
     * @param c a color for stroke.
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }
    /**
     * @param myImg setting an image.
     */
    public void setImage(Image myImg) {
        this.img = myImg;
    }
    /**
     * @param w the width.
     */
    public void setWidth(int w) {
        this.width = w;
    }
    /**
     * @param s is the symbol.
     */
    public void setSymbol(String s) {
        this.symbol = s;
    }
    /**
     * @param h the height.
     */
    public void setHeight(int h) {
        this.height = h;
    }
    /**
     * @param specifiedKey hits number.
     * @param fill the filling string.
     */
    public void setMapByHits(int specifiedKey, String fill) {
        this.fillK.put(specifiedKey, fill);
    }
    /**
     * @return hits number.
     */
    public int getHits() {
        return this.hits;
    }
    /**
     * @return the color.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * @return stroke stroke color is returned.
     */
    public Color getStroke() {
        return this.stroke;
    }
    /**
     * @return img the image.
     */
    public Image getImage() {
        return this.img;
    }
    /**
     * @return the width.
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * @return the symbol.
     */
    public String getSymbol() {
        return this.symbol;
    }
    /**
     * @return height.
     */
    public int getHeight() {
        return this.height;
    }
    /**
     * @return color map list.
     */
    public Map<Integer, Color> getMappedColors() {
        Map<Integer, Color> m = new HashMap<Integer, Color>();
        m.putAll(this.colorWhenHit);
        return m;
    }
}

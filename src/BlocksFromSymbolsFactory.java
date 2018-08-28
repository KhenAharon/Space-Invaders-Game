import java.util.HashMap;
import java.util.Map;
/**
 * class BlocksFromSymbolsFactory - responsible of keeping the map of the
 * blockCreators and the spacerWidths and make some operations on them.
 *
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;
    /**
     * ctor.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new HashMap<String, Integer>();
        this.blockCreators = new HashMap<String, BlockCreator>();
    }
    /**
     * returns true if 's' is a valid space symbol.
     * @param s check if s exists in map.
     * @return whether a valid symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }
    /**
     * returns true if 's' is a valid block symbol.
     * @param s s check if s exists in map.
     * @return whether a valid symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }
    /**
     * returns a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s does s exist
     * @param xpos x block coordinate.
     * @param ypos y block coordinate.
     * @return a defined block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }
    /**
     * @param s does s exist
     * @return width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
    /**
     * @param symbol symbol to put.
     * @param b block creator to put.
     */
    public void setMapBlocks(String symbol, BlockCreatorConcrete b) {
        this.blockCreators.put(symbol, b);
    }
    /**
     * @param symbol symbol to put.
     * @param width to put.
     */
    public void setSpaceWidth(String symbol, int width) {
        this.spacerWidths.put(symbol, width);
    }
}
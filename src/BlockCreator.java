/**
 * needs to implement a block making.
 */
public interface BlockCreator {
    /**
     * make a block by x and y coordinates.
     * @param x x location
     * @param y y location
     * @return a new block
     */
    Block create(int x, int y);
}
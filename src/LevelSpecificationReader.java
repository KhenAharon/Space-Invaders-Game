import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
/**
 * class LevelSpecificationReader - responsible of reading a file of level
 * information, and makes some operations on it.
 *
 */
public class LevelSpecificationReader {

    private String name = null;
    private BlocksFromSymbolsFactory blockDefinition = null;
    private Integer blocksNum = null;
    private String velocities = null;
    private Integer rowHeight = null;
    private Integer paddleSpeed = null;
    private Integer paddleWidth = null;
    private String backgroundColor = null;
    private Integer x = null;
    private Integer y = null;
    private List<Block> blocksList;
    /**
     * @param reader is the one to read
     * @return listOfLevels list of level information
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> listOfLevels = new ArrayList<LevelInformation>();
        int row = 0;
        boolean isReading = false;
        BufferedReader temp = null;
        try {
            temp = new BufferedReader(reader);
            String pass;
            while ((pass = temp.readLine()) != null) {
                pass = pass.trim();
                // not an empty line or a comment
                if ((!pass.startsWith("#")) && (!"".equals(pass))) {
                    if (!isReading) {
                        if (pass.equals("END_LEVEL")) {
                            // finished to read info about the level
                            Level theLevel = new Level(this.paddleSpeed,
                                    this.paddleWidth, this.blocksNum,
                                    this.name, this.velocities,
                                    this.backgroundColor, this.blocksList);
                            // if we have all the components for the level
                            if (!theLevel.initLevel()) {
                                return null;
                            }
                            // add the level to list
                            listOfLevels.add(theLevel);

                        } else if (pass.equals("START_LEVEL")) {
                            this.backgroundColor = null;
                            this.name = null;
                            this.paddleSpeed = null;
                            this.paddleWidth = null;
                            this.x = null;
                            this.y = null;
                            this.blocksNum = null;
                            this.velocities = null;
                            this.rowHeight = null;
                            this.blocksList = new ArrayList<Block>();

                        } else if (pass.equals("START_BLOCKS")) {
                            isReading = true;
                            row = 0;

                        // insert values to objects
                        } else {
                            String[] readLine = pass.split(":");
                            String leftSide = readLine[0], rightSide = readLine[1];
                            if (leftSide.equals("level_name")) {
                                this.name = rightSide;
                            } else if (leftSide.equals("paddle_speed")) {
                                this.paddleSpeed = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("paddle_width")) {
                                this.paddleWidth = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("blocks_start_x")) {
                                this.x = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("blocks_start_y")) {
                                this.y = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("row_height")) {
                                this.rowHeight = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("num_blocks")) {
                                this.blocksNum = Integer.valueOf(rightSide);
                            } else if (leftSide.equals("ball_velocities")) {
                                this.velocities = rightSide;
                            } else if (leftSide.equals("background")) {
                                this.backgroundColor = rightSide;
                                // makes the blocks
                            } else if (leftSide.equals("block_definitions")) {
                                this.blockDefinition = new BlocksFromSymbolsFactory();
                                Reader readerBlock = null;
                                InputStream isBlock = null;
                                try {
                                    isBlock = ClassLoader.getSystemClassLoader()
                                            .getResourceAsStream(rightSide);
                                    readerBlock = new InputStreamReader(isBlock);
                                } catch (Exception e) {
                                    System.err.println("reading error");
                                    return null;
                                }
                                this.blockDefinition = BlocksDefinitionReader.
                                        fromReader(readerBlock,
                                                blockDefinition);
                            } else if (leftSide.equals("START_BLOCKS")) {
                                isReading = true;
                                row = 0;
                            }
                        }
                    } else if ("END_BLOCKS".equals(pass)) {
                        isReading = false;
                    } else {
                        int currentX = this.x.intValue();
                        for (int i = 0; i < pass.length(); i++) {
                            char symbol = pass.charAt(i);
                            int currentY = row
                                    * this.rowHeight.intValue()
                                    + this.y.intValue();
                            if (this.blockDefinition.isSpaceSymbol(
                                    String.valueOf(symbol))) {
                                currentX += this.blockDefinition.getSpaceWidth(
                                        String.valueOf(symbol));
                            } else {
                                Block b = this.blockDefinition.getBlock(String.valueOf(symbol), currentX, currentY);
                                if (b == null) {
                                    return null;
                                }
                                currentX = (int) (currentX + b.getCollisionRectangle().getWidth());
                                this.blocksList.add(b);
                            }
                        }
                        row++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("reading error.");
            return null;
        } finally {
            if (temp != null) {
                try {
                    temp.close();
                } catch (IOException e) {
                    System.out.println("closing file error.");
                    return null;
                }
            }
        }
        return listOfLevels;
    }
    /**
     * @return listOfBlocks a list of blocks.
     */
    public List<Block> getBlockList() {
        return this.blocksList;
    }
}
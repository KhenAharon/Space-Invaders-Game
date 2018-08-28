import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * The block definitions reader.
 */
public class BlocksDefinitionReader {
    /**
     * @param reader an io reader
     * @param bs our symbol factory.
     * @return initialized block factory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader, BlocksFromSymbolsFactory bs) {
        BlockCreatorConcrete mainBlockCreator = new BlockCreatorConcrete();
        BufferedReader is = null;
        try {
            String line;
            boolean bdef, sdef, def, comment;
            is = new BufferedReader(reader);
            while ((line = is.readLine()) != null) {
                line = line.trim();
                comment = line.startsWith("#") || "".equals(line);
                if (!comment) {
                    String[] splitted = line.split(" ");
                    def = splitted[0].equals("default");
                    bdef = splitted[0].equals("bdef");
                    sdef = splitted[0].equals("sdef");
                    if (def) {
                        BlocksDefinitionReader.createBlock(splitted, mainBlockCreator);
                    } else if (bdef) {
                        BlockCreatorConcrete blockCreator = new BlockCreatorConcrete(mainBlockCreator);
                        BlocksDefinitionReader.createBlock(splitted, blockCreator);
                        bs.setMapBlocks(blockCreator.getSymbol(), blockCreator);
                    } else if (sdef) {
                        String [] symbol = splitted[1].split(":");
                        String [] width = splitted[2].split(":");
                        bs.setSpaceWidth(symbol[1],
                        Integer.valueOf(width[1]));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading.");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("Error while closing file.");
                }
            }
        }
        return bs;
    }
    /**
     * @param s string to analyze and due to it taking an action to set the block creator.
     * @param b block creator implemented by the string.
     */
    public static void createBlock(String[] s, BlockCreatorConcrete b) {
        String[] splitted = null;
        for (int i = 1; i < s.length; i++) {
            splitted = s[i].split(":");
            if (splitted[0].equals("width")) {
                b.setWidth(Integer.valueOf(splitted[1]));
            } else if (splitted[0].equals("height")) {
                b.setHeight(Integer.valueOf(splitted[1]));
            } else if (splitted[0].equals("hit_points")) {
                b.setHits(Integer.valueOf(splitted[1]));
            } else if (splitted[0].equals("stroke") || splitted[0].equals("fill")) {
                String[] type = splitted[1].split("\\(");
                ColorsParser parse = new ColorsParser();
                if (type[0].equals("color")) {
                    Color color = null;
                    color = parse.colorFromString(type);
                    if (splitted[0].equals("stroke")) {
                        b.setStroke(color);
                    } else {
                        b.setColor(color);
                    }
                } else if (type[0].equals("image")) {
                    Image img = parse.imageFromString(type);
                    b.setImage(img);
                }
            } else if (splitted[0].equals("symbol")) {
                b.setSymbol(splitted[1]);
            } else if (b.getHits() > 1) {
                String [] hitType = splitted[0].split("-");
                if (hitType[0].equals("fill")) {
                    b.setMapByHits(Integer.valueOf(hitType[1]), splitted[1]);
                }
            }
        }
    }
}
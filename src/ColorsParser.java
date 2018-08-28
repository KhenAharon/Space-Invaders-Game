import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Analyze color details in the files.
 */
public class ColorsParser {
    private Color c;
    private Image img;
    /**
     * ctor nullify.
     */
    public ColorsParser() {
        this.c = null;
        this.img = null;
    }
    /**
     * @param s string to analyze.
     * @return img analyzed image by the string.
     */
    public Image imageFromString(String[] s) {
        String[] braSplit = s[1].split("\\)");
        try {
            this.img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(braSplit[0]));
        } catch (IOException e) {
            this.img = null;
        }
        return this.img;
    }
    /**
     * @param s string to analyze
     * @return c analyzed color from the string.
     */
    public java.awt.Color colorFromString(String[] s) {
        if (s[1].equals("RGB")) {
            String[] remBracket = s[2].split("\\)");
            String[] no = remBracket[0].split(",");
            this.c = new Color(Integer.valueOf(no[0]), Integer.valueOf(no[1]), Integer.valueOf(no[2]));
        } else {
            String[] colour = s[1].split("\\)");
            try {
                this.c = (Color) Class.forName("java.awt.Color").getField(colour[0]).get(null);
            } catch (Exception e) {
                this.c = null;
            }
        }
        return this.c;
    }
}
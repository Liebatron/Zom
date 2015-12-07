//@author alieb
package DotPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageReader {
    public BufferedImage image = null;
    public ImageReader() { // Public Image. Goddamnit, I'm hilarious.
    }
    public static BufferedImage getImage(String file) {
      BufferedImage img = null;
        try {
            String path =  System.getProperty("user.dir")+"/img/"+file+".png";
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Failed to read image from: "+file);
        }
      return img;
    }
}
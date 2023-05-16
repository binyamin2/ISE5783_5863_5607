package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testWriteToImage() {

        ImageWriter iw = new ImageWriter("basic image",800,500);
        Color green = new Color(0,255,0);
        Color red = new Color(255,0,0);

        for (int x = 0 ; x < iw.getNx(); x++ ){
            for (int y = 0 ; y < iw.getNy(); y++ ){

                if (x % 50 == 0 || y % 50 ==0)
                    iw.writePixel(x,y,red);
                else
                    iw.writePixel(x,y,green);

            }
        }

        iw.writeToImage();

    }


}
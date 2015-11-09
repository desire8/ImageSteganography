import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by jyoti on 9/11/15.
 */
public class Main {
    public static BufferedImage embedText(BufferedImage image, String text){
        int bitMask = 0x00000001;
        int bit, x=0, y=0;

        for(int i = 0; i < text.length(); i++) {
            bit = (int) text.charAt(i);		// get the ASCII number of a character
            System.out.println("bit.." + bit);
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;	// get 1 digit from the character
                if(flag == 1) {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                    }
                }
                else {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                    }
                }
                bit = bit >> 1;				// get the next digit from the character
                System.out.println("bit = next" + bit);
            }
        }

        try {
            File outputfile = new File("textEmbedded.png");
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;



    }


    // extract secret information/Text from a "cover image"
    public static void extractText(BufferedImage image, int length) {
        System.out.print("Extracting: ");
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        char[] c = new char[length] ;	// define a character array to store the secret information
        for(int i = 0; i < length; i++) {
            int bit = 0;

            // 8 digits form a character
            for(int j = 0; j < 8; j++) {
                if(x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                }

                // store the extracted digits into an integer as a ASCII number
                if(flag == 1) {
                    bit = bit >> 1;
                    bit = bit | 0x80;
                }
                else {
                    bit = bit >> 1;
                }
            }
            c[i] = (char) bit;	// represent the ASCII number by characters
            System.out.print(c[i]);
        }
    }

    public static void main(String[] args) {
        BufferedImage originalText = null;
        BufferedImage coverText = null;
        BufferedImage originalImage = null;
        try {
           //originalText = ImageIO.read(new File("cover.png"));
            coverText = ImageIO.read(new File("/home/jyoti/stg/src/small_pink_balloon.png"));
            //originalImage = ImageIO.read(new File("cover2.png"));
            String input = null;
            System.out.println("input the text....");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            System.out.println("input = " + input);
            coverText = embedText(coverText,input);
            extractText(ImageIO.read(new File("textEmbedded.png")), input.length());
            String  st = "abndgh";
            int bitMask = 0x00000001;
            int bit;


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

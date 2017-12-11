import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileToArt {
	static String FILE_NAME = "default";
	final static String OUTPUT_FILE_NAME = "output.png";
	  
	public static void main(String... args) throws IOException{
		
		System.out.print("please enter the name of the input, including extension: ");
		
		Scanner input = new Scanner(System.in);
		
		FILE_NAME = input.nextLine();
		
		input.close();
		
        FileToArt binary = new FileToArt();
        byte[] bytes = binary.readFile(FILE_NAME);
        
//        System.out.println(bytes.length + " " + Math.sqrt(bytes.length));
        
        int size = (int)((Math.sqrt(bytes.length)) + 1);
		
		BufferedImage img = new BufferedImage((size/3 + 1), size, BufferedImage.TYPE_INT_ARGB);
	    
//	    System.out.println((int)(size/3 + 1) * size);
	    
	    int a = 0;
	    int r = 0;
	    int g = 0;
	    int b = 0;
	    
	    int temp;
	    
	    int count = 0;
	    
	    boolean endFound = false;
	    
	    for (int i = 0; i < img.getWidth(); i++) {
	    		for (int j = 0; j < img.getHeight(); j++) {
		    		
	    			a = 255;
	    		    r = 0;
	    		    g = 0;
	    		    b = 0;
	    		    
	    		    if (count < bytes.length) {
	    		    		a = 128;
	    		    		g = (int)(bytes[count] & 0xff);
		    		    	count++;
		    		    	if (count < bytes.length) {
		    		    		a = 192;
		    		    		b = (int)(bytes[count] & 0xff);
		    		    		count++;
		    		    	}
		    		    	if (count < bytes.length) {
		    		    		a = 255;
		    		    		r = (int)(bytes[count] & 0xff);
		    		    		count++;
		    		    	}
		    		    		
	    		    } else if (!endFound) {
//	    		    		System.out.println(count);
	    		    		endFound = true;
	    		    } else {
		    			b = 8;
		    			a = 2;
		    			g = 8;
	    		    }
	    		    
			    temp = (a<<24) | (r<<16) | (g<<8) | b;
	    		    
//	    		    		System.out.println(a + " " + r + " " + g + " " + b);
			    
			    img.setRGB(i, j, temp);
	    		}
	    }
	    
	    try{
	        ImageIO.write(img, "png", new File(OUTPUT_FILE_NAME));
	      }catch(IOException e){
	        System.out.println(e);
	      }
	    
	    System.out.println("Done! " + bytes.length + " " + img);
	}
	  
	  byte[] readFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllBytes(path);
	  }
}

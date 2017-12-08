import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileToArt {
	public static void main(String... args) throws IOException{
		
        FileToArt binary = new FileToArt();
        byte[] bytes = binary.readFile(FILE_NAME);
        
        System.out.println(bytes.length/3 + " " + Math.sqrt(bytes.length));
        
        int size = (int)((Math.sqrt(bytes.length)) + 1);
		
		BufferedImage img = new BufferedImage((int)(size/2 + 1), size, BufferedImage.TYPE_INT_ARGB);
	    File f = null;
	    
	    System.out.println((int)(size/2 + 1) * size);
	    
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
	    		    if (count < bytes.length) {
	    		    		b = 255;
	    		    		r = (int)bytes[count];
	    		    		count++;
	    		    		if (count < bytes.length) {
	    		    			g = (int)bytes[count];
		    		    		count++;
	    		    		} else {
	    		    			b = 128;
	    		    			g = 0;
		    		    		}
	    		    } else if (!endFound) {
	    		    		System.out.println(count);
	    		    		endFound = true;
	    		    } else {
		    			b = 0;
		    			r = 255;
		    			g = 255;
	    		    }
	    		    
			    temp = (a<<24) | (r<<16) | (g<<8) | b;
	    		    
	    		    		System.out.println(a + " " + r + " " + g + " " + b);
			    
			    img.setRGB(i, j, temp);
	    		}
	    }
	    
	    try{
	        ImageIO.write(img, "gif", new File(OUTPUT_FILE_NAME));
	      }catch(IOException e){
	        System.out.println(e);
	      }
	    
	    System.out.println("Done! " + bytes.length + " " + img);
	}
	
	final static String FILE_NAME = "MP7.zip";
	  final static String OUTPUT_FILE_NAME = "sample.gif";
	  
	  byte[] readFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllBytes(path);
	  }
}

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
        byte[] bytes = binary.readFile("input.jpg");
        
        for (int i = 0; i < bytes.length; i++) {
        		try {
        			System.out.println(bytes[i]);
        		} catch(NullPointerException e) {
        			System.out.println("!!!");
        		}
        }
        
        System.out.println(bytes.length);
		
		BufferedImage img = null;
	    File f = null;
	    
	    try{
	      f = new File(FILE_NAME);
	      img = ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	    }
	    
	    System.out.println(img.getHeight() * img.getWidth());
	    System.out.println((img.getHeight() * img.getWidth())/(bytes.length - 1));
	    
	    int a = 0;
	    int r = 0;
	    int g = 0;
	    int b = 0;
	    
	    int temp;
	    
	    int count = 0;
	    int posCounter = 0;
	    
	    for (int i = 0; i < img.getWidth(); i++) {
	    		for (int j = 0; j < img.getHeight(); j++) {
	    			temp = img.getRGB(i, j);

	    		    	a = (temp>>24) & 0xff;
	    		    r = (temp>>16) & 0xff;
	    		    g = (temp>>8) & 0xff;
	    		    b = temp & 0xff;
	    		    
	    		    if (count < bytes.length && posCounter%((img.getHeight() * img.getWidth())/(bytes.length - 1)) == 0) {
	    		    		r = (int)bytes[count];
	    		    		g = 0;
	    		    		b = 0;
	    		    		
	    		    		count++;
	    		    }
	    			posCounter++;
	    		    
			    temp = (a<<24) | (r<<16) | (g<<8) | b;
			    
			    img.setRGB(i, j, temp);
	    		}
	    }
	    
	    try{
	        f = new File(OUTPUT_FILE_NAME);
	        ImageIO.write(img, "jpg", f);
	      }catch(IOException e){
	        System.out.println(e);
	      }
	    
	    System.out.println("Done!");
	}
	
	final static String FILE_NAME = "input.jpg";
	  final static String OUTPUT_FILE_NAME = "output.jpg";
	  
	  byte[] readFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllBytes(path);
	  }
	  
	  void writeFile(byte[] aBytes, String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    Files.write(path, aBytes);
	  }
}

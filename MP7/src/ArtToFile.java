import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ArtToFile {
	public static void main(String... args) throws IOException{
		
		BufferedImage img = null;
	    File f = null;

	    //read image
	    try{
	      f = new File("sample.gif");
	      img = ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	    }

	    //get image width and height
	    int width = img.getWidth();
	    int height = img.getHeight();

	    /**
	     * Since, Sample.jpg is a single pixel image so, we will
	     * not be using the width and height variable in this
	     * project.
	     */

	    //get pixel value
	    int p = img.getRGB(0,0);

	    int a = (p>>24) & 0xff;
	    int r = (p>>16) & 0xff;
	    int g = (p>>8) & 0xff;
	    int b = p & 0xff;
	    
	    System.out.println(a + " " + r + " " + g + " " + b + " " + width + " " + height);
		
		
		byte[] bytes;
		
		int bytesEnd = 0;
		boolean endReached = false;
		
		int temp = 0;
		
		int[] pictureStorage = new int[img.getWidth() * img.getHeight()];
		
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				pictureStorage[temp] = img.getRGB(i, j);
				temp++;
			}
		}
		
		temp = 0;
		
    		for (int i = 0; i < pictureStorage.length && !endReached; i++) {
    				
    			System.out.println((pictureStorage[i] & 0xff) + " " + bytesEnd);
    			System.out.println((pictureStorage[i] & 0xff) + "\t" 
    					+ (pictureStorage[i] >> 16 & 0xff) + "\t" 
    					+ (pictureStorage[i] >> 8 & 0xff));
    				
    			if ((pictureStorage[i] == pictureStorage[i + 1] 
    					&& (pictureStorage[i] & 0xff) == 128)) {
    				System.out.println("end state entered" + pictureStorage[i + 1]);
    				
    				bytesEnd++;
    				
    				break;
    			} else if ((pictureStorage[i] & 0xff) == 0) {
    				break;
    			} else {
    				bytesEnd+=2;
    			}
    			
    			System.out.println("looping!");
		}
		
		bytes = new byte[bytesEnd];
		System.out.println(bytes.length);
	    
		/*
	    int count = 0;
	    
	    for (int i = 0; i < img.getWidth(); i++) {
	    		for (int j = 0; j < img.getHeight(); j++) {
	    			p = img.getRGB(i, j);
	    		    
			    a = p>>24 & 0xff;
	    			r = p>>16 & 0xff;
	    			g = p>>8 & 0xff;
	    			b = p & 0xff;;
	    		    
	    		    if (count < bytes.length) 
	    		    {
			    			bytes[count] = (byte)r;
		    		    		count++;
		    		    		
		    		    	if (count < bytes.length) 
		    		    	{
		    		    		bytes[count] = (byte)g;
			    			count++;
			    		}
		    		    	
	    		    } 
	    		    else 
	    		    {
	    		    		i = img.getWidth();
	    		    		break;
	    		    }
	    		}
	    }
		
	for (int i = 0; i < bytes.length; i++) {
		System.out.println(Integer.toBinaryString(bytes[i]));
	}
		
		/*
		
		ArtToFile binary = new ArtToFile();
	    
	    binary.writeFile(bytes, OUTPUT_FILE_NAME);
	    
	    System.out.println("Done!");
	    */
	}
	
	final static String FILE_NAME = "output.jpg";
	final static String OUTPUT_FILE_NAME = "mp7.zip";
	  
  	void writeFile(byte[] aBytes, String aFileName) throws IOException {
  		Path path = Paths.get(aFileName);
  		Files.write(path, aBytes);
  	}
}

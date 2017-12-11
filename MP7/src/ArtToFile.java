import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ArtToFile {
	final static String FILE_NAME = "output.png";
	static String OUTPUT_FILE_NAME = "default.gif";
	
	public static void main(String... args) throws IOException{
		
		System.out.print("please enter the name of the output, including extension: ");
		
		Scanner input = new Scanner(System.in);
		
		OUTPUT_FILE_NAME = input.nextLine();
		
		input.close();
		
		BufferedImage img = null;
	    File f = null;

	    try{
	      f = new File(FILE_NAME);
	      img = ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	    }

	    int p = img.getRGB(0,0);

	    int a = (p>>24) & 0xff;
	    int r = (p>>16) & 0xff;
	    int g = (p>>8) & 0xff;
	    int b = p & 0xff;
	    
//	    System.out.println(a + " " + r + " " + g + " " + b + " " + width + " " + height);
		
		
		byte[] bytes;
		
		int bytesEnd = 0;
		
		int temp = 0;
		
		int[] pictureStorage = new int[img.getWidth() * img.getHeight()];
		
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				pictureStorage[temp] = img.getRGB(i, j);
				temp++;
			}
		}
		
		temp = 0;
		
    		for (int i = 0; i < pictureStorage.length; i++) {
    			
    			if (((pictureStorage[i] >> 24) & 0xff) == 2) {
    				bytesEnd--;
    				if (((pictureStorage[i-1] >> 24) & 0xff) == 192){
    					bytesEnd--;
    				}
    				if (((pictureStorage[i-1] >> 24) & 0xff) == 255){
    					bytesEnd-=2;
    				}
    				break;
    			} else {
    				bytesEnd++;
    				if (((pictureStorage[i] >> 24) & 0xff) == 192) {
    					bytesEnd++;
    				}
    				if (((pictureStorage[i] >> 24) & 0xff) == 255) {
    					bytesEnd+=2;
    				}
    			}
    			
//    			System.out.println("looping!");
		}
		
		bytes = new byte[bytesEnd];
//		System.out.println(bytes.length);
	    
	    int count = 0;
	    
	    for (int i = 0; i < img.getWidth(); i++) {
	    		for (int j = 0; j < img.getHeight(); j++) {
	    			
	    			p = img.getRGB(i, j);
    				r = p>>16 & 0xff;
	    			g = p>>8 & 0xff;
	    			b = p & 0xff;;
	    		    
	    		    if (count < bytes.length) 
	    		    {
			    		bytes[count] = (byte)(g & 0xff);
		    		    	count++;	
		    		    	
		    		    	if (count < bytes.length) {
		    		    		bytes[count] = (byte)(b & 0xff);
			    		    	count++;	
		    		    	}
		    		    	
		    		    	if (count < bytes.length) {
		    		    		bytes[count] = (byte)(r & 0xff);
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
		
//	for (int i = 0; i < bytes.length; i++) {
//		System.out.println(bytes[i]);
//	}
		
		ArtToFile binary = new ArtToFile();
	    
	    binary.writeFile(bytes, OUTPUT_FILE_NAME);
	    
	    System.out.println("Done!");
	}
	  
  	void writeFile(byte[] aBytes, String aFileName) throws IOException {
  		Path path = Paths.get(aFileName);
  		Files.write(path, aBytes);
  	}
}

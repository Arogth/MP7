import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
	
public class FileToArt {

	final static String INPUT = "C:/Users/jpkon/Downloads/game_data.zip";
	final static String OUTPUT = "output.gif";
		  
	public static void main(String... args) throws IOException{
		Path pathToFile = Paths.get(INPUT);
		byte[] byteArray = Files.readAllBytes(pathToFile);
	        	        
	    int sideLength = (int)((Math.sqrt(byteArray.length)) + 0.5);
	    BufferedImage image = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);
		
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		    
		int valueARGB;
		int index = 0;
		    		    
		for (int i = 0; i < image.getWidth(); i++) {
		    for (int j = 0; j < image.getHeight(); j++) {
		    	alpha = (int)(0xFF & (byteArray[index] >> 24));
		    	red = (int)(0xFF & (byteArray[index] >> 16));
		    	green = (int)(0xFF & (byteArray[index] >> 8));
		    	blue = (int)(0xFF & (byteArray[index] >> 0));
		    	
				valueARGB = (alpha<<24) | (red<<16) | (green<<8) | blue;  
				image.setRGB(i, j, valueARGB);
				index++;
		    }
		}
		    
		try {
			ImageIO.write(image, "gif", new File(OUTPUT));
		} catch(IOException exception){
			System.out.println(exception);
		}
		
		Graphics g = image.getGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}
}

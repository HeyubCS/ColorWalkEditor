import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(1920,1080);
	private BufferedImage background;
	private BufferedImage foreground;
	private String backgroundFilePath;
	private String foregroundFilePath;
	private int imageScale = 1;
	private ArrayList<Touchable> sprites;
    private static MediaTracker mt;
    private int selectedObject = -1;
    private int objectCount = 0;
	public ImagePanel(MouseListener observer){
		sprites = new ArrayList<Touchable>();
		addMouseMotionListener(new MouseMotionHandler());
		mt = new MediaTracker(this);
		this.addMouseListener(observer);
	}
	public Dimension getPreferredSize()
	{
		return size;
	}
	public void setPreferredSize(int width, int height)
	{
		size =  new Dimension(width,height);
	}
	public void loadBackgroundImage(String filePath)
	{
		backgroundFilePath = filePath;
		try {
 			
			background = ImageIO.read(new File(filePath));
		}
		catch(IOException e)
		{
			System.out.println("Error");
		}
		
	}
	public void loadForegroundImage(String filePath)
	{
		foregroundFilePath = filePath;
		try {
 			
			foreground = ImageIO.read(new File(filePath));
		}
		catch(IOException e)
		{
			System.out.println("Error");
		}
		
	}
	
	public String getBackgroundFilePath(){
		return backgroundFilePath;
	}
	
	public String getForegroundFilePath(){
		return foregroundFilePath;
	}
	
	public void exportImages(){
		int w;
		int h;
		BufferedImage scaled;
		AffineTransform at;
		AffineTransformOp scaleOp;
		//Export background
		if (background != null) { //Export the background
			//Export at 100%
			//Export at full scale - This is necessary to indicate to corona sdk the scale of the image
			int endOfString = backgroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_100 = backgroundFilePath.substring(0, endOfString - 4) + "@100" + backgroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_100);
				ImageIO.write(background, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
			
			//Export at 67%
			w = (int) Math.round((background.getWidth() * 0.67)); // Should be 1920
			h = (int) Math.round(background.getHeight() * 0.67);// Should be 1080
			
			scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			at = new AffineTransform();

			at.scale(0.67, 0.67);
			scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(background, scaled);

			//Modify name it indicate scale
			endOfString = backgroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_67 = backgroundFilePath.substring(0, endOfString - 4) + "@67" + backgroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_67);
				ImageIO.write(scaled, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
			
			//Scale to 42% ~800x450
			w = (int) Math.round((background.getWidth() * 0.42)); // Should be 1920
			h = (int) Math.round(background.getHeight() * 0.42);// Should be 1080

			scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			at = new AffineTransform();
			//67% which is ~1280 (I think more like 1284 X 680?
			at.scale(0.42, 0.42);
			scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(background, scaled);
			
			//Modify name to indicate scale.
			endOfString = backgroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_42 = backgroundFilePath.substring(0, endOfString - 4) + "@42" + backgroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_42);
				ImageIO.write(scaled, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
		}
		
		//export foreground
		if (foreground != null) { 
			//Export at 100%
			//Export at full scale - This is necessary to indicate to corona sdk the scale of the image
			int endOfString = foregroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_100 = foregroundFilePath.substring(0, endOfString - 4) + "@100" + foregroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_100);
				ImageIO.write(foreground, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
			
			//Export at 67%
			w = (int) Math.round((foreground.getWidth() * 0.67)); // Should be 1920
			h = (int) Math.round(foreground.getHeight() * 0.67);// Should be 1080
			
			scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			at = new AffineTransform();

			at.scale(0.67, 0.67);
			scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(foreground, scaled);

			//Modify name it indicate scale
			endOfString = foregroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_67 = foregroundFilePath.substring(0, endOfString - 4) + "@67" + foregroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_67);
				ImageIO.write(scaled, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
			
			//Scale to 42% ~800x450
			w = (int) Math.round((foreground.getWidth() * 0.42)); // Should be 1920
			h = (int) Math.round(foreground.getHeight() * 0.42);// Should be 1080

			scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			at = new AffineTransform();
			//67% which is ~1280 (I think more like 1284 X 680?
			at.scale(0.42, 0.42);
			scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(foreground, scaled);
			
			//Modify name to indicate scale.
			endOfString = foregroundFilePath.length();
			//Add condition if format is .jpeg (since it is 5 char)
			String scale_42 = foregroundFilePath.substring(0, endOfString - 4) + "@42" + backgroundFilePath.substring(endOfString-4, endOfString);
			
			try {
				File outputFile = new File(scale_42);
				ImageIO.write(scaled, "png", outputFile);
			} catch(IOException e) {
				System.out.println("error exporting scaled images");
			}
		}
		
	}
	
	
	public void setScale(int scale)
	{
		float scaleDecimal = (float) scale;
		float tempW = (scaleDecimal/100);
		float tempH = (scaleDecimal/100);

		//scale background
		if(background != null) {
			if(scale > imageScale){
				try{
					background = ImageIO.read(new File(backgroundFilePath));
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			int w = 1920;
			int h = 1280;
			BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(tempW, tempH);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(background, after);
			background = after;
		}
		//Scale objects
		if(sprites != null) {
			for(int i = 0; i < objectCount; i++){
				sprites.get(i).loadImage(tempW);
			}
		}
		//Scale foreground
		if(foreground != null) {
			if(scale > imageScale){
				try{
					foreground = ImageIO.read(new File(foregroundFilePath));
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}

			int w = 1920;
			int h = 1280;
			BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(tempW, tempH);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(foreground, after);
			foreground = after;
		}
	}
	
	public void addSprite(Touchable newSprite){
		objectCount++;
	    mt.addImage(newSprite.getImage(), 1);
	    try {
	      mt.waitForAll();
	    } catch (Exception e) {
	      System.out.println("Exception while loading image.");
	    }
		sprites.add(newSprite);
	}
	
	public int checkSelected(int x, int y){
		int newSelection = -1;
		for(int i = 0; i < objectCount; i++){
    		if(x > sprites.get(i).getScreenX() && x < (sprites.get(i).getScreenX() + sprites.get(i).getWidth()) &&
    				y > sprites.get(i).getScreenY() && y < (sprites.get(i).getScreenY() + sprites.get(i).getHeight())){
    			    				selectedObject = i;
    				newSelection = i;
    		}
    	}

		return newSelection;

	}
	
	public void setSelectedObject(int selection){
		selectedObject = selection;
	}
	public Touchable getSelectedObject(){
		return sprites.get(selectedObject);
	}
	public int getSelectionIndex(){
		return selectedObject;
	}
	
	public void updateSprite(String property, Object value){
		switch (property) {
		case "id": //{"id",""}, //private String touchableId;
				int newId;
				if(value.getClass().equals("class java.lang.String")){
					newId = Integer.parseInt((String) value);
				}
				else{
					newId = (int) value;
				}
				sprites.get(selectedObject).ID = newId;
			break;
		case "sound": //{"sound",""}, //private String soundPath; //private String soundName;
			sprites.get(selectedObject).setSoundPath((String) value);
			break;
		case "image": //{"image",""}, //private String imageName; //private String imagePath;
			sprites.get(selectedObject).setImagePath((String) value);
			break;
		case "X": //{"X",""}, //private int screenX;
			int newX;
			if(value.getClass().getName().equals("java.lang.String") ){
				newX = Integer.parseInt((String) value);
			}
			else{
				newX = (int) value;
			}
			sprites.get(selectedObject).setScreenX(newX);
			break;
		case "Y": //{"Y",""}, //private int screenY;
			int newY;
			if(value.getClass().getName().equals("java.lang.String") ){
				newY = Integer.parseInt((String) value);
			}
			else{
				newY = (int) value;
			}
			sprites.get(selectedObject).setScreenY(newY);
			break;
		case "Scale X": //{"Scale X",""}, //private float scaleX;
			float newScaleX;
			if(value.getClass().getName().equals("java.lang.String") ){
				newScaleX = Float.parseFloat((String) value);
			}
			else{
				newScaleX = (float) value;
			}
			//Refresh the image drawing
			sprites.get(selectedObject).loadImage();
			break;
		case "Scale Y": //{"Scale Y",""}, //private float scaleY;
			float newScaleY;
			if(value.getClass().getName().equals("java.lang.String") ){
				newScaleY = Float.parseFloat((String) value);
			}
			else{
				newScaleY = (float) value;
			}
			//Refresh the image drawing
			sprites.get(selectedObject).loadImage();
			break;
		case "Rotation": //{"Rotation",""}, //private float rotation;
			float newRotation;
			if(value.getClass().getName().equals("java.lang.String") ){
				newRotation = Float.parseFloat((String) value);
			}
			else{
				newRotation = (float) value;
			}
			//Refresh the image drawing
			sprites.get(selectedObject).loadImage();
			break;
		case "isFinal": //{"isFinal",""} //private String isFinal;
			sprites.get(selectedObject).isFinal = (String) value;
			break;
		default: //unknown value
				break;
		}

	}
	
	    public void paintComponent(Graphics g){
	        super.paintComponent(g);
			 		
	        if(background != null){
	            g.drawImage(background, 0, 0, this);
	        }
	        

	        if(sprites != null){
	    		sprites.forEach((temp) -> {

	    		        Graphics2D g2d;
	    		        g2d = (Graphics2D)g.create();
		    		    g2d.drawImage(temp.getImage(), temp.getScreenX(), temp.getScreenY(), this);


	    		});
	        }
	        if(foreground != null){
	        	g.drawImage(foreground, 0, 0, this);
	        }

	    }	    
	 class MouseMotionHandler extends MouseMotionAdapter {
	        public void mouseDragged(MouseEvent e) {
	        	if(selectedObject > -1){
	        		sprites.get(selectedObject).setScreenX(e.getX());
	        		sprites.get(selectedObject).setScreenY(e.getY());
	        		repaint();
	        	}
	        }
	      }
	 
	 public ArrayList<Touchable> getObjectList(){
		 return sprites;
	 }
	 
	 private void setObjectList(ArrayList<Touchable> newList){
		 sprites = newList;
		 objectCount = 0;
		 selectedObject = -1;
		 int newCount = 0;
		 for(Touchable sprite : sprites){
			 newCount++;
		 }
		 objectCount = newCount;
	 }
	 
	 public void loadLevel(ArrayList<Touchable> newList, String background, String foreground, String nl){
		 setObjectList(newList);
		 if(background != null){
			 loadBackgroundImage(background);
		 }
		 if(foreground != null) {
			 loadForegroundImage(foreground);
		 }
		 
	 }
	public int getObjectCount() {
		// TODO Auto-generated method stub
		return objectCount;
	}
}

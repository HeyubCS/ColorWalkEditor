import java.util.ArrayList;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Touchable implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 int ID;
	 String dataString;
	 String imagePath;
	 String soundPath;
	 String touchableId;
	 String imageName;
	 String soundName;
	 int imageX;
	 int imageY;
	 int width;
	 int height;
	 float scaleX;
	 float scaleY;
	 int screenX;
	 int screenY;
	 float rotation;
	 String isFinal;
	private BufferedImage image;
	private float softScale = 1; // This scale is used only by the editor.	
	private int frameCount;
	private final int idleFrame = 0; //Idle frame is always 0.
	private String id;
	private String sound;
	
	private ArrayList<ImageFrame> frameList;
	Touchable(){
		
	}
	
	Touchable(String data){
		frameList = new ArrayList<ImageFrame>();		

		dataString = data;
		String[] dataList = data.split(",");
		setTouchableId(dataList[0]);
		setImageName(dataList[1]);
		setSoundName(dataList[2]);
		ImageFrame idleFrame = new ImageFrame();
		imageX = idleFrame.x = Integer.parseInt(dataList[3]);
		imageY = idleFrame.y = Integer.parseInt(dataList[4]);
		width = idleFrame.width = Integer.parseInt(dataList[5]);
		height = idleFrame.height = Integer.parseInt(dataList[6]);
		setScaleX(Float.parseFloat(dataList[7]));
		setScaleY(Float.parseFloat(dataList[8]));
		setScreenX(Integer.parseInt(dataList[9]));
		setScreenY(Integer.parseInt(dataList[10]));
		setRotation(Float.parseFloat(dataList[11]));
		setIsFinal(dataList[12]);
		//loadImage();
	}
	
	public BufferedImage getImage(){
		if(image == null){
			this.loadImage();
		}
		return image;
	}

	//Getters and setters here on out.
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String newPath){
		imagePath = newPath;
	}

	public String getSoundPath() {
		return soundPath;
	}

	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}

	public String getTouchableId() {
		return touchableId;
	}

	public void setTouchableId(String touchableId) {
		this.touchableId = touchableId;
	}

	public String getSoundName() {
		return soundName;
	}

	public void setSoundName(String soundName) {
		this.soundName = soundName;
		this.soundPath = "sound/" + soundName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		this.imagePath = "images/";
		this.imagePath = this.imagePath.concat(imageName);
		setImagePath(this.imagePath);

	}

	public void loadImage(){
		try{
		//System.out.println(this.imagePath);
			BufferedImage fullImage = ImageIO.read(new File(this.imagePath));
			fullImage = fullImage.getSubimage(imageX, imageY, width, height);
			BufferedImage scaled = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scaleX * softScale, scaleY * softScale);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(fullImage, scaled);
			image = scaled;//;.getSubimage(imageX, imageY, width, height);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadImage(float newSoftScale){
		softScale = newSoftScale;
		try{
		//System.out.println(this.imagePath);
			BufferedImage fullImage = ImageIO.read(new File(this.imagePath));
			fullImage = fullImage.getSubimage(imageX, imageY, width, height);
			BufferedImage scaled = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scaleX * softScale, scaleY * softScale);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaled = scaleOp.filter(fullImage, scaled);
			image = scaled;//;.getSubimage(imageX, imageY, width, height);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public int getImageX() {
		return imageX;
	}

	public void setImageX(int imageX) {
		this.imageX = imageX;
	}

	public int getImageY() {
		return imageY;
	}

	public void setImageY(int imageY) {
		this.imageY = imageY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float newScaleX) {
		this.scaleX = newScaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float newScaleY) {
		this.scaleY = newScaleY;
	}

	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public String getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(String isFinal) {
		this.isFinal = isFinal;
	}
	
	public Touchable getClone(int id){
		Touchable thisClone = null;
		try{
			thisClone = (Touchable) this.clone();
		}
		catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		if(thisClone != null){
			thisClone.ID = id;
		}
		return thisClone;
	}

	public String getDataString() {
		return dataString;
	}
	
    private void readObject(ObjectInputStream aInputStream) 
    {
    	try{
	    	try{
		    	ID          = (int)    aInputStream.readObject();
		        imagePath   = (String) aInputStream.readObject();
		        soundPath   = (String) aInputStream.readObject();
		        touchableId = (String) aInputStream.readObject();
		        imageName   = (String) aInputStream.readObject();
		        soundName   = (String) aInputStream.readObject();
		        imageX      = (int)    aInputStream.readObject();
		        imageY      = (int)    aInputStream.readObject();
		        width       = (int)    aInputStream.readObject();
		        height      = (int)    aInputStream.readObject();
		        scaleX      = (float)  aInputStream.readObject();
		        scaleY      = (float)  aInputStream.readObject();
		        screenY     = (int)    aInputStream.readObject();
		        screenX     = (int)    aInputStream.readObject();
		        rotation    = (float)  aInputStream.readObject();
		        isFinal     = (String) aInputStream.readObject();
		        softScale   = (float)  aInputStream.readObject();
	    	}
	    	catch(ClassNotFoundException cnfe){
	    		cnfe.printStackTrace();
	    	}
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
 
    private void writeObject(ObjectOutputStream aOutputStream) throws IOException
    {
    	aOutputStream.writeObject(ID);
        aOutputStream.writeObject(imagePath);
        aOutputStream.writeObject(soundPath);
        aOutputStream.writeObject(touchableId);
        aOutputStream.writeObject(imageName);
        aOutputStream.writeObject(soundName);
        aOutputStream.writeObject(imageX);
        aOutputStream.writeObject(imageY);
        aOutputStream.writeObject(width);
        aOutputStream.writeObject(height);
        aOutputStream.writeObject(scaleX);
        aOutputStream.writeObject(scaleY);
        aOutputStream.writeObject(screenY);
        aOutputStream.writeObject(screenX);
        aOutputStream.writeObject(rotation);
        aOutputStream.writeObject(isFinal);
        aOutputStream.writeObject(softScale);
    }
	//Begin sprite data code...	
	public Row[] getRows(/*int frameNumber*/){
		ArrayList<Row> rowList = new ArrayList<Row>();
		ImageFrame frameData = frameList.get(idleFrame/*frameNumber*/);
		Row r = new Row("ID", this.getId());
		rowList.add(r);
		r = new Row("sound", this.getSound());
		rowList.add(r);
		r = new Row("image", this.getImageName());
		rowList.add(r);
		r = new Row("x", Integer.toString(frameData.x));
		rowList.add(r);
		r = new Row("y", Integer.toString(frameData.y));
		rowList.add(r);
		Row[] rows = rowList.toArray(new Row[this.getFrameCount()]);
		return rows;
	}
	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public void setImage(String image) {
		this.imageName = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}
	
	public void newFrame(int frameNumber){
		ImageFrame frame = new ImageFrame();
		frame.frameNumber = frameNumber;
		frame.x = 0;
		frame.y = 0;
	}
	public void newImageFrame(int frameNumber, int x, int y, int scaleX, int scaleY, int rotation){
		ImageFrame frame = new ImageFrame();
		frame.frameNumber = frameNumber;
		frame.x = x;
		frame.y = y;
		frameList.add(frame);
		frameCount++;
	}
	public ImageFrame getFrame(int frameNumber){
		return frameList.get(frameNumber);
	}
}
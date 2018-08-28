import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Touchable> sprites;
	public String levelName;
	public String backgroundImagePath;
	public String foregroundImagePath;
	public String[] objectList;
	public String nextLevel;
	public int objectCount;
	
	Level(ArrayList<Touchable> spr, String bip, String fip, String[] ol, int oc, String nl){
		sprites = spr;
		backgroundImagePath = bip;
		foregroundImagePath = fip;
		objectList = ol;
		objectCount = oc;
		nextLevel = nl;
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream aInputStream) 
    {
    	try{
	    	try{
		    	sprites     		  = (ArrayList<Touchable>) aInputStream.readObject();
		        backgroundImagePath   = (String) aInputStream.readObject();
		        foregroundImagePath   = (String) aInputStream.readObject();
		        objectList 		      = (String[]) aInputStream.readObject();
		        nextLevel 			  = (String) aInputStream.readObject();
	    	}
	    	catch(ClassNotFoundException cnfe){
	    		cnfe.printStackTrace();
	    	}
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	//objectList = (String[]) alObjectList.toArray();
    }
 
    private void writeObject(ObjectOutputStream aOutputStream) throws IOException
    {
    	aOutputStream.writeObject(sprites);
        aOutputStream.writeObject(backgroundImagePath);
        aOutputStream.writeObject(foregroundImagePath);
        aOutputStream.writeObject(objectList);
        aOutputStream.writeObject(nextLevel);

    }

	public String getName() {
		
		return levelName;
	}

	public String[] getObjectList() {
		return objectList;
	}

	public ArrayList<Touchable> getSprites() {
		return sprites;
	}

}

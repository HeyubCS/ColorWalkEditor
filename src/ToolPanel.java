import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ToolPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField backgroundImage;
	private JTextField foregroundImage;
	private JTextField scaleField;
	private JTextField nextLevelField;
	private PropertyPanel properties;
	private LinkedList<Touchable> imageList;
	private int touchableCount = 0;
	private JList<Object> touchableList;
	private JList<String> objectList;
	private DefaultListModel<String> objectListModel = new DefaultListModel<String>();
	private int objectCount = 0;
	
	ToolPanel(MainFrame observer){
		imageList = new LinkedList<Touchable>();
		//Background panel
		JPanel backgroundPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel nll = new JLabel("Next Level");
		JPanel nlfp = new JPanel();
		nlfp.add(nll);
		nlfp.add(nextLevelField = new JTextField(16));
		this.add(nlfp);
		backgroundImage = new JTextField(12);
		JLabel bil = new JLabel("Background image");
		backgroundPanel.add(bil);
		backgroundPanel.add(backgroundImage);
		JButton load = new JButton("Load");
		backgroundPanel.add(load);
		this.add(backgroundPanel);
		load.addActionListener(observer);
		//forground panel
		JPanel foregroundPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel fil = new JLabel("Foreground image");
		foregroundImage = new JTextField(12);
		foregroundPanel.add(fil);
		foregroundPanel.add(foregroundImage);
		JButton loadForeground = new JButton("Load");
		loadForeground.setActionCommand("LoadForeground");
		foregroundPanel.add(loadForeground);
		this.add(foregroundPanel);
		loadForeground.addActionListener(observer);
		//Scale panel
		JPanel scalePanel = new JPanel();
		JButton scaleButton = new JButton("Scale");
		scaleButton.addActionListener(observer);
		scaleField = new JTextField("100", 4);
		scalePanel.add(scaleField);
		scalePanel.add(scaleButton);
		this.add(scalePanel);
		
		//Load touchable data
		touchableList = new JList<Object>();
		loadTouchables();
		//Create a JList of touchable object templates
	
		JPanel touchableListPanel = new JPanel();
		JPanel addAndDeletePanel = new JPanel();
		JButton addTouchable = new JButton("Add");
		addTouchable.setActionCommand("AddTouchable");
		addTouchable.addActionListener(observer);
		JButton deleteTouchable = new JButton("Delete");
		deleteTouchable.setActionCommand("DeleteTouchable");
		deleteTouchable.addActionListener(observer);
		JButton newTouchable = new JButton("New");
		newTouchable.addActionListener(observer);
		addAndDeletePanel.add(newTouchable);
		addAndDeletePanel.add(addTouchable);
		touchableListPanel.add(addAndDeletePanel);
		touchableListPanel.add(touchableList);
		this.add(touchableListPanel);
		
		//Create a jlist of instantiated touchable objects.
		JPanel selectableObjectPanel = new JPanel();
		objectListModel.addElement("none");
		objectList = new JList<String>(objectListModel);
		objectList.addListSelectionListener(observer);
		selectableObjectPanel.add(deleteTouchable);
		selectableObjectPanel.add(objectList);
		this.add(selectableObjectPanel);
		
		//Create the property panel
		properties = new PropertyPanel(observer);
		this.add(properties);
		
	}
	
	public String getFilePath()
	{
		return backgroundImage.getText();
	}
	public String getForegroundFilePath()
	{
		return foregroundImage.getText();
	}
	
	public int getScale(){
		String scale = scaleField.getText();
		return Integer.parseInt(scale);
	}
	
	public Touchable getSelectedTouchable(int newID){
		if(touchableList.getSelectedIndex() == -1){
			return null;
		}
		return getTouchable(touchableList.getSelectedIndex(), newID);
	}
	
	public Touchable getTouchable(int index, int newID){
		if(imageList.get(index) != null){
			return imageList.get(index).getClone(newID);
		}
		return null;
	}
	
	public void addObject(String sid, int iid){
		objectCount++;
		objectListModel.addElement(iid + ":" + sid);
	}
	
	public int getSelectedIndex(){
		return objectList.getSelectedIndex() - 1;
	}
	
	
	public void setSelectedIndex(int newIndex){
		objectList.setSelectedIndex(newIndex + 1);
	}
	
	public String[] getObjectList(){
		ArrayList<String> list = new ArrayList<String>();
		int elementCount = -1;
		for(Enumeration<String> e =  ((DefaultListModel<String>) objectList.getModel()).elements(); e.hasMoreElements();){
			list.add(e.nextElement());
			elementCount++;
		}
		String[] newArray = null;
		if(elementCount > 0){
			newArray = (String[]) list.toArray(new String[0]);
		}
		return newArray;
	}
	
	public void setSelectedObject(int newSelection, Touchable currentTouchable){
		properties.loadNewProperties(
									currentTouchable.ID,//{"id",""}, //private String touchableId;
									currentTouchable.getSoundPath(),//{"sound",""}, //private String soundPath; //private String soundName;
									currentTouchable.getImagePath(),//{"image",""}, //private String imageName; //private String imagePath;
									currentTouchable.getScreenX(),//{"X",""}, //private int screenX;
									currentTouchable.getScreenY(),//{"Y",""}, //private int screenY;
									currentTouchable.getFinal()
								);
	}
	
	public void loadLevel(String[] newObjectList, String background, String foreground, String nl){
		//Remove any existing objects...
		for(; objectCount > 0; objectCount--){
			((DefaultListModel<String>) objectList.getModel()).removeElement(objectCount);
		}
		//add new objects
		int index = 0;
		for(String datum : newObjectList){
			if(index != 0){
				((DefaultListModel<String>) objectList.getModel()).addElement(datum);
			}
			else{
				index++;
			}
		}
		
		foregroundImage.setText(foreground);
		backgroundImage.setText(background);
		setNextLevel(nl);
	}
	
	public String getNextLevel(){
		return nextLevelField.getText();
	}
	public void setNextLevel(String loaded){
		nextLevelField.setText(loaded);
	}
	
	public void loadTouchables(){
		//Load sprites into jlist
		try {
			File file = new File("objects/touchables.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if(i >= touchableCount) {
					imageList.add(new Touchable(line));
					touchableCount++;
				}
				i++;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Create a JList of touchable object templates
		
		List<String> dataList = new ArrayList<String>();
		imageList.forEach((temp) -> {
			dataList.add(temp.getTouchableId());
		});
		touchableList.setListData(dataList.toArray());
	}
}

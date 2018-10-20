/*	TouchableCreatorjava
 * This class will provide a UI for creating touchable objects.
 * It will load a json formatted sprite atlas and then allow the user
 * to create touchable objects by selecting frames and animations.
 */
//TODO
// 1: Add export functionality
//		1.1: Export animation data
// 2: Add cancel/close window functionality
// 3: Add edit functionality
// 4: Input validation checking

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TouchableCreator implements ActionListener
{
	private ArrayList<SpriteFrame> frameList;
	private JFrame creatorFrame;
	private JTextArea frames;
	private JTextField idleFrameField;
	private JTextField touchableName;
	private JPanel touchablePanel;
	private JList<Object> frameJList;
	private ArrayList<JPanel> animationPanels;
	private String spriteSheetPath;

	public void TouchableCreator(ActionListener observer){
		//Instantiate animation panels. This is used for creating new animations.
		animationPanels = new ArrayList<JPanel>();
		//Instantiate frameList. This is used to store sprite frames.
		frameList = new ArrayList<SpriteFrame>();
		//Create JFrame
		creatorFrame = new JFrame();
		creatorFrame.setSize(new Dimension(800,400));
        creatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        creatorFrame.setVisible(true);
        
        //Create a list of frames
        frames = new JTextArea();
        frames.setEditable(false);
        //Load atlas data.
        parseAtlas("objects/ColorWalkTest.csv");
		//Create a JList of frames templates
		
		List<String> dataList = new ArrayList<String>();
		frameList.forEach((temp) -> {
		    dataList.add(temp.name);
		});
		frameJList = new JList<Object>(dataList.toArray());
        
        
        
        //Create a panel for modifying a touchable.
        touchableName = new JTextField(28);
        idleFrameField = new JTextField(10);
        idleFrameField.setText("0");//Default idle frame
        JButton newAnimation = new JButton("New Animation");
        newAnimation.addActionListener(this);
        
        touchablePanel = new JPanel();
        touchablePanel.setLayout(new BoxLayout(touchablePanel, BoxLayout.Y_AXIS));
        //Create a sub panel to hold touchable name.
        JPanel touchableNamePanel = new JPanel();
        JLabel touchableNameLabel = new JLabel("Touchable Name");
        touchableNamePanel.add(touchableNameLabel);
        touchableNamePanel.add(touchableName);
        touchableNamePanel.add(new JLabel("idle frame"));
        touchableNamePanel.add(idleFrameField);
        touchablePanel.add(touchableNamePanel);
        touchablePanel.add(newAnimation);
        //Create a label indicating frames and animation names
        JLabel animationFrameLabel = new JLabel("Animation Frames | ");
        JLabel animationNameLabel = new JLabel("Animation Names | ");
        JLabel timeLabel = new JLabel("Time in milliseconds | ");
        JLabel iterationsLabel = new JLabel("Animation Iterations");
        JPanel animLabelPanel = new JPanel();
        animLabelPanel.add(animationNameLabel);
        animLabelPanel.add(animationFrameLabel);
        animLabelPanel.add(timeLabel);
        animLabelPanel.add(iterationsLabel);
        touchablePanel.add(animLabelPanel);
        
        
        //create a panel for the frame list
        JScrollPane listScrollPane = new JScrollPane(frameJList);
        listScrollPane.setPreferredSize(frameJList.getPreferredSize());
        listScrollPane.revalidate();
        
        //Create a panel for creating the touchable
        JPanel createExitPanel = new JPanel();
        JButton exportButton = new JButton("Export");
        exportButton.setActionCommand("ExportTouchable");
        exportButton.addActionListener(this);
        exportButton.addActionListener(observer);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        createExitPanel.add(exportButton);
        createExitPanel.add(cancelButton);
        
        //add components to main frame
        creatorFrame.add(listScrollPane, BorderLayout.WEST);
        creatorFrame.add(touchablePanel, BorderLayout.EAST);
        creatorFrame.add(createExitPanel, BorderLayout.SOUTH);


	}
	
	public void parseAtlas(String path){
		 try {
			 	String line;
			 	String[] stringArray = new String[5];
	            BufferedReader input = new BufferedReader( new FileReader(path));
	            while( (line = input.readLine()) != null){
	            	SpriteFrame frame = new SpriteFrame();
	            	stringArray = line.split(",");
	            	//Data stored in rows such that: name, x, y, width, height
	           		frame.name    = stringArray[0];
	           		frame.data[0] = Integer.parseInt(stringArray[1]);
	           		frame.data[1] = Integer.parseInt(stringArray[2]);
	            	frame.data[2] = Integer.parseInt(stringArray[3]);
	            	frame.data[3] = Integer.parseInt(stringArray[4]);
	            	frames.append(frame.name + "\n");
	            	frameList.add(frame);
	            }
	            input.close();
	        } catch (FileNotFoundException e) {
	            System.err.println("Unable to find the file: " + path);
	        } catch (IOException e) {
	            System.err.println("Unable to read the file: " + path);
	        }
		 spriteSheetPath = "ColorWalkTest.png";
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Check for cancel button
		if(e.getActionCommand().equals("Cancel")) {
			//Exit the frame
		}
		System.out.println(e.getActionCommand());
		//Export touchable object; needs a lot of error checking
		if(e.getActionCommand().equals("ExportTouchable")){
		//	saveObject(touchableName.getText());
		}
		
		//Check for new animation button
		if(e.getActionCommand().equals("New Animation")){
			//Get selected indexes
			int[] selectedIndex = null;
			selectedIndex = frameJList.getSelectedIndices();
			System.out.println(selectedIndex);

			if( selectedIndex != null) {
				//Create new panel to hold animation data.
				JPanel animData = new JPanel();
				//Create text field to hold csv values of frames
				JTextField frames = new JTextField(10);
				//Crate text field to hold animation name
				JTextField name = new JTextField(10);
				//Create a text field for the time it takes to iterate over all frames
				JTextField time = new JTextField(10);
				time.setText("1000");
				JTextField iteration = new JTextField(10);
				iteration.setText("1");
				//Create data string for animation.
				//Set to first value in order to format neatly.
				String frameDataString = Integer.toString(selectedIndex[0]);
				for(int i = 1; i < selectedIndex.length; i++){
					//By already having the first value a comma can be prepended.
					frameDataString += "," + Integer.toString(selectedIndex[i]);
				}
				frames.setText(frameDataString);
				animData.add(name);
				animData.add(frames);
				animData.add(time);
				animData.add(iteration);
				//Put panel in list of panel for later referencing.
				animationPanels.add(animData);
				
				//Set the name text to a generic name.
				name.setText("Animation: " + Integer.toString(animationPanels.size()));
				
				touchablePanel.add(animData);
				creatorFrame.revalidate();
				
			}
			
		}
	}
	

	public void saveObject(){
		String objectName = touchableName.getText();
		//Write to editor touchable objects
		try {
			PrintWriter writer;
			writer = new PrintWriter(new BufferedWriter(new FileWriter("objects/touchables.txt", true)));

			//If writer successfully created construct the strings.
			//brownDog,ColorWalkSprite.png,,16,666,157,179
			int idleFrameNumber = Integer.parseInt(idleFrameField.getText());
			String idleFrameString = objectName + "," + spriteSheetPath + ",,"
					+ frameList.get(idleFrameNumber).data[0] + ","
					+ frameList.get(idleFrameNumber).data[1] + ","
					+ frameList.get(idleFrameNumber).data[2] + ","
					+ frameList.get(idleFrameNumber).data[3] ;
			writer.println(idleFrameString);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

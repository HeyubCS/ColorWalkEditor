import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, MouseListener, TableModelListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean initialized = false;
    private ImagePanel apane;
    private ToolPanel tools;
    private int objectCount = 0;
    
    public void initialize() {
        initializeGui();
        initializeEvents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initializeGui() {
        if (initialized)
            return;
        initialized = true;
        this.setSize(1360, 720);
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        //pane.setLayout(new GridBagLayout());
        apane = new ImagePanel(this);
        JScrollPane drawPane = new JScrollPane(apane);
        drawPane.getVerticalScrollBar().setUnitIncrement(16);
        drawPane.getHorizontalScrollBar().setUnitIncrement(16);
        drawPane.setPreferredSize(new Dimension(1000,200));
		drawPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		drawPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(drawPane, BorderLayout.WEST);
        tools = new ToolPanel(this);
        this.add(tools, BorderLayout.EAST);
        //Create a menu bar
        JMenuBar jmb = new JMenuBar();
        JMenu lAndS = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setActionCommand("LoadSave");
        loadItem.addActionListener(this);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        JMenuItem exportItem = new JMenuItem("Export to lua");
        exportItem.addActionListener(this);
        lAndS.add( loadItem );
        lAndS.add( saveItem );
        lAndS.add(exportItem);
        jmb.add(lAndS);
        this.setJMenuBar(jmb);
        //this.setJMenuBar(jmb);
        this.repaint();
    }
    
    private void initializeEvents() {
        // TODO: Add action listeners, etc
    }
    
    public class Actions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            command = command == null ? "" : command;
            // TODO: add if...if else... for action commands
        
        }
    }

    public void dispose() {
        // TODO: Save settings
        //super.dispose();
        System.exit(0);
    }
    
    public void setVisible(boolean b) {
        initialize();
        super.setVisible(b);
    }

    
	public static void main(String[] args) {
        new MainFrame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("Load")){
			apane.loadBackgroundImage(tools.getFilePath());
			this.repaint();
		}
		else if(e.getActionCommand().equals("Scale")){
			apane.setScale(tools.getScale());
			this.repaint();
		}
		else if(e.getActionCommand().equals("LoadForeground")){
			apane.loadForegroundImage(tools.getForegroundFilePath());
			this.repaint();
		}
		else if(e.getActionCommand().equals("AddTouchable")){
			if(tools.getSelectedTouchable(objectCount) != null){
				apane.addSprite(tools.getSelectedTouchable(objectCount));
				tools.addObject(tools.getSelectedTouchable(objectCount).getTouchableId(), objectCount);
				objectCount++;
				this.repaint();
			}
			else{
				//No selection
			}
		}
		else if(e.getActionCommand().equals("New")){ //New touchable template
			//Launch the sprite editor.
		}
		else if (e.getActionCommand().equals("LoadSave")) {
			FileInputStream fis;
			ObjectInputStream ois;
			String filePath = "save/";
			
		    JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new File("./save"));
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
			   filePath = filePath + chooser.getSelectedFile().getName();

		       System.out.println("You chose to open this file: " +
		            filePath);
		    }
		    else{
		    	System.out.println("Choice unapproved.");
		    }			
			try{
				fis = new FileInputStream(filePath);
				ois = new ObjectInputStream(fis);
				Level loaded = (Level) ois.readObject();
				tools.loadLevel(loaded.objectList, loaded.backgroundImagePath, loaded.foregroundImagePath, loaded.nextLevel);
				apane.loadLevel(loaded.sprites, loaded.backgroundImagePath, loaded.foregroundImagePath, loaded.nextLevel);
				fis.close();
				ois.close();

			}catch (Exception ex){
				ex.printStackTrace();

			}
			
			this.repaint();
		}
		else if (e.getActionCommand().equals("Save")) {
			FileOutputStream fout;
			ObjectOutputStream oos;
			String filePath = "save/";
			filePath = filePath + JOptionPane.showInputDialog("Enter filename: ");
			filePath = filePath + ".obj";
			try{
				
				fout = new FileOutputStream(filePath);
				oos = new ObjectOutputStream(fout);
				Level newLevel = new Level(apane.getObjectList(), apane.getBackgroundFilePath(),
						apane.getForegroundFilePath(), tools.getObjectList(), apane.getObjectCount(), tools.getNextLevel());
				oos.writeObject(newLevel);
				fout.close();
				oos.close();
			}catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
		else if (e.getActionCommand().equals("Export to lua")) {
			String filePath = "export/";
			String fileName = JOptionPane.showInputDialog("Enter filename: ");
			filePath = filePath + fileName;
			Level newLevel = new Level(apane.getObjectList(), apane.getBackgroundFilePath(),
						apane.getForegroundFilePath(), tools.getObjectList(), apane.getObjectCount(), tools.getNextLevel());
			newLevel.levelName = fileName;
			new ExportLevel(filePath, newLevel);
		}

		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		apane.setSelectedObject(tools.getSelectedIndex());
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int newSelection = apane.checkSelected(e.getX(), e.getY());
		if(newSelection != -1){
			tools.setSelectedObject(newSelection, apane.getSelectedObject());
			tools.setSelectedIndex(apane.getSelectionIndex());

		}
		else{
			//User did not click on an object.
		}
		this.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(apane.getSelectionIndex() != -1){
			tools.setSelectedObject(apane.getSelectionIndex(), apane.getSelectedObject());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		TableModel tm = (TableModel) e.getSource();
		int row = e.getFirstRow();
		String property = (String) tm.getValueAt(row, 0);
		Object value = tm.getValueAt(row, 1);
		//System.out.println(value);
		apane.updateSprite(property, value);
		this.repaint();		
	}
	
	//Save function
	public void save(){
		//Oh boy.
		
	}
}

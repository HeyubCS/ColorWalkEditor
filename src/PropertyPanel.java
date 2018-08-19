import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PropertyPanel extends JPanel {
	/**
	 * 
	 */
	private JTable propertyTable;
	PropertyPanel(MainFrame observer){
		Object[][] columnNames = {{"Property"}, {"Value"}};
		Object[][] data = {
							{"id",""}, //private String touchableId;
							{"sound",""}, //private String soundPath; //private String soundName;
							{"image",""}, //private String imageName; //private String imagePath;
							{"X",""}, //private int screenX;
							{"Y",""}, //private int screenY;
							{"isFinal",""} //private String isFinal;
						   };
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){

	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            // make read only fields except column 0,13,14
	            return column == 1;
	        }
	    };
		propertyTable = new JTable(tableModel);
		propertyTable.getModel().addTableModelListener(observer);
		this.add(propertyTable);

	}
	
	public JTable getTable(){
		return propertyTable;
	}
		
	public void loadNewProperties(int id, String sound, String image, int x, int y, String isFinal){
		int column = 1; //Column is always 1, since column 0 is labels
		DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();
		model.setValueAt(id, 0, column);
		model.setValueAt(sound, 1, column);
		model.setValueAt(image, 2, column);
		model.setValueAt(x, 3, column);
		model.setValueAt(y, 4, column);
		model.setValueAt(isFinal, 5, column);
	}
	
}

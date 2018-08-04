import javafx.beans.property.SimpleStringProperty;

public class Row {
	private final SimpleStringProperty name;
	private final SimpleStringProperty value;
	Row(String n, String v){
		this.name = new SimpleStringProperty(n);
		this.value = new SimpleStringProperty(v);
	}
	public String getName() {
		return name.get();
	}
	public String getValue() {
		return value.get();
	}
}

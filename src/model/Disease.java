package model;

public class Disease {
private String db;
private String id;
private String label;
private String cui;

public String getDb() {
	return db;
}
public void setDb(String db) {
	this.db = db;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
public String getCui() {
	return cui;
}
public void setCui(String cui) {
	this.cui = cui;
}
public Disease(String db, String id, String label, String cui){
	this.db=db;
	this.id=id;
	this.label=label;
	this.cui=cui;
	
}
}

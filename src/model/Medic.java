package model;

public class Medic {
private String atc;
private String cid;
private String label;
public Medic(String atc, String cid, String label){
	this.setAtc(atc);
	this.setCid(cid);
	this.setLabel(label);
}
public String getAtc() {
	return atc;
}
public void setAtc(String atc) {
	this.atc = atc;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
}

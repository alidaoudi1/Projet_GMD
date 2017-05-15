package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import sider.Meddra;
import stitch.ATC;
import stitch.Stitch;

public class Results2Controller {
	private String query;
	@FXML BorderPane MainPane ;
	@FXML StackPane PrevPane;
	@FXML StackPane CurrPane;
	@FXML ListView<String> list3;
	@FXML Text txt;

	public void setMainPane(BorderPane mainPane, String query, String mode) throws ClassNotFoundException, SQLException, IOException, ParseException {
		this.MainPane=mainPane;
		setQuery(query);
		txt.setText("Your query is : " + query);
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res3=new ArrayList<String>();
		if (mode.equals("0")){
			String s[]=query.split("\\|");
			for (String i:s){
				ArrayList<String>res8 = Meddra.Request("stitch_compound_id", "meddra_all_indications", "cui = '"+i+"' OR cui_of_meddra_term = '"+i+"'");
				for (String l:res8){
					if (!res.contains(l)){
						res.add(l);
					}
				}
			}
		}else{
			res=new ArrayList<String>();
			ArrayList<String> res1 = Meddra.Request("cui", "meddra_all_se", "stitch_compound_id1 = '"+query+"' AND meddra_concept_type<>'PT'");
			for (String h:res1){
				ArrayList<String> res2 = Meddra.Request("stitch_compound_id", "meddra_all_indications", "cui = '"+h+"' OR cui_of_meddra_term = '"+h+"'");
				for (String k:res2){
					if (!res.contains(k)){
						res.add(k);
					}
				}
			}
		}
		for (String hjl:res){
			String ju="";
			if(hjl.charAt(3)=='1'){
				ju = Stitch.Request(hjl, "m");
			}else{
				ju = Stitch.Request(hjl, "s");
			}
			if(!ju.equals("patate")){
				System.out.println(ju);
				String jk = ATC.Request(ju);
				if(!jk.equals("patate")){
					res3.add(hjl+"//"+ju+"//"+jk);
				}
			}
		}
		ObservableList<String> l = FXCollections.observableArrayList();
		l.addAll(res3);
		list3.setItems(l);

	}
	public void hit3() throws ClassNotFoundException, SQLException, IOException, ParseException{
		String s = list3.getSelectionModel().getSelectedItem();
		String s1[] = s.split("//");
		String query = s1[1];
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(Main.class.getResource("../views/ResultsView2.fxml"));
		StackPane ResultsView = (StackPane) loader2.load();
		ResultsView.setAlignment(Pos.CENTER);
		MainPane.setCenter(ResultsView);
		Results2Controller st = loader2.getController();
		st.setBack(this.CurrPane);
		String mode="1";
		st.setMainPane(MainPane, query, mode);
	}
	public void setCurr(StackPane s){
		this.CurrPane=s;
	}
	public void setBack(StackPane s){
		this.PrevPane=s;
	}
	private void setQuery(String query) {
		this.query=query;

	}

}

package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.lucene.queryparser.classic.ParseException;

import application.Main;
import hpo.HpoParse;
import hpo.Hpoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import omim.Omimcsv;
import omim.Omimtxt;
import orpha.Orpha;
import sider.Meddra;
import stitch.ATC;
import stitch.Stitch;

public class ResultsController {
	@FXML BorderPane MainPane ;
	@FXML StackPane CurrPane;
	@FXML ListView<String> list;
	@FXML ListView<String> list1;
	@FXML Text txt;
	String query;
	public void setCurr(StackPane s){
		this.CurrPane=s;
	}
	public void setQuery(String q){
		this.query=q;
	}
	public String getQuery(){
		return this.query;
	}

	public void setMainPane(BorderPane mainPane,String query) throws IOException, ClassNotFoundException, SQLException, ParseException {
		this.MainPane=mainPane;
		setQuery(query);
		txt.setText("Your query is : " + query);
		ArrayList<String> le = HpoParse.Request(query);
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		ArrayList<String> res3 = new ArrayList<String>();
		ArrayList<String> res4 = new ArrayList<String>();
		ArrayList<String> res5 = new ArrayList<String>();
		ArrayList<String> hpo =new ArrayList<String>();
		ArrayList<String> mdr =new ArrayList<String>();
		for(String hui:le){
			String[] jio = hui.split("//");
			hpo.add(jio[0]);
			if (jio.length>1){
				mdr.add(jio[1]);
		}
		}
		for (String s:mdr){
			res2=Meddra.Request("CUI", "meddra", "meddra_id = '"+s+"'");
			for (String gh:res2){
				res3.addAll(Meddra.Request("stitch_compound_id1", "meddra_all_se", "cui = '"+gh+"'"));
			}
		}
		String qsql=query.replace("*", "%");
		ArrayList<String> resu = Meddra.Request("stitch_compound_id1", "meddra_all_se", "side_effect_name LIKE '%"+qsql+"%' AND meddra_concept_type<>'PT'");
		for (String l:resu){
			if (!res3.contains(l)){
				res3.add(l);
			}
		}
		for (String hjl:res3){
			String ju="";
			if(hjl.charAt(3)=='1'){
				ju = Stitch.Request(hjl, "m");
			}else{
				ju = Stitch.Request(hjl, "s");
			}
			if(!ju.equals("patate")){
				String jk = ATC.Request(ju);
				if(!jk.equals("patate")){
					res5.add(hjl+"//"+ju+"//"+jk);
				}
			}
		}
		ArrayList<ArrayList<String>> clon = new ArrayList<ArrayList<String>>();
		for (String h:hpo){
			clon.add(Hpoan.search(h));
		}
		for (ArrayList<String> i:clon){
			for(String j:i){
				if (!j.isEmpty()){
					if(j.startsWith("OMIM")){
						String s1[]=j.split("//");
						String s2=Omimcsv.Request(s1[1]);
						if (!s2.equals("kowabunga")){
							String s3 = j+"//"+s2;
							res.add(s3);
						}
					}else{
						String s1[]=j.split("//");
						String s2=Orpha.Request(s1[1]);
						if (!s2.equals("kowabunga")){
							s2=s2.replace("'","''");
							ArrayList<String> b=Meddra.Request("cui","meddra","label='"+s2+"' AND concept_type<>'PT'");
							
							if(!b.isEmpty()){
								for (String klj:b){
									String s3 = j+"//"+s2+"//"+klj;
									res.add(s3);
								}
							}
						}
					}
				}
			}
		}
		res4=Omimtxt.Request(query);
		for (String jk:res4){
			if (!jk.isEmpty()){
				if(jk.startsWith("OMIM")){
					String s1[]=jk.split("//");
					String s2=Omimcsv.Request(s1[1]);
					if (!s2.equals("kowabunga")){
						String s3 = jk+"//"+s2;
						if (!res.contains(s3)){
						res.add(s3);
						}
					}
		}
			}}

		Collections.sort(res);
		ObservableList<String> l = FXCollections.observableArrayList();
		l.addAll(res);
		list.setItems(l);
		ObservableList<String> l1 = FXCollections.observableArrayList();
		l1.addAll(res5);
		list1.setItems(l1);
	}
	
	public void hitlist() throws IOException, ClassNotFoundException, SQLException, ParseException{
		String s = list.getSelectionModel().getSelectedItem();
		String s1[] = s.split("//");
		String query = s1[3];
			FXMLLoader loader2 = new FXMLLoader();
	        loader2.setLocation(Main.class.getResource("../views/ResultsView2.fxml"));
	        StackPane ResultsView = (StackPane) loader2.load();
	        ResultsView.setAlignment(Pos.CENTER);
	        MainPane.setCenter(ResultsView);
	        Results2Controller st = loader2.getController();
	        st.setBack(this.CurrPane);
	        String mode="0";
            st.setMainPane(MainPane, query, mode);
	}
	
	public void hitlist1() throws IOException, ClassNotFoundException, SQLException, ParseException{
		String s = list1.getSelectionModel().getSelectedItem();
		String s1[] = s.split("//");
		String query = s1[0];
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

}

package controller;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.lucene.queryparser.classic.ParseException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class SearchController {
	
	@FXML BorderPane MainPane ;
	@FXML Button Bouton;
	@FXML TextField Bar;
	
	public void setMainPane(BorderPane rootLayout){
		this.MainPane=rootLayout;
	}
	
	public void Hit() throws IOException, ClassNotFoundException, SQLException, ParseException{
		String query = Bar.getText();
		if (!query.isEmpty()){
			FXMLLoader loader2 = new FXMLLoader();
	        loader2.setLocation(Main.class.getResource("../views/ResultsView.fxml"));
	        StackPane ResultsView = (StackPane) loader2.load();
	        ResultsView.setAlignment(Pos.CENTER);
	        MainPane.setCenter(ResultsView);
	        ResultsController st = loader2.getController();
	        st.setCurr(ResultsView);
            st.setMainPane(MainPane, query);
		}else{
			Bar.setPromptText("ENGLISH, DO YOU SPEAK IT ?! ENTER YOUR SYMPTOMS");
		}
		
	}
}

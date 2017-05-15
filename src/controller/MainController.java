package controller;

import java.io.IOException;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class MainController {
	@FXML BorderPane MainPane ;
	
	public void setMainPane(BorderPane rootLayout){
		this.MainPane=rootLayout;
	}
	
	public void exit(){
		Platform.exit();
	}
	public void home() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/SearchView.fxml"));
		MainPane.setCenter((StackPane) loader.load());
		MainController c = loader.getController();
		c.setMainPane(MainPane);
	}
}

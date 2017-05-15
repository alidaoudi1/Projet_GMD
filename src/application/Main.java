package application;

import java.io.IOException;


import controller.MainController;
import controller.SearchController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private void initialize() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../views/MainView.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            MainController mt = loader.getController();
            mt.setMainPane(rootLayout);
            FXMLLoader loader2 = new FXMLLoader();
	        loader2.setLocation(Main.class.getResource("../views/SearchView.fxml"));
	        StackPane SearchView = (StackPane) loader2.load();
	        SearchView.setAlignment(Pos.CENTER);
	        rootLayout.setCenter(SearchView);
	        SearchController st = loader2.getController();
            st.setMainPane(rootLayout);
            //primaryStage.setFullScreen(true);
	}
            
	public void start(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Doctissimaux");
        initialize();
	}


	public static void main(String[] args) {
		launch(args);
	}

}

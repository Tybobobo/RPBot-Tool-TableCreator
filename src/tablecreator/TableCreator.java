/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablecreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ivanskodje
 */
public class TableCreator extends Application
{
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("TableCreator.fxml"));
		
		Scene scene = new Scene(root);
		stage.setTitle("RPBot Table Creator");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
}

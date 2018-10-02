package pack.petshop;

import java.io.IOException;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Menu extends Application
{
	static Stage stageLoader;
	@Override
	public void start(Stage stage) 
	{
		this.stageLoader = stage;
		//AnchorPane root;
		try 
		{
			
			Parent root = FXMLLoader.load(getClass().getResource("/interfaces/menu.fxml"));
	        stage.setTitle("Menu");
	        
			//Carrega o arquivo FXML com a defini��o da tela
			//root = (AnchorPane)FXMLLoader.load(getClass().getResource("/interfaces/Menu_Inicial.fxml"));
			
			//Define a sena principal (janela)
			Scene scene = new Scene(root, 900, 600);
			
			//Carrega o arquivo CSS
			//scene.getStylesheets().add(getClass().getResource("FXAppOne.css").toExternalForm());
			
			//Faz a m�gica...
			stage.setScene(scene);
			stage.show();
	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)  
	{	
		Application.launch(Menu.class, args);
	}
}

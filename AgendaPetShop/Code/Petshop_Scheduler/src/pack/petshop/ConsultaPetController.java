package pack.petshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import pack.DAO.PetDAO;
import pack.VO.Pet;

public class ConsultaPetController implements Initializable
{
	@FXML
	private TextField txtId;
	
	@FXML
	private Pane content;
	
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
	}
	
	@FXML
	protected void btnPesquisarHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnSelecionarHandler(ActionEvent event)
	{
		Pet pet = new Pet();
		pet.setNome("José");
		PetController.pet = pet;
		
		content.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/pet.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			PetController controller = loader.<PetController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Cadastro de Pets");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnLimparHandler(ActionEvent event)
	{
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
	}
}

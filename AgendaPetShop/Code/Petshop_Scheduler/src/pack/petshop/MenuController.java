package pack.petshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable
{
	@FXML
	private Label lblTitle;
	
	@FXML 
	private Pane pnlContent;
	
	@FXML
	public Button btnMenu;
	
	@FXML
	protected void btnMenuHandler(ActionEvent event)
	{
		loadContent("inicial.fxml");
	}
	
	@FXML
	protected void btnClientesHandler(ActionEvent event) 
	{
		loadContent("cliente.fxml");
	}
	
	@FXML
	protected void btnDonosHandler(ActionEvent event)
	{
		loadContent("dono.fxml");
	}
	
	@FXML
	protected void btnAgendaHandler(ActionEvent event)
	{
		loadContent("agenda.fxml");
	}
	
	@FXML
	protected void btnAgendamentoHandler(ActionEvent event)
	{
		loadContent("agendamento.fxml");
	}
	
	@FXML
	protected void btnPacotesHandler(ActionEvent event)
	{
		loadContent("pacote.fxml");
	}
	
	@FXML
	protected void btnPagamentosHandler(ActionEvent event)
	{
		loadContent("pagamento.fxml");
	}
	
	@FXML
	protected void btnPendenciasHandler(ActionEvent event)
	{
		loadContent("pendencia.fxml");
	}
	
	@FXML
	protected void btnConsultaHandler(ActionEvent event)
	{
		loadContent("consulta.fxml");
	}
	
	@FXML
	protected void btnSobreHandler(ActionEvent event) 
	{
		loadContent("sobre.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		btnMenu.fire();
	}

	private void loadContent(String file)
	{
		pnlContent.getChildren().clear();
		try 
		{
			Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/interfaces/"+file));
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}

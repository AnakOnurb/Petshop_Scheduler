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
		lblTitle.setText("Menu Inicial");
		loadContent("inicial.fxml");
	}
	
	@FXML
	protected void btnClientesHandler(ActionEvent event) 
	{
		lblTitle.setText("Cadastro de Cliente");
		loadContent("cliente.fxml");
	}
	
	@FXML
	protected void btnDonosHandler(ActionEvent event)
	{
		lblTitle.setText("Cadastro de Dono");
		loadContent("dono.fxml");
	}
	
	@FXML
	protected void btnAgendaHandler(ActionEvent event)
	{
		lblTitle.setText("Agenda");
		loadContent("agenda.fxml");
	}
	
	@FXML
	protected void btnAgendamentoHandler(ActionEvent event)
	{
		lblTitle.setText("Agendamentos");
		loadContent("agendamento.fxml");
	}
	
	@FXML
	protected void btnPacotesHandler(ActionEvent event)
	{
		lblTitle.setText("Cadastro de Pacotes");
		loadContent("pacote.fxml");
	}
	
	@FXML
	protected void btnPagamentosHandler(ActionEvent event)
	{
		lblTitle.setText("Pagamentos");
		loadContent("pagamento.fxml");
	}
	
	@FXML
	protected void btnPendenciasHandler(ActionEvent event)
	{
		lblTitle.setText("Pendências");
		loadContent("pendencia.fxml");
	}
	
	@FXML
	protected void btnConsultaHandler(ActionEvent event)
	{
		lblTitle.setText("Consultas");
		loadContent("consulta.fxml");
	}
	
	@FXML
	protected void btnFuncionariosHandler(ActionEvent event) 
	{
		lblTitle.setText("Funcionários");
		loadContent("funcionario.fxml");
	}
	
	@FXML
	protected void btnSobreHandler(ActionEvent event) 
	{
		lblTitle.setText("Sobre");
		loadContent("sobre.fxml");
		DBConn.getConnection();
	}

	@FXML
	protected void btnSairHandler(ActionEvent event) 
	{
		System.exit(0);
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

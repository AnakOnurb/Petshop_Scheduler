package pack.petshop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import pack.DAO.PetDAO;

public class MenuController implements Initializable
{	
	@FXML
	private Label lblTitle;
	
	@FXML 
	public Pane pnlContent;
	
	@FXML
	public Button btnMenu;
	
	@FXML
	protected void btnMenuHandler(ActionEvent event)
	{
		lblTitle.setText("Menu Inicial");
		loadContent("inicial.fxml");
	}
	
	@FXML
	protected void btnPetHandler(ActionEvent event) throws IOException 
	{
		lblTitle.setText("Cadastro de Pets");
		pnlContent.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/pet.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			PetController controller = loader.<PetController>getController();
			controller.InitData(pnlContent, lblTitle);
			
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnDonoHandler(ActionEvent event)
	{
		lblTitle.setText("Cadastro de Donos");
		pnlContent.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/dono.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			DonoController controller = loader.<DonoController>getController();
			controller.InitData(pnlContent, lblTitle);
			
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
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
		pnlContent.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/agendamento.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			AgendamentoController controller = loader.<AgendamentoController>getController();
			controller.InitData(pnlContent, lblTitle);
			
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnPacoteHandler(ActionEvent event)
	{
		lblTitle.setText("Cadastro de Pacotes");
		loadContent("pacote.fxml");
	}
	
	@FXML
	protected void btnPagamentoHandler(ActionEvent event)
	{
		lblTitle.setText("Pagamentos");
		loadContent("pagamento.fxml");
	}
	
	@FXML
	protected void btnPendenciaHandler(ActionEvent event)
	{
		lblTitle.setText("Pendências");
		loadContent("pendencia.fxml");
	}
	
	@FXML
	protected void btnConsultaHandler(ActionEvent event)
	{
		lblTitle.setText("Consultas");
		loadContent("menuconsulta.fxml");
	}
	
	@FXML
	protected void btnCadastroHandler(ActionEvent event)
	{
		lblTitle.setText("Cadastros");
		pnlContent.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/menucadastro.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			MenuCadastroController controller = loader.<MenuCadastroController>getController();
			controller.InitData(pnlContent, lblTitle);
			
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnFuncionarioHandler(ActionEvent event) 
	{
		lblTitle.setText("Funcionários");
		pnlContent.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/funcionario.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			FuncionarioController controller = loader.<FuncionarioController>getController();
			controller.InitData(pnlContent, lblTitle);
			
			pnlContent.setPrefSize(780, 547);
			pnlContent.getChildren().add(newLoadedPane);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnSobreHandler(ActionEvent event) 
	{
		lblTitle.setText("Sobre");
		loadContent("sobre.fxml");
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
		Utils.ReadRacas();
		Utils.ReadDono();
		Utils.ReadEspecie();
		Utils.ReadPelagem();
		Utils.ReadPorte();
		Utils.ReadPet();
		Utils.ReadFuncionario();
		Utils.ReadServico();
	}

	public void loadContent(String file)
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

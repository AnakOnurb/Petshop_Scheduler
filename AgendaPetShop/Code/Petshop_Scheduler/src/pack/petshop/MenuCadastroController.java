package pack.petshop;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import pack.DAO.PetDAO;
import pack.DAO.ServicoDAO;
import pack.DAO.TipoPacoteDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;
import pack.VO.Servico;
import pack.VO.TipoPacote;

public class MenuCadastroController implements Initializable
{	
	public static TipoPacote tipoPacote = new TipoPacote();
	public static Servico tipoServico = new Servico();
	private static int saveOption = 1;
	public static TipoPacote receivedpacote = new TipoPacote();
	public static Servico receivedservico = new Servico();
	
	@FXML
	private TextField txtIdPacote;
	@FXML
	private TextField txtDescPacote;
	@FXML
	private Button btnPesquisarPacote;
	@FXML
	private Button btnNovoPacote;
	@FXML
	private Button btnSalvarPacote;
	@FXML
	private Button btnAlterarPacote;
	@FXML
	private Button btnExcluirPacote;
	
	@FXML
	private TextField txtIdServico;
	@FXML
	private TextField txtDescServico;
	@FXML
	private TextField txtPrecoServico;
	@FXML
	private TextField txtDuracaoServico;
	@FXML
	private Button btnPesquisarServico;
	@FXML
	private Button btnNovoServico;
	@FXML
	private Button btnSalvarServico;
	@FXML
	private Button btnAlterarServico;
	@FXML
	private Button btnExcluirServico;
	
	
	@FXML
	private Pane content;
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) 
	{
		content = pnlContent;	
		title = lblTitle;
	}
	
	@FXML
	protected void btnTipoPacoteHandler(ActionEvent event)
	{

	}
	
	@FXML
	protected void btnTipoServicoHandler(ActionEvent event)
	{

	}
	
	@FXML
	protected void btnNovoPacoteHandler(ActionEvent event)
	{
		clearFields();
		enableFieldsPacote();
		btnSalvarPacote.setDisable(false);
		btnNovoPacote.setDisable(true);
		btnAlterarPacote.setDisable(true);
		btnExcluirPacote.setDisable(true);
		btnPesquisarPacote.setDisable(true);
		btnSalvarServico.setDisable(true);
		btnNovoServico.setDisable(true);
		btnAlterarServico.setDisable(true);
		btnExcluirServico.setDisable(true);
		btnPesquisarServico.setDisable(true);
		saveOption = 1;
	}
	
	@FXML
	protected void btnNovoServicoHandler(ActionEvent event)
	{
		clearFields();
		enableFieldsServico();
		btnSalvarPacote.setDisable(true);
		btnNovoPacote.setDisable(true);
		btnAlterarPacote.setDisable(true);
		btnExcluirPacote.setDisable(true);
		btnPesquisarPacote.setDisable(true);
		btnSalvarServico.setDisable(false);
		btnNovoServico.setDisable(true);
		btnAlterarServico.setDisable(true);
		btnExcluirServico.setDisable(true);
		btnPesquisarServico.setDisable(true);
		saveOption = 1;
	}
	
	@FXML
	protected void btnSalvarPacoteHandler(ActionEvent event)
	{
		disableFieldsPacote();
		btnSalvarPacote.setDisable(true);
		btnNovoPacote.setDisable(false);
		btnAlterarPacote.setDisable(false);
		btnExcluirPacote.setDisable(false);
		btnPesquisarPacote.setDisable(false);
		btnSalvarServico.setDisable(false);
		btnNovoServico.setDisable(false);
		btnAlterarServico.setDisable(false);
		btnExcluirServico.setDisable(false);
		btnPesquisarServico.setDisable(false);
		
		getValuesPacote();
		if(saveOption == 1)
		{
			if(validadeFieldsPacote())
			{
				TipoPacoteDAO.Create(tipoPacote.getDescricao());
				UpdateORDeletePacote();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
			}
		}
		else if(saveOption == 2)
		{
			if(validadeFieldsPacote())
			{
				TipoPacoteDAO.Update(tipoPacote.getId(), tipoPacote.getDescricao());
				UpdateORDeletePacote();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
				checkReceivedPacote();
			}
		}
	}
	
	@FXML
	protected void btnSalvarServicoHandler(ActionEvent event)
	{
		disableFieldsServico();
		btnSalvarPacote.setDisable(false);
		btnNovoPacote.setDisable(false);
		btnAlterarPacote.setDisable(false);
		btnExcluirPacote.setDisable(false);
		btnPesquisarPacote.setDisable(false);
		btnSalvarServico.setDisable(true);
		btnNovoServico.setDisable(false);
		btnAlterarServico.setDisable(false);
		btnExcluirServico.setDisable(false);
		btnPesquisarServico.setDisable(false);
		
		getValuesServico();
		if(saveOption == 1)
		{
			if(validadeFieldsServico())
			{
				ServicoDAO.Create(tipoServico.getDescricao(), tipoServico.getPreco(), tipoServico.getDuracao());
				UpdateORDeleteServico();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
			}
		}
		else if(saveOption == 2)
		{
			if(validadeFieldsServico())
			{
				ServicoDAO.Update(tipoServico.getId(), tipoServico.getDescricao(), tipoServico.getPreco(), tipoServico.getDuracao());
				UpdateORDeleteServico();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
				checkReceivedServico();
			}
		}
	}
	
	@FXML
	protected void btnAlterarPacoteHandler(ActionEvent event)
	{
		enableFieldsPacote();
		btnSalvarPacote.setDisable(false);
		btnNovoPacote.setDisable(true);
		btnAlterarPacote.setDisable(true);
		btnExcluirPacote.setDisable(true);
		btnPesquisarPacote.setDisable(true);
		btnSalvarServico.setDisable(true);
		btnNovoServico.setDisable(true);
		btnAlterarServico.setDisable(true);
		btnExcluirServico.setDisable(true);
		btnPesquisarServico.setDisable(true);
		saveOption = 2;
	}
	
	@FXML
	protected void btnAlterarServicoHandler(ActionEvent event)
	{
		enableFieldsServico();
		btnSalvarPacote.setDisable(true);
		btnNovoPacote.setDisable(true);
		btnAlterarPacote.setDisable(true);
		btnExcluirPacote.setDisable(true);
		btnPesquisarPacote.setDisable(true);
		btnSalvarServico.setDisable(false);
		btnNovoServico.setDisable(true);
		btnAlterarServico.setDisable(true);
		btnExcluirServico.setDisable(true);
		btnPesquisarServico.setDisable(true);
		saveOption = 2;
	}
	
	@FXML
	protected void btnExcluirPacoteHandler(ActionEvent event)
	{
		try
		{
			int id = Integer.parseInt(txtIdPacote.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exclusão");
			alert.setHeaderText("Confirmação de Exclusão");
			alert.setContentText("Deseja mesmo excluir?");
			alert.showAndWait();
			if(alert.getResult() == alert.getResult().OK)
			{
				TipoPacoteDAO.Delete(id);
				clearFields();
				UpdateORDeletePacote();
			}
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Falha");
			alert.setHeaderText("Erro de Exclusão");
			alert.setContentText("Não foi possível excluir o registro selecionado! \n\n Error: " + e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	protected void btnExcluirServicoHandler(ActionEvent event)
	{
		try
		{
			int id = Integer.parseInt(txtIdServico.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exclusão");
			alert.setHeaderText("Confirmação de Exclusão");
			alert.setContentText("Deseja mesmo excluir?");
			alert.showAndWait();
			if(alert.getResult() == alert.getResult().OK)
			{
				ServicoDAO.Delete(id);
				clearFields();
				UpdateORDeleteServico();
			}
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Falha");
			alert.setHeaderText("Erro de Exclusão");
			alert.setContentText("Não foi possível excluir o registro selecionado! \n\n Error: " + e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	protected void btnPesquisarPacoteHandler(ActionEvent event)
	{
		content.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/menuconsulta.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			MenuConsultaController controller = loader.<MenuConsultaController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consultas");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void btnPesquisarServicoHandler(ActionEvent event)
	{
		content.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/menuconsulta.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			MenuConsultaController controller = loader.<MenuConsultaController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consultas");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void checkReceivedPacote()
	{
		if(receivedpacote.getDescricao() != null)
		{
			txtIdPacote.setText(String.valueOf(receivedpacote.getId()));
			txtDescPacote.setText(receivedpacote.getDescricao());
		}
	}
	
	private void checkReceivedServico()
	{
		if(receivedservico.getDescricao() != null)
		{
			txtIdServico.setText(String.valueOf(receivedservico.getId()));
			txtDescServico.setText(receivedservico.getDescricao());
			txtPrecoServico.setText(String.valueOf(receivedservico.getPreco()));
			txtDuracaoServico.setText(String.valueOf(receivedservico.getDuracao()));
		}
	}
	
	private void enableFieldsPacote()
	{
		txtDescPacote.setDisable(false);
		txtDescPacote.setStyle("-fx-opacity: 1;");
	}
	
	private void enableFieldsServico()
	{
		txtDescServico.setDisable(false);
		txtDescServico.setStyle("-fx-opacity: 1;");
		txtPrecoServico.setDisable(false);
		txtPrecoServico.setStyle("-fx-opacity: 1;");
		txtDuracaoServico.setDisable(false);
		txtDuracaoServico.setStyle("-fx-opacity: 1;");
	}
	
	private void disableFieldsPacote()
	{
		txtIdPacote.setDisable(true);
		txtIdPacote.setStyle("-fx-opacity: 1;");
		txtDescPacote.setDisable(true);
		txtDescPacote.setStyle("-fx-opacity: 1;");
	}
	
	private void disableFieldsServico()
	{
		txtIdServico.setDisable(true);
		txtIdServico.setStyle("-fx-opacity: 1;");
		txtDescServico.setDisable(true);
		txtDescServico.setStyle("-fx-opacity: 1;");
		txtPrecoServico.setDisable(true);
		txtPrecoServico.setStyle("-fx-opacity: 1;");
		txtDuracaoServico.setDisable(true);
		txtDuracaoServico.setStyle("-fx-opacity: 1;");
	}
	
	private void UpdateORDeletePacote()
	{
		if (!txtIdPacote.getText().equals(""))
		{
			btnAlterarPacote.setDisable(false);
			btnExcluirPacote.setDisable(false);
		}
		else
		{
			btnAlterarPacote.setDisable(true);
			btnExcluirPacote.setDisable(true);
		}
	}
	
	private void UpdateORDeleteServico()
	{
		if (!txtIdServico.getText().equals(""))
		{
			btnAlterarServico.setDisable(false);
			btnExcluirServico.setDisable(false);
		}
		else
		{
			btnAlterarServico.setDisable(true);
			btnExcluirServico.setDisable(true);
		}
	}
	
	private boolean validadeFieldsPacote()
	{
		boolean retorno = true;
		if(tipoPacote.getDescricao() == null || tipoPacote.getDescricao().equals(""))
			retorno = false;
		
		return retorno;
	}
	
	private boolean validadeFieldsServico()
	{
		boolean retorno = true;
		if(tipoServico.getDescricao() == null || tipoServico.getDescricao().equals(""))
			retorno = false;
		if(tipoServico.getPreco() < 0)
			retorno = false;
		if(tipoServico.getDuracao() < 0)
			retorno = false;
		return retorno;
	}
	
	private void getValuesPacote()
	{
		if(txtIdPacote.getText() != "")
		{
			try 
			{
				tipoPacote.setId(Integer.parseInt(txtIdPacote.getText()));
			}
			catch(Exception e)
			{
				tipoPacote.setId(-1);
			}
		}
		if(txtDescPacote.getText() != "")
			tipoPacote.setDescricao(txtDescPacote.getText());
	}
	
	private void getValuesServico()
	{
		if(txtIdServico.getText() != "")
		{
			try 
			{
				tipoServico.setId(Integer.parseInt(txtIdServico.getText()));
			}
			catch(Exception e)
			{
				tipoServico.setId(-1);
			}
		}
		if(txtDescServico.getText() != "")
			tipoServico.setDescricao(txtDescServico.getText());
		if(txtPrecoServico.getText() != "")
		{
			double p = 0;
			try
			{
				p = Double.parseDouble(txtPrecoServico.getText());
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Falha");
				alert.setHeaderText("Erro de Preço");
				alert.setContentText("O preço não é válido");
				alert.showAndWait();
			}
			tipoServico.setPreco(p);
		}
		if(txtDuracaoServico.getText() != "")
		{
			int dur = 0;
			try
			{
				dur = Integer.parseInt(txtDuracaoServico.getText());
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Falha");
				alert.setHeaderText("Erro de Duração");
				alert.setContentText("A duração não é válida");
				alert.showAndWait();
			}
			tipoServico.setDuracao(dur);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		disableFieldsPacote();
		disableFieldsServico();
		btnSalvarPacote.setDisable(true);
		btnSalvarServico.setDisable(true);
		checkReceivedPacote();
		
		UpdateORDeletePacote();
		UpdateORDeleteServico();
	}
	
	private void clearFields()
	{
		txtIdPacote.setText("");
		txtDescPacote.setText("");
		txtIdServico.setText("");
		txtDescServico.setText("");
		txtPrecoServico.setText("");
		txtDuracaoServico.setText("");
	}
}

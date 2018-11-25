package pack.petshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import pack.DAO.DonoDAO;
import pack.DAO.FuncionarioDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Funcionario;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;

public class FuncionarioController implements Initializable
{	
	private Funcionario func = new Funcionario();
	private static int saveOption = 1;
	public static Funcionario receivedfunc = new Funcionario();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtObservacoes;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtTelefone;
	@FXML
	private TextField txtCelular;
	@FXML
	private TextField txtSalario;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnAlterar;
	@FXML
	private Button btnExcluir;
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private Pane content;
	
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
	}
	
	@FXML
	protected void btnNovoHandler(ActionEvent event)
	{
		clearFields();
		enableFields();
		btnSalvar.setDisable(false);
		btnNovo.setDisable(true);
		btnAlterar.setDisable(true);
		btnExcluir.setDisable(true);
		btnPesquisar.setDisable(true);
		saveOption = 1;
	}
	
	@FXML
	protected void btnSalvarHandler(ActionEvent event)
	{
		disableFields();
		btnSalvar.setDisable(true);
		btnNovo.setDisable(false);
		btnAlterar.setDisable(false);
		btnExcluir.setDisable(false);
		btnPesquisar.setDisable(false);
		
		getValues();
		if(saveOption == 1)
		{
			if(validadeFields())
			{
				FuncionarioDAO.Create(func.getCpf(), func.getNome(), func.getEndereco(), func.getTelefone(), func.getCelular(), func.getObservacoes(), func.getSalario());
				UpdateORDelete();
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
			if(validadeFields())
			{
				FuncionarioDAO.Update(func.getId(), func.getCpf(), func.getNome(), func.getEndereco(), func.getTelefone(), func.getCelular(), func.getObservacoes(), func.getSalario());
				UpdateORDelete();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
				checkReceivedFunc();
			}
		}
	}
	
	@FXML
	protected void btnAlterarHandler(ActionEvent event)
	{
		enableFields();
		btnSalvar.setDisable(false);
		btnNovo.setDisable(true);
		btnAlterar.setDisable(true);
		btnExcluir.setDisable(true);
		btnPesquisar.setDisable(true);
		saveOption = 2;
	}
	
	@FXML
	protected void btnExcluirHandler(ActionEvent event)
	{
		try
		{
			int id = Integer.parseInt(txtId.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exclusão");
			alert.setHeaderText("Confirmação de Exclusão");
			alert.setContentText("Deseja mesmo excluir?");
			alert.showAndWait();
			if(alert.getResult() == alert.getResult().OK)
			{
				FuncionarioDAO.Delete(id);
				clearFields();
				UpdateORDelete();
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
	protected void btnPesquisarHandler(ActionEvent event)
	{
		content.getChildren().clear();
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/consultafuncionario.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			ConsultaFuncionarioController controller = loader.<ConsultaFuncionarioController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consulta de Funcionários");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		populateComboBox();
		disableFields();
		btnSalvar.setDisable(true);
		checkReceivedFunc();
		
		UpdateORDelete();
	}
	
	private void checkReceivedFunc()
	{
		if(receivedfunc.getNome() != null)
		{
			txtId.setText(String.valueOf(receivedfunc.getId()));
			txtNome.setText(receivedfunc.getNome());
			txtCPF.setText(receivedfunc.getCpf());
			txtEndereco.setText(receivedfunc.getEndereco());
			txtTelefone.setText(receivedfunc.getTelefone());
			txtCelular.setText(receivedfunc.getCelular());
			txtObservacoes.setText(receivedfunc.getObservacoes());
			txtSalario.setText(String.valueOf(receivedfunc.getSalario()));
		}
	}
	
	private void enableFields()
	{
		txtNome.setDisable(false);
		txtNome.setStyle("-fx-opacity: 1;");
		txtCPF.setDisable(false);
		txtCPF.setStyle("-fx-opacity: 1;");
		txtEndereco.setDisable(false);
		txtEndereco.setStyle("-fx-opacity: 1;");
		txtTelefone.setDisable(false);
		txtTelefone.setStyle("-fx-opacity: 1;");
		txtCelular.setDisable(false);
		txtCelular.setStyle("-fx-opacity: 1;");
		txtSalario.setDisable(false);
		txtSalario.setStyle("-fx-opacity: 1;");
		txtObservacoes.setDisable(false);
		txtObservacoes.setStyle("-fx-opacity: 1;");
	}
	
	private void disableFields()
	{
		txtId.setDisable(true);
		txtId.setStyle("-fx-opacity: 1;");
		txtNome.setDisable(true);
		txtNome.setStyle("-fx-opacity: 1;");
		txtCPF.setDisable(true);
		txtCPF.setStyle("-fx-opacity: 1;");
		txtEndereco.setDisable(true);
		txtEndereco.setStyle("-fx-opacity: 1;");
		txtTelefone.setDisable(true);
		txtTelefone.setStyle("-fx-opacity: 1;");
		txtCelular.setDisable(true);
		txtCelular.setStyle("-fx-opacity: 1;");
		txtSalario.setDisable(true);
		txtSalario.setStyle("-fx-opacity: 1;");
		txtObservacoes.setDisable(true);
		txtObservacoes.setStyle("-fx-opacity: 1;");
	}
	
	private void UpdateORDelete()
	{
		if (!txtId.getText().equals(""))
		{
			btnAlterar.setDisable(false);
			btnExcluir.setDisable(false);
		}
		else
		{
			btnAlterar.setDisable(true);
			btnExcluir.setDisable(true);
		}
	}
	
	private void populateComboBox()
	{
	}

	private void clearFields()
	{
		txtId.setText("");
		txtNome.setText("");
		txtCPF.setText("");
		txtEndereco.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtSalario.setText("");
		txtObservacoes.setText("");
	}
	
	private boolean validadeFields()
	{
		boolean retorno = true;
		if(func.getNome() == null || func.getNome().equals(""))
			retorno = false;
		if(func.getCpf() == null || func.getCpf().equals(""))
			retorno = false;
		if(func.getCelular() == null || func.getCelular().equals(""))
			retorno = false;
		if(func.getSalario() < 0)
			retorno = false;
		return retorno;
	}
	
	private void getValues()
	{
		if(txtId.getText() != "")
		{
			try 
			{
				func.setId(Integer.parseInt(txtId.getText()));
			}
			catch(Exception e)
			{
				func.setId(-1);
			}
		}
		if(txtNome.getText() != "")
			func.setNome(txtNome.getText());
		if(txtCPF.getText() != "")
			func.setCpf(txtCPF.getText());
		if(txtEndereco.getText() != "")
			func.setEndereco(txtEndereco.getText());
		if(txtTelefone.getText() != "")
			func.setTelefone(txtTelefone.getText());
		if(txtCelular.getText() != "")
			func.setCelular(txtCelular.getText());
		if(txtObservacoes.getText() != "")
			func.setObservacoes(txtObservacoes.getText());	
		if(txtSalario.getText() != "")
		{
			double sal = 0;
			try
			{
				sal = Double.parseDouble(txtSalario.getText());
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Falha");
				alert.setHeaderText("Erro de Salário");
				alert.setContentText("O salário não é válido");
				alert.showAndWait();
			}
			func.setSalario(sal);
		}
	}
}

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
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;

public class DonoController implements Initializable
{	
	private Dono dono = new Dono();
	private static int saveOption = 1;
	public static Dono receiveddono = new Dono();
	
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
	private ComboBox<Pet> cboPets;
	@FXML
	private ListView<Pet> lstPets;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnRemove;
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
	protected void btnAddHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnRemoveHandler(ActionEvent event)
	{
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
				DonoDAO.Create(dono.getCpf(), dono.getNome(), dono.getEndereco(), dono.getTelefone(), dono.getCelular(), dono.getObservacoes());
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
				DonoDAO.Update(dono.getId(), dono.getCpf(), dono.getNome(), dono.getEndereco(), dono.getTelefone(), dono.getCelular(), dono.getObservacoes());
				UpdateORDelete();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
				checkReceivedDono();
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
				DonoDAO.Delete(id);
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/consultadono.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			ConsultaDonoController controller = loader.<ConsultaDonoController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consulta de Donos");
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
		checkReceivedDono();
		
		UpdateORDelete();
	}
	
	private void checkReceivedDono()
	{
		if(receiveddono.getNome() != null)
		{
			txtId.setText(String.valueOf(receiveddono.getId()));
			txtNome.setText(receiveddono.getNome());
			txtCPF.setText(receiveddono.getCpf());
			txtEndereco.setText(receiveddono.getEndereco());
			txtTelefone.setText(receiveddono.getTelefone());
			txtCelular.setText(receiveddono.getCelular());
			txtObservacoes.setText(receiveddono.getObservacoes());
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
		txtObservacoes.setDisable(false);
		txtObservacoes.setStyle("-fx-opacity: 1;");
		cboPets.setDisable(false);
		cboPets.setStyle("-fx-opacity: 1;");
		btnAdd.setDisable(false);
		btnRemove.setDisable(false);
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
		txtObservacoes.setDisable(true);
		txtObservacoes.setStyle("-fx-opacity: 1;");
		cboPets.setDisable(true);
		cboPets.setStyle("-fx-opacity: 1;");
		btnAdd.setDisable(true);
		btnRemove.setDisable(true);
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
		ObservableList<Pet> optionspets = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.pets.size(); i++)
		{	
			optionspets.add(Utils.pets.get(i));
		}
		cboPets.setItems(optionspets);
		
		UpdateORDelete();
		
	}

	private void clearFields()
	{
		txtId.setText("");
		txtNome.setText("");
		txtCPF.setText("");
		txtEndereco.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtObservacoes.setText("");
		cboPets.getSelectionModel().clearSelection();
		lstPets.getItems().clear();
	}
	
	private boolean validadeFields()
	{
		boolean retorno = true;
		if(dono.getNome() == null || dono.getNome().equals(""))
			retorno = false;
		if(dono.getCpf() == null || dono.getCpf().equals(""))
			retorno = false;
		if(dono.getCelular() == null || dono.getCelular().equals(""))
			retorno = false;
		
		return retorno;
	}
	
	private void getValues()
	{
		if(txtId.getText() != "")
		{
			try 
			{
				dono.setId(Integer.parseInt(txtId.getText()));
			}
			catch(Exception e)
			{
				dono.setId(-1);
			}
		}
		if(txtNome.getText() != "")
			dono.setNome(txtNome.getText());
		if(txtCPF.getText() != "")
			dono.setCpf(txtCPF.getText());
		if(txtEndereco.getText() != "")
			dono.setEndereco(txtEndereco.getText());
		if(txtTelefone.getText() != "")
			dono.setTelefone(txtTelefone.getText());
		if(txtCelular.getText() != "")
			dono.setCelular(txtCelular.getText());
		if(txtObservacoes.getText() != "")
			dono.setObservacoes(txtObservacoes.getText());	
	}
}

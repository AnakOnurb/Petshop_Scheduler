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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import pack.DAO.PetDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;

public class PetController implements Initializable
{	
	private Pet pet = new Pet();
	private static int saveOption = 1;
	public static Pet receivedpet = new Pet();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtIdade;
	@FXML
	private TextField txtObservacoes;
	@FXML
	private ComboBox<Especie> cboEspecie;
	@FXML
	private ComboBox<Pelagem> cboPelagem;
	@FXML
	private ComboBox<Raca> cboRaca;
	@FXML
	private ComboBox<Porte> cboPorte;
	@FXML
	private ComboBox<Dono> cboDonos;
	@FXML
	private ListView<Dono> lstDonos;
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
				PetDAO.Create(pet.getNome(), pet.getIdade(), pet.getEspecieId(), pet.getRacaId(), pet.getPelagemId(), pet.getPorteId(), pet.getObservacoes());
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
				PetDAO.Update(pet.getId(), pet.getNome(), pet.getIdade(), pet.getEspecieId(), pet.getRacaId(), pet.getPelagemId(), pet.getPorteId(), pet.getObservacoes());
				UpdateORDelete();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informações");
				alert.setHeaderText("Preenchimento inválido");
				alert.setContentText("Alguns dos campos não estão preenchidos corretamente. \n Por favor, verifique e tente novamente.");
				alert.showAndWait();
				checkReceivedPet();
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
				PetDAO.Delete(id);
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/consultapet.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			ConsultaPetController controller = loader.<ConsultaPetController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consulta de Pets");
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
		checkReceivedPet();
		
		UpdateORDelete();
	}
	
	private void checkReceivedPet()
	{
		if(receivedpet.getNome() != null)
		{
			txtId.setText(String.valueOf(receivedpet.getId()));
			txtNome.setText(receivedpet.getNome());
			txtIdade.setText(receivedpet.getIdade());
			
			int espId = receivedpet.getEspecieId();
			cboEspecie.getSelectionModel().select(Utils.ReadEspecie(espId));
			int pelId = receivedpet.getPelagemId();
			cboPelagem.getSelectionModel().select(Utils.ReadPelagem(pelId));
			int racaId = receivedpet.getRacaId();
			cboRaca.getSelectionModel().select(Utils.ReadRacas(racaId));
			int porteId = receivedpet.getPorteId();
			cboPorte.getSelectionModel().select(Utils.ReadPorte(porteId));
			txtObservacoes.setText(receivedpet.getObservacoes());
		}
	}
	
	private void enableFields()
	{
		txtNome.setDisable(false);
		txtNome.setStyle("-fx-opacity: 1;");
		txtIdade.setDisable(false);
		txtIdade.setStyle("-fx-opacity: 1;");
		txtObservacoes.setDisable(false);
		txtObservacoes.setStyle("-fx-opacity: 1;");
		cboEspecie.setDisable(false);
		cboEspecie.setStyle("-fx-opacity: 1;");
		cboPelagem.setDisable(false);
		cboPelagem.setStyle("-fx-opacity: 1;");
		cboRaca.setDisable(false);
		cboRaca.setStyle("-fx-opacity: 1;");
		cboPorte.setDisable(false);
		cboPorte.setStyle("-fx-opacity: 1;");
		cboDonos.setDisable(false);
		cboDonos.setStyle("-fx-opacity: 1;");
		lstDonos.setDisable(false);
		lstDonos.setStyle("-fx-opacity: 1;");
		btnAdd.setDisable(false);
		btnRemove.setDisable(false);
	}
	
	private void disableFields()
	{
		txtId.setDisable(true);
		txtId.setStyle("-fx-opacity: 1;");
		txtNome.setDisable(true);
		txtNome.setStyle("-fx-opacity: 1;");
		txtIdade.setDisable(true);
		txtIdade.setStyle("-fx-opacity: 1;");
		txtObservacoes.setDisable(true);
		txtObservacoes.setStyle("-fx-opacity: 1;");
		cboEspecie.setDisable(true);
		cboEspecie.setStyle("-fx-opacity: 1;");
		cboPelagem.setDisable(true);
		cboPelagem.setStyle("-fx-opacity: 1;");
		cboRaca.setDisable(true);
		cboRaca.setStyle("-fx-opacity: 1;");
		cboPorte.setDisable(true);
		cboPorte.setStyle("-fx-opacity: 1;");
		cboDonos.setDisable(true);
		cboDonos.setStyle("-fx-opacity: 1;");
		lstDonos.setDisable(true);
		lstDonos.setStyle("-fx-opacity: 1;");
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
		ObservableList<Raca> optionsraca = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.racas.size(); i++)
		{	
			optionsraca.add(Utils.racas.get(i));
		}
		cboRaca.setItems(optionsraca);
		
		ObservableList<Especie> optionsespecie = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.especies.size(); i++)
		{	
			optionsespecie.add(Utils.especies.get(i));
		}
		cboEspecie.setItems(optionsespecie);
		
		ObservableList<Porte> optionsporte = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.portes.size(); i++)
		{	
			optionsporte.add(Utils.portes.get(i));
		}
		cboPorte.setItems(optionsporte);
		
		ObservableList<Pelagem> optionspelagem = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.pelagens.size(); i++)
		{	
			optionspelagem.add(Utils.pelagens.get(i));
		}
		cboPelagem.setItems(optionspelagem);
		
		ObservableList<Dono> optionsdonos = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.donos.size(); i++)
		{	
			optionsdonos.add(Utils.donos.get(i));
		}
		cboDonos.setItems(optionsdonos);
		
		UpdateORDelete();
		
	}

	private void clearFields()
	{
		txtId.setText("");
		txtNome.setText("");
		txtIdade.setText("");
		txtObservacoes.setText("");
		cboRaca.getSelectionModel().clearSelection();
		cboRaca.setValue(null);
		cboDonos.getSelectionModel().clearSelection();
		cboDonos.setValue(null);
		cboEspecie.getSelectionModel().clearSelection();
		cboEspecie.setValue(null);
		cboPelagem.getSelectionModel().clearSelection();
		cboPelagem.setValue(null);
		cboPorte.getSelectionModel().clearSelection();
		cboPorte.setValue(null);
		lstDonos.getItems().clear();
	}
	
	private boolean validadeFields()
	{
		boolean retorno = true;
		if(pet.getNome() == null || pet.getNome().equals(""))
			retorno = false;
		if(pet.getPelagemId() <= 0)
			retorno = false;
		if(pet.getEspecieId() <= 0)
			retorno = false;
		if(pet.getPorteId() <= 0)
			retorno = false;
		if(pet.getRacaId() <= 0)
			retorno = false;
		
		return retorno;
	}
	
	private void getValues()
	{
		if(txtId.getText() != "")
		{
			try 
			{
				pet.setId(Integer.parseInt(txtId.getText()));
			}
			catch(Exception e)
			{
				pet.setId(-1);
			}
		}
		if(txtNome.getText() != "")
			pet.setNome(txtNome.getText());
		if(txtIdade.getText() != "")
			pet.setIdade(txtIdade.getText());
		if(txtObservacoes.getText() != "")
			pet.setObservacoes(txtObservacoes.getText());
		if(cboEspecie.getSelectionModel().getSelectedIndex() != -1)
		{
			Especie especie = cboEspecie.getSelectionModel().getSelectedItem();
			pet.setEspecieId(especie.getId());
		}
		if(cboRaca.getSelectionModel().getSelectedIndex() != -1)
		{
			Raca raca = cboRaca.getSelectionModel().getSelectedItem();
			pet.setRacaId(raca.getId());
		}
		if(cboPelagem.getSelectionModel().getSelectedIndex() != -1)
		{
			Pelagem pelagem = cboPelagem.getSelectionModel().getSelectedItem();
			pet.setPelagemId(pelagem.getId());
		}
		if(cboPorte.getSelectionModel().getSelectedIndex() != -1)
		{
			Porte porte = cboPorte.getSelectionModel().getSelectedItem();
			pet.setPorteId(porte.getId());
		}
	}
}

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
import pack.DAO.PetDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;

public class PetController implements Initializable
{	
	public static Pet pet = new Pet();
	
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
	protected void btnNovoHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnSalvarHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnAlterarHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnExcluirHandler(ActionEvent event)
	{
		try
		{
			int id = Integer.parseInt(txtId.getText());
			PetDAO.Delete(id);
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
		if(pet.getNome() != null)
		{
			txtId.setText(String.valueOf(pet.getId()));
			txtNome.setText(pet.getNome());
			txtIdade.setText(pet.getIdade());
			cboEspecie.setValue(pet.getEspecieId());
			//cboEspecie.setValue(String.valueOf(pet.getEspecieId()));
			//cboPelagem.setValue(String.valueOf(pet.getPelagemId()));
			//cboRaca.setValue(String.valueOf(pet.getRacaId()));
			//cboPorte.setValue(String.valueOf(pet.getPorteId()));
			txtObservacoes.setText(pet.getObservacoes());
		}
		populateComboBox();
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
	}
}

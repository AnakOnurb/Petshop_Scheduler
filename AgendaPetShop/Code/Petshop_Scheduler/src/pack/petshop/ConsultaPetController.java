package pack.petshop;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pack.DAO.PetDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pet;
import pack.VO.Raca;

public class ConsultaPetController implements Initializable
{	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private ComboBox<Dono> cboDonos;
	@FXML
	private ComboBox<Raca> cboRaca;
	@FXML
	private ComboBox<Especie> cboEspecie;
	
	@FXML
	private TableView<Pet> dtDados;
	
	@FXML
	private Pane content;
	@FXML
	private Label title;
	
	int id = -1, racaId = -1, especieId = -1, donoId = -1; 
	String nome = ""; 
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
	}
	
	private void getFields()
	{
		if(txtId.getText() != "")
		{
			try 
			{
				id = Integer.parseInt(txtId.getText());
			}
			catch(Exception e)
			{
				id = -1;
			}
		}
		
		if(txtNome.getText() != "")
			nome = txtNome.getText();
		
		if(cboDonos.getSelectionModel().getSelectedIndex() != -1)
		{
			Dono dono = cboDonos.getSelectionModel().getSelectedItem();
			donoId = dono.getId();
		}
		
		if(cboRaca.getSelectionModel().getSelectedIndex() != -1)
		{
			Raca raca = cboRaca.getSelectionModel().getSelectedItem();
			racaId = raca.getId();
		}
		
		if(cboEspecie.getSelectionModel().getSelectedIndex() != -1)
		{
			Especie esp = cboEspecie.getSelectionModel().getSelectedItem();
			especieId = esp.getId();
		}
	}
	
	@FXML
	protected void btnPesquisarHandler(ActionEvent event)
	{
		dtDados.getItems().clear();
		
		getFields();
		
		ArrayList<Pet> pets = PetDAO.Read(id, nome, especieId, racaId, -1, donoId);
		if(pets != null && pets.size() > 0)
		{
			for(int i = 0; i < pets.size(); i++)
			{
				dtDados.getItems().add(pets.get(i));	
			}	
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Vazio");
			alert.setHeaderText("Consulta vazia");
			alert.setContentText("A consulta não retornou nenhum valor!");
			alert.showAndWait();
		}
	}
	
	@FXML
	protected void btnSelecionarHandler(ActionEvent event)
	{
		if(dtDados.getSelectionModel().getSelectedItem() != null)
		{
			Pet pet = new Pet();
			pet = dtDados.getSelectionModel().getSelectedItem();
			PetController.receivedpet = pet;
			
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
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta!");
			alert.setHeaderText("Seleção");
			alert.setContentText("Não há dados selecionados!");
			alert.showAndWait();
		}
	}
	
	@FXML
	protected void btnLimparHandler(ActionEvent event)
	{
		txtId.setText("");
		txtNome.setText("");
		cboRaca.getSelectionModel().clearSelection();
		cboDonos.getSelectionModel().clearSelection();
		cboEspecie.getSelectionModel().clearSelection();
		dtDados.getItems().clear();
		id = -1; 
		racaId = -1;
		especieId = -1;
		donoId = -1; 
		nome = ""; 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		setTableColumns();
		populateComboBox();
	}
	
	private void populateComboBox()
	{
		ObservableList<Raca> optionsracas = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.racas.size(); i++)
		{	
			optionsracas.add(Utils.racas.get(i));
		}
		cboRaca.setItems(optionsracas);
		
		ObservableList<Dono> optionsdonos = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.donos.size(); i++)
		{	
			optionsdonos.add(Utils.donos.get(i));
		}
		cboDonos.setItems(optionsdonos);
		
		ObservableList<Especie> optionsespecie = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.especies.size(); i++)
		{	
			optionsespecie.add(Utils.especies.get(i));
		}
		cboEspecie.setItems(optionsespecie);
	}
	
	private void setTableColumns()
	{
		TableColumn idCol = new TableColumn("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn idadeCol = new TableColumn("Idade");
		idadeCol.setCellValueFactory(new PropertyValueFactory<>("idade"));

		TableColumn especieCol = new TableColumn("Espécie");
		especieCol.setCellValueFactory(new PropertyValueFactory<>("especieId"));
		
		TableColumn racaCol = new TableColumn("Raça");
		racaCol.setCellValueFactory(new PropertyValueFactory<>("racaId"));
		
		TableColumn pelagemCol = new TableColumn("Pelagem");
		pelagemCol.setCellValueFactory(new PropertyValueFactory<>("pelagemId"));
		
		TableColumn porteCol = new TableColumn("Porte");
		porteCol.setCellValueFactory(new PropertyValueFactory<>("porteId"));
		
		TableColumn observacoesCol = new TableColumn("Observações");
		observacoesCol.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
		
		dtDados.getColumns().addAll(idCol, nameCol, idadeCol, especieCol, racaCol, pelagemCol, porteCol, observacoesCol);
	}
}

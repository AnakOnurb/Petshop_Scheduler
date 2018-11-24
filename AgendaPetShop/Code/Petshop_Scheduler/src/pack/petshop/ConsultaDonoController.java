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
import pack.DAO.DonoDAO;
import pack.DAO.PetDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pet;
import pack.VO.Raca;

public class ConsultaDonoController implements Initializable
{	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtCPF;
	@FXML
	private ComboBox<Pet> cboPets;
	
	@FXML
	private TableView<Dono> dtDados;
	
	@FXML
	private Pane content;
	@FXML
	private Label title;
	
	int id = -1, petId = -1;
	String nome = "", cpf = ""; 
	
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
		if(txtCPF.getText() != "")
			cpf = txtCPF.getText();
		
		if(cboPets.getSelectionModel().getSelectedIndex() != -1)
		{
			Pet pet = cboPets.getSelectionModel().getSelectedItem();
			petId = pet.getId();
		}
	}
	
	@FXML
	protected void btnPesquisarHandler(ActionEvent event)
	{
		dtDados.getItems().clear();	
		getFields();
		
		ArrayList<Dono> donos = DonoDAO.Read(id, cpf, nome, -1);
		if(donos != null && donos.size() > 0)
		{
			for(int i = 0; i < donos.size(); i++)
			{
				dtDados.getItems().add(donos.get(i));	
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
			Dono dono = new Dono();
			dono = dtDados.getSelectionModel().getSelectedItem();
			DonoController.receiveddono = dono;
			
			content.getChildren().clear();
			try 
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/dono.fxml"));
				Pane newLoadedPane = (Pane) loader.load();

				DonoController controller = loader.<DonoController>getController();
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
		txtCPF.setText("");
		cboPets.getSelectionModel().clearSelection();
		dtDados.getItems().clear();
		id = -1; 
		petId = -1; 
		nome = "";
		cpf = "";
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		setTableColumns();
		populateComboBox();
	}
	
	private void populateComboBox()
	{
		ObservableList<Pet> optionspet = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.pets.size(); i++)
		{	
			optionspet.add(Utils.pets.get(i));
		}
		cboPets.setItems(optionspet);
	}
	
	private void setTableColumns()
	{
		TableColumn idCol = new TableColumn("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn cpfCol = new TableColumn("CPF");
		cpfCol.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		
		TableColumn nomeCol = new TableColumn("Name");
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn enderecoCol = new TableColumn("Endereço");
		enderecoCol.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		
		TableColumn telefoneCol = new TableColumn("Telefone");
		telefoneCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		
		TableColumn celularCol = new TableColumn("Celular");
		celularCol.setCellValueFactory(new PropertyValueFactory<>("celular"));
		
		TableColumn observacoesCol = new TableColumn("Observações");
		observacoesCol.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
		
		dtDados.getColumns().addAll(idCol, cpfCol, nomeCol, enderecoCol, telefoneCol, celularCol, observacoesCol);
	}
}

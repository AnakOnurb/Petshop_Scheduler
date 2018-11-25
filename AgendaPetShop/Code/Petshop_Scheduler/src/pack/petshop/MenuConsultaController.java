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
import pack.DAO.ServicoDAO;
import pack.DAO.TipoPacoteDAO;
import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pet;
import pack.VO.Raca;
import pack.VO.Servico;
import pack.VO.TipoPacote;

public class MenuConsultaController implements Initializable
{	
	@FXML
	private TextField txtIdPacote;
	@FXML
	private TextField txtDescricaoPacote;
	@FXML
	private TextField txtIdServico;
	@FXML
	private TextField txtDescricaoServico;
	
	@FXML
	private TableView<TipoPacote> dtDadosPacote;
	@FXML
	private TableView<Servico> dtDadosServico;
	
	@FXML
	private Pane content;
	@FXML
	private Label title;
	
	int idPacote = -1, idServico = -1;
	String descPacote = "", descServico = ""; 
	
	public void InitData(Pane pnlContent, Label lblTitle) {
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
	
	private void getFieldsPacote()
	{
		if(txtIdPacote.getText() != "")
		{
			try 
			{
				idPacote = Integer.parseInt(txtIdPacote.getText());
			}
			catch(Exception e)
			{
				idPacote = -1;
			}
		}
		
		if(txtDescricaoPacote.getText() != "")
			descPacote = txtDescricaoPacote.getText();
	}
	
	private void getFieldsServico()
	{
		if(txtIdServico.getText() != "")
		{
			try 
			{
				idServico = Integer.parseInt(txtIdServico.getText());
			}
			catch(Exception e)
			{
				idServico = -1;
			}
		}
		
		if(txtDescricaoServico.getText() != "")
			descServico = txtDescricaoServico.getText();
	}
	
	@FXML
	protected void btnPesquisarPacoteHandler(ActionEvent event)
	{
		dtDadosPacote.getItems().clear();
		
		getFieldsPacote();
		
		ArrayList<TipoPacote> tipospacote = TipoPacoteDAO.Read(idPacote, descPacote);
		if(tipospacote != null && tipospacote.size() > 0)
		{
			for(int i = 0; i < tipospacote.size(); i++)
			{
				dtDadosPacote.getItems().add(tipospacote.get(i));	
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
	protected void btnPesquisarServicoHandler(ActionEvent event)
	{
		dtDadosServico.getItems().clear();
		
		getFieldsServico();
		
		ArrayList<Servico> tiposservico = ServicoDAO.Read(idPacote, descPacote);
		if(tiposservico != null && tiposservico.size() > 0)
		{
			for(int i = 0; i < tiposservico.size(); i++)
			{
				dtDadosServico.getItems().add(tiposservico.get(i));	
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
	protected void btnSelecionarPacoteHandler(ActionEvent event)
	{
		if(dtDadosPacote.getSelectionModel().getSelectedItem() != null)
		{
			TipoPacote tipopacote = new TipoPacote();
			tipopacote = dtDadosPacote.getSelectionModel().getSelectedItem();
			MenuCadastroController.receivedpacote = tipopacote;
			
			content.getChildren().clear();
			try 
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/menucadastros.fxml"));
				Pane newLoadedPane = (Pane) loader.load();

				MenuCadastroController controller = loader.<MenuCadastroController>getController();
				controller.InitData(content, title);
				
				content.setPrefSize(780, 547);
				content.getChildren().add(newLoadedPane);
				title.setText("Cadastros");
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
	protected void btnSelecionarServicoHandler(ActionEvent event)
	{
		if(dtDadosServico.getSelectionModel().getSelectedItem() != null)
		{
			Servico tiposervico = new Servico();
			tiposervico = dtDadosServico.getSelectionModel().getSelectedItem();
			MenuCadastroController.receivedservico = tiposervico;
			
			content.getChildren().clear();
			try 
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/menucadastros.fxml"));
				Pane newLoadedPane = (Pane) loader.load();

				MenuCadastroController controller = loader.<MenuCadastroController>getController();
				controller.InitData(content, title);
				
				content.setPrefSize(780, 547);
				content.getChildren().add(newLoadedPane);
				title.setText("Cadastros");
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
		txtIdPacote.setText("");
		txtDescricaoPacote.setText("");
		txtIdServico.setText("");
		txtDescricaoServico.setText("");
		dtDadosPacote.getItems().clear();
		dtDadosServico.getItems().clear();
		idPacote = -1; 
		descPacote = ""; 
		idServico = -1; 
		descServico = ""; 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		setTableColumns();
	}
	
	private void setTableColumns()
	{
		TableColumn idPacoteCol = new TableColumn("ID");
		idPacoteCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn descPacoteCol = new TableColumn("Descrição");
		descPacoteCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		dtDadosPacote.getColumns().addAll(idPacoteCol, descPacoteCol);
		
		TableColumn idServicoCol = new TableColumn("ID");
		idServicoCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn descServicoCol = new TableColumn("Descrição");
		descServicoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		TableColumn precoServicoCol = new TableColumn("Preço");
		precoServicoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
		
		TableColumn duracaoServicoCol = new TableColumn("Duração");
		duracaoServicoCol.setCellValueFactory(new PropertyValueFactory<>("duracao"));
		
		dtDadosServico.getColumns().addAll(idPacoteCol, descPacoteCol, precoServicoCol, duracaoServicoCol);
		
	}
}

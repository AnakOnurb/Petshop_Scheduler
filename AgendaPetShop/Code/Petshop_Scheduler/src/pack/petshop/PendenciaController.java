package pack.petshop;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pack.DAO.AgendamentoDAO;
import pack.VO.Agendamento;

public class PendenciaController implements Initializable
{

	@FXML
	private TableView<Agendamento> dtDados;
	
	@FXML
	private Pane content;
	
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
	}
	
	@FXML
	protected void btnAdicionarHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnExcluirHandler(ActionEvent event)
	{
	}
	
	@FXML
	protected void btnPagarHandler(ActionEvent event)
	{
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setTableColumns();
		pesquisar();
	}	
	
	private void pesquisar()
	{
		dtDados.getItems().clear();
		
		ArrayList<Agendamento> agendamentos = AgendamentoDAO.ReadPendente();
		if(agendamentos != null && agendamentos.size() > 0)
		{
			for(int i = 0; i < agendamentos.size(); i++)
			{
				dtDados.getItems().add(agendamentos.get(i));	
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
	
	private void setTableColumns()
	{
		TableColumn idCol = new TableColumn("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn petCol = new TableColumn("Pet");
		petCol.setCellValueFactory(new PropertyValueFactory<>("petId"));
		
		TableColumn dataCol = new TableColumn("Data");
		dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

		TableColumn horarioCol = new TableColumn("Horário");
		horarioCol.setCellValueFactory(new PropertyValueFactory<>("horario"));
		
		TableColumn servicoCol = new TableColumn("Serviço");
		servicoCol.setCellValueFactory(new PropertyValueFactory<>("servicoId"));
		
		TableColumn funcCol = new TableColumn("Funcionário");
		funcCol.setCellValueFactory(new PropertyValueFactory<>("funcionarioId"));
		
		TableColumn pacoteCol = new TableColumn("Pacote");
		pacoteCol.setCellValueFactory(new PropertyValueFactory<>("pacoteId"));
		
		TableColumn pagamentoCol = new TableColumn("Pagamento");
		pagamentoCol.setCellValueFactory(new PropertyValueFactory<>("pagamentoId"));
		
		TableColumn canceladoCol = new TableColumn("Cancelado");
		canceladoCol.setCellValueFactory(new PropertyValueFactory<>("cancelado"));
		
		dtDados.getColumns().addAll(idCol, petCol, dataCol, horarioCol, servicoCol, funcCol, pacoteCol, pagamentoCol, canceladoCol);
	}
}

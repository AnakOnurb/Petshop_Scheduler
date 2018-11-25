package pack.petshop;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pack.DAO.AgendamentoDAO;
import pack.DAO.PetDAO;
import pack.VO.Agendamento;
import pack.VO.Funcionario;
import pack.VO.Pet;
import pack.VO.Servico;

public class AgendamentoController implements Initializable
{	
	private Agendamento agendamento = new Agendamento();
	private static int saveOption = 1;
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtHorario;
	@FXML
	private DatePicker dtpData;
	@FXML
	private ComboBox<Pet> cboPet;
	@FXML
	private ComboBox<Funcionario> cboFuncionario;
	@FXML
	private ComboBox<Servico> cboServico;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnAlterar;
	@FXML
	private Button btnDesmarcar;
	@FXML
	private Button btnPagar;
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
	protected void btnNovoHandler(ActionEvent event)
	{
		clearFields();
		enableFields();
		btnSalvar.setDisable(false);
		btnNovo.setDisable(true);
		btnAlterar.setDisable(true);
		btnDesmarcar.setDisable(true);
		btnPagar.setDisable(true);
		saveOption = 1;
	}
	
	@FXML
	protected void btnSalvarHandler(ActionEvent event)
	{
		disableFields();
		btnSalvar.setDisable(true);
		btnNovo.setDisable(false);
		btnAlterar.setDisable(false);
		btnDesmarcar.setDisable(false);
		btnPagar.setDisable(false);
		
		getValues();
		if(saveOption == 1)
		{
			if(validadeFields())
			{
				AgendamentoDAO.Create(agendamento.getPetId(), agendamento.getData(), agendamento.getHorario(), agendamento.getServicoId(), agendamento.getFuncionarioId(), agendamento.getPacoteId());
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
				AgendamentoDAO.Update(agendamento.getId(), agendamento.getPetId(), agendamento.getData(), agendamento.getHorario(), agendamento.getServicoId(), agendamento.getFuncionarioId(), agendamento.getPacoteId());
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
		pesquisar();
	}
	
	@FXML
	protected void btnAlterarHandler(ActionEvent event)
	{
		enableFields();
		btnSalvar.setDisable(false);
		btnNovo.setDisable(true);
		btnAlterar.setDisable(true);
		btnDesmarcar.setDisable(true);
		btnPagar.setDisable(true);
		saveOption = 2;
	}
	
	@FXML
	protected void btnDesmarcarHandler(ActionEvent event)
	{
		try
		{
			int id = Integer.parseInt(txtId.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Desmarcação");
			alert.setHeaderText("Confirmação");
			alert.setContentText("Deseja mesmo desmarcar?");
			alert.showAndWait();
			if(alert.getResult() == alert.getResult().OK)
			{
				AgendamentoDAO.Desmarcar(id);
				clearFields();
				UpdateORDelete();
			}
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Falha");
			alert.setHeaderText("Erro de Desmarcação");
			alert.setContentText("Não foi possível desmarcar o registro selecionado! \n\n Error: " + e.getMessage());
			alert.showAndWait();
		}
	}
	
	
	@FXML
	protected void btnPagarHandler(ActionEvent event)
	{
		content.getChildren().clear();
		try 
		{
			/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/consultapet.fxml"));
			Pane newLoadedPane = (Pane) loader.load();

			ConsultaPetController controller = loader.<ConsultaPetController>getController();
			controller.InitData(content, title);
			
			content.setPrefSize(780, 547);
			content.getChildren().add(newLoadedPane);
			title.setText("Consulta de Pets");*/
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interfaces/pagamento.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void dtDadosClickedHandler(MouseEvent event)
	{
		if(dtDados.getSelectionModel().getSelectedItem() != null)
		{
			Agendamento ag = dtDados.getSelectionModel().getSelectedItem();
			fillFields(ag);
			btnAlterar.setDisable(false);
			btnPagar.setDisable(false);
			btnDesmarcar.setDisable(false);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		populateComboBox();
		disableFields();
		btnSalvar.setDisable(true);
		UpdateORDelete();
		setTableColumns();
		pesquisar();
	}
	
	private void fillFields(Agendamento ag)
	{
			txtId.setText(String.valueOf(ag.getId()));
			
			txtHorario.setText(String.valueOf(ag.getHorario()));
			
			Date d = ag.getData();
			dtpData.setValue(d.toLocalDate());
			
			int petId = ag.getPetId();
			cboPet.getSelectionModel().select(Utils.ReadPet(petId));
			
			int funcId = ag.getFuncionarioId();
			cboFuncionario.getSelectionModel().select(Utils.ReadFuncionario(funcId));
			
			int servicoId = ag.getServicoId();
			cboServico.getSelectionModel().select(Utils.ReadServico(servicoId));
			
	}
	
	private void enableFields()
	{
		cboPet.setDisable(false);
		cboPet.setStyle("-fx-opacity: 1;");
		dtpData.setDisable(false);
		dtpData.setStyle("-fx-opacity: 1;");
		txtHorario.setDisable(false);
		txtHorario.setStyle("-fx-opacity: 1;");
		cboServico.setDisable(false);
		cboServico.setStyle("-fx-opacity: 1;");
		cboFuncionario.setDisable(false);
		cboFuncionario.setStyle("-fx-opacity: 1;");
	}
	
	private void disableFields()
	{
		txtId.setDisable(true);
		txtId.setStyle("-fx-opacity: 1;");
		cboPet.setDisable(true);
		cboPet.setStyle("-fx-opacity: 1;");
		dtpData.setDisable(true);
		dtpData.setStyle("-fx-opacity: 1;");
		txtHorario.setDisable(true);
		txtHorario.setStyle("-fx-opacity: 1;");
		cboServico.setDisable(true);
		cboServico.setStyle("-fx-opacity: 1;");
		cboFuncionario.setDisable(true);
		cboFuncionario.setStyle("-fx-opacity: 1;");
	}
	
	private void UpdateORDelete()
	{
		if (!txtId.getText().equals(""))
		{
			btnAlterar.setDisable(false);
			btnDesmarcar.setDisable(false);
		}
		else
		{
			btnAlterar.setDisable(true);
			btnDesmarcar.setDisable(true);
		}
	}
	
	private void populateComboBox()
	{
		ObservableList<Pet> optionspet = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.pets.size(); i++)
		{	
			optionspet.add(Utils.pets.get(i));
		}
		cboPet.setItems(optionspet);
		
		ObservableList<Funcionario> optionsfunc = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.funcionarios.size(); i++)
		{	
			optionsfunc.add(Utils.funcionarios.get(i));
		}
		cboFuncionario.setItems(optionsfunc);
		
		ObservableList<Servico> optionsservico = FXCollections.observableArrayList();
		for(int i = 0; i < Utils.servicos.size(); i++)
		{	
			optionsservico.add(Utils.servicos.get(i));
		}
		cboServico.setItems(optionsservico);
		
		UpdateORDelete();
		
	}

	private void clearFields()
	{
		txtId.setText("");
		txtHorario.setText("");
		cboPet.getSelectionModel().clearSelection();
		cboServico.getSelectionModel().clearSelection();
		cboFuncionario.getSelectionModel().clearSelection();
	}
	
	private boolean validadeFields()
	{
		boolean retorno = true;
		if(agendamento.getHorario() == null || agendamento.getHorario().equals(null))
			retorno = false;
		if(agendamento.getData() == null || agendamento.getData().equals(null))
			retorno = false;
		if(agendamento.getServicoId() <= 0)
			retorno = false;
		
		return retorno;
	}
	
	private void getValues()
	{
		if(txtId.getText() != "")
		{
			try 
			{
				agendamento.setId(Integer.parseInt(txtId.getText()));
			}
			catch(Exception e)
			{
				agendamento.setId(-1);
			}
		}
		if(cboPet.getSelectionModel().getSelectedIndex() != -1)
		{
			Pet pet = cboPet.getSelectionModel().getSelectedItem();
			agendamento.setPetId(pet.getId());
		}
		if(cboFuncionario.getSelectionModel().getSelectedIndex() != -1)
		{
			Funcionario func = cboFuncionario.getSelectionModel().getSelectedItem();
			agendamento.setFuncionarioId(func.getId());
		}
		else
		{
			agendamento.setFuncionarioId(-1);
		}
		if(cboServico.getSelectionModel().getSelectedIndex() != -1)
		{
			Servico servico = cboServico.getSelectionModel().getSelectedItem();
			agendamento.setServicoId(servico.getId());
		}
		if(dtpData.getValue() != null)
		{
			try
			{
				LocalDate d = dtpData.getValue();
				Date d2 = Date.valueOf(d);
				agendamento.setData(d2);
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Falha");
				alert.setHeaderText("Erro de Data");
				alert.setContentText("A data está no formato errado");
				alert.showAndWait();
			}
		}
		if(txtHorario.getText() != null)
		{
			try
			{
				LocalTime t = LocalTime.parse(txtHorario.getText());
				Time t2 = Time.valueOf(t);
				agendamento.setHorario(t2);
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Falha");
				alert.setHeaderText("Erro de Data");
				alert.setContentText("A data está no formato errado");
				alert.showAndWait();
			}
		}
		agendamento.setPacoteId(-1);
	}
	
	private void pesquisar()
	{
		dtDados.getItems().clear();
		
		ArrayList<Agendamento> agendamentos = AgendamentoDAO.ReadAtivo();
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

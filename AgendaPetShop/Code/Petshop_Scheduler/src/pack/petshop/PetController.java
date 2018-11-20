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
import pack.VO.Pet;

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
	private ComboBox<String> cboEspecie;
	@FXML
	private ComboBox<String> cboPelagem;
	@FXML
	private ComboBox<String> cboRaca;
	@FXML
	private ComboBox<String> cboPorte;
	@FXML
	private ComboBox<String> cboDonos;
	
	
	@FXML
	private Pane content;
	
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
		if(pet.getNome() != null)
		{
			txtNome.setText(pet.getNome());
		}
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
		ObservableList<String> options = FXCollections.observableArrayList(
		            "Option 1",
		            "Option 2",
		            "Option 3"
		);
		
		cboEspecie.setValue("oi");
		cboEspecie.setItems(options);
	}
}

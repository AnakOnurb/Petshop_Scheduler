package pack.petshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class PacoteController implements Initializable
{

	@FXML
	private Pane content;
	
	@FXML
	private Label title;
	
	public void InitData(Pane pnlContent, Label lblTitle) {
		content = pnlContent;	
		title = lblTitle;
	}
	
	@FXML
	protected void btnAddServicoHandler(ActionEvent event)
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
	protected void btnDesmarcarHandler(ActionEvent event)
	{
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
	}	
}

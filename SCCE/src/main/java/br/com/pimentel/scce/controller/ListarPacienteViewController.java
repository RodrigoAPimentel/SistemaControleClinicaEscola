package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.model.Paciente.PacienteSimpleProperty;
import br.com.pimentel.scce.service.PacienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Listar Pacientes
 */
public class ListarPacienteViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ListarPacienteViewController.class);

	@FXML private TableView<PacienteSimpleProperty> tableViewPaciente;
	@FXML private TableColumn<PacienteSimpleProperty, String> columnCodigo;
	@FXML private TableColumn<PacienteSimpleProperty, String> columnCPF;
	@FXML private TableColumn<PacienteSimpleProperty, String> columnNome;
	@FXML private JFXButton buttonSair;	
	
	private PacienteService pacienteService;
	
	private List<Paciente> listPaciente;
	private List<PacienteSimpleProperty> listPacienteSimpleProperty;
	private ObservableList<PacienteSimpleProperty> observableListPacienteSimpleProperty;
	
	@FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA LISTAR PACIENTE");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO LISTAR PACIENTE CONTROLLER");
		
		carregarAtributos();		
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		pacienteService = new PacienteService();
		
		listPaciente = new ArrayList<>();
		
		carregaTableView();
	}
	
	private void carregaTableView() {
		logger.info("+++ CARREGANDO TABLE VIEW");
		
		listPacienteSimpleProperty = new ArrayList<>();
		listPaciente = pacienteService.buscarTodos();
		
		for (Paciente paciente : listPaciente) {
			listPacienteSimpleProperty.add(paciente.new PacienteSimpleProperty(paciente));
		}		
			
		columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		
		observableListPacienteSimpleProperty = FXCollections.observableArrayList(listPacienteSimpleProperty);
		
		tableViewPaciente.setItems(observableListPacienteSimpleProperty);		
	}
}

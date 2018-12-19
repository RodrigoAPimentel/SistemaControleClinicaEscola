package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.Agendamento.AgendamentoSimpleProperty;
import br.com.pimentel.scce.service.AgendamentoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Listar Agendamentos
 */
public class ListarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ListarAgendamentoViewController.class);

	@FXML private TableView<AgendamentoSimpleProperty> tableViewAgendamento;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnCodigo;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnNomePaciente;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnAreaMedica;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnDataConsulta;
	@FXML private JFXButton buttonSair;	
	
	private AgendamentoService agendamentoService;
	
	private List<Agendamento> listAgendamento;
	private List<AgendamentoSimpleProperty> listAgendamentoSimpleProperty;
	private ObservableList<AgendamentoSimpleProperty> observableListAgendamentoSimpleProperty;
	
	@FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA LISTAR AGENDAMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO LISTAR AGENDAMENTO CONTROLLER");
		
		carregarAtributos();		
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		agendamentoService = new AgendamentoService();
		
		listAgendamento = new ArrayList<>();
		
		carregaTableView();
	}
	
	private void carregaTableView() {
		logger.info("+++ CARREGANDO TABLE VIEW");
		
		listAgendamentoSimpleProperty = new ArrayList<>();
		listAgendamento = agendamentoService.buscarTodos();
		
		for (Agendamento agendamento : listAgendamento) {
			listAgendamentoSimpleProperty.add(agendamento.new AgendamentoSimpleProperty(agendamento));
		}		
			
		columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoAgendamento"));
		columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
		columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
		
		observableListAgendamentoSimpleProperty = FXCollections.observableArrayList(listAgendamentoSimpleProperty);
		
		tableViewAgendamento.setItems(observableListAgendamentoSimpleProperty);		
	}
}

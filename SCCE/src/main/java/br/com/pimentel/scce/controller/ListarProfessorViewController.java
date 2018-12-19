package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;

import br.com.pimentel.scce.model.Professor;
import br.com.pimentel.scce.model.Professor.ProfessorSimpleProperty;
import br.com.pimentel.scce.service.ProfessorService;
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
 * Classe de Controller de Listar Professores
 */
public class ListarProfessorViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ListarProfessorViewController.class);

	@FXML private TableView<ProfessorSimpleProperty> tableViewProfessor;
	@FXML private TableColumn<ProfessorSimpleProperty, String> columnCodigo;
	@FXML private TableColumn<ProfessorSimpleProperty, String> columnNome;
	@FXML private TableColumn<ProfessorSimpleProperty, String> columnCurso;
	@FXML private JFXButton buttonSair;	
	
	private ProfessorService professorService;
	
	private List<Professor> listProfessor;
	private List<ProfessorSimpleProperty> listProfessorSimpleProperty;
	private ObservableList<ProfessorSimpleProperty> observableListProfessorSimpleProperty;
	
	@FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA LISTAR PROFESSOR");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO LISTAR PROFESSOR CONTROLLER");
		
		carregarAtributos();		
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		professorService = new ProfessorService();
		
		listProfessor = new ArrayList<>();
		
		carregaTableView();
	}
	
	private void carregaTableView() {
		logger.info("+++ CARREGANDO TABLE VIEW");
		
		listProfessorSimpleProperty = new ArrayList<>();
		listProfessor = professorService.buscarTodos();
		
		for (Professor professor : listProfessor) {
			listProfessorSimpleProperty.add(professor.new ProfessorSimpleProperty(professor));
		}		
			
		columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		
		observableListProfessorSimpleProperty = FXCollections.observableArrayList(listProfessorSimpleProperty);
		
		tableViewProfessor.setItems(observableListProfessorSimpleProperty);		
	}
}

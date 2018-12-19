package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;

import br.com.pimentel.scce.model.Aluno;
import br.com.pimentel.scce.model.Aluno.AlunoSimpleProperty;
import br.com.pimentel.scce.service.AlunoService;
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
 * Classe de Controller de Listar Alunos
 */
public class ListarAlunoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ListarAlunoViewController.class);

	@FXML private TableView<AlunoSimpleProperty> tableViewAluno;
	@FXML private TableColumn<AlunoSimpleProperty, String> columnMatricula;
	@FXML private TableColumn<AlunoSimpleProperty, String> columnNome;
	@FXML private TableColumn<AlunoSimpleProperty, String> columnCurso;
	@FXML private JFXButton buttonSair;	
	
	private AlunoService alunoService;
	
	private List<Aluno> listAluno;
	private List<AlunoSimpleProperty> listAlunoSimpleProperty;
	private ObservableList<AlunoSimpleProperty> observableListAlunoSimpleProperty;
	
	@FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA LISTAR ALUNO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO LISTAR ALUNO CONTROLLER");
		
		carregarAtributos();		
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		alunoService = new AlunoService();
		
		listAluno = new ArrayList<>();
		
		carregaTableView();
	}
	
	private void carregaTableView() {
		logger.info("+++ CARREGANDO TABLE VIEW");
		
		listAlunoSimpleProperty = new ArrayList<>();
		listAluno = alunoService.buscarTodos();
		
		for (Aluno aluno : listAluno) {
			listAlunoSimpleProperty.add(aluno.new AlunoSimpleProperty(aluno));
		}		
			
		columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		
		observableListAlunoSimpleProperty = FXCollections.observableArrayList(listAlunoSimpleProperty);
		
		tableViewAluno.setItems(observableListAlunoSimpleProperty);		
	}
}

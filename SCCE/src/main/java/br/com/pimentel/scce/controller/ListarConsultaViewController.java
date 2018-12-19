package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import br.com.pimentel.scce.model.Aluno;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Consulta;
import br.com.pimentel.scce.model.Consulta.ConsultaSimpleProperty;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.model.Professor;
import br.com.pimentel.scce.service.AlunoService;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.ConsultaService;
import br.com.pimentel.scce.service.PacienteService;
import br.com.pimentel.scce.service.ProfessorService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Listar Consultas
 */
public class ListarConsultaViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ListarConsultaViewController.class);

	@FXML private TableView<ConsultaSimpleProperty> tableViewConsulta;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnCodigo;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnNomePaciente;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnAreaMedica;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnDataConsulta;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnAluno;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnProfessor;
	@FXML private JFXButton buttonSair;	
	@FXML private JFXRadioButton radioButtonTodos;
	@FXML private JFXRadioButton radioButtonAreaMedica;
	@FXML private JFXRadioButton radioButtonAluno;
	@FXML private JFXRadioButton radioButtonPaciente;
	@FXML private JFXRadioButton radioButtonProfessor;
	private ToggleGroup radioGroup = new ToggleGroup();
	@FXML private JFXComboBox<String> comboBoxPesquisar;
	
	private ConsultaService consultaService;
	private AreaMedicaService areaMedicaService;
	private PacienteService pacienteService;
	private AlunoService alunoService;
	private ProfessorService professorService;
	
	private List<Consulta> listConsulta;
	private ObservableList<ConsultaSimpleProperty> observableListConsultaSimpleProperty;	
	
	@FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA LISTAR CONSULTA");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO LISTAR CONSULTA CONTROLLER");
		
		carregarAtributos();		
		carregaTableView();
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		consultaService = new ConsultaService();
		areaMedicaService = new AreaMedicaService();
		pacienteService = new PacienteService();
		alunoService = new AlunoService();
		professorService = new ProfessorService();
		
		listConsulta = new ArrayList<>();
		
		observableListConsultaSimpleProperty = FXCollections.observableArrayList();
		
		radioButtonAluno.setToggleGroup(radioGroup);
		radioButtonAreaMedica.setToggleGroup(radioGroup);
		radioButtonPaciente.setToggleGroup(radioGroup);
		radioButtonTodos.setToggleGroup(radioGroup);
		radioButtonProfessor.setToggleGroup(radioGroup);
		
		comboBoxPesquisar.setDisable(true);
	}
	
	private void carregaTableView() {		
		logger.info("+++ CARREGANDO TABLE VIEW");
		
		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				
				comboBoxPesquisar.setDisable(true);
				if (!observableListConsultaSimpleProperty.isEmpty()) {
					observableListConsultaSimpleProperty.clear();
					logger.info("+++ LIMPANDO TABLE VIEW");
				}				
				
				if (new_toggle == radioButtonTodos) {					
					logger.info("+++ ESCOLHIDO O RADIO BUTTON TODOS");
					
					listConsulta = consultaService.buscarTodos();
					
					for (Consulta consulta : listConsulta) {
						observableListConsultaSimpleProperty.add(consulta.new ConsultaSimpleProperty(consulta));
					}		
						
					columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
					columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
					columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
					columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
					columnAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
					columnProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
					
					tableViewConsulta.setItems(observableListConsultaSimpleProperty);
					
				} else if (new_toggle == radioButtonAreaMedica) {
					logger.info("+++ ESCOLHIDO RADIO BUTTON AREA MEDICA");
					
					comboBoxPesquisar.setDisable(false);
					
					ObservableList<String> observableListAreaMedica = FXCollections.observableArrayList();
					List<AreaMedica> listAreaMedica = areaMedicaService.buscarTodos();
					for (AreaMedica areaMedica : listAreaMedica) {
						observableListAreaMedica.add(areaMedica.getNomeAreaMedica());
					}
					comboBoxPesquisar.setItems(observableListAreaMedica);
					new AutoCompleteComboBoxListener<>(comboBoxPesquisar);
					
					comboBoxPesquisar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					    	
					    	if (!observableListConsultaSimpleProperty.isEmpty()) {
								observableListConsultaSimpleProperty.clear();
								logger.info("+++ LIMPANDO TABLE VIEW");
							}
					    	
					    	listConsulta = consultaService.buscarTodosPorAreaMedica(areaMedicaService.buscarPorDescricao(newValue));
							
							for (Consulta consulta : listConsulta) {
								observableListConsultaSimpleProperty.add(consulta.new ConsultaSimpleProperty(consulta));
							}		
								
							columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
							columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
							columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
							columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
							columnAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
							columnProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
					    	
					    	switch (newValue) {
								case "Fisioterapia":
									logger.info("+++ ESCOLHIDO A AREA MEDICA [FISIOTERAIPA]");
									
									tableViewConsulta.setItems(observableListConsultaSimpleProperty);
									break;
								case "Odontologia":
									logger.info("+++ ESCOLHIDO A AREA MEDICA [ODONTOLOGIA]");
									
									tableViewConsulta.setItems(observableListConsultaSimpleProperty);
									break;
								case "Psicologia":
									logger.info("+++ ESCOLHIDO A AREA MEDICA [PSICOLOGIA]");
									
									tableViewConsulta.setItems(observableListConsultaSimpleProperty);
									break;
							}
					    }
					});
					
				} else if (new_toggle == radioButtonPaciente) {
					logger.info("+++ ESCOLHIDO RADIO BUTTON PACIENTE");
					
					comboBoxPesquisar.setDisable(false);
					
					ObservableList<String> observableListPaciente = FXCollections.observableArrayList();
					List<Paciente> listPaciente = pacienteService.buscarTodos();
					for (Paciente paciente : listPaciente) {
						observableListPaciente.add(paciente.getNome());
					}
					comboBoxPesquisar.setItems(observableListPaciente);
					new AutoCompleteComboBoxListener<>(comboBoxPesquisar);
					
					comboBoxPesquisar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					    	
					    	if (!observableListConsultaSimpleProperty.isEmpty()) {
								observableListConsultaSimpleProperty.clear();
								logger.info("+++ LIMPANDO TABLE VIEW");
							}
					    	
					    	listConsulta = consultaService.buscarTodosPorPaciente(pacienteService.buscarPorNome(newValue));
							
							for (Consulta consulta : listConsulta) {
								observableListConsultaSimpleProperty.add(consulta.new ConsultaSimpleProperty(consulta));
							}		
								
							columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
							columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
							columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
							columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
							columnAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
							columnProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
							
							tableViewConsulta.setItems(observableListConsultaSimpleProperty);
					    }
					});
				} else if (new_toggle == radioButtonAluno) {
					logger.info("+++ ESCOLHIDO RADIO ALUNO");
					
					comboBoxPesquisar.setDisable(false);
					
					ObservableList<String> observableListAluno = FXCollections.observableArrayList();
					List<Aluno> listAluno = alunoService.buscarTodos();
					for (Aluno aluno : listAluno) {
						observableListAluno.add(aluno.getNome());
					}
					comboBoxPesquisar.setItems(observableListAluno);
					new AutoCompleteComboBoxListener<>(comboBoxPesquisar);
					
					comboBoxPesquisar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					    	
					    	if (!observableListConsultaSimpleProperty.isEmpty()) {
								observableListConsultaSimpleProperty.clear();
								logger.info("+++ LIMPANDO TABLE VIEW");
							}
					    	
					    	listConsulta = consultaService.buscarTodosPorAluno(alunoService.buscarPorNome(newValue));
							
							for (Consulta consulta : listConsulta) {
								observableListConsultaSimpleProperty.add(consulta.new ConsultaSimpleProperty(consulta));
							}		
								
							columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
							columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
							columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
							columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
							columnAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
							columnProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
							
							tableViewConsulta.setItems(observableListConsultaSimpleProperty);
					    }
					});
				} else if (new_toggle == radioButtonProfessor) {
					logger.info("+++ ESCOLHIDO RADIO PROFESSOR");
					
					comboBoxPesquisar.setDisable(false);
					
					ObservableList<String> observableListProfessor = FXCollections.observableArrayList();
					List<Professor> listProfessor = professorService.buscarTodos();
					for (Professor professor : listProfessor) {
						observableListProfessor.add(professor.getNome());
					}
					comboBoxPesquisar.setItems(observableListProfessor);
					new AutoCompleteComboBoxListener<>(comboBoxPesquisar);
					
					comboBoxPesquisar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					    	
					    	if (!observableListConsultaSimpleProperty.isEmpty()) {
								observableListConsultaSimpleProperty.clear();
								logger.info("+++ LIMPANDO TABLE VIEW");
							}
					    	
					    	listConsulta = consultaService.buscarTodosPorProfessor(professorService.buscarPorNome(newValue));
							
							for (Consulta consulta : listConsulta) {
								observableListConsultaSimpleProperty.add(consulta.new ConsultaSimpleProperty(consulta));
							}		
								
							columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
							columnNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
							columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
							columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
							columnAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
							columnProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
							
							tableViewConsulta.setItems(observableListConsultaSimpleProperty);
					    }
					});
				} 				
			}
		});
	}
}

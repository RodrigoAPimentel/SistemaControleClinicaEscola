package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.pimentel.scce.model.Aluno;
import br.com.pimentel.scce.model.Consulta;
import br.com.pimentel.scce.model.Professor;
import br.com.pimentel.scce.service.AlunoService;
import br.com.pimentel.scce.service.ConsultaService;
import br.com.pimentel.scce.service.ProfessorService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Confirmar Agendamento
 */
public class ConfirmarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(ConfirmarAgendamentoViewController.class);
	
	private MainViewController mainViewController;

	private AlunoService alunoService;
	private ProfessorService professorService;
	
	private Consulta consultaNova;
	private ConsultaService consultaService;

	@FXML private Label labelCodigoAgendamento;
	@FXML private Label labelNomePaciente;
	@FXML private Label labelAreaMedica;
	@FXML private JFXComboBox<String> comboBoxProfessor;
	@FXML private JFXComboBox<String> comboBoxAluno;
	@FXML private JFXButton buttonConfirmar;
	@FXML private JFXButton buttonSair;
	
	private List<Aluno> listAlunos;
	private List<String> listNomeAlunos;
	private ObservableList<String> observablelistAlunos;
	private Aluno alunoSelecionado;
	
	private List<Professor> listProfessores;
	private List<String> listNomeProfessores;
	private ObservableList<String> observablelistProfessores;
	private Professor professorSelecionado;

	private Alert dialogoAviso;
	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneAviso;
	private DialogPane dialogPaneConfirmacao;
		
	@FXML private void clickedButtonConfirmar(MouseEvent event) throws Exception {
		logger.info("+++ TRANSFORMANDO AGENDAMENTO EM CONSULTA");		
		
		if (!comboBoxAluno.getValue().equals("") && !comboBoxProfessor.getValue().equals("")) {
			alunoSelecionado = alunoService.buscarPorNome(comboBoxAluno.getValue());
			professorSelecionado = professorService.buscarPorNome(comboBoxProfessor.getValue());
			
			consultaNova = new Consulta(mainViewController.getAgendamentoSelecionado(), alunoSelecionado, professorSelecionado);
			consultaService.salvar(consultaNova);			
			
			mainViewController.atualizaTabelas(LocalDate.now());
			
			Stage stage = (Stage) buttonSair.getScene().getWindow();
			stage.close();
			
			dialogoConfirmacao.setHeaderText("CONSULTA MARCADA");
			dialogoConfirmacao.showAndWait();
		}else {		
			dialogoAviso.setHeaderText("ESCOLHA O ALUNO(INSTRUENDO) E O PROFESSOR(SUPERVISOR) PARA A CONSULTA");
	        dialogoAviso.setContentText("Você precisa escolher um Aluno(Instruendo) e um Professor(Supervisor) para que a consulta seja marcada.");
	        dialogoAviso.showAndWait();
		}
	}

	@FXML private void clickedButtonSair(MouseEvent event) {
		logger.info("+++ FECHANDO JANELA CONFIRMAR AGENDAMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
	}
	
	private void carregaLabels() {
		logger.info("+++ CARREGANDO INFORMAÇÕES DE AGENDAMENTO");
		
		labelAreaMedica.setText(mainViewController.getAgendamentoSelecionado().getAreaMedica().getNomeAreaMedica());
		labelCodigoAgendamento.setText(String.valueOf(mainViewController.getAgendamentoSelecionado().getCodigoAgendamento()));
		labelNomePaciente.setText(mainViewController.getAgendamentoSelecionado().getPaciente().getNome());
		
		listAlunos = alunoService.buscarTodosPorAreaMedica(mainViewController.getAgendamentoSelecionado().getAreaMedica().getCodigoAreaMedica());
		listNomeAlunos = new ArrayList<>();
		
		for (Aluno aluno : listAlunos) {
			listNomeAlunos.add(aluno.getNome());
		}
		
		observablelistAlunos = FXCollections.observableArrayList(listNomeAlunos);
		
		comboBoxAluno.setItems(observablelistAlunos);		
		new AutoCompleteComboBoxListener<>(comboBoxAluno);
		
		listProfessores = professorService.buscarTodosPorAreaMedica(mainViewController.getAgendamentoSelecionado().getAreaMedica().getCodigoAreaMedica());
		listNomeProfessores = new ArrayList<>();
		
		for (Professor profesor : listProfessores) {
			listNomeProfessores.add(profesor.getNome());
		}
		
		observablelistProfessores = FXCollections.observableArrayList(listNomeProfessores);
		
		comboBoxProfessor.setItems(observablelistProfessores);
		new AutoCompleteComboBoxListener<>(comboBoxProfessor);
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		mainViewController = MainViewController.getInstanceMainViewController();
		
		alunoService = new AlunoService();
		professorService = new ProfessorService();
		consultaService = new ConsultaService();
		
		dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.initStyle(StageStyle.TRANSPARENT);		
		dialogPaneAviso = dialogoAviso.getDialogPane();
		dialogPaneAviso.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
		dialogPaneAviso.getStyleClass().add("myDialogWarning");
		
		dialogoConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
		dialogoConfirmacao.initStyle(StageStyle.TRANSPARENT);		
		dialogPaneConfirmacao = dialogoConfirmacao.getDialogPane();
		dialogPaneConfirmacao.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
		dialogPaneConfirmacao.getStyleClass().add("myDialogConfirmation");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO CONFIRMAR AGENDAMENTO CONTROLLER");
		
		carregarAtributos();
		carregaLabels();
	}
	
}

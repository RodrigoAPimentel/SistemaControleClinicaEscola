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
import com.jfoenix.controls.JFXDatePicker;

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.model.SituacaoAgendamento;
import br.com.pimentel.scce.model.StatusAgendamento;
import br.com.pimentel.scce.service.AgendamentoService;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.PacienteService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Cadastrar Agendamento
 */
public class CadastrarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(CadastrarAgendamentoViewController.class);
	
	private MainViewController mainViewController;

	@FXML JFXComboBox<String> comboBoxPaciente;
	@FXML JFXComboBox<String> comboBoxAreaMedica;
	@FXML JFXButton buttonCadastrar;
	@FXML JFXButton buttonSair;
	@FXML JFXDatePicker datePickerDataConsulta;
	
	private AreaMedicaService areaMedicaService;    
    private List<AreaMedica> listAreaMedica;
    private List<String> listAreaMedicaDescricao;
    private ObservableList<String> observableListAreaMedicaDescricao;
    
    private PacienteService pacienteService;
    private List<Paciente> listPaciente;
    private List<String> listPacienteNome;
    private ObservableList<String> observableListPacienteNome;
    
    private Agendamento agendamento;
    private AgendamentoService agendamentoService;

    private DialogPane dialogPaneAviso;
   	private Alert dialogoAviso;
   	private DialogPane dialogPaneConfirmacao;
   	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneError;
	private Alert dialogoError;

    @FXML void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ CADASTRANDO AGENDAMENTO");    	
    	
    	if (comboBoxAreaMedica.getValue() != null && datePickerDataConsulta.getValue() != null && !comboBoxPaciente.getValue().equals("") ) {
    		
			if (pacienteService.verificaLimiteAgendamentos(pacienteService.buscarPorNome(comboBoxPaciente.getValue()))) {
				
				if (pacienteService.verificaLimiteAreaMedica(pacienteService.buscarPorNome(comboBoxPaciente.getValue()), 
					areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue()))) {
					
					agendamento = new Agendamento(pacienteService.buscarPorNome(comboBoxPaciente.getValue()),
							areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue()), LocalDate.now(),
							datePickerDataConsulta.getValue(),
							new SituacaoAgendamento(StatusAgendamento.AGENDADO, null, null));
					try {
						agendamentoService.salvar(agendamento);
						
						mainViewController.atualizaTabelas(LocalDate.now());
					} catch (Exception e) {
						logger.info(e.getMessage());
					}

					Stage stage = (Stage) buttonSair.getScene().getWindow();
					stage.close();

					dialogoConfirmacao.setHeaderText("AGENDMENTO CADASTRADO");
					dialogoConfirmacao.setContentText("Consulta do(a) Sr(a). \"" + comboBoxPaciente.getValue() + "\" foi Agendada com Sucesso");
					dialogoConfirmacao.showAndWait();
				} else {
					logger.error("+++ PACIENTE DE CPF: [{}] JA POSSUI AGENDAMENTOS EM 2 AREAS MEDICAS EM MENOS DE 6 MESES", pacienteService.buscarPorNome(comboBoxPaciente.getValue()).getCpf());
	    			
	    			dialogoError.setHeaderText("PACIENTE JA POSSUI AGENDAMENTOS EM 2 AREAS MEDICAS");
	    			dialogoError.setContentText("O paciente \"" + pacienteService.buscarPorNome(comboBoxPaciente.getValue()).getNome() + "\" ja possui agendamento em 2 Areas Medicas em menos de 6 meses."
	    										+ "\nPor determinação da Universidade o maximo de Areas Medicas simultaneas é 2 em um periodo de 6 meses.");
	    			dialogoError.showAndWait();
				}	
				
			} else {
				logger.error("+++ PACIENTE DE CPF: [{}] JA POSSUI 8 AGENDAMENTOS SIMULTANEOS", pacienteService.buscarPorNome(comboBoxPaciente.getValue()).getCpf());
    			
    			dialogoError.setHeaderText("PACIENTE JA POSSUI 8 AGENDAMENTOS SIMULTANEOS");
    			dialogoError.setContentText("O paciente \"" + pacienteService.buscarPorNome(comboBoxPaciente.getValue()).getNome() + "\" ja possui 8 Agendamentos de Consulta Simultaneos."
    					+ "\nPor determinação da Universidade o maximo de Agendamentos simultaneos é 8.");
    			dialogoError.showAndWait();
			}    
			
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para Agendar uma Consulta.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA CADASTRAR AGENDAMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ADICIONAR AGENDAMENTO CONTROLLER");
		
		carregarAtributos();
		carregarComboBoxs();		
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		mainViewController = MainViewController.getInstanceMainViewController();
		
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
		
		dialogoError = new Alert(Alert.AlertType.ERROR);
		dialogoError.initStyle(StageStyle.TRANSPARENT);		
		dialogPaneError = dialogoError.getDialogPane();
		dialogPaneError.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
		dialogPaneError.getStyleClass().add("myDialogError");
		
		listAreaMedica = new ArrayList<>();
		listAreaMedicaDescricao = new ArrayList<>();
		areaMedicaService = new AreaMedicaService();
		
		pacienteService = new PacienteService();
		listPaciente = new ArrayList<>();
		listPacienteNome = new ArrayList<>();
		
		agendamentoService = new AgendamentoService();
	}
	
	private void carregarComboBoxs() {
		logger.info("+++ CARREGANDO COMBOBOXS");
		
		listAreaMedica = areaMedicaService.buscarTodos();		
		for (AreaMedica areaMedica : listAreaMedica) {
			listAreaMedicaDescricao.add(areaMedica.getNomeAreaMedica());
		}				
		observableListAreaMedicaDescricao = FXCollections.observableArrayList(listAreaMedicaDescricao);
		comboBoxAreaMedica.setItems(observableListAreaMedicaDescricao);
		new AutoCompleteComboBoxListener<>(comboBoxAreaMedica);
		
		listPaciente = pacienteService.buscarTodos();
		for (Paciente paciente : listPaciente) {
			listPacienteNome.add(paciente.getNome());
		}
		observableListPacienteNome = FXCollections.observableArrayList(listPacienteNome);
		comboBoxPaciente.setItems(observableListPacienteNome);		
		new AutoCompleteComboBoxListener<>(comboBoxPaciente);
	}
	
}

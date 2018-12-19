package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.Agendamento.AgendamentoSimpleProperty;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.service.AgendamentoService;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.PacienteService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Alterar Agendamento
 */
public class AlterarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(AlterarAgendamentoViewController.class);
	
	private MainViewController mainViewController;

	@FXML private JFXComboBox<String> comboBoxAreaMedica;
	@FXML private JFXButton buttonCadastrar;
	@FXML private JFXButton buttonSair;
	@FXML private JFXButton buttonExcluir;
	@FXML private JFXDatePicker datePickerDataConsulta;
	
	@FXML private JFXComboBox<String> comboBoxPesquisarPorCPF;
	@FXML private Label labelPaciente;
	@FXML private TableView<AgendamentoSimpleProperty> tableViewAgendamentos;
	@FXML private TableColumn<AgendamentoSimpleProperty, Integer> columnCodigo;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnAreaMedica;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnDataConsulta;
	
	private AreaMedicaService areaMedicaService;    
    private List<AreaMedica> listAreaMedica;
    private List<String> listAreaMedicaDescricao;
    private ObservableList<String> observableListAreaMedicaDescricao;
    
    private PacienteService pacienteService;
    
    private Agendamento agendamento;
    private AgendamentoService agendamentoService;

    private DialogPane dialogPaneAviso;
   	private Alert dialogoAviso;
   	private DialogPane dialogPaneConfirmacao;
   	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneError;
	private Alert dialogoError;

	private List<Paciente> listPacienteCarregarCampos;
	private List<String> listPacienteCPFCarregarCampos;
	private ObservableList<String> observableListPacienteCPF;

	private Paciente pacienteSelecionado;
	private List<AgendamentoSimpleProperty> listAgendamentosAux;
	private List<Agendamento> listAgendamentosOriginais;
	private ObservableList<AgendamentoSimpleProperty> observableAgendamentosAux;

	private AgendamentoSimpleProperty agendamentoAuxSelecionado;
	private Agendamento agendamentoSelecionado;

    @FXML void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ ALTERANDO AGENDAMENTO");    	
    	
    	if (comboBoxAreaMedica.getValue() != null && datePickerDataConsulta.getValue() != null) {
    		
			if (pacienteService.verificaLimiteAgendamentos(pacienteService.buscarPorNome(pacienteSelecionado.getNome()))) {
				
				if (pacienteService.verificaLimiteAreaMedica(pacienteService.buscarPorNome(pacienteSelecionado.getNome()), 
					areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue()))) {
					
					agendamento = agendamentoSelecionado;
					agendamento.setAreaMedica(areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue()));
					agendamento.setDataAgendamento(datePickerDataConsulta.getValue());
					try {
						agendamentoService.atualizar(agendamento);
						
						mainViewController.atualizaTabelas(LocalDate.now());
					} catch (Exception e) {
						logger.info(e.getMessage());
					}

					Stage stage = (Stage) buttonCadastrar.getScene().getWindow();
					stage.close();

					dialogoConfirmacao.setHeaderText("AGENDAMENTO ALTERADO");
					dialogoConfirmacao.setContentText("Consulta do(a) Sr(a). \"" + pacienteSelecionado.getNome() + "\" foi Alterada com Sucesso");
					dialogoConfirmacao.showAndWait();
				} else {
					logger.error("+++ PACIENTE DE CPF: [{}] JA POSSUI AGENDAMENTOS EM 2 AREAS MEDICAS EM MENOS DE 6 MESES", pacienteSelecionado.getCpf());
	    			
	    			dialogoError.setHeaderText("PACIENTE JA POSSUI AGENDAMENTOS EM 2 AREAS MEDICAS");
	    			dialogoError.setContentText("O paciente \"" + pacienteSelecionado.getNome() + "\" ja possui agendamento em 2 Areas Medicas em menos de 6 meses."
	    										+ "\nPor determinação da Universidade o maximo de Areas Medicas simultaneas é 2 em um periodo de 6 meses.");
	    			dialogoError.showAndWait();
				}	
				
			} else {
				logger.error("+++ PACIENTE DE CPF: [{}] JA POSSUI 8 AGENDAMENTOS SIMULTANEOS", pacienteSelecionado.getCpf());
    			
    			dialogoError.setHeaderText("PACIENTE JA POSSUI 8 AGENDAMENTOS SIMULTANEOS");
    			dialogoError.setContentText("O paciente \"" + pacienteSelecionado.getNome() + "\" ja possui 8 Agendamentos de Consulta Simultaneos."
    					+ "\nPor determinação da Universidade o maximo de Agendamentos simultaneos é 8.");
    			dialogoError.showAndWait();
			}    
			
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para alterar um Agendamento.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA ALTERAR AGENDAMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
    
    @FXML private void clickedExcluir(MouseEvent event) {    	
    	if (agendamentoSelecionado != null) {
	        dialogoAviso.setHeaderText("EXCLUINDO AGENDAMENTO");
	        dialogoAviso.setContentText("Você tem certeza que deseja EXCLUIR o Agendamento \"" + agendamentoSelecionado.getCodigoAgendamento() 
	        							+"\" do Sr(a). \"" + agendamentoSelecionado.getPaciente().getNome() + "\" de CPF \"" 
	        							+ agendamentoSelecionado.getPaciente().getCpf() + "\"");
	        
	        ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
	        ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
	        dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
	        
	        Optional<ButtonType> result = dialogoAviso.showAndWait();    		
	        if (result.get() == buttonTypeOk){
	        	logger.info("+++ EXLUINDO AGENDAMENTO [{}]", agendamentoSelecionado.getCodigoAgendamento());  
	        	
	        	try {
	    			agendamentoService.excluir(agendamentoSelecionado);
	    			
	    			mainViewController.atualizaTabelas(LocalDate.now());
	    			
	    			Stage stage = (Stage) buttonExcluir.getScene().getWindow();
	    			stage.close();
	    			
	    			dialogoConfirmacao.setHeaderText("AGENDAMENTO EXCLUIDO");
	    			dialogoConfirmacao.setContentText("O Agendamento \"" + agendamentoSelecionado.getCodigoAgendamento() 
														+"\" do Sr(a). \"" + agendamentoSelecionado.getPaciente().getNome() + "\" de CPF \"" 
														+ agendamentoSelecionado.getPaciente().getCpf() + "\" foi EXCLUIDO com Sucesso");
	    			dialogoConfirmacao.showAndWait();
	    		} catch (Exception e) {
	    			logger.error(e.getMessage());
	    		}
	        } 
		} else {
			dialogoAviso.setHeaderText("AGENDAMENTO NÃO SELECIONADO");
	        dialogoAviso.setContentText("Você precisa selecionar um Agendamento para Ecluir.");
	        dialogoAviso.showAndWait();
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ALTERAR AGENDAMENTO CONTROLLER");
		
		carregarAtributos();
		carregarComboBoxs();	
		carregarCampos();
		selecaoTableView();
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
		
		agendamentoService = new AgendamentoService();
		
		listPacienteCarregarCampos = new ArrayList<>();
		listPacienteCPFCarregarCampos = new ArrayList<>();
		
		listAgendamentosAux = new ArrayList<>();
		
		agendamentoSelecionado = new Agendamento();
		
		comboBoxAreaMedica.setDisable(true);
		datePickerDataConsulta.setDisable(true);
		
		buttonExcluir.setDisable(true);
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
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	private void carregarCampos() {
		logger.info("+++ CARREGANDO CAMPOS PARA EDIÇÃO");
		
		listPacienteCarregarCampos = pacienteService.buscarTodos();
		for (Paciente paciente : listPacienteCarregarCampos) {
			listPacienteCPFCarregarCampos.add(paciente.getCpf());
		}
		observableListPacienteCPF = FXCollections.observableArrayList(listPacienteCPFCarregarCampos);
		comboBoxPesquisarPorCPF.setItems(observableListPacienteCPF);
		new AutoCompleteComboBoxListener<>(comboBoxPesquisarPorCPF);		
		
		comboBoxPesquisarPorCPF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (comboBoxPesquisarPorCPF.getSelectionModel().getSelectedItem() != null) {
					
					pacienteSelecionado = pacienteService.buscarPorCPF(comboBoxPesquisarPorCPF.getValue());
					
					labelPaciente.setText(pacienteSelecionado.getNome());
					
					logger.info("+++ CARREGANDO TABLE VIEW AGENDAMENTO COM OS AGENDAMENTOS DO PACIENTE: {}", pacienteSelecionado.getCodigoPaciente());
										
					listAgendamentosOriginais = pacienteService.buscarAgendamentosAbertos(pacienteSelecionado);
					
					listAgendamentosAux.clear();
					
					for (Agendamento agendamento : listAgendamentosOriginais) {
						listAgendamentosAux.add(agendamento.new AgendamentoSimpleProperty(agendamento));
					}		
						
					columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoAgendamento"));
					columnAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
					columnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
					
					observableAgendamentosAux = FXCollections.observableArrayList(listAgendamentosAux);
					
					tableViewAgendamentos.setItems(observableAgendamentosAux);
				} 
            }
        });			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void selecaoTableView() {
		logger.info("+++ INICIANDO METODO SELECAOTABLEVIEW");

		tableViewAgendamentos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (tableViewAgendamentos.getSelectionModel().getSelectedItem() != null) {
					agendamentoAuxSelecionado = tableViewAgendamentos.getSelectionModel().getSelectedItem();
					agendamentoSelecionado = agendamentoAuxSelecionado.getAgendamentoOriginal();
					
					comboBoxAreaMedica.setDisable(false);
					datePickerDataConsulta.setDisable(false);
					
					buttonExcluir.setDisable(false);
					
					comboBoxAreaMedica.setValue(agendamentoSelecionado.getAreaMedica().getNomeAreaMedica());
					datePickerDataConsulta.setValue(agendamentoSelecionado.getDataConsulta());					
					
					logger.info("+++ SELECIONADO O AGENDAMENTO [{}] NA TABLE VIEW AGENDAMENTO", agendamentoAuxSelecionado.getCodigoAgendamento());
				}else {					
					agendamentoSelecionado = null;		
					
					comboBoxAreaMedica.setDisable(true);
					datePickerDataConsulta.setDisable(true);
					
					comboBoxAreaMedica.setValue("");
					datePickerDataConsulta.setValue(LocalDate.EPOCH);
					
					buttonExcluir.setDisable(true);
				}
			}
		});
	}
	
}

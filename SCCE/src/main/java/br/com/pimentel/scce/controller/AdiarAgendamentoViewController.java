package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.SituacaoAgendamento;
import br.com.pimentel.scce.model.StatusAgendamento;
import br.com.pimentel.scce.service.AgendamentoService;
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
 * Classe de Controller de Adiar Agendamento
 */
public class AdiarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(AdiarAgendamentoViewController.class);
	
	private MainViewController mainViewController;
	
	private AgendamentoService agendamentoService;
	private Agendamento agendamento;

	@FXML private Label labelCodigoAgendamento;
	@FXML private Label labelNomePaciente;
	@FXML private Label labelAreaMedica;
	@FXML private Label labelDataMarcada;
	@FXML private JFXButton buttonAdiar;
	@FXML private JFXButton buttonSair;
	@FXML private JFXDatePicker datePickerRemarcar;
	@FXML private JFXTextField textFieldMotivoRemarcacao;
	
	private Alert dialogoAviso;
	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneAviso;
	private DialogPane dialogPaneConfirmacao;
	
	@FXML public void clickedButtonConfirmar(MouseEvent event) {
		logger.info("+++ ADIANDO AGENDAMENTO");		
		
		if (datePickerRemarcar.getValue() != null && textFieldMotivoRemarcacao.getLength() > 0) {
			agendamento = mainViewController.getAgendamentoSelecionado();
			agendamento.setDataConsulta(datePickerRemarcar.getValue());
			agendamento.setSituacaoAgendamento(new SituacaoAgendamento(StatusAgendamento.AGENDADO, textFieldMotivoRemarcacao.getText(), LocalDate.now()));
			
			try {
				agendamentoService.atualizar(agendamento);
				
				mainViewController.atualizaTabelas(LocalDate.now());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			Stage stage = (Stage) buttonSair.getScene().getWindow();
			stage.close();			
			
			dialogoConfirmacao.setHeaderText("AGENDAMENTO ADIADO");
			dialogoConfirmacao.showAndWait();
		}else {		
			dialogoAviso.setHeaderText("ESCOLHA A DATA E O MOTIVO PARA REMARCAÇÃO");
	        dialogoAviso.setContentText("Você precisa escolher uma nova data e dizer o motivo da remarcação do Agendamento.");
	        dialogoAviso.showAndWait();
		}
	}

	@FXML public void clickedButtonSair(MouseEvent event) {
		logger.info("+++ FECHANDO JANELA ADIAR AGENDAMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ADIAR AGENDAMENTO CONTROLLER");
		
		carregarAtributos();
		carregaLabels();
	}
	
	private void carregaLabels() {
		logger.info("+++ CARREGANDO INFORMAÇÕES DE AGENDAMENTO");
		
		labelAreaMedica.setText(mainViewController.getAgendamentoSelecionado().getAreaMedica().getNomeAreaMedica());
		labelCodigoAgendamento.setText(String.valueOf(mainViewController.getAgendamentoSelecionado().getCodigoAgendamento()));
		labelNomePaciente.setText(mainViewController.getAgendamentoSelecionado().getPaciente().getNome());
		labelDataMarcada.setText(mainViewController.getAgendamentoSelecionado().getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
		
		agendamentoService = new AgendamentoService();
	}
	
}

package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.SituacaoAgendamento;
import br.com.pimentel.scce.model.StatusAgendamento;
import br.com.pimentel.scce.service.AgendamentoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Cancelar Agendamento
 */
public class CancelarAgendamentoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(CancelarAgendamentoViewController.class);
	
	private MainViewController mainViewController;
	
	private AgendamentoService agendamentoService;
	private Agendamento agendamento;
	
	private Alert dialogoAviso;
	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneAviso;
	private DialogPane dialogPaneConfirmacao;

	@FXML private Label labelCodigoAgendamento;
    @FXML private Label labelNomePaciente;
    @FXML private Label labelAreaMedica;
    @FXML private Label labelDataMarcada;
    @FXML private JFXTextField textFieldMotivoCancelamento;
    @FXML private JFXButton buttonCancelarAgendamento;
    @FXML private JFXButton buttonSair;
	
	@FXML public void clickedButtonConfirmar(MouseEvent event) {
		logger.info("+++ CANCELANDO AGENDAMENTO");		
		
		if (textFieldMotivoCancelamento.getLength() > 0) {
			agendamento = mainViewController.getAgendamentoSelecionado();
			agendamento.setSituacaoAgendamento(new SituacaoAgendamento(StatusAgendamento.CANCELADO, textFieldMotivoCancelamento.getText(), LocalDate.now()));
			
			 dialogoAviso.setHeaderText("EXCLUINDO AGENDAMENTO");
		     dialogoAviso.setContentText("Você tem certeza que deseja EXCLUIR o Agendamento \"" + agendamento.getCodigoAgendamento() 
		     							+ "\" do Sr(a). \"" + agendamento.getPaciente().getNome() + "\" de CPF \"" + agendamento.getPaciente().getCpf() + "\"");
		        
		     ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
		     ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
		     dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
		        
		     Optional<ButtonType> result = dialogoAviso.showAndWait();    		
		     if (result.get() == buttonTypeOk){
		    	 try {
						agendamentoService.atualizar(agendamento);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
		    	 	mainViewController.atualizaTabelas(LocalDate.now());
		    	 
					Stage stage = (Stage) buttonSair.getScene().getWindow();
					stage.close();			
					
					dialogoConfirmacao.setHeaderText("AGENDAMENTO CANCELADO");
					dialogoConfirmacao.showAndWait();
		     }
		}else {		
			dialogoAviso.setHeaderText("INFORME O MOTIVO PARA CANCELAMENTO");
	        dialogoAviso.setContentText("Você precisa informar o motivo de cancelamento do Agendamento.");
	        dialogoAviso.showAndWait();
		}
	}

	@FXML public void clickedButtonSair(MouseEvent event) {
		logger.info("+++ FECHANDO JANELA CANCELAR ATENDIMENTO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO CANCELAR AGENDAMENTO CONTROLLER");
		
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

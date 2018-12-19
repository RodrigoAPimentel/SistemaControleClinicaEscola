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
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.pimentel.scce.model.Endereco;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.service.EnderecoService;
import br.com.pimentel.scce.service.PacienteService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import br.com.pimentel.scce.util.MascarasFX;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXComboBox;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller de Alterar Paciente
 */
public class AlterarPacienteViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(AlterarPacienteViewController.class);
	
	private MainViewController mainViewController;
	
	@FXML private JFXTextField textFieldCPF;
    @FXML private JFXTextField textFieldNome;
    @FXML private JFXTextField textFieldCEP;
    @FXML private JFXTextField textFieldRua;
    @FXML private JFXTextField textFieldNumero;
    @FXML private JFXTextField textFieldBairro;
    @FXML private JFXTextField textFieldCidade;
    @FXML private JFXTextField textFieldEstado;
    @FXML private JFXTextField textFieldComplemento;
    @FXML private JFXTextField textFieldPais;
    @FXML private JFXTextField textFieldTelefone;
    @FXML private JFXTextField textFieldEmail;
    @FXML private JFXDatePicker datePickerNascimento;
    @FXML private JFXButton buttonCadastrar;
    @FXML private JFXButton buttonSair;
    @FXML private JFXButton buttonExcluir;
    @FXML private Label labelCodigo;
    @FXML private JFXComboBox<String> comboBoxPesquisarPorCPF;
    
    private EnderecoService enderecoService;
    private Endereco endereco;
    
    private PacienteService pacienteService;
    private Paciente paciente;
    private Paciente pacienteSelecionado;

    private DialogPane dialogPaneAviso;
	private Alert dialogoAviso;
	private DialogPane dialogPaneConfirmacao;
	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneError;
	private Alert dialogoError;

	private List<Paciente> listPaciente;
	private List<String> listPacienteCPF;
	private ObservableList<String> observableListPacienteNome;

    @FXML private void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ ALTERANDO PACIENTE");    	
    	
    	if (textFieldCPF.getText().length()>0 && textFieldNome.getText().length()>0 && textFieldCEP.getText().length()>0 
    		&& textFieldRua.getText().length()>0 && textFieldNumero.getText().length()>0 && textFieldBairro.getText().length()>0 
    		&& textFieldCidade.getText().length()>0 && textFieldEstado.getText().length()>0 && textFieldPais.getText().length()>0 
    		&& textFieldTelefone.getText().length()>0 && textFieldEmail.getText().length()>0 && datePickerNascimento.getValue() != null) {
    		
    		endereco = pacienteSelecionado.getEndereco();
    		endereco.setBairro(textFieldBairro.getText());
    		endereco.setCep(textFieldCEP.getText());
    		endereco.setCidade(textFieldCidade.getText());
    		endereco.setComplemento(textFieldComplemento.getText());
    		endereco.setEmail(textFieldEmail.getText());
    		endereco.setEstado(textFieldEstado.getText());
    		endereco.setNumero(Integer.parseInt(textFieldNumero.getText()));
    		endereco.setPais(textFieldPais.getText());
    		endereco.setRua(textFieldRua.getText());
    		endereco.setTelefone(textFieldTelefone.getText());
    		
    		try {
				enderecoService.atualizar(endereco);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			paciente = pacienteSelecionado;
			paciente.setDataNascimento(datePickerNascimento.getValue());
			paciente.setEndereco(endereco);
			paciente.setNome(textFieldNome.getText());
    		try {
				pacienteService.salvar(paciente);
				
				mainViewController.atualizaTabelas(LocalDate.now());
				
				logger.info("+++ PACIENTE DE CPF: [{}] ALTERADO", textFieldCPF.getText()); 
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
    		
    		Stage stage = (Stage) buttonCadastrar.getScene().getWindow();
			stage.close();
			
			dialogoConfirmacao.setHeaderText("PACIENTE ALTERADO");
			dialogoConfirmacao.setContentText("O Sr(a). \"" + textFieldNome.getText() + "\" de CPF \"" + textFieldCPF.getText() + "\" foi alterado com Sucesso");
			dialogoConfirmacao.showAndWait();      	
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para Alterar um Paciente.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML private void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA ATUALIZAR PACIENTE");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
    
    @FXML private void clickedExcluir(MouseEvent event) {    	
    	if (pacienteSelecionado != null) {
	        dialogoAviso.setHeaderText("EXCLUINDO PACIENTE");
	        dialogoAviso.setContentText("Você tem certeza que deseja EXCLUIR o Sr(a). \"" + pacienteSelecionado.getNome() + "\" de CPF \"" + pacienteSelecionado.getCpf() + "\"");
	        
	        ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
	        ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
	        dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
	        
	        Optional<ButtonType> result = dialogoAviso.showAndWait();    		
	        if (result.get() == buttonTypeOk){
	        	logger.info("+++ EXLUINDO PACIENTE [{}]", pacienteSelecionado.getCpf());  
	        	
	        	try {
	    			pacienteService.excluir(pacienteSelecionado);
	    			
	    			mainViewController.atualizaTabelas(LocalDate.now());
	    			
	    			Stage stage = (Stage) buttonExcluir.getScene().getWindow();
	    			stage.close();
	    			
	    			dialogoConfirmacao.setHeaderText("PACIENTE EXCLUIDO");
	    			dialogoConfirmacao.setContentText("O Sr(a). \"" + pacienteSelecionado.getNome() + "\" de CPF \"" + pacienteSelecionado.getCpf() + "\" foi EXCLUIDO com Sucesso");
	    			dialogoConfirmacao.showAndWait();
	    		} catch (Exception e) {
	    			logger.error(e.getMessage());
	    		}
	        } 
		} else {
			dialogoAviso.setHeaderText("PACIENTE NÃO SELECIONADO");
	        dialogoAviso.setContentText("Você precisa selecionar um Paciente para Ecluir.");
	        dialogoAviso.showAndWait();
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ATUALIZAR PACIENTE CONTROLLER");
		
		carregarAtributos();
		aplicarMascaras();
		carregarCampos();
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		mainViewController = MainViewController.getInstanceMainViewController();
		
		enderecoService = new EnderecoService();
		pacienteService = new PacienteService();
		
		listPaciente = new ArrayList<>();
		listPacienteCPF = new ArrayList<>();
		
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
	}
	
	private void aplicarMascaras() {
		logger.info("+++ APLICANDO MASCARAS EM TEXTFIELDS");
		
		MascarasFX.cpfField(textFieldCPF);
		MascarasFX.cepField(textFieldCEP);
		MascarasFX.numericField(textFieldNumero);
		MascarasFX.telefoneField(textFieldTelefone);		
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	private void carregarCampos() {
		logger.info("+++ CARREGANDO CAMPOS PARA EDIÇÃO");
		
		listPaciente = pacienteService.buscarTodos();
		for (Paciente paciente : listPaciente) {
			listPacienteCPF.add(paciente.getCpf());
		}
		observableListPacienteNome = FXCollections.observableArrayList(listPacienteCPF);
		comboBoxPesquisarPorCPF.setItems(observableListPacienteNome);
		new AutoCompleteComboBoxListener<>(comboBoxPesquisarPorCPF);		
		
		comboBoxPesquisarPorCPF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (comboBoxPesquisarPorCPF.getSelectionModel().getSelectedItem() != null) {
					
					pacienteSelecionado = pacienteService.buscarPorCPF(comboBoxPesquisarPorCPF.getValue());
					
					logger.info("+++ SELECIONADO O PACIENTE [{}]", pacienteSelecionado.getCodigoPaciente());
					
					labelCodigo.setText(pacienteSelecionado.getCodigoPaciente());
					datePickerNascimento.setValue(pacienteSelecionado.getDataNascimento());
					textFieldCPF.setText(pacienteSelecionado.getCpf());
					textFieldNome.setText(pacienteSelecionado.getNome());
					textFieldCEP.setText(pacienteSelecionado.getEndereco().getCep());
					textFieldRua.setText(pacienteSelecionado.getEndereco().getRua());
					textFieldNumero.setText(""+ pacienteSelecionado.getEndereco().getNumero());
					textFieldBairro.setText(pacienteSelecionado.getEndereco().getBairro());
					textFieldCidade.setText(pacienteSelecionado.getEndereco().getCidade());
					textFieldEstado.setText(pacienteSelecionado.getEndereco().getEstado());
					textFieldComplemento.setText(pacienteSelecionado.getEndereco().getComplemento());
					textFieldPais.setText(pacienteSelecionado.getEndereco().getPais());
					textFieldTelefone.setText(pacienteSelecionado.getEndereco().getTelefone());
					textFieldEmail.setText(pacienteSelecionado.getEndereco().getEmail());
				} 
            }
        });		
	}
	
}

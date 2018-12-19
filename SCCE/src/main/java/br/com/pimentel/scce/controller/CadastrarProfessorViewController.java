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
import com.jfoenix.controls.JFXTextField;

import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Endereco;
import br.com.pimentel.scce.model.Professor;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.EnderecoService;
import br.com.pimentel.scce.service.ProfessorService;
import br.com.pimentel.scce.util.MascarasFX;
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
 * Classe de Controller de Cadastrar Professor
 */
public class CadastrarProfessorViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(CadastrarProfessorViewController.class);
	
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
    @FXML private JFXTextField textFieldCurso;
    @FXML private JFXComboBox<String> comboBoxAreaMedica;
    
    private EnderecoService enderecoService;
    private Endereco endereco;
   
    private ProfessorService professorService;
    private Professor professor;   
    
    private AreaMedicaService areaMedicaService;    
    private List<AreaMedica> listAreaMedica;
    private List<String> listAreaMedicaDescricao;
    private ObservableList<String> observableListAreaMedicaDescricao;
   
    private DialogPane dialogPaneAviso;
	private Alert dialogoAviso;
	private DialogPane dialogPaneConfirmacao;
	private Alert dialogoConfirmacao;
	private DialogPane dialogPaneError;
	private Alert dialogoError;

    @FXML void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ CADASTRANDO PROFESSOR");    	
    	
    	if (textFieldCPF.getText().length()>0 && textFieldNome.getText().length()>0 && textFieldCEP.getText().length()>0 
    		&& textFieldRua.getText().length()>0 && textFieldNumero.getText().length()>0 && textFieldBairro.getText().length()>0 
    		&& textFieldCidade.getText().length()>0 && textFieldEstado.getText().length()>0 && textFieldPais.getText().length()>0 
    		&& textFieldTelefone.getText().length()>0 && textFieldEmail.getText().length()>0 && datePickerNascimento.getValue() != null
    		&& textFieldCurso.getText().length()>0 && comboBoxAreaMedica.getValue() != null) {
    		
    		endereco = new Endereco(textFieldCEP.getText(), textFieldRua.getText(), Integer.parseInt(textFieldNumero.getText()), 
									textFieldBairro.getText(), textFieldCidade.getText(), textFieldEstado.getText(), textFieldComplemento.getText(), 
									textFieldPais.getText(), textFieldTelefone.getText(), textFieldEmail.getText());
    		
    		if (professorService.buscarPorCPF(textFieldCPF.getText()) == null) {
    			try {
    				enderecoService.salvar(endereco);
    			} catch (Exception e) {
    				logger.info(e.getMessage());
    			}
    			
    			AreaMedica areaMedicaAux = areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue());
        		
        		professor = new Professor(textFieldCPF.getText(), textFieldNome.getText(), datePickerNascimento.getValue(), endereco, 
        							textFieldCurso.getText(), areaMedicaAux);
        		
        		try {
					professorService.salvar(professor);
					
					mainViewController.atualizaTabelas(LocalDate.now());
					
					logger.info("+++ PROFESSOR DE CPF: [{}] CADASTRADO", textFieldCPF.getText()); 
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
	    		
	    		Stage stage = (Stage) buttonSair.getScene().getWindow();
				stage.close();
				
				dialogoConfirmacao.setHeaderText("PROFESSOR CADASTRADO");
				dialogoConfirmacao.setContentText("O Sr(a). \"" + textFieldNome.getText() + "\" de CPF \"" + textFieldCPF.getText() + "\" foi cadastrado com Sucesso");
				dialogoConfirmacao.showAndWait();	
			} else {
				logger.error("+++ PROFESSOR DE CPF: [{}] JA ESTA CADASTRADO", textFieldCPF.getText());
    			
    			dialogoError.setHeaderText("PROFESSOR JA ESTA CADASTRADO");
    			dialogoError.setContentText("Ja existe um Professor com CPF \"" + textFieldCPF.getText() + "\" cadastrado");
    			dialogoError.showAndWait();
			}
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para Cadastrar um Professor.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA CADASTRAR PROFESSOR");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ADICIONAR PROFESSOR CONTROLLER");
		
		carregarAtributos();
		aplicarMascaras();
		
		listAreaMedica = areaMedicaService.buscarTodos();		
		for (AreaMedica areaMedica : listAreaMedica) {
			listAreaMedicaDescricao.add(areaMedica.getNomeAreaMedica());
		}				
		observableListAreaMedicaDescricao = FXCollections.observableArrayList(listAreaMedicaDescricao);
		comboBoxAreaMedica.setItems(observableListAreaMedicaDescricao);
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		mainViewController = MainViewController.getInstanceMainViewController();
		
		enderecoService = new EnderecoService();
		professorService = new ProfessorService();
		
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
	}
	
	private void aplicarMascaras() {
		logger.info("+++ APLICANDO MASCARAS EM TEXTFIELDS");
		
		MascarasFX.cpfField(textFieldCPF);
		MascarasFX.cepField(textFieldCEP);
		MascarasFX.numericField(textFieldNumero);
		MascarasFX.telefoneField(textFieldTelefone);
	}
	
}

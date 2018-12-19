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
import com.jfoenix.controls.JFXTextField;

import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Endereco;
import br.com.pimentel.scce.model.Professor;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.EnderecoService;
import br.com.pimentel.scce.service.ProfessorService;
import br.com.pimentel.scce.util.AutoCompleteComboBoxListener;
import br.com.pimentel.scce.util.MascarasFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Classe de Controller de Alterar Professor
 */
public class AlterarProfessorViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(AlterarProfessorViewController.class);
	
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
    @FXML private JFXTextField textFieldCurso;
    @FXML private JFXComboBox<String> comboBoxAreaMedica;
    @FXML JFXComboBox<String> comboBoxPesquisarPorCPF;
	@FXML Label labelCodigo;
    
    private EnderecoService enderecoService;
    private Endereco endereco;
   
    private ProfessorService professorService;
    private Professor professor; 
    private Professor professorSelecionado;
    
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

	private List<Professor> listProfessor;
	private List<String> listProfessorCPF;
	private ObservableList<String> observableListProfessorCPF;

    @FXML void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ ALTERANDO PROFESSOR");    	
    	
    	if (textFieldCPF.getText().length()>0 && textFieldNome.getText().length()>0 && textFieldCEP.getText().length()>0 
    		&& textFieldRua.getText().length()>0 && textFieldNumero.getText().length()>0 && textFieldBairro.getText().length()>0 
    		&& textFieldCidade.getText().length()>0 && textFieldEstado.getText().length()>0 && textFieldPais.getText().length()>0 
    		&& textFieldTelefone.getText().length()>0 && textFieldEmail.getText().length()>0 && datePickerNascimento.getValue() != null
    		&& textFieldCurso.getText().length()>0 && comboBoxAreaMedica.getValue() != null) {
    		
    		endereco = professorSelecionado.getEndereco();
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
				logger.info(e.getMessage());
			}
			
			AreaMedica areaMedicaAux = areaMedicaService.buscarPorDescricao(comboBoxAreaMedica.getValue());
    		
    		professor = new Professor(textFieldCPF.getText(), textFieldNome.getText(), datePickerNascimento.getValue(), endereco, 
    							textFieldCurso.getText(), areaMedicaAux);
    		
    		professor = professorSelecionado;
    		professor.setAreaMedica(areaMedicaAux);
    		professor.setCurso(textFieldCurso.getText());
    		professor.setDataNascimento(datePickerNascimento.getValue());
    		professor.setEndereco(endereco);
    		professor.setNome(textFieldNome.getText());
    		
    		try {
				professorService.atualiza(professor);
				
				mainViewController.atualizaTabelas(LocalDate.now());
				
				logger.info("+++ PROFESSOR DE CPF: [{}] ALTERADO", textFieldCPF.getText()); 
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
    		
    		Stage stage = (Stage) buttonCadastrar.getScene().getWindow();
			stage.close();
			
			dialogoConfirmacao.setHeaderText("PROFESSOR ALTERADO");
			dialogoConfirmacao.setContentText("O Sr(a). \"" + textFieldNome.getText() + "\" de CPF \"" + textFieldCPF.getText() + "\" foi alterado com Sucesso");
			dialogoConfirmacao.showAndWait();	
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para Alterar um Professor.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA ALTERAR PROFESSOR");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
	
    @FXML private void clickedExcluir(MouseEvent event) {    	
    	if (professorSelecionado != null) {
	        dialogoAviso.setHeaderText("EXCLUINDO PROFESSOR");
	        dialogoAviso.setContentText("Você tem certeza que deseja EXCLUIR o Sr(a). \"" + professorSelecionado.getNome() + "\" de CPF \"" + professorSelecionado.getCpf() + "\"");
	        
	        ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
	        ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
	        dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
	        
	        Optional<ButtonType> result = dialogoAviso.showAndWait();    		
	        if (result.get() == buttonTypeOk){
	        	logger.info("+++ EXLUINDO ALUNO [{}]", professorSelecionado.getCpf());  
	        	
	        	try {
	    			professorService.excluir(professorSelecionado);
	    			
	    			mainViewController.atualizaTabelas(LocalDate.now());
	    			
	    			Stage stage = (Stage) buttonExcluir.getScene().getWindow();
	    			stage.close();
	    			
	    			dialogoConfirmacao.setHeaderText("PROFESSOR EXCLUIDO");
	    			dialogoConfirmacao.setContentText("O Sr(a). \"" + professorSelecionado.getNome() + "\" de CPF \"" + professorSelecionado.getCpf() + "\" foi EXCLUIDO com Sucesso");
	    			dialogoConfirmacao.showAndWait();
	    		} catch (Exception e) {
	    			logger.error(e.getMessage());
	    		}
	        } 
		} else {
			dialogoAviso.setHeaderText("PROFESSOR NÃO SELECIONADO");
	        dialogoAviso.setContentText("Você precisa selecionar um Professor para Ecluir.");
	        dialogoAviso.showAndWait();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ALTERAR PROFESSOR CONTROLLER");
		
		carregarAtributos();
		aplicarMascaras();
		carregarCampos();
		
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
		
		listProfessor = new ArrayList<>();
		listProfessorCPF = new ArrayList<>();
		
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
	
	@SuppressWarnings({"unchecked","rawtypes"})
	private void carregarCampos() {
		logger.info("+++ CARREGANDO CAMPOS PARA EDIÇÃO");
		
		listProfessor = professorService.buscarTodos();
		for (Professor professor : listProfessor) {
			listProfessorCPF.add(professor.getCpf());
		}
		observableListProfessorCPF = FXCollections.observableArrayList(listProfessorCPF);
		comboBoxPesquisarPorCPF.setItems(observableListProfessorCPF);
		new AutoCompleteComboBoxListener<>(comboBoxPesquisarPorCPF);		
		
		comboBoxPesquisarPorCPF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (comboBoxPesquisarPorCPF.getSelectionModel().getSelectedItem() != null) {
					
					professorSelecionado = professorService.buscarPorCPF(comboBoxPesquisarPorCPF.getValue());
					
					logger.info("+++ SELECIONADO O PROFESSOR [{}]", professorSelecionado.getCodigoProfessor());
					
					labelCodigo.setText(professorSelecionado.getCodigoProfessor());
					datePickerNascimento.setValue(professorSelecionado.getDataNascimento());
					textFieldCPF.setText(professorSelecionado.getCpf());
					textFieldNome.setText(professorSelecionado.getNome());
					textFieldCurso.setText(professorSelecionado.getCurso());
					
					comboBoxAreaMedica.setValue(professorSelecionado.getCurso());
					
					textFieldCEP.setText(professorSelecionado.getEndereco().getCep());
					textFieldRua.setText(professorSelecionado.getEndereco().getRua());
					textFieldNumero.setText(""+ professorSelecionado.getEndereco().getNumero());
					textFieldBairro.setText(professorSelecionado.getEndereco().getBairro());
					textFieldCidade.setText(professorSelecionado.getEndereco().getCidade());
					textFieldEstado.setText(professorSelecionado.getEndereco().getEstado());
					textFieldComplemento.setText(professorSelecionado.getEndereco().getComplemento());
					textFieldPais.setText(professorSelecionado.getEndereco().getPais());
					textFieldTelefone.setText(professorSelecionado.getEndereco().getTelefone());
					textFieldEmail.setText(professorSelecionado.getEndereco().getEmail());
				} 
            }
        });		
	}
	
}

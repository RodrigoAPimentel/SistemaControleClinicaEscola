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

import br.com.pimentel.scce.model.Aluno;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Endereco;
import br.com.pimentel.scce.service.AlunoService;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.EnderecoService;
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
 * Classe de Controller de Alterar Aluno
 */
public class AlterarAlunoViewController implements Initializable, Serializable{

	private static final long serialVersionUID = 533698194976927058L;
	
	private static Logger logger = LoggerFactory.getLogger(AlterarAlunoViewController.class);
	
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
    @FXML private JFXComboBox<String> comboBoxPesquisarPorCPF;
	@FXML private Label labelMatricula;
    
    private EnderecoService enderecoService;
    private Endereco endereco;
   
    private AlunoService alunoService;
    private Aluno aluno;
    private Aluno alunoSelecionado;
    
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

	private List<Aluno> listAluno;
	private List<String> listAlunoCPF;
	private ObservableList<String> observableListAlunoCPF;

    @FXML void clickedCadastrar(MouseEvent event) {
    	logger.info("+++ ALTERANDO ALUNO");    	
    	
    	if (textFieldCPF.getText().length()>0 && textFieldNome.getText().length()>0 && textFieldCEP.getText().length()>0 
    		&& textFieldRua.getText().length()>0 && textFieldNumero.getText().length()>0 && textFieldBairro.getText().length()>0 
    		&& textFieldCidade.getText().length()>0 && textFieldEstado.getText().length()>0 && textFieldPais.getText().length()>0 
    		&& textFieldTelefone.getText().length()>0 && textFieldEmail.getText().length()>0 && datePickerNascimento.getValue() != null
    		&& textFieldCurso.getText().length()>0 && comboBoxAreaMedica.getValue() != null) {
    		
    		endereco = alunoSelecionado.getEndereco();
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
    		
    		aluno = alunoSelecionado;
    		aluno.setAreaMedica(areaMedicaAux);
    		aluno.setCurso(textFieldCurso.getText());
    		aluno.setDataNascimento(datePickerNascimento.getValue());
    		aluno.setEndereco(endereco);
    		aluno.setNome(textFieldNome.getText());
    		try {
				alunoService.atualizar(aluno);
				
				mainViewController.atualizaTabelas(LocalDate.now());
				
				logger.info("+++ ALUNO DE CPF: [{}] ALTERADO", textFieldCPF.getText()); 
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
    		
    		Stage stage = (Stage) buttonCadastrar.getScene().getWindow();
			stage.close();
			
			dialogoConfirmacao.setHeaderText("ALUNO ALTERADO");
			dialogoConfirmacao.setContentText("O Sr(a). \"" + textFieldNome.getText() + "\" de CPF \"" + textFieldCPF.getText() + "\" foi alterado com Sucesso");
			dialogoConfirmacao.showAndWait();    		
		}else {
			dialogoAviso.setHeaderText("CAMPO(S) NÃO PREENCHIDO(S)");
	        dialogoAviso.setContentText("Você precisa preencher todos os campo para Alterar um Aluno.");
	        dialogoAviso.showAndWait();
		}    	
    }

    @FXML void clickedSair(MouseEvent event) {
    	logger.info("+++ FECHANDO JANELA ALTERAR ALUNO");
		
		Stage stage = (Stage) buttonSair.getScene().getWindow();
		stage.close();
    }
    
    @FXML private void clickedExcluir(MouseEvent event) {    	
    	if (alunoSelecionado != null) {
	        dialogoAviso.setHeaderText("EXCLUINDO ALUNO");
	        dialogoAviso.setContentText("Você tem certeza que deseja EXCLUIR o Sr(a). \"" + alunoSelecionado.getNome() + "\" de CPF \"" + alunoSelecionado.getCpf() + "\"");
	        
	        ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
	        ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
	        dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
	        
	        Optional<ButtonType> result = dialogoAviso.showAndWait();    		
	        if (result.get() == buttonTypeOk){
	        	logger.info("+++ EXLUINDO ALUNO [{}]", alunoSelecionado.getCpf());  
	        	
	        	try {
	    			alunoService.excluir(alunoSelecionado);
	    			
	    			mainViewController.atualizaTabelas(LocalDate.now());
	    			
	    			Stage stage = (Stage) buttonExcluir.getScene().getWindow();
	    			stage.close();
	    			
	    			dialogoConfirmacao.setHeaderText("ALUNO EXCLUIDO");
	    			dialogoConfirmacao.setContentText("O Sr(a). \"" + alunoSelecionado.getNome() + "\" de CPF \"" + alunoSelecionado.getCpf() + "\" foi EXCLUIDO com Sucesso");
	    			dialogoConfirmacao.showAndWait();
	    		} catch (Exception e) {
	    			logger.error(e.getMessage());
	    		}
	        } 
		} else {
			dialogoAviso.setHeaderText("ALUNO NÃO SELECIONADO");
	        dialogoAviso.setContentText("Você precisa selecionar um Aluno para Ecluir.");
	        dialogoAviso.showAndWait();
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO ALTERAR ALUNO CONTROLLER");
		
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
		alunoService = new AlunoService();
		
		listAluno = new ArrayList<>();
		listAlunoCPF = new ArrayList<>();
		
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
		
		listAluno = alunoService.buscarTodos();
		for (Aluno aluno : listAluno) {
			listAlunoCPF.add(aluno.getCpf());
		}
		observableListAlunoCPF = FXCollections.observableArrayList(listAlunoCPF);
		comboBoxPesquisarPorCPF.setItems(observableListAlunoCPF);
		new AutoCompleteComboBoxListener<>(comboBoxPesquisarPorCPF);		
		
		comboBoxPesquisarPorCPF.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (comboBoxPesquisarPorCPF.getSelectionModel().getSelectedItem() != null) {
					
					alunoSelecionado = alunoService.buscarPorCPF(comboBoxPesquisarPorCPF.getValue());
					
					logger.info("+++ SELECIONADO O ALUNO [{}]", alunoSelecionado.getMatricula());
					
					labelMatricula.setText(alunoSelecionado.getMatricula());
					datePickerNascimento.setValue(alunoSelecionado.getDataNascimento());
					textFieldCPF.setText(alunoSelecionado.getCpf());
					textFieldNome.setText(alunoSelecionado.getNome());
					textFieldCurso.setText(alunoSelecionado.getCurso());
					
					comboBoxAreaMedica.setValue(alunoSelecionado.getCurso());
					
					textFieldCEP.setText(alunoSelecionado.getEndereco().getCep());
					textFieldRua.setText(alunoSelecionado.getEndereco().getRua());
					textFieldNumero.setText(""+ alunoSelecionado.getEndereco().getNumero());
					textFieldBairro.setText(alunoSelecionado.getEndereco().getBairro());
					textFieldCidade.setText(alunoSelecionado.getEndereco().getCidade());
					textFieldEstado.setText(alunoSelecionado.getEndereco().getEstado());
					textFieldComplemento.setText(alunoSelecionado.getEndereco().getComplemento());
					textFieldPais.setText(alunoSelecionado.getEndereco().getPais());
					textFieldTelefone.setText(alunoSelecionado.getEndereco().getTelefone());
					textFieldEmail.setText(alunoSelecionado.getEndereco().getEmail());
				} 
            }
        });		
	}
	
}

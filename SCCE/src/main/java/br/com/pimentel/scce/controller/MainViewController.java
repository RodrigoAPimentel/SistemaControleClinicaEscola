package br.com.pimentel.scce.controller;

import java.io.IOException;
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

import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.Agendamento.AgendamentoSimpleProperty;
import br.com.pimentel.scce.model.Consulta;
import br.com.pimentel.scce.model.Consulta.ConsultaSimpleProperty;
import br.com.pimentel.scce.service.AgendamentoService;
import br.com.pimentel.scce.service.ConsultaService;
import br.com.pimentel.scce.util.StyleChangingRowFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Controller da Main View
 */
public class MainViewController implements Initializable, Serializable{

	private static final long serialVersionUID = -7862224900449821021L;
	
	private static Logger logger = LoggerFactory.getLogger(MainViewController.class);

	@FXML private BorderPane borderPaneMain;
	
	@FXML private MenuBar menuBarTop;
	@FXML private Menu menuPaciente;
	@FXML private MenuItem menuItemPacienteListar;
	@FXML private MenuItem menuItemPacienteAdicionar;
	@FXML private MenuItem menuItemPacienteAtualizar;
	@FXML private Menu menuAluno;
	@FXML private MenuItem menuItemAlunoListar;
	@FXML private MenuItem menuItemAlunoAdicionar;
	@FXML private MenuItem menuItemAlunoAtualizar;
	@FXML private Menu menuProfessor;
	@FXML private MenuItem menuItemProfessorListar;
	@FXML private MenuItem menuItemProfessorAdicionar;
	@FXML private MenuItem menuItemProfessorAtualizar;
	@FXML private Menu menuAgendamento;
	@FXML private MenuItem menuItemAgendamentoListar;
	@FXML private MenuItem menuItemAgendamentoAdicionar;
	@FXML private MenuItem menuItemAgendamentoAtualizar;
	@FXML private Menu menuSobre;
	@FXML private MenuItem menuItemSobre;
	
	@FXML private Pane paneLeft;	
	@FXML private HBox titledPaneCadastroLabelPaciente;
	@FXML private HBox titledPaneCadastroLabelAluno;
	@FXML private HBox titledPaneCadastroLabelProfessor;
	@FXML private HBox titledPaneCadastroLabelAgendamento;
	@FXML private HBox titledPaneAlterarLabelPaciente;
	@FXML private HBox titledPaneAlterarLabelAluno;
	@FXML private HBox titledPaneAlterarLabelProfessor;
	@FXML private HBox titledPaneAlterarLabelAgendamento;
	@FXML private TitledPane titlePaneCadastrar;
	@FXML private TitledPane titlePaneAlterar;
	@FXML private TitledPane titlePaneListar;
	@FXML private HBox titledPaneListarLabelPaciente;
	@FXML private HBox titledPaneListarLabelAluno;
	@FXML private HBox titledPaneListarLabelProfessor;
	@FXML private HBox titledPaneListarLabelAgendamento;
	@FXML private HBox titledPaneListarLabelConsulta;
	
	@FXML private SplitPane splitPane;
	@FXML private AnchorPane splitPaneAgendamento;
	@FXML private TableView<AgendamentoSimpleProperty> tableViewAgendamento = new TableView<AgendamentoSimpleProperty>();
	@FXML private TableColumn<AgendamentoSimpleProperty, Integer> columnAgendamentoCodigo;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnAgendamentoPaciente;
	@FXML private TableColumn<AgendamentoSimpleProperty, String> columnAgendamentoAreaMedica;
	@FXML private JFXButton buttonAgendamentoConfirmar;
	@FXML private JFXButton buttonAgendamentoAdiar;
	@FXML private JFXButton buttonAgendamentoCancelar;
	
	@FXML private AnchorPane splitPaneConsulta;
	@FXML private JFXButton buttonConsultaAtendimento;	
	@FXML private TableView<ConsultaSimpleProperty> tableViewConsulta  = new TableView<ConsultaSimpleProperty>();
	@FXML private TableColumn<ConsultaSimpleProperty, Integer> columnConsultaCodigo;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnConsultaPaciente;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnConsultaAreaMedica;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnConsultaAluno;
	@FXML private TableColumn<ConsultaSimpleProperty, String> columnConsultaProfessor;
	
	private AgendamentoService agendamentoService;
	private List<Agendamento> listAgendamentosOriginais;
	private List<AgendamentoSimpleProperty> listAgendamentosAux;
	private ObservableList<AgendamentoSimpleProperty> observableAgendamentosAux;
	
	private static Agendamento agendamentoSelecionado;
	private AgendamentoSimpleProperty agendamentoAuxSelecionado;
	
	private ConsultaService consultaService;
	private List<Consulta> listConsultasOriginais;
	private List<ConsultaSimpleProperty> listConsultasAux;
	private ObservableList<ConsultaSimpleProperty> observableConsultasAux;
	
	private Consulta consultaSelecionada;
	private ConsultaSimpleProperty consultaAuxSelecionada;
	
	private Alert dialogoAviso;
	private DialogPane dialogPane;	

	private StyleChangingRowFactory<ConsultaSimpleProperty> rowFactory;
	
	private static MainViewController mainViewController;
	
	private void carregarTableViewAgendamentos(LocalDate data) {
		logger.info("+++ CARREGANDO TABLE VIEW AGENDAMENTO COM DATA: {}", data);
		
		listAgendamentosAux = new ArrayList<>();
		listAgendamentosOriginais = agendamentoService.buscarTodosStatusAgendadoPorData(data);
		
		for (Agendamento agendamento : listAgendamentosOriginais) {
			listAgendamentosAux.add(agendamento.new AgendamentoSimpleProperty(agendamento));
		}		
			
		columnAgendamentoCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoAgendamento"));
		columnAgendamentoPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		columnAgendamentoAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
		
		observableAgendamentosAux = FXCollections.observableArrayList(listAgendamentosAux);
		
		tableViewAgendamento.setItems(observableAgendamentosAux);		
	}
	
	private void carregarTableViewConsulta(LocalDate data) {
		logger.info("+++ CARREGANDO TABLE VIEW CONSULTA COM DATA: {}", data);
		
		listConsultasAux = new ArrayList<>();
		listConsultasOriginais = consultaService.buscarTodosPorData(data);
		
		for (Consulta consulta : listConsultasOriginais) {
			listConsultasAux.add(consulta.new ConsultaSimpleProperty(consulta));
		}		
			
		columnConsultaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta"));
		columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
		columnConsultaAreaMedica.setCellValueFactory(new PropertyValueFactory<>("areaMedica"));
		columnConsultaAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
		columnConsultaProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
		
		observableConsultasAux = FXCollections.observableArrayList(listConsultasAux);
		
		tableViewConsulta.setItems(observableConsultasAux);	
		
		tableViewConsulta.setRowFactory(row -> new TableRow<ConsultaSimpleProperty>(){
		    @Override
		    public void updateItem(ConsultaSimpleProperty item, boolean empty){
		        super.updateItem(item, empty);

		        if (item == null || empty) {
		            setStyle("");
		        } else {
		            if (item.getIsConsultaRealizada() == true) {		            	
		            	setStyle("-fx-background-color: #2ac22a");
		            } 
		        }
		    }
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void selecaoTableView() {
		logger.info("+++ INICIANDO METODO SELECAOTABLEVIEW");

		tableViewAgendamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (tableViewAgendamento.getSelectionModel().getSelectedItem() != null) {
					agendamentoAuxSelecionado = tableViewAgendamento.getSelectionModel().getSelectedItem();
					agendamentoSelecionado = agendamentoAuxSelecionado.getAgendamentoOriginal();
					
					logger.info("+++ SELECIONADO O AGENDAMENTO [{}] NA TABLE VIEW AGENDAMENTO", agendamentoAuxSelecionado.getCodigoAgendamento());
				}else {					
					agendamentoSelecionado = null;				
				}

			}
		});
		
		tableViewConsulta.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (tableViewConsulta.getSelectionModel().getSelectedItem() != null) {
					consultaAuxSelecionada = tableViewConsulta.getSelectionModel().getSelectedItem();
					consultaSelecionada = consultaAuxSelecionada.getConsultaOriginal();
					
					logger.info("+++ SELECIONADO A CONSULTA [{}] NA TABLE VIEW CONSULTA", consultaAuxSelecionada.getCodigoConsulta());
				}else {					
					consultaSelecionada = null;					
				}

			}
		});
	}
	
	@FXML private void clickedConfirmaAgendamento(MouseEvent event) {	
		if (agendamentoSelecionado != null) {
			logger.info("+++ BOTÃO CONFIRMAR AGENDAMENTO CLICADO COM O AGENDAMENTO [{}]", agendamentoSelecionado.getCodigoAgendamento());			
	    	
	    	Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ConfirmarAgendamento.fxml"));
				Stage stage = new Stage();
		    	Scene scene = new Scene(root, 600, 370);
		    	stage.setScene(scene);
		    	stage.initStyle(StageStyle.TRANSPARENT);
		    	stage.show();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {			
			dialogoAviso.setHeaderText("SELECIONE UM AGENDAMENTO");
	        dialogoAviso.setContentText("Você precisa escolher um Agendamento \npara Transforma em Consulta.");
	        dialogoAviso.showAndWait();
		}
	}
	
	@FXML private void clickedAdiarAgendamento(MouseEvent event) {
		if (agendamentoSelecionado != null) {
			logger.info("+++ BOTÃO ADIAR AGENDAMENTO CLICADO COM O AGENDAMENTO [{}]", agendamentoSelecionado.getCodigoAgendamento());
			
			Parent rootAdiar;
			try {
				rootAdiar = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/AdiarAgendamento.fxml"));
				Stage stage = new Stage();
		    	Scene scene = new Scene(rootAdiar, 600, 370);
		    	stage.setScene(scene);
		    	stage.initStyle(StageStyle.TRANSPARENT);
		    	stage.show();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {			
			dialogoAviso.setHeaderText("SELECIONE UM AGENDAMENTO");
	        dialogoAviso.setContentText("Você precisa escolher um Agendamento \npara Adiar a Consulta.");
	        dialogoAviso.showAndWait();
		}
	}

	@FXML private void clickedCancelarAgendamento(MouseEvent event) {
		if (agendamentoSelecionado != null) {
			logger.info("+++ BOTÃO CANCELAR AGENDAMENTO CLICADO COM O AGENDAMENTO [{}]", agendamentoSelecionado.getCodigoAgendamento());
			
			Parent rootCancelar;
			try {
				rootCancelar = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/CancelarAgendamento.fxml"));
				Stage stage = new Stage();
		    	Scene scene = new Scene(rootCancelar, 600, 370);
		    	stage.setScene(scene);
		    	stage.initStyle(StageStyle.TRANSPARENT);
		    	stage.show();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {			
			dialogoAviso.setHeaderText("SELECIONE UM AGENDAMENTO");
	        dialogoAviso.setContentText("Você precisa escolher um Agendamento \npara Cancelar.");
	        dialogoAviso.showAndWait();
		}
	}
		
	@FXML private void clickedAtendimentoConsulta(MouseEvent event) {
		if (consultaSelecionada != null) {
			logger.info("+++ BOTÃO CONFIRMAR ATENDIMENTO CLICADO COM A CONSULTA [{}]", consultaSelecionada.getCodigoConsulta());

			dialogoAviso.setHeaderText("CONFIRMANDO CONSULTA");
			dialogoAviso.setContentText( "Você tem certeza que deseja CONFIRMAR a consulta \"" + consultaSelecionada.getCodigoConsulta()
										+ "\" do Sr(a). \"" + consultaSelecionada.getAgendamento().getPaciente().getNome() + "\"");

			ButtonType buttonTypeOk = new ButtonType("Sim", ButtonData.OK_DONE);
			ButtonType buttonTypeCancel = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
			dialogoAviso.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

			Optional<ButtonType> result = dialogoAviso.showAndWait();
			if (result.get() == buttonTypeOk) {
				buttonConsultaAtendimento.disableProperty().bind(Bindings.isEmpty(tableViewConsulta.getSelectionModel().getSelectedIndices()));

				rowFactory.getStyledRowIndices().setAll(tableViewConsulta.getSelectionModel().getSelectedIndices());

				try {
					consultaService.confirmaConsultaRealizada(consultaSelecionada);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				carregarTableViewConsulta(LocalDate.now());
			}
		} else {
			dialogoAviso.setHeaderText("SELECIONE UMA CONSULTA");
			dialogoAviso.setContentText("Você precisa escolher uma Consulta \npara Confirma Atendimento.");
			dialogoAviso.showAndWait();
		}
	}
	
	@FXML private void clickedCadastrarPaciente(MouseEvent event) {
		logger.info("+++ CADASTRAR PACIENTE INICIADO");
		
		Parent rootCadastrarPaciente;
		try {
			rootCadastrarPaciente = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/CadastrarPaciente.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootCadastrarPaciente, 800, 420);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
    }	
	
	@FXML private void clickedCadastrarAluno(MouseEvent event) {
		logger.info("+++ CADASTRAR ALUNO INICIADO");
		
		Parent rootCadastrarAluno;
		try {
			rootCadastrarAluno = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/CadastrarAluno.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootCadastrarAluno, 800, 500);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@FXML private void clickedCadastrarProfessor(MouseEvent event) {
		logger.info("+++ CADASTRAR PROFESSOR INICIADO");
		
		Parent rootCadastrarProfessor;
		try {
			rootCadastrarProfessor = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/CadastrarProfessor.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootCadastrarProfessor, 800, 500);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedCadastrarAgendamento(MouseEvent event) {
		logger.info("+++ CADASTRAR AGENDAMENTO INICIADO");
		
		Parent rootCadastrarAgendamento;
		try {
			rootCadastrarAgendamento = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/CadastrarAgendamento.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootCadastrarAgendamento, 800, 300);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedAlterarPaciente(MouseEvent event) {
		logger.info("+++ ALTERAR PACIENTE INICIADO");
		
		Parent rootAlterarPaciente;
		try {
			rootAlterarPaciente = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/AlterarPaciente.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootAlterarPaciente, 800, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedAlterarAluno(MouseEvent event) {
		logger.info("+++ ALTERAR ALUNO INICIADO");
		
		Parent rootAlterarAluno;
		try {
			rootAlterarAluno = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/AlterarAluno.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootAlterarAluno, 800, 650);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedAlterarProfessor(MouseEvent event) {
		logger.info("+++ ALTERAR PROFESSOR INICIADO");
		
		Parent rootAlterarProfessor;
		try {
			rootAlterarProfessor = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/AlterarProfessor.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootAlterarProfessor, 800, 650);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}	

	@FXML private void clickedAlterarAgendamento(MouseEvent event) {
		logger.info("+++ ALTERAR AGENDAMENTO INICIADO");
		
		Parent rootAlterarAgendamento;
		try {
			rootAlterarAgendamento = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/AlterarAgendamento.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootAlterarAgendamento, 800, 550);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@FXML private void clickedListarPaciente(MouseEvent event) {
		logger.info("+++ LISTAR PACIENTE INICIADO");
		
		Parent rootListarPaciente;
		try {
			rootListarPaciente = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ListarPaciente.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootListarPaciente, 600, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedListarAluno(MouseEvent event) {
		logger.info("+++ LISTAR ALUNO INICIADO");
		
		Parent rootListarAluno;
		try {
			rootListarAluno = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ListarAluno.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootListarAluno, 600, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedListarProfessor(MouseEvent event) {
		logger.info("+++ LISTAR PROFESSOR INICIADO");
		
		Parent rootListarProfessor;
		try {
			rootListarProfessor = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ListarProfessor.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootListarProfessor, 600, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML private void clickedListarAgendamento(MouseEvent event) {
		logger.info("+++ LISTAR AGENDAMENTO INICIADO");
		
		Parent rootListarAgendamento;
		try {
			rootListarAgendamento = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ListarAgendamento.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootListarAgendamento, 600, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@FXML private void clickedListarConsulta(MouseEvent event) {
		logger.info("+++ LISTAR CONSULTA INICIADO");
		
		Parent rootListarConsulta;
		try {
			rootListarConsulta = FXMLLoader.load(getClass().getResource("/FXML/COMPONENTES/ListarConsulta.fxml"));
			Stage stage = new Stage();
	    	Scene scene = new Scene(rootListarConsulta, 800, 600);
	    	stage.setScene(scene);
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("+++ INICIANDO MAIN VIEW CONTROLLER");
		
		carregarAtributos();		
		carregarMetodos();
	}
	
	private void carregarMetodos() {
		logger.info("+++ CARREGANDO METODOS INICIAIS");
		
		carregarTableViewAgendamentos(LocalDate.now());
		carregarTableViewConsulta(LocalDate.now());
		selecaoTableView();
	}
	
	private void carregarAtributos() {
		logger.info("+++ CARREGANDO ATRIBUTOS INICIAIS");
		
		agendamentoService = new AgendamentoService();
		consultaService = new ConsultaService();
		
		listAgendamentosOriginais = new ArrayList<>();
		
		dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.initStyle(StageStyle.TRANSPARENT);		
		dialogPane = dialogoAviso.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialogWarning");
		
		rowFactory = new StyleChangingRowFactory<>("highlightedRow");
		tableViewConsulta.setRowFactory(rowFactory);
		
		mainViewController = this;
	}
	
	public Agendamento getAgendamentoSelecionado() {
		return agendamentoSelecionado;
	}

	public static MainViewController getInstanceMainViewController() {
		return mainViewController;
	}
	
	public void atualizaTabelas(LocalDate data) {
		logger.info("+++ CARREGANDO TABLE VIEW EXTERNAMENTE");
		
		carregarTableViewAgendamentos(data);
		carregarTableViewConsulta(data);
	}
	
}

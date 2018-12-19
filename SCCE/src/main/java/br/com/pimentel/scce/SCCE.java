package br.com.pimentel.scce;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.controller.ConfiguracaoInicialController;
import br.com.pimentel.scce.controller.StageController;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.ConfiguracaoInicial;
import br.com.pimentel.scce.service.AreaMedicaService;
import br.com.pimentel.scce.service.ConfiguracaoInicialService;
import br.com.pimentel.scce.util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe Principal do Sistema de Controle Clinica Escola - SCCE
 */
public class SCCE extends Application implements Serializable{

	private static final long serialVersionUID = 399245728484336560L;
	
	private static Logger logger = LoggerFactory.getLogger(SCCE.class);
	
	private StageController stageController;
	private ConfiguracaoInicialController configuracaoInicialController;
	private ConfiguracaoInicial configuracaoInicial;
	private ConfiguracaoInicialService configuracaoInicialService;
	private AreaMedicaService areaMedicaService;

	@Override
	public void start(Stage stage) throws Exception {
		logger.info("+++ INICIANDO APLICATIVO EM [{}]", LocalDateTime.now());
		
		configuracaoInicialController = new ConfiguracaoInicialController();
		configuracaoInicialService = new ConfiguracaoInicialService();	
		areaMedicaService = new AreaMedicaService();
		
		if (configuracaoInicialService.buscarTodos().isEmpty()) {
			new ConfiguracaoInicialService().salvar(
					new ConfiguracaoInicial("Sistema de Controle Clinica Escola", "Beta", "/IMAGENS/ICONES/iconeSCCE.png", "123456789"));
		}
		
		if (areaMedicaService.buscarTodos().isEmpty()) {
			new AreaMedicaService().salvar(new AreaMedica("Odontologia")); 
			new AreaMedicaService().salvar(new AreaMedica("Fisioterapia")); 
			new AreaMedicaService().salvar(new AreaMedica("Psicologia")); 
		}
		
		configuracaoInicial = configuracaoInicialController.carregaConfiguracaoInicial();
				
		this.stageController = StageController.instance(stage);
		Parent root = null;
		Scene scene;
		
		logger.info("+++ CARREGADO A CONFIGURAÇÃO INICIAL: {}", configuracaoInicial.toString());
		
		stage.setTitle(configuracaoInicial.getNome());
		stage.getIcons().add(new Image(configuracaoInicial.getIconeURL()));
		stage.setResizable(false);			
		
		stage.centerOnScreen();	
		root = FXMLLoader.load(getClass().getResource("/FXML/MainView.fxml"));
		
		scene = new Scene(root);		
		scene.getStylesheets().add(getClass().getResource("/CSS/scce.css").toExternalForm());		
		scene.setFill(null);
		
		Util.janelaMaximizada(stage);
		
		stageController.getStage().setScene(scene);
		stageController.getStage().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}

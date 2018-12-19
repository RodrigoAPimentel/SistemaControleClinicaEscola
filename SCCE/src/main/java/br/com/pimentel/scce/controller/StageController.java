package br.com.pimentel.scce.controller;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.SCCE;
import br.com.pimentel.scce.util.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageController implements Serializable {

	private static final long serialVersionUID = 945843548334799697L;
	
	private static Logger logger = LoggerFactory.getLogger(StageController.class);
	
	private static StageController stageController = null;
    private Stage stage;

    private StageController(Stage stage) {
    	logger.info("+++ CRIANDO STAGE: {}", stage);
    	
        this.stage = stage;
    }

    public static StageController instance(Stage stage) {
    	logger.info("+++ INSTANCIANDO STAGE: {}", stage);
    	
        if (stageController == null) {
        	stageController = new StageController(stage);
        }
        return stageController;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void loadNewStage(String fxml) throws IOException {
    	logger.info("+++ CARREGANDO FXML: {}", fxml);
    	
    	FXMLLoader fxmlLoad = new FXMLLoader();
    	fxmlLoad.setLocation(SCCE.class.getResource("/FXML/"+fxml+".fxml"));
        if (stage != null) {
            Parent root = fxmlLoad.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Util.janelaMaximizada(stage);
            stage.show();
        }
    }

}

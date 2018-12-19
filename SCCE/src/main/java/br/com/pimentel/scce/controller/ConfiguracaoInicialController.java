package br.com.pimentel.scce.controller;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.model.ConfiguracaoInicial;
import br.com.pimentel.scce.service.ConfiguracaoInicialService;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Controller da classe Configuração Inicial
 */
public class ConfiguracaoInicialController implements Serializable{
	
	private static final long serialVersionUID = 309798460543645388L;
	
	private static Logger logger = LoggerFactory.getLogger(ConfiguracaoInicialController.class);
	
	private ConfiguracaoInicial configuracaoInicial;
	private ConfiguracaoInicialService configuracaoInicialService;
	
	public ConfiguracaoInicialController() {
		super();
		
		configuracaoInicial = new ConfiguracaoInicial();
		configuracaoInicialService = new ConfiguracaoInicialService();
	}
	
	/**
	 * Carrega as Configurações Iniciais
	 * 
	 * @return Configuração Inicial
	 */
	public ConfiguracaoInicial carregaConfiguracaoInicial() {	
		logger.info("+++ CARREGANDO CONFIGURAÇÃO INICIAL");
		
		List<ConfiguracaoInicial> configAUX = configuracaoInicialService.buscarTodos();
		configuracaoInicial = configAUX.get(configAUX.size()-1);		
		
		return configuracaoInicial;
	}	
	
}

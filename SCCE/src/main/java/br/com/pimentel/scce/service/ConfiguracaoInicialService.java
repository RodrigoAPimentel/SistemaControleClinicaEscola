package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.ConfiguracaoInicialDAO;
import br.com.pimentel.scce.model.ConfiguracaoInicial;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Configuração Inicial
 */
public class ConfiguracaoInicialService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(ConfiguracaoInicialService.class);
	
	private ConfiguracaoInicialDAO configuracaoInicialDAO;
	
	public ConfiguracaoInicialService() {
		super();		
		configuracaoInicialDAO = new ConfiguracaoInicialDAO();
	}
	
	public List<ConfiguracaoInicial> buscarTodos(){
		logger.info("+++ BUSCANDO TODAS AS CONFIGURAÇÕES INICIAIS");
		
		return configuracaoInicialDAO.findAll();
	}
	
	public void salvar(ConfiguracaoInicial configuracaoInicial) {
		configuracaoInicialDAO.save(configuracaoInicial);
	}

}

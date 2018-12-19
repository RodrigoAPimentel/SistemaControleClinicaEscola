package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.EnderecoDAO;
import br.com.pimentel.scce.model.Endereco;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Endereço
 */
public class EnderecoService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(EnderecoService.class);
	
	private EnderecoDAO enderecoDAO;
	
	public EnderecoService() {
		super();		
		enderecoDAO = new EnderecoDAO();
	}

	public List<Endereco> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS OS ENDEREÇOS");
		
		return enderecoDAO.findAll();
	}
	
	public void salvar(Endereco endereco) throws Exception{
		logger.info("+++ SALVANDO ENDEREÇO COM CEP: [{}]", endereco.getCep());
		
		enderecoDAO.save(endereco);
	}
	
	public void atualizar(Endereco endereco) throws Exception{
		logger.info("+++ ATUALIZANDO ENDEREÇO COM ID: [{}]  E CEP: [{}]", endereco.getIdEndereco(), endereco.getCep());
		
		enderecoDAO.update(endereco);
	}
}

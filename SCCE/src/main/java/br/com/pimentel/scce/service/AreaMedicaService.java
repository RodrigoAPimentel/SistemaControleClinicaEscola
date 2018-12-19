package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.AreaMedicaDAO;
import br.com.pimentel.scce.model.AreaMedica;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Area Medica
 */
public class AreaMedicaService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(AreaMedicaService.class);
	
	private AreaMedicaDAO areaMedicaDAO;
	
	public AreaMedicaService() {
		super();		
		areaMedicaDAO = new AreaMedicaDAO();
	}

	public List<AreaMedica> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS AS AREAS MEDICAS");
		
		return areaMedicaDAO.findAll();
	}
	
	public AreaMedica buscarPorDescricao(String descricaoAreaMedica) {
		logger.info("+++ BUSCANDO AREA MEDICA PELA DESCRIÇÃO: [{}]", descricaoAreaMedica);
		
		return areaMedicaDAO.findByDescricao(descricaoAreaMedica).get(0);
	}	
	
	public void salvar(AreaMedica nomeAreaMedica) {
		logger.info("+++ SALVANDO AREA MEDICA: [{}]", nomeAreaMedica.getNomeAreaMedica());
		
		areaMedicaDAO.save(nomeAreaMedica);
	}

}

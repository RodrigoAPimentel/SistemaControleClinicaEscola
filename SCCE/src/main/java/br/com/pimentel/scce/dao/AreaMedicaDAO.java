package br.com.pimentel.scce.dao;

import java.util.List;

import br.com.pimentel.scce.model.AreaMedica;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Area Medica
 */
public class AreaMedicaDAO extends DAOGeneric<AreaMedica, Integer>{

	private static final long serialVersionUID = -7690582377766514005L;
	
	@SuppressWarnings("unchecked")
	public List<AreaMedica> findByDescricao(String descricaoAreaMedica) {
		return (List<AreaMedica>) executeQuery("FROM br.com.pimentel.scce.model.AreaMedica WHERE nome_area_medica = ?0", descricaoAreaMedica);	
	}

}

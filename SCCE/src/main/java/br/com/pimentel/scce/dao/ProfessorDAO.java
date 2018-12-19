package br.com.pimentel.scce.dao;

import java.util.List;
import br.com.pimentel.scce.model.Professor;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Professor
 */
public class ProfessorDAO extends DAOGeneric<Professor, String>{

	private static final long serialVersionUID = -7690582377766514005L;
	
	@SuppressWarnings("unchecked")
	public List<Professor> findAllPerAreaMedica(Integer codAreaMedica){		
		return (List<Professor>) executeQuery("FROM br.com.pimentel.scce.model.Professor WHERE areaMedica_codigo_area_medica = ?0", codAreaMedica);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> findByName(String nome) {
		return (List<Professor>) executeQuery("FROM br.com.pimentel.scce.model.Professor WHERE nome = ?0", nome);	
	}

}

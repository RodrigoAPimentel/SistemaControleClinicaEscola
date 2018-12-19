package br.com.pimentel.scce.dao;

import java.util.List;

import br.com.pimentel.scce.model.Aluno;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Aluno
 */
public class AlunoDAO extends DAOGeneric<Aluno, String>{

	private static final long serialVersionUID = -7690582377766514005L;
	
	@SuppressWarnings("unchecked")
	public List<Aluno> findAllPerAreaMedica(Integer codAreaMedica){		
		return (List<Aluno>) executeQuery("FROM br.com.pimentel.scce.model.Aluno WHERE areaMedica_codigo_area_medica = ?0", codAreaMedica);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> findByName(String nome) {
		return (List<Aluno>) executeQuery("FROM br.com.pimentel.scce.model.Aluno WHERE nome = ?0", nome);	
	}

}

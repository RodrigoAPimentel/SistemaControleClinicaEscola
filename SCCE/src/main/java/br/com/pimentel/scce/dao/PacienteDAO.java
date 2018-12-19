package br.com.pimentel.scce.dao;

import java.util.List;

import br.com.pimentel.scce.model.Paciente;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Paciente
 */
@SuppressWarnings("unchecked")
public class PacienteDAO extends DAOGeneric<Paciente, String>{

	private static final long serialVersionUID = -7690582377766514005L;
		
	public List<Paciente> findByName(String nome) {
		return (List<Paciente>) executeQuery("FROM br.com.pimentel.scce.model.Paciente WHERE nome = ?0", nome);	
	}
	
}

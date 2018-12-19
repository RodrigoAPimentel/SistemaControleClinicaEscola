package br.com.pimentel.scce.dao;

import java.time.LocalDate;
import java.util.List;

import br.com.pimentel.scce.model.Consulta;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Consulta
 */
public class ConsultaDAO extends DAOGeneric<Consulta, Integer>{

	private static final long serialVersionUID = -7690582377766514005L;
	
	@SuppressWarnings("unchecked")
	public List<Consulta> findAllPerDate(LocalDate data){		
		return (List<Consulta>) executeQuery("FROM br.com.pimentel.scce.model.Consulta WHERE dataConsulta = ?0", data);		
	}

}

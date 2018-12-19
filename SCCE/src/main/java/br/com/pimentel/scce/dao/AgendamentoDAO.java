package br.com.pimentel.scce.dao;

import java.time.LocalDate;
import java.util.List;

import br.com.pimentel.scce.model.Agendamento;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Data Access Object(DAO) de Agendamento
 */
public class AgendamentoDAO extends DAOGeneric<Agendamento, Integer>{

	private static final long serialVersionUID = -7690582377766514005L;
	
	@SuppressWarnings("unchecked")
	public List<Agendamento> findAllByDate(LocalDate data){		
		return (List<Agendamento>) executeQuery("FROM br.com.pimentel.scce.model.Agendamento WHERE dataConsulta = ?0", data);		
	}	
	
	@SuppressWarnings("unchecked")
	public List<Agendamento> findAllBylDateRange(LocalDate dataInicial, LocalDate dataFinal){		
		return (List<Agendamento>) executeQuery("FROM br.com.pimentel.scce.model.Agendamento WHERE data_consulta BETWEEN ?0 AND ?1", dataInicial, dataFinal);		
	}

}

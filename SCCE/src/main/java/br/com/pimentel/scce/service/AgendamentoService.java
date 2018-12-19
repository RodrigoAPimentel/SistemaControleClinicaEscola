package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.AgendamentoDAO;
import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.StatusAgendamento;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Agendamentos
 */
public class AgendamentoService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(AgendamentoService.class);
	
	private AgendamentoDAO agentamentoDAO;
	
	public AgendamentoService() {
		super();
		
		agentamentoDAO = new AgendamentoDAO();
	}

	public List<Agendamento> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS OS AGENDAMENTOS");
		
		return agentamentoDAO.findAll();
	}
	
	public List<Agendamento> buscarTodosStatusAgendadoPorData(LocalDate data){
		logger.info("+++ BUSCANDO TODOS OS AGENDAMENTOS COM DATA DE CONSULTA: [{}]", data);
		
		List<Agendamento> agendamentos = agentamentoDAO.findAllByDate(data);
		List<Agendamento> agendamentosSituacaoAgendado = new ArrayList<>();
		
		for (Agendamento agendamento : agendamentos) {
			if (agendamento.getSituacaoAgendamento().getStatusAgendamento() == StatusAgendamento.AGENDADO) {
				agendamentosSituacaoAgendado.add(agendamento);
			}
		}
		
		return agendamentosSituacaoAgendado;
	}	
	
	public void atualizar(Agendamento agendamento) throws Exception {
		logger.info("+++ ATUALIZANDO AGENDAMENTO: [{}]", agendamento.getCodigoAgendamento());
		
		agentamentoDAO.update(agendamento);
	}
	
	public void salvar(Agendamento agendamento) throws Exception{
		logger.info("+++ SALVANDO AGENDAMENTO DO PACIENTE: [{}]", agendamento.getPaciente().getNome());
		
		agentamentoDAO.save(agendamento);
	}
	
	public void excluir(Agendamento agendamento) throws Exception{
		logger.info("+++ EXCLUINDO AGENDAMENTO DO PACIENTE: [{}]", agendamento.getPaciente().getNome());
		
		agentamentoDAO.deleteForPK(agendamento.getCodigoAgendamento());
	}

}

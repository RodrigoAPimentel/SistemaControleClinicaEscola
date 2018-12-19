package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.PacienteDAO;
import br.com.pimentel.scce.model.Agendamento;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.model.StatusAgendamento;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Paciente
 */
public class PacienteService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(PacienteService.class);
	
	private PacienteDAO pacienteDAO;
	
	public PacienteService() {
		super();		
		pacienteDAO = new PacienteDAO();
	}

	public List<Paciente> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS OS PACIENTES");
		
		return pacienteDAO.findAll();
	}
	
	public Paciente buscarPorNome(String nome) {
		logger.info("+++ BUSCANDO PACIENTE PELO NOME: [{}]", nome);
		
		if (pacienteDAO.findByName(nome).size() == 0) {
			return null;
		}else {
			return pacienteDAO.findByName(nome).get(0);
		}
		
	}	
	
	public Paciente buscarPorCPF(String cpf) {
		logger.info("+++ BUSCANDO PACIENTE PELO CPF: [{}]", cpf);
		
		if (pacienteDAO.findById(cpf) == null) {
			return null;
		}else {
			return pacienteDAO.findById(cpf);
		}		
	}	
	
	public void salvar(Paciente paciente) throws Exception {
		logger.info("+++ SALVANDO PACIENTE COM CPF: [{}]", paciente.getCpf());
		
		pacienteDAO.save(paciente);
	}
	
	public void atualizar(Paciente paciente) throws Exception {
		logger.info("+++ ATUALIZANDO PACIENTE COM CPF: [{}]", paciente.getCpf());
		
		pacienteDAO.update(paciente);
	}
	
	public void excluir(Paciente paciente) throws Exception {
		logger.info("+++ EXLUINDO PACIENTE COM CPF: [{}]", paciente.getCpf());
		
		pacienteDAO.delete(paciente);
	}
	
	public Boolean verificaLimiteAgendamentos(Paciente paciente) {
		logger.info("+++ VERIFICANDO QUANTIDADE DE AGENDAMENTOS SIMULTANEOS PELO CPF: [{}]", paciente.getCpf());
		
		Integer limiteAgendamentosSimultaneos = 8;
		Integer contadorAgendamento = 0;		
		
		List<Agendamento> listAgendamentos = pacienteDAO.findById(paciente.getCpf()).getAgendamentos();
		
		for (Agendamento agendamento : listAgendamentos) {
			if (agendamento.getSituacaoAgendamento().getStatusAgendamento() == StatusAgendamento.AGENDADO) {
				contadorAgendamento ++;
				continue;
			}
		}
		
		if (contadorAgendamento <= limiteAgendamentosSimultaneos) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean verificaLimiteAreaMedica(Paciente paciente, AreaMedica areaMedica) {
		logger.info("+++ VERIFICANDO QUANTIDADE DE AREAS MEDICAS UTILIZADAS SIMULTANEAMENTE PELO CPF: [{}]", paciente.getCpf());
		
		Boolean area1 = false;
		Boolean area2 = false;
		Boolean area3 = false;	
		
		Integer contadorArea = 0;
		
		List<Agendamento> listAgendamentos = pacienteDAO.findById(paciente.getCpf()).getAgendamentos();
		List<Agendamento> listAgendamentosPorPeriodo = new ArrayList<>();
		
		for (Agendamento agendamento : listAgendamentos) {
			Period periodo = Period.between(agendamento.getDataConsulta(), LocalDate.now());
			
			if (periodo.getMonths() <= 6) {
				listAgendamentosPorPeriodo.add(agendamento);
			}
		}
		
		for (Agendamento agendamento : listAgendamentosPorPeriodo) {
			switch (agendamento.getAreaMedica().getCodigoAreaMedica()) {
			case 1:
				area1 = true;
				continue;
			case 2:
				area2 = true;
				continue;
			case 3:
				area3 = true;
				continue;
			}
		}
		
		switch (areaMedica.getCodigoAreaMedica()) {
		case 1:
			area1 = false;
		case 2:
			area2 = false;
		case 3:
			area3 = false;
		}
		
		List<Boolean> listBoolean = new ArrayList<>();
		listBoolean.add(area1);
		listBoolean.add(area2);
		listBoolean.add(area3);
		
		for (Boolean boolean1 : listBoolean) {
			if (boolean1 == true) {				
				contadorArea ++;
				continue;
			} 
		}
		
		if (contadorArea < 2) {
			return true;
		} else {
			return false;
		}		
	}
	
	public List<Agendamento> buscarAgendamentosAbertos(Paciente paciente){
		logger.info("+++ BUSCANDO AGENDAMENTOS EM ABERTO PELO CPF: [{}]", paciente.getCpf());
		
		List<Agendamento> listAgendamentos = pacienteDAO.findById(paciente.getCpf()).getAgendamentos();
		List<Agendamento> listAgendamentosAbertos = new ArrayList<>();
		
		for (Agendamento agendamento : listAgendamentos) {
			if (agendamento.getSituacaoAgendamento().getStatusAgendamento() == StatusAgendamento.AGENDADO) {
				listAgendamentosAbertos.add(agendamento);
			}
		}
		return listAgendamentosAbertos;
	}

}

package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.ConsultaDAO;
import br.com.pimentel.scce.model.Aluno;
import br.com.pimentel.scce.model.AreaMedica;
import br.com.pimentel.scce.model.Consulta;
import br.com.pimentel.scce.model.Paciente;
import br.com.pimentel.scce.model.Professor;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Consultas
 */
public class ConsultaService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(ConsultaService.class);
	
	private ConsultaDAO consultaDAO;
	
	public ConsultaService() {
		super();
		
		consultaDAO = new ConsultaDAO();
	}

	public List<Consulta> buscarTodos(){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS");
		
		return consultaDAO.findAll();
	}
	
	public List<Consulta> buscarTodosPorAreaMedica(AreaMedica areaMedica){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS POR AREA MEDICA");
		
		List<Consulta> consultasPorAreaMedica = new ArrayList<>();
		List<Consulta> consultas = consultaDAO.findAll();
		
		for (Consulta consulta : consultas) {
			if (consulta.getAgendamento().getAreaMedica().equals(areaMedica)) {
				consultasPorAreaMedica.add(consulta);
			}
		}
		
		return consultasPorAreaMedica;
	}
	
	public List<Consulta> buscarTodosPorPaciente(Paciente paciente){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS POR PACIENTE");
		
		List<Consulta> consultasPorPaciente = new ArrayList<>();
		List<Consulta> consultas = consultaDAO.findAll();
		
		for (Consulta consulta : consultas) {
			if (consulta.getPaciente().equals(paciente)) {
				consultasPorPaciente.add(consulta);
			}
		}
		
		return consultasPorPaciente;
	}
	
	public List<Consulta> buscarTodosPorAluno(Aluno aluno){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS POR ALUNO");
		
		List<Consulta> consultasPorAluno = new ArrayList<>();
		List<Consulta> consultas = consultaDAO.findAll();
		
		for (Consulta consulta : consultas) {
			if (consulta.getAluno().equals(aluno)) {
				consultasPorAluno.add(consulta);
			}
		}
		
		return consultasPorAluno;
	}
	
	public List<Consulta> buscarTodosPorProfessor(Professor professor){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS POR PROFESSOR");
		
		List<Consulta> consultasPorProfessor = new ArrayList<>();
		List<Consulta> consultas = consultaDAO.findAll();
		
		for (Consulta consulta : consultas) {
			if (consulta.getProfessorSupervisor().equals(professor)) {
				consultasPorProfessor.add(consulta);
			}
		}
		
		return consultasPorProfessor;
	}
	
	public List<Consulta> buscarTodosPorData(LocalDate data){
		logger.info("+++ BUSCANDO TODAS AS CONSULTAS COM A DATA: [{}]", data);
		
		return consultaDAO.findAllPerDate(data);
	}
	
	public void salvar(Consulta consulta) throws Exception{
		logger.info("+++ SALVANDO CONSULTA. CRIADA A PARTIR DO AGENDAMENTO: [{}]", consulta.getAgendamento().getCodigoAgendamento());
		
		consultaDAO.save(consulta);
	}
	
	public void confirmaConsultaRealizada(Consulta consulta) throws Exception{
		logger.info("+++ CONFIRMANDO REALIZAÇÃO DA CONSULTA: [{}]", consulta.getAgendamento().getCodigoAgendamento());
		
		consulta.setIsConsultaRealizada(true);
		consultaDAO.update(consulta);
	}	

}

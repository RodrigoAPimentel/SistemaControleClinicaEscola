package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.ProfessorDAO;
import br.com.pimentel.scce.model.Professor;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Professor
 */
public class ProfessorService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(ProfessorService.class);
	
	private ProfessorDAO professorDAO;
	
	public ProfessorService() {
		super();		
		professorDAO = new ProfessorDAO();
	}

	public List<Professor> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS OS PROFESSORES");
		
		return professorDAO.findAll();
	}
	
	public List<Professor> buscarTodosPorAreaMedica(Integer codAreaMedica){
		logger.info("+++ BUSCANDO TODOS OS PROFESSORES PELA AREA MEDICA: [{}]", codAreaMedica);
		
		return professorDAO.findAllPerAreaMedica(codAreaMedica);
	}
	
	public Professor buscarPorNome(String nome) {
		logger.info("+++ BUSCANDO PROFESSOR PELO NOME: [{}]", nome);
		
		return professorDAO.findByName(nome).get(0);
	}	
	
	public Professor buscarPorCPF(String cpf) {
		logger.info("+++ BUSCANDO PROFESSOR PELO CPF: [{}]", cpf);
		
		if (professorDAO.findById(cpf) == null) {
			return null;
		}else {
			return professorDAO.findById(cpf);
		}		
	}	
	
	public void salvar(Professor professor) throws Exception{
		logger.info("+++ SALVANDO PROFESSOR COM CPF: [{}]", professor.getCpf());
		
		professorDAO.save(professor);
	}
	
	public void atualiza(Professor professor) throws Exception{
		logger.info("+++ ATUALIZANDO PROFESSOR COM CPF: [{}]", professor.getCpf());
		
		professorDAO.update(professor);
	}
	
	public void excluir(Professor professor) throws Exception{
		logger.info("+++ EXCLUINDO PROFESSOR COM CPF: [{}]", professor.getCpf());
		
		professorDAO.delete(professor);
	}

}

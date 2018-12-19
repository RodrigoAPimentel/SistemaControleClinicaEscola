package br.com.pimentel.scce.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.AlunoDAO;
import br.com.pimentel.scce.model.Aluno;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Classe de Serviço de Aluno
 */
public class AlunoService implements Serializable{
	
	private static final long serialVersionUID = 6929790093592886455L;

	private static Logger logger = LoggerFactory.getLogger(AlunoService.class);
	
	private AlunoDAO alunoDAO;
	
	public AlunoService() {
		super();		
		alunoDAO = new AlunoDAO();
	}

	public List<Aluno> buscarTodos(){
		logger.info("+++ BUSCANDO TODOS OS ALUNOS");
		
		return alunoDAO.findAll();
	}
	
	public List<Aluno> buscarTodosPorAreaMedica(Integer codAreaMedica){
		logger.info("+++ BUSCANDO TODOS OS ALUNOS PELA AREA MEDICA: [{}]", codAreaMedica);
		
		return alunoDAO.findAllPerAreaMedica(codAreaMedica);
	}
	
	public Aluno buscarPorNome(String nome) {
		logger.info("+++ BUSCANDO ALUNO PELO NOME: [{}]", nome);
		
		if (alunoDAO.findByName(nome).size() == 0) {
			return null;
		} else {
			return alunoDAO.findByName(nome).get(0);
		}		
	}	
	
	public Aluno buscarPorCPF(String cpf) {
		logger.info("+++ BUSCANDO ALUNO PELO CPF: [{}]", cpf);
		
		if (alunoDAO.findById(cpf) == null) {
			return null;
		}else {
			return alunoDAO.findById(cpf);
		}		
	}	
	
	public void salvar(Aluno aluno) throws Exception{
		logger.info("+++ SALVANDO ALUNO COM CPF: [{}]", aluno.getCpf());
		
		alunoDAO.save(aluno);
	}
	
	public void atualizar(Aluno aluno) throws Exception{
		logger.info("+++ ATUALIZANDO ALUNO COM CPF: [{}]", aluno.getCpf());
		
		alunoDAO.update(aluno);
	}
	
	public void excluir(Aluno aluno) throws Exception{
		logger.info("+++ EXLUINDO ALUNO COM CPF: [{}]", aluno.getCpf());
		
		alunoDAO.delete(aluno);
	}

}

package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai um Aluno do tipo Pessoa Fisica
 */
@Entity
public class Aluno extends Pessoa implements Serializable {

	private static final long serialVersionUID = -5622219522720837234L;
	
	@Column(length=20) private String matricula;
	@Column(length=50) private String curso;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno") private List<Consulta> consultas;	
	@ManyToOne private AreaMedica areaMedica;
	
	/**
	 * Cria um objeto que abstrai um Aluno do tipo Pessoa Fisica
	 */
	public Aluno() {
		super();
	}

	/**
	 * Cria um objeto que abstrai um Aluno do tipo Pessoa Fisica
	 * 
	 * @ @param cpf Numero do Cadastro de Pessoa Fisica (CPF)
	 * @param nome Nome completo da Pessoa
	 * @param dataNascimento Data de Nascimento da Pessoa
	 * @param endereco Endereço da Pessoa
	 * @param matricula Matricula do Aluno
	 * @param curso Curso do Aluno
	 * @param areaMedica Area Medica de atuação do Aluno
	 */
	public Aluno(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, String matricula, String curso, AreaMedica areaMedica) {
		super(cpf, nome, dataNascimento, endereco);
		this.matricula = matricula;
		this.curso = curso;
		this.areaMedica = areaMedica;
	}

	/**
	 * Retorna a Matricula do Aluno
	 * @return Matricula do Aluno
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Altera a Matricula do Aluno
	 * 
	 * @param matricula Matricula do Aluno
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * Retorna o Curso do Aluno
	 * 
	 * @return Curso do Aluno
	 */
	public String getCurso() {
		return curso;
	}

	/**
	 * Altera o Curso do Aluno
	 * 
	 * @param curso Curso do Aluno
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}

	/**
	 * Retorna a Lista de Consultas Execultadas pelo Aluno
	 * 
	 * @return Lista de Consultas Execultadas pelo Aluno
	 */
	public List<Consulta> getConsultas() {
		return consultas;
	}

	/**
	 * Altera a Lista de Consultas Execultadas pelo Aluno
	 * 
	 * @param consultas Lista de Consultas Execultadas pelo Aluno
	 */
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
	
	/**
	 * Retorna a Area Medica de atuação do Aluno
	 * 
	 * @return Area Medica de atuação do Aluno
	 */
	public AreaMedica getAreaMedica() {
		return areaMedica;
	}

	/**
	 * Altera a Area Medica de atuação do Aluno
	 * 
	 * @param areaMedica Area Medica de atuação do Aluno
	 */
	public void setAreaMedica(AreaMedica areaMedica) {
		this.areaMedica = areaMedica;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
	
	public class AlunoSimpleProperty {
		private Logger logger = LoggerFactory.getLogger(AlunoSimpleProperty.class);
		private SimpleStringProperty matricula = new SimpleStringProperty();
		private SimpleStringProperty nome = new SimpleStringProperty();
		private SimpleStringProperty curso = new SimpleStringProperty(); 
		private Aluno alunoOriginal;
		public AlunoSimpleProperty(Aluno alunoOriginal) {
			super();
			logger.info("+++ TRANSFORMANDO ALUNO [{}] EM ALUNO SIMPLE PROPERY", alunoOriginal.getMatricula());
			
			this.matricula = new SimpleStringProperty(alunoOriginal.getMatricula());
			this.nome = new SimpleStringProperty(alunoOriginal.getNome());
			this.curso = new SimpleStringProperty(alunoOriginal.getCurso());
			this.alunoOriginal = alunoOriginal;
		}
		public String getMatricula() {
			return matricula.get();
		}
		public String getNome() {
			return nome.get();
		}
		public String getCurso() {
			return curso.get();
		}
		public Aluno getAlunoOriginal() {
			return alunoOriginal;
		}
	}

}

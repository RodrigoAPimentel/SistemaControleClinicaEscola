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

import br.com.pimentel.scce.util.GeradorCodigo;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai um Professor do tipo Pessoa Fisica
 */
@Entity
public class Professor extends Pessoa implements Serializable {

	private static final long serialVersionUID = -1573363622315454654L;
	
	@Column(length=20, name = "codigo_professor") private String codigoProfessor;
	@Column(length=50) private String curso;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professorSupervisor") private List<Consulta> consultas;
	
	@ManyToOne private AreaMedica areaMedica;

	/**
	 * Cria um objeto que abstrai um Professor do tipo Pessoa Fisica
	 */
	public Professor() {
		super();
	}

	/**
	 * Cria um objeto que abstrai um Professor do tipo Pessoa Fisica
	 * 
	 * @param cpf Numero do Cadastro de Pessoa Fisica (CPF)
	 * @param nome Nome completo da Pessoa
	 * @param dataNascimento Data de Nascimento da Pessoa
	 * @param endereco Endereço da Pessoa
	 * @param codigoProfessor Codigo do Professor
	 * @param curso Curso do Aluno
	 * @param areaMedica Area Medica de atuação do Professor
	 */
	public Professor(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, String curso, AreaMedica areaMedica) {
		super(cpf, nome, dataNascimento, endereco);
		this.codigoProfessor = GeradorCodigo.codigoProfessor(cpf, LocalDate.now());
		this.curso = curso;
		this.areaMedica = areaMedica;
	}

	/**
	 * Retorna o Codigo do Professor
	 *  
	 * @return Codigo do Professor
	 */
	public String getCodigoProfessor() {
		return codigoProfessor;
	}

	/**
	 * Altera o Codigo do Professor
	 * 
	 * @param codigoProfessor Codigo do Professor
	 */
	public void setCodigoProfessor(String codigoProfessor) {
		this.codigoProfessor = codigoProfessor;
	}

	/**
	 * Retorna o Curso do Professor
	 * 
	 * @return Curso do Professor
	 */
	public String getCurso() {
		return curso;
	}

	/**
	 * Altera o Curso do Professor
	 * 
	 * @param curso Curso do Professor
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	/**
	 * Retorna a Lista de Consultas Supervisionadas pelo Professor
	 * 
	 * @return Lista de Consultas Supervisionadas pelo Professor
	 */
	public List<Consulta> getConsultas() {
		return consultas;
	}

	/**
	 * Altera a Lista de Consultas Supervisionadas pelo Professor
	 * 
	 * @param consultas Lista de Consultas Supervisionadas pelo Professor
	 */
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	/**
	 * Retorna a Area Medica de atuação do Professor
	 * 
	 * @return Area Medica de atuação do Professor
	 */
	public AreaMedica getAreaMedica() {
		return areaMedica;
	}

	/**
	 * Altera a Area Medica de atuação do Professor
	 * 
	 * @param areaMedica Area Medica de atuação do Professor
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
		result = prime * result + ((codigoProfessor == null) ? 0 : codigoProfessor.hashCode());
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
		Professor other = (Professor) obj;
		if (codigoProfessor == null) {
			if (other.codigoProfessor != null)
				return false;
		} else if (!codigoProfessor.equals(other.codigoProfessor))
			return false;
		return true;
	}
	
	public class ProfessorSimpleProperty {
		private Logger logger = LoggerFactory.getLogger(ProfessorSimpleProperty.class);
		private SimpleStringProperty codigo = new SimpleStringProperty();
		private SimpleStringProperty nome = new SimpleStringProperty();
		private SimpleStringProperty curso = new SimpleStringProperty(); 
		private Professor professorOriginal;
		public ProfessorSimpleProperty(Professor professorOriginal) {
			super();
			logger.info("+++ TRANSFORMANDO PROFESSOR [{}] EM PROFESSOR SIMPLE PROPERY", professorOriginal.getCodigoProfessor());
			
			this.codigo = new SimpleStringProperty(professorOriginal.getCodigoProfessor());
			this.nome = new SimpleStringProperty(professorOriginal.getNome());
			this.curso = new SimpleStringProperty(professorOriginal.getCurso());
			this.professorOriginal = professorOriginal;
		}
		public String getCodigo() {
			return codigo.get();
		}
		public String getNome() {
			return nome.get();
		}
		public String getCurso() {
			return curso.get();
		}
		public Professor getProfessorOriginal() {
			return professorOriginal;
		}
	}

}

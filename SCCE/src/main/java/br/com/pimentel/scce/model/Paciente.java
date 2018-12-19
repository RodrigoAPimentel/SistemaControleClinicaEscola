package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Modela um objeto que abstrai um Paciente do tipo Pessoa Fisica
 */
@Entity
public class Paciente extends Pessoa implements Serializable {

	private static final long serialVersionUID = -5221081513639440725L;
	
	@Column(length=20, name = "codigo_paciente") private String codigoPaciente;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente") private List<Agendamento> agendamentos;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente") private List<Consulta> consultas;
	
	/**
	 * Cria um objeto que abstrai um Paciente do tipo Pessoa Fisica
	 */
	public Paciente() {
		super();
	}

	/**
	 * Cria um objeto que abstrai um Paciente do tipo Pessoa Fisica
	 * 
	 * @param cpf Numero do Cadastro de Pessoa Fisica (CPF)
	 * @param nome Nome completo da Pessoa
	 * @param dataNascimento Data de Nascimento da Pessoa
	 * @param endereco Endereço da Pessoa
	 * @param codigoPaciente Codigo do Paciente
	 */
	public Paciente(String cpf, String nome, LocalDate dataNascimento, Endereco endereco) {
		super(cpf, nome, dataNascimento, endereco);
		this.codigoPaciente = GeradorCodigo.codigoPaciente(cpf, LocalDate.now());
	}
	
	/**
	 * Retorna o Codigo do Paciente
	 * 
	 * @return Codigo do Paciente
	 */
	public String getCodigoPaciente() {
		return codigoPaciente;
	}

	/**
	 * Altera o Codigo do Paciente
	 * 
	 * @param codigoPaciente Codigo do Paciente
	 */
	public void setCodigoPaciente(String codigoPaciente) {
		this.codigoPaciente = codigoPaciente;
	}

	/**
	 * Retorna a Lista de Agendamento do Paciente
	 * 
	 * @return Lista de Agendamento do Paciente
	 */
	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	/**
	 * Altera a Lista de Agendamento do Paciente
	 * 
	 * @param agendamentos Lista de Agendamento do Paciente
	 */
	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	
	/**
	 * Retorna a Lista de Consultas do Paciente 
	 * 
	 * @return Lista de Consultas do Paciente
	 */
	public List<Consulta> getConsultas() {
		return consultas;
	}

	/**
	 * Altera a Lista de Consultas do Paciente 
	 * 
	 * @param consultas Lista de Consultas do Paciente
	 */
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigoPaciente == null) ? 0 : codigoPaciente.hashCode());
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
		Paciente other = (Paciente) obj;
		if (codigoPaciente == null) {
			if (other.codigoPaciente != null)
				return false;
		} else if (!codigoPaciente.equals(other.codigoPaciente))
			return false;
		return true;
	}
	
	public class PacienteSimpleProperty {
		private Logger logger = LoggerFactory.getLogger(PacienteSimpleProperty.class);
		private SimpleStringProperty codigo = new SimpleStringProperty();
		private SimpleStringProperty nome = new SimpleStringProperty();
		private SimpleStringProperty cpf = new SimpleStringProperty();  
		private Paciente pacienteOriginal;
		public PacienteSimpleProperty(Paciente pacienteOriginal) {
			super();
			logger.info("+++ TRANSFORMANDO PACIENTE [{}] EM PACIENTE SIMPLE PROPERY", pacienteOriginal.getCodigoPaciente());
			
			this.codigo = new SimpleStringProperty(pacienteOriginal.getCodigoPaciente());
			this.nome = new SimpleStringProperty(pacienteOriginal.getNome());
			this.cpf = new SimpleStringProperty(pacienteOriginal.getCpf());
			this.pacienteOriginal = pacienteOriginal;
		}
		public String getCodigo() {
			return codigo.get();
		}
		public String getNome() {
			return nome.get();
		}
		public String getCpf() {
			return cpf.get();
		}
		public Paciente getPacienteOriginal() {
			return pacienteOriginal;
		}
	}
	
}

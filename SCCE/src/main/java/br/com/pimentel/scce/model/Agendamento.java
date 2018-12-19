package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai um Agendamento de Consulta
 */
@Entity
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 7422951546534612574L; 
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(length=20, name = "codigo_agendamento") private Integer codigoAgendamento;
	@ManyToOne private Paciente paciente;	
	@OneToOne(cascade = CascadeType.MERGE) private AreaMedica areaMedica;
	@Column(name = "data_agendamento") private LocalDate dataAgendamento;
	@Column(name = "data_consulta") private LocalDate dataConsulta;	
	@Embedded @Column(name = "situacao_agendamento") private SituacaoAgendamento situacaoAgendamento;
	
	/**
	 * Cria um objeto que abstrai um Agendamento de Consulta
	 */
	public Agendamento() {
		super();
	}

	/**
	 * Cria um objeto que abstrai um Agendamento de Consulta
	 * 
	 * @param paciente Paciente que realiza o Agendamento
	 * @param areaMedica Area Medica para o qual o paciente deseja o agendamento
	 * @param dataConsulta Data para a realização da Consulta
	 * @param situacaoAgendamento Situação do Agendamento
	 */
	public Agendamento(Paciente paciente, AreaMedica areaMedica, LocalDate dataAgendamento,
			LocalDate dataConsulta, SituacaoAgendamento situacaoAgendamento) {
		super();
		this.paciente = paciente;
		this.areaMedica = areaMedica;
		this.dataAgendamento = LocalDate.now();
		this.dataConsulta = dataConsulta;
		this.situacaoAgendamento = situacaoAgendamento;
	}
	
	/**
	 * Retorna o Codigo do Agendamento
	 * 
	 * @return Codigo do Agendamento
	 */
	public Integer getCodigoAgendamento() {
		return codigoAgendamento;
	}

	/**
	 * Altera o Codigo do Agendamento
	 * 
	 * @param codigoAgendamento Codigo do Agendamento
	 */
	public void setCodigoAgendamento(Integer codigoAgendamento) {
		this.codigoAgendamento = codigoAgendamento;
	}

	/**
	 * Retorna o Paciente que realiza o Agendamento
	 * 
	 * @return Paciente que realiza o Agendamento
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * Altera o Paciente que realiza o Agendamento
	 * 
	 * @param paciente Paciente que realiza o Agendamento
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * Retorna a Area Medica para o qual o paciente deseja o agendamento
	 * 
	 * @return Area Medica para o qual o paciente deseja o agendamento
	 */
	public AreaMedica getAreaMedica() {
		return areaMedica;
	}

	/**
	 * Altera a Area Medica para o qual o paciente deseja o agendamento
	 * 
	 * @param areaMedica Area Medica para o qual o paciente deseja o agendamento
	 */
	public void setAreaMedica(AreaMedica areaMedica) {
		this.areaMedica = areaMedica;
	}

	/**
	 * Retorna a data que foi realizado o agendamento
	 * 
	 * @return Data que foi realizado o agendamento
	 */
	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}

	/**
	 * Altera a data que foi realizado o agendamento
	 * 
	 * @param dataAgendamento Data que foi realizado o agendamento
	 */
	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	/**
	 * Retorna a Data para a realização da Consulta
	 * 
	 * @return Data para a realização da Consulta
	 */
	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	/**
	 * Altera a Data para a realização da Consulta
	 * 
	 * @param dataConsulta Data para a realização da Consulta
	 */
	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	/**
	 * Retorna a Situação do Agendamento
	 * 
	 * @return Situação do Agendamento
	 */
	public SituacaoAgendamento getSituacaoAgendamento() {
		return situacaoAgendamento;
	}

	/**
	 * Altera a Situação do Agendamento
	 * 
	 * @param situacaoAgendamento Situação do Agendamento
	 */
	public void setSituacaoAgendamento(SituacaoAgendamento situacaoAgendamento) {
		this.situacaoAgendamento = situacaoAgendamento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoAgendamento == null) ? 0 : codigoAgendamento.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (codigoAgendamento == null) {
			if (other.codigoAgendamento != null)
				return false;
		} else if (!codigoAgendamento.equals(other.codigoAgendamento))
			return false;
		return true;
	}
	
	public class AgendamentoSimpleProperty {
		private Logger logger = LoggerFactory.getLogger(AgendamentoSimpleProperty.class);
		private SimpleIntegerProperty codigoAgendamento = new SimpleIntegerProperty();
		private SimpleStringProperty nomePaciente = new SimpleStringProperty();
		private SimpleStringProperty areaMedica = new SimpleStringProperty();  
		private SimpleStringProperty dataConsulta = new SimpleStringProperty();
        private Agendamento agendamentoOriginal;		
		public AgendamentoSimpleProperty(Agendamento agendmentoOriginal) {
			super();
			logger.info("+++ TRANSFORMANDO AGENDAMENTO [{}] EM AGENDAMENTOAUX", agendmentoOriginal.getCodigoAgendamento());
			
			this.codigoAgendamento = new SimpleIntegerProperty(agendmentoOriginal.getCodigoAgendamento());
			this.nomePaciente = new SimpleStringProperty(agendmentoOriginal.getPaciente().getNome());
			this.areaMedica = new SimpleStringProperty(agendmentoOriginal.getAreaMedica().getNomeAreaMedica());
			this.dataConsulta = new SimpleStringProperty(agendmentoOriginal.getDataConsulta().format(DateTimeFormatter.ofPattern ("dd/MM/yyyy")));
			this.agendamentoOriginal = agendmentoOriginal;
		}
		public Integer getCodigoAgendamento() {
			return codigoAgendamento.get();
		}
		public String getNomePaciente() {
			return nomePaciente.get();
		}
		public String getAreaMedica() {
			return areaMedica.get();
		}
		public String getDataConsulta() {
			return dataConsulta.get();
		}
		public Agendamento getAgendamentoOriginal() {
			return agendamentoOriginal;
		}		
	}
}

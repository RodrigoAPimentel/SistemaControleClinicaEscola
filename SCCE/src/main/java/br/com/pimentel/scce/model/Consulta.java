package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pimentel.scce.dao.AgendamentoDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai uma Consulta
 */
@Entity
public class Consulta implements Serializable {

	private static final long serialVersionUID = -7707829887136847903L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(length=20, name = "codigo_consulta") private Integer codigoConsulta;
	@OneToOne(cascade = CascadeType.MERGE) private Agendamento agendamento;
	@ManyToOne private Aluno aluno;
	@Column(name = "data_consulta") private LocalDate dataConsulta;
	@ManyToOne private Professor professorSupervisor;	
	@ManyToOne private Paciente paciente;
	private Boolean isConsultaRealizada;
	
	/**
	 * Cria um objeto que abstrai uma Consulta
	 */
	public Consulta() {
		super();
	}

	/**
	 * Cria um objeto que abstrai uma Consulta
	 * 
	 * @param agendamento Agendamento realizado para a Consulta
	 * @param aluno Aluno que ira participar da Consulta
	 * @param professorSupervisor Professor que ira Supervisionar a Consulta
	 * @throws Exception 
	 */
	public Consulta(Agendamento agendamento, Aluno aluno, Professor professorSupervisor) throws Exception {
		super();
		this.agendamento = agendamento;
		this.aluno = aluno;
		this.dataConsulta = LocalDate.now();
		this.professorSupervisor = professorSupervisor;		
		this.paciente = agendamento.getPaciente();
		this.isConsultaRealizada = false;
		
		agendamento.getSituacaoAgendamento().setStatusAgendamento(StatusAgendamento.CONCLUIDO);
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		agendamentoDAO.update(agendamento);
	}

	/**
	 * Retorna o Codigo da Consulta
	 * 
	 * @return Codigo da Consulta
	 */
	public Integer getCodigoConsulta() {
		return codigoConsulta;
	}

	/**
	 * Altera o Codigo da Consulta
	 * 
	 * @param codigoConsulta Codigo da Consulta
	 */
	public void setCodigoConsulta(Integer codigoConsulta) {
		this.codigoConsulta = codigoConsulta;
	}

	/**
	 * Retorna o Agendamento para a Consulta
	 * 
	 * @return Agendamento para a Consulta
	 */
	public Agendamento getAgendamento() {
		return agendamento;
	}

	/**
	 * Altera o Agendamento para a Consulta
	 * 
	 * @param agendamento Agendamento para a Consulta
	 */
	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	/**
	 * Retorna o Aluno que ira participar da Consulta
	 * 
	 * @return Aluno que ira participar da Consulta
	 */
	public Aluno getAluno() {
		return aluno;
	}

	/**
	 * Altera o Aluno que ira participar da Consulta
	 * 
	 * @param aluno Aluno que ira participar da Consulta
	 */
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	/**
	 * Retorna a Data da Consulta
	 * 
	 * @return Data da Consulta
	 */
	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	/**
	 * Altera a Data da Consulta
	 * 
	 * @param dataConsulta Data da Consulta
	 */
	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	/**
	 * Retorna o Professor que ira Supervisionar a Consulta
	 * @return Professor que ira Supervisionar a Consulta
	 */
	public Professor getProfessorSupervisor() {
		return professorSupervisor;
	}

	/**
	 * Altera o Professor que ira Supervisionar a Consulta
	 * 
	 * @param professorSupervisor Professor que ira Supervisionar a Consulta
	 */
	public void setProfessorSupervisor(Professor professorSupervisor) {
		this.professorSupervisor = professorSupervisor;
	}

	/**
	 * Retorna o Paciente da Consulta
	 * 
	 * @return Paciente da Consulta
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * Altera o Paciente da Consulta
	 * 
	 * @param paciente Paciente da Consulta
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * Retorna se a Consulta foi Realizada
	 * 
	 * @return Se a Consulta foi Realizada
	 */
	public Boolean getIsConsultaRealizada() {
		return isConsultaRealizada;
	}

	/**
	 * Altera se a Consulta foi Realizada
	 * 
	 * @param isConsultaRealizada Se a Consulta foi Realizada
	 */
	public void setIsConsultaRealizada(Boolean isConsultaRealizada) {
		this.isConsultaRealizada = isConsultaRealizada;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoConsulta == null) ? 0 : codigoConsulta.hashCode());
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
		Consulta other = (Consulta) obj;
		if (codigoConsulta == null) {
			if (other.codigoConsulta != null)
				return false;
		} else if (!codigoConsulta.equals(other.codigoConsulta))
			return false;
		return true;
	}
	
	public class ConsultaSimpleProperty {
		private Logger logger = LoggerFactory.getLogger(ConsultaSimpleProperty.class);
		private SimpleIntegerProperty codigoConsulta = new SimpleIntegerProperty();
		private SimpleStringProperty nomePaciente = new SimpleStringProperty();
		private SimpleStringProperty areaMedica = new SimpleStringProperty();
		private SimpleStringProperty nomeAluno = new SimpleStringProperty();
		private SimpleStringProperty nomeProfessor = new SimpleStringProperty();
		private SimpleStringProperty dataConsulta = new SimpleStringProperty();
		private SimpleBooleanProperty isConsultaRealizada = new SimpleBooleanProperty();
		private Consulta consultaOriginal;
		public ConsultaSimpleProperty(Consulta consultaOriginal) {
			super();
			logger.info("+++ TRANSFORMANDO CONSULTA [{}] EM CONSULTAAUX", consultaOriginal.getCodigoConsulta());
			
			this.codigoConsulta = new SimpleIntegerProperty(consultaOriginal.getCodigoConsulta());
			this.nomePaciente = new SimpleStringProperty(consultaOriginal.getAgendamento().getPaciente().getNome());
			this.areaMedica = new SimpleStringProperty(consultaOriginal.getAgendamento().getAreaMedica().getNomeAreaMedica());
			this.nomeAluno = new SimpleStringProperty(consultaOriginal.getAluno().getNome());
			this.nomeProfessor = new SimpleStringProperty(consultaOriginal.getProfessorSupervisor().getNome());
			this.dataConsulta = new SimpleStringProperty(consultaOriginal.getDataConsulta().format(DateTimeFormatter.ofPattern ("dd/MM/yyyy")));
			this.isConsultaRealizada = new SimpleBooleanProperty(consultaOriginal.getIsConsultaRealizada());
			this.consultaOriginal = consultaOriginal;
		}
		public Integer getCodigoConsulta() {
			return codigoConsulta.get();
		}
		public String getNomePaciente() {
			return nomePaciente.get();
		}
		public String getAreaMedica() {
			return areaMedica.get();
		}
		public String getNomeAluno() {
			return nomeAluno.get();
		}
		public String getNomeProfessor() {
			return nomeProfessor.get();
		}
		public String getDataConsulta() {
			return dataConsulta.get();
		}
		public Boolean getIsConsultaRealizada() {
			return isConsultaRealizada.get();
		}
		public Consulta getConsultaOriginal() {
			return consultaOriginal;
		}		
	}
	
}

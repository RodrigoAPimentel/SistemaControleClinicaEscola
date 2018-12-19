/**
 * 
 */
package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um Status para o Agendamento de Consulta
 */
@Embeddable
public class SituacaoAgendamento implements Serializable {

	private static final long serialVersionUID = 8447843746675235629L;
	
	@Enumerated(EnumType.STRING) @Column(name = "situacao_agendamento_status") private StatusAgendamento statusAgendamento;
	@Column(name = "situacao_agendamento_motivo_cancelamento_adiamento") private String motivoCancelamentoAdiamento;
	@Column(name = "situacao_agendamento_data_ultima_modificação") private LocalDate dataModificacao;
	
	/**
	 * Cria um objeto que abstrai uma Situação para o Agendamento
	 */
	public SituacaoAgendamento() {
		super();
	}
	
	/**
	 * Cria um objeto que abstrai uma Situação para o Agendamento
	 * 
	 * @param statusAgendamento Status para o Agendamento
	 * @param motivo Motivo
	 * @param dataModificacao Data da Ultima Modificação
	 */
	public SituacaoAgendamento(StatusAgendamento statusAgendamento, String motivoCancelamentoAdiamento, LocalDate dataModificacao) {
		super();
		this.statusAgendamento = statusAgendamento;
		this.motivoCancelamentoAdiamento = motivoCancelamentoAdiamento;
		this.dataModificacao = dataModificacao;
	}

	/**
	 * Retorna o Status do Agendamento
	 * 
	 * @return Status do Agendamento
	 */
	public StatusAgendamento getStatusAgendamento() {
		return statusAgendamento;
	}

	/**
	 * Altera o Status do Agendamento
	 * 
	 * @param statusAgendamento Status do Agendamento
	 */
	public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}

	/**
	 * Retorna o Motivo do Status
	 * 
	 * @return Motivo do Status
	 */
	public String getMotivoCancelamentoAdiamento() {
		return motivoCancelamentoAdiamento;
	}

	/**
	 * Altera o Motivo do Status
	 * 
	 * @param motivo Motivo do Status
	 */
	public void setMotivoCancelamentoAdiamento(String motivoCancelamentoAdiamento) {
		this.motivoCancelamentoAdiamento = motivoCancelamentoAdiamento;
	}

	/**
	 * Retorna a Data da Ultima Modificação do Status
	 * 
	 * @return Data da Ultima Modificação do Status
	 */
	public LocalDate getDataModificacao() {
		return dataModificacao;
	}

	/**
	 * Altera a Data da Ultima Modificação do Status
	 * 
	 * @param dataModificacao Data da Ultima Modificação do Status
	 */
	public void setDataModificacao(LocalDate dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((motivoCancelamentoAdiamento == null) ? 0 : motivoCancelamentoAdiamento.hashCode());
		result = prime * result + ((dataModificacao == null) ? 0 : dataModificacao.hashCode());
		result = prime * result + ((statusAgendamento == null) ? 0 : statusAgendamento.hashCode());
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
		SituacaoAgendamento other = (SituacaoAgendamento) obj;
		if (motivoCancelamentoAdiamento == null) {
			if (other.motivoCancelamentoAdiamento != null)
				return false;
		} else if (!motivoCancelamentoAdiamento.equals(other.motivoCancelamentoAdiamento))
			return false;
		if (dataModificacao == null) {
			if (other.dataModificacao != null)
				return false;
		} else if (!dataModificacao.equals(other.dataModificacao))
			return false;
		if (statusAgendamento != other.statusAgendamento)
			return false;
		return true;
	}

}

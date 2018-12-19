package br.com.pimentel.scce.model;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Status para Agendamento
 */
public enum StatusAgendamento {
	
	AGENDADO("Agendado"),
	CANCELADO("Cancelado"),
	REMARCADO("Remarcado"),
	CONCLUIDO("Concluido"),;
	
	private String descricaoStatusAgendamento;

	/**
	 * @param descricaoStatusAgendamento Descrição do Status do Agendamento
	 */
	private StatusAgendamento(String descricaoStatusAgendamento) {
		this.descricaoStatusAgendamento = descricaoStatusAgendamento;
	}

	/**
	 * @return Retorna a Descrição do Status do Agendamento
	 */
	public String getDescricaoStatusAgendamento() {
		return descricaoStatusAgendamento;
	}

}

package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai uma Pessoa Fisica
 */
@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa implements Serializable{

	private static final long serialVersionUID = 7682560092917994481L;
	
	@Id @Column(length=15) private String cpf;
	@Column(length=100) private String nome;
	private LocalDate dataNascimento;
	@OneToOne(cascade = CascadeType.MERGE) private Endereco endereco;
	
	/**
	 * Cria um objeto do tipo Pessoa
	 */
	public Pessoa() {
		super();
	}

	/**
	 * Cria um objeto do tipo Pessoa
	 * 
	 * @param cpf Numero do Cadastro de Pessoa Fisica (CPF)
	 * @param nome Nome completo da Pessoa
	 * @param dataNascimento Data de Nascimento da Pessoa
	 * @param endereco Endereço da Pessoa
	 */
	public Pessoa(String cpf, String nome, LocalDate dataNascimento, Endereco endereco) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}

	/**
	 * Retorna o numero do Cadastro de Pessoa Fisica (CPF)
	 * 
	 * @return Numero do Cadastro de Pessoa Fisica (CPF)
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * Altera o numero do Cadastro de Pessoa Fisica (CPF)
	 * 
	 * @param cpf Numero do Cadastro de Pessoa Fisica (CPF)
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * Retorna o nome completo da Pessoa Fisica
	 * 
	 * @return Nome completo da Pessoa
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Altera o nome completo da Pessoa Fisica
	 * 
	 * @param nome Nome completo da Pessoa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna a Data de Nascimento da Pessoa
	 * 
	 * @return Data de Nascimento da Pessoa
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Altera a Data de Nascimento da Pessoa
	 * 
	 * @param dataNascimento Data de Nascimento da Pessoa
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Retorna um objeto do tipo Endereço com o endereço da Pessoa
	 * 
	 * @return Endereço da Pessoa
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * Altera um objeto do tipo Endereço com o endereço da Pessoa
	 * 
	 * @param endereco Endereço da Pessoa
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
}

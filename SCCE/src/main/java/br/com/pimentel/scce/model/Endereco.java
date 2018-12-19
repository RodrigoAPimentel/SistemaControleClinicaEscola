/**
 * 
 */
package br.com.pimentel.scce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Modela um objeto que abstrai um Endereço
 */
@Entity
public class Endereco implements Serializable {

	private static final long serialVersionUID = 2416972180288659812L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(length=10, name = "id_endereco") private Integer idEndereco;
	@Column(length=10) private String cep;
	@Column(length=50) private String rua;
	@Column(length=6) private Integer numero;
	@Column(length=20) private String bairro;
	@Column(length=20) private String cidade;
	@Column(length=20) private String estado;
	@Column(length=30) private String complemento;
	@Column(length=20) private String pais;
	@Column(length=15) private String telefone;
	@Column(length=50) private String email;

	public Endereco() {
		super();
	}

	/**
	 * Cria um objeto Endereço
	 * 
	 * @param cep Numero do CEP
	 * @param rua Nome da Rua
	 * @param numero Numero do domicilio
	 * @param bairro Bairro
	 * @param cidade Cidade
	 * @param estado Estado
	 * @param complemento Complemento ao endereço
	 * @param pais Pais
	 * @param telefone Numero de telefone
	 * @param email Endereço de Email
	 */
	public Endereco(String cep, String rua, Integer numero, String bairro, String cidade, String estado,
			String complemento, String pais, String telefone, String email) {
		super();
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.complemento = complemento;
		this.pais = pais;
		this.telefone = telefone;
		this.email = email;
	}

	/**
	 * Retorna o ID de Endereço
	 * 
	 * @return ID de Endereço
	 */
	public Integer getIdEndereco() {
		return idEndereco;
	}

	/**
	 * Altera o ID de Endereço
	 * 
	 * @param idEndereco ID de Endereço
	 */
	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	/**
	 * Retorna o numero do CEP
	 * @return Numero do CEP
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * Altera o numero do CEP
	 * @param cep Numero do CEP
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/** 
	 * Retorna o nome da Rua
	 * @return Nome da Rua
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * Altera o nome da Rua
	 * @param rua Nome da Rua
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * Retorna o numero do Imovel
	 * @return Numero do Imovel
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * Altera o numero do Imovel
	 * @param numero Numero do Imovel
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * Retorna o Bairro
	 * @return Bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Altera o Bairro
	 * @param bairro Bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * Retorna a Cidade
	 * @return Cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Altera a Cidade
	 * @param cidade Cidade
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Retorna o Estado da Federação
	 * @return Estado da Federação
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Altera o Estado da Federação
	 * @param estado Estado da Federação
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Retorna o Complemento ao Endereço
	 * @return Complemento ao Endereço
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * Altera o Complemento ao Endereço
	 * @param complemento Complemento ao Endereço
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * Retorna o Pais
	 * @return Pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Altera o Pais
	 * @param pais Pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Retorna o numero de telefone
	 * @return numero de telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Altera o numero de telefone
	 * @param telefone numero de telefone
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Retorna o endereço de E-Mail
	 * @return endereço de E-Mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Altera o endereço de E-Mail
	 * @param email1 endereço de E-Mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
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
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		return true;
	}

}

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
 * Modela um objeto que contem as informações para a Configuração Inicial
 */
@Entity
public class ConfiguracaoInicial implements Serializable{

	private static final long serialVersionUID = -5339759027315379459L;
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)	private Integer id;
	private String nome;
	private String versao;
	@Column(name = "icone_url") private String iconeURL;	
	private String serial;
	
	public ConfiguracaoInicial() {
		super();
	}

	public ConfiguracaoInicial(String nome, String versao, String iconeURL, String serial) {
		super();
		this.nome = nome;
		this.versao = versao;
		this.iconeURL = iconeURL;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getIconeURL() {
		return iconeURL;
	}

	public void setIconeURL(String icone) {
		this.iconeURL = icone;
	}

	@Override
	public String toString() {
		return "ConfiguracaoInicial [id=" + id + ", nome=" + nome + ", versao=" + versao + ", iconeURL=" + iconeURL
				+ ", serial=" + serial + "]";
	}
	
}

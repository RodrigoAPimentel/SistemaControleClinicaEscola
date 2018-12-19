package br.com.pimentel.scce.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 * 
 * Cria as Areas Medicas de Atendimento
 */
@Entity
public class AreaMedica implements Serializable {

	private static final long serialVersionUID = -4356298624549540109L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "codigo_area_medica")private Integer codigoAreaMedica;
	@Column(length=20, name = "nome_area_medica") private String nomeAreaMedica;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "areaMedica") private List<Professor> professores;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "areaMedica") private List<Aluno> alunos;
	
	/**
	 * Cria uma Area Medica de Atendimento
	 */
	public AreaMedica() {
		super();
	}

	/**
	 * Cria uma Area Medica de Atendimento
	 * 
	 * @param descricaoAreaMedica Nome da Area Medica de Atendimento
	 * @param professores Professores que atuam na Area Medica de Atendimento
	 * @param alunos Alunos que atuam na Area Medica de Atendimento
	 */
	public AreaMedica(String nomeAreaMedica) {
		super();
		this.nomeAreaMedica = nomeAreaMedica;
	}

	/**
	 * Retorna o Codigo da Area Medica
	 * 
	 * @return Codigo da Area Medica
	 */
	public Integer getCodigoAreaMedica() {
		return codigoAreaMedica;
	}

	/**
	 * Altera o Codigo da Area Medica
	 * 
	 * @param codigoAreaMedica Codigo da Area Medica
	 */
	public void setCodigoAreaMedica(Integer codigoAreaMedica) {
		this.codigoAreaMedica = codigoAreaMedica;
	}

	/**
	 * Retorna o Nome da Area Medica
	 *  
	 * @return Nome da Area Medica
	 */
	public String getNomeAreaMedica() {
		return nomeAreaMedica;
	}

	/**
	 * Altera o Nome da Area Medica
	 * 
	 * @param nomeAreaMedica Nome da Area Medica
	 */
	public void setNomeAreaMedica(String nomeAreaMedica) {
		this.nomeAreaMedica = nomeAreaMedica;
	}

	/**
	 * Retorna Lista de Professores que atuam nesta Area Medica
	 * 
	 * @return Lista de Professores que atuam nesta Area Medica
	 */
	public List<Professor> getProfessores() {
		return professores;
	}

	/**
	 * Altera Lista de Professores que atuam nesta Area Medica
	 * 
	 * @param professores Lista de Professores que atuam nesta Area Medica
	 */
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	/**
	 * Retorna Lista de Alunos que atuam nesta Area Medica
	 * 
	 * @return Lista de Alunos que atuam nesta Area Medica
	 */
	public List<Aluno> getAlunos() {
		return alunos;
	}

	/**
	 * Altera Lista de Alunos que atuam nesta Area Medica
	 * 
	 * @param alunos Lista de Alunos que atuam nesta Area Medica
	 */
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoAreaMedica == null) ? 0 : codigoAreaMedica.hashCode());
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
		AreaMedica other = (AreaMedica) obj;
		if (codigoAreaMedica == null) {
			if (other.codigoAreaMedica != null)
				return false;
		} else if (!codigoAreaMedica.equals(other.codigoAreaMedica))
			return false;
		return true;
	}
	
}

package br.com.pimentel.scce.util;

import java.time.LocalDate;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rodrigo Pimentel
 *
 * SCCE
 *
 * Classe que gera codigo 
 */
public abstract class GeradorCodigo {
	
	private static Logger logger = LoggerFactory.getLogger(GeradorCodigo.class);
	
	private static final String PREFIXOPACIENTE = "1";
	private static final String PREFIXOPROFESSOR = "2";
	
	private GeradorCodigo() {
		super();
	}

	/**
	 * Criar um codigo para o paciente do tipo pessoa fisica de forma unica
	 * 
	 * @param cpf Recebe o CPF do paciente
	 * @param dataAtual Recebe a data de cadastro do paciente
	 * @return Retorna um codigo unico
	 */
	public static String codigoPaciente(String cpf, LocalDate dataAtual) {	
		char[] indiceCpf = cpf.toCharArray();		
		int a = 100 + new Random().nextInt(899);
		
		String codigo = PREFIXOPACIENTE+indiceCpf[0]+dataAtual.getYear()+indiceCpf[4]+a+indiceCpf[8];
		
		logger.info("+++ GERANDO CODIGO DE PACIENTE COM CPF: {} E DATA: {}. CODIGO: [{}]", cpf, dataAtual, codigo);
		
		return codigo;
	}
	
	/**
	 * Criar um codigo para o professor do tipo pessoa fisica de forma unica
	 * 
	 * @param cpf Recebe o CPF do professor
	 * @param dataAtual Recebe a data de cadastro do professor
	 * @return Retorna um codigo unico
	 */
	public static String codigoProfessor(String cpf, LocalDate dataAtual) {	
		char[] indiceCpf = cpf.toCharArray();		
		int a = 100 + new Random().nextInt(899);
		
		String codigo = PREFIXOPROFESSOR+indiceCpf[0]+dataAtual.getYear()+indiceCpf[4]+a+indiceCpf[8];
		
		logger.info("+++ GERANDO CODIGO DE PROFESSOR COM CPF: {} E DATA: {}. CODIGO: [{}]", cpf, dataAtual, codigo);
		
		return codigo;
	}
		
}
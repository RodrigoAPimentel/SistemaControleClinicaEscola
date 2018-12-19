package br.com.pimentel.scce.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAOGeneric<T, PK> implements Serializable {

	private static final long serialVersionUID = 3967123262108326155L;
	
	private static Logger logger = LoggerFactory.getLogger(DAOGeneric.class);

	protected final EntityManager entityManager;

	private final EntityManagerFactory factory;
	
	private static final String PERSISTENCE_UNIT_NAME = "SCCEPersistenceUnit";
	
	private Class<?> clazz;

	///////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	///////////////////////////////////////////////////////////////////

	public DAOGeneric() {
		this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		this.entityManager = factory.createEntityManager();
		this.clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	///////////////////////////////////////////////////////////////////
	// CRUD Methods
	///////////////////////////////////////////////////////////////////

	public Object executeQuery(String query, Object... params) {
		Query createdQuery = this.entityManager.createQuery(query);
		
		for (int i = 0; i < params.length; i++) {
			createdQuery.setParameter(i, params[i]);
		}
		
		logger.info("+++ CRIANDO QUERY COM OS PARAMETROS: [QUERY: {}, PARAMETROS: {}]", query, params);

		return createdQuery.getResultList();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		logger.info("+++ EXECULTANDO FINDALL NA CLASSE: {}", this.clazz.getName());
		
		return this.entityManager.createQuery(("FROM " + this.clazz.getName())).getResultList();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public T findById(PK pk) {
		logger.info("+++ EXECULTANDO FINDBYID NA CLASSE: {}, COM ID: {}", this.clazz.getName(), pk);
		
		return (T) this.entityManager.find(this.clazz, pk);				
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void  save(T entity) {
		try {
			this.beginTransaction();
			
			Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<T>> erros = validador.validate(entity);
			if (erros.size() > 0) {
				for (ConstraintViolation<T> constraintViolation : erros) {
					System.out.println("Erro: " + constraintViolation.getMessage());
				}
			}else {
				this.entityManager.persist(entity);				
			}
			
			logger.info("+++ SALVANDO NA CLASSE: {}, O OBJETO: {}", this.clazz.getName(), entity);
			
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void update(T entity) throws Exception {
		try {
			this.beginTransaction();
			
			Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<T>> erros = validador.validate(entity);
			if (erros.size() > 0) {
				for (ConstraintViolation<T> constraintViolation : erros) {
					System.out.println("Erro: " + constraintViolation.getMessage());
				}
			}else {
				this.entityManager.merge(entity);				
			}			
			
			logger.info("+++ ATUALIZANDO NA CLASSE: {}, O OBJETO: {}", this.clazz.getName(), entity);
			
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void delete(T entity) throws Exception {
		try {
			this.beginTransaction();
			this.entityManager.remove(entity);
			
			logger.info("+++ DELETANDO NA CLASSE: {}, O OBJETO: {}", this.clazz.getName(), entity);
			
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void deleteForPK(PK pk) throws Exception {
		try {
			this.beginTransaction();
			this.entityManager.remove(this.entityManager.find(this.clazz, pk));
			
			logger.info("+++ DELETANDO NA CLASSE: {}, O OBJETO DE PK: {}", this.clazz.getName(), pk);
			
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}

	///////////////////////////////////////////////////////////////////
	// Transaction Methods
	///////////////////////////////////////////////////////////////////

	public void beginTransaction(){
		logger.info("+++ INICIANDO TRANSAÇÃO COM BANCO DE DADOS");
		
		this.entityManager.getTransaction().begin();
	}

	public void commit(){
		logger.info("+++ COMMIT NO BANCO DE DADOS");
		
		this.entityManager.getTransaction().commit();
	}

	public void close(){
		logger.info("+++ FECHANDO TRANSAÇÃO COM BANCO DE DADOS");
		
		this.entityManager.close();
		this.factory.close();
	}

	public void rollBack(){
		logger.info("+++ ROLLBACK TRANSAÇÃO NO BANCO DE DADOS");
		
		this.entityManager.getTransaction().rollback();
	}

	public EntityManager getEntityManager(){
		return this.entityManager;
	}

}

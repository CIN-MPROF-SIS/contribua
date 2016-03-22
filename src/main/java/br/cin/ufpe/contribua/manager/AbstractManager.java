package br.cin.ufpe.contribua.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.cin.ufpe.contribua.model.AbstractModel;

public abstract class AbstractManager<T extends AbstractModel> {

	private Class<T> entityClass;

	@PersistenceContext
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public AbstractManager() {

	}

	/*
	 * public AbstractManager(){ //System.out.println("------" +
	 * getClass().getGenericSuperclass().toString()); //ParameterizedType
	 * parameterizedType = (ParameterizedType)
	 * getClass().getGenericSuperclass(); //this.manager = (Class<Manager>)
	 * parameterizedType.getActualTypeArguments()[0]; //T entidade =
	 * (T)getClass().getGenericSuperclass(); //this.entityClass =
	 * (Class<T>)parameterizedType.getActualTypeArguments()[0]; }
	 */

	public AbstractManager(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
		Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}
		for (Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T findWithNamedQueryUniq(String namedQueryName, Map parameters) {
		Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);

		for (Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		try {
			return (T) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}

	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}
}
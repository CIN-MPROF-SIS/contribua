package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.AbstractModel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractManager<T extends AbstractModel> {

    private Class<T> entityClass;
    
    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    public AbstractManager(){
        
    }
    
    /*public AbstractManager(){
        //System.out.println("------" + getClass().getGenericSuperclass().toString());
        //ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        //this.manager = (Class<Manager>) parameterizedType.getActualTypeArguments()[0];
        //T entidade = (T)getClass().getGenericSuperclass();
        //this.entityClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }*/
    
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
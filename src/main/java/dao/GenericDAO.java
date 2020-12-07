package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericDAO<E> {

    private Class<E> persistedClass;

    public GenericDAO(Class<E> persistedClass) {
        this.persistedClass = persistedClass;
    }

    public E executeNamedQuery(String name, HashMap<String, Object> params) {
        E result = null;
        EntityManager em = DB.getEntityManager();
        try {
            TypedQuery<E> query = em.createNamedQuery(name, this.persistedClass);
            for (String param : params.keySet()) {
                Object value = params.get(param);
                query.setParameter(param, value);
            }
            result = (E) query.getSingleResult();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e);
        } finally {
            em.close();
        }
        return result;
    }

    public E save(E entity) {
        EntityManager em = DB.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.refresh(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e);
        } finally {
            em.close();
        }
        return entity;
    }

    public E find(Integer id) {
        EntityManager em = DB.getEntityManager();
        E entity = null;
        try {
            entity = em.find(this.persistedClass, id);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return entity;
    }

    public List findAll() {
        EntityManager em = DB.getEntityManager();
        List<E> entities = null;
        try {
            Query query = em.createQuery("from " + persistedClass.getSimpleName());
            entities = (List<E>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        if(entities == null){
            entities = new ArrayList<E>();
        }
        return entities;
    }

    public void update(E entity) {
        EntityManager em = DB.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e);
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = DB.getEntityManager();
        E entity = null;
        try {
            entity = em.find(this.persistedClass, id);
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e);
        } finally {
            em.close();
        }
    }

    public List findAll(String args) {
        EntityManager em = DB.getEntityManager();
        List<E> entities = null;
        try {
            String sql = String.format("FROM %s %s", this.persistedClass.getSimpleName(), args);
            Query query = em.createQuery(sql);
            entities = (List<E>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        if(entities == null){
            entities = new ArrayList<E>();
        }
        return entities;
    }

    public BigDecimal sumAll(String column) {
        return this.sumAll(column, "");
    }

    public BigDecimal sumAll(String column, String args) {
        EntityManager em = DB.getEntityManager();
        BigDecimal value = null;
        try {
            String sql = String.format("SELECT SUM(%s) FROM %s %s",column, this.persistedClass.getSimpleName(), args);
            Query query = em.createQuery(sql);
            value = (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return value;
    }

    public Integer countAll(String column) {
        return this.countAll(column, "");
    }

    public Integer countAll(String column, String args) {
        EntityManager em = DB.getEntityManager();
        Integer value = null;
        try {
            String sql = String.format("SELECT COUNT(%s)FROM %s %s",column, this.persistedClass.getSimpleName(), args);
            Query query = em.createQuery(sql);
            value = (Integer) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return value;
    }

}

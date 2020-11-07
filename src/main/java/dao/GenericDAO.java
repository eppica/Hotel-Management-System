package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

public class GenericDAO<E> {

    private Class<E> persistedClass;

    public GenericDAO(Class<E> persistedClass) {
        this.persistedClass = persistedClass;
    }

    public E executeNamedQuery(String name, HashMap<String, Object> params) {
        E result = null;
        EntityManager em = DB.getConnection();
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
        EntityManager em = DB.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(entity);
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
        EntityManager em = DB.getConnection();
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
        EntityManager em = DB.getConnection();
        List<E> entities = null;
        try {
            Query query = em.createQuery("from " + persistedClass.getName());
            entities = (List<E>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return entities;
    }

    public void update(E entity) {
        EntityManager em = DB.getConnection();
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
        EntityManager em = DB.getConnection();
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
        EntityManager em = DB.getConnection();
        List<E> entities = null;
        try {
            Query query = em.createQuery("from " + this.persistedClass.getName() + " " + args);
            entities = (List<E>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return entities;
    }
}

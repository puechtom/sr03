package services;

import models.Step;
import exceptions.DataNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by tompu on 30/04/2017.
 */
public class StepService {

    public void addStep(Step step) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(step);
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        session.getTransaction().commit();
    }

    public List<Step> getAllSteps() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select s from Step s");
        List<Step> steps = query.list();
        session.getTransaction().commit();
        return steps;
    }

    public Step getStep(Integer id, UriInfo uriInfo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Object obj = session.get(Step.class, id);
        if (obj == null) {
            throw new DataNotFoundException("Step with id " + id + " not found");
        }
        return (Step)obj;
    }

    public void deleteStep(Integer id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Object obj = session.get(Step.class, id);
        if (obj == null) {
            throw new DataNotFoundException("Step with id " + id + " not found");
        }
        session.delete(obj);
        session.getTransaction().commit();
    }

    public void updateStep(Step step) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(step);
        session.getTransaction().commit();
    }

}

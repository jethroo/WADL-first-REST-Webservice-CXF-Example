package de.jethroo.rest.example;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
@Repository
public class ThingyDao {

    private SessionFactory sessionFactory;
 
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    public void saveOrUpdate(Thingy person) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(person);
        session.getTransaction().commit();
    }
    
    public Thingy findById(int id) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Thingy thing = (Thingy)session.get(Thingy.class, id);
        session.getTransaction().commit();
        return thing;
    }
 
    @SuppressWarnings("unchecked")
	public List<Thingy> selectAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Thingy.class);
        List<Thingy> thingies = (List<Thingy>) criteria.list();
        session.getTransaction().commit();
        return thingies;
    }
 
}
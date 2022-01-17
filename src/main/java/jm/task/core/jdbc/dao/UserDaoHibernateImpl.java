package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection ();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection ().openSession ()) {
            transaction =session.beginTransaction ();
            session.createNativeQuery ("CREATE TABLE IF NOT EXISTS userss" +
                    " (id mediumint not null auto_increment, name VARCHAR(50), " +
                    "lastname VARCHAR(50), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit ();
            System.out.println ("Table new");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback ();
            }
            e.printStackTrace ();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection ().openSession ()) {
            transaction = session.beginTransaction ();
            session.createNativeQuery("DROP TABLE IF EXISTS userss").executeUpdate ();
            transaction.commit ();
            System.out.println ("Table drop");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback ();
            }
            e.printStackTrace ();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getConnection ().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User (name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getConnection ().openSession ()) {
            transaction = session.beginTransaction ();
            session.delete (session.get (User.class, id));
            transaction.commit ();
        }catch (HibernateException e){
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
        }
    }



    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = null;
        try (Session session = Util.getConnection ().openSession ()) {
            transaction = session.beginTransaction ();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder ().createQuery (User.class);
            criteriaQuery.from (User.class);
            userList = session.createQuery (criteriaQuery).getResultList ();
            transaction.commit ();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback ();
            }
            e.printStackTrace ();
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection ().openSession ()) {
            transaction = session.beginTransaction ();
            session.createNativeQuery ("TRUNCATE TABLE userss").executeUpdate ();
            transaction.commit ();
            System.out.println ("Table delete");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback ();
            }
            e.printStackTrace ();
        }

    }
}

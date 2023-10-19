package com.example.lab05.dao;

import com.example.lab05.model.User;
import com.example.lab05.repository.Repository;
import com.example.lab05.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO implements Repository<User, Long> {
    @Override
    public Long add(User item) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Long id = (Long) session.save(item);
            session.getTransaction().commit();
            return id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User get(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            User u = session.find(User.class, id);
            session.getTransaction().commit();
            return u;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getUserByEmail(String email) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM User u WHERE u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);

            User user = query.uniqueResult();

            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }
}

package com.example.lab05.dao;

import com.example.lab05.model.Product;
import com.example.lab05.repository.Repository;
import com.example.lab05.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAO implements Repository<Product, Long> {
    @Override
    public Long add(Product item) {
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
    public Product get(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Product> product = session.createQuery("FROM Product", Product.class).list();
            session.getTransaction().commit();
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean remove(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Product p = (Product)session.load(Product.class,id);
            session.delete(p);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void update(Product product) {
        Transaction transaction = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

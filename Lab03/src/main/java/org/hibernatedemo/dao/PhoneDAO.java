package org.hibernatedemo.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernatedemo.domain.Phone;
import org.hibernatedemo.repository.Repository;
import org.hibernatedemo.utils.HibernateUtils;

import java.util.List;

public class PhoneDAO implements Repository<Phone, Long> {

    @Override
    public Long add(Phone item) {
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
    public Phone get(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Phone p = session.find(Phone.class, id);
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean remove(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Phone p = (Phone)session.load(Phone.class,id);
            session.delete(p);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Phone item) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Phone p = session.get(Phone.class, item.getId());
            p.setColor(item.getColor());
            p.setName(item.getName());
            p.setPrice(item.getPrice());
            p.setQuantity(item.getQuantity());
            p.setCountry(item.getCountry());
            p.setManufacturer(item.getManufacturer());
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Phone> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Phone> phones = session.createQuery("FROM Phone", Phone.class).list();
            session.getTransaction().commit();
            return  phones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Phone> getHighestPricePhone() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Phone> phones = session.createQuery("FROM Phone p WHERE p.price = (SELECT MAX(price) FROM Phone)", Phone.class).list();
            session.getTransaction().commit();
            return phones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Phone> sortPhoneByCountryAndPrice() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Phone> phones = session.createQuery("FROM Phone p order by p.country, p.price desc", Phone.class).list();
            session.getTransaction().commit();
            return phones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Phone> getPhoneHigherThanPrice(int price) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query<Phone> query  = session.createQuery("FROM Phone p WHERE p.price > :price", Phone.class);
            query.setParameter("price", price);
            List<Phone> phones = query.getResultList();
            session.getTransaction().commit();
            return phones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Phone> getPhoneByColorAndGreaterThanPrice(int price, String color) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query<Phone> query  = session.createQuery("FROM Phone p WHERE p.color like :color and p.price > :price", Phone.class);
            query.setParameter("color", color);
            query.setParameter("price", price);
            List<Phone> phones = query.getResultList();
            session.getTransaction().commit();
            return phones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

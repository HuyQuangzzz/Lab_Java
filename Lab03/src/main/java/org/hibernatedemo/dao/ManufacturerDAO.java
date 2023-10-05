package org.hibernatedemo.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernatedemo.domain.Manufacturer;
import org.hibernatedemo.domain.Manufacturer;
import org.hibernatedemo.exception.InvalidOperationException;
import org.hibernatedemo.repository.Repository;
import org.hibernatedemo.utils.HibernateUtils;

import javax.naming.InvalidNameException;
import java.util.List;

public class ManufacturerDAO implements Repository<Manufacturer, Long> {
    @Override
    public Long add(Manufacturer item) {
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
    public Manufacturer get(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Manufacturer m = session.find(Manufacturer.class, id);
            session.getTransaction().commit();
            return m;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean remove(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Manufacturer m = (Manufacturer)session.load(Manufacturer.class,id);
            session.delete(m);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Manufacturer item) {
        try(Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Manufacturer m = session.get(Manufacturer.class, item.getId());
            m.setEmployee(item.getEmployee());
            m.setName(item.getName());
            m.setLocation(item.getLocation());
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Manufacturer> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Manufacturer> manufacturers = session.createQuery("FROM Manufacturer", Manufacturer.class).list();
            session.getTransaction().commit();
            return  manufacturers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Manufacturer> checkManuHaveMoreThanEmployees(int employee) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query<Manufacturer> query = session.createQuery("FROM Manufacturer m WHERE m.employee > :employee", Manufacturer.class);
            query.setParameter("employee", employee);
            List<Manufacturer> manufacturers = query.getResultList();
            session.getTransaction().commit();
            return  manufacturers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public long getSumEmployee() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT SUM(m.employee) FROM Manufacturer m", Long.class);
            long sum = query.getSingleResult();
            session.getTransaction().commit();
            return sum;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Manufacturer getLastLocationManufacturer(String location) throws InvalidOperationException {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            session.beginTransaction();

            Query<Manufacturer> query = session.createQuery("FROM Manufacturer m WHERE m.location like :location ORDER BY m.id DESC", Manufacturer.class);
            query.setParameter("location", location);
            query.setMaxResults(1);
            List<Manufacturer> manufacturers = query.getResultList();
            session.getTransaction().commit();

            return manufacturers.get(0);
        } catch (Exception e) {
            throw new InvalidOperationException("There is no producer that meets the above criteria");
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory;
    private static Session session;

    static {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTable = "create table if not exists users(id bigint not null auto_increment primary key, name varchar(50) not null, lastName varchar(50) not null, age tinyint not null)";
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(createTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Fail to create table");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTable = "drop table if exists users";
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(dropTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Fail to drop table users");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.getCurrentSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.printf("User с именем - %s добавлен в базу данных%n", name);
        } catch (Exception e) {
            System.out.println("Fail to save table users");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.printf("User с id - %s добавлен в базу данных%n", id);
        } catch (Exception e) {
            System.out.println("Fail to delete user");
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            listUser = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Fail to get all users");
            session.getTransaction().rollback();
        }
        return  listUser;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table is cleaned");
        } catch (Exception e) {
            System.out.println("Fail to clean table");
            session.getTransaction().rollback();
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException  {
        String createTable = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT primary key, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate(createTable);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Fail to create table");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void dropUsersTable() throws SQLException {
        String dropTable = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute(dropTable);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Fail to create table");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String saveUser = "INSERT INTO users(name, lastName, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Fail to save user");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        String removeUser = "DELETE FROM users WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Fail to delete user with id = " + id);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String selectAll = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            ResultSet rs = statement.executeQuery(selectAll);
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                user.setId(rs.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Fail to get all users");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        String truncateTable = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate(truncateTable);
        } catch (SQLException e) {
            System.out.println("Fail to truncate table Users");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}

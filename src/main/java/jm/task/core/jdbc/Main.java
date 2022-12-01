package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService us = new UserServiceImpl();

        us.createUsersTable();

        us.saveUser("Ira", "Bobrova", (byte) 32);
        us.saveUser("Leonid", "Karishev", (byte) 49);
        us.saveUser("Angelina", "Droblena", (byte) 27);
        us.saveUser("Evgeny", "Krik", (byte) 10);

        List<User> users = us.getAllUsers();
        System.out.println(users);

        us.cleanUsersTable();
        us.dropUsersTable();

    }
}

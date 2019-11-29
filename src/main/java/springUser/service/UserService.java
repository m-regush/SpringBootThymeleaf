package springUser.service;

import springUser.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void addUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void deleteUser(Long id);
    void updateUser(User user);
    User getUserByName(String name) throws SQLException;
    User getUserById(Long id) throws SQLException;
    String getPasswordByName(String name);


}

package springUser.service;

import springUser.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User addUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void deleteUser(Long id);
    User updateUser(User user) throws SQLException;
    User getUserByName(String name) throws SQLException;
    User getUserById(Long id) throws SQLException;
    String getPasswordByName(String name);


}

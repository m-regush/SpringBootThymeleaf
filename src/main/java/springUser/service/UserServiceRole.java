package springUser.service;

import springUser.model.UserRole;

import java.sql.SQLException;

public interface UserServiceRole {

     UserRole getUserRole(String role) throws SQLException;
}

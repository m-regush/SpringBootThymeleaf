package springUser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springUser.dao.UserRoleDAO;
import springUser.model.UserRole;

import java.sql.SQLException;

@Service
public class UserServiceRoleImpl implements UserServiceRole {

    private UserRoleDAO userRoleDAO;

    public UserServiceRoleImpl() {

    }

    @Autowired
    public UserServiceRoleImpl(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    @Transactional
    public UserRole getUserRole(String role) throws SQLException {
        return userRoleDAO.getUserRole(role);
    }

}

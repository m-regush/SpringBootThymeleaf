package springUser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springUser.dao.UserDAO;
import springUser.model.User;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService  {

    private UserDAO userHibernateDAO;

    @Autowired
    public UserServiceImpl(UserDAO userHibernateDAO) {
        this.userHibernateDAO = userHibernateDAO;

    }
    @Transactional
    public User addUser(User user) throws SQLException {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        userHibernateDAO.addUser(user);
        return user;
    }

    @Transactional
    public List<User> getAllUsers() throws SQLException {
        return userHibernateDAO.getAllUser();
    }

    @Transactional
    public void deleteUser(Long id) {
        userHibernateDAO.deleteUser(id);
    }

    @Transactional
    public User updateUser(User user) throws SQLException {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
//        if (user.getPassword().equals(userHibernateDAO.getPasswordByName(user.getName()))) {
//            user.setPassword(user.getPassword());
//        } else {
//            user.setPassword(password);
//        }
        userHibernateDAO.updateUser(user);
        return user;
    }

    @Transactional
    public User getUserByName(String name) throws SQLException {
       return userHibernateDAO.getUserByName(name);
    }

    @Transactional
    public User getUserById(Long id) throws SQLException {
        return userHibernateDAO.getUserById(id);
    }

    @Override
    public String getPasswordByName(String name) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
           user = userHibernateDAO.getUserByName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

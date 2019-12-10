package springUser.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springUser.model.User;
import springUser.model.UserRole;
import springUser.service.UserServiceRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("userDao")
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;
    private UserServiceRole roleService;

    @Autowired
    public UserDAOImpl(UserServiceRole roleService) {
        this.roleService = roleService;
    }

    public List<User> getAllUser() throws SQLException {
      return entityManager.createQuery("FROM User").getResultList();
    }


    public void addUser(User user) throws SQLException {
        user.setRoles(getRoleSet(user));
        entityManager.persist(user);
    }


    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }


    public void updateUser(User user) throws SQLException {
        user.setRoles(getRoleSet(user));
        entityManager.merge(user);
    }


    public User getUserByName(String name) throws SQLException {
        String sql = "FROM User WHERE name= :name";
        Query query = entityManager.createQuery(sql);
        query.setParameter("name", name);
        return (User) query.getSingleResult();

    }


    public User getUserById(Long id) throws SQLException {
        return entityManager.find(User.class, id);
    }

    private Set<UserRole> getRoleSet(User user) throws SQLException {
        UserRole roleAdmin = roleService.getUserRole("ADMIN");
        UserRole roleUser = roleService.getUserRole("USER");
        Set<UserRole> roleSet = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            if (role.getRole().equals("ADMIN")) {
                roleSet.add(roleAdmin);
            }
            if (role.getRole().equals("USER")) {
                roleSet.add(roleUser);
            }
        }
        return roleSet;
    }



//
//    public String getPasswordByName(String name) {
//        String sql = "SELECT password FROM User WHERE name= :name";
//        Query query = entityManager.createQuery(sql);
//        query.setParameter("name", name);
//        return (String) query.getSingleResult();
//    }


}

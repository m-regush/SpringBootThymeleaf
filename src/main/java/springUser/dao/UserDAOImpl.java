package springUser.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springUser.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    public List<User> getAllUser() throws SQLException {
      return entityManager.createQuery("FROM User").getResultList();
    }


    public void addUser(User user) throws SQLException {
        entityManager.persist(user);
    }


    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }


    public void updateUser(User user) {
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


    public String getPasswordByName(String name) {
        String sql = "SELECT password FROM User WHERE name= :name";
        Query query = entityManager.createQuery(sql);
        query.setParameter("name", name);
        return (String) query.getSingleResult();
    }


}

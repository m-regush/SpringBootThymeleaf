package springUser.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springUser.model.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;

@Repository
public class UserRoleDAO {

    @Autowired
    private EntityManager entityManager;



    public UserRole getUserRole(String role) throws SQLException {
       String sql = "FROM UserRole WHERE role= :role";
       Query query = entityManager.createQuery(sql);
       query.setParameter("role", role);
       UserRole userRole = (UserRole) query.getSingleResult();
       return userRole;

    }

}

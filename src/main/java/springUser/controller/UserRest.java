package springUser.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springUser.model.User;
import springUser.model.UserRole;
import springUser.service.UserService;
import springUser.service.UserServiceRole;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class UserRest {

    private UserService userService;
    private UserServiceRole userServiceRole;

    @Autowired
    public UserRest(UserService userService, UserServiceRole userServiceRole) {
        this.userService = userService;
        this.userServiceRole = userServiceRole;
    }

    @GetMapping(value = "/admin/users")
    public List<User> userList() throws SQLException {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/admin/add")
    public User addUser(@RequestBody User user /*@RequestParam(value = "roles", required = false) String role*/) throws SQLException {
//        Set<UserRole> roles = new HashSet<>();
//        if (role.equals("ADMIN,USER")) {
//            String[] res = role.split(",");
//            roles.add(userServiceRole.getUserRole(res[0]));
//            roles.add(userServiceRole.getUserRole(res[1]));
//            user.setRoles(roles);
//        } else if (role.equals("ADMIN") || role.equals("USER")) {
//            roles.add(userServiceRole.getUserRole(role));
//            user.setRoles(roles);
//        }
        userService.addUser(user);
        return userService.getUserById(user.getId());
    }

    @GetMapping(value = "/admin/update/{id}")
    public User getUserById(@PathVariable Long id) throws SQLException {
        return userService.getUserById(id);

    }

    @PutMapping(value = "/admin/update")
    public User updateUser(@RequestBody User user /* @RequestParam("roles") String role, @RequestParam("id") Long id*/) throws SQLException {
//        Set<UserRole> roles = new HashSet<>();
//        User user1 = userService.getUserById(id);
//        if(user1.getRoles().size() > 1) {
//            user1.getRoles().clear();
//            roles.add(userServiceRole.getUserRole(role));
//            user.setRoles(roles);
//        } else {
//            user1.getRoles().add(userServiceRole.getUserRole(role));
//            user.setRoles(user1.getRoles());
//        }
        userService.updateUser(user);
        return userService.getUserById(user.getId());
    }

    @DeleteMapping(value = "/admin/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id)  {
       userService.deleteUser(id);
    }


}

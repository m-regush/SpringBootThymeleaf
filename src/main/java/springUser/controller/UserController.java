package springUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springUser.model.User;
import springUser.model.UserRole;
import springUser.service.UserService;
import springUser.service.UserServiceRole;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;
    private UserServiceRole userServiceRole;

    @Autowired
    public void setUserService(UserService userService, UserServiceRole userServiceRole) {
        this.userService = userService;
        this.userServiceRole = userServiceRole;

    }

    @RequestMapping(value = "/admin/users")
    public ModelAndView listUsers(ModelAndView model) throws SQLException {
        List<User> listUsers = userService.getAllUsers();
        model.addObject("listUsers", listUsers);
        model.setViewName("AdminPage");
        return model;
    }

    @RequestMapping(value = "/admin/newUser", method = RequestMethod.GET)
    public ModelAndView newUser(ModelAndView model) {
        User user = new User();
        model.addObject("user", user);
        model.setViewName("AddUser");
        return model;
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute User user, @RequestParam("role") String role) throws SQLException {
        ModelAndView model = new ModelAndView();
        Set<UserRole> roles = new HashSet<>();
        if (role.equals("ADMIN,USER")) {
                String[] res = role.split(",");
                roles.add(userServiceRole.getUserRole(res[0]));
                roles.add(userServiceRole.getUserRole(res[1]));
                user.setUserRoles(roles);
                userService.addUser(user);
                model.setViewName("redirect:/admin/users");
        } else if (role.equals("ADMIN") || role.equals("USER")) {
                roles.add(userServiceRole.getUserRole(role));
                user.setUserRoles(roles);
                userService.addUser(user);
                model.setViewName("redirect:/admin/users");
        } else {
            model.setViewName("redirect:/admin/add");
        }

        return model;
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/admin/users");
    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.GET)
    public ModelAndView updatePage (@PathVariable(name = "id") Long id) throws SQLException {
        User user = userService.getUserById(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("UpdateUser");
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ModelAndView updateUser (@ModelAttribute("user") User user,  @RequestParam("role") String role, @RequestParam("id") Long id) throws SQLException {
        ModelAndView model = new ModelAndView();
        Set<UserRole> roles = new HashSet<>();
        User user1 = userService.getUserById(id);
        if(user1.getUserRoles().size() > 1 && user1.getPassword().equals(user.getPassword())) {
            user1.getUserRoles().clear();
            roles.add(userServiceRole.getUserRole(role));
            user.setUserRoles(roles);
            userService.updateUser(user);
        } else if (user1.getPassword().equals(user.getPassword())) {
            user1.getUserRoles().add(userServiceRole.getUserRole(role));
            user.setUserRoles(user1.getUserRoles());
            userService.updateUser(user);
        }
        model.setViewName("redirect:/admin/users");
        return model;

//        Set<UserRole> userRoles = user.getUserRoles();
//        Set<UserRole> roles = Collections.singleton(userServiceRole.getRoleById(Long.parseLong(role)));
//        if (userService.getUserById(user.getId()) != null) {
//            user.setUserRoles(roles);
//            userService.updateUser(user);
//            model.setViewName("redirect:/admin/users");
//        } else {
//            model.setViewName("redirect:/add");
//        }
//        model.addObject("user", user);
//        return new ModelAndView("redirect:/admin/users");
    }

}

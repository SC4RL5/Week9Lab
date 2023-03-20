package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author Swift
 */
public class UserService {

    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String email, String firstName, String lastName, String password, int roleID) throws Exception {
        Role role = new Role(roleID);
        User user = new User(email, firstName, lastName, password);
        user.setRole(role);
        
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    public void update(String email, String firstName, String lastName, String password, int roleID) throws Exception {
        UserDB userDB = new UserDB();
        Role role = new Role(roleID);
        User user = userDB.get(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        
        userDB.update(user);
    }

    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        userDB.delete(user);
    }

}
package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password      
        return invalidName(username) || invalidPassword(password);
    }
    
    private boolean invalidName(String username){
        return invalidLength(username, 3) || 
                invalidPattern("[a-z]*", username);
    }
    
    private boolean invalidPassword(String password){        
        return invalidLength(password, 8) ||
                invalidPattern(".*[\\d\\W].*", password);
    }
    
    private boolean invalidLength(String string, int minimumLength){
        return string.length() < minimumLength;
    }
    
    private boolean invalidPattern(String pattern, String string){
        return !Pattern.matches(pattern, string);
    }
}

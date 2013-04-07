/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author markus
 */

public class FileUserDao implements UserDao {

    String fileName;

    public FileUserDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<User> listAll() {
        ArrayList<User> users = new ArrayList<User>();
        Scanner scanner = createScanner();
        if (scanner == null) {
            return users;
        }
        while (scanner.hasNextLine()) {
            String[] userData = scanner.nextLine().split(";");
            users.add(new User(userData[0], userData[1]));
        }
        return users;
    }

    @Override
    public User findByName(String name) {
        List<User> users = listAll();
        for (User user : users) {
            if (user.getUsername().equals(name)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            FileWriter writer = new FileWriter(new File(fileName), true);
            writer.append(user.getUsername() + ";" + user.getPassword() + "\n");
            writer.close();
        } catch (IOException e) {

        }       
    }

    private Scanner createScanner() {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            return scanner;
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}

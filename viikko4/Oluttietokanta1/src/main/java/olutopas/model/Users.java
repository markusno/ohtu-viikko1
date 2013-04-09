/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author markus
 */

@Entity
public class Users {
    @Id
    private Integer id;
    
    private String username;

    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

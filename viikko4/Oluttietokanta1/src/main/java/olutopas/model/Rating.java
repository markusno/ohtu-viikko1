/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author markus
 */

@Entity
public class Rating {
    
    private int points;
    
    @Id
    private Integer id;
    
    @ManyToOne
    private Beer beer;
    
    
    @ManyToOne
    private Users user;
    
    public Rating() {
    }

    public Rating(int points, Beer beer, Users user) {
        this.points = points;
        this.beer = beer;
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return beer.toString() + " " + points + " points"; 
    }
    
    
}

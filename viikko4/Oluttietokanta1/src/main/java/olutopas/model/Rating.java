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

//@Entity
public class Rating {
    
    private int points;
    
    @Id
    private Integer id;
    
    public Rating() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}

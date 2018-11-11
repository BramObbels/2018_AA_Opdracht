/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Plays API bean.
 * Retrieves all the upcoming plays and allows to add and remove plays.
 * @author Dylan Van Assche
 */
@Stateless
public class PlaysBean implements PlaysBeanRemote {
    @PersistenceContext private EntityManager em;
    
    @Override
    public ArrayList<Object> getUpcomingPlays() {
        ArrayList<Object> plays = new ArrayList<Object>(em.createNamedQuery("Plays.findAll").getResultList());
        return plays;
    }
}

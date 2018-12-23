/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Accounts;
import entities.Groups;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bram Obbels
 */
@Stateless
public class AccountBean implements AccountBeanRemote {
    @PersistenceContext private EntityManager em;

    @Override
    public boolean checkUsername(String username){
        boolean exists = false;
        Query q; // Find object by given username
        q = em.createNamedQuery("Accounts.findByUsername");
        q.setParameter("username", username);
        ArrayList<Accounts> accounts = (ArrayList<Accounts>) q.getResultList();
        System.out.print(accounts.size());
        if(accounts.size() > 0){
            exists = true;
        }
        return exists;
    }
    
    @Override
    public int getIdByUsername(String username) {
        Query q;
        q = em.createNamedQuery("Accounts.findByUsername");
        q.setParameter("username", username);
        Accounts account = (Accounts) q.getSingleResult();
        return account.getId();
    }
    
    @Override
    public Object getAccountById(int id) {
        try {
            Query q;
            q = em.createNamedQuery("Accounts.findById");
            q.setParameter("id", id);
            return q.getSingleResult();
        }
        catch(Exception error) {
            System.err.println("Cannot find account, make sure you use a valid ID! Supplied ID: " + id);
            return null;
        }
    }
    
    @Override
    public void addAccount(String username, String password){
        Accounts account = new Accounts();
        Groups group = new Groups();
       
        int lastAccountId = (int)em.createNamedQuery("Accounts.findLastId").getSingleResult();
        int lastGroupId = (int)em.createNamedQuery("Groups.findLastId").getSingleResult();
        
        account.setId(lastAccountId + 1); 
        account.setUsername(username);
        account.setPassword(password);
        account = em.merge(account);       
        em.persist(account);
        
        group.setId(lastGroupId +1);
        group.setGroupname("members");
        group.setUsername(username);
        group = em.merge(group);       
        em.persist(group);
    }
}

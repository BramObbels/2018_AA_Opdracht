/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Remote;

/**
 *
 * @author Bram
 */
@Remote
public interface AccountBeanRemote {
    public boolean checkUsername(String username);
    public int getIdByUsername(String username);
    public Object getAccountById(int id);
    public void addAccount(String username, String password);
}

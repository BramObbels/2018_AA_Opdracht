/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 * Plays API bossiness interface.
 * Retrieves all the upcoming plays and allows to add and remove plays.
 * Use this interface in case you want to access the EE bean. 
 * @author Dylan Van Assche
 */
@Remote
public interface PlaysBeanRemote {
    public ArrayList<Object> getUpcomingPlays();
}

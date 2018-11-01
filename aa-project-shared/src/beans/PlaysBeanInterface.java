/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 * Interface for the PlaysBean.
 * Use this business interface to interact with PlaysBean.
 * 
 * @see https://github.com/BramObbels/2018_AA_Opdracht/issues/2
 * @author Dylan Van Assche
 */
@Remote
public interface PlaysBeanInterface {
    public ArrayList<Object> getUpcomingPlays();
}

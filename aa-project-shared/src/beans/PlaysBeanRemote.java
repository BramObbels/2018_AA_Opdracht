package beans;

import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Remote;

/**
 * Plays API business interface.
 * Retrieves all the upcoming plays and allows to add and remove plays.
 * Use this interface in case you want to access the EE bean. 
 * @author Dylan Van Assche
 */
@Remote
public interface PlaysBeanRemote {
    public ArrayList<Object> getUpcomingPlays();
    public ArrayList<Object> getAllPlays();
    public void removePlay(int id);
    public void addPlay(String name, Date date, float basicPrice, float rankFee);
}

package beans;

import entities.Plays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 * The plays API to provide a list of upcoming plays. Fetches the list of plays
 * from the database and exposes this list to the front end. The list of plays
 * is sorted by play date in ascending order.
 * 
 * /!\ Use the business interface if you want to use this bean!
 *
 * @see https://github.com/BramObbels/2018_AA_Opdracht/issues/2
 * @author Dylan Van Assche
 */
@Stateless
public class PlaysBean implements PlaysBeanInterface {
    private EntityManager em;

    /**
     * Retrieves the upcoming plays.
     * Fetches the plays from the database, converts it to an ArrayList<Object>
     * with only the plays that are playing now and in the future.
     * 
     * @return ArrayList<Object> plays
     */
    @Override
    public ArrayList<Object> getUpcomingPlays() {
        List<Plays> temp = em.createNamedQuery("Plays.findAll").getResultList();
        Date now = new Date();
        ArrayList<Object> plays = new ArrayList<>(); // Diamond operator

        for (Plays p : temp) {
            // Only plays that are happening now and in the future
            if (p.getDate().getTime() > now.getTime()) {
                // Upcasting since the interface can't access the entity classes
                plays.add(((Object) p)); 
            }
        }
        return plays;
    }
}

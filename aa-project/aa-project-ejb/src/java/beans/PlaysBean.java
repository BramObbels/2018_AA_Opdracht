package beans;

import entities.Plays;
import entities.Seats;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Plays API bean.
 * Retrieves all the upcoming plays and allows to add and remove plays.
 * @author Dylan Van Assche
 */
@Stateless
public class PlaysBean implements PlaysBeanRemote {
    @PersistenceContext private EntityManager em;
    
    /**
     * Get upcoming plays.
     * Retrieves all the upcoming plays. If you want all the plays, see getAllPlays().
     * @see getAllPlays()
     * @author Dylan Van Assche
     */
    @Override
    public ArrayList<Object> getUpcomingPlays() {
        // Convert to ArrayList for easy access
        List<Plays> temp = em.createNamedQuery("Plays.findAll").getResultList();
        ArrayList<Object> plays = new ArrayList<Object>();
        Date now = new Date();
        
        // Filter for upcoming plays
        for(Plays p : temp) {
            Collection<Seats> seatList = p.getSeatsCollection();
            boolean availableSeats = false;
            for(Seats s : seatList) {
                if(s.getStatus() == Seats.AVAILABLE) {
                    availableSeats = true;
                    break;
                }
            }
            
            if(p.getDate().getTime() < now.getTime() && availableSeats) {
                // Administrators can see all the plays, members and public only the plays that aren't sold out
                plays.add(p);
            }
        }
        return plays;
    }
    
    /**
     * Get upcoming plays.
     * Retrieves all the plays. If you want only the upcoming plays, see getUpcomingPlays().
     * @see getUpcomingPlays()
     * @author Dylan Van Assche
     */
    @Override
    public ArrayList<Object> getAllPlays() {
        // Convert to ArrayList for easy access
        return new ArrayList<Object>(em.createNamedQuery("Plays.findAll").getResultList());
    }
    
    /**
     * Find a Plays object by ID.
     * @parameter int playId
     * @author Dylan Van Assche
     */
    @Override
    public Object getPlayById(int playId) {
        Query q = em.createNamedQuery("Plays.findById"); // Find object by given ID
        q.setParameter("id", playId);
        return q.getSingleResult();
    }

    /**
     * Removes a play.
     * Removes a play by a given play ID.
     * @parameter int playId
     * @see addPlay()
     * @author Dylan Van Assche
     */
    @Override
    public void removePlay(int playId) {
        Plays play = (Plays)this.getPlayById(playId);
        em.remove(play); // Remove the retrieved object from the database
    }

    /**
     * Adds a play.
     * Adds a play by a name, date, basicPrice and rankFee.
     * @parameter String name
     * @paramter Date date
     * @parameter float basicPrice
     * @parameter float rankFee
     * @see removePlay()
     * @author Dylan Van Assche
     */
    @Override
    public void addPlay(String name, Date date, float basicPrice, float rankFee) {
        int lastId = (int)em.createNamedQuery("Plays.findLastId").getSingleResult();
        Plays play = new Plays(); // Create a new Plays object
        play.setId(lastId + 1); // Increment ID
        play.setName(name);
        play.setDate(date);
        play.setBasicPrice(basicPrice);
        play.setRankFee(rankFee);
        em.persist(play); // Make the new object persistent in the database
    }
}

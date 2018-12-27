package beans;

import entities.Plays;
import entities.Seats;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Seats API bean.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * @author Dylan Van Assche
 */
@Stateless
public class SeatsBean implements SeatsBeanRemote {
    @PersistenceContext private EntityManager em;
    @EJB PlaysBeanRemote playsBean;
    
    /**
     * Retrieves all seats for a play.
     * @parameter int playId
     * @author Dylan Van Assche
     */
    @Override
    public ArrayList<ArrayList<Object>> getAllSeatsForPlay(int playId) {
        // Convert to Map for easy access by row and column number using the TablePosition helper class
        Query q = em.createNamedQuery("Seats.findAllSortedByPlayId");
        q.setParameter("playId", (Plays)playsBean.getPlayById(playId));
        List<Seats> temp = q.getResultList();
        ArrayList<ArrayList<Object>> seats = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> seatsRow = new ArrayList<Object>();
        int rowNumber = 0;
        for(Seats s: temp) {
            if(rowNumber != s.getRowNumber()) {
                seats.add(seatsRow);
                seatsRow = new ArrayList<Object>();
                rowNumber = s.getRowNumber();
            }
            seatsRow.add((Object)s);
        }
        seats.add(seatsRow);
        System.out.println("Seats:" + seats);
        return seats;
    }
    
    /**
     * Find a Seat object by ID.
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public Object getSeatById(int seatId) {
        Query q = em.createNamedQuery("Seats.findById");
        q.setParameter("id", seatId);
        Seats seat = (Seats)q.getSingleResult();
        return seat;
    }
    
    /**
     * Gets the number of rows for a play.
     * @parameter int playId
     * @author Dylan Van Assche
     */
    @Override
    public int getNumberofRowsForPlay(int playId) {
        return (int)em.createNamedQuery("Seats.findLastRowNumber").getSingleResult() + 1; // convert to size
    }

    /**
     * Gets the number of columns for a play.
     * @parameter int playId
     * @author Dylan Van Assche
     */
    @Override
    public int getNumberofColumnsForPlay(int playId) {
        return (int)em.createNamedQuery("Seats.findLastColumnNumber").getSingleResult() + 1; // convert to size
    }

    /**
     * Free a seat by ID.
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void freeSeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.AVAILABLE);
        em.merge(seat);
    }

    /**
     * Reserve a seat by ID.
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void reserveSeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.RESERVED);
        em.merge(seat);
    }

    /**
     * Occupy a seat by ID.
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void occupySeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.OCCUPIED);
        em.merge(seat);
    }
}

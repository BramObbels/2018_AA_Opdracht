package beans;

import entities.Plays;
import entities.Seats;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.TablePosition;

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
    public Map<TablePosition, Object> getAllSeatsForPlay(int playId) {
        // Convert to Map for easy access by row and column number using the TablePosition helper class
        Query q = em.createNamedQuery("Seats.findAllSortedByPlayId");
        q.setParameter("playId", (Plays)playsBean.getPlayById(playId));
        List<Seats> temp = q.getResultList();
        Map<TablePosition, Object> seats = new LinkedHashMap<TablePosition, Object>(); // Order is maintained using a LinkedHashMap
        
        for(Seats s: temp) {
            // IF USER is member or public (add check):
            if(s.getStatus() == Seats.AVAILABLE) {
               seats.put(new TablePosition(s.getRowNumber(), s.getColumnNumber()), (Object)s); // Insert seat using upcasting
            }
            // IF USER is administrator:
        }
        
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
     * Reserves a seat
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void reserveSeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.RESERVED);
    }

    /**
     * Free a seat
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void freeSeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.AVAILABLE);
    }

    /**
     * Occupy a seat
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void occupySeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        seat.setStatus(Seats.OCCUPIED);
    }

    /**
     * Add a seat
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void addSeat(int row, int column, int rank) {
        int lastSeatId = (int)em.createNamedQuery("Seats.findLastId").getSingleResult();
        // Validation needed for rows and colums?
        Seats seat = new Seats();
        seat.setId(lastSeatId + 1);
        seat.setColumnNumber(column);
        seat.setRowNumber(row);
        seat.setRank(rank);
        em.persist(seat);
    }

    /**
     * Remove a seat
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public void removeSeat(int seatId) {
        Seats seat = (Seats)this.getSeatById(seatId);
        em.remove(seat);
    }
}

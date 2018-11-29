package beans;

import entities.Plays;
import entities.Seats;
import entities.Tickets;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Tickets API bean.
 * Tickets generation and validation. When the ticket is validated, it can't be 
 * used anymore!
 * @author Dylan Van Assche
 */
// TO DO: Change id from int to long
// TO DO: Add valid field
@Stateless
public class TicketsBean implements TicketsBeanRemote {
    @PersistenceContext private EntityManager em;
    @EJB PlaysBeanRemote playsBean;
    @EJB SeatsBeanRemote seatsBean;
    private static final BigInteger MIN = new BigInteger("0");
    private static final BigInteger MAX = new BigInteger("9999999999999");
    
    /**
     * Generate ticket.
     * Generates a ticket for a given play, account and seat.
     * @parameter int accountId
     * @parameter int playId
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public Object generateTicket(int accountId, int playId, int seatId) {
        // Generate ticket ID and build Ticket object
        long ticketId = ThreadLocalRandom.current().nextLong(MIN.longValue(), MAX.longValue() + 1);
        Tickets ticket = new Tickets();
        System.out.println("AccountId=" + accountId);
        System.out.println("PlayId=" + playId);
        System.out.println("SeatId=" + seatId);
        Plays play = (Plays)playsBean.getPlayById(playId);
        System.out.println("PLAY=" + play.getName());
        Seats seat = (Seats)seatsBean.getSeatById(seatId);
        System.out.println("SEAT=" + seat.getRowNumber() + "," + seat.getColumnNumber() + " ID=" + seat.getId());
        
        ticket.setId(ticketId); 
        /*ticket.setAccountId(); */// Security story
        ticket.setPlayId(play);
        ticket.setSeatId(seat);        
        ticket.setValid(Tickets.VALID);
        
        // Make seat OCCUPIED and merge the change with the DB
        seat.setStatus(Seats.OCCUPIED);
        seat = em.merge(seat);
        
        // Make ticket persistent and return it
        em.persist(ticket);
        return (Object)ticket;
    }

    /**
     * Get ticket by ID.
     * Get ticket by ID and returns it.
     * @parameter int ticketId
     * @author Dylan Van Assche
     */
    @Override
    public Object getTicketById(long ticketId) {
        Query q = em.createNamedQuery("Tickets.findById"); // Find object by given ID
        q.setParameter("id", ticketId);
        return q.getSingleResult();
    }
    
    /**
     * Get all sold tickets for a play.
     * Retrieves all the sold tickets for a play ID. 
     * @parameter int playId
     * @author Dylan Van Assche
     */
    @Override
    public ArrayList<Object> getAllSoldTicketsForPlay(Object playId) {
        Query q = em.createNamedQuery("Tickets.findByPlayId"); // Find object by given ID
        q.setParameter("playId", (Plays)playId);
        ArrayList<Object> tickets = new ArrayList<Object>(q.getResultList());
        return tickets;
    }

    /**
     * Validate ticket by ID.
     * Checks if a ticket is valid and returns the result.
     * @parameter int ticketId
     * @see invalidateTicketById()
     * @author Dylan Van Assche
     */
    @Override
    public boolean isTicketValidById(long ticketId) {
        Tickets ticket = (Tickets)this.getTicketById(ticketId);
        // check if ticket is valid and return it
        return ticket.getValid() == Tickets.VALID;
    }
    
    /**
     * Invalidate ticket by ID.
     * Makes the given ticket by ID invalid.
     * @parameter int ticketId
     * @see isTicketValidById()
     * @author Dylan Van Assche
     */
    @Override
    public void invalidateTicketById(long ticketId) {
        Tickets ticket = (Tickets)this.getTicketById(ticketId);
        
        // Make ticket invalid and merge it with the DB
        ticket.setValid(Tickets.INVALID);
        ticket = em.merge(ticket);
    }
}
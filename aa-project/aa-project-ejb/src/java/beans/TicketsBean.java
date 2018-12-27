package beans;

import entities.Plays;
import entities.Seats;
import entities.Tickets;
import entities.Accounts;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Stateless
public class TicketsBean implements TicketsBeanRemote {
    @PersistenceContext private EntityManager em;
    @EJB PlaysBeanRemote playsBean;
    @EJB SeatsBeanRemote seatsBean;
    @EJB AccountBeanRemote accountBean;
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
    private Object generateTicket(int accountId, int playId, int seatId, String buyer) {
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
        Accounts account = (Accounts)accountBean.getAccountById(accountId);
        
        ticket.setId(ticketId); 
        ticket.setAccountId(account); // Security story
        ticket.setPlayId(play);
        ticket.setSeatId(seat);        
        ticket.setValid(Tickets.VALID);
        ticket.setBuyer(buyer);
        
        // Make ticket persistent and return it
        em.persist(ticket);
        return (Object)ticket;
    }
    
    /**
     * Generate ticket to occupy seat.
     * Generates a ticket for a given play, account and seat.
     * @parameter int accountId
     * @parameter int playId
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public Object generateOccupiedTicket(int accountId, int playId, int seatId, String buyer) {
        // Make seat OCCUPIED and merge the change with the DB
        seatsBean.occupySeat(seatId);
        
        // Handle reserved tickets
        Tickets t = this.getReservedTicket(seatId, playId);
        if(t == null) {
            return this.generateTicket(accountId, playId, seatId, buyer);
        }
        return t;
    }
    
    /**
     * Generate ticket to reserve seat.
     * Generates a ticket for a given play, account and seat.
     * @parameter int playId
     * @parameter int seatId
     * @author Dylan Van Assche
     */
    @Override
    public Object generateReservedTicket(int playId, int seatId) {
        // Make seat RESERVED and merge the change with the DB
        seatsBean.reserveSeat(seatId);
        // accountId = 0 since we reserve the ticket
        return this.generateTicket(0, playId, seatId, "MANUAL VENDING");
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
    
    /**
     * Remove ticket by seat ID and play ID.
     * Removes a given ticket from the DB.
     * @parameter int seatId
     * @parameter int playId
     * @see isTicketValidById()
     * @author Dylan Van Assche
     */
    @Override
    public void removeTicketBySeatIdAndPlayId(int seatId, int playId) {
        Query q = em.createNamedQuery("Tickets.findByPlayId"); // Find object by given ID
        Plays play = (Plays)playsBean.getPlayById(playId);
        q.setParameter("playId", play);
        ArrayList<Object> tickets = new ArrayList<Object>(q.getResultList());
        for(Object o : tickets) {
            Tickets t = (Tickets)o;
            if(t.getSeatId().getId() == seatId) {
                em.remove(t);
                break;
            }
        }
    }
    
    /**
     * Find reserved ticket by seat ID and play ID.
     * Returns null if no ticket has been found
     * @parameter int seatId
     * @parameter int playId
     * @see generateTicket()
     * @author Dylan Van Assche
     */
    private Tickets getReservedTicket(int seatId, int playId) {
        Query q = em.createNamedQuery("Tickets.findByPlayId"); // Find object by given ID
        Plays play = (Plays)playsBean.getPlayById(playId);
        q.setParameter("playId", play);
        ArrayList<Object> tickets = new ArrayList<Object>(q.getResultList());
        for(Object o : tickets) {
            Tickets t = (Tickets)o;
            if(t.getSeatId().getId() == seatId) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public ArrayList<Object> getTicketsFromAccountId(int id){
        Query q = em.createNamedQuery("Tickets.findByAccountId"); // Find object by given ID
        Accounts account = (Accounts)accountBean.getAccountById(id);
        q.setParameter("accountId", account);
        ArrayList<Object> tickets = new ArrayList<Object>(q.getResultList());
        System.out.println(tickets.size());
        return tickets;
    }
}
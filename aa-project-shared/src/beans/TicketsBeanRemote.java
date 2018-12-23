package beans;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 * Tickets API business interface.
 * Tickets generation and validation. When the ticket is validated, it can't be 
 * used anymore!
 * Use this interface in case you want to access the EE bean. 
 * @author Dylan Van Assche
 */
@Remote
public interface TicketsBeanRemote {
    public Object generateTicket(int accountId, int playId, int seatId);
    public Object getTicketById(long ticketId);
    public ArrayList<Object> getAllSoldTicketsForPlay(Object playId);
    public boolean isTicketValidById(long ticketId);
    public void invalidateTicketById(long ticketId);
}
package beans;

import java.util.Map;
import javax.ejb.Remote;
import util.TablePosition;

/**
 * Seats API business interface.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * Use this interface in case you want to access the EE bean. 
 * @author Dylan Van Assche
 */
@Remote
public interface SeatsBeanRemote {
    public Map<TablePosition, Object> getAllSeatsForPlay(int playId);
    public int getNumberofRowsForPlay(int playId);
    public int getNumberofColumnsForPlay(int playId);
    public void reserveSeat(int seatId);
    public void freeSeat(int seatId);
    public void occupySeat(int seatId);
}

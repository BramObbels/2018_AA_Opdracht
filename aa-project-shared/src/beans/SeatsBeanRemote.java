package beans;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 * Seats API business interface.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * Use this interface in case you want to access the EE bean. 
 * @author Dylan Van Assche
 */
@Remote
public interface SeatsBeanRemote {
    public ArrayList<ArrayList<Object>> getAllSeatsForPlay(int playId);
    public Object getSeatById(int seatId);
    public int getNumberofRowsForPlay(int playId);
    public int getNumberofColumnsForPlay(int playId);
    public void reserveSeat(int seatId);
    public void freeSeat(int seatId);
    public void occupySeat(int seatId);
    public void addSeat(int row, int column, int rank);
    public void removeSeat(int seatId);
}

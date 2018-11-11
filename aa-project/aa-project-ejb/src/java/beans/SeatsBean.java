package beans;

import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 * Seats API bean.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * @author Dylan Van Assche
 */
@Stateless
public class SeatsBean implements SeatsBeanRemote {

    @Override
    public ArrayList<ArrayList<Object>> getAvailableSeatsForPlay(int playId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ArrayList<ArrayList<Object>> getOccupiedSeatsForPlay(int playId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<ArrayList<Object>> getReservedSeatsForPlay(int playId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<ArrayList<Object>> getAllSeatsForPlay(int playId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reserveSeat(int seatId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void freeSeat(int seatId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void occupySeat(int seatId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

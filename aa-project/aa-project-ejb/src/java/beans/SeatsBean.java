package beans;

import entities.Seats;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Seats API bean.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * @author Dylan Van Assche
 */
@Stateless
public class SeatsBean implements SeatsBeanRemote {
    @PersistenceContext private EntityManager em;

    @Override
    public ArrayList<ArrayList<Object>> getAllSeatsForPlay(int playId) {
        // Convert to ArrayList for easy access
        List<Seats> temp = em.createNamedQuery("Seats.findAllSorted").getResultList();
        int rowSize = (int)em.createNamedQuery("Seats.findRowSize").getSingleResult();
        int columnSize = (int)em.createNamedQuery("Seats.findColumnSize").getSingleResult();      
        // Create an empty 2D ArrayList with given row and column size since we don't know
        ArrayList<ArrayList<Object>> seats = new ArrayList<ArrayList<Object>>();
        /*for(int r=0; r < rowSize; r++) {
            ArrayList<Object> rowSeats = new ArrayList<Object>();
            for(int c=0; c < columnSize; c++) {
                rowSeats.add(new Object());
            }
            seats.add(rowSeats);
        }*/
        
        
        // Filter for upcoming plays
        for(Seats s : temp) {
            // Get list of seats for a certain row, create the row if needed
            ArrayList<Object> rowSeats = new ArrayList<Object>();
            
            // Row already exist, load it
            if(s.getRowNumber() < seats.size()) {
                rowSeats = seats.get(s.getRowNumber());
            }
            
            // Sorted by Java Persistence Query language
            rowSeats.add(s.getRowNumber(), s); 
            seats.add(s.getColumnNumber(), rowSeats);
        }
        return seats;
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

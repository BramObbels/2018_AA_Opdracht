package beans;

import entities.Seats;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.TablePosition;

/**
 * Seats API bean.
 * Retrieves all the seats for a play and allows to add, remove, reserve, occupy, free seats.
 * @author Dylan Van Assche
 */
@Stateless
public class SeatsBean implements SeatsBeanRemote {
    @PersistenceContext private EntityManager em;

    @Override
    public Map<TablePosition, Object> getAllSeatsForPlay(int playId) {
        // Convert to ArrayList for easy access
        List<Seats> temp = em.createNamedQuery("Seats.findAllSorted").getResultList();
        // Create an empty 2D ArrayList with given row and column size since we don't know
        /*ArrayList<ArrayList<Seats>> seats = new ArrayList<ArrayList<Seats>>();
        for(int r=0; r < rowSize; r++) {
            ArrayList<Object> rowSeats = new ArrayList<Seats>();
            for(int c=0; c < columnSize; c++) {
                rowSeats.add(new Seats());
            }
            seats.add(rowSeats);
        }
        System.out.println("ROW SIZE 2D: " + seats.size());
        System.out.println("COLUMN SIZE 2D: " + seats.get(0).size());
        
        // Filter for upcoming plays
        for(Seats s : temp) {
            // Get list of seats for a certain row, create the row if needed
            
            // Row already exist, load it
            ArrayList<Seats> rowSeats = seats.get(s.getRowNumber());
            
            // Sorted by Java Persistence Query language
            rowSeats.add(s.getRowNumber(), s); 
            seats.add(s.getColumnNumber(), rowSeats);
        }
        
        ArrayList<ArrayList<Object>> upcastingSeats = new ArrayList<ArrayList<Object>>();
        for(ArrayList<Seats> rowSeats: seats) {
            for(Seats s: rowSeats) {
                upcastingSeats.at(s.getRowNumber()).at(s.getColumnNumber())
            }
        }*/
        
        Map<TablePosition, Object> seats = new LinkedHashMap<TablePosition, Object>();
        for(Seats s: temp) {
            seats.put(new TablePosition(s.getRowNumber(), s.getColumnNumber()), (Object)s);
        }
        return seats;
    }
    
    @Override
    public int getNumberofRowsForPlay(int playId) {
        return (int)em.createNamedQuery("Seats.findLastRowNumber").getSingleResult() + 1; // convert to size
    }

    @Override
    public int getNumberofColumnsForPlay(int playId) {
        return (int)em.createNamedQuery("Seats.findLastColumnNumber").getSingleResult() + 1; // convert to size
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

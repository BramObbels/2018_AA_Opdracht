package controller;

import beans.PlaysBeanRemote;
import beans.TicketsBeanRemote;
import entities.Plays;
import entities.Tickets;
import java.util.ArrayList;
import javax.ejb.EJB;
import view.MainWindow;

/**
 * Controller standalone application.
 * The Controller provides interaction between the View (Swing) and the 
 * Model (JEE Beans), following the MVC principles.
 * Only one instance of the Controller must be created (Singleton pattern).
 * @author Dylan Van Assche
 */
public class Controller {
    private static Controller controller = null;
    @EJB private static PlaysBeanRemote playsBean;
    @EJB private static TicketsBeanRemote ticketsBean;
    
    /**
     * main().
     * main() is called when the standalone application is started by the user.
     * @param args the command line arguments
     * @author Dylan Van Assche
     */
    public static void main(String[] args) {
        Controller c = Controller.getInstance();
    }
    
    /**
     * Singleton getInstance()
     * @return Controller controller
     * @author Dylan Van Assche
     */
    public static Controller getInstance() {
        if(controller == null) {
            System.out.println("Generating Controller instance...");
            controller = new Controller();
        }
        return controller;
    }
    
    /**
     * Controller private constructor.
     * Needs to be private for the Singleton design pattern to work.
     * @author Dylan Van Assche
     */
    private Controller() {
        System.out.println("Standalone application for JEE AA-project");
        new MainWindow(this);
    }
    
    public ArrayList<Plays> getPlays() {
        ArrayList<Object> temp = playsBean.getAllPlays();
        ArrayList<Plays> plays = new ArrayList<Plays>();
        for(Object p: temp) {
            System.out.println(((Plays)p).getName()); // Hoe downcasten we deze handel?
            plays.add(((Plays)p));
        }
        return plays;
    }
    
    public ArrayList<Tickets> getValidTickets(int playId) {
        System.out.println("PLAY ID=" + playId);
        Plays play = (Plays)playsBean.getPlayById(playId);
        ArrayList<Object> temp = ticketsBean.getAllSoldTicketsForPlay(play);
        ArrayList<Tickets> tickets = new ArrayList<Tickets>();
        for(Object t: temp) {
            System.out.println(((Tickets)t).getId()); // Hoe downcasten we deze handel?
            Tickets ticket = (Tickets)t;
            if(ticket.getValid() == Tickets.VALID) {
                tickets.add(ticket);
            }
            else {
                System.out.println("Ticket invalid ID:" + ticket.getId());
            }
        }
        return tickets;
    }
    
    public int getNumberOfSoldTickets(int playId) {
        Plays play = (Plays)playsBean.getPlayById(playId);
        ArrayList<Object> temp = ticketsBean.getAllSoldTicketsForPlay(play);
        return temp.size();
    }
    
    public Tickets getTicketById(long ticketId) {
        Tickets ticket = (Tickets)ticketsBean.getTicketById(ticketId);
        return ticket;
    }
    
    public void invalidateTicketById(long ticketId) {
        ticketsBean.invalidateTicketById(ticketId);
        System.out.println("Ticket validation status:" + this.getTicketById(ticketId).getValid());
    }
}
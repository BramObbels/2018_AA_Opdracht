package controllers;

import beans.PlaysBeanRemote;
import beans.SeatsBeanRemote;
import beans.TicketsBeanRemote;
import entities.Tickets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ControllerOrder dispatches all the requests to order tickets and glues the Model and the View together.
 * The View receives it's data through the ControllerOrder and the ControllerOrder updates the 
 * Model if needed from the input given by the View.
 * @author Dylan Van Assche
 */
public class ControllerOrder extends HttpServlet {
    @EJB PlaysBeanRemote playsBean;
    @EJB SeatsBeanRemote seatsBean;
    @EJB TicketsBeanRemote ticketsBean;
    private static final String SELECT_PLAY = "selectPlay";
    private static final String SELECT_SEAT = "selectSeat";
    private static final String CONFIRM_ORDER = "confirmOrder";
    private static final String GENERATE_TICKETS = "generateTickets";
    
    /**
     * Initialisation of the ControllerOrder Servlet.
     */
    public void init() {
        
    }
    
    /**
     * Process requests for both HTTP GET and POST requests.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // At the beginning, the order state isn't initialised for the current session
        String state;
        if(request.getParameter("nextState") == null) {
            state = SELECT_PLAY;
            System.out.println("Order process started");
        }
        else {
            state = request.getParameter("nextState");
        }
        
        System.out.println("STATE=" + state);
        switch(state) {
            default:
                System.err.println("Unknown ordering state, starting from the beginning");
            case SELECT_PLAY:
                System.out.println("UPCOMING PLAYS=" + playsBean.getUpcomingPlays());
                request.setAttribute("upcomingPlays", playsBean.getUpcomingPlays());
                session.setAttribute("nextOrderState", SELECT_SEAT);
                this.goToJSPPage("select-play.jsp", request, response);
                break;
                
            case SELECT_SEAT:
                System.out.println("SELECT SEATS");
                // Input validation
                if(request.getParameter("selectedPlayId") == null) {
                    System.err.println("No play selected, unable to continue. HTTP 500");
                    response.sendError(500, "Unable to process the selected play");
                    break;
                }
                else {
                    session.setAttribute("selectedPlayId", Integer.parseInt(request.getParameter("selectedPlayId")));
                }
                
                // Valid input, processing...
                System.out.println("SELECTED PLAY ID=" + session.getAttribute("selectedPlayId"));
                request.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                session.setAttribute("nextOrderState", CONFIRM_ORDER);
                this.goToJSPPage("select-seat.jsp", request, response);
                break;
                
            case CONFIRM_ORDER:
                System.out.println("CONFIRM ORDER");
                // Input validation
                if(request.getParameterValues("selectedSeatIds") == null) {
                    System.err.println("No seats selected, unable to continue. Return to selected seats page");
                    session.setAttribute("nextOrderState", SELECT_SEAT);
                    request.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                    this.goToJSPPage("select-seat.jsp", request, response);
                    break;
                }    
                else {
                    System.out.println("NUMBER OF SELECTED SEATS=" + Arrays.toString(request.getParameterValues("selectedSeatIds")));
                    String[] checkboxes = request.getParameterValues("selectedSeatIds");
                    ArrayList<Integer> selectedSeatIds = new ArrayList<Integer>();
                    for(String id : checkboxes) {
                        selectedSeatIds.add(Integer.parseInt(id));
                    }
                    session.setAttribute("selectedSeatIds", selectedSeatIds);
                }
                
                // Valid input, processing...
                System.out.println("SELECTED SEATS ID=" + session.getAttribute("selectedSeatIds"));
                session.setAttribute("nextOrderState", GENERATE_TICKETS);
                session.setAttribute("orderedPlay", playsBean.getPlayById((Integer)session.getAttribute("selectedPlayId")));
                ArrayList<Object> orderedSeats = new ArrayList<Object>();
                for(Integer id : (ArrayList<Integer>)session.getAttribute("selectedSeatIds")) {
                    orderedSeats.add(seatsBean.getSeatById(id));
                }
                session.setAttribute("orderedSeats", orderedSeats);
                this.goToJSPPage("confirm-order.jsp", request, response);
                break;
                
            case GENERATE_TICKETS:
                System.out.println("GENERATING TICKETS");
                session.setAttribute("nextOrderState", SELECT_SEAT);
                ArrayList<Object> generatedTickets = new ArrayList<Object>();
                for(Integer id : (ArrayList<Integer>)session.getAttribute("selectedSeatIds")) {
                    int playId = (Integer)session.getAttribute("selectedPlayId");
                    int accountId = 0;
                    System.out.println("Ticket");
                    Object ticket = ticketsBean.generateTicket(accountId, playId, id);
                    System.out.println(((Tickets)ticket).getId());
                    generatedTickets.add(ticket);
                }
                session.setAttribute("generatedTickets", generatedTickets);
                
                this.goToJSPPage("tickets.jsp", request, response);
                break;
        }
    }
    
    /**
     * Handles HTTP POST method for the Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }
    
    /**
     * Handles HTTP GET method for the Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }
    
    /**
     * Forwards a request to a given JSP page.
     * 
     * @param page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void goToJSPPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
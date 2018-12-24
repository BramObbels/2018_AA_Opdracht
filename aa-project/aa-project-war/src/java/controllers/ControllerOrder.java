package controllers;

import beans.AccountBeanRemote;
import beans.PlaysBeanRemote;
import beans.SeatsBeanRemote;
import beans.TicketsBeanRemote;
import entities.Tickets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ejb.EJB;
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
    @EJB AccountBeanRemote accountBean;
    private static final String STATE_LANDING = "landing";
    private static final String STATE_SELECT_PLAY = "selectPlay";
    private static final String STATE_SELECT_SEAT = "selectSeat";
    private static final String STATE_CONFIRM_ORDER = "confirmOrder";
    private static final String STATE_GENERATE_TICKETS = "generateTickets";
    private static final String ACTION_NOTHING = "nothing";
    private static final String ACTION_FREE = "free";
    private static final String ACTION_RESERVE = "reserve";
    private static final String ACTION_OCCUPY = "occupy";
    
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
        String state = (String)session.getAttribute("nextOrderState");
        if(state == null) {
            System.out.println("Order process started");
            session.setAttribute("nextOrderState", STATE_LANDING);
            state = STATE_LANDING;
        }
        
        System.out.println("STATE=" + state);
        switch(state) {
            default:
                System.err.println("Unknown ordering state, starting from the beginning");
                
            case STATE_LANDING:
                System.out.println("CONNECT OPTIONALLY ACCOUNT TO TICKETS");
                // User isn't logged in, give him/her the option to connect his/her account to the tickets
                if(request.getUserPrincipal() == null) {
                    session.setAttribute("nextOrderState", STATE_SELECT_PLAY);
                    this.goToJSPPage("order-landing.jsp", request, response);
                    break;
                }
                // If user is already logged in, fall through to SELECT_PLAY immediately
                
            case STATE_SELECT_PLAY:
                System.out.println("UPCOMING PLAYS=" + playsBean.getUpcomingPlays());
                session.setAttribute("upcomingPlays", playsBean.getUpcomingPlays());
                session.setAttribute("nextOrderState", STATE_SELECT_SEAT);
                this.goToJSPPage("select-play.jsp", request, response);
                break;
                
            case STATE_SELECT_SEAT:
                System.out.println("SELECT SEATS");
                // Input validation
                if(request.getParameter("selectedPlayId") == null) {
                    System.err.println("No play selected, unable to continue. Returning to the previous step: SELECT_PLAY");
                    session.setAttribute("upcomingPlays", playsBean.getUpcomingPlays());
                    session.setAttribute("nextOrderState", STATE_SELECT_SEAT);
                    this.goToJSPPage("select-play.jsp", request, response);
                    break;
                }
                else {
                    session.setAttribute("selectedPlayId", Integer.parseInt(request.getParameter("selectedPlayId")));
                }
                
                // Valid input, processing...
                System.out.println("SELECTED PLAY ID=" + session.getAttribute("selectedPlayId"));
                session.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                session.setAttribute("nextOrderState", STATE_CONFIRM_ORDER);
                this.goToJSPPage("select-seat.jsp", request, response);
                break;
                
            case STATE_CONFIRM_ORDER:
                System.out.println("CONFIRM ORDER");
                // Input validation
                String[] actions = request.getParameterValues("seatAction");
                String[] seatIds = request.getParameterValues("seatId");
                System.out.println("#actions= " + actions.length);
                System.out.println("#seatIds= " + seatIds.length);
                boolean hasAction = false;
                for(String a : actions) {
                    if(!a.equals(ACTION_NOTHING)) {
                        hasAction = true;
                        break;
                    }
                }
                
                // Continue if we have at least one action
                if(hasAction) {
                    System.out.println("NUMBER OF SELECTED SEATS=" + Arrays.toString(request.getParameterValues("selectedSeatIds")));
                    ArrayList<Integer> freeSeatIds = new ArrayList<Integer>();
                    ArrayList<Integer> reserveSeatIds = new ArrayList<Integer>();
                    ArrayList<Integer> occupySeatIds = new ArrayList<Integer>();
                    for(int i=0; i < seatIds.length; i++) {
                        String id = seatIds[i];
                        if(actions[i].equals(ACTION_FREE)) {
                            freeSeatIds.add(Integer.parseInt(id));
                        }
                        else if(actions[i].equals(ACTION_FREE)) {
                            reserveSeatIds.add(Integer.parseInt(id));
                        }
                        else if(actions[i].equals(ACTION_FREE)) {
                            occupySeatIds.add(Integer.parseInt(id));
                        }
                    }
                    session.setAttribute("freeSeatIds", freeSeatIds);
                    session.setAttribute("reserveSeatIds", reserveSeatIds);
                    session.setAttribute("occupySeatIds", occupySeatIds);
                }
                // In case the user hasn't selected anything, redirect to the seats selection page
                else {
                    System.err.println("No seats selected, unable to continue. Return to selected seats page");
                    session.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                    session.setAttribute("nextOrderState", STATE_CONFIRM_ORDER);
                    this.goToJSPPage("select-seat.jsp", request, response);
                    break;
                }
                
                // Valid input, processing...
                System.out.println("SELECTED SEATS ID=" + session.getAttribute("selectedSeatIds"));
                session.setAttribute("nextOrderState", STATE_GENERATE_TICKETS);
                session.setAttribute("orderedPlay", playsBean.getPlayById((Integer)session.getAttribute("selectedPlayId")));
                ArrayList<Object> orderedSeats = new ArrayList<Object>();
                for(Integer id : (ArrayList<Integer>)session.getAttribute("selectedSeatIds")) {
                    orderedSeats.add(seatsBean.getSeatById(id));
                }
                session.setAttribute("orderedSeats", orderedSeats);
                this.goToJSPPage("confirm-order.jsp", request, response);
                break;
                
            case STATE_GENERATE_TICKETS:
                System.out.println("GENERATING TICKETS");
                session.setAttribute("nextOrderState", null); // End of ordering
                ArrayList<Object> generatedTickets = new ArrayList<Object>();
                
                // Free seats -> set status to free if allowed
                for(Integer id : (ArrayList<Integer>)session.getAttribute("freeSeatIds")) {
                    seatsBean.freeSeat(id);
                }
                
                // Reserve seats -> set status to reserve if allowed
                for(Integer id : (ArrayList<Integer>)session.getAttribute("reserveSeatIds")) {
                    seatsBean.reserveSeat(id);
                }
                
                // Occupied seats -> generate tickets
                for(Integer id : (ArrayList<Integer>)session.getAttribute("occupySeatIds")) {
                    int playId = (Integer)session.getAttribute("selectedPlayId");
                    int accountId = 0;
                    if(request.getUserPrincipal() == null) {
                        System.out.println("No account linked, account ID = 0");
                    }
                    else {
                        System.out.println("User is authenticated, linking tickets to account");
                        accountId = accountBean.getIdByUsername(request.getUserPrincipal().getName());
                    }
                    System.out.println("Play ID: " + playId + " Account Id: " + accountId + " seats ID: " + id);
                    Object ticket = ticketsBean.generateTicket(accountId, playId, id);
                    System.out.println("Ticket generated: " + ((Tickets)ticket).getId());
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
        response.sendRedirect(page);
    }
}
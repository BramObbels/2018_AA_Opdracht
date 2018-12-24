package controllers;

import beans.AccountBeanRemote;
import beans.PlaysBeanRemote;
import beans.SeatsBeanRemote;
import beans.TicketsBeanRemote;
import entities.Plays;
import entities.Seats;
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
        String state = (String)request.getParameter("nextOrderState");
        if(state == null) {
            System.out.println("Order process started");
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
                    this.goToJSPPage("order-landing.jsp", request, response);
                    break;
                }
                // If user is already logged in, fall through to SELECT_PLAY immediately
                
            case STATE_SELECT_PLAY:
                System.out.println("UPCOMING PLAYS=" + playsBean.getUpcomingPlays());
                session.setAttribute("upcomingPlays", playsBean.getUpcomingPlays());
                this.goToJSPPage("select-play.jsp", request, response);
                break;
                
            case STATE_SELECT_SEAT:
                System.out.println("SELECT SEATS");
                // Input validation
                if(request.getParameter("selectedPlayId") == null) {
                    System.err.println("No play selected, unable to continue. Returning to the previous step: SELECT_PLAY");
                    session.setAttribute("upcomingPlays", playsBean.getUpcomingPlays());
                    this.goToJSPPage("select-play.jsp", request, response);
                    break;
                }
                else {
                    session.setAttribute("selectedPlayId", Integer.parseInt(request.getParameter("selectedPlayId")));
                }
                
                // Valid input, processing...
                System.out.println("SELECTED PLAY ID=" + session.getAttribute("selectedPlayId"));
                System.out.println("Management features enabled? " + request.isUserInRole("administrators"));
                session.setAttribute("isManagement", request.isUserInRole("administrators"));
                session.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                Plays play = (Plays)playsBean.getPlayById(Integer.parseInt(request.getParameter("selectedPlayId")));
                session.setAttribute("basicPrice", play.getBasicPrice());
                session.setAttribute("rankFee", play.getRankFee());
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
                    
                    // Sort seats based on action
                    ArrayList<Object> freeSeats = new ArrayList<Object>();
                    ArrayList<Object> reserveSeats = new ArrayList<Object>();
                    ArrayList<Object> occupySeats = new ArrayList<Object>();
                    for(int i=0; i < seatIds.length; i++) {
                        String id = seatIds[i];
                        System.out.println("Action: " + actions[i]);
                        // Make sure nobody tricks the server to free or reserve seats without admin rights
                        if(actions[i].equals(ACTION_FREE) && ((Boolean)session.getAttribute("isManagement"))) {
                            freeSeats.add(seatsBean.getSeatById(Integer.parseInt(id)));
                        }
                        else if(actions[i].equals(ACTION_RESERVE) && ((Boolean)session.getAttribute("isManagement"))) {
                            reserveSeats.add(seatsBean.getSeatById(Integer.parseInt(id)));
                        }
                        else if(actions[i].equals(ACTION_OCCUPY)) {
                            occupySeats.add(seatsBean.getSeatById(Integer.parseInt(id)));
                        }
                    }
                    
                    // Add the lists to the HTTP session
                    session.setAttribute("freeSeats", freeSeats);
                    session.setAttribute("reserveSeats", reserveSeats);
                    session.setAttribute("occupySeats", occupySeats);
                }
                // In case the user hasn't selected anything, redirect to the seats selection page
                else {
                    System.err.println("No seats selected, unable to continue. Return to selected seats page");
                    session.setAttribute("seats", seatsBean.getAllSeatsForPlay((Integer)session.getAttribute("selectedPlayId")));
                    this.goToJSPPage("select-seat.jsp", request, response);
                    break;
                }
                
                // Valid input, processing...
                System.out.println("SELECTED SEATS ID, FREE=" 
                        + session.getAttribute("freeSeats") 
                        + " | RESERVE=" + session.getAttribute("reserveSeats") 
                        + " | OCCUPY=" + session.getAttribute("occupySeats"));
                session.setAttribute("orderedPlay", playsBean.getPlayById((Integer)session.getAttribute("selectedPlayId")));
                this.goToJSPPage("confirm-order.jsp", request, response);
                break;
                
            case STATE_GENERATE_TICKETS:
                System.out.println("GENERATING TICKETS");
                ArrayList<Object> generatedTickets = new ArrayList<Object>();
                int playId = (Integer)session.getAttribute("selectedPlayId");
                
                // Free seats -> set status to free if allowed
                for(Seats seat : (ArrayList<Seats>)session.getAttribute("freeSeats")) {
                    seatsBean.freeSeat(seat.getId());
                    // Remove any ticket associated with this seat and play
                    ticketsBean.removeTicketBySeatIdAndPlayId(seat.getId(), playId);
                }
                
                // Reserve seats -> set status to reserve if allowed
                for(Seats seat : (ArrayList<Seats>)session.getAttribute("reserveSeats")) {
                    System.out.println("Play ID: " + playId + " seats ID: " + seat.getId());
                    Object ticket = ticketsBean.generateReservedTicket(playId, seat.getId());
                    System.out.println("Ticket reserved generated: " + ((Tickets)ticket).getId());
                    generatedTickets.add(ticket);
                }
                
                // Occupied seats -> generate tickets
                for(Seats seat : (ArrayList<Seats>)session.getAttribute("occupySeats")) {
                    int accountId = 0;
                    if(request.getUserPrincipal() == null) {
                        System.out.println("No account linked, account ID = 0");
                    }
                    else {
                        System.out.println("User is authenticated, linking tickets to account");
                        accountId = accountBean.getIdByUsername(request.getUserPrincipal().getName());
                    }
                    System.out.println("Play ID: " + playId + " Account Id: " + accountId + " seats ID: " + seat.getId());

                    Object ticket = ticketsBean.generateOccupiedTicket(accountId, playId, seat.getId());
                    System.out.println("Ticket occupied generated: " + ((Tickets)ticket).getId());
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
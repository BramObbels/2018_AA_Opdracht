package controllers;

import beans.PlaysBeanRemote;
import beans.SeatsBeanRemote;
import java.io.IOException;
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
    private static final int SELECT_PLAY = 0;
    private static final int SELECT_SEAT = 1;
    private static final int CONFIRM_ORDER = 2;
    
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
        if(session.getAttribute("nextOrderState") == null) {
            session.setAttribute("nextOrderState", SELECT_PLAY);
        }
              
        Integer state = (Integer)session.getAttribute("nextOrderState");
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
                if(request.getParameter("selectedPlay") == null) {
                    System.err.println("No play selected, unable to continue. HTTP 500");
                    response.sendError(500, "Unable to process the selected play, please try again later");
                    break;
                }               
                
                // Valid input, processing...
                System.out.println("SELECTED PLAY ID=" + request.getParameter("selectedPlay"));
                request.setAttribute("allSeats", seatsBean.getAllSeatsForPlay(Integer.parseInt(request.getParameter("selectedPlay"))));
                session.setAttribute("nextOrderState", CONFIRM_ORDER);
                this.goToJSPPage("select-seat.jsp", request, response);
                break;
                
            case CONFIRM_ORDER:
                System.out.println("CONFIRM ORDER");
                session.setAttribute("nextOrderState", SELECT_PLAY);
                this.goToJSPPage("confirm-order.jsp", request, response);
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
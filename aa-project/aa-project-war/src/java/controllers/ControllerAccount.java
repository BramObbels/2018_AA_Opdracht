package controllers;

import beans.AccountBeanRemote;
import beans.PlaysBeanRemote;
import beans.TicketsBeanRemote;
import entities.Collection;
import entities.Plays;
import entities.Seats;
import entities.Tickets;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ControllerManagement dispatches all the requests for the management area and glues the Model and the View together.
 * The View receives it's data through the ControllerManagement and the ControllerManagement updates the 
 * Model if needed from the input given by the View.
 * @author Dylan Van Assche
 */
public class ControllerAccount extends HttpServlet {
    @EJB PlaysBeanRemote playsBean;
    @EJB TicketsBeanRemote ticketsBean;
    @EJB AccountBeanRemote accountBean;
    /**
     * Initialisation of the ControllerManagement Servlet.
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
        Principal principal = request.getUserPrincipal();
        String name = principal.getName();
        System.out.println(name);
        
        session.setAttribute("username", name);
        session.setAttribute("test", "test");
        ArrayList<Object> tickets = new ArrayList<>();
        ArrayList<Collection> collections = new ArrayList<>();
        int aid = accountBean.getIdByUsername(name);
        System.out.println(aid);
        tickets = ticketsBean.getTicketsFromAccountId(aid);
        
        
        for(Object o : tickets) {
            Tickets t = (Tickets)o;
            Collection collection = new Collection(t.getPlayId(), t.getSeatId(), t);
            collections.add(collection);
        }
        session.setAttribute("Collections", collections);

        this.goToJSPPage("account.jsp", request, response);
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


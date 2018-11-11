package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ControllerLanding dispatches all the requests and glues the Model and the View together.
 * The View receives it's data through the ControllerLanding and the ControllerLanding updates the 
 * Model if needed from the input given by the View.
 * @author Dylan Van Assche
 */
public class ControllerLanding extends HttpServlet {
    
    /**
     * Initialisation of the ControllerLanding Servlet.
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
        this.goToJSPPage("landing.jsp", request, response);
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
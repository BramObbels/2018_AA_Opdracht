/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.AccountBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bram
 */
public class ControllerAccount extends HttpServlet {
    @EJB AccountBeanRemote accountBean;
    private static final String CONFIRM = "confirm";

    
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
        String state;
        String CREATE = "create";
        String CONFIRM_CREATE = "confirm";
        if(request.getParameter("nextState") == null) {
            state = CREATE;
            System.out.println("Order process started");
        }
        else {
            state = request.getParameter("nextState");
            System.out.println(state);
        }
        
        if(state.equals(CREATE)){
            this.goToJSPPage("create.jsp", request, response);
        }
        if(state.equals(CONFIRM_CREATE)){
            System.out.println("ga naar confirmpage");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if(accountBean.checkUsername(username)){
                System.out.println("acount bestaat al");
                this.goToJSPPage("create.jsp", request, response);
            }
            else{
                System.out.println("acount bestaat nog niet bestaat");
                accountBean.addAccount(username, password);
                this.goToJSPPage("confirm-create.jsp", request, response);
            }
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

package controller;

import beans.PlaysBeanRemote;
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
}

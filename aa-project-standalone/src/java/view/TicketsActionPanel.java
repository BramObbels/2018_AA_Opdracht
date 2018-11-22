package view;

import controller.Controller;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel for each JList ticket entry.
 * @author Dylan Van Assche
 */
public class TicketsActionPanel extends JPanel {
    private Controller controller;
    private JButton invalidateButton;
    private JLabel seatRowLabel;
    private JLabel seatColumnLabel;
    
    /**
    * Public constructor.
    * This panel holds the seat information for a ticket and allows the operator 
    * to invalidate the ticket at the entrance. 
    * @author Dylan Van Assche
    */
    public TicketsActionPanel(Controller controller) {
        // Keep a reference to the Controller
        this.setController(controller);
        
        // Setup the data and add it to the panel
        invalidateButton = new JButton("Invalidate");
        seatRowLabel= new JLabel("Row: 3");
        seatColumnLabel= new JLabel("Column: 2");
        this.add(seatRowLabel);
        this.add(seatColumnLabel);
        this.add(invalidateButton);
    }
    
    // Getters & Setters
    public Controller getController() {
        return this.controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
}

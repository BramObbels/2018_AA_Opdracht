package view;

import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.invalidateButton = new JButton("Invalidate");
        this.seatRowLabel= new JLabel();
        this.seatColumnLabel= new JLabel();
        this.update();
    }
    
    // Getters & Setters
    public Controller getController() {
        return this.controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public JButton getInvalidateButton() {
        return this.invalidateButton;
    }
    
    // Updaters
    public void setRow(String row) {
        this.seatRowLabel= new JLabel("Row: " + row);
        this.update();
    }
    
    public void setColumn(String column) {
        this.seatColumnLabel= new JLabel("Column: " + column);
        this.update();
    }
    
    public void update() {
        this.removeAll();
        this.add(seatRowLabel);
        this.add(seatColumnLabel);
        this.add(invalidateButton);
    }
}

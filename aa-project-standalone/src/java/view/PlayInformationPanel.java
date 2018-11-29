package view;

import controller.Controller;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * JPanel for information about the selected play.
 * @author Dylan Van Assche
 */
public class PlayInformationPanel extends JPanel {
    private Controller controller;
    private JLabel nameLabel;
    private JLabel numberOfTicketsLabel;
    private JLabel dateLabel;
    private JLabel nameText;
    private JLabel numberOfTicketsText;
    private JLabel dateText;
    
    /**
    * Public constructor.
    * This panel holds all the information about a play.
    * @author Dylan Van Assche
    */
    public PlayInformationPanel(Controller controller) {
        // Keep a reference to the controller
        this.setController(controller);
        
        // Set the layout and static labels of the JPanel
        GridLayout layout = new GridLayout(3, 1);
        this.setLayout(layout);
        this.nameLabel = new JLabel("Name:");
        this.numberOfTicketsLabel = new JLabel("Sold tickets:");
        this.dateLabel = new JLabel("Date:");
        
        // Setup the data and add it to the panel
        this.nameText = new JLabel();
        this.numberOfTicketsText = new JLabel();
        this.dateText = new JLabel();
        this.update();
    }
    
    // Getters & Setters
    public Controller getController() {
        return this.controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setName(String name) {
        this.nameText = new JLabel(name);
        this.update();
    }
    
    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTicketsText = new JLabel(Integer.toString(numberOfTickets));
        this.update();
    }
    
    public void setDate(String date) {
        this.dateText = new JLabel(date);
        this.update();
    }
    
    // Updaters
    public void update() {
        this.removeAll();
        this.add(this.nameLabel);
        this.add(this.nameText);
        this.add(this.numberOfTicketsLabel);
        this.add(this.numberOfTicketsText);
        this.add(this.dateLabel);
        this.add(this.dateText);
    }
}
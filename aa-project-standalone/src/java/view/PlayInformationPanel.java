package view;

import controller.Controller;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel for information about the current selected play.
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
        this.nameText = new JLabel("Sneeuwitje");
        this.numberOfTicketsText = new JLabel("53");
        this.dateText = new JLabel("22-11-2018");
        this.add(this.nameLabel);
        this.add(this.nameText);
        this.add(this.numberOfTicketsLabel);
        this.add(this.numberOfTicketsText);
        this.add(this.dateLabel);
        this.add(this.dateText);
    }
    
    // Getters & Setters
    public Controller getController() {
        return this.controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
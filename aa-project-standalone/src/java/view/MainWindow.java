package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * MainWindow.
 * Main window where all the interaction with the Controller happens.
 * @author Dylan Van Assche
 */
public class MainWindow extends JFrame {
    public final static String WINDOW_TITLE = "AA-Project ticket validator";
    private Controller controller;
    private JComboBox selectPlay;
    private JList soldTickets;
    private TicketsActionPanel actionPanel;
    private PlayInformationPanel informationPanel;
    
    public MainWindow(Controller controller) {      
        // JFrame setup
        super();
        
        // Keep a reference to the Controller
        this.setController(controller);
        
        // JFrame specific configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(WINDOW_TITLE);

        // JFrame fill content pane
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        String[] plays = { "play 1", "play 2", "play 3" };
        String[] tickets = { "6495964956568441", "5415656569964944", "9794631559456164", "7564946946549641", "8841653794668784" };
        selectPlay = new JComboBox(plays);
        soldTickets = new JList(tickets);
        actionPanel = new TicketsActionPanel(this.getController());
        informationPanel = new PlayInformationPanel(this.getController());
        content.add(selectPlay, BorderLayout.NORTH);
        content.add(soldTickets, BorderLayout.CENTER);
        content.add(actionPanel, BorderLayout.SOUTH);
        content.add(informationPanel, BorderLayout.EAST);
        
        // JFrame auto size and make visible
        this.pack();
        this.setVisible(true);
    }
    
    // Getters & Setters
    public Controller getController() {
        return this.controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
}

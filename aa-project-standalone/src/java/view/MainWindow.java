package view;

import controller.Controller;
import entities.Plays;
import entities.Seats;
import entities.Tickets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
    private DefaultListModel ticketsModel;
    private static final int SPACING = 10;
    
    public MainWindow(Controller controller) {      
        // JFrame setup
        super();
                
        // Keep a reference to the Controller
        this.setController(controller);
        
        // JFrame specific configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(WINDOW_TITLE);
        Container content = this.getContentPane();
        BorderLayout layout = new BorderLayout();
        layout.setHgap(SPACING);
        layout.setVgap(SPACING);
        content.setLayout(layout);
        Border emptyBorder = BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING);
        this.getRootPane().setBorder(emptyBorder);

        // JFrame fill content pane
        this.ticketsModel = new DefaultListModel();
        this.selectPlay = new JComboBox();
        for(Plays p: this.getController().getPlays()) {
            selectPlay.addItem(p);
        }
        this.soldTickets = new JList(this.ticketsModel);
        this.actionPanel = new TicketsActionPanel(this.getController());
        this.informationPanel = new PlayInformationPanel(this.getController());
        this.updateInformationPanel();
        this.updateActionPanel();
        this.updateTickets();
        this.updateMain();
        
        // Make JComboBox interactive
        this.selectPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Play selection changed");
                updateInformationPanel();
                updateActionPanel();
                updateTickets();
                updateMain();
            }
        });
        
        // Make JList interactive
        this.soldTickets.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                System.out.println("Ticket selection changed");
                updateActionPanel();
                updateMain();
            }
        });
        
        // Make JButton interactive
        this.actionPanel.getInvalidateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Invalidating ticket");
                Tickets selectedTicket = (Tickets)soldTickets.getSelectedValue();
                getController().invalidateTicketById(selectedTicket.getId());
                updateTickets();
                updateMain();
            }
        });
        
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
    
    // Updaters
    public void updateMain() {
        Container content = this.getContentPane();
        content.add(this.selectPlay, BorderLayout.NORTH);
        content.add(this.soldTickets, BorderLayout.CENTER);
        content.add(this.actionPanel, BorderLayout.SOUTH);
        content.add(this.informationPanel, BorderLayout.EAST);
        this.revalidate();
        this.repaint();
        this.pack();
    }
    
    public void updateInformationPanel() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Plays selectedPlay = (Plays)selectPlay.getSelectedItem();
        ArrayList<Tickets> tickets = getController().getValidTickets(selectedPlay.getId());
        int numberOfSoldTickets = getController().getNumberOfSoldTickets(selectedPlay.getId());
        this.informationPanel.setName(selectedPlay.getName());
        this.informationPanel.setDate(df.format(selectedPlay.getDate()));
        this.informationPanel.setNumberOfTickets(numberOfSoldTickets);
    }
    
    public void updateTickets() {
        Plays selectedPlay = (Plays)this.selectPlay.getSelectedItem();
        ArrayList<Tickets> tickets = getController().getValidTickets(selectedPlay.getId());
        this.ticketsModel.clear();
        for(Tickets t: tickets) {
            System.out.println(t);
            this.ticketsModel.addElement(t);
        }
        this.soldTickets.setModel(ticketsModel);
    }
    
    public void updateActionPanel() {
        if(this.soldTickets.isSelectionEmpty()) {
            this.actionPanel.getInvalidateButton().setEnabled(false);
            this.actionPanel.setRow("?");
            this.actionPanel.setColumn("?");
            return;
        }
        this.actionPanel.getInvalidateButton().setEnabled(true);
        Tickets selectedTicket = (Tickets)this.soldTickets.getSelectedValue();
        Seats associatedSeat = selectedTicket.getSeatId();
        this.actionPanel.setRow(Integer.toString(associatedSeat.getRowNumber()));
        this.actionPanel.setColumn(Integer.toString(associatedSeat.getColumnNumber()));
    }
}

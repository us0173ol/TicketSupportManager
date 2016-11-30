package com.miked;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by miked on 11/1/2016.
 */
public class TicketSupportGUI extends JFrame {

    private JPanel rootPanel;
    private JButton addTicketButton;
    private JButton deleteTicketButton;
    private JButton resolveTicketsButton;
    private JTextField problemTextField;
    private JLabel problemLable;
    private JLabel severityLabel;
    private JComboBox<Integer> severityComboBox;
    private JLabel whoReportedLabel;
    private JTextField whoReportedTextField;
    private JList<Ticket> ticketQue;
    private JLabel deleteTicketLabel;
    private JList<Ticket> resolvedTicketList;
    private JButton saveButton;
    private JButton exitButton;

    //list models for JLists, these allow functionality with the lists of tickets
    private DefaultListModel<Ticket> ticketQueListModel;
    private DefaultListModel<Ticket> resolvedTicketListModel;

    //setting up the gui
    public TicketSupportGUI() {
        super("Ticket Manager");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(700, 600);

        ticketQueListModel = new DefaultListModel<Ticket>();
        ticketQue.setModel(ticketQueListModel);
        resolvedTicketListModel = new DefaultListModel<Ticket>();
        resolvedTicketList.setModel(resolvedTicketListModel);

        //variables for making sure the ticket IDs start at the next possible number
        //or 1 if no tickets are in either list
        int highestIDnum=0;
        int highestIDnumr=0;

        //it should be noted that variables with an added "r" at the end refer to resolved tickets
        try {//readers for files with resources
            BufferedReader brr = new BufferedReader(new FileReader("resolvedTickets.txt"));
            BufferedReader br = new BufferedReader(new FileReader("Tickets.txt"));
            String liner = brr.readLine();
            String line = br.readLine();
            while (line != null) {
                //reading in from the Tickets.txt file
                //split each line in the list to find particular variables needed to put together a Ticket Object
                String[] splitTix = line.split(";");
                int ID = Integer.parseInt(splitTix[0]);
                String description = splitTix[1];
                int priority = Integer.parseInt(splitTix[2]);
                String whoReported = splitTix[3];
                String DATEreported = splitTix[4];
                //basically this is taking a string and making it a date again
                DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy @ hh:mm a");
                Date dateReported = (Date) formatter.parse(DATEreported);
                String resolution = splitTix[5];
                //make sure the highest Ticket ID is located
                highestIDnum = ID;
                //make the ticket from the file and put it back together
                Ticket ticket = new Ticket(ID, description, priority, whoReported, dateReported, resolution);
                //adding the ticket to the JList
                ticketQueListModel.addElement(ticket);
                line = br.readLine();

            }
            while (liner != null) {
                //same principal just with the resolved tickets list
                String[] splitTix = liner.split(";");
                int ID = Integer.parseInt(splitTix[0]);
                String description = splitTix[1];
                int priority = Integer.parseInt(splitTix[2]);
                String whoReported = splitTix[3];
                String DATEreported = splitTix[4];
                DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy @ hh:mm a");
                Date dateReported = (Date) formatter.parse(DATEreported);
                String resolution = splitTix[5];

                highestIDnumr = ID;

                Ticket ticketr = new Ticket(ID, description, priority, whoReported, dateReported, resolution);
                resolvedTicketListModel.addElement(ticketr);

                liner = brr.readLine();
            }
            br.close();//close the readers
            brr.close();
        } catch (IOException ioe) {//Catch exceptions
            System.out.println("Error reading file");
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.toString());
        } catch (ParseException pe) {
            System.out.println(pe.toString());
        }
        //take the highest ticket number from both lists of tickets and start the next new ticket on the next number
        if(highestIDnum>highestIDnumr){
            Ticket.setStaticTicketIDCounter(highestIDnum +1);
        }else {
            Ticket.setStaticTicketIDCounter(highestIDnumr + 1);
        }

        //what happens when we click the add button to add a new ticket
        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if either textfield is empty then let the user know that they are required to create a ticket
                //this makes sure that the txt files can be written and read properly(for the most part)
                if (problemTextField.getText().equals("") || whoReportedTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(TicketSupportGUI.this, "Please complete all fields");
                } else {
                    //if data is entered gather all user data and make a ticket
                    String problem = problemTextField.getText();
                    int priority = severityComboBox.getSelectedIndex();
                    String reported = whoReportedTextField.getText();
                    Date date = new Date();
                    //set the resolution to "none" so file ticket object can be read in properly if not resolved
                    String resolution = "none";
                    //add the ticket to the list of new tickets
                    Ticket t = new Ticket(problem, priority, reported, date, resolution);
                    ticketQueListModel.addElement(t);
                    //reset textfields
                    problemTextField.setText("");
                    whoReportedTextField.setText("");
                }
            }
        });
        //Sets the options for the drop down list of severity options
        severityComboBox.addItem(1);
        severityComboBox.addItem(2);
        severityComboBox.addItem(3);
        severityComboBox.addItem(4);
        severityComboBox.addItem(5);
        severityComboBox.setSelectedIndex(0);

        //what happens when we click the delete button...
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //no matter what ticket is being deleted, double check to make sure
                int delete = JOptionPane.showConfirmDialog(TicketSupportGUI.this, "Are you sure?",
                        "Delete?", JOptionPane.OK_CANCEL_OPTION);
                if (delete == JOptionPane.OK_OPTION) {
                    //if the user is sure, delete the ticket
                    Ticket toDeleteFromQue = ticketQue.getSelectedValue();
                    Ticket toDeleteFromResolved = resolvedTicketList.getSelectedValue();
                    ticketQueListModel.removeElement(toDeleteFromQue);
                    resolvedTicketListModel.removeElement(toDeleteFromResolved);
                    //tickets can be deleted from both resolved and unresolved lists, only one at a time from each
                }
            }
        });

        //what happens when we click the resolve ticket button...
        resolveTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the user doesnt select a ticket then they need to be reminded that they need to
                if(ticketQue.getSelectedIndex()== -1){
                    JOptionPane.showMessageDialog(TicketSupportGUI.this, "Please select ticket to resolve");
                }else {
                    //if the user does select a ticket, show a dialog where they can enter the resolution
                    String resolve = JOptionPane.showInputDialog(TicketSupportGUI.this, "Enter the resolution.", "");
                    if (!resolve.equals("")) {
                        //as long as a resolution is entered,
                        //that resolution overwrites "none" in the resolution for the ticket
                        Ticket toResolve = ticketQue.getSelectedValue();
                        toResolve.setResolution(resolve);
                        ticketQueListModel.removeElement(toResolve);
                        resolvedTicketListModel.addElement(toResolve);
                    }else{//if no resolution is entered then remind the user that one is necessary
                        JOptionPane.showMessageDialog(TicketSupportGUI.this, "Enter a resolution");
                    }
                }
            }
        });

        //what happens when we click the save button...
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //this is where the lists to be saved are created for each file
                //so the tickets that are still open or have been resolved can still be accessed
                ArrayList<Ticket> openTix = new ArrayList<Ticket>();
                ArrayList<Ticket> resolvedTix = new ArrayList<Ticket>();
                //loop through the list and add each ticket to the list
                for (int i = 0; i<ticketQue.getModel().getSize(); i++){
                    openTix.add(i, ticketQue.getModel().getElementAt(i));
                }
                for (int i = 0; i<resolvedTicketList.getModel().getSize(); i++){
                    resolvedTix.add(i, resolvedTicketList.getModel().getElementAt(i));
                }

                try{//writing to the file
                    BufferedWriter bw = new BufferedWriter(new FileWriter("Tickets.txt"));
                    //for each ticket in the arraylists, a concatenated string is created for each ticket
                        for(Ticket ticket:openTix){
                            bw.write( ticket.lineForFile() + "\n");
                        }
                        bw.close();
                    BufferedWriter bwr = new BufferedWriter(new FileWriter("resolvedTickets.txt"));
                        for(Ticket resolvedTicket:resolvedTix){
                            bwr.write( resolvedTicket.lineForFile() + "\n");
                    }
                    //close the writers
                    bw.close();
                    bwr.close();
                }//catch exceptions
                catch (IOException ioe){
                    JOptionPane.showMessageDialog(rootPanel, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println(ioe.toString());
                }
                //show message confirming save operation
                JOptionPane.showMessageDialog(TicketSupportGUI.this, "Tickets saved to file");

            }
        });
        //what happens when we click the quit button...
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show dialog with options to make sure the user wants to quir
                int quit = JOptionPane.showConfirmDialog(TicketSupportGUI.this, "Are you sure you want to quit?",
                        "Quit?", JOptionPane.OK_CANCEL_OPTION);
                if(quit == JOptionPane.OK_OPTION){
                    System.exit(0);//close
                }

            }
        });
    }
}






package com.miked;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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

    private DefaultListModel<Ticket> ticketQueListModel;
    private DefaultListModel<Ticket> resolvedTicketListModel;




    public TicketSupportGUI() {
        super("Ticket Manager");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(700, 600);



        try{
            BufferedReader br = new BufferedReader(new FileReader("Tickets.txt"));
            String line = br.readLine();
            while(line != null){


                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ioe){
            System.out.println("Error reading file");
        }


        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (problemTextField.getText().equals("") || whoReportedTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(TicketSupportGUI.this, "Please complete all fields");
                } else {
                    String problem = problemTextField.getText();
                    int priority = severityComboBox.getSelectedIndex();
                    String reported = whoReportedTextField.getText();
                    Date date = new Date();
                    String resolution = "";

                    Ticket t = new Ticket(problem, priority, reported, date, resolution);
                    ticketQueListModel.addElement(t);


                }
            }
        });
        ticketQueListModel = new DefaultListModel<Ticket>();
        ticketQue.setModel(ticketQueListModel);
        resolvedTicketListModel = new DefaultListModel<Ticket>();
        resolvedTicketList.setModel(resolvedTicketListModel);


        severityComboBox.addItem(1);
        severityComboBox.addItem(2);
        severityComboBox.addItem(3);
        severityComboBox.addItem(4);
        severityComboBox.addItem(5);
        severityComboBox.setSelectedIndex(0);
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int delete = JOptionPane.showConfirmDialog(TicketSupportGUI.this, "Are you sure?",
                        "Delete?", JOptionPane.OK_CANCEL_OPTION);
                if (delete == JOptionPane.OK_OPTION) {
                    Ticket toDeleteFromQue = ticketQue.getSelectedValue();
                    Ticket toDeleteFromResolved = resolvedTicketList.getSelectedValue();
                    ticketQueListModel.removeElement(toDeleteFromQue);
                    resolvedTicketListModel.removeElement(toDeleteFromResolved);

                }
            }
        });
        resolveTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resolve = JOptionPane.showInputDialog(TicketSupportGUI.this, "Enter the resolution.", "");
                if (resolve != null) {

                    Ticket toResolve = ticketQue.getSelectedValue();
                    toResolve.setResolution(resolve);
                    ticketQueListModel.removeElement(toResolve);
                    resolvedTicketListModel.addElement(toResolve);
                }
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Ticket> openTix = new ArrayList<Ticket>();
                for (int i = 0; i<ticketQue.getModel().getSize(); i++){
                    openTix.add(i, ticketQue.getModel().getElementAt(i));
                }

                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter("Tickets.txt"));
                        for(Ticket ticket:openTix){
                            bw.write( ticket.toString() + "\n");
                        }
                        bw.close();
                }
                catch (IOException ioe){
                    JOptionPane.showMessageDialog(rootPanel, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println(ioe.toString());
                }

            }
        });
    }
}






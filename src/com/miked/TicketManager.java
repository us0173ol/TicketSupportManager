package com.miked;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketManager {
    private static Scanner scanner;
    public static void main(String[] args) {

        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

        scanner = new Scanner(System.in);
        String description;
        String reporter;
        Date dateReported = new Date();


        while (true) {
            System.out.println("1. Enter Ticket \n" +
                    "2. Delete Ticket \n" +
                    "3. Display All Tickets \n" +
                    "4. Quit");
            int task = getPositiveIntInput();

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new Tickets
                addTickets(ticketQueue);
            } else if (task == 2) {
                //delete a ticket
                deleteTicket(ticketQueue);

            } else if (task == 4) {
                //Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");

            } else {
                //this will happen for 3 of any other selection that is a valid int
                //TODO Program crashes if you enter anything else-Please fix
                printAllTickets(ticketQueue);
            }
            scanner.close();
        }

    }

    private static int getPositiveIntInput() {
        while (true){
            try{
                String stringInput = scanner.nextLine();
                int intInput = Integer.parseInt(stringInput);
                if( intInput >=0){
                    return intInput;
                }else {
                    System.out.println("Please enter a positive number");
                    continue;
                }
            }catch (NumberFormatException ime){
                System.out.println("Please type a positive number");
            }
        }
    }

    protected static void deleteTicket(LinkedList<Ticket> ticketQueue) {
        //What to do here? Need to delete ticket, but how do we identify the ticket to delete?
        //TODO implement this method
        printAllTickets(ticketQueue); //display list for user

        if (ticketQueue.size() == 0) {//no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }
        Scanner deleteScanner = new Scanner(System.in);

            System.out.println("Enter ID of ticket to delete");
            int deleteID = deleteScanner.nextInt();


            //Loop of over all tickets. Delete the one with this ticket ID
            boolean found = false;
            for (Ticket ticket : ticketQueue)
                if (ticket.getTicketID() == deleteID) {
                    found = true;
                    ticketQueue.remove(ticket);
                    System.out.println(String.format("Ticket %d deleted", deleteID));
                    break; // dont loop any more
                }
            if (!found) {
                System.out.println("Ticket ID not found, no ticket deleted");
                //TODO - rewrite this method to ask for ID again if not found
                return;
            }
            printAllTickets(ticketQueue);//print updated list

            System.out.println("Delete tickets method called");

        }


    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {


        Scanner sc = new Scanner(System.in);
        //Ask for some ticket info, create tickets, store in ticketQueue
        String description;
        String reporter;
        //Lets assume all tickets are created today, for testing. Can change later
        Date dateReported = new Date();//Default constructor creates date with current date/time
        int priority;
        boolean moreProblems = true;
        while (moreProblems) {
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());

            Ticket t = new Ticket(description, priority, reporter, dateReported);
            //ticketQueue.add(t);
            addTicketInPriorityOrder(ticketQueue, t);
            //to test, lets print out all of the currently stored tickets after
            //a new ticket is entered
            printAllTickets(ticketQueue);
            System.out.println("More tickets?");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
        sc.close();
    }

    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket) {

        //Logic: assume the list is either empty or sorted
        if (tickets.size() == 0) {//Special case- if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list
        //Tickets with the LOWEST priority go at the end of the list
        int newTicketPriority = newTicket.getPriority();
        for (int x = 0; x < tickets.size(); x++) {//for loop so we know which element we are looking at
            //if newTicket is higher or equal priority than the this element, add it in front of this one and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }
        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println("--------All tickets--------");
        for (Ticket t : tickets) {
            System.out.println(t);//While a toString method in Ticket Class
            //println will try to call toString on its argument
        }
    }
}

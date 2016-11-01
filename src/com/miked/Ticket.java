package com.miked;

import java.util.Date;

/**
 * Created by miked on 10/9/2016.
 */
public class Ticket {
    private int priority;
    private String reporter; //Stores person or dept. who reported issue
    private String description;
    private Date dateReported;

    //STATIC Counter - accessible to all Ticket objects
    //If any Ticket object modifies this counter, all Ticket objects will have
    //the modified value
    //Make it private - only Ticket objects will have the modified value
    private  static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have its own
    //ticketID variable
    protected int ticketID;

    public Ticket(String desc, int p, String rep, Date date){
        this.description = desc;
        this.priority = p;
        this. reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;

    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public static int getStaticTicketIDCounter() {
        return staticTicketIDCounter;
    }

    public static void setStaticTicketIDCounter(int staticTicketIDCounter) {
        Ticket.staticTicketIDCounter = staticTicketIDCounter;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    protected int getPriority(){
        return priority;
    }
    //Called automatically if a ticket is an argument to System.out.println
    public String toString(){
        return ("ID= " + this.ticketID + " Issued: " + this.description + " Priority: "
                + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
}

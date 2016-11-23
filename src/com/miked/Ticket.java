package com.miked;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by miked on 10/9/2016.
 */
public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private String resolution;

    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy @ hh:mm a");

    Ticket(String desc, int p, String rep, Date date, String res) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.resolution = res;
        this.ticketID = staticTicketIDCounter;

        staticTicketIDCounter++;
    }





    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    protected int getPriority() {
        return priority;
    }

    public int getTicketID() {
        return ticketID;
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

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String toString(){
        return("ID= " + this.ticketID + " Issue: " + this.description + " Priority: " + (this.priority +1) + " Reported by: "
                + this.reporter + " Reported on: " + sdf.format(this.dateReported) + " Resolution: " + resolution);
    }


}

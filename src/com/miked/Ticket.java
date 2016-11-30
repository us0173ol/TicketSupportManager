package com.miked;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private String resolution = "none";

    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;
    //format the date for easier reading
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy @ hh:mm a");

    //ticket object
    Ticket(String desc, int p, String rep, Date date, String res) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.resolution = res;
        this.ticketID = staticTicketIDCounter;

        staticTicketIDCounter++;
    }
    //ticket object where ID is set
    Ticket(int ID, String desc, int p, String rep, Date date, String res) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.resolution = res;
        this.ticketID = ID;

        staticTicketIDCounter++;
    }
    //getters and setters not really used but just in case
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

    public String toString(){//still needed to write and read for files
        return( this.ticketID + ";" + this.description + ";" + (this.priority +1) + ";"
                + this.reporter + ";" + sdf.format(this.dateReported) + ";" + resolution);
    }
    //set up an easy way to split line for file writing and reading
    public String lineForFile(){
        String priorityFF = Integer.toString(this.priority + 1);
        String dateReportedFF = sdf.format(this.dateReported);
        return( this.ticketID + ";" + this.description + ";" + priorityFF + ";"
                + this.reporter + ";" + dateReportedFF + ";" + resolution);

    }

}

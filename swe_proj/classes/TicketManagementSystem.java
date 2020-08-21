package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import utility.Info;

public class TicketManagementSystem {
	public ArrayList<Ticket> ticketList;
	
	public TicketManagementSystem(){
		ticketList = new ArrayList<>();
	}
	public void addTicket(Ticket ticket){
		ticketList.add(ticket);
	}
	public void removeTicket(int i) {
		ticketList.remove(i);
	}
}

package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import utility.Info;

public class AuctionTicketManagementSystem {
	public ArrayList<AuctionTicket> auctionTicketList;
	
	public AuctionTicketManagementSystem(){
		auctionTicketList = new ArrayList<>();
	}
	public void addTicket(AuctionTicket ticket){
		auctionTicketList.add(ticket);
	}
	public void removeTicket(int i) {
		auctionTicketList.remove(i);
	}
}

package classes;

import java.sql.Date;
import java.util.ArrayList;

import utility.Info;

public class Reservation {
	public int reservationId;
	public int buyerId;
	public Ticket ticket;
	public String reservationDate;
	
	public Reservation(int reservationId, int buyerId, String ticketdate, String city, String home, String away, int seat, int price, String reservationDate) {
		this.reservationId = reservationId;
		this.buyerId = buyerId;
		this.ticket = new Ticket(0, 0, ticketdate, "", city, home, away, seat, price);
		this.reservationDate = reservationDate;
	}
}

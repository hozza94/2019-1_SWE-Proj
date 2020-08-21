package classes;

import java.util.Vector;

public class Buyer extends Member{
	private Reservation reservation;
	private Member member;
	private int mileagePoint;
	private int ticketCount;
	private int paymentMoney;
	private Vector<Ticket> listTicket;
	
	public Buyer(){
		
	}
	public void buyerLogin(){
		
	}
	public void buyerRegiseration(){
		
	}
	public void buyerDelete(){
		
	}
	public void viewReservation(){
		
	}
	public void buyTicket(){
		
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public int getMileagePoint() {
		return mileagePoint;
	}
	public void setMileagePoint(int mileagePoint) {
		this.mileagePoint = mileagePoint;
	}
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}
	public int getPaymentMoney() {
		return paymentMoney;
	}
	public void setPaymentMoney(int paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	public Vector<Ticket> getListTicket() {
		return listTicket;
	}
	public void setListTicket(Vector<Ticket> listTicket) {
		this.listTicket = listTicket;
	}
	
	
}

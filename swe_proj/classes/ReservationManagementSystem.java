package classes;

import java.util.ArrayList;
import java.util.Vector;

public class ReservationManagementSystem {
	public ArrayList<Reservation> reservationList;
	
	public ReservationManagementSystem(){
		reservationList = new ArrayList<>();
	}
	
	public void addReservation(Reservation reservation){
		reservationList.add(reservation);
	}
	public void removeReservation(int i){
		reservationList.remove(i);
	}
}

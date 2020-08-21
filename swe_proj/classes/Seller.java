package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import utility.Info;

public class Seller extends Member{
	public int sellerId;
	private Member member;
	public Ticket sellerTicket;
	public int profit;
	public Vector<Ticket> listTicket;
	
	public Seller(){
		
	}
	public void sellerLogin(){
		
	}
	public void checkForTicketOnSale(){
		
	}
	public void sellerRegistration(){
		
	}
	public void sellerDelete(){
		
	}
	public void sellTicket(){
		
	}
	
	
	
	public int getSellerId(){
		return this.sellerId;
	}
	
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	
	
	public Ticket getSellerTicket() {
		return sellerTicket;
	}
	public void setSellerTicket(Ticket sellerTicket) {
		this.sellerTicket = sellerTicket;
	}
	public int getProfit() {
		return profit;
	}
	public void setProfit(int profit) {
		this.profit = profit;
	}
	public Vector<Ticket> getListTicket() {
		return listTicket;
	}
	public void setListTicket(Vector<Ticket> listTicket) {
		this.listTicket = listTicket;
	}
	
	/*		옥션등록부분 수정중 2019/05/31
	public void autionTicketRegister() {
		try {
			Connection conn = null;
			String sql = "insert into aution(ticketId, winningBidderId, winningBid, timer) "
					+ "values(?,?,?,?)";
			
			conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, sellerTicket.get);
			pstmt.setString(2, ticketDate);
			pstmt.setString(3, registerDate);
			pstmt.setString(4,  city);
			pstmt.setString(5, homeTeam);
			pstmt.setString(6, awayTeam);
			pstmt.setInt(7, seat);
			pstmt.setInt(8,  ticketPrice);
			
			int result = pstmt.executeUpdate();
			
			//----------등록 실패시 수행할 기능 필요
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	
}

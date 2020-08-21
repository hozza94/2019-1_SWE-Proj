package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import utility.Info;

public class Ticket {
	public int id;
	public int sellerId;
	public String ticketDate;
	public String registerDate;
	public String city;
	public String homeTeam;
	public String awayTeam;
	public int seat;
	public int ticketPrice;
	
	public Ticket(String ticketDate, String city, String homeTeam, String awayTeam, String seat) {
		this.ticketDate = ticketDate;
		this.city = city;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.seat = Integer.parseInt(seat);
	}
	
	public Ticket(int id, int sellerId, String ticketDate, String registerDate, String city, String homeTeam, String awayTeam, int seat,
			int ticketPrice) {
		this.id = id;
		this.sellerId = sellerId;
		this.ticketDate = ticketDate;
		this.registerDate = registerDate;
		this.city = city;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.seat = seat;
		this.ticketPrice = ticketPrice;
	}
	
	
	// ---------------- 판매자 티켓 등록 부분		
	//seller 클래스로 이동해야 함	2019/05/31
	public void registerSellerTicket() {
		try {
			Connection conn = null;
			String sql = "insert into ticket(sellerId, ticketDate, registerDate, city, homeTeamId, awayTeamId, seat, ticketPrice) "
					+ "values(?,?,?,?,?,?,?,?)";
			
			conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, sellerId);
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
}
